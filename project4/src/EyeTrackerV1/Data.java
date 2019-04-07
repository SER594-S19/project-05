package EyeTrackerV1;


/**
 * This class encapsulates a timestamp for a row of data (one entry per channel)
 *
 * @author javiergs
 * @version 20190130
 */
public class Data {

    private String time;
    private double value;
    
    public Data(String k, double value) {
    	this.time = k;
        this.value = value;
    }

    public void setData(double data) {
    	time=null;
        value=data;
    }

public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

@Override
  public String toString() {
	    return "Data{" + "time=" + time + ", value=" + value + '}';
	  }
     
}
