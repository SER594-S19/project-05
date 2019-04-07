package neuralnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class NN {
  
    public static void main(String[] args) {
  
    	ArrayList<ArrayList<Double>> inputrecords = new ArrayList<>();
    	ArrayList<ArrayList<Double>> outputrecords = new ArrayList<>();
    	try (BufferedReader br = new BufferedReader(new FileReader("/Users/manasmishra/HCIModel/hci/src/neuralnet/TrainingData60K.csv"))) {
    		String line;
            int i=0;
            while ((line = br.readLine()) != null) {
            	String[] values = line.split(",");
            	ArrayList<Double> inner = new ArrayList<>();
            	ArrayList<Double> outer = new ArrayList<>();
            	inner.add(Double.valueOf(values[6]));
            	inner.add(Double.valueOf(values[7]));
            	inputrecords.add(inner);
            	outer.add(Double.valueOf(values[8]));
            	outputrecords.add(outer);
            }
            br.close();
    	}catch(IOException ex) {
    		System.out.println(ex);
    	}
    	System.out.println("FILE READ DONE");
        double[][] X = new double[inputrecords.size()][2];
//        = (double[][]) inputrecords.toArray();
        double[][] Y = new double[outputrecords.size()][1];
        for (int i = 0; i < inputrecords.size(); i++) {
            X[i][0] = inputrecords.get(i).get(0).doubleValue();
            X[i][1] = inputrecords.get(i).get(1).doubleValue();
            Y[i][0] = outputrecords.get(i).get(0).doubleValue();
          }
//        		(double[][]) outputrecords.toArray();
        System.out.println("ARRAY READ DONE");
        System.out.println(X.length);
        System.out.println(X[0].length);
        System.out.println(Y.length);
        int m = inputrecords.size();
        int nodes = 400;

        X = NP.T(X);
        Y = NP.T(Y);

        double[][] W1 = NP.random(nodes, 2);
        double[][] b1 = new double[nodes][m];

        double[][] W2 = NP.random(1, nodes);
        double[][] b2 = new double[1][m];

        for (int i = 0; i < 40; i++) {
        	
            // Foward Prop
            // LAYER 1
        	System.out.println("dot row " + NP.dot(W1, X).length);
        	System.out.println("dot col " + NP.dot(W1, X)[0].length);
        	System.out.println("b1 row " + b1.length);
        	System.out.println("b1 row " + b1[0].length);
            double[][] Z1 = NP.add(NP.dot(W1, X), b1);
            double[][] A1 = NP.sigmoid(Z1);

            //LAYER 2
            double[][] Z2 = NP.add(NP.dot(W2, A1), b2);
            double[][] A2 = NP.sigmoid(Z2);

            double cost = NP.cross_entropy(m, Y, A2);
            //costs.getData().add(new XYChart.Data(i, cost));
         
            // Back Prop
            //LAYER 2
            double[][] dZ2 = NP.subtract(A2, Y);
            double[][] dW2 = NP.divide(NP.dot(dZ2, NP.T(A1)), m);
            double[][] db2 = NP.divide(dZ2, m);

            //LAYER 1
            double[][] dZ1 = NP.multiply(NP.dot(NP.T(W2), dZ2), NP.subtract(1.0, NP.power(A1, 2)));
            double[][] dW1 = NP.divide(NP.dot(dZ1, NP.T(X)), m);
            double[][] db1 = NP.divide(dZ1, m);

            // G.D
            W1 = NP.subtract(W1, NP.multiply(0.01, dW1));
            b1 = NP.subtract(b1, NP.multiply(0.01, db1));

            W2 = NP.subtract(W2, NP.multiply(0.01, dW2));
            b2 = NP.subtract(b2, NP.multiply(0.01, db2));
//            if(i==39) {
//            	System.out.println("Save W2 = " + Arrays.deepToString(W2));
//                System.out.println("Save W1 = " + Arrays.deepToString(W1));
//                System.out.println("Save b2 = " + Arrays.deepToString(b2));
//                System.out.println("Save b1 = " + Arrays.deepToString(b1));
//            }
            System.out.println("i=" + i);
            if (i % 4 == 0) {
                System.out.println("==============");
                System.out.print("Cost = " + cost);
                System.out.println("Predictions = " + Arrays.deepToString(A2));
            }
        }
//        System.out.println("Save W2 = " + Arrays.deepToString(W2));
//        System.out.println("Save W1 = " + Arrays.deepToString(W1));
//        System.out.println("Save b2 = " + Arrays.deepToString(b2));
//        System.out.println("Save b1 = " + Arrays.deepToString(b1));
        try {
			BufferedWriter br = new BufferedWriter(new FileWriter("/Users/manasmishra/HCIModel/hci/src/neuralnet/weights.csv"));
			br.write(Arrays.deepToString(W2));
			br.write(Arrays.deepToString(W1));
			br.write(Arrays.deepToString(b2));
			br.write(Arrays.deepToString(b1));
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}