package FacialGesturesClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class ClientDemo extends JFrame implements Observer, ActionListener {

  private final Subscriber  [] subscriber = new Subscriber[5];
  private final ExecutorService service;
  private JTextArea textArea = new JTextArea();
  private JButton buttonConnect = new JButton("connect");
  private JTextField faceIp = new JTextField();
  private JTextField facePort = new JTextField();
  private JTextField skinIp = new JTextField();
  private JTextField skinPort = new JTextField();
  private JTextField heartIp = new JTextField();
  private JTextField heartPort = new JTextField();
  private JTextField bciIp = new JTextField();
  private JTextField bciPort = new JTextField();
  private JTextField eyeIp = new JTextField();
  private JTextField eyePort = new JTextField();
  
  private JPanel processPanelEye(String lableName) {

    JPanel jLabel = new JPanel();
    jLabel.setBackground(new Color(0,94,181));
    jLabel.setLayout(new GridLayout(1,1));
    jLabel.add(new JLabel(lableName),BorderLayout.WEST);

    JPanel jPanel = new JPanel();
    jPanel.setBackground(Color.white);
    jPanel.setLayout(new GridLayout(1,2));
//    JTextField ipAdd = new JTextField();
    jPanel.add(new JLabel("IP Address" ));
    jPanel.add(eyeIp);

    JPanel jPort = new JPanel();
    jPort.setBackground(Color.white);
    jPort.setLayout(new GridLayout(1,2));
//    JTextField portInput = new JTextField();
    jPort.add(new JLabel("Port #" ));
    jPort.add(eyePort);

    JPanel connectCondition = new JPanel();
    connectCondition.setBackground(Color.white);
    connectCondition.setLayout(new GridLayout(1,3));
    JLabel condition = new JLabel("Connecting...");
    JButton connect = new JButton("Connect");
    JButton disconnect = new JButton("Disconnect");
    connect.addActionListener(this);
    
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });

    disconnect.addActionListener(this);
    
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });

    connectCondition.add(condition);
    connectCondition.add(connect);
    connectCondition.add(disconnect);

    JPanel panel = new JPanel();
    panel.setBackground(Color.white);
    panel.setLayout(new GridLayout(4,1));
    panel.add(jLabel,BorderLayout.NORTH);
    panel.add(jPanel, BorderLayout.AFTER_LAST_LINE);
    panel.add(jPort, BorderLayout.AFTER_LAST_LINE);
    panel.add(connectCondition, BorderLayout.AFTER_LAST_LINE);
    panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    return panel;
  }

  private JPanel processPanelHeart(String lableName) {

	    JPanel jLabel = new JPanel();
	    jLabel.setBackground(new Color(0,94,181));
	    jLabel.setLayout(new GridLayout(1,1));
	    jLabel.add(new JLabel(lableName),BorderLayout.WEST);

	    JPanel jPanel = new JPanel();
	    jPanel.setBackground(Color.white);
	    jPanel.setLayout(new GridLayout(1,2));
//	    JTextField ipAdd = new JTextField();
	    jPanel.add(new JLabel("IP Address" ));
	    jPanel.add(heartIp);

	    JPanel jPort = new JPanel();
	    jPort.setBackground(Color.white);
	    jPort.setLayout(new GridLayout(1,2));
//	    JTextField portInput = new JTextField();
	    jPort.add(new JLabel("Port #" ));
	    jPort.add(heartPort);

	    JPanel connectCondition = new JPanel();
	    connectCondition.setBackground(Color.white);
	    connectCondition.setLayout(new GridLayout(1,3));
	    JLabel condition = new JLabel("Connecting...");
	    JButton connect = new JButton("Connect");
	    JButton disconnect = new JButton("Disconnect");
	    connect.addActionListener(this);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });

	    disconnect.addActionListener(this);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });

	    connectCondition.add(condition);
	    connectCondition.add(connect);
	    connectCondition.add(disconnect);

	    JPanel panel = new JPanel();
	    panel.setBackground(Color.white);
	    panel.setLayout(new GridLayout(4,1));
	    panel.add(jLabel,BorderLayout.NORTH);
	    panel.add(jPanel, BorderLayout.AFTER_LAST_LINE);
	    panel.add(jPort, BorderLayout.AFTER_LAST_LINE);
	    panel.add(connectCondition, BorderLayout.AFTER_LAST_LINE);
	    panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	    return panel;
	  }
  private JPanel processPanelBCI(String lableName) {

	    JPanel jLabel = new JPanel();
	    jLabel.setBackground(new Color(0,94,181));
	    jLabel.setLayout(new GridLayout(1,1));
	    jLabel.add(new JLabel(lableName),BorderLayout.WEST);

	    JPanel jPanel = new JPanel();
	    jPanel.setBackground(Color.white);
	    jPanel.setLayout(new GridLayout(1,2));
	    JTextField ipAdd = new JTextField();
	    jPanel.add(new JLabel("IP Address" ));
	    jPanel.add(bciIp);

	    JPanel jPort = new JPanel();
	    jPort.setBackground(Color.white);
	    jPort.setLayout(new GridLayout(1,2));
	    JTextField portInput = new JTextField();
	    jPort.add(new JLabel("Port #" ));
	    jPort.add(bciPort);

	    JPanel connectCondition = new JPanel();
	    connectCondition.setBackground(Color.white);
	    connectCondition.setLayout(new GridLayout(1,3));
	    JLabel condition = new JLabel("Connecting...");
	    JButton connect = new JButton("Connect");
	    JButton disconnect = new JButton("Disconnect");
	    connect.addActionListener(this);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });

	    disconnect.addActionListener(this);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });

	    connectCondition.add(condition);
	    connectCondition.add(connect);
	    connectCondition.add(disconnect);

	    JPanel panel = new JPanel();
	    panel.setBackground(Color.white);
	    panel.setLayout(new GridLayout(4,1));
	    panel.add(jLabel,BorderLayout.NORTH);
	    panel.add(jPanel, BorderLayout.AFTER_LAST_LINE);
	    panel.add(jPort, BorderLayout.AFTER_LAST_LINE);
	    panel.add(connectCondition, BorderLayout.AFTER_LAST_LINE);
	    panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	    return panel;
	  }
  private JPanel processPanelSkin(String lableName) {

	    JPanel jLabel = new JPanel();
	    jLabel.setBackground(new Color(0,94,181));
	    jLabel.setLayout(new GridLayout(1,1));
	    jLabel.add(new JLabel(lableName),BorderLayout.WEST);

	    JPanel jPanel = new JPanel();
	    jPanel.setBackground(Color.white);
	    jPanel.setLayout(new GridLayout(1,2));
//	    JTextField ipAdd = new JTextField();
	    jPanel.add(new JLabel("IP Address" ));
	    jPanel.add(skinIp);

	    JPanel jPort = new JPanel();
	    jPort.setBackground(Color.white);
	    jPort.setLayout(new GridLayout(1,2));
//	    JTextField portInput = new JTextField();
	    jPort.add(new JLabel("Port #" ));
	    jPort.add(skinPort);

	    JPanel connectCondition = new JPanel();
	    connectCondition.setBackground(Color.white);
	    connectCondition.setLayout(new GridLayout(1,3));
	    JLabel condition = new JLabel("Connecting...");
	    JButton connect = new JButton("Connect");
	    JButton disconnect = new JButton("Disconnect");
	    connect.addActionListener(this);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });

	    disconnect.addActionListener(this);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });

	    connectCondition.add(condition);
	    connectCondition.add(connect);
	    connectCondition.add(disconnect);

	    JPanel panel = new JPanel();
	    panel.setBackground(Color.white);
	    panel.setLayout(new GridLayout(4,1));
	    panel.add(jLabel,BorderLayout.NORTH);
	    panel.add(jPanel, BorderLayout.AFTER_LAST_LINE);
	    panel.add(jPort, BorderLayout.AFTER_LAST_LINE);
	    panel.add(connectCondition, BorderLayout.AFTER_LAST_LINE);
	    panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	    return panel;
	  }
  private JPanel processPanelFace(String lableName) {

	    JPanel jLabel = new JPanel();
	    jLabel.setBackground(new Color(0,94,181));
	    jLabel.setLayout(new GridLayout(1,1));
	    jLabel.add(new JLabel(lableName),BorderLayout.WEST);

	    JPanel jPanel = new JPanel();
	    jPanel.setBackground(Color.white);
	    jPanel.setLayout(new GridLayout(1,2));
//	    JTextField ipAdd = new JTextField();
	    jPanel.add(new JLabel("IP Address" ));
	    jPanel.add(faceIp);

	    JPanel jPort = new JPanel();
	    jPort.setBackground(Color.white);
	    jPort.setLayout(new GridLayout(1,2));
//	    JTextField portInput = new JTextField();
	    jPort.add(new JLabel("Port #" ));
	    jPort.add(facePort);

	    JPanel connectCondition = new JPanel();
	    connectCondition.setBackground(Color.white);
	    connectCondition.setLayout(new GridLayout(1,3));
	    JLabel condition = new JLabel("Connecting...");
	    JButton connect = new JButton("Connect");
	    JButton disconnect = new JButton("Disconnect");
	    connect.addActionListener(this);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });

	    disconnect.addActionListener(this);
	    
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });

	    connectCondition.add(condition);
	    connectCondition.add(connect);
	    connectCondition.add(disconnect);

	    JPanel panel = new JPanel();
	    panel.setBackground(Color.white);
	    panel.setLayout(new GridLayout(4,1));
	    panel.add(jLabel,BorderLayout.NORTH);
	    panel.add(jPanel, BorderLayout.AFTER_LAST_LINE);
	    panel.add(jPort, BorderLayout.AFTER_LAST_LINE);
	    panel.add(connectCondition, BorderLayout.AFTER_LAST_LINE);
	    panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	    return panel;
	  }
  private JPanel ClientPanel(){
    JPanel panel = new JPanel();
    panel.setBackground(new Color(0,94,181));
    panel.setLayout(new GridLayout(5,1));
    panel.add(processPanelFace(" Face "),BorderLayout.NORTH);
    panel.add(processPanelHeart(" Heart "),BorderLayout.AFTER_LAST_LINE);
    panel.add(processPanelBCI(" BCI "),BorderLayout.AFTER_LAST_LINE);
    panel.add(processPanelSkin(" Skin "),BorderLayout.AFTER_LAST_LINE);
    panel.add(processPanelEye(" Eye "),BorderLayout.AFTER_LAST_LINE);
    return panel;
  }
  public ClientDemo() {

    service = Executors.newCachedThreadPool();

    // TO TEST, RUN TWO SERVERS IN PORTS 1594 and 1595

    

    setLayout(new GridLayout(1,2));
    add(ClientPanel(), BorderLayout.NORTH);
    add(textArea, BorderLayout.CENTER);

    buttonConnect.addActionListener(this);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });
    setSize(600,600);
    setVisible(true);

  }
   
  

  private void close() {
    System.out.println("closing ....... +++++++");
    subscriber[0].stop();
    subscriber[1].stop();
    subscriber[2].stop();
    subscriber[3].stop();
    subscriber[4].stop();
  }

  private void shutdown() {
    subscriber[0].stop();
    subscriber[1].stop();
    subscriber[2].stop();
    subscriber[3].stop();
    subscriber[4].stop();
    service.shutdown();
    try {
      if (!service.awaitTermination(10, TimeUnit.SECONDS)) {
        service.shutdownNow();
      }
    } catch (InterruptedException ex) {
    }
  }


  @Override
  public void update(Observable o, Object arg) {
    String data = ((Subscriber) o).getObject().toString();
    if (data.compareTo("FIN") != 0)
      textArea.append(data + "\n" );
    else {
      close();
      buttonConnect.setEnabled(true);
    }
  }

  public static void main(String[] args) {
    ClientDemo tester = new ClientDemo();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    buttonConnect.setEnabled(false);
    String faceIP = faceIp.getText()!=null ? faceIp.getText(): "localhost";
    String heartIP = heartIp.getText()!=null ? heartIp.getText(): "localhost";
    String bciIP = bciIp.getText()!=null ? bciIp.getText(): "localhost";
    String skinIP = skinIp.getText()!=null ? skinIp.getText(): "localhost";
    String eyeIP = eyeIp.getText()!=null ? eyeIp.getText(): "localhost";
    int facePo = facePort.getText()!=null?Integer.parseInt(facePort.getText()):0;
    int skinPo = skinPort.getText()!=null?Integer.parseInt(skinPort.getText()):0;
    int heartPo = heartPort.getText()!=null?Integer.parseInt(heartPort.getText()):0;
    int bciPo = bciPort.getText()!=null?Integer.parseInt(bciPort.getText()):0;
    int eyePo = eyePort.getText()!=null?Integer.parseInt(eyePort.getText()):0;
    subscriber[0] = new Subscriber(faceIP, facePo);
    subscriber[1] = new Subscriber(heartIP, heartPo);
    subscriber[2] = new Subscriber(bciIP, bciPo);
    subscriber[3] = new Subscriber(skinIP, skinPo);
    subscriber[4] = new Subscriber(eyeIP, eyePo);
    service.submit(subscriber[0]);
    subscriber[0].addObserver(this);

    service.submit(subscriber[1]);
    subscriber[1].addObserver(this);
    
    service.submit(subscriber[2]);
    subscriber[2].addObserver(this);
    
    service.submit(subscriber[3]);
    subscriber[3].addObserver(this);
    
    service.submit(subscriber[4]);
    subscriber[4].addObserver(this);
    
  }
}