package MultiModalClient;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

public class PredictData {

    private MultiLayerNetwork model;

    public void loadModel() throws IOException {
        File location = new File("model.zip");
        model = ModelSerializer.restoreMultiLayerNetwork(location);
    }

    public int getResult(MultiLayerNetwork model, double a, double b, double c, double d){

        INDArray test = Nd4j.create(new double[] {a,b,c,d});

        int[] output = model.predict(test);

        return output[0];
    }
}
