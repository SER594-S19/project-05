package FacialGesturesClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;
public class VisualizePlot extends JPanel{
	private static VisualizePlot instance = null;
	private static VisualizePlot getInstance() {
        if (instance == null)
            instance = new VisualizePlot();

        return instance;
    }
		static BufferedImage image = new BufferedImage(1280, 768,
	            BufferedImage.TYPE_INT_RGB);
		public static void plot() throws Exception {
			VisualizePlot plot = getInstance();
		     
		    JPanel canvas = new JPanel() {
		        @Override
		        protected void paintComponent(Graphics g) {
		            super.paintComponent(g);
		            g.drawImage(image, 64, 38, this);
		        }
		    };

		    JFrame frame = new JFrame();
		    frame.setLayout(new BorderLayout());   // <== make panel fill frame
		    frame.add(canvas, BorderLayout.CENTER);
		    frame.setSize(500, 500);
		    frame.setVisible(true);

		    // do you drawing somewhere else, maybe a different thread
		    Graphics g = image.getGraphics();
		    g.setColor(Color.red);
		    g.drawRect(5, 7, 2, 2);
		    g.drawRect(5, 8, 1, 1);
		    g.drawRect(5, 6, 1, 1);
		    g.drawRect(5, 9, 1, 1);
		    g.drawRect(5, 10, 1, 1);
		    g.drawRect(5, 11, 1, 1);
		    g.dispose();
		    canvas.repaint();
		}

}
