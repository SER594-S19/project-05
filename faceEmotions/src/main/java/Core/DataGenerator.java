package Core;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

class DataGenerator extends Observable implements Runnable {

  private Data data;
  private Gui val;
  private boolean stop = false;
  //private int input_face=0;


  protected static HashMap<String, Double> faceParameters = new HashMap<String, Double>() {
    {
      put("agreement", (double) 0.0);
      put("disagreement", (double) 0);
      put("frustrate", (double) 0);
      put("concentrate", (double) 0);
      put("thinking", (double) 0);
      put("unsure", (double) 0);

    }
  };


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
    long initialTime = calendar.getTimeInMillis();
    double timeStamp = 0;


    while (!stop) {
      System.out.println("data generator running");
      float agreeInput = (float) (faceParameters.get("agreement") / 10);
      float disagreeInput = (float) (faceParameters.get("disagreement") / 10);
      float frusInput = (float) (faceParameters.get("frustrate") / 10);
      float concenInput = (float) (faceParameters.get("concentrate") / 10);
      float thinkInput = (float) (faceParameters.get("thinking") / 10);
      float unsureInput = (float) (faceParameters.get("unsure") / 10);
      timeStamp = (System.currentTimeMillis() - initialTime) * .001;
      float agree = (float) ((agreeInput - 0.1) + new Random().nextFloat() * (agreeInput - (agreeInput - 0.1)));
      float disagree = (float) ((disagreeInput - 0.1) + new Random().nextFloat() * (disagreeInput - (disagreeInput - 0.1)));
      float frustrate = (float) ((frusInput - 0.1) + new Random().nextFloat() * (frusInput - (frusInput - 0.1)));
      float concentrate = (float) ((concenInput - 0.1) + new Random().nextFloat() * (concenInput - (concenInput - 0.1)));
      float thinking = (float) ((thinkInput - 0.1) + new Random().nextFloat() * (thinkInput - (thinkInput - 0.1)));
      float unsure = (float) ((unsureInput - 0.1) + new Random().nextFloat() * (unsureInput - (unsureInput - 0.1)));

      if (agree < 0)
        agree = (float) 0.0000000;
      if (disagree < 0)
        disagree = (float) 0.0000000;
      if (frustrate < 0)
        frustrate = (float) 0.0000000;
      if (concentrate < 0)
        concentrate = (float) 0.0000000;
      if (thinking < 0)
        thinking = (float) 0.0000000;
      if (unsure < 0)
        unsure = (float) 0.0000000;

      createAndNotify(timeStamp, agree, disagree, frustrate, concentrate, thinking, unsure);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
      }

    }
  }

  public void run_data_generator(int value) {
    //input_face= value;
    run();
  }


  public void updateParam(String s, Double val) {

    faceParameters.put(s, Math.floor(val));
    System.out.println(faceParameters);

  }

  private void createAndNotify(double timestampsystem, float agreement, float disagreement, float frustrate, float concentrating, float thinking, float unsure) {
    System.out.println("notifying ...");

    data = new Data(timestampsystem, agreement, concentrating, disagreement, frustrate, thinking, unsure);
    data.setData(timestampsystem, agreement, concentrating, disagreement, frustrate, thinking, unsure);

    System.out.println(data.toString());
    setChanged();
    notifyObservers();
  }
}