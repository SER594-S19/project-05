package EyeTrackerV1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import java.util.TimeZone;

public class DataGenerator extends Observable implements Runnable
{
  private DataEyeTracker data;
  private Gui val;
  private boolean stop = false;
  protected static HashMap<String, Double> eyeParameters = new HashMap<String,Double>() 
  {
	  private static final long serialVersionUID = 1L;   
	  {
			put("pupilLeft", 0.0);
			put("pupilRight", 0.0);
			put("gpxValue", (double) 0);
			put("gpyValue", (double) 0);
			put("validityL", (double) 0);
			put("validityR", (double) 0);
			put("fixationValue", (double) 0);
			put("event", (double) 0);
			put("aoi", (double) 0);
	  }
  };//eyeParameter declaration

  public DataEyeTracker getDataEyeTracker() 
  {
	  return data;
  }//getDataEyeTracker
  
  public void stop() 
  {
	  this.stop = true;
  }//stop

  public Object getObject() 
  {
	  return data;
  }//getObject

  
  @Override
  public void run()
  {
		System.out.println("Within Thread Run()");
	    stop = false;

	   
	    while (!stop) 
	    {
	    	System.out.println("data generator running");
		    Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			System.out.println("UTC Time is: " + dateFormat.format(date));
		    Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    long initialTime = calendar.getTimeInMillis();
		    System.out.println("time in mill"+initialTime);
		    System.out.println("time in mill"+initialTime%1000);
		    String k = dateFormat.format(date)+":"+initialTime%1000;
			    	
	    	createAndNotify(k, Math.random(), generatePupilLeft(), generatePupilright(), generateGpxValue(), generate_GpyValue(), generateValidityL(), generateValidityR(), fixationValue(), event(), aoi());
	    	
	    	try 
	    	{
	    		Thread.sleep(100);
	    	}//try 
	    	catch (InterruptedException ex) 
	    	{
	    		System.out.println("Exception..");
	    	}//catch
	    }//while
  	}//run

  
  public void updateParam(String s, Double val) 
  {
	  
	  if (s == "pupilLeft" || s == "pupilRight") 
	  {
		  eyeParameters.put(s, val);
	  }
	  else 
	  {
		  eyeParameters.put(s, Math.floor(val));
	  }
	  
	  System.out.println(eyeParameters);
  }

  public static Double generateGpxValue() 
  {
	  return eyeParameters.get("gpxValue");
  }
  
  public static Double generate_GpyValue() 
  {
	  return eyeParameters.get("gpyValue");
  }
  
  public static double generatePupilLeft() 
  {
	  return eyeParameters.get("pupilLeft");
  }
  

  public static double generatePupilright() 
  {
	  return eyeParameters.get("pupilRight");
		  
	  }

  public static double generateValidityL()
  {
	return  eyeParameters.get("validityL");
		
	}
  public static double generateValidityR() 
  {
	  return eyeParameters.get("validityR");
	  
  }
  public static double fixationValue() 
  {
	  return eyeParameters.get("fixationValue");
  }
  public static double event()
  {
	  return eyeParameters.get("event");
  }
  public static double aoi() 
  {
	  return eyeParameters.get("aoi");
  }

 /**
  * This method will instantiate the Data Eye Tracker and Notify all the observers.
  * @param k
  * @param s
  * @param pupilLeft
  * @param pupilRight
  * @param gpxValue
  * @param d
  * @param e
  * @param f
  * @param g
  * @param h
  */
  private void createAndNotify(String k, double s, double pupilLeft, 
		  double pupilRight,double gpxValue, double gpyValue,
		  double d, 
		  double e, double f, double g, double h)
	{
	    System.out.println("notifying ...");
	    data = new DataEyeTracker(k, s, pupilLeft, pupilRight, gpxValue, gpyValue, d, e, f, g, h);
	    System.out.println("The data is " + data);
	    setChanged();
	    notifyObservers();
	}
}
