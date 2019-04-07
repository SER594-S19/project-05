package MultiModalClient;

import java.awt.*;
import java.util.*;

import MultiModalClient.ClientUI;

public class GraphObserver implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		
		CombinedDataStatefull dataObj = (CombinedDataStatefull) o;
		Queue<String> dataQueue = dataObj.getGlobalQueue();
		String data = dataQueue.poll();
		String values[] = data.split(",");
		values[1] = "" + (Double.parseDouble(values[1]) - 60) / 130;

		int result = MultiModalClient.ClientUI.getInstance().predictData.getResult(
				MultiModalClient.ClientUI.getInstance().model,
				Double.parseDouble(values[2]),
				Double.parseDouble(values[3]),
				Double.parseDouble(values[4]),
				Double.parseDouble(values[1]));

		if(result == 0)
			MultiModalClient.ClientUI.getInstance().updateSmileImage("smile.png");
		else if(result == 2)
			MultiModalClient.ClientUI.getInstance().updateSmileImage("sad.png");
		else
			MultiModalClient.ClientUI.getInstance().updateSmileImage("neutral.png");

		MultiModalClient.ClientUI.getInstance().graphModel.setNoOfChannels(1);
		Color channelColors[] = new Color[] { Color.RED };
		MultiModalClient.ClientUI.getInstance().graphModel.setChannelColors(channelColors);

		ArrayList<ArrayList<CoordinatesModel>> graphData = MultiModalClient.ClientUI.getInstance().graphModel.getGraphData();

		ArrayList<CoordinatesModel> coordinatesList = new ArrayList<>();
		coordinatesList.add(new CoordinatesModel(Double.parseDouble(values[1]), Double.parseDouble(values[2])));
		graphData.clear();
		graphData.add(coordinatesList);

		MultiModalClient.ClientUI.getInstance().graphModel.setXLength(1);
		MultiModalClient.ClientUI.getInstance().graphModel.setGraphData(graphData);
		MultiModalClient.ClientUI.getInstance().graph.updateGraphView(ClientUI.getInstance().graphModel, "chart.png");
	}

}
