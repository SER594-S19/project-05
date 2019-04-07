package Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

public class IdxReader {

    private final static Logger LOGGER = LoggerFactory.getLogger(IdxReader.class);


    public static final String INPUT_IMAGE_PATH = "/Users/sanaydevi/Desktop/project-05-FaceEmotionsAI 3/faceEmotions/src/main/resources/train-images-idx3-ubyte";
    public static final String INPUT_LABEL_PATH = "/Users/sanaydevi/Desktop/project-05-FaceEmotionsAI 3/faceEmotions/src/main/resources/train-labels-idx1-ubyte";

    public static final String INPUT_IMAGE_PATH_TEST_DATA = "/Users/sanaydevi/Desktop/project-05-FaceEmotionsAI 3/faceEmotions/src/main/resources/test-images-idx3-ubyte";
    public static final String INPUT_LABEL_PATH_TEST_DATA = "/Users/sanaydevi/Desktop/project-05-FaceEmotionsAI 3/faceEmotions/src/main/resources/test-labels-idx1-ubyte";

    public static final int VECTOR_DIMENSION = 2500; //square 28*28 as from data set -> array 784 items

    /**
     * @param size
     * @return
     */
    public static List<LabeledImage> loadData(final int size) {
        return getLabeledImages(INPUT_IMAGE_PATH, INPUT_LABEL_PATH, size);
    }

    /**
     * @param size
     * @return
     */
    public static List<LabeledImage> loadTestData(final int size) {
        return getLabeledImages(INPUT_IMAGE_PATH_TEST_DATA, INPUT_LABEL_PATH_TEST_DATA, size);
    }

    private static List<LabeledImage> getLabeledImages(final String inputImagePath,
                                                       final String inputLabelPath,
                                                       final int amountOfDataSet) {

        final List<LabeledImage> labeledImageArrayList = new ArrayList<>(amountOfDataSet);

        try (FileInputStream inImage = new FileInputStream(inputImagePath);
             FileInputStream inLabel = new FileInputStream(inputLabelPath)) {

            inImage.skip(16);
            inLabel.skip(8);
            System.out.println(inLabel);
            LOGGER.debug("Available bytes in inputImage stream after read: " + inImage.available());
            LOGGER.debug("Available bytes in inputLabel stream after read: " + inLabel.available());

            //empty array for 2500 pixels - the image from 50*50 pixels in a single row
            double[] imgPixels = new double[VECTOR_DIMENSION];

            LOGGER.info("Creating ADT filed with Labeled Images ...");
            long start = System.currentTimeMillis();
            for (int i = 0; i < amountOfDataSet; i++) {

                if (i % 1000 == 0) {
                    LOGGER.info("Number of images extracted: " + i);
                }
                //it fills the array of pixels
                for (int index = 0; index < VECTOR_DIMENSION; index++) {
                    imgPixels[index] = inImage.read();
                }
                //it creates a label for that
                int label = inLabel.read();
                System.out.println("label = "+label);
                LOGGER.info("Label = "+label);
                LOGGER.info("Image pixel = "+imgPixels);
                labeledImageArrayList.add(new LabeledImage(label, imgPixels));
            }
            LOGGER.info("Time to load LabeledImages in seconds: " + ((System.currentTimeMillis() - start) / 1000d));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry, IDX Reader Cannot Read File", "Error!" , JOptionPane.INFORMATION_MESSAGE);
            LOGGER.error("Smth went wrong: \n" + e);
            throw new RuntimeException(e);
        }

        return labeledImageArrayList;
    }

}