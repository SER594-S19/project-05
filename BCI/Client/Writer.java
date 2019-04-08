package BCI.Client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


public class Writer implements Observer {

	private String file;
	private BufferedWriter fileWriter;



	public Writer(int num) {
		file = num+" BCI output " + ".csv";
		try {
			fileWriter = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void update(Observable observable, Object arg) {

		NewSubscriber subscriber = (NewSubscriber) observable;
		String data = subscriber.getObject().toString();
		Writer output = subscriber.getFile();
		if (data.equals("FIN")) {
			try {
				output.fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				output.fileWriter.write(data);
				output.fileWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
