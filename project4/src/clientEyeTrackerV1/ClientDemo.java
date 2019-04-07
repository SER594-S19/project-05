package clientEyeTrackerV1;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import javax.swing.*;


public class ClientDemo extends JFrame implements Observer, ActionListener {
	
	private Integer count_1 = 0, count_2 = 0, count_3 = 0;

  private final Subscriber[] subscriber = new Subscriber[5];
  private final ExecutorService service;
  private JTextArea eyeTextArea = new JTextArea("");
  private JTextArea faceTextArea = new JTextArea("");
  private JTextArea heartTextArea = new JTextArea("");
  private JTextArea BCITextArea = new JTextArea("");
  private JTextArea skinTextArea = new JTextArea("");
  private JTextArea predictionTextArea = new JTextArea("");

  static StringBuilder sb1 = new StringBuilder();
  static StringBuilder sb2 = new StringBuilder();
  static StringBuilder sb3 = new StringBuilder();
   
  private JButton buttonConnect = new JButton("connect");
  private JButton predictButton = new JButton("Predict");
  
  private JTextField portInputFace = new JTextField("9999");
  private JTextField portInputHeart = new JTextField("9999");
  private JTextField portInputBCI = new JTextField("9999");
  private JTextField portInputSkin = new JTextField("9999");
  private JTextField portInputEye = new JTextField("9999");
  
  
  private JTextField ipInputFace = new JTextField("localhost");
  private JTextField ipInputHeart = new JTextField("localhost");
  private JTextField ipInputBCI = new JTextField("localhost");
  private JTextField ipInputSkin = new JTextField("localhost");
  private JTextField ipInputEye = new JTextField("localhost");
  

  private JTabbedPane tabbedPane = new JTabbedPane();
  
  
  private JPanel processPanel(String labelName) {

	    JPanel label = new JPanel();
	    label.setBackground(Color.lightGray);
	    label.setLayout(new GridLayout(1,1));
	    label.add(new JLabel(labelName),BorderLayout.WEST);

	    JPanel port = new JPanel();
	    port.setBackground(Color.orange);
	    port.setLayout(new GridLayout(1,2));
	    port.add(new JLabel("Enter Port Num" ));
	    
	    JPanel ip = new JPanel();
	    ip.setBackground(Color.orange);
	    ip.setLayout(new GridLayout(1,2));
	    ip.add(new JLabel("Enter the IP Address" ));
	    
	    if(labelName == " Face Simulator ") {
	    	port.add(portInputFace);
	    	ip.add(ipInputFace);
	    }
	    	
	    if(labelName == " Heart Simulator ") {
	    	port.add(portInputHeart);
	    	ip.add(ipInputHeart);
	    }
	    	
		if(labelName == " BCI Simulator ") {
			port.add(portInputBCI);
			ip.add(ipInputBCI);

		}
	    if(labelName == " Skin Simulator ") {
	    	port.add(portInputSkin);
	    	ip.add(ipInputSkin);
	    }
	    	
		if(labelName == " Eye Simulator ") {
			port.add(portInputEye);
			ip.add(ipInputEye);
		}


	    JPanel connectionButtons = new JPanel();
	    connectionButtons.setBackground(Color.CYAN);
	    connectionButtons.setLayout(new GridLayout(1,2));
	    
	    JButton connectButton = new JButton("Connect");
	    connectButton.addActionListener(this);
	    connectionButtons.add(connectButton);
	    
	    /*
	    JButton disconnectButton = new JButton("Disconnect");
	    disconnectButton.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  close();
	            }
		    });
	    	    
	    connectionButtons.add(disconnectButton);
	    */

	      
	    addWindowListener(new java.awt.event.WindowAdapter() {
			  @Override 
			  public void windowClosing(java.awt.event.WindowEvent e) {
			  shutdown(); 
			  System.exit(0); 
			  } 
			  });
	    
		  addWindowListener(new java.awt.event.WindowAdapter() {
		  @Override 
		  public void windowClosing(java.awt.event.WindowEvent e) {
		  shutdown(); 
		  System.exit(0); 
		  } 
		  });
		 
	    
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.CYAN);
	    panel.setLayout(new GridLayout(4,1));
	    panel.add(label,BorderLayout.NORTH);
	    panel.add(ip, BorderLayout.AFTER_LAST_LINE);
	    panel.add(port, BorderLayout.AFTER_LAST_LINE);
	    panel.add(connectionButtons, BorderLayout.AFTER_LAST_LINE);
	    panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
	    return panel;
	    
	  }
  
  private JPanel buttonPanel(String labelName) {
	  
	    JPanel label = new JPanel();
	    label.setBackground(Color.lightGray);
	    label.setLayout(new GridLayout(1,1));
	    label.add(new JLabel(labelName),BorderLayout.CENTER);
	    
	  JPanel connectionButtons = new JPanel();
	    connectionButtons.setBackground(Color.CYAN);
	    connectionButtons.setLayout(new GridLayout(1,2));
	    
	    JButton disconnectButton = new JButton("Stop servers");
	    disconnectButton.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  close();
	            }
		    });
	    	    
	    connectionButtons.add(disconnectButton);
	    
	    //JButton predictButton = new JButton("Predict");
	    predictButton.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  predict();
		    	  tabbedPane.setSelectedIndex(6);
	            }

		    });
	    	    
	    connectionButtons.add(predictButton);
	    
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.blue);
	    panel.setLayout(new GridLayout(2,1));
	    panel.add(label,BorderLayout.NORTH);
	    panel.add(connectionButtons, BorderLayout.AFTER_LAST_LINE);
    
	  return panel;
	 
  }
  
  private void predict() {/*
		System.out.println("Calling the python module");
		ProcessBuilder procBuild = new ProcessBuilder("python3","pad.py");
		/*
      try {
			//Process p = procBuild.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		String pythonScriptPath = "./pad_script.py";
		String[] cmd = new String[2];
		cmd[0] = "python"; // check version of installed python: python -V
		cmd[1] = pythonScriptPath;
		System.out.println("Python module called");
		// create runtime to execute external command
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmd);
			// retrieve output from python script
			BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while((line = bfr.readLine()) != null) {
				// display each output line form python script
				System.out.println(line);
				predictionTextArea.append(line + "\n" );
				}
						
		} catch (IOException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  
	  private JPanel ClientPanel(){
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.CYAN);
	    panel.setLayout(new GridLayout(6,1));
	    panel.add(processPanel(" Face Simulator "),BorderLayout.NORTH);
	    panel.add(processPanel(" Heart Simulator "),BorderLayout.AFTER_LAST_LINE);
	    panel.add(processPanel(" BCI Simulator "),BorderLayout.AFTER_LAST_LINE);
	    panel.add(processPanel(" Skin Simulator "),BorderLayout.AFTER_LAST_LINE);
	    panel.add(processPanel(" Eye Simulator "),BorderLayout.AFTER_LAST_LINE);
	    panel.add(buttonPanel("Stop and Predict"),BorderLayout.AFTER_LAST_LINE);
	    return panel;
	  }
	  
	  private void addIt(JTabbedPane tabbedPane, String text, JTextArea textArea) {
		    JPanel panel = new JPanel();
		    JScrollPane scroll = new JScrollPane (textArea);
		    
		    tabbedPane.addTab(text, panel);
		    
		    panel.setLayout(new BorderLayout());
		    panel.setBackground(Color.CYAN);	    
		    panel.add(scroll, BorderLayout.CENTER);
		    panel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		  }
	  
  public ClientDemo() {

    service = Executors.newCachedThreadPool();
    
    // TO TEST, RUN TWO SERVERS IN PORTS 1594 and 1595
    
    subscriber[0] = new Subscriber("localhost", 1595); //eye
    subscriber[1] = new Subscriber("localhost", 1596); //bci
    subscriber[2] = new Subscriber("localhost", 1597);
//    subscriber[3] = new Subscriber("localhost", 1597);
//    subscriber[4] = new Subscriber("localhost", 1598);
    
    getContentPane().setLayout(new GridLayout(1,2));
   tabbedPane.addTab("Settings", null, ClientPanel(), "Important Panel");
   addIt(tabbedPane, "Face Simulator", faceTextArea );
   addIt(tabbedPane, "Heart Simulator", heartTextArea);
   addIt(tabbedPane, "Brain Simulator", BCITextArea);
   addIt(tabbedPane, "Skin Simulator", skinTextArea);
   addIt(tabbedPane, "Eye Simulator", eyeTextArea);
   addIt(tabbedPane, "Prediction", predictionTextArea); 
   
   getContentPane().add(tabbedPane);

    buttonConnect.addActionListener(this);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });
      
    setSize(800,800);
    setVisible(true);
    
  }

  private void close() {
    System.out.println("clossing ....... +++++++");
    subscriber[0].stop();
    subscriber[1].stop();
    subscriber[2].stop();
    
	PrintWriter writer1;
	PrintWriter writer2;
	PrintWriter writer3;
	try {
		writer1 = new PrintWriter(new FileWriter("eye.csv", false));
		writer2 = new PrintWriter(new FileWriter("brain.csv", false));
		writer3 = new PrintWriter(new FileWriter("face.csv", false));
		System.out.println("The values are ---------------------");
		System.out.println(sb1.toString());
		System.out.println(sb2.toString());
	    writer1.write(sb1.toString());
	    writer2.write(sb2.toString());
	    writer3.write(sb3.toString());

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    //subscriber[3].stop();
    //subscriber[4].stop();
  }
  
    private void shutdown() {
    subscriber[0].stop();
    subscriber[1].stop();
    subscriber[2].stop();
    //subscriber[3].stop();
    //subscriber[4].stop();
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
    int portNum;
    String port = data.substring(data.length()-4,  data.length());
    data = data.substring(0,data.length()-4);
    System.out.println("Hello uhsuibcyec" + port);
    portNum = Integer.parseInt(port);
    
    System.out.println();
    if (data.compareTo("FIN") != 0 && portNum == 1595) {
    	eyeTextArea.append(String.valueOf(count_1++));
    	sb1.append(data + "\n" );
    	eyeTextArea.append(data + "\n" );
    	}
    else if(data.compareTo("FIN") != 0 && portNum == 1596) {
    	BCITextArea.append(String.valueOf(count_2++));
    	sb2.append(data + "\n" );
    	BCITextArea.append(data + "\n" );
    	}
    else if(data.compareTo("FIN") != 0 && portNum == 1597) {
    	faceTextArea.append(String.valueOf(count_3++));
    	sb3.append(data + "\n" );
    	faceTextArea.append(data + "\n" );
    	}
    else if(data.compareTo("FIN") != 0 && portNum == 1598)
    	skinTextArea.append(data + "\n" );
    else if(data.compareTo("FIN") != 0 && portNum == 1599)
    	heartTextArea.append(data + "\n" );

    else {
      close();
      buttonConnect.setEnabled(true);
    }    
  }

  public static void main(String[] args) throws IOException {
	  File file1 = new File("eye.csv");
	  File file2 = new File("brain.csv");
	  File file3 = new File("face.csv");
	  boolean result1 = Files.deleteIfExists(file1.toPath());
	  boolean result2 = Files.deleteIfExists(file2.toPath());
	  boolean result3 = Files.deleteIfExists(file3.toPath());
	  sb1.append("timestamp");
	  sb1.append(',');
	  sb1.append("PupilLeft");
	  sb1.append(',');
	  sb1.append("PupilRight");
	  sb1.append(',');
	  sb1.append("ValidityL");
	  sb1.append(',');
	  sb1.append("ValidityR");
	  sb1.append(',');
	  sb1.append("Gpx");
	  sb1.append(',');
	  sb1.append("Gpy");
	  sb1.append(',');
	  sb1.append("Fixation");
	  sb1.append(',');
	  sb1.append("Event");
	  sb1.append(',');
	  sb1.append("AOI");
	  sb1.append('\n');

	  sb2.append("timestamp,Frustration,Engagement,Meditation,Short_term_engagement,Long_term_engagement");
	  sb2.append('\n');

	  sb3.append("timestamp,Agreement,Concentrating,Disagreement,Interested,Thinking,Unsure");
	  sb3.append('\n');


    ClientDemo tester = new ClientDemo();
     
  }

  @Override
  public void actionPerformed(ActionEvent e) {
	  int portNumInputFace = Integer.parseInt(portInputFace.getText());
	  int portNumInputHeart = Integer.parseInt(portInputHeart.getText());
	  int portNumInputBCI = Integer.parseInt(portInputBCI.getText());
	  int portNumInputSkin = Integer.parseInt(portInputSkin.getText());
	  int portNumInputEye = Integer.parseInt(portInputEye.getText());
	  	  
	System.out.println("BCI POrt number is " + portNumInputBCI +  "Eye port number is " + portNumInputEye + "Face port number is " + portNumInputFace);
    buttonConnect.setEnabled(false);
  
	
    if (portNumInputEye == 1595) {
    service.submit(subscriber[0]);
    subscriber[0].addObserver(this);
    }
    else if (portNumInputBCI == 1596) {
    service.submit(subscriber[1]);
    subscriber[1].addObserver(this);
    }
    else if (portNumInputFace == 1597) {
    service.submit(subscriber[2]);
    subscriber[2].addObserver(this);
    }
    else if (portNumInputHeart == 1598) {
    service.submit(subscriber[3]);
    subscriber[3].addObserver(this); 
    }  

    else if (portNumInputSkin == 1599) {
    service.submit(subscriber[4]);
    subscriber[4].addObserver(this);
    } 	
  }
}

/*
try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {

    StringBuilder sb = new StringBuilder();
    sb.append("id,");
    sb.append(',');
    sb.append("Name");
    sb.append('\n');
    sb.append("1");
    sb.append(',');
    sb.append("Prashant Ghimire");
    sb.append('\n');
     
    sb.append(data);
    writer.write(sb.toString());

    System.out.println("done!");

  } catch (FileNotFoundException e) {
    System.out.println(e.getMessage());
  }
  */