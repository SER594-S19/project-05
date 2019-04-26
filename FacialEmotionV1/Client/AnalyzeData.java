package Client;

import java.io.File;

public class AnalyzeData {

	public static int runMachineLearningScript() {
		int result = -1;
		try {
	         System.out.println("Calling Machine Learning Script to predict emotion...");
	         File pythonScript = new File("Client/Scripts/readyToGoData.py");
	         Process p = Runtime.getRuntime().exec("/Library/Frameworks/Python.framework/Versions/3.6/bin/python3 "+pythonScript.getAbsolutePath());
	         result = p.waitFor();
	         int len;
	         if ((len = p.getErrorStream().available()) > 0) {
	        	 byte[] buf = new byte[len]; 
	        	 p.getErrorStream().read(buf); 
	        	 System.err.println("Command error:\t\""+new String(buf)+"\""); 
	         }
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }
		return result;
	}
}
