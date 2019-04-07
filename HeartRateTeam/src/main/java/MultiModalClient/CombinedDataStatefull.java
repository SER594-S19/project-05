package MultiModalClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import MultiModalClient.FileOutputObserver;

public class CombinedDataStatefull extends Observable {
	private static CombinedDataStatefull instance = null;
	private MultiModalClient.FileOutputObserver file;
	private GraphObserver graphObserver;
	
	String prevData;
	Queue<String> globalQueue ;
	
	public static synchronized CombinedDataStatefull getInstance() {
        if (instance == null)
            instance = new CombinedDataStatefull();

        return instance;
	}
	
	private CombinedDataStatefull() {
		prevData = "0,0,0,0,0";
		globalQueue = new ConcurrentLinkedQueue<String>();
		file = new MultiModalClient.FileOutputObserver();
		graphObserver = new GraphObserver();
		this.addObserver(file);
		this.addObserver(graphObserver);
	}

	public Queue<String> getGlobalQueue() {
		return globalQueue;
	}
	
	public synchronized Object getObject() {
		return this.prevData;
	}

	public FileOutputObserver getFile() {
		return file;
	}

	public synchronized void addToGlobalQueue(String data) {
		if(data.equals("FIN")) {
			this.prevData = "FIN";
			setChanged();
			notifyObservers();
		}else {
			String curRow = processRow(data);
			//System.out.println("hello");
			System.out.println(prevData);
			this.globalQueue.add(curRow);
			this.prevData = curRow;
			setChanged();
			notifyObservers();
		}
		
		
	}

	private String processRow(String data) {
		// TODO Auto-generated method stub
		String[] dataVals = data.split(",");
		StringBuffer sb = new StringBuffer();
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		sb.append(timeStamp);
		sb.append(",");
		if(dataVals.length == 2) {
			sb.append(dataVals[1]);
			sb.append(",");
			String[] prevArr = prevData.split(",");
			for(int i =2 ; i < prevArr.length; i++) {
				sb.append(prevArr[i]);
				sb.append(",");
			}
		}else if(dataVals.length == 4) {
			String[] prevArr = prevData.split(",");;
			sb.append(prevArr[1]);
			sb.append(",");
			for(int i =1; i <dataVals.length;i++) {
				sb.append(dataVals[i]);
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	
	
	
}
