package Client;

//import com.mortennobel.imagescaling.ResampleFilters;
//import com.mortennobel.imagescaling.ResampleOp;
//import org.dmg.pmml.False;
//import org.dmg.pmml.True;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ClientDemo extends JFrame implements Observer, ActionListener  {

  public static HashMap<Integer,String> port_panel_mapping = new HashMap<Integer,String>();
  private final Subscriber [] subscriber = new Subscriber[5];
  private final ExecutorService service;
  //JTextArea panel1Text = new JTextArea(" ", 20, 30);
  JTextArea panel2Text = new JTextArea(" ", 30, 30);
  JTextArea panel3Text = new JTextArea(" ", 30, 30);
  JTextArea panel4Text = new JTextArea(" ", 30, 30);
  JTextArea panel5Text = new JTextArea(" ", 30, 30);

  private JTabbedPane tabbedPane1 = new JTabbedPane();
  private String ipFace;
  private String ipEyes;
  private String ipSkin;
  private String ipHeart;
  private String ipBCI;
  private String port_face;
  private String port_eyes;
  private String port_skin;
  private String port_heart;
  private String port_bci;
  private boolean connect_status1 = false;
  private boolean connect_status2 = false;
  private boolean connect_status3 = false;
  private boolean connect_status4 = false;
  private boolean connect_status5 = false;
  private String val = "0";

  private JFrame frame = new JFrame();
  private JButton buttonConnect1 = new JButton("connect");
  private JButton buttonConnect2 = new JButton("connect");
  private JButton buttonConnect3 = new JButton("connect");
  private JButton buttonConnect4 = new JButton("connect");
  private JButton buttonConnect5 = new JButton("connect");
  private JButton buttonEval = new JButton("Predict");
  private JButton Next = new JButton("Next");
  private JComponent panel1 = makeTextPanel1("Face");
  private JComponent panel2 = makeTextPanel2("Eyes");
  private JComponent panel3 = makeTextPanel3("Skin");
  private JComponent panel4 = makeTextPanel4("Heart Rate");
  private JComponent panel5 = makeTextPanel5("BCI");
  private JLabel t = new JLabel("Client Running...");
  private JLabel nnMsg = new JLabel("Please wait while we Predict on Trained Model....May take a few minutes.");
  private JLabel Goodlabel = new JLabel();
  private JLabel ImageToPredict = new JLabel();
  //private final NeuralNet neuralNetwork = new NeuralNet();

  int num = 3;
  int numOfImage = 1;

  int NumberOfInputsReceived = 0;
  
  
  String filePath = "res/input.csv";
  File file = new File(filePath);

  
  FileWriter outputfile;
  
  //  private JComponent panel1 = makeTextPanel("Panel #1");
//  tabbedPane.add
  public ClientDemo() {
    if(file.exists()){
      file.delete();
    }
    String filePath = "res/input.csv";
    file = new File(filePath);


    service = Executors.newCachedThreadPool();

    setLayout(new BorderLayout());
    //frame.add(textArea, BorderLayout.CENTER);


   // panel1.add(panel1Text, BorderLayout.SOUTH);
    panel1.add(Goodlabel);
    panel1.add(ImageToPredict);
    panel2.add(panel2Text, BorderLayout.SOUTH);
    panel3.add(panel3Text, BorderLayout.SOUTH);
    panel4.add(panel4Text, BorderLayout.SOUTH);
    panel5.add(panel5Text, BorderLayout.SOUTH);

    frame.add(tabbedPane1);
    tabbedPane1.addTab("Face",panel1);
    tabbedPane1.addTab("Eyes",panel2);
    tabbedPane1.addTab("Skin",panel3);
    tabbedPane1.addTab("Heart Rate",panel4);
    tabbedPane1.addTab("BCI",panel5);
    frame.setSize(800,450);
    frame.setVisible(true);
    frame.setResizable(false);


  }

  protected JComponent makeTextPanel1(String text) {
	buttonConnect1.setName("1");
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel("Enter IP address of Server ");
    filler.setHorizontalAlignment(JLabel.LEFT);
    JLabel filler1 = new JLabel("Select Port Number ");
    filler1.setHorizontalAlignment(JLabel.LEFT);
    final JTextField textField = new JTextField();
    textField.setColumns(8);
    String[] portNumber = {"1594","1595","1596","1597","1598"};
    SpinnerListModel portModel = new SpinnerListModel(portNumber);
    JSpinner spinner = new JSpinner(portModel);
    buttonConnect1.setHorizontalAlignment(JButton.LEFT);
    buttonEval.setHorizontalAlignment(JButton.RIGHT);




    // panel.setLayout(new GridLayout(4, 4));
    panel.add(filler);
    panel.add(textField);
    panel.add(filler1);
    panel.add(spinner);
    panel.add(buttonConnect1);
    panel.add(buttonEval);
    
    ipFace = textField.getText();
    port_face = spinner.getValue().toString();
    subscriber[0] = new Subscriber(ipFace, Integer.parseInt(port_face));
    ClientDemo.port_panel_mapping.put(Integer.parseInt(port_face), "textPanel1");
    
    spinner.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            ipFace = textField.getText();
            port_face = spinner.getValue().toString();
            subscriber[0] = new Subscriber(ipFace, Integer.parseInt(port_face));
            ClientDemo.port_panel_mapping.put(Integer.parseInt(port_face), "textPanel1");
            System.out.println("New port is " + port_face);
        }
    });
    buttonEval.addActionListener(this);
    buttonConnect1.addActionListener(this);
    Next.addActionListener(this);
    Next.setVisible(true);


    /*buttonConnect1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ipFace = textField.getText();
        port_face = spinner.getValue().toString();
        subscriber[0] = new Subscriber(ipFace, Integer.parseInt(port_face));
        Client.ClientDemo.port_panel_mapping.put(Integer.parseInt(port_face), "textPanel1");
        // To add for loop to connect other ports.

        subscriber[0] = new Subscriber(ipFace, Integer.parseInt(port_face));

      }
    });*/
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {

        shutdown();
        System.exit(0);

      }
    });
    return panel;
  }

  protected JComponent makeTextPanel2(String text) {
    buttonConnect2.setName("2");
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel("Enter IP address of Server ");
    filler.setHorizontalAlignment(JLabel.LEFT);
    JLabel filler1 = new JLabel("Select Port Number ");
    filler1.setHorizontalAlignment(JLabel.LEFT);
    final JTextField textField = new JTextField();
    textField.setColumns(8);
    String[] portNumber = {"1594","1595","1596","1597","1598"};
    SpinnerListModel portModel = new SpinnerListModel(portNumber);
    JSpinner spinner = new JSpinner(portModel);
    // panel.setLayout(new GridLayout(4, 4));
    panel.add(filler);
    panel.add(textField);
    panel.add(filler1);
    panel.add(spinner);
    panel.add(buttonConnect2);
    
    port_eyes = spinner.getValue().toString();
    ipEyes = textField.getText();
    subscriber[1] = new Subscriber(ipEyes, Integer.parseInt(port_eyes));
    ClientDemo.port_panel_mapping.put(Integer.parseInt(port_eyes), "textPanel2");
    
    
    spinner.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            port_eyes = spinner.getValue().toString();
            ipEyes = textField.getText();
            subscriber[1] = new Subscriber(ipEyes, Integer.parseInt(port_eyes));
            ClientDemo.port_panel_mapping.put(Integer.parseInt(port_eyes), "textPanel2");
            System.out.println("New port is " + port_eyes);
        }
    });
    
    buttonConnect2.addActionListener(this);

    /*buttonConnect2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ipEyes = textField.getText();
        port_eyes = spinner.getValue().toString();
        subscriber[1] = new Subscriber(ipEyes, Integer.parseInt(port_eyes));
        Client.ClientDemo.port_panel_mapping.put(Integer.parseInt(port_eyes), "textPanel2");
      }
    });*/
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {

        shutdown();
        System.exit(0);

      }
    });
    return panel;
  }
  protected JComponent makeTextPanel3(String text) {
	  buttonConnect3.setName("3");
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel("Enter IP address of Server ");
    filler.setHorizontalAlignment(JLabel.LEFT);
    JLabel filler1 = new JLabel("Select Port Number ");
    filler1.setHorizontalAlignment(JLabel.LEFT);
    final JTextField textField = new JTextField();
    textField.setColumns(8);
    String[] portNumber = {"1594","1595","1596","1597","1598"};
    SpinnerListModel portModel = new SpinnerListModel(portNumber);
    JSpinner spinner = new JSpinner(portModel);

    // panel.setLayout(new GridLayout(4, 4));
    panel.add(filler);
    panel.add(textField);
    panel.add(filler1);
    panel.add(spinner);
    panel.add(buttonConnect3);
    
    ipSkin = textField.getText();
    port_skin = spinner.getValue().toString();
    subscriber[2] = new Subscriber(ipSkin, Integer.parseInt(port_skin));
    ClientDemo.port_panel_mapping.put(Integer.parseInt(port_skin), "textPanel3");
    
    spinner.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            ipSkin = textField.getText();
            port_skin = spinner.getValue().toString();
            subscriber[2] = new Subscriber(ipSkin, Integer.parseInt(port_skin));
            ClientDemo.port_panel_mapping.put(Integer.parseInt(port_skin), "textPanel3");
            System.out.println("New port is " + port_skin);
        }
    });
    
    
    buttonConnect3.addActionListener(this);
    
    
    /*buttonConnect3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ipSkin = textField.getText();
        port_skin = spinner.getValue().toString();
        subscriber[2] = new Subscriber(ipSkin, Integer.parseInt(port_skin));
        Client.ClientDemo.port_panel_mapping.put(Integer.parseInt(port_skin), "textPanel3");
      }
    });*/
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {

        shutdown();
        System.exit(0);

      }
    });
    return panel;
  }
  protected JComponent makeTextPanel4(String text) {
	  buttonConnect4.setName("4");
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel("Enter IP address of Server ");
    filler.setHorizontalAlignment(JLabel.LEFT);
    JLabel filler1 = new JLabel("Select Port Number ");
    filler1.setHorizontalAlignment(JLabel.LEFT);
    final JTextField textField = new JTextField();
    textField.setColumns(8);
    String[] portNumber = {"1594","1595","1596","1597","1598"};
    SpinnerListModel portModel = new SpinnerListModel(portNumber);
    JSpinner spinner = new JSpinner(portModel);

    // panel.setLayout(new GridLayout(4, 4));
    panel.add(filler);
    panel.add(textField);
    panel.add(filler1);
    panel.add(spinner);
    panel.add(buttonConnect4);
    
    ipHeart = textField.getText();
    port_heart =spinner.getValue().toString();
    subscriber[3] = new Subscriber(ipHeart, Integer.parseInt(port_heart));
    ClientDemo.port_panel_mapping.put(Integer.parseInt(port_heart), "textPanel4");
    
    spinner.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            ipHeart = textField.getText();
            port_heart =spinner.getValue().toString();
            subscriber[3] = new Subscriber(ipHeart, Integer.parseInt(port_heart));
            ClientDemo.port_panel_mapping.put(Integer.parseInt(port_heart), "textPanel4");
            System.out.println("New port is " + port_heart);
        }
    });
    
    buttonConnect4.addActionListener(this);
    /*buttonConnect4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ipHeart = textField.getText();
        port_heart =spinner.getValue().toString();
        subscriber[3] = new Subscriber(ipHeart, Integer.parseInt(port_heart));
        Client.ClientDemo.port_panel_mapping.put(Integer.parseInt(port_heart), "textPanel4");
      }
    });*/
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {

        shutdown();
        System.exit(0);

      }
    });
    return panel;
  }
  protected JComponent makeTextPanel5(String text) {
	  buttonConnect5.setName("5");
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel("Enter IP address of Server ");
    filler.setHorizontalAlignment(JLabel.LEFT);
    JLabel filler1 = new JLabel("Select Port Number ");
    filler1.setHorizontalAlignment(JLabel.LEFT);
    final JTextField textField = new JTextField();
    textField.setColumns(8);
    String[] portNumber = {"1594","1595","1596","1597","1598"};
    SpinnerListModel portModel = new SpinnerListModel(portNumber);
    JSpinner spinner = new JSpinner(portModel);




    // panel.setLayout(new GridLayout(4, 4));
    panel.add(filler);
    panel.add(textField);
    panel.add(filler1);
    panel.add(spinner);
    panel.add(buttonConnect5);
    
    ipBCI = textField.getText();
    port_bci = spinner.getValue().toString();
    subscriber[4] = new Subscriber(ipBCI, Integer.parseInt(port_bci));
    ClientDemo.port_panel_mapping.put(Integer.parseInt(port_bci), "textPanel5");
    
    spinner.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            ipBCI = textField.getText();
            port_bci = spinner.getValue().toString();
            subscriber[4] = new Subscriber(ipBCI, Integer.parseInt(port_bci));
            ClientDemo.port_panel_mapping.put(Integer.parseInt(port_bci), "textPanel5");
            System.out.println("New port is " + port_bci);
        }
    });
    
    buttonConnect5.addActionListener(this);
    
    
    /*buttonConnect5.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ipBCI = textField.getText();
        port_bci = spinner.getValue().toString();
        subscriber[4] = new Subscriber(ipBCI, Integer.parseInt(port_bci));
        Client.ClientDemo.port_panel_mapping.put(Integer.parseInt(port_bci), "textPanel5");
      }
    });*/
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {

        shutdown();
        System.exit(0);

      }
    });
    return panel;
  }

  private void close() {
    System.out.println("clossing ....... +++++++");
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
    int portNo = ((Subscriber) o).getPort();
    String data = ((Subscriber) o).getObject().toString();
    //double[] dataOrig= ((Subscriber) o).getObject();
    if (data.compareTo("FIN") != 0) {
      t.setText("Client running and connected to server "+portNo+"......");
      t.setVisible(true);
      try {
        if (outputfile == null) {
          outputfile = new FileWriter(file);
        }
        CSVUtils.writeLine1(outputfile, data);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

        checkPaneltoAppend(portNo, data);

//      try {
//        if (outputfile == null) {
//          outputfile = new FileWriter(file);
//        }
//        CSVUtils.writeLine1(outputfile, data);
//
//
//      } catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }


    }
    else {
      t.setText("Sorry we are not recieving any data on port "+portNo);
      t.setVisible(true);
      buttonConnect1.setEnabled(true);
      buttonConnect2.setEnabled(true);
      buttonConnect3.setEnabled(true);
      buttonConnect4.setEnabled(true);
      buttonConnect5.setEnabled(true);

    }
  }

  private void checkPaneltoAppend(int portNo, String data) {
    switch (ClientDemo.port_panel_mapping.get(portNo)){
      case "textPanel1":

       // panel1Text.append(data + "\n" );
        break;
      case "textPanel2":
        panel2Text.append(data + "\n" );
        break;
      case "textPanel3":
        panel3Text.append(data + "\n" );
        break;
      case "textPanel4":
        panel4Text.append(data + "\n" );
        break;
      case "textPanel5":
        panel5Text.append(data + "\n" );
        break;
    }
  }

  public static void main(String[] args) {

    ClientDemo tester = new ClientDemo();
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == buttonConnect1) {
    	
    	connect_status1 = !connect_status1;
    	
      if(connect_status1)
      {
          t.setVisible(false);
          panel1.add(t,BorderLayout.PAGE_END);
    	  buttonConnect1.setText("Disconnect");
          buttonEval.setEnabled(false);
    	  service.submit(subscriber[0]);
          subscriber[0].addObserver(this);
          nnMsg.setVisible(false);

    	  
      }
      else
      {
          panel1.remove(t);
          panel1.remove(nnMsg);
    	  myFunc();
          buttonEval.setEnabled(true);
    	  buttonConnect1.setText("Connect");
      }
      
      
    }else if (e.getSource() == buttonConnect2) {
    	
    	connect_status2 = !connect_status2;
      
      if(connect_status2)
      {
          t.setVisible(false);
          panel2.add(t,BorderLayout.PAGE_END);
    	  buttonConnect2.setText("DisConnect");
    	  service.submit(subscriber[1]);
          subscriber[1].addObserver(this);
    	  
      }
      else
      {
    	  myFunc();
    	  buttonConnect2.setText("Connect");
      }
      
      
    }else if (e.getSource() == buttonConnect3) {
    	
    	connect_status3 = !connect_status3;
    	
    	if(connect_status3)
    	{
              t.setVisible(false);
              panel3.add(t,BorderLayout.PAGE_END);
    		  buttonConnect3.setText("DisConnect");
    	      service.submit(subscriber[2]);
    	      subscriber[2].addObserver(this);
    		
    	}
    	   else
    	      {
    	    	  
    	    	  buttonConnect3.setText("Connect");
    	      }

    }else if (e.getSource() == buttonConnect4) {
    	
    	connect_status4 = !connect_status4;
    	
    	if(connect_status4) {
              t.setVisible(false);
              panel4.add(t,BorderLayout.PAGE_END);
    		  buttonConnect4.setText("DisConnect");
    	      service.submit(subscriber[3]);
    	      subscriber[3].addObserver(this);
    		
    		
    	}
    	 else
	      {
	    	  
	    	  buttonConnect4.setText("Connect");
	      }
    
    }else if (e.getSource() == buttonConnect5) {

      connect_status5 = !connect_status5;

      if (connect_status5) {
        buttonConnect5.setText("DisConnect");
        service.submit(subscriber[4]);
        subscriber[4].addObserver(this);
      } else {
        buttonConnect5.setText("Connect");
      }
    } else if(e.getSource() == buttonEval)
     {
        // SwingUtilities.invokeLater(() -> progressBar.showProgressBar("Training may take one or two minutes..."));

         Executors.newCachedThreadPool().submit(() -> {
         nnMsg.setVisible(true);
         panel1.add(Next);

         try {
//            Path path = Paths.get("Python");
//           // System.out.print();
//            Path absolutePathP = path.toAbsolutePath();
            Path pred = Paths.get("predict.py");
            Path absolutePred = pred.toAbsolutePath();
            panel1.add(nnMsg,BorderLayout.PAGE_END);

           //System.out.println(command);
            File n = new File("CNN/bin/Python");
            System.out.println("here");
           String command = n.getAbsolutePath() + " " + absolutePred;

           nnMsg.setVisible(true);
           Process p = Runtime.getRuntime().exec(command);
           BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
           String line = "";
           while ((line = in.readLine()) != null) {
             // display each output line form python script
           //  System.out.println(line);
             System.out.println(line);
             val = line;
             System.out.println("val" +val);
           }
         }
         catch (IOException ie){
           System.out.print(ie);
         }

            finally {
             ImageIcon imgPre = new ImageIcon(new ImageIcon("res/0/PredictImage0.png").getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT));
          // ImageIcon imgPre = new ImageIcon((new ImageIcon("res/0/PredictImage0.png").getS);
             ImageToPredict.setIcon(imgPre);
             System.out.println("here" + val.charAt(1));
             if (val.charAt(1) == '0') {
               System.out.println("GOOD IMAGE");
               ImageIcon image2 = new ImageIcon("res/Smile.jpg");
               Goodlabel.setIcon(image2);
               nnMsg.setText("Good Image");
             } else if (val.charAt(1) == '1') {
               ImageIcon image3 = new ImageIcon("res/Sad.jpg");
              // JLabel label = new JLabel(image3);
               nnMsg.setText("Bad Image");
               Goodlabel.setIcon(image3);
               System.out.println("BAD IMAGE");
             } else if (val.charAt(1) == '2') {
               ImageIcon image4 = new ImageIcon("res/straightFace.png");
              // JLabel label = new JLabel(image4);
               nnMsg.setText("Okay Image");
               Goodlabel.setIcon(image4);
               System.out.println("OKAY IMAGE");
             } else {
               System.out.println("Couldnt predict");
             }
         }
//         catch (IOException ie){
//             System.out.println(ie);
//         }
       });
     } else if(e.getSource() == Next){

      if(num >= val.length()){
        nnMsg.setText("No more images left, Congratulations, the Prediction is Over! ");
        Goodlabel.setIcon(null);
        ImageToPredict.setIcon(null);
        Next.setVisible(false);
        buttonConnect1.setEnabled(true);
        buttonEval.setEnabled(false);
        System.out.println("Couldnt predict");

      }
      else if (val.charAt(num) == '0') {
        System.out.println("GOOD IMAGE");
        ImageIcon image2 = new ImageIcon("res/Smile.jpg");
        Goodlabel.setIcon(image2);
        ImageIcon imgPre = new ImageIcon(new ImageIcon("res/0/PredictImage"+numOfImage+".png").getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT));
        ImageToPredict.setIcon(imgPre);
       // panel1.add(Goodlabel);
        nnMsg.setText("Good Image");
      } else if (val.charAt(num) == '1') {
        ImageIcon image3 = new ImageIcon("res/Sad.jpg");
       // JLabel label = new JLabel(image3);
        Goodlabel.setIcon(image3);
        nnMsg.setText("Bad Image");
        ImageIcon imgPre = new ImageIcon(new ImageIcon("res/0/PredictImage"+numOfImage+".png").getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT));
        ImageToPredict.setIcon(imgPre);
       // panel1.add(label, BorderLayout.SOUTH);
        System.out.println("BAD IMAGE");
      } else if (val.charAt(num) == '2') {
        ImageIcon image4 = new ImageIcon("res/straightFace.png");
        JLabel label = new JLabel(image4);
        nnMsg.setText("Okay Image");
        Goodlabel.setIcon(image4);
        ImageIcon imgPre = new ImageIcon(new ImageIcon("res/0/PredictImage"+numOfImage+".png").getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT));
        ImageToPredict.setIcon(imgPre);
        //panel1.add(label, BorderLayout.SOUTH);
        System.out.println("OKAY IMAGE");
      }
      num = num + 2;
      numOfImage++;
    }

  }
  private static double[] transformImageToOneDimensionalVector(BufferedImage img) {

    double[] imageGray = new double[50 * 50];
    int w = img.getWidth();
    int h = img.getHeight();
    int index = 0;
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        Color color = new Color(img.getRGB(j, i));
        int red = (color.getRed());
        int green = (color.getGreen());
        int blue = (color.getBlue());
        int v = 255 - ((red + green + blue)/ 3);
        imageGray[index] = v;
        index++;
      }
    }
    return imageGray;
  }


  
  public void myFunc() {

	GraphPlot plot = new GraphPlot(0);
	int x = plot.returnedVal();
	System.out.println(x);
	if(x>49){
      JOptionPane.showMessageDialog(null, "Your Graphs have been plotted!", "Hurray!" , JOptionPane.INFORMATION_MESSAGE);
    }
    else{
      JOptionPane.showMessageDialog(null, "Sorry, Let it run for more time. We require More Values to plot the graphs. ", "Try Again!" , JOptionPane.INFORMATION_MESSAGE);

    }
	plot.setLayout(null);
	plot.updateUI();


  }


}
