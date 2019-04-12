package Client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
public class ClientEyeTracker extends JFrame implements Observer, ActionListener {

  private final Subscriber subscriber;
  private final ExecutorService service;
  private JTextArea textArea = new JTextArea("This is Text Area");
  private JButton buttonConnect = new JButton("CONNECT FOR EYE TRACKING");
  private JLabel label = new JLabel("EYE TRACKING CLIENT");
  
  public ClientEyeTracker() {

    service = Executors.newCachedThreadPool();
    subscriber = new Subscriber("localhost", 1594);
    setLayout(new BorderLayout());
    label.setBounds(100,100,100,100);  
	label.setForeground(Color.DARK_GRAY); 
	label.setFont(new Font("Courier",Font.BOLD, 41));
	buttonConnect.setBackground(Color.CYAN);
	buttonConnect.setFont(new Font("Courier",Font.BOLD, 20));	
	add(label, BorderLayout.NORTH);
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
    subscriber.stop();
  }
  
    private void shutdown() {
    subscriber.stop();
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
	  ClientEyeTracker tester = new ClientEyeTracker();
     
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	  if (e.getSource() == buttonConnect) {
		  JOptionPane.showMessageDialog(null,"Port Connecting");
		    service.submit(subscriber);
		    subscriber.addObserver(this);
	  }
}
}