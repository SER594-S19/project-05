package Client;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FaceEmotion extends JPanel {
	public void paintComponent(Graphics g) {
		
		JLabel lblNewLabel = new JLabel("New label");
	    Double x;
	    Double y;
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
    	        if(x <= 0.5 && y <= 0.5) {
    	        	lblNewLabel.setIcon(new ImageIcon("C:\\Users\\desai\\Desktop\\SER-594\\project-05\\EyeTrackingB\\image\\happy.jpg"));
    	            add(lblNewLabel);
    	    	} 
    	    	else if (x >= 0.8 && y >= 0.8) {
    	    	
    	    		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\desai\\Desktop\\SER-594\\project-05\\EyeTrackingB\\image\\normal.png"));
    	    	    add(lblNewLabel);
    	    	} else {
    	    	
    	    		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\desai\\Desktop\\SER-594\\project-05\\EyeTrackingB\\image\\sad.jpg"));
    	    	    add(lblNewLabel);
    	    	} 
    	    }
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}