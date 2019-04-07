package Core;

import java.util.Calendar;
import java.util.Observable;

public class DataGenerator extends Observable implements Runnable {

  private Data data;
  private boolean stop = false;

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
    Double timeStamp;

    while (!stop) {
              System.out.println("data generator running");
      timeStamp = (System.currentTimeMillis() - initialTime) * .001;
      createAndNotify(timeStamp, Math.random());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
      }
    }
  }

  private void createAndNotify(Double timestampsystem, double s) {
    System.out.println("notifying ...");
    //0:Aggresion; 1:Concentration; 2:Disagreement; 3:Interested; 4:Thinking 5:Unsure
    data = new Data(timestampsystem,Gui.faceEmotions.get(0),Gui.faceEmotions.get(1),Gui.faceEmotions.get(2),Gui.faceEmotions.get(3),Gui.faceEmotions.get(4),Gui.faceEmotions.get(5));           
    //data = new Data(timestampsystem, s);
    setChanged();
    notifyObservers();
  }

}
