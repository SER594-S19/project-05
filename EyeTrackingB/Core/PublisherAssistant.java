package Core;


import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

public class PublisherAssistant implements Runnable {

  protected Socket socket;
  protected Observable observable;
  protected boolean updated;
  protected boolean stop;

  public PublisherAssistant(Socket socket, Observable observable) {
    this.socket = socket;
    this.observable = observable;
    updated = false;
    stop = false;
  }

  @Override
  public void run() {
    System.out.println("***** assistant runing");
    ObjectOutputStream oos = null;
    PrintWriter pw;
    try {
      pw = new PrintWriter(socket.getOutputStream(), true);
      while (!stop) {
            System.out.println("***** assistant loop - " + updated);

        if (!updated) continue;
        post(pw);
        updated = false;
        Thread.sleep(1000);
      }
      if (oos != null) oos.close();
      if (pw != null) oos.close();
      if (socket != null) socket.close();
    } catch (Exception e) {
    }
  }

  private void post(PrintWriter oos) throws Exception {
    Object measure = ((DataGenerator) observable).getObject();
    oos.println(measure);
    oos.flush();
    System.out.println("sending...");
  }

  public void stop() {
    stop = true;
  }

  public void updated() {
   System.out.println("***** assistant updated");

    updated = true;
  }

}
