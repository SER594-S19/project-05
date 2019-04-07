package Client;

public class FaceData {


    private Double time;
    private Double agreement;
    private Double concentrating;
    private Double disagreement;
    private Double interested;
    private Double thinking;
	private Double unsure;
    
    public Double getAgreement() {
		return agreement;
	}

	public void setAgreement(Double agreement) {
		this.agreement = agreement;
	}

	public Double getConcentrating() {
		return concentrating;
	}

	public void setConcentrating(Double concentrating) {
		this.concentrating = concentrating;
	}

	public Double getDisagreement() {
		return disagreement;
	}

	public void setDisagreement(Double disagreement) {
		this.disagreement = disagreement;
	}

	public Double getInterested() {
		return interested;
	}

	public void setInterested(Double interested) {
		this.interested = interested;
	}

    public Double getThinking() {
		return thinking;
	}

	public void setThinking(Double thinking) {
		this.thinking = thinking;
	}


    public Double getUnsure() {
		return unsure;
	}

	public void setUnsure(Double unsure) {
		this.unsure = unsure;
	}
	
	public FaceData(Double time, Double val1, Double val2 , Double val3, Double val4, Double val5, Double val6) {
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
    return "Data{" + "time="+time+ ", agreement="+agreement+", concentrating="+concentrating+", disagreement="+disagreement+", interested="+interested+", thinking="+thinking+", unsure="+unsure+'}';
  }
     


}
