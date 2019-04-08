package BCI.Client;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BCI.Core.Gui;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlotPanel extends JPanel {

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public String happyImagePath="/BCI/Core/Smiley.jpg";
	public String neutralImagePath="/BCI/Core/Neutral.jpg";
	public String sadImagePath="/BCI/Core/Sad.jpg";
	private static PlotPanel plotPanel=null;
	BufferedImage myPicture = ImageIO.read(Gui.class.getResource(happyImagePath));
    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
	private PlotPanel() throws IOException {
		setLayout(new GridLayout(2, 1));
		 VectorProject2 v=new VectorProject2("vector");
	     add(v);
	     JPanel smileyPanel = new JPanel();
	     add(smileyPanel);
	     smileyPanel.add(picLabel);
	     
	     
	}
	
	public static PlotPanel getIntance() 
	{
		if(plotPanel==null) {
			try {
				plotPanel=new PlotPanel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		return plotPanel;
		
	}
	public void setHappyFace() {
		BufferedImage myPicture;
		picLabel.setIcon(new ImageIcon(Gui.class.getResource(happyImagePath)));
	   
	}
	public void setNeutralFace() {
		
		picLabel.setIcon(new ImageIcon(Gui.class.getResource(neutralImagePath)));
		
	}
	public void setSadFace() {
		picLabel.setIcon(new ImageIcon(Gui.class.getResource(sadImagePath)));
	}
	public void repaintPanel() {
		this.revalidate();
	}

}
