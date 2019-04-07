package neuralnet;

import java.util.function.Function;
import static neuralnet.MatrixUtil.apply;
import static neuralnet.NNMath.*;

public class NeuronLayer {
	
	public enum ActivationFunctionType {
        SIGMOID,
        TANH
    }

	public final Function<Double, Double> activationFunction, activationFunctionDerivative;	

    double[][] weights;

    public NeuronLayer(int numberOfNeurons, int numberOfInputsPerNeuron) {
        weights = new double[numberOfInputsPerNeuron][numberOfNeurons];

        for (int i = 0; i < numberOfInputsPerNeuron; ++i) {
            for (int j = 0; j < numberOfNeurons; ++j) {
                weights[i][j] = (2 * Math.random()) - 1; // shift the range from 0-1 to -1 to 1
            }
        }

        activationFunction = NNMath::sigmoid;
        activationFunctionDerivative = NNMath::sigmoidDerivative;
    }

    public void adjustWeights(double[][] adjustment) {
        this.weights = NNMath.matrixAdd(weights, adjustment);
    }
}