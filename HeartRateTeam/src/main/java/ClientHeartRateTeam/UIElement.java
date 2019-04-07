package ClientHeartRateTeam;

import ClientHeartRateTeam.ClientSubscriber;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * This class is used to create a UI element in the client frame
 *
 * @version 20190213
 *
 */

public class UIElement extends JPanel implements Observer, ActionListener {

	private ClientSubscriber subscriber;
	private final ExecutorService service;
	//private JTextPane dataPane = new JTextPane();
	private JTextField ipAddress = new JTextField();
	private JTextField port = new JTextField();
	private JButton connect = new JButton();
	static private final String IPV4_REGEX = "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))";
	static private Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

	
	


	// Constructor for UI element present in client frame
	UIElement(ClientSubscriber subscriber, String color) {

		service = Executors.newCachedThreadPool();
		this.setBackground(Color.decode(color));
		this.setLayout(new FlowLayout());
		this.add(createMainPanel());
		this.add(createRightButtonGroup());
		this.subscriber = subscriber;

	}

	// Method used to validate the IP
	public static boolean isValidIPV4(final String s) {
		if (s.equals("localhost") || s.equals("LOCALHOST"))
			return true;
		else {
			return IPV4_PATTERN.matcher(s).matches();
		}
	}

	// Method to get the ExecutorService instance
	public ExecutorService getService() {
		return service;
	}

	// MEthod to get the ClientSubscriber instance
	public ClientSubscriber getSubscriber() {
		return subscriber;
	}

	// Method used to create the right button group for each server
	private Component createRightButtonGroup() {
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.WHITE);

		this.ipAddress.setText("localhost");
		this.port.setText("port");
		this.connect.setText("Connect");

		buttons.add(ipAddress);
		buttons.add(port);
		buttons.add(connect);
		buttons.setLayout(new GridLayout(3, 1));
		buttons.setOpaque(false);

		connect.setPreferredSize(new Dimension(100, 20));
		connect.addActionListener(this);

		return buttons;
	}

	// Method used to create main panel in each server element
	private Component createMainPanel() {
		JPanel panel = new JPanel(new GridLayout());
		//panel.setPreferredSize(new Dimension(400, 150));
		panel.setBackground(Color.decode("#7d7d7d"));
		panel.setOpaque(false);

	//	this.dataPane.setFont(new Font("Tahoma", Font.BOLD, 12));
	//	this.dataPane.setForeground(Color.BLUE);
		
		//this.dataPane.setCaretColor(Color.decode("#030303"));
	//	this.dataPane.setBackground(Color.decode("#c0e0f0"));
	//	this.dataPane.setEnabled(false);
	//	this.dataPane.setEditable(true);
		//this.dataPane.

	//	JScrollPane scrollPane = new JScrollPane(this.dataPane);
	//	panel.add(scrollPane);
		return panel;

	}

	// Method used to check if port numbers are in range
	private boolean isPortValid() {
		try {
			return Integer.parseInt(port.getText()) > 1024 && Integer.parseInt(port.getText()) < 65536;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Method called when the application is stopped
	private void close() {
		subscriber.stop();
	}

	// Method used for action listeners
	@Override
	public void actionPerformed(ActionEvent e) {
		if (connect.getText().equals("Connect")) {

			if (!isValidIPV4(ipAddress.getText())) {
				JOptionPane.showMessageDialog(new JPanel(), "Invalid IP. Please enter valid value to continue.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!isPortValid()) {
				JOptionPane.showMessageDialog(new JPanel(), "Invalid Port. Please enter valid value to continue.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			//dataPane.setText("");
			connect.setText("Stop");
			subscriber.setIp(ipAddress.getText());
			subscriber.setPort(Integer.parseInt(port.getText()));
			service.submit(subscriber);
			subscriber.addObserver(this);
			// JOptionPane.showMessageDialog(new JPanel(), "Connected to "+
			// ipAddress.getText() + " on PORT: "+ port.getText(), "Information",
			// JOptionPane.INFORMATION_MESSAGE);

		} else {
			close();
			connect.setText("Connect");
			// JOptionPane.showMessageDialog(new JPanel(), "Not connected to "+
			// ipAddress.getText() + " on PORT: "+ port.getText(), "Error",
			// JOptionPane.ERROR_MESSAGE);
		}
	}

	// Method used to update the observers for changes in data in server
	@Override
	public void update(Observable o, Object arg) {
		String data = ((ClientSubscriber) o).getObject().toString();
		if (data.compareTo("STOPPED") == 0 || data.compareTo("FIN") == 0) {
			if(data.compareTo("FIN") == 0) {
				CombinedDataStatefull combined = CombinedDataStatefull.getInstance();
				combined.addToGlobalQueue(data);
			}
			this.getParent().revalidate();
			this.getParent().repaint();
			close();
			connect.setText("Connect");
			if (data.compareTo("STOPPED") == 0)
				JOptionPane.showMessageDialog(new JPanel(), "Server Stopped", "Error", JOptionPane.ERROR_MESSAGE);
			
			final String dir = System.getProperty("user.dir");
			//dataPane.setText("CSV file generated in "+dir+" directory.");
		} else if (data.compareTo("FAIL") == 0) {
			JOptionPane.showMessageDialog(new JPanel(), "Connection Fail: Server not running", "Error", JOptionPane.ERROR_MESSAGE);
			connect.setText("Connect");
		} else {
			System.out.println(CombinedDataStatefull.getInstance());
			CombinedDataStatefull combined = CombinedDataStatefull.getInstance();
			combined.addToGlobalQueue(data);
		//	dataPane.setText(dataPane.getText() + "\n" + data);
		//	dataPane.setForeground(Color.BLACK);
			this.getParent().revalidate();
			this.getParent().repaint();
		}
	}
}
