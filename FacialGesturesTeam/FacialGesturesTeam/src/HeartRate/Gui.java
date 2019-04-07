package HeartRate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Core.Publisher;
/***
 * This is Main server class for graphical interface that displays different values for heart rate.
 * @version 20190206
 */

public class Gui extends JPanel implements ActionListener {

	private static Gui instance = null;
	private static Model model;

	private final int PORT = 1700;

	private JTextField portText = new JTextField("1700");
	private JTextField freqText = new JTextField("1");

	private final JButton buttonConnect = new JButton("run");
	private final JRadioButton resting = new JRadioButton("Resting", true);
	private final JRadioButton moderate = new JRadioButton("Moderate Exercise");
	private final JRadioButton vigorous = new JRadioButton("Vigorous Exercise");
	private final JPanel gifPanel = new JPanel();
	private final JLabel connectLabel = new JLabel("Click on the heart below to start/stop the server");
	private final JTextPane textPane = new JTextPane();

	// Method to get object of GUI class
	public static Gui getInstance()
	{
		if (instance == null)
			instance = new Gui();

		return instance;
	}

	// Constructor for GUI class that creates the JFrame
	private Gui() {

		model = new Model(new HRDataGenerator(), new Publisher(PORT));
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(createNorthComponent());
		this.add(createButtonGroups());
		this.add(createRunLabel());
		this.add(createConnectPanel());
		initializeHeartGif();
		this.add(createConsole());
		

		Dimension screen = getToolkit().getScreenSize();
		this.setSize(screen.width / 2, 3 * screen.height / 6);
		this.setLocation((screen.width - getSize().width) / 2, (screen.height - getSize().height) / 2);
	}

	// Method to create the topmost element in the GUI
	private Component createNorthComponent() {
		JPanel top = new JPanel(new GridLayout(2, 2));
		top.add(new JLabel("Enter Port Number: "));
		top.add(portText);
		top.add(new JLabel("Enter Frequency: "));
		top.add(freqText);

		portText.setEnabled(false);

		return top;
	}

	// Method to create radio button group
	private Component createButtonGroups() {
		JPanel buttons = new JPanel();
		ButtonGroup hrControlGroup = new ButtonGroup();

		hrControlGroup.add(resting);
		hrControlGroup.add(moderate);
		hrControlGroup.add(vigorous);

		buttons.add(resting);
		buttons.add(moderate);
		buttons.add(vigorous);
		resting.addActionListener(this);
		moderate.addActionListener(this);
		vigorous.addActionListener(this);

		return buttons;
	}
	
	private Component createRunLabel() {
		JPanel panel = new JPanel();
		connectLabel.setForeground(Color.GRAY);
		panel.setBackground(Color.WHITE);
		panel.add(connectLabel);
		
		return panel;
	}
	
	// Method used to create the JPanels for start/stop button
	private Component createConnectPanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.add(buttonConnect);

		buttonConnect.addActionListener(this);
		buttonConnect.add(gifPanel);
		buttonConnect.setBackground(Color.WHITE);
		buttonConnect.setEnabled(true);
		buttonConnect.setBorder(null);

		return panel;
	}

	// Method used to create a Console in the GUI
	private Component createConsole() {
		JPanel panel = new JPanel(new GridLayout());

		textPane.setFont(new Font("Tahoma", Font.BOLD, 12));
		textPane.setEnabled(false);
		textPane.setEditable(true);
		textPane.setPreferredSize(new Dimension(200, 50));

		JScrollPane scrollPane = new JScrollPane(textPane);
		panel.add(scrollPane);

		return panel;
	}

	// Method used to create the initial static GIF
	private void initializeHeartGif() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource("HeartRate.Resources/heart_static.png"));
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(ii);
		gifPanel.setBackground(Color.WHITE);
		gifPanel.add(imageLabel);

	}

	// Method for Action Listeners for button clicks
	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedAction;

		if (e.getSource() == buttonConnect) {
			if (buttonConnect.getText().compareTo("run") == 0) {
				if (isFrequencyValid()) {
					textPane.setText("SERVER IS RUNNING...");
					selectedAction = checkButtonSelected();
					model.setFrequency(Double.parseDouble(freqText.getText()));
					model.start();
					model.setHeartState(selectedAction);
					freqText.setEnabled(false);
					buttonConnect.setText("stop");
				} else {
					JOptionPane.showMessageDialog(
							new JPanel(), "Invalid Frequency. Please enter valid value", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (buttonConnect.getText().compareTo("stop") == 0) {
				model.stop();
				textPane.setText("");
				buttonConnect.setText("run");
				freqText.setEnabled(true);
				textPane.setBackground(Color.WHITE);
				ImageIcon ii = new ImageIcon(this.getClass().getResource("Resources/heart_static.png"));
				JLabel imageLabel = new JLabel();
				imageLabel.setIcon(ii);
				gifPanel.removeAll();
				gifPanel.add(imageLabel);
				this.getParent().revalidate();
				this.getParent().repaint();
			}

			if(model.getServerState()) {
				if (checkButtonSelected()==0)
					updateHeartGif("HeartRate.Resources/heart_slow.gif");
				else if (checkButtonSelected()==1)
					updateHeartGif("HeartRate.Resources/heart_moderate.gif");
				else if (checkButtonSelected()==2)
					updateHeartGif("HeartRate.Resources/heart_fast.gif");
				model.setHeartState(checkButtonSelected());
			}
		}
		
		if(model.getServerState()) {
			if (e.getSource() == resting ) {
				updateHeartGif("HeartRate.Resources/heart_slow.gif");
				model.setHeartState(0);
			} else if (e.getSource() == moderate) {
				updateHeartGif("HeartRate.Resources/heart_moderate.gif");
				model.setHeartState(1);
			} else if (e.getSource() == vigorous) {
				updateHeartGif("HeartRate.Resources/heart_fast.gif");
				model.setHeartState(2);
			}
		}
	}

	// Method to update GIF based on heart state
	private void updateHeartGif(String image) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(image));
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(ii);
		gifPanel.removeAll();
		gifPanel.add(imageLabel);
		this.getParent().revalidate();
		this.getParent().repaint();
	}

	// Method used to check state of radio buttons selected
	private int checkButtonSelected(){
		if(resting.isSelected())
			return 0;
		else if(moderate.isSelected())
			return 1;
		else
			return 2;
	}

	// Method that is used to validate the frequency entered
	private boolean isFrequencyValid() {
		try {
			return Double.parseDouble(freqText.getText()) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Method used to set the textPane data 
	public void setTextPane(String data) {
		textPane.setText("SERVER IS RUNNING..." + "\n" + data);
	}

	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Heart Rate Simulator");
		frame.getContentPane().setLayout(new GridLayout(1, 1));
		frame.setLayout(new GridLayout(1, 1));
		frame.getContentPane().add(Gui.getInstance());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				model.shutdown();
				System.exit(0);
			}
		});
		frame.pack();
		frame.setPreferredSize(new Dimension(367, 510));
		frame.setMinimumSize(new Dimension(367, 510));
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
