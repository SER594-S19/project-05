package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import com.opencsv.CSVWriter;

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
  private File file;
  private int iter;



  public int getPort(){
    return this.port;
  }


  public Subscriber(String Ip, int port) {
    this.stop = false;
    this.Ip = Ip;
    this.port = port;
  }

  protected synchronized void setData(String data) {
    this.data = data;
  }

  public synchronized Object getObject() {
    return this.data;
  }

  private void writeCSV(String data) {
	  if(null == file) {
	    file = new File("Client/"+port+".csv"); 
	    System.out.println("File Path:"+file.getAbsolutePath());
	  }else {
	    iter++;
	    System.out.println("File exists:"+file.getName());
	  }
	  try { 
	    String formattedData = data;
	    formattedData = formattedData.replace('{', ' ');
	    formattedData = formattedData.replace('}', ' ');
	    formattedData = formattedData.replace('[', ' ');
	    formattedData = formattedData.replace(']', ' ');
	        FileWriter outputfile = new FileWriter(file,true); 
	        CSVWriter csvWriter = new CSVWriter(outputfile); 
	        String[] dataSplit = formattedData.split(",");
	        String[] headers = new String[dataSplit.length];
	        String[] rows = new String[dataSplit.length];
	        if(iter == 0) {
	          for(int k=0;k<dataSplit.length;k++) {
	            if(k == 0) {
	              headers[k] = "time";
	              continue;
	            }
	            headers[k] = dataSplit[k].split("=")[0];
	          }
	          csvWriter.writeNext(headers); 
	        }else {
	          for(int k=0;k<rows.length;k++) {
	            rows[k] = dataSplit[k].split("=")[1];
	          }
	          csvWriter.writeNext(rows); 
	        }
	        csvWriter.close(); 
	    } 
	    catch (IOException e) { 
	        e.printStackTrace(); 
	    } 
	}
  
  public void stop() {
    stop = true;
  }

  @Override
  public void run() {

    Socket client = null;
    ObjectInputStream ois = null;
    BufferedReader input = null;
    stop = false;
    String measureLocal=null;
    try {
      client = new Socket(InetAddress.getByName(Ip.trim()), port); 
      input = new BufferedReader(new InputStreamReader(client.getInputStream()));      
      client.setSoTimeout(1000);
    } catch (IOException ex) {
      stop = true;
    }
    while (!stop) {
      System.out.println("in hello");
      try {
        measureLocal= input.readLine();
      System.out.println("in hello read");
      } catch (IOException sce) {
        measureLocal= null;
      }
      if (measureLocal == null) {
        stop = true;
      } else {
              System.out.println("in hello read and =" + stop + " " + measureLocal);
        setData(measureLocal);
        writeCSV(measureLocal);
        setChanged();
        notifyObservers();
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException ex) {
      }
         System.out.println("in hello end while" + stop);
    }
                  System.out.println("in hello end loop");
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
}
