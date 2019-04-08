package BCI.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.HashMap;
import ClientHeartRateTeam.FileOutputObserver;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class NewSubscriber extends Observable implements Runnable {

	private boolean stop;
	private String Ip;
	private int port;
	private String data;
	private Writer file;
	private int num;
	private HashMap<Integer, Integer> map;
	private static File trainFile = new File("train.txt"); 
	private static FileWriter outputfile ; 
	private static BufferedWriter writer=null;
	NewSubscriber(String Ip, int port) {
		this.stop=false;
		this.Ip = Ip;
		this.port = port;
		this.num=0;
		this.map= new HashMap<>();
		if(writer==null) {
			try {
				outputfile=new FileWriter(trainFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writer=new BufferedWriter(new FileWriter("train.txt",true));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			String[] header = { "Pleasure", "Arousal", "Label" }; 
//		     writer.writeNext(header); 
		}
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

	public Writer getFile() {
		return file;
	}

	public void setFile(Writer file) {
		this.file = file;
	}

	public void stop() {
		stop = true;
		 try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	}

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
			if(!map.containsKey(port)){
				map.put(port,1);
			}else{
				map.put(port,map.get(port)+1);
			}
			for(Integer p:map.keySet()) {
				num=Math.max(num,map.get(p));
			}

			file = new Writer(num);
			this.addObserver(file);
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
				System.out.println(measureLocal);
				setData(measureLocal);
				calculatePA(measureLocal);
//				if(measureLocal.contains("Channel 5")) {
//					String[] tokens=measureLocal.split(":");
//					PADCalculator.pleasure=Double.parseDouble(tokens[1]);
//					//PADCalculator.calculatePA();
//					VectorProject2.vectorSeries.remove(0);
//					VectorProject2.vectorSeries.add(0, 0, PADCalculator.pleasure, PADCalculator.arousal);
//					labelCalculator();
//				}
				
//				if(measureLocal.contains("Heart Rate")) {
//					
//					String[] tokens=measureLocal.split(",")[1].split("=");
//					PADCalculator.arousal=Double.parseDouble(tokens[1]);
//					PADCalculator.arousal=PADCalculator.arousal<100?PADCalculator.arousal/100:PADCalculator.arousal/1000;
//					//PADCalculator.calculatePA();
//					VectorProject2.vectorSeries.remove(0);
//					VectorProject2.vectorSeries.add(0, 0, PADCalculator.pleasure, PADCalculator.arousal);
//					labelCalculator();
//				}
//				
				setChanged();
				notifyObservers();
			}
			try {
				if(port==1700) {
				Thread.sleep(100);
				}
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
	
	private void labelCalculator() {
		if(PADCalculator.pleasure > 0.5 && PADCalculator.arousal <0.5) {
			PlotPanel.getIntance().setHappyFace();
			writePAToCsv(1);
			
		}
		if(PADCalculator.pleasure > 0.5 && PADCalculator.arousal >0.5) {
			PlotPanel.getIntance().setNeutralFace();
			writePAToCsv(2);
		}
		if(PADCalculator.pleasure < 0.5 && PADCalculator.arousal >0.5) {
			PlotPanel.getIntance().setSadFace();
			writePAToCsv(3);
		}

		if(PADCalculator.pleasure < 0.5 && PADCalculator.arousal <0.5) {
			PlotPanel.getIntance().setNeutralFace();
			writePAToCsv(2);
		}
		
	}
	
	private void calculatePA(String measureLocal) {
		if(measureLocal.contains("short term engagement")) {
			String[] tokens=measureLocal.split(":");
			PADCalculator.stexcitement=Double.parseDouble(tokens[1]);
			PADCalculator.calculatePA();
			
		}
		if(measureLocal.contains("long term engagement")) {
			String[] tokens=measureLocal.split(":");
			PADCalculator.ltexcitement=Double.parseDouble(tokens[1]);
			PADCalculator.calculatePA();
			
		}
		if(measureLocal.contains("meditation")) {
			String[] tokens=measureLocal.split(":");
			PADCalculator.mediatation=Double.parseDouble(tokens[1]);
			PADCalculator.calculatePA();	
		}
		if(measureLocal.contains("frustration")) {
			String[] tokens=measureLocal.split(":");
			PADCalculator.frustration=Double.parseDouble(tokens[1]);
			PADCalculator.calculatePA();	
		}
		VectorProject2.vectorSeries.remove(0);
		VectorProject2.vectorSeries.add(0, 0, PADCalculator.pleasure, PADCalculator.arousal);
		labelCalculator();
		
	}
	private void writePAToCsv(int label) {
		 String  data1 =  PADCalculator.pleasure +","+ PADCalculator.arousal+ ","+label ; 
		 try {
			writer.write(data1);
			writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
