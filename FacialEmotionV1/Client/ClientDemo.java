package Client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ClientDemo extends JFrame implements Observer {

  private final Subscriber  [] subscriber;
  private final ExecutorService service;
  private JButton buttonConnect;
  private JButton buttonConnect1;
  private JButton buttonConnect2;
  private JButton success;
  private JLabel dummySpacePanel;
  private JButton setGraph;
  private JFreeChart chart;
  private XYPlot plot;
  private XYLineAndShapeRenderer renderer;
  private ArrayList<JTextField> textFieldsList;
  public static ArrayList<JButton> successList;
  public static ArrayList<JButton> connectList;
  public static ArrayList<JButton> stopList;

  public static double graphData;
  public static String graphLabel;
  public static ClientDemo thisClass;

  
  JFrame frame;
  
  public ClientDemo() {

    service = Executors.newCachedThreadPool();
    
    graphData = 0.0;
    graphLabel = "Agreement";
    renderer = new XYLineAndShapeRenderer();
    textFieldsList = new ArrayList<JTextField>();
    subscriber = new Subscriber[5];
    successList = new ArrayList<JButton>();
    connectList = new ArrayList<JButton>();
    stopList = new ArrayList<JButton>();  
    
    JPanel connectionPanel = new JPanel(new GridLayout(5, 0));
    connectionPanel.setPreferredSize(new Dimension(100,100));
    JPanel buttonPanel = new JPanel(new GridLayout(0,1));  
    
    JPanel graphPanel = new JPanel(new BorderLayout());
    JPanel chartPanel = createGraphPanel();
    graphPanel.add(chartPanel,BorderLayout.CENTER);
    graphPanel.setVisible(true);
	graphPanel.setPreferredSize(new Dimension(400,400));
	    
    JPanel dropDownPanel = new JPanel(new FlowLayout());
	JComboBox serversList = new JComboBox();
	JLabel dropDownLabel = new JLabel("Show in graph:");
	//setGraph = new JButton("Display");
	serversList.setPreferredSize(new Dimension(100,30));
	dropDownPanel.setPreferredSize(new Dimension(70,70));
	dropDownPanel.add(dropDownLabel);
	dropDownPanel.add(serversList);
	//dropDownPanel.add(setGraph);
	graphPanel.add(dropDownPanel, BorderLayout.NORTH);
	Image icon = new ImageIcon(this.getClass().getResource("Play16.png")).getImage();
	Image icon1 = new ImageIcon(this.getClass().getResource("Stop16.png")).getImage();
	Image icon2 = new ImageIcon(this.getClass().getResource("Done24.png")).getImage();

	
	serversList.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			String selected = (String) ((JComboBox) event.getSource()).getSelectedItem();
			for(int i=0;i<5;i++) {
				if(null != subscriber[i])
					subscriber[i].setServerPortSelected(selected);
			}
		}
	});
	
    int i=1;
     while(i<6) {    
	     // create empty JTextField	  
         buttonConnect= new JButton("");
         buttonConnect.setIcon(new ImageIcon(icon));
         buttonConnect1 = new JButton("");
         buttonConnect1.setIcon(new ImageIcon(icon1));
         success = new JButton("");
         success.setIcon(new ImageIcon(icon2));
         success.setBorder(BorderFactory.createEmptyBorder());
         buttonConnect1.setEnabled(false);
         successList.add(success);
         connectList.add(buttonConnect);
         stopList.add(buttonConnect1);
         buttonConnect.setBorder(BorderFactory.createEmptyBorder());
         buttonConnect1.setBorder(BorderFactory.createEmptyBorder());
         
         dummySpacePanel = new JLabel("         ");
	     JLabel label = new JLabel();		
		 label.setText("ENTER PORT NUMBER "+i);
		 
		// create JTextField with default text
	     JTextField field2 = new JTextField();
	     textFieldsList.add(field2);
	     JLabel label1 = new JLabel();		
		 label1.setText("ENTER IP ADDRESS "+i);
		 
	     JTextField field1 = new JTextField();
	     //field1.setText("IP Address");
	     field1.setEnabled(false); 
	     connectionPanel.add(label);
	     connectionPanel.add(field2);
	     connectionPanel.add(label1);
	     connectionPanel.add(field1);
	     buttonConnect.setName(""+i);
	     buttonConnect1.setName(""+i);

	     connectionPanel.add(dummySpacePanel);
	     connectionPanel.add(buttonConnect);
	     connectionPanel.add(buttonConnect1);
	     connectionPanel.add(success);
	     success.setVisible(false);
	     serversList.addItem("Server Port "+i);
		  try {
			  connectButtonActionListener();
			  stopButtonActionListener();
		  }catch(Exception e) {
		    	//Put your error messages here.
		    	//when port and IP addresses are empty
		    	//When there is no active server
		    	//set error button and tooltip.
			  JOptionPane.showMessageDialog(null, "Connection Error!");
			}
	     
	     
	     
	     //buttonConnect.setVisible(false);
	     i++;
     }  
     //buttonConnect= new JButton("CONNECT");
     add(graphPanel, BorderLayout.NORTH);
     add(connectionPanel, BorderLayout.CENTER);
     //buttonPanel.add(buttonConnect);
     //buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
     //add(buttonPanel,BorderLayout.SOUTH);
     //buttonConnect.addActionListener(this);
     
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        shutdown();
        System.exit(0);
      }
    });
    
    
    //this.getContentPane().setLayout(new GridLayout(5,1));
    this.pack(); 
    this.setTitle("Multimodal Client Connector");
    this.setSize(900, 700);
	this.setVisible(true);
    
  }
  
  private void stopButtonActionListener() {
	     buttonConnect1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 System.out.println("clossing ....... +++++++");
				 JButton conn = (JButton) e.getSource();
		 		  int val = Integer.parseInt(conn.getName());
				  subscriber[val-1].stop();
				  subscriber[val-1] = null;
				  connectList.get(val-1).setEnabled(true);
				  successList.get(val-1).setVisible(false);
				  stopList.get(val-1).setEnabled(false);
			}
	    	 
	     });
	
  }

private void connectButtonActionListener() {
		     buttonConnect.addActionListener(new ActionListener() {
		 			@Override
			 		public void actionPerformed(ActionEvent e) {
			 			// TODO Auto-generated method stub
			 			JButton conn = (JButton) e.getSource();
			 		    int val = Integer.parseInt(conn.getName());
			 		    System.out.println("Value of button:"+val);
			 		    /*if(subscriber[val-1] == null) {
			 		    	System.out.println("Subscribrt is null");
			 		    }else {
			 		    	System.out.println("Subscribrt is null not"+subscriber[val-1].getPort());
			 		    } */
			 		    String text= textFieldsList.get(val-1).getText();
			 		    if(!text.isEmpty() && !isInvalidPortNumber(text)) {
				 		    subscriber[val-1] = new Subscriber("localhost", Integer.parseInt(text));
			 		    }else {
			 				  JOptionPane.showMessageDialog(null, "Port number is empty or invalid!");
			 				  return;
			 		    }
			 		    subscriber[val-1].setServerPortActive("Server Port "+(val));
			 		    subscriber[val-1].setServerPortSelected("Server Port 1");
			 		    subscriber[val-1].setAttachedButtonVal(val-1);
			 		    service.submit(subscriber[val-1]);
			 		    subscriber[val-1].addObserver(thisClass);
			 		    conn.setEnabled(false);
			 			
			 		}
		     });
}

private boolean isInvalidPortNumber(String text) {
	int port = Integer.parseInt(text);
	boolean isInValid = false;
	if(port >=1024 && port <= 65535) {
	}else {
		isInValid = true;
	}
	return isInValid;
}

private ChartPanel createGraphPanel() {
      XYSeries series = new XYSeries("Data");
     XYSeriesCollection dataset = new XYSeriesCollection(series);
     new Timer(1000, new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
             series.add(series.getItemCount(), graphData);
             chart.setTitle(graphLabel);
         }
     }).start();
     chart = ChartFactory.createXYLineChart("Emotion", "Time",
         "Range", dataset, PlotOrientation.VERTICAL, false, false, false);
     plot = chart.getXYPlot();
     renderer.setSeriesPaint(0, Color.GREEN);
     renderer.setSeriesStroke(0, new BasicStroke(2.0f));
     plot.setBackgroundPaint(Color.BLACK);
     plot.setDomainGridlinesVisible(false);
     plot.setRenderer(renderer);
     
     return new ChartPanel(chart) {
		private static final long serialVersionUID = 4960544148862965383L;
		@Override
         public Dimension getPreferredSize() {
             return new Dimension(480, 240);
         }
     };
 }
    
  
    private void shutdown() {
    for(int i=0;i<5;i++)
    {
    	if(null != subscriber[i])
    		subscriber[i].stop();
    }
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
    	if(!data.isEmpty())
    		graphData = Double.parseDouble(data.split(",")[1].split("=")[1].replace("}", ""));
    	else
    		graphData = 0.0;
    	 System.out.println("Graph Data:"+graphData); 
         System.out.println(data + "\n" );
         //textArea.append();
    }else {
      buttonConnect.setEnabled(true);
    }    
  }

  public static void main(String[] args) {
	  thisClass = new ClientDemo();  
  }

}
