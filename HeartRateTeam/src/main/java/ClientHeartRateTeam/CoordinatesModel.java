package ClientHeartRateTeam;

public class CoordinatesModel {

  private double xCoordinate;
  private double yCoordinate;

  /**
   * Initializes a coordinate with x and y values.
   *
   * @param xCoordinate the x coordinate of the graph
   * @param yCoordinate the y coordinate of the graph
   */
  public CoordinatesModel(double xCoordinate, double yCoordinate) {
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
  }

  /**
   * Gets the x coordinate of the graph
   *
   * @return the x coordinate of the graph
   */
  public double getXCoordinate() {
    return xCoordinate;
  }

  /**
   * Sets the x coordinate of the graph
   *
   * @param xCoordinate the x coordinate of the graph
   */
  public void setXCoordinate(double xCoordinate) {
    this.xCoordinate = xCoordinate;
  }

  /**
   * Gets the y coordinate of the graph
   *
   * @return the y coordinate of the graph
   */
  public double getYCoordinate() {
    return yCoordinate;
  }

  /**
   * Sets the y coordinate of the graph
   *
   * @param yCoordinate the y coordinate of the graph
   */
  public void setYCoordinate(double yCoordinate) {
    this.yCoordinate = yCoordinate;
  }
}