package BCI.Core;

import java.awt.Color;
import java.util.Calendar;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

import BCI.Model.AffectiveData;
import BCI.Model.ExpressionData;

public class DataGenerator extends Observable implements Runnable {

  private Data data;
  private boolean stop = false; 
  public String setting="random";
  public void stop() {
    this.stop = true;
  }

  public Object getObject() {
	  System.out.println("Data sent is" +data.getAffective().getFrustration()+"");
    return data;
    
  }
  
  public DataGenerator() {
	  this.data=new Data(0,new double[14],new AffectiveData(),new ExpressionData());
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
      double[] values=new double[14];
      for(int i=0;i<values.length;i++) {
    	double val=Math.random();
        values[i]=val;
        Gui.channelLabels.get(i).setText(val+"");
        setBackGround(val,i);
       }
      data.setValues(values);
      data.setTimeStamp(timeStamp);
      setData();
      createAndNotify();
      try {
        Thread.sleep(1000); 
      } catch (InterruptedException ex) {
      }
    }
  }
  
  private void setData() {
	  if(setting.equals("happy")) {
		  setHappyData();
	  } else if(setting.equals("neutral")) {
		  setNeutralData();
	  } else if(setting.equals("sad")) {
		  setSadData();
	  } else if(setting.equals("random")) {
		  setRandomData();
	  }
  }
  public void setHappyData() {
	  AffectiveData affectiveData=this.data.getAffective();
	  double frustration = ThreadLocalRandom.current().nextDouble(0, 0.4);
	  Gui.affectivePanel.getAffectiveController().setFrustration(frustration);
	  double engagement = ThreadLocalRandom.current().nextDouble(0, 0.3 + 1);
	  Gui.affectivePanel.getAffectiveController().setEngagement(engagement);
	  double meditation = ThreadLocalRandom.current().nextDouble(0, 0.2);
	  Gui.affectivePanel.getAffectiveController().setMeditation(meditation);
	  double stengagement = ThreadLocalRandom.current().nextDouble(0.5, 1);
	  Gui.affectivePanel.getAffectiveController().setStExcitement(stengagement);
	  double ltengagement = ThreadLocalRandom.current().nextDouble(0.5, 1);
	  Gui.affectivePanel.getAffectiveController().setltExcitement(ltengagement);
	  data.getAffective().updateAffectiveData(frustration, engagement, meditation, stengagement, ltengagement);
	  this.data.setAffective(data.getAffective());
  }
  
  public void setNeutralData() {
	  AffectiveData affectiveData=this.data.getAffective();
	  double frustration = ThreadLocalRandom.current().nextDouble(0, 0.3);
	  Gui.affectivePanel.getAffectiveController().setFrustration(frustration);
	  double engagement = ThreadLocalRandom.current().nextDouble(0, 0.4);
	  Gui.affectivePanel.getAffectiveController().setEngagement(engagement);
	  double meditation = ThreadLocalRandom.current().nextDouble(0.5, 1);
	  Gui.affectivePanel.getAffectiveController().setMeditation(meditation);
	  double stengagement = ThreadLocalRandom.current().nextDouble(0.3, 0.7);
	  Gui.affectivePanel.getAffectiveController().setEngagement(stengagement);
	  double ltengagement = ThreadLocalRandom.current().nextDouble(0.5, 0.8 );
	  Gui.affectivePanel.getAffectiveController().setltExcitement(ltengagement);
	  data.getAffective().updateAffectiveData(frustration, engagement, meditation, stengagement, ltengagement);
	  this.data.setAffective(data.getAffective());
  }
  
  public void setSadData() {
	  AffectiveData affectiveData=this.data.getAffective();
	  double frustration = ThreadLocalRandom.current().nextDouble(0.5, 1);
	  Gui.affectivePanel.getAffectiveController().setFrustration(frustration);
	  double engagement = ThreadLocalRandom.current().nextDouble(0, 0.4);
	  Gui.affectivePanel.getAffectiveController().setEngagement(engagement);
	  double meditation = ThreadLocalRandom.current().nextDouble(0, 0.4);
	  Gui.affectivePanel.getAffectiveController().setMeditation(meditation);
	  double stengagement = ThreadLocalRandom.current().nextDouble(0, 0.5);
	  Gui.affectivePanel.getAffectiveController().setStExcitement(stengagement);
	  double ltengagement = ThreadLocalRandom.current().nextDouble(0, 0.5);
	  Gui.affectivePanel.getAffectiveController().setltExcitement(ltengagement);
	  data.getAffective().updateAffectiveData(frustration, engagement, meditation, stengagement, ltengagement);
	  this.data.setAffective(data.getAffective());
  }
  
  public void setRandomData() {
	  AffectiveData affectiveData=this.data.getAffective();
	  double frustration = ThreadLocalRandom.current().nextDouble(0, 1);
	  Gui.affectivePanel.getAffectiveController().setFrustration(frustration);
	  double engagement = ThreadLocalRandom.current().nextDouble(0, 1);
	  Gui.affectivePanel.getAffectiveController().setEngagement(engagement);
	  double meditation = ThreadLocalRandom.current().nextDouble(0, 1);
	  Gui.affectivePanel.getAffectiveController().setMeditation(meditation);
	  double stengagement = ThreadLocalRandom.current().nextDouble(0, 1);
	  Gui.affectivePanel.getAffectiveController().setStExcitement(stengagement);
	  double ltengagement = ThreadLocalRandom.current().nextDouble(0, 1);
	  Gui.affectivePanel.getAffectiveController().setltExcitement(ltengagement);
	  data.getAffective().updateAffectiveData(frustration, engagement, meditation, stengagement, ltengagement);
	  this.data.setAffective(data.getAffective());
  }

   void createAndNotify() {
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
