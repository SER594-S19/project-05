package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ClientDemo extends JFrame implements Observer, ActionListener {

  private final Subscriber  [] subscriber = new Subscriber[2];
  private final ExecutorService service;
  private JTextArea textArea = new JTextArea();
  private JButton buttonConnect = new JButton("connect");
  
  public ClientDemo() {

    service = Executors.newCachedThreadPool();
    
    // TO TEST, RUN TWO SERVERS IN PORTS 1594 and 1595
    
    subscriber[0] = new Subscriber("localhost", 1594);
    subscriber[1] = new Subscriber("localhost", 1595);
    
    setLayout(new BorderLayout());
    add(textArea, BorderLayout.CENTER);  
    add(buttonConnect, BorderLayout.SOUTH);  
    buttonConnect.addActionListener(this);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });
    setSize(500,500);
    setVisible(true);
    
  }

  private void close() {
    System.out.println("clossing ....... +++++++");
    subscriber[0].stop();
    subscriber[1].stop();
  }
  
    private void shutdown() {
    subscriber[0].stop();
    subscriber[1].stop();
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
    service.submit(subscriber[0]);
    subscriber[0].addObserver(this); 

    service.submit(subscriber[1]);
    subscriber[1].addObserver(this);     
  }
}
