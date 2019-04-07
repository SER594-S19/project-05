package BCI.Model;

public class ExpressionData {
	private double blink = 0 ;
	private double winkLeft = 0;
	private	double winkRight = 0;
	private double lookLeft = 0;
	private double lookRight = 0;
	private double smirkLeft = 0;
	private double smirkRight = 0;
	private double clench = 0;
	private double smile = 0;
	private double laugh = 0;
	private double raiseBrow = 0;
	private double furrow = 0;
	
	public double getBlink() {
		return blink;
	}

	public void setBlink(double blink) {
		this.blink = blink;
	}

	public double getWinkLeft() {
		return winkLeft;
	}

	public void setWinkLeft(double winkLeft) {
		this.winkLeft = winkLeft;
	}

	public double getWinkRight() {
		return winkRight;
	}

	public void setWinkRight(double winkRight) {
		this.winkRight = winkRight;
	}

	public double getLookLeft() {
		return lookLeft;
	}

	public void setLookLeft(double lookLeft) {
		this.lookLeft = lookLeft;
	}

	public double getLookRight() {
		return lookRight;
	}

	public void setLookRight(double lookRight) {
		this.lookRight = lookRight;
	}

	public double getSmirkLeft() {
		return smirkLeft;
	}

	public void setSmirkLeft(double smirkLeft) {
		this.smirkLeft = smirkLeft;
	}

	public double getSmirkRight() {
		return smirkRight;
	}

	public void setSmirkRight(double smirkRight) {
		this.smirkRight = smirkRight;
	}

	public double getClench() {
		return clench;
	}

	public void setClench(double clench) {
		this.clench = clench;
	}

	public double getSmile() {
		return smile;
	}

	public void setSmile(double smile) {
		this.smile = smile;
	}

	public double getLaugh() {
		return laugh;
	}

	public void setLaugh(double laugh) {
		this.laugh = laugh;
	}

	public double getRaiseBrow() {
		return raiseBrow;
	}

	public void setRaiseBrow(double raiseBrow) {
		this.raiseBrow = raiseBrow;
	}

	public double getFurrow() {
		return furrow;
	}

	public void setFurrow(double furrow) {
		this.furrow = furrow;
	}

	
	public ExpressionData() {
		
	}

	public void updateExpressionData(double blink, double winkLeft, double winkRight, double lookLeft,
			double lookRight, double smirkLeft, double smirkRight, double clench, double smile, double laugh,
			double raiseBrow, double furrow) {
		// TODO Auto-generated method stub
		
		this.blink = blink;
		this.winkLeft = winkLeft;
		this.winkRight = winkRight;
		this.lookLeft = lookLeft;
		this.lookRight = lookRight;
		this.smirkLeft = smirkLeft;
		this.smirkRight = smirkRight;
		this.clench = clench;
		this.smile = smile;
		this.laugh = laugh;
		this.raiseBrow = raiseBrow;
		this.furrow = furrow;
	}
	
	
}
