package Client;

public class EyeData {
	private double time;
    private int gx;
    private int gy;
    private long pupilx;
    private long pupily;
    private int validation;
    private long fixation;
    private long aoi;

    public EyeData(double time, int gx,int gy,long pupilx,long pupily, int validation,long fixation,long aoi){
        this.time=time;
        this.gx=gx;
        this.gy=gy;
        this.pupilx=pupilx;
        this.pupily=pupily;
        this.validation=validation;
        this.fixation=fixation;
        this.aoi=aoi;
    }

  @Override
  public String toString() {
    return "Data{" + "time=" + time + ", gx=" + gx + ", gy="+gy +", pupilx="+pupilx +", pupily="+pupily +", fixation="+fixation +", validation="+validation +", aoi="+aoi +"}";
  }

}
