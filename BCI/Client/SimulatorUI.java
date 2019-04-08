package BCI.Client;

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

import com.sun.javafx.css.SubCssMetaData;

public class SimulatorUI extends JPanel implements ActionListener, Observer {

	private NewSubscriber subscriber;
	private final ExecutorService service;
	private JTextPane dataOutput = new JTextPane();
	private JTextField ipAddress = new JTextField();
	private JTextField port = new JTextField();
	private JButton connectButton = new JButton();
	static private final String IP = "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))";
	static private Pattern IP_PATTERN = Pattern.compile(IP);

    
	SimulatorUI(NewSubscriber subscriber) {
		this.subscriber = subscriber;
		this.setBackground(Color.decode("#4267b2"));
		service = Executors.newCachedThreadPool();
		setLayout(new FlowLayout());
		add(createPanel());
		add(createButtons());
		
	}

	public static boolean valiateIpAddress(final String s) {
		if (s.toLowerCase().equals("localhost"))
			return true;
		else {
			return IP_PATTERN.matcher(s).matches();
		}
	}

	public ExecutorService getService() {
		return service;
	}

	public NewSubscriber getSubscriber() {
		return subscriber;
	}

	private Component createButtons() {
		JPanel buttons = new JPanel();
		buttons.setBackground(Color.WHITE);

		this.ipAddress.setText("localhost");
		this.port.setText("1594");
		this.connectButton.setText("Connect");

		buttons.add(ipAddress);
		buttons.add(port);
		buttons.add(connectButton);
		buttons.setLayout(new GridLayout(3, 1));
		buttons.setOpaque(false);

		connectButton.setPreferredSize(new Dimension(100, 20));
		connectButton.addActionListener(this);

		return buttons;
	}

	private Component createPanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setPreferredSize(new Dimension(400, 150));
//		panel.setBackground(Color.decode("#7d7d7d"));
		panel.setOpaque(false);

		this.dataOutput.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		this.dataOutput.setBackground(Color.WHITE);
		this.dataOutput.setForeground(Color.BLACK);

		
		this.dataOutput.setEnabled(false);
		this.dataOutput.setEditable(true);

		JScrollPane scrollPane = new JScrollPane(this.dataOutput);
		panel.add(scrollPane);
		return panel;

	}

	private boolean validatePort() {
		try {
			return Integer.parseInt(port.getText()) > 1024 && Integer.parseInt(port.getText()) < 65536;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void close() {
		subscriber.stop();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (connectButton.getText().equals("Connect")) {

			if (!valiateIpAddress(ipAddress.getText())) {
				JOptionPane.showMessageDialog(new JPanel(), "Invalid IP. Please enter valid value to continue.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!validatePort()) {
				JOptionPane.showMessageDialog(new JPanel(), "Invalid Port. Please enter valid value to continue.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			dataOutput.setText("");
			connectButton.setText("Stop");
			subscriber.setIp(ipAddress.getText());
			subscriber.setPort(Integer.parseInt(port.getText()));
			service.submit(subscriber);
			subscriber.addObserver(this);
		} else {
			close();
			connectButton.setText("Connect");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		String data = ((NewSubscriber) o).getObject().toString();
		if (data.toLowerCase().equals("stopped")|| data.toLowerCase().equals("FIN")) {
			this.getParent().revalidate();
			this.getParent().repaint();
			close();
			connectButton.setText("Connect");
			ipAddress.setEnabled(true);
			port.setEnabled(true);
			if (data.toLowerCase().equals("stopped"))
				JOptionPane.showMessageDialog(new JPanel(),
						"Server has stopped.Please start server to receive values", "Error", JOptionPane.ERROR_MESSAGE);
			dataOutput.setText("CSV file is created in the source directory");
		} else if (data.toLowerCase().equals("fail")) {
			JOptionPane.showMessageDialog(new JPanel(), "Failed to connect to the server,"
					+ " please check if the server is running and try again", "Error", JOptionPane.ERROR_MESSAGE);
			connectButton.setText("Connect");
			ipAddress.setEnabled(true);
			port.setEnabled(true);
		} else {
			dataOutput.setText(dataOutput.getText() + "\n" + data);
			dataOutput.setForeground(Color.BLACK);
			this.getParent().revalidate();
			this.getParent().repaint();
			ipAddress.setEnabled(false);
			port.setEnabled(false);
		}
	}
}

