package ClientHeartRateTeam;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph extends JPanel {
  private JFreeChart chart;
  private ChartPanel chartPanel;
  private GraphModel graphModel;
  private boolean legendDisplay;
  private double currentXCoordinate;

  private static final int TITLE_FONT_SIZE = 17;
  private static final int GRAPH_AXIS_FONT_SIZE = 14;

  /**
   * Initializes a graph instance and creates a default empty
   * graph.
   */
  Graph(GraphModel graphModel) {
    this.graphModel = graphModel;
    currentXCoordinate = 0;
  }

  /**
   * this method initializes the view and configures the positions of
   * components.
   */
  public void initializeView() {
    setLayout(new GridLayout(1, 1, 8, 8));
    setBackground(Color.decode("#ffffff"));
    XYSeriesCollection dataSet = new XYSeriesCollection();
    chart = createChart(dataSet);
    chartPanel = new ChartPanel(chart);
    add(chartPanel);
    setVisible(true);
  }

  /**
   * Updates the Graph with new model data.
   *
   * @param graphModel a model object containing required graph data.
   * @see GraphModel
   */
  public void updateGraphView(GraphModel graphModel, String saveFileName) {
    this.graphModel = graphModel;
    legendDisplay = false;
    remove(chartPanel);
    XYDataset dataSet = createDataSet();
    chart = createChart(dataSet);
    chartPanel = new ChartPanel(chart);
    add(chartPanel);
    setVisible(true);
  }

  /**
   * this method creates data set to plot graph
   *
   * @return dataset an object of XYDataset
   */
  private XYDataset createDataSet() {
    XYSeries series[] = new XYSeries[graphModel.getNoOfChannels()];
    XYSeriesCollection dataSet = new XYSeriesCollection();

    for (int i = 0; i < graphModel.getNoOfChannels(); i++) {
      if (legendDisplay)
        series[i] = new XYSeries(graphModel.getLegendNames()[i]);
      else
        series[i] = new XYSeries(i);
    }

    if (graphModel.getGraphData() != null) {
      for (ArrayList<CoordinatesModel> data : graphModel.getGraphData()) {
        for (int i = 0; i < graphModel.getNoOfChannels(); i++) {
          double xCoordinate = data.get(i).getXCoordinate();
          double yCoordinate = data.get(i).getYCoordinate();
          series[i].add(xCoordinate, yCoordinate);
          currentXCoordinate = xCoordinate;
        }
      }
    }

    for (int i = 0; i < graphModel.getNoOfChannels(); i++) {
      dataSet.addSeries(series[i]);
    }

    return dataSet;
  }

  /**
   * This method creates chart for graphs.
   *
   * @param dataSet XYDataset
   * @return chart JFreeChart
   */
  private JFreeChart createChart(final XYDataset dataSet) {
    JFreeChart chart = ChartFactory.createScatterPlot("", "Pleasure",
        "Arousal", dataSet, PlotOrientation.VERTICAL, legendDisplay, true,
        false);
    chart.setBackgroundPaint(Color.decode("#ffffff"));

    XYPlot plot = chart.getXYPlot();

    ValueAxis range = plot.getDomainAxis();
    if(graphModel.getXLength() == 0)
      graphModel.setXLength(1);
    range.setRange(0, graphModel.getXLength());
    range.setTickLabelPaint(Color.decode("#0d3d56"));
    range.setTickLabelFont(new Font("Courier New", Font.BOLD, GRAPH_AXIS_FONT_SIZE));

    plot.getRangeAxis().setRange(0, 1);
    plot.getRangeAxis().setTickLabelPaint(Color.decode("#0d3d56"));
    plot.getRangeAxis().setTickLabelFont(new Font("Courier New", Font.BOLD, GRAPH_AXIS_FONT_SIZE));


    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

    for (int i = 0; i < graphModel.getNoOfChannels(); i++) {
      if (graphModel.getChannelColors() != null)
        renderer.setSeriesPaint(i, graphModel.getChannelColors()[i]);
      renderer.setSeriesShapesVisible(i, false);
    }

    //plot.setRenderer(renderer);
    plot.setBackgroundPaint(Color.decode("#4a6b7c"));

    plot.setRangeGridlinesVisible(false);
    plot.setDomainGridlinesVisible(false);

    return chart;
  }
}