package Core;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class encapsulates a timestamp for a row of data (one entry per channel)
 *
 * @author javiergs
 * @version 20190130
 */
public class Data {

    private double time;
    private int gx;
    private int gy;
    private long pupilx;
    private long pupily;
    private int validation;
    private long fixation;
    private long aoi;

    public Data(double time, int gx,int gy,long pupilx,long pupily, int validation,long fixation,long aoi){
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
        double P, A;
        P = (pupilx + pupily + fixation);
        A = (pupilx + pupily - validation);
    String filePath = "C:\\Users\\desai\\eclipse-workspace\\ScrollbarDemo\\src\\Core\\data.csv";
    String[] lines = {P + "", A + ""};
    writeDataLineByLine(filePath,lines);
    return "P = " + P + "A = " + A;

  }

    public static void writeDataLineByLine(String filePath, String[] lines)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        boolean insertHeader = false;
        try {
            if(!file.exists()){
                insertHeader = true;
            }
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file,true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            if(insertHeader) {
                // adding header to csv
                String[] header = {"Pleasure", "Arousal"};
                writer.writeNext(header);
            }
            writer.writeNext(lines);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     
}
