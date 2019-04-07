package facialgesturesServer1;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.DoubleStream;
import Core.DataGenerator;

class FacialDataGenerator extends DataGenerator implements Runnable {

  private FacialData data;
  private boolean stop = false;
  private ArrayList<Double> facialValues = new ArrayList<>(); 
  public ArrayList<Double> getFacialValues() {
	return facialValues;
}
  public void stop() {
    this.stop = true;
  }

  public Object getObject() {
    return data;
  }

  @Override
  public void run() {
    stop = false;
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    long initialTime = calendar.getTimeInMillis();
    double timeStamp = 0;

    while (!stop) {
              System.out.println("data generator running");
      timeStamp = (System.currentTimeMillis() - initialTime) * .001;
      createAndNotify(timeStamp, getFacialValues());
      try {
        Thread.sleep(40);
      } catch (InterruptedException ex) {
      }
    }
  }

  private void createAndNotify(double timestampsystem, ArrayList<Double> facialValues) {
	    System.out.println("notifying ...");       
	    for(int index=5;index<=11;index++) {
	    	facialValues.set(index, getSecureRandomNumber(facialValues.get(index)));
	    }
	    System.out.println("DG: " + facialValues);
	    data = new FacialData(timestampsystem, facialValues);
	    
	    setChanged();
	    data.toString();
	    notifyObservers();
	  }
	  
	  
	  private double getSecureRandomNumber(double max) {
		  double randomValue = Math.random();
//		  try {
//	    	  SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
//			DoubleStream doubles = instanceStrong.doubles(0.0,max);
//			randomValue = doubles.findAny().getAsDouble();
//			System.out.println("Random Num: " +randomValue);
//	    	} catch (NoSuchAlgorithmException e) {
//	    		
//	    		e.printStackTrace();
//	    	}
		  
		  return randomValue;
	  }

	public void setFacialValues(ArrayList<Double> facialValues) {
		// TODO Auto-generated method stub
		this.facialValues = facialValues;
		
		
	}

}
