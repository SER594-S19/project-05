package MultiModalClient;

import java.awt.*;
import java.util.ArrayList;

public class GraphModel {
  private int XLength;
  private int noOfChannels;
  private Color channelColors[];
  private ArrayList<ArrayList<CoordinatesModel>> graphData;
  private String[] legendNames;
  private int xStartPoint;

  GraphModel() {
    XLength = 1;
    noOfChannels = 1;
    xStartPoint = 0;
    graphData = new ArrayList<>();
  }

  /**
   * Sets the X-axis display length.
   *
   * @param XLength length of X-axis
   */
  public void setXLength(int XLength) {
    this.XLength = XLength;
  }

  /**
   * Gets the X-axis display length.
   *
   * @return length of X-axis
   */
  public int getXLength() {
    return XLength;
  }

  /**
   * Sets the no of channels in the graph.
   *
   * @param noOfChannels no of channels in the graph
   */
  public void setNoOfChannels(int noOfChannels) {
    this.noOfChannels = noOfChannels;
  }

  /**
   * Gets the no of channels in the graph.
   *
   * @return no of channels in the graph.
   */
  public int getNoOfChannels() {
    return noOfChannels;
  }

  /**
   * Sets the list of colors for each channel in the graph.
   *
   * @param channelColors list of colors for each channel in the graph
   */
  public void setChannelColors(Color channelColors[]) {
    this.channelColors = channelColors;
  }

  /**
   * Gets the list of colors for each channel in the graph.
   *
   * @return list of colors for each channel in the graph
   */
  public Color[] getChannelColors() {
    return channelColors;
  }

  /**
   * Sets the coordinate values for each channel in the graph.
   *
   * @param graphData list of coordinate values for each channel in the graph
   */
  public void setGraphData(ArrayList<ArrayList<CoordinatesModel>> graphData) {
    this.graphData = graphData;
  }

  /**
   * Gets the coordinate values for each channel in the graph.
   *
   * @return list of coordinate values for each channel in the graph
   */
  public ArrayList<ArrayList<CoordinatesModel>> getGraphData() {
    return graphData;
  }

  /**
   * Sets the names of each legend in the graph.
   *
   * @param legendNames the names of each legend in the graph
   */
  public void setLegendNames(String[] legendNames) {
    this.legendNames = legendNames;
  }

  /**
   * Gets the names of each legend in the graph.
   *
   * @return names of each legend in the graph.
   */
  public String[] getLegendNames() {
    return legendNames;
  }

  /**
   * Sets the x-axis starting point of the graph.
   *
   * @param xStartPoint the x-axis starting point of the graph
   */
  public void setXStartPoint(int xStartPoint) {
    this.xStartPoint = xStartPoint;
  }

  /**
   * Gets the x-axis starting point of the graph.
   *
   * @return the x-axis starting point of the graph
   */
  public int getXStartPoint() {
    return xStartPoint;
  }
}