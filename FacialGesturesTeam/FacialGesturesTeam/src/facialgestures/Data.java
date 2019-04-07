package facialgestures;

import java.util.ArrayList;

/**
 * This class encapsulates a timestamp for a row of data (one entry per channel)
 *
 * @author javiergs
 * @version 20190130
 */
public class Data {

    private double time;
    


    public Data(double time) {
        this.time = time;
    }


    public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	
    
  @Override
  public String toString() {
    return "Data{" + "time=" + time + "}";
  }
     
}
