package Core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Put data in a port. 
 * It uses Publisher-Assistants to be able to attend more than one client
 * at the same time.
 * 
 * @author javiergs
 */
public class Publisher implements Observer, Runnable {

  private boolean stop = false;
  private final int port;
  private ExecutorService executor;
  protected Observable observable = null;
  private ServerSocket listener = null;
  private final ArrayList<PublisherAssistant> threads = new ArrayList<>();

  public Publisher(int port) {
    this.port = port;
  }

  public void stop() {
    for (int i = 0; i < threads.size(); i++) {
      threads.get(i).stop();
    }
    stop = true;
  }

  /**
   * Create new PublisherAssistant each time that a connection is detected
   * 
   */
  @Override
  public void run() {
    executor = Executors.newCachedThreadPool();
    stop = false;
    // open server
    try {
      listener = new ServerSocket(port);
      listener.setSoTimeout(10);
    } catch (IOException e) {
    }
    // loop
    while (!stop) {
      Socket socket = null;
      try {
        socket = listener.accept();
        if (observable == null) continue;  
        PublisherAssistant assistant = new PublisherAssistant(socket, observable);
        threads.add(assistant);
        executor.submit(assistant);
      } catch (SocketTimeoutException ste) {
      } catch (IOException e) {
        if (stop) break;
      }
    }
    try {
      listener.close();
    } catch (IOException ex) {
    }
    executor.shutdown();
    try {
      if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
        executor.shutdownNow();
      }
    } catch (InterruptedException ie) {
    }
  }
  
  /**
   * This method is called when the class that we are observing change.
   * Every PublisherAssistant is notified of the change
   * 
   * @param o is the object that we are observing
   * @param arg is a parameter that we are not using
   */
  @Override
  public void update(Observable o, Object arg) {
                  System.out.println("publisher updated");

    this.observable = o;
    for (int i = 0; i < threads.size(); i++) {
      System.out.println("assistant try updated");
      threads.get(i).updated();
    }
  }

}
