package HeartRate;

import Core.Publisher;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Put together a dataGenerator and a publisher.
 * It read from the dataGenerator and publish in a port using the publisher.
 * A GUI control the model and can start, stop or shutdown it.
 * @version 20190206
 */
public class Model {

    private final ExecutorService executorService;
    private final HRDataGenerator dataGenerator;
    private final Publisher threadPublisher;
    private boolean serverState;


    // Constructor for Model class
    public Model(HRDataGenerator device, Publisher publisher) {
        executorService = Executors.newCachedThreadPool();
        dataGenerator = device;
        threadPublisher = publisher;
        dataGenerator.addObserver(threadPublisher);
        serverState = false;
    }

    // Method to set the state of heart rate in HRDataGenerator
    public void setHeartState(int heartState) {
        dataGenerator.setHeartState(heartState);
    }

    // Method to get the Server state
    public boolean getServerState() {
        return serverState;
    }

    // Method to set the Server state
    public void setServerState(boolean serverState) {
        this.serverState = serverState;
    }

    // Method to set the frequency
    public void setFrequency(double frequency) {
        dataGenerator.setFrequency(frequency);
    }

    // Method to close the server
    public void shutdown() {
        dataGenerator.stop();
        threadPublisher.stop();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
        }
    }

    // Method to start the server for sending data 
    public void start() {
    		this.serverState=true;
        executorService.submit(dataGenerator);
        executorService.submit(threadPublisher);
    }

    // Method to stop the server from sending data
    public void stop() {
    		this.serverState=false;
        dataGenerator.stop();
        threadPublisher.stop();
    }

}
