package Core;


/**
 * This class encapsulates a timestamp for a row of data (one entry per channel)
 *
 * @author javiergs
 * @version 20190130
 */
public class Data {

    private double time;
    private double voltage;
    private double conductance;

    public Data(double time, double volvalue, double convalue) {
        this.time = time;
        this.voltage = volvalue;
        this.conductance = convalue;
    }

    public void setData(double volvalue, double convalue) {
        time=0;
        voltage=volvalue;
        conductance=convalue;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getVoltage() {
        return voltage;
    }
    public double getConductance() {
        return conductance;
    }

    public void setVoltage(double value) {
        this.voltage = value;
    }
    public void setConductance(double value) {
        this.conductance = value;
    }

    @Override
    public String toString() {
        return "Data{" + "time=" + time + ", voltage=" + voltage + ", conductance=" + conductance + '}';
    }
     
}
