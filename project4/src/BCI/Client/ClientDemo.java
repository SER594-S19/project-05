
package BCI.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.StyledDocument;

public class ClientDemo extends JFrame implements Observer, ActionListener {

  private static final Logger LOGGER = Logger.getLogger(ClientDemo.class.getName());
  //private Subscriber subscriber;
  private final Subscriber  [] subscriber = new Subscriber[5];

  private JTextField [] ipInput = new JTextField[5];
  private JTextField [] portInput = new JTextField[5];
  private JButton [] connect = new JButton[5];
  private JButton [] disConnect = new JButton[5];
  private int i = 0;


  private final ExecutorService service;
  private JTextPane textArea = new JTextPane();
  private JButton buttonConnect = new JButton("connect");
  StyledDocument doc = textArea.getStyledDocument();
  JScrollPane scrollPane = new JScrollPane(textArea);
  private Observer obs = this;

  private JPanel connectivityButton(int type) {

    JPanel connectCondition = new JPanel();
    connectCondition.setLayout(new GridLayout(1,3));
    JLabel condition = new JLabel("Connecting...");

    connect[type] = new JButton("Connect");
    connect[type].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        connect[type].setEnabled(false);
        if(ipInput[0].getText().equals("")){
          JOptionPane.showMessageDialog(null, "BCI IP or Input is null. Please enter and try again", "alert", JOptionPane.ERROR_MESSAGE);
        }
        if(ipInput[1].getText().equals("")){
         // JOptionPane.showMessageDialog(null, "Face IP or Input is null", "alert", JOptionPane.ERROR_MESSAGE);
        }
        if(ipInput[2].getText().equals("")){
         // JOptionPane.showMessageDialog(null, "Heart IP or Input is null", "alert", JOptionPane.ERROR_MESSAGE);
        }
        if(ipInput[3].getText().equals("")){
        //  JOptionPane.showMessageDialog(null, "Skin IP or Input is null", "alert", JOptionPane.ERROR_MESSAGE);
        }
        if(ipInput[4].getText().equals("")){
        //  JOptionPane.showMessageDialog(null, "Eye IP or Input is null", "alert", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println(ipInput[type].getText());
        subscriber[type] = new Subscriber(ipInput[type].getText(), ((portInput[type].getText().equals("")) ? 1594 : Integer.parseInt(portInput[type].getText())));
        service.submit(subscriber[type]);
        subscriber[type].addObserver(obs);
      }
    });

    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });

    disConnect[type] = new JButton("disconnect");
    disConnect[type].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        connect[type].setEnabled(true);
        shutdown();
        close(type);
      }
    });

    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });

    connectCondition.add(condition);
    connectCondition.add(connect[type]);
    connectCondition.add(disConnect[type]);
    return connectCondition;

  }

  private JPanel processPanel(String lableName) {

    JPanel label = new JPanel();
    label.setLayout(new GridLayout(1,1));
    label.add(new JLabel(lableName),BorderLayout.WEST);

    JPanel ip = new JPanel();
    ip.setLayout(new GridLayout(1,2));
    //JTextField ipInput = new JTextField();
    ipInput[i] = new JTextField();
    ip.add(new JLabel("IP" ));
    ip.add(ipInput[i]);

    JPanel port = new JPanel();
    port.setLayout(new GridLayout(1,2));
    //JTextField portInput = new JTextField();
    portInput[i] = new JTextField();
    port.add(new JLabel("Port" ));
    port.add(portInput[i]);

    JPanel connectCondition = null;
    if(!lableName.equals(" BCI ")) {
      connectCondition = connectivityButton(0);
    }
    else if(!lableName.equals(" Face ")) {
      connectCondition = connectivityButton(1);
    }
    else if(!lableName.equals(" Heart ")) {
      connectCondition = connectivityButton(2);
    }
    else if(!lableName.equals(" Skin ")) {
      connectCondition = connectivityButton(3);
    }
    else if(!lableName.equals(" Eye ")) {
      connectCondition = connectivityButton(4);
    }
    /*JPanel connectCondition = new JPanel();
    connectCondition.setLayout(new GridLayout(1,3));
    JLabel condition = new JLabel("Connecting...");

    connect[i] = new JButton("Connect");
	connect[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          connect[i].setEnabled(false);
          subscriber[i] = new Subscriber(ipInput[i].getText(), Integer.parseInt(portInput[i].getText()));
            service.submit(subscriber[i]);
            subscriber[i].addObserver(obs);
      }
	});

    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown(i);
        System.exit(0);
      }
    });

    disConnect[i] = new JButton("disconnect");
    disConnect[i].addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          connect[i].setEnabled(false);
          shutdown(i);
          close(i);
      }
});

    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown(i);
        System.exit(0);
      }
    });

    connectCondition.add(condition);
    connectCondition.add(connect[i]);
    connectCondition.add(disConnect[i]); */

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4,1));
    panel.add(label,BorderLayout.NORTH);
    panel.add(ip, BorderLayout.AFTER_LAST_LINE);
    panel.add(port, BorderLayout.AFTER_LAST_LINE);
    panel.add(connectCondition, BorderLayout.AFTER_LAST_LINE);
    panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

    i++;
    return panel;
  }

  private JPanel ClientPanel(){
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(5,1));
    panel.add(processPanel(" BCI "),BorderLayout.NORTH);
    panel.add(processPanel(" Face "),BorderLayout.AFTER_LAST_LINE);
    panel.add(processPanel(" Heart "),BorderLayout.AFTER_LAST_LINE);
    panel.add(processPanel(" Skin "),BorderLayout.AFTER_LAST_LINE);
    panel.add(processPanel(" Eye "),BorderLayout.AFTER_LAST_LINE);
    return panel;
  }
  public ClientDemo() {

    service = Executors.newCachedThreadPool();
    // TO TEST, RUN TWO SERVERS IN PORTS 1594 and 1595

    //subscriber[0] = new Subscriber("localhost", 1594);
    //subscriber[1] = new Subscriber("localhost", 1595);

    //setLayout(new BorderLayout());
    setLayout(new GridLayout(1,2));
    add(ClientPanel(), BorderLayout.NORTH);
    // add(textArea, BorderLayout.CENTER);
    add(scrollPane, BorderLayout.CENTER);
    //add(buttonConnect, BorderLayout.SOUTH);
    //buttonConnect.addActionListener(this);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });
    setSize(800,600);
    setVisible(true);

  }

  private void close(int cnt) {
    System.out.println("clossing ....... +++++++");
    //subscriber[0].stop();
    //subscriber[1].stop();
    subscriber[cnt].stop();
  }

  private void shutdown() {
    //subscriber[0].stop();
    //subscriber[1].stop();
    //subscriber[cnt].stop();
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
    if (data.compareTo("FIN") != 0) {
      try {
        doc.insertString(doc.getLength(), data + "\n", null);
      } catch(Exception e) {
        LOGGER.log(Level.SEVERE, "Exception while writing in client", e);
      }
    } else {
      //close();
      buttonConnect.setEnabled(true);
    }
  }

  public static void main(String[] args) {
    ClientDemo tester = new ClientDemo();

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    buttonConnect.setEnabled(false);
    //service.submit(subscriber[0]);
    //subscriber[0].addObserver(this);

    //service.submit(subscriber[1]);
    //subscriber[1].addObserver(this);
  }
}
