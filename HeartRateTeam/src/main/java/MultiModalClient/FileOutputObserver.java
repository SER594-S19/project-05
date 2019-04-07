package MultiModalClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is used to generate CSV files from different servers
 *
 * @version 20190213
 *
 */

public class FileOutputObserver implements Observer {

	private String fileName;
	private BufferedWriter writer;

	// Constructor for FileOutputObserver class
	public FileOutputObserver() {
		
		// System.out.println( sdf.format(cal.getTime()) );
		//name currently hardcoded but needs to change as per simulator
		fileName = "output-HR" + getTimeStamp() + ".csv";
		try {
			writer = new BufferedWriter(new FileWriter(fileName, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Update method used to generate the CSV file and close the writer stream if button is stopped
	@Override
	public void update(Observable o, Object arg) {

		CombinedDataStatefull csObject = (CombinedDataStatefull) o;
		String data = csObject.getObject().toString();
		FileOutputObserver fileOutput = csObject.getFile();
		if (data.equals("FIN")) {
			
			System.out.println("shutdown");
			try {
				fileOutput.writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				System.out.println("Writing to file "+fileOutput.fileName);
				fileOutput.writer.write(data);
				fileOutput.writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	// Method used to get the time stamp in String format
	String getTimeStamp() {
		return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	}
	//To implement next format data for csv format
	// formatDataForCsv(String )

}
