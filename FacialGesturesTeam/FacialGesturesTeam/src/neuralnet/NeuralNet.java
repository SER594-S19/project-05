package neuralnet;

import java.util.function.Function;

import static neuralnet.MatrixUtil.apply;
import static neuralnet.NNMath.*;

public class NeuralNet {

    private final NeuronLayer layer1, layer2;
    private double[][] outputLayer1;
    private double[][] outputLayer2;
    private final double learningRate;

    public NeuralNet(NeuronLayer layer1, NeuronLayer layer2) {
        this(layer1, layer2, 0.1);
    }

    public NeuralNet(NeuronLayer layer1, NeuronLayer layer2, double learningRate) {
        this.layer1 = layer1;
        this.layer2 = layer2;
        this.learningRate = learningRate;
    }

    /**
     * Forward propagation
     * <p>
     * Output of neuron = 1 / (1 + e^(-(sum(weight, input)))
     *
     * @param inputs
     */
    public void think(double[][] inputs) {
        outputLayer1 = apply(matrixMultiply(inputs, layer1.weights), layer1.activationFunction); // 4x4
        outputLayer2 = apply(matrixMultiply(outputLayer1, layer2.weights), layer2.activationFunction); // 4x1
    }

    public void train(double[][] inputs, double[][] outputs, int numberOfTrainingIterations) {
        for (int i = 0; i < numberOfTrainingIterations; ++i) {
            // pass the training set through the network
            think(inputs); // 4x3

            // adjust weights by error * input * output * (1 - output)

            // calculate the error for layer 2
            // (the difference between the desired output and predicted output for each of the training inputs)
            double[][] errorLayer2 = matrixSubtract(outputs, outputLayer2); // 4x1
            double[][] deltaLayer2 = scalarMultiply(errorLayer2, apply(outputLayer2, layer2.activationFunctionDerivative)); // 4x1

            // calculate the error for layer 1
            // (by looking at the weights in layer 1, we can determine by how much layer 1 contributed to the error in layer 2)

            double[][] errorLayer1 = matrixMultiply(deltaLayer2, matrixTranspose(layer2.weights)); // 4x4
            double[][] deltaLayer1 = scalarMultiply(errorLayer1, apply(outputLayer1, layer1.activationFunctionDerivative)); // 4x4

            // Calculate how much to adjust the weights by
            // Since we’re dealing with matrices, we handle the division by multiplying the delta output sum with the inputs' transpose!

            double[][] adjustmentLayer1 = matrixMultiply(matrixTranspose(inputs), deltaLayer1); // 4x4
            double[][] adjustmentLayer2 = matrixMultiply(matrixTranspose(outputLayer1), deltaLayer2); // 4x1

            adjustmentLayer1 = MatrixUtil.apply(adjustmentLayer1, (x) -> learningRate * x);
            adjustmentLayer2 = MatrixUtil.apply(adjustmentLayer2, (x) -> learningRate * x);

            // adjust the weights
            this.layer1.adjustWeights(adjustmentLayer1);
            this.layer2.adjustWeights(adjustmentLayer2);

            // if you only had one layer
            // synaptic_weights += dot(training_set_inputs.T, (training_set_outputs - output) * output * (1 - output))
            // double[][] errorLayer1 = NNMath.matrixSubtract(outputs, outputLayer1);
            // double[][] deltaLayer1 = NNMath.matrixMultiply(errorLayer1, MatrixUtil.apply(outputLayer1, NNMath::sigmoidDerivative));
            // double[][] adjustmentLayer1 = NNMath.matrixMultiply(NNMath.matrixTranspose(inputs), deltaLayer1);

            if(i % 10000 == 0){
                System.out.println(" Training iteration " + i + " of " + numberOfTrainingIterations);
            }
            //System.out.println(this);

        }
    }

    public double[][] getOutput() {
        return outputLayer2;
    }

    @Override
    public String toString() {
        String result = "Layer 1\n";
        result += layer1.toString();
        result += "Layer 2\n";
        result += layer2.toString();

        if (outputLayer1 != null) {
            result += "Layer 1 output\n";
            result += MatrixUtil.matrixToString(outputLayer1);
        }

        if (outputLayer2 != null) {
            result += "Layer 2 output\n";
            result += MatrixUtil.matrixToString(outputLayer2);
        }

        return result;
    }
    public static void main(String args[]) {

        // create hidden layer that has 1 neuron and 3 inputs
        NeuronLayer layer1 = new NeuronLayer(1, 3);

        NeuralNetSimple net = new NeuralNetSimple(layer1);

        // train the net
        double[][] inputs = new double[][]{
                {0, 0, 1},
                {1, 1, 1},
                {1, 0, 1},
                {0, 1, 1}
        };

        double[][] outputs = new double[][]{
                {1},
                {2},
                {3},
                {1}
        };

        System.out.println("Training the neural net...");
        net.train(inputs, outputs, 10000);
        System.out.println("Finished training");

        System.out.println("Layer 1 weights");
        System.out.println(layer1);}
}