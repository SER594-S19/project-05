package Core;

import java.util.Calendar;
import java.util.*;


class DataGenerator extends Observable implements Runnable {

  private Data data;
  private boolean stop = false;
  //double timeStamp = 0;



  public void stop() {
    this.stop = true;
  }

  protected static HashMap<String, Double> skinP = new HashMap<String, Double>()
  {
    {
      put("voltage", (double) 0);
      put("conductance", (double) 0);

    }
  };

  public void updatePara(String desc,double value){
    skinP.put(desc, value);
  }

  public Object getObject() {
    return data;
  }

  @Override
  public void run() {
    stop = false;

    Calendar calendar = Calendar.getInstance();
    long initialTime = calendar.getTimeInMillis();
    double timeStamp = 0;



    while (!stop) {
      System.out.println("data generator running");
      double volInput = (skinP.get("voltage")/1);
      double conInput = (skinP.get("conductance")/1);
      timeStamp = (System.currentTimeMillis() - initialTime) * .001;

      double volMax=volInput+0.5;
      double volMin=volInput-0.5;
      if(volMin<0) volMin=0;
      if(volMax>3) volMax=3;
      double volRan= Math.random()*(volMax-volMin)+volMin;

      double conMax=conInput+0.5;
      double conMin=conInput-0.5;
      if(conMin<0) conMin=0;
      if(conMax>3) conMax=3;
      double conRan= Math.random()*(conMax-conMin)+conMin;


      //double voltage =  ((volInput - 0.1) + Math.random()*(volInput - (volInput - 0.1)));

      //double conductance =  ((conInput - 0.1) + Math.random()*(conInput - (conInput - 0.1)));

      if(volRan<0)
        volRan=0.0;

      if(conRan<0)
        conRan=0.0;


      createAndNotify(timeStamp, volRan, conRan);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
      }
    }
  }

//  public void updateVoltage(double value){
//    //value= Math.random()*20;
//    System.out.println("Updating Voltage! "+ value);
//    createAndNotify(timeStamp, value, data.getConductance());
//
//  }
//
//  public void updateConductance(double value){
//    createAndNotify(timeStamp, data.getVoltage(), value);
//
//  }

  private void createAndNotify(double timestampsystem, double voltage, double conductance) {
    System.out.println("notifying ...");

    data = new Data(timestampsystem, voltage, conductance);
    setChanged();
    notifyObservers();
  }


}
