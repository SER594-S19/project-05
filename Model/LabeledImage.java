package Model;

import java.io.Serializable;
import java.util.Vector;

/** This code for Image classification has been taken from 
 * Java Handwritten digit Recognition with Neural networks link
 * provided in SER594 Class slides: Week 7.  
 * @author ramok.tech
 */

//Building an image for each data entry
public class LabeledImage implements Serializable{
	private static final long serialVersionUID = 1L;
	private double label;
	private Vector features;	
	public LabeledImage(int label, double[] pixels){
		this.label = label;
	}	
	public Vector getFeatures() {
		return features;
	}	
	public double getLabel() {
		return label;
	}
	public void setlabel(double label) {
		this.label = label;
	}
	
// Training our model
public void train(Integer trainData, Integer testfieldValue) {
	initSparkSession();
	//List<LabeledImage> labeledImages = IdxReader.loadData(trainData);
	//List<LabeledImage> testLabeledImages = IdxReader.loadTestData(testfieldValue);	
	//Dataset<Row> train = sparksession.createDataFrame(labeledImages, LabeledImage.class.ceckpoint();
	//Dataset<Row> test = sparkSession.createDataFrame(testLabeledImages, LabeledImage.class.checkpoint();	
	int[] layers = new int[]{784, 128, 64, 10};	
	train trainer = new train();
	Object train = null;
	evalOnTest(train);
}
private void evalOnTest(Object train) {
	// TODO Auto-generated method stub	
}
private void initSparkSession() {
	// TODO Auto-generated method stub	
}

//Normalizing data
private double[] normalizeFeatures(double[] pixels) {
	double min = Double.MAX_VALUE;
	double max = Double.MIN_VALUE;
	double sum = 0;
	for (double pixel : pixels) {
		sum = sum + pixel;
		if(pixel > max) {
			max = pixel;
		}
		if(pixel < min) {
			min = pixel;
		}
	}
	double mean = sum/pixels.length;	
	double[] pixelsNorm = new double[pixels.length];
	for(int i=0;i<pixels.length;i++){
		pixelsNorm[i] = (pixels[i] - mean)/(max-min);
	}
	return pixelsNorm;
}
}