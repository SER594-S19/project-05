package facialgestures;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Core.Publisher;
import facialgestures.FacialDataGenerator;

/**
 * Put together a dataGenerator and a publisher. 
 * It read from the dataGenerator and publish in a port using the publisher.
 * A GUI control the model and can start, stop or shutdown it.
 * 
 */
public class Model {

  private final ExecutorService executorService;
  private final FacialDataGenerator dataGenerator;
  private final Publisher threadPublisher;
  private ArrayList<Double> facialValues = new ArrayList<>();

  public Model(FacialDataGenerator device, Publisher publisher) {
    executorService = Executors.newCachedThreadPool();
    dataGenerator = device;
    threadPublisher = publisher;
    dataGenerator.addObserver(threadPublisher);
  }

  public ArrayList<Double> getFacialValues() {
	return facialValues;
}

public void setFacialValues(ArrayList<Double> facialValues) {
	this.facialValues = facialValues;
	this.dataGenerator.setFacialValues(this.facialValues);
}

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

  public void start() {
        System.out.println("model start");
    executorService.submit(dataGenerator);
    executorService.submit(threadPublisher);
  }

  public void stop() {
            System.out.println("model stop");

    dataGenerator.stop();
    threadPublisher.stop();
  }
  
}
