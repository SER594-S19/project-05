package Face.Core;


/**
 * This class encapsulates a timestamp for a row of data (one entry per channel)
 *
 * @author javiergs
 * @version 20190130
 */
public class Data {

    private String time;
    private double agreement;
    private double concentrating;
    private double disagreement;
    private double interested;
    private double thinking;
	private double unsure;
    
    public double getAgreement() {
		return agreement;
	}

	public void setAgreement(double agreement) {
		this.agreement = agreement;
	}

	public double getConcentrating() {
		return concentrating;
	}

	public void setConcentrating(double concentrating) {
		this.concentrating = concentrating;
	}

	public double getDisagreement() {
		return disagreement;
	}

	public void setDisagreement(double disagreement) {
		this.disagreement = disagreement;
	}

	public double getInterested() {
		return interested;
	}

	public void setInterested(double interested) {
		this.interested = interested;
	}

    public double getThinking() {
		return thinking;
	}

	public void setThinking(double thinking) {
		this.thinking = thinking;
	}


    public double getUnsure() {
		return unsure;
	}

	public void setUnsure(double unsure) {
		this.unsure = unsure;
	}
	
	public Data(String time, double val1, double val2 , double val3, double val4, double val5, double val6) {
        this.time = time;
        this.agreement = val1;
        this.concentrating = val2;
        this.disagreement = val3;
        this.interested = val4;
        this.thinking = val5;
        this.unsure = val6;
    }

  @Override
  public String toString() {
    return time + "," + agreement+ "," + concentrating + "," + disagreement + "," + interested + "," + thinking + "," + unsure;
  }
     
}
