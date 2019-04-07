package FacialGesturesClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;


public class HeartClient extends JPanel implements Observer,ActionListener {
	private JTextArea textArea = new JTextArea();
	private JButton connect = new JButton("Connect");
	private JTextField heartIp = new JTextField();
	private Border border = BorderFactory.createLineBorder(Color.BLACK);
	private JTextField heartPort = new JTextField();
	private Subscriber subscriber;
	private ExecutorService service;
	private boolean firstCheck = true;
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private static final String PORT_PATTERN = "[0-9]+";
	
	public HeartClient(Subscriber s) {
		// TODO Auto-generated constructor stub
		service = Executors.newCachedThreadPool();
		this.subscriber = s;
	}

	public void IPAddressValidator() {
		pattern = Pattern.compile(IPADDRESS_PATTERN);
	}

	/**
	 * Validate ip address with regular expression
	 * 
	 * @param ip
	 *            ip address for validation
	 * @return true valid ip address, false invalid ip address
	 */
	public boolean validate(String ip) {
		IPAddressValidator();
		matcher = pattern.matcher(ip);
		if(ip.equals("localhost") || ip.equals("LOCALHOST"))
			  return true;
		return matcher.matches();
	}

	public boolean validatePort(String port) {
		Pattern patternPort = Pattern.compile(PORT_PATTERN);
		matcher = patternPort.matcher(port);
		return matcher.matches();
	}
	
	public ExecutorService getService() {
		return service;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public JPanel processPanelHeart(String lableName) {

		JPanel jLabel = new JPanel();
		jLabel.setBackground(new Color(0, 94, 181));
		jLabel.setLayout(new GridLayout(1, 1));
		jLabel.add(new JLabel(lableName), BorderLayout.WEST);

		JPanel jPanel = new JPanel();
		jPanel.setBackground(Color.white);
		jPanel.setLayout(new GridLayout(1, 2));
		jPanel.add(new JLabel("IP Address"));
		jPanel.add(heartIp);

		JPanel jPort = new JPanel();
		jPort.setBackground(Color.white);
		jPort.setLayout(new GridLayout(1, 2));
		jPort.add(new JLabel("Port #"));
		jPort.add(heartPort);

		JPanel connectCondition = new JPanel();
		connectCondition.setBackground(Color.white);
		connectCondition.setLayout(new GridLayout(1, 3));
		connect.addActionListener(this);

		connectCondition.add(connect);

		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(new GridLayout(4, 1));
		panel.add(jLabel, BorderLayout.NORTH);
		panel.add(jPanel, BorderLayout.AFTER_LAST_LINE);
		panel.add(jPort, BorderLayout.AFTER_LAST_LINE);
		panel.add(connectCondition, BorderLayout.AFTER_LAST_LINE);
		panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		JPanel outerPanel = new JPanel();
		outerPanel.setBackground(Color.white);
		outerPanel.setLayout(new GridLayout(2, 1));
		outerPanel.add(panel);
		textArea.setBorder(border);
		textArea.setEditable(false);
		textArea.setFont(new Font("Sans-Serif", Font.BOLD, 12));

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		outerPanel.add(scroll, BorderLayout.CENTER);
		return outerPanel;
	}

	
	
	public void update(Observable o, Object arg) {
	    String data = ((Subscriber) o).getObject().toString();
	    int index = data.lastIndexOf("=");
	    MainClient.collectData(Double.parseDouble(data.substring(index+1)), 5);
	    if(data.compareTo("STOPPED")==0) {
	    	textArea.append("Connection to the server has been Terminated. Closing the Client Connection." + "\n" );
	    	close();
	    }else if(data.compareTo("FAIL")==0) {
	    	textArea.append("Server Not Up.  Closing the Client Connection." + "\n" );
	    	close();
	    }else if(data.compareTo("FIN")==0) {
	    	textArea.append("Connection to the server has been Terminated. Closing the Client Connection." + "\n" );
	    	close();
	    }else {
	    	if(firstCheck) {
	    		textArea.append("Connection Estabished with the server." + "\n" );
				textArea.append("Starting to receive data..." + "\n" );
				firstCheck = false;
	    	}
	    	
			textArea.append(data + "\n" );
	    }
	    
	  }

	public void close() {
		System.out.println("Closing...");
		subscriber.stop();
		connect.setText("Connect");
	}

	public static void setWarningMsg(String text) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane optionPane = new JOptionPane(text, JOptionPane.WARNING_MESSAGE);
		JDialog dialog = optionPane.createDialog("Warning!");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (connect.getText().equals("Connect")) {
			if (heartIp.getText().equals("") || heartPort.getText().equals("")) {
				setWarningMsg("Please enter a valid IP Address & Port Number.");
			} else if (validate(heartIp.getText()) && heartPort.getText().length() == 4
					&& validatePort(heartPort.getText())) {
				connect.setText("Disconnect");
				subscriber.setIp(heartIp.getText());
				subscriber.setPort(Integer.parseInt(heartPort.getText()));
				subscriber = new Subscriber(heartIp.getText(),Integer.parseInt(heartPort.getText()));
				 service.submit(subscriber);
				 subscriber.addObserver(this);
			} else {
				setWarningMsg("Please enter a valid IP Address & Port Number.");
			}
		} else {
//			close();
			subscriber.stop();
			connect.setText("Connect");
		}
	}
}
