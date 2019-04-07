package BCI.Core;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.TimeZone;

import BCI.Model.AffectiveData;
import BCI.Model.ExpressionData;

public class DataGenerator extends Observable implements Runnable {

  private Data data;
  private boolean stop = false; 

  public void stop() {
    this.stop = true;
  }

  public Object getObject() {
    return data;
  }
  
  public DataGenerator() {
	  this.data=new Data("0",new double[14],new AffectiveData(),new ExpressionData());
  }

  @Override 
  public void run() {
    stop = false;
    //Date date = new Date();

    /*
    System.out.println("time in mill"+dateFormat.format(date)+":"+initialTime%1000);
    calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
   // calendar.setTime(date);
    StringBuilder string = new StringBuilder();
    string.append(calendar.get(Calendar.YEAR)).append(calendar.get(Calendar.MONTH)).append(calendar.get(Calendar.DATE));
    System.out.println("time is jdhsdgdfhd " + string);
    double timeStamp = 0;
    String time = null;
    */

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
      //timeStamp = (System.currentTimeMillis() - initialTime) * .001;
      double[] values=new double[14];
      for(int i=0;i<values.length;i++) {
    	double val=Math.random();
        values[i]=val;
        Gui.channelLabels.get(i).setText(val+"");
        setBackGround(val,i);
       }
      data.setValues(values);
      data.setTimeStamp(k);
      createAndNotify();
      try {
        Thread.sleep(9); 
      } catch (InterruptedException ex) {
      }
    }
  }

  private void createAndNotify() {
    System.out.println("notifying ...");
    setChanged();
    notifyObservers();
  }
  
  private void setBackGround(double val,int index) {
	  Gui.channelButtons.get(index).setOpaque(true);
	  if(val<0.2 && val>0) {
		  Gui.channelButtons.get(index).setBackground(Color.decode("#b2dcb2"));
	  } else if(val<0.4 && val>0.2) {
		  Gui.channelButtons.get(index).setBackground(Color.decode("#7fc57f"));
	  } else if(val<0.6 && val>0.4) {
		  Gui.channelButtons.get(index).setBackground(Color.decode("#66b966"));
	  } else if(val<0.8 && val>0.6) {
		  Gui.channelButtons.get(index).setBackground(Color.decode("#32a232"));
	  } else  {
		  Gui.channelButtons.get(index).setBackground(Color.decode("#008b00"));
	  }
  }

}
