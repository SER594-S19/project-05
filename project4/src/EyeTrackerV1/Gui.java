package EyeTrackerV1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Gui extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private static Model model;
	protected HashMap<String, Integer> eyeParameters;
	private int radius = 50;
	private Graphics g;
	private int r1 = 17;
	private int r2 = 17;

	private final int PORT = 1595;
	protected JLabel labelPublishPort;
	private final String IP = "localhost";
	protected JLabel labelPublishedIp;
	private final JButton buttonConnect = new JButton("run");
	private JPanel sliderPanel_1;
	private static Dimension screen;

	public Gui() {
		screen = getToolkit().getScreenSize();
		model = new Model(new DataGenerator(), new Publisher(PORT));
		Color color = UIManager.getColor("JSlider.background");
		JPanel mainPanel = new JPanel(new GridLayout(10, 1));
		this.setLayout(new BorderLayout());
		mainPanel.add(createRunButtonNorth(), BorderLayout.NORTH);
		mainPanel.setBackground(Color.LIGHT_GRAY);

		
		  sliderPanel_1 = new JPanel() { public void paintComponent(Graphics g) {
		  super.paintComponent(g); int r = 50;
		  
		  Dimension screenSize = sliderPanel_1.getPreferredSize(); int centerX =
		  screenSize.width/2; int centerY = screenSize.height/2; int lefteyecenterX =
		  centerX-100; int lefteyecenterY = centerY-100; int righteyecenterX =
		  centerX+100; int righteyecenterY = centerY-100; g.setColor(Color.BLACK);
		  g.drawOval(lefteyecenterX-r, lefteyecenterY-r, 2*r,2*r-(int)(0.5*r));
		  g.fillOval(lefteyecenterX-r1, lefteyecenterY-r1, 2*r1,2*r1-(int)(0.5*r1));
		  g.drawOval(righteyecenterX-r, righteyecenterY-r, 2*r, 2*r-(int)(0.5*r));
		  g.fillOval(righteyecenterX-r2, righteyecenterY-r2, 2*r2, 2*r2-(int)(0.5*r2));
		  
		  } };
		 

		mainPanel.add(createSliderPanel("pupilRight", 2, 8, 5));
		mainPanel.add(createSliderPanel("pupilLeft", 2, 8, 5));
		mainPanel.add(createSliderPanel("validityL", 0, 4, 2));
		mainPanel.add(createSliderPanel("validityR", 0, 4, 2));

		JPanel eyePanel1 = new JPanel(new GridLayout(1, 1));
		eyePanel1.setBackground(new Color(234, 241, 249));
		eyePanel1.add(new JavaEyes());

		mainPanel.add(eyePanel1);
		// JPanel eyePanel2 = new JPanel(new GridLayout(1,1));
		// eyePanel2.setBackground(Color.WHITE );
		// mainPanel.add(eyePanel2);

		mainPanel.add(createSliderPanel("fixationValue", 0, 50, 25), BorderLayout.NORTH);
		mainPanel.add(createSliderPanel("event", 1, 4, 2), BorderLayout.AFTER_LAST_LINE);
		mainPanel.add(createSliderPanel("aoi", 1, 4, 2), BorderLayout.AFTER_LAST_LINE);

		mainPanel.add(createPanelSouth(), BorderLayout.SOUTH);

		this.add(mainPanel);
		Dimension screen = getToolkit().getScreenSize();
		this.setSize(screen.width / 2, 3 * screen.height / 4);
		this.setLocation((screen.width - getSize().width) / 2, (screen.height - getSize().height) / 2);

		System.out.println("gui done");
	}

	public int getR1() {
		return r1;
	}

	public void setR1(int r1) {
		this.r1 = r1;
		sliderPanel_1.repaint();
	}

	public int getR2() {
		return r2;
	}

	public void setR2(int r2) {
		this.r2 = r2;
		// call paint
		sliderPanel_1.repaint();
	}

	public HashMap<String, Integer> getEyeParameters() {
		return eyeParameters;
	}

	public void setEyeParameters(HashMap<String, Integer> eyeParameters) {
		this.eyeParameters = eyeParameters;
	}

	private Component createPanelSouth() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.setBackground(Color.LIGHT_GRAY);

		JPanel labelPanel = new JPanel(new BorderLayout());
		labelPanel.setBackground(new Color(219, 219, 219));
		JLabel label = new JLabel("Click to start the simulator...");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		labelPanel.add(label, BorderLayout.CENTER);
		labelPanel.setBackground(new Color(219, 219, 219));
		panel.add(labelPanel);
		panel.setBackground(new Color(219, 219, 219));
		panel.add(buttonConnect, BorderLayout.CENTER);
		buttonConnect.addActionListener(this);
		buttonConnect.setEnabled(true);
		panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		return panel;
	}

	private Component createRunButtonNorth() {
		JPanel labelPanel = new JPanel(new GridLayout(2, 1));
		// labelPanel.setBackground(new Color(234,241,249));
		JPanel port = new JPanel();
		port.add(new JLabel("  Publishing at port: "));
		labelPublishPort = new JLabel("" + PORT);
		port.setBackground(Color.LIGHT_GRAY);
		port.add(labelPublishPort);
		JPanel ip = new JPanel();
		ip.add(new JLabel("  Publishing at IP: "));
		labelPublishedIp = new JLabel("" + IP);
		ip.add(labelPublishedIp);
		ip.setBackground(Color.LIGHT_GRAY);
		labelPanel.add(port, BorderLayout.CENTER);
		labelPanel.add(ip, BorderLayout.CENTER);
		labelPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		return labelPanel;
	}

	private Component createSliderPanel(String s, double min, double max, double mid) {
		JPanel panel = new JPanel(new GridLayout(1, 3));

		JSlider slider = new JSlider(JSlider.HORIZONTAL, (int) min * 100, (int) max * 100, (int) mid * 100);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		if (s == "fixationValue") {
			slider.setMajorTickSpacing(1000);
			slider.setMinorTickSpacing(500);
		} else {
			slider.setMajorTickSpacing(100);
			slider.setMinorTickSpacing(50);
		}

		slider.setSnapToTicks(true);
		Enumeration e = slider.getLabelTable().keys();
		while (e.hasMoreElements()) {
			Integer i = (Integer) e.nextElement();
			JLabel labelname = (JLabel) slider.getLabelTable().get(i);
			String g = labelname.getText();
			int ii = Integer.parseInt(g) / 100;
			if (ii == 0) {
				String di = String.valueOf(ii);
				labelname.setText(String.valueOf(Integer.parseInt(labelname.getText()) / 100));
			} else {
				String di = String.valueOf(ii);
				labelname.setHorizontalTextPosition(JLabel.CENTER);
				labelname.setText(String.valueOf(Integer.parseInt(labelname.getText()) / 100) + "  ");
			}

		}
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent event) {

				JSlider source = (JSlider) event.getSource();

				if (s == "pupilLeft") {

					setR1(getPupilRadius(source.getValue() / 100));

				}
				if (s == "pupilRight") {

					setR2(getPupilRadius(source.getValue() / 100));
				}
				model.getDataGenerator().updateParam(s, source.getValue() / 100.0);
			}
		};
		slider.addChangeListener(listener);

		JPanel sliderPanel = new JPanel();
		sliderPanel.setBackground(new Color(234, 241, 249));
		JPanel labelPanel = new JPanel(new BorderLayout());
		labelPanel.setBackground(new Color(234, 241, 249));
		JPanel detailPanel = new JPanel();
		detailPanel.setBackground(new Color(234, 241, 249));
		
		String updatedString;
		//JLabel label = new JLabel(s);
		if (s == "pupilLeft")
			updatedString = "Left pupil";
		else if ( s == "pupilRight")
			updatedString = "Right Pupil";		
		else if ( s == "validityL")
			updatedString = "Left Validity";	
		else if ( s == "validityR")
			updatedString = "Right Validity";	
		else if ( s == "fixationValue")
			updatedString = "Fixation Value";	
		else if ( s == "event")
			updatedString = "Event type";	
		else if ( s == "aoi")
			updatedString = "Area of Interest";
		else	
			updatedString = "NA";
		JLabel label = new JLabel(updatedString);
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		labelPanel.add(label, BorderLayout.CENTER);
		panel.add(labelPanel);

		sliderPanel.add(slider, BorderLayout.CENTER);
		panel.add(sliderPanel);

		JLabel labelquestion = new JLabel("?");
		labelquestion.setHorizontalAlignment(JLabel.CENTER);
		labelquestion.setVerticalAlignment(JLabel.CENTER);
		labelquestion.setForeground(Color.BLACK);
		
		
		if (s == "pupilLeft")
			labelquestion.setToolTipText("Pupil size (left eye) in mm.");
		else if ( s == "pupilRight")
			labelquestion.setToolTipText("Pupil size (right eye) in mm.");		
		else if ( s == "validityL")
			labelquestion.setToolTipText("Validity of the gaze data.");	
		else if ( s == "validityR")
			labelquestion.setToolTipText("Validity of the gaze data.");	
		else if ( s == "fixationValue")
			labelquestion.setToolTipText("Fixation duration. The time in milliseconds that a fixation lasts.");	
		else if ( s == "event")
			labelquestion.setToolTipText("Events, automatic and logged, will show up under Event.");	
		else if ( s == "aoi")
			labelquestion.setToolTipText("Areas Of Interests if fixations on multiple AOIs are to be written on\n" + 
					"the same row.");	
		else
            labelquestion.setToolTipText("Info other");
		
		
		
		detailPanel.add(labelquestion, BorderLayout.CENTER);
		panel.add(detailPanel, BorderLayout.CENTER);

		return panel;

	}

	public int getPupilRadius(int sliderValue) {
		double percent = ((sliderValue) - 2) / 6.0;
		return (int) ((percent * 15) + 10);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("listener trigger");
		if (e.getSource() == buttonConnect) {
			if (buttonConnect.getText().compareTo("run") == 0) {
				System.out.println("start");
				model.start();
				buttonConnect.setText("stop");
			} else if (buttonConnect.getText().compareTo("stop") == 0) {
				System.out.println("stop");
				model.stop();
				buttonConnect.setText("run");
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Eye Tracker Simulator");
		frame.getContentPane().setLayout(new GridLayout(1, 1));
		frame.getContentPane().add(new Gui());

		frame.setLocationRelativeTo(null);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				model.shutdown();
				System.exit(0);
			}
		});
		frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (model.getDataGenerator() != null) {
					model.getDataGenerator().updateParam("gpxValue", (double) e.getX());
					model.getDataGenerator().updateParam("gpyValue", (double) e.getY());
				}
			}
		});
		frame.pack();
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setSize(600, 800);
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}

class JavaEyes extends JPanel {
	// Constants to control look of eyes
	static final int EYE_RADIUS = 35; 
	static final int EYE_SPACE = 20; 
	static final int PUPIL_RADIUS = 10; 
	static final Color PUPIL_COLOR = Color.BLACK; 

	// Constants to control physics of interaction
	static final int ATTRACTION = 1;
	static final int REPULSION = 2;

	// Variables representing current state of eyes
	int physics = ATTRACTION; 
	int eyeRadius = EYE_RADIUS;
	int eyeSpace = EYE_SPACE; 
	int pupilRadius = PUPIL_RADIUS; 
	Color pupilColor = PUPIL_COLOR; 
	int leftEyeX, leftEyeY; 
	int rightEyeX, rightEyeY; 
	int leftPupilX, leftPupilY; 
	int rightPupilX, rightPupilY; 
	int pointerX, pointerY; 

	public JavaEyes() {
		setBackground(new Color(234, 241, 249));
		addMouseMotionListener(new EyeListener(this)); 
		addComponentListener(new EyeComponentListener(this));
	}

	public void setPhysics(int newPhysics) {
		if ((newPhysics == ATTRACTION) || (newPhysics == REPULSION)) {
			physics = newPhysics;
			computeEyePositions();
			repaint();
		}
	}

	public void setEyeRadius(int newEyeRadius) {
		if (newEyeRadius > 0) {
			eyeRadius = newEyeRadius;
			computeEyePositions();
			repaint();
		}
	}

	public void setEyeSpace(int newEyeSpace) {
		if (newEyeSpace > 0) {
			eyeSpace = newEyeSpace;
			computeEyePositions();
			repaint();
		}
	}

	public void setPupilRadius(int newPupilRadius) {
		if (newPupilRadius > 0) {
			pupilRadius = newPupilRadius;
			computeEyePositions();
			repaint();
		}
	}

	public void setPupilColor(Color newPupilColor) {
		pupilColor = newPupilColor;
		repaint();
	}

	/**
	 * This should be called whenever the pointer is moved. It causes the pupil
	 * position to be recomputed, and triggers repainting of the eyes.
	 * 
	 * @param pointerX The X position of the mouse pointer
	 * @param pointerY The Y position of the mouse pointer
	 */
	public void setPointerPosition(int newPointerX, int newPointerY) {
		pointerX = newPointerX;
		pointerY = newPointerY;
		computeEyePositions();
		repaint();
	}

	/**
	 * Paints the eyes (and pupils) on the screen using values that have been
	 * precomputed.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.black);
		g.drawArc(leftEyeX, leftEyeY, eyeRadius * 2, eyeRadius * 2, 0, 360);
		g.drawArc(rightEyeX, rightEyeY, eyeRadius * 2, eyeRadius * 2, 0, 360);

		g.setColor(pupilColor);
		g.fillArc(leftPupilX, leftPupilY, pupilRadius * 2, pupilRadius * 2, 0, 360);
		g.fillArc(rightPupilX, rightPupilY, pupilRadius * 2, pupilRadius * 2, 0, 360);
	}

	/**
	 * This is the math part that computes the position of the eyes and the pupils
	 * based on the dimensions of the window, and the position of the pointer.
	 */
	void computeEyePositions() {
		int leftEyeCtrX = (getWidth() / 2) - eyeRadius - (eyeSpace / 2);
		int rightEyeCtrX = (getWidth() / 2) + eyeRadius + (eyeSpace / 2);
		int eyeCtrY = getHeight() / 2;

		leftEyeX = leftEyeCtrX - eyeRadius;
		leftEyeY = eyeCtrY - eyeRadius;
		rightEyeX = rightEyeCtrX - eyeRadius;
		rightEyeY = eyeCtrY - eyeRadius;

		int pupilDist = (int) (eyeRadius - (pupilRadius * 1.5));
		if (physics == REPULSION) {
			pupilDist *= -1;
		}
		int pointerDist = (int) Math.sqrt(
				(pointerX - leftEyeCtrX) * (pointerX - leftEyeCtrX) + (pointerY - eyeCtrY) * (pointerY - eyeCtrY));
		if (pointerDist > 5) {
			leftPupilX = (int) (leftEyeCtrX + pupilDist * (pointerX - leftEyeCtrX) / pointerDist) - pupilRadius;
			leftPupilY = (int) (eyeCtrY + pupilDist * (pointerY - eyeCtrY) / pointerDist) - pupilRadius;
		}

		pointerDist = (int) Math.sqrt(
				(pointerX - rightEyeCtrX) * (pointerX - rightEyeCtrX) + (pointerY - eyeCtrY) * (pointerY - eyeCtrY));
		if (pointerDist > 5) {
			rightPupilX = (int) (rightEyeCtrX + pupilDist * (pointerX - rightEyeCtrX) / pointerDist) - pupilRadius;
			rightPupilY = (int) (eyeCtrY + pupilDist * (pointerY - eyeCtrY) / pointerDist) - pupilRadius;
		}
	}

}

/**
 * This is a utility class that reports mouse motion back to the eyes.
 */
class EyeListener extends MouseMotionAdapter {
	JavaEyes eyes; // Store a reference back to the actual eyes

	public EyeListener(JavaEyes e) {
		eyes = e;
	}

	/**
	 * Gets notified whenever the mouse moves, and reports changes back to the eyes.
	 */
	public void mouseMoved(MouseEvent e) {
		eyes.setPointerPosition(e.getX(), e.getY());
	}
}

class EyeComponentListener extends ComponentAdapter {
	JavaEyes eyes; // Store a reference back to the actual eyes

	public EyeComponentListener(JavaEyes e) {
		eyes = e;
	}

	/**
	 * Gets notified whenever the mouse moves, and reports changes back to the eyes.
	 */
	public void componentResized(ComponentEvent e) {
		eyes.computeEyePositions();
	}
}
