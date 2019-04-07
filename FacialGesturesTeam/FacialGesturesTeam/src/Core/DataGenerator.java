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
    double timeStamp = 0;

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

  private void createAndNotify(double timestampsystem, double s) {
                  System.out.println("notifying ...");

    data = new Data(timestampsystem, s);
    setChanged();
    notifyObservers();
  }

}
