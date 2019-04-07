package Client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.io.FileWriter;

/**
 * ThreadSubscriber. A generic subscriber; get data from a network connection;
 * data is served to local consumers
 *
 * @author javiergs
 * @version 20140915
 *
 */
public class Subscriber extends Observable implements Runnable {

  private boolean stop;
  private String Ip;
  private int port;
  private String data;

  public Subscriber(String Ip, int port) {
    this.stop = false;
    this.Ip = Ip;
    this.port = port;
  }

  protected synchronized void setData(String data) {
    this.data = data;
  }

  public synchronized Object getObject(){
    return label(this.data);
  }

  public void stop() {
    this.stop = true;
  }

  @Override
  public void run() {

    Socket client = null;
    ObjectInputStream ois = null;
    BufferedReader input = null;
    String measureLocal=null;
    try {
      client = new Socket(InetAddress.getByName(Ip.trim()), port); 
      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
      client.setSoTimeout(1000);
    } catch (IOException ex) {
      this.stop = true;
    }
    while (!this.stop) {
      System.out.println("in hello");
      try {
        measureLocal= input.readLine();
      System.out.println("in hello read");
      } catch (IOException sce) {
        measureLocal= null;
      }
      if (measureLocal == null) {
        this.stop = true;
      } else {
              System.out.println("in hello read and =" + this.stop + " " + "ip/port = " + Ip + port + " " + measureLocal);
        setData(measureLocal);
        setChanged();
        notifyObservers();
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException ex) {
      }
  System.out.println("in hello end while" + this.stop);
    }
                  //ClientDemo.serverStopped(this.Ip, this.port);
                  //System.out.println("in hello end loop");
    try {
      if (ois != null) {
        ois.close();
      }
      if (input != null) {
        input.close();
      }
      if (client != null) {
        client.close();
      }
    } catch (IOException e) {
    }
    setData("FIN");
    setChanged();
    notifyObservers();
  }

  public String label(String input){
    String[] arrOfStr = input.split("[= ,}]+");

  String label="Average";

    if(Double.parseDouble(arrOfStr[4])>0.8 && Double.parseDouble(arrOfStr[10])<0.15)
      label="Good";
    else if(Double.parseDouble(arrOfStr[4])<0.15 && Double.parseDouble(arrOfStr[10])>0.8)
      label="Bad";
    //return "Agree="+arrOfStr[4]+" Disagree="+arrOfStr[8]+" Frurstrate="+arrOfStr[10]+" Concentrate="+arrOfStr[6]+" Thinking="+arrOfStr[12]+" Unsure="+arrOfStr[14];
    try {
      String csvFile = "result.csv";
      FileWriter writer = new FileWriter(csvFile, true);
      writer.write(arrOfStr[4]+ "," +(-1 * Double.parseDouble(arrOfStr[4]))+"," +(-1 * Double.parseDouble(arrOfStr[4]))+"," + (-1 * Double.parseDouble(arrOfStr[10])) +","+ arrOfStr[10] +"," +arrOfStr[10]+"," + label+ "\n");


      writer.flush();
      writer.close();
    }
    catch (Exception e){
      System.out.println("Something went wrong.");
    }
    return label;
  }
}
