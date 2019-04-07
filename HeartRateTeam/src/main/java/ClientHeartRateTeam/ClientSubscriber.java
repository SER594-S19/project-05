package ClientHeartRateTeam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

/**
 * ThreadSubscriber. A generic subscriber; get data from a network connection;
 * data is served to local consumers
 *
 * @version 20190213
 *
 */

public class ClientSubscriber extends Observable implements Runnable {

	private boolean stop;
	private String Ip;
	private int port;
	private String data;
	//private FileOutputObserver file;

	// Constructor for Client Subscriber
	ClientSubscriber(String Ip, int port) {
		this.stop = false;
		this.Ip = Ip;
		this.port = port;
	}

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	protected synchronized void setData(String data) {
		this.data = data;
	}

	public synchronized Object getObject() {
		return this.data;
	}
/*
	public FileOutputObserver getFile() {
		return file;
	}

	public void setFile(FileOutputObserver file) {
		this.file = file;
	}*/

	public void stop() {
		stop = true;
	}

	// This method is used to create a socket connection and run the client UI
	@Override
	public void run() {

		Socket client = null;
		ObjectInputStream ois = null;
		BufferedReader input = null;
		stop = false;
		String measureLocal;
		boolean serverCheck = false;
		boolean serverRunning = false;

		try {
			client = new Socket(InetAddress.getByName(Ip.trim()), port);
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			client.setSoTimeout(1000);
			//file = new FileOutputObserver();
			//this.addObserver(file);
			serverCheck = true;
			serverRunning = true;
		} catch (IOException ex) {
			stop = true;
		}

		while (!stop) {
			try {
				measureLocal = input.readLine();
			} catch (IOException sce) {
				measureLocal = null;
			}
			if (measureLocal == null) {
				stop = true;
				serverRunning = false;
			} else {
				//System.out.println(measureLocal);
				setData(measureLocal);
				setChanged();
				notifyObservers();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				System.out.println("Exception: " + ex);
			}
		}

		try {
			if (ois != null)
				ois.close();
			if (input != null)
				input.close();
			if (client != null)
				client.close();
		} catch (IOException e) {
			System.out.println("Exception: " + e);
		}
		if (!serverCheck)
			setData("FAIL");
		else if (!serverRunning)
			setData("STOPPED");
		else
			setData("FIN");
		setChanged();
		notifyObservers();
	}

}
