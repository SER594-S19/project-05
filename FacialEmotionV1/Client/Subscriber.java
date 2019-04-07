package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JOptionPane;

import com.opencsv.CSVWriter;

import Core.Gui;

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
  private String serverPortActive;
  private String serverPortSelected;
  private int attachedButtonVal;
  private File file;
  private int iterator;

  public int getAttachedButtonVal() {
	return attachedButtonVal;
  }

  public void setAttachedButtonVal(int attachedButtonVal) {
	this.attachedButtonVal = attachedButtonVal;
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
  
  public String getServerPortActive() {
	  return serverPortActive;
  }

  public void setServerPortActive(String serverPortActive) {
	  this.serverPortActive = serverPortActive;
  }
  
  public String getServerPortSelected() {
	  return serverPortSelected;
  }

  public void setServerPortSelected(String serverPortSelected) {
	  this.serverPortSelected = serverPortSelected;
  }


  public void stop() {
    stop = true;
  }
  
  public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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
      ClientDemo.successList.get(getAttachedButtonVal()).setVisible(true);
	  ClientDemo.stopList.get(getAttachedButtonVal()).setEnabled(true);
      client.setSoTimeout(1000);
    } catch (IOException ex) {
      stop = true;
	  JOptionPane.showMessageDialog(null, "There is no server running on port "+port+". Aborting connection!");
	  //click corresponding stop button to disallow zombie process to exist.
	  ClientDemo.connectList.get(getAttachedButtonVal()).setEnabled(true);
	  ClientDemo.stopList.get(getAttachedButtonVal()).doClick();
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
        //checkIfCastExists(dataObject);
        if(serverPortActive.equals(serverPortSelected)) {
            setData(measureLocal);
        }else {
            setData("");
        }
        writeToCSVFile(measureLocal);
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

private void writeToCSVFile(String incomingData) {
	if(null == file) {
		file = new File("Client/CSVDumps/"+port+".csv"); 
		System.out.println("File Path:"+file.getAbsolutePath());
	}else {
		iterator++;
		System.out.println("File exists:"+file.getName());
	}
	try { 
		String cleanData = data.split("\\{")[1];
		cleanData = cleanData.replace('}', ' ');
        // create FileWriter object with file as parameter 
        FileWriter outputfile = new FileWriter(file,true); 
  
        // create CSVWriter object filewriter object as parameter 
        CSVWriter writer = new CSVWriter(outputfile); 
  
        // adding header to csv 
        String[] dataSplit = cleanData.split(",");
        String[] headers = new String[dataSplit.length];
        String[] rows = new String[dataSplit.length];
        if(iterator == 0) {
        	for(int k=0;k<dataSplit.length;k++) {
        		headers[k] = dataSplit[k].split("=")[0];
        	}
            writer.writeNext(headers); 
        }else {
        	for(int k=0;k<rows.length;k++) {
        		rows[k] = dataSplit[k].split("=")[1];
        	}
        }
  
        // add data to csv 
        writer.writeNext(rows); 
  
        // closing writer connection 
        writer.close(); 
    } 
    catch (IOException e) { 
        // TODO Auto-generated catch block 
        e.printStackTrace(); 
    } 
}
  
}
