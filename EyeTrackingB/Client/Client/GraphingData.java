package Client;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import com.opencsv.CSVReader;
 
public class GraphingData extends JPanel {
		
    private static final String COMMA_DELIMITER = ",";
    final int PAD = 5;
    Double x;
    Double y;
 
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	System.out.println("\n\nSomething...\n\n");
    	try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\desai\\eclipse-workspace\\ScrollbarDemo\\src\\Core\\data.csv"))) {
    	    String line;
    	    br.readLine();
    	    while ((line = br.readLine()) != null) {
    	        String[] values = line.split(",");
    	        //ArrayList<String> records=new ArrayList<String>();
    	        String replacex=values[0].replace("\"", "");
    	        String replacey=values[1].replace("\"", "");
    	        x=Double.parseDouble(replacex);
    	        y=Double.parseDouble(replacey);
    	        
    	  
    	          Graphics2D g2 = (Graphics2D)g;
    	          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    	                              RenderingHints.VALUE_ANTIALIAS_ON);
    	          int w = getWidth();
    	          int h = getHeight();
    	          // Draw ordinate.
    	          g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
    	          // Draw abcissa.
    	          g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
    	          // Draw labels.
    	          Font font = g2.getFont();
    	          FontRenderContext frc = g2.getFontRenderContext();
    	          LineMetrics lm = font.getLineMetrics("0", frc);
    	          float sh = lm.getAscent() + lm.getDescent();
    	          // Ordinate label.
    	          String s = "data";
    	          float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
    	          for(int i = 0; i < s.length(); i++) {
    	              String letter = String.valueOf(s.charAt(i));
    	              float sw = (float)font.getStringBounds(letter, frc).getWidth();
    	              float sx = (PAD - sw)/2;
    	              g2.drawString(letter, sx, sy);
    	              sy += sh;
    	          }
    	          // Abcissa label.
    	          s = "x axis";
    	          sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
    	          float sw = (float)font.getStringBounds(s, frc).getWidth();
    	          float sx = (w - sw)/2;
    	          g2.drawString(s, sx, sy);
    	          // Draw lines.
    	          double xInc = (double)(w - 2*PAD)/(19);
    	          double scale = (double)(h - 2*PAD)/16;
    	          g2.setPaint(Color.green.darker());
    	              double x1 = PAD;
    	              double y1 = h - PAD;
    	              double x2 = PAD + x*xInc;
    	              double y2 = h - PAD - scale*y;
    	              g2.draw(new Line2D.Double(x1, y1, x2, y2));
    	              System.out.println("print x and y "+x2+","+y2);
    	          // Mark data points.
    	          g2.setPaint(Color.red);
    	        
    	              double xo = PAD + x*xInc;
    	              double yo = h - PAD - scale*y;
    	              g2.fill(new Ellipse2D.Double(xo-2, yo-2, 6, 6));
    	        
    	    }
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    	
    	    	
    }
    
    public double[] getXY() {
    	double[] data = new double[2];
    	data[0] = x;
    	data[1] = y;
    	return data;
    }
    
    public double[] graphValue() {
    	double graphval[] = {x,y};
    	return graphval;
    }
   

	public GraphingData graphcall() {
		
		/*JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new GraphingData());
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
		*/
		return null;
	}
}