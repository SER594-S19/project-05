package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;

public class ClientDemo extends JFrame implements Observer, ActionListener {
  private final Subscriber  [] subscriber = new Subscriber[5];
  private final ExecutorService service;
  private JButton buttonPort1 = new JButton("Connect for BCI");
  private JButton buttonPort2 = new JButton("Connect for Face Recognition");
  private JButton buttonPort3 = new JButton("Connect for Eye Tracking");
  private JButton buttonPort4 = new JButton("Connect for Heart Rate");
  private JButton buttonPort5 = new JButton("Connect for Galvanic Skin Conductivity");
  JTextArea textArea = new JTextArea();
  JTextArea textArea_2 = new JTextArea();
  JPanel dataPanel = new JPanel();
  JPanel facePanel = new JPanel();
  
  Double x, y;
  
  GraphingData gf;
  
  public ClientDemo() {
	  
	  
	  
	  x = y = 0.0;
	  
    service = Executors.newCachedThreadPool();
    subscriber[0] = new Subscriber("localhost", 1596);
    subscriber[1] = new Subscriber("localhost", 1595);
    subscriber[2] = new Subscriber("localhost", 1594);
    subscriber[3] = new Subscriber("localhost", 1597);
    subscriber[4] = new Subscriber("localhost", 1598);
    getContentPane().setLayout(null);
    facePanel.setBounds(43, 476, 660, 291);
      
    JPanel panelBCI=new JPanel();  
    panelBCI.setPreferredSize(new Dimension(300,180));
    JPanel panelFR=new JPanel(); 
    panelFR.setPreferredSize(new Dimension(300,180));
    JPanel panelET=new JPanel(); 
    panelET.setPreferredSize(new Dimension(300,180));
    JPanel panelHR=new JPanel(); 
    panelHR.setPreferredSize(new Dimension(300,180));
    JPanel panelSC=new JPanel();    
    panelSC.setPreferredSize(new Dimension(300,180));
    buttonPort1.setBounds(207, 57, 314, 35);
    buttonPort1.setBackground(new Color(26, 187, 190));
    buttonPort1.setFont(new Font("Courier",Font.BOLD, 20));
    buttonPort2.setBounds(160, 57, 417, 35);
    buttonPort2.setBackground(new Color(26, 187, 190));
    buttonPort2.setFont(new Font("Courier",Font.BOLD, 20));
    buttonPort3.setBounds(185, 63, 379, 35);
    buttonPort3.setBackground(new Color(26, 187, 190));
    buttonPort3.setFont(new Font("Courier",Font.BOLD, 20));
    buttonPort4.setBounds(198, 64, 344, 35);
    buttonPort4.setBackground(new Color(26, 187, 190));
    buttonPort4.setFont(new Font("Courier",Font.BOLD, 20));
    buttonPort5.setBounds(100, 58, 553, 35);
    buttonPort5.setBackground(new Color(26, 187, 190));
    buttonPort5.setFont(new Font("Courier",Font.BOLD, 20));
    facePanel.setBackground(new Color(26, 187, 190));
    panelBCI.setLayout(null);
   // graph.setSize(400,400);
    //graph.setBounds(20, 100, 300, 300);
    //graph.setVisible(true);
    panelBCI.add(buttonPort1);
    panelFR.setLayout(null);
    panelFR.add(buttonPort2);
    panelET.setLayout(null);
    panelET.add(buttonPort3);
    panelET.add(facePanel);
    panelHR.setLayout(null);
    panelHR.add(buttonPort4);
    panelSC.setLayout(null);
    panelSC.add(buttonPort5);
    
    JTabbedPane tp = new JTabbedPane();
    tp.setBounds(25, 16, 760, 900);
    tp.setBackground(Color.WHITE);
    tp.setFont(new Font("Courier",Font.BOLD,20));
    tp.add("BCI",panelBCI);  
    tp.add("FaceRecognition",panelFR);   
    tp.add("EyeTracking",panelET);   
    tp.add("HeartRate",panelHR); 
    tp.add("SkinConductance",panelSC);
    getContentPane().add(tp);
   // getContentPane().add(textArea);
   // getContentPane().add(graph);
       
    buttonPort1.addActionListener(this);
    buttonPort1.setEnabled(true);
    textArea.setBounds(43, 155, 665, 291);
    panelBCI.add(textArea);
    buttonPort2.addActionListener(this);
    buttonPort2.setEnabled(true);
    
    JTextArea textArea_1 = new JTextArea();
    textArea_1.setBounds(41, 161, 672, 291);
    panelFR.add(textArea_1);
    buttonPort3.addActionListener(this);
    buttonPort3.setEnabled(true);
    
    //JTextArea textArea_2 = new JTextArea();
    textArea_2.setBounds(43, 169, 264, 291);
    panelET.add(textArea_2);
    buttonPort4.addActionListener(this);
    buttonPort4.setEnabled(true);
    
    JTextArea textArea_3 = new JTextArea();
    textArea_3.setBounds(54, 169, 644, 291);
    panelHR.add(textArea_3);
    buttonPort5.addActionListener(this);
    buttonPort5.setEnabled(true);
    
    
    dataPanel = new GraphingData();
    dataPanel.setBounds(322, 169, 381, 291);
    //dataPanel.setBackground(Color.BLACK);
    panelET.add(dataPanel);
    
    //gf=new GraphingData(this);
	// dataPanel.add(gf);
    
    JTextArea textArea_4 = new JTextArea();
    textArea_4.setBounds(55, 166, 648, 291);
    panelSC.add(textArea_4);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });
    setSize(830,1000);
    setVisible(true);
    
  }

  public void setXY(Double x, Double y) {
	  
	  this.x = x;
	  this.y = y;
	  
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
    
    dataPanel.repaint();
    dataPanel = new GraphingData();
    this.repaint();
   // double[] dataval = ((GraphingData)dataPanel).getXY();
	//System.out.println("datavalueeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+dataval[0]);
	//double dataval[] = gf.graphValue();
	//System.out.print(dataval[0]);
	/* if(dataval[0] <= 0.5 && dataval[1] <= 0.5) {
	    JLabel imgLabel = new JLabel();
	    imgLabel.setIcon(new ImageIcon("image\\happy.gif"));
	    facePanel.add(imgLabel);
	} 
	else if (dataval[0] >= 0.8 && dataval[1] >= 0.8) {
		/*BufferedImage image = null;
		try {
			image = ImageIO.read(new File("image\\happy.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(new ImageIcon("image\\normal.gif"));
	    facePanel.add(imgLabel);
	} else {
		/*BufferedImage image = null;
		try {
			image = ImageIO.read(new File("image\\normal.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(new ImageIcon("image\\sad.gif"));
	    facePanel.add(imgLabel);
	} */
    if (data.compareTo("FIN") != 0) {
      textArea_2.append(data + "\n" );
    	}
    else {
      close();
      buttonPort3.setEnabled(true);
    }    
    //this.repaint();
  }

  public static void main(String[] args) {
    ClientDemo tester = new ClientDemo();
     
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	  if (e.getSource() == buttonPort1) {
		  JOptionPane.showMessageDialog(null,"Port1 BCI Connecting.");
		    service.submit(subscriber[0]);
		    subscriber[0].addObserver(this);
	  }else if (e.getSource() == buttonPort2) {
		  JOptionPane.showMessageDialog(null,"Port2 FaceRecognition Connecting.");
		  	service.submit(subscriber[1]);
		    subscriber[1].addObserver(this);
	  }else if (e.getSource() == buttonPort3) {
		  JOptionPane.showMessageDialog(null,"Port3 EyeTracking Connecting.");
		  	service.submit(subscriber[2]);
		    subscriber[2].addObserver(this);		   
	  }else if (e.getSource() == buttonPort4) {
		  JOptionPane.showMessageDialog(null,"Port4 HeartRate Connecting.");
		  	service.submit(subscriber[3]);
		    subscriber[3].addObserver(this);
	  }else if (e.getSource() == buttonPort5) {
		  JOptionPane.showMessageDialog(null,"Port5 SkinConductivity Connecting.");
		  	service.submit(subscriber[4]);
		    subscriber[4].addObserver(this);
	  }
  }
}