package Face.Core;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Random;
import java.util.TimeZone;

class DataGenerator extends Observable implements Runnable {

  private Data data;
  private boolean stop = false;
  private String emotionFromGUI;
  
  public String getEmotion() {
		return emotionFromGUI;
  }

  public void setEmotion(String emotion) {
	this.emotionFromGUI = emotion;
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
	

    while (!stop) {
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
      createAndNotify(k);
      try {
        Thread.sleep(34);
      } catch (InterruptedException ex) {
      }
    }
  }
  
  private double getValueBetweenExpRange(SecureRandom rand,String createEmo) {
	  Random r = new Random();
	  int powerOf = 0;
	  if(!createEmo.equals(this.emotionFromGUI)){
		  powerOf = r.nextInt(Constants.RANGE_HIGH-Constants.RANGE_LOW) + Constants.RANGE_LOW;
	  }
	  double randomValue = rand.nextDouble();
	  return randomValue;
  }

  private void createAndNotify(String timestampsystem) {
                  System.out.println("notifying ...");
    SecureRandom random = new SecureRandom();
    double agreementRange = getValueBetweenExpRange(random,"Agreement");
    double concentratingRange = getValueBetweenExpRange(random,"Concentrating");
    double disagreementRange = getValueBetweenExpRange(random,"Disagreement");
    double interestedRange = getValueBetweenExpRange(random,"Interested");
    double thinkingRange = getValueBetweenExpRange(random,"Thinking");
	double unsureRange = getValueBetweenExpRange(random,"Unsure");
    data = new Data(timestampsystem, agreementRange, concentratingRange, disagreementRange, interestedRange, thinkingRange, unsureRange);
    System.out.println("Data generator generated:"+data);
    setChanged();
    notifyObservers();
  }

}
