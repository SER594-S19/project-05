package HeartRate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import Core.DataGenerator;

/***
 * This class generates heart rate data depending on the state of human activity.
 * This data generated is sent to the client.
 * @version 20190206
 */

public class HRDataGenerator extends DataGenerator implements Runnable  {

    private HeartData data;
    private boolean stop = false;
    private int heartState;
    private double frequency;

    // Constructor for HRDataGenerator
    public HRDataGenerator() {
        heartState = 0;
    }

    // Method to set the state of the heart rate to be selected
    public void setHeartState(int heartState) {
        this.heartState = heartState;
    }

    // Method to set the frequency of the heart rate to be passed to client
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    // Method to stop passing values to client
    public void stop() {
        this.stop = true;
    }

    // Method to get HeartData object
    public Object getObject() {
        return data;
    }

    // Method used to run the thread for heart rate
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
            timeStamp = (System.currentTimeMillis() - initialTime) * .001;

            createAndNotify(timeStamp, generateValues(heartState));
            try {
                Thread.sleep((long)(frequency * 1000));
            } catch (InterruptedException ex) {
            }
        }
    }

    // Method used to generate values for heart rate based on state
    private List<Integer> generateValues(int heartState) {
        int startRange, endRange;
        switch(heartState) {
            case 0: startRange = 60;
                endRange = 100;
                break;
            case 1: startRange = 100;
                endRange = 140;
                break;
            case 2: startRange = 140;
                endRange = 190;
                break;
            default: startRange = 60;
                endRange = 100;
        }

        int randomNum = ThreadLocalRandom.current().nextInt(startRange, endRange + 1);
        List<Integer> values = new ArrayList<>();
        values.add(randomNum);

        return values;
    }

    // Method to notify client about heart rate changed values
    private void createAndNotify(double timeStamp, List<Integer> values) {
        data = new HeartData(timeStamp, values);
        Gui.getInstance().setTextPane(data.toString());
        setChanged();
        notifyObservers();
    }

}