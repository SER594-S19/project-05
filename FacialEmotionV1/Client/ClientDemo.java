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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ClientDemo extends JFrame implements Observer {

  private Subscriber  [] subscriber;
  private ExecutorService service;
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
	  
	  //Your tabbed panels go here.
	  JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	  tabbedPane.addTab("Mutimodal Client", mainPanel());
	  tabbedPane.addTab("Results", makePanel("Making Tab2"));

	  add(tabbedPane);
  }
  
  private static JPanel makePanel(String text) {
      JPanel resultsButtonPanel = new JPanel(new GridLayout(6,2));
      JPanel resultsPanel = new JPanel(new GridLayout(1,0));
      resultsPanel.setPreferredSize(new Dimension(100,100));

      JLabel loadLabel = new JLabel("Loading Results...");
      
      //resultsTabPanel.setPreferredSize(new Dimension(10,10));
      JButton showResults = new JButton("Generate Results");
      showResults.setPreferredSize(new Dimension(20,20));
      if(connectList.size() == 5) 
    	  showResults.setEnabled(true);
      else 
    	  showResults.setEnabled(false);
      
      showResults.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//Display Results graph
		    resultsPanel.add(loadLabel);
			loadLabel.setText("Loading Results...");
		    int result = AnalyzeData.runMachineLearningScript();
		    if(result == 0) {
			    resultsPanel.remove(loadLabel);
		    	showPredictionGraph(resultsPanel);
		    }else {
		    	loadLabel.setText("Error occured during analysis, please try later!");
		    }
			//JOptionPane.showMessageDialog(null, "Button clicked!");
		}
    	  
      });
      resultsButtonPanel.add(showResults);
      resultsButtonPanel.add(resultsPanel);
      return resultsButtonPanel;
  }
  
  private static void showPredictionGraph(JPanel resultsPanel) {
	  DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
	  	
	  	int[] data = getData();
	    // Population in 2005 
	  	System.out.println("Print Happy:"+data[0]);
	  	System.out.println("Print Happy:"+data[1]);
	  	System.out.println("Print Happy:"+data[2]);

	    dataset.addValue(data[0], "Happy", "Happy");  
	    dataset.addValue(data[1], "Neutral", "Neutral");  
	    dataset.addValue(data[2], "Sad", "Sad");  
	  
	    
	  JFreeChart chart=ChartFactory.createBarChart(  
		        "Emotion Prediction", //Chart Title  
		        "Emotion", // Category axis  
		        "Probability", // Value axis  
		        dataset,  
		        PlotOrientation.HORIZONTAL,  
		        true,true,false  
		       );  
	  
	  resultsPanel.add(new ChartPanel(chart) {
			private static final long serialVersionUID = 4960544148862965383L;
			@Override
	         public Dimension getPreferredSize() {
	             return new Dimension(1000, 1000);
	         }
	     },BorderLayout.CENTER);
  }
  
  private static int[] getData() {
	  BufferedReader reader;
	  int[] data = new int[4];
	  int total = 0;
		try {
			reader = new BufferedReader(new FileReader(
					"Client/Scripts/results.txt"));
			String line = reader.readLine();
			while (line != null) {
				if(line.equals("Happy"))
					data[0] += 1;
				else if(line.equals("Neutral"))
					data[1] += 1;
				else if(line.equals("Sad"))
					data[2] += 1;
				else {
					line = reader.readLine();
					continue;
				}
				
				total++;
				System.out.println(line);
				line = reader.readLine();
			}
			data[3] = total;
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return data;
}

private JPanel mainPanel() {

	  	JPanel mainTabPanel = new JPanel();
	  	mainTabPanel.setLayout(new GridLayout(2,0));


	    service = Executors.newCachedThreadPool();
	    
	    graphData = 0.0;
	    graphLabel = "Agreement";
	    renderer = new XYLineAndShapeRenderer();
	    textFieldsList = new ArrayList<JTextField>();
	    subscriber = new Subscriber[5];
	    successList = new ArrayList<JButton>();
	    connectList = new ArrayList<JButton>();
	    stopList = new ArrayList<JButton>();  
	    thisClass = this;
	    
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
		serversList.setPreferredSize(new Dimension(100,30));
		dropDownPanel.setPreferredSize(new Dimension(70,70));
		dropDownPanel.add(dropDownLabel);
		dropDownPanel.add(serversList);
		
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
				  JOptionPane.showMessageDialog(null, "Connection Error!");
			}
		    i++;
	     }  	     
	     
	     mainTabPanel.add(graphPanel,BorderLayout.NORTH);
	     mainTabPanel.add(connectionPanel, BorderLayout.CENTER);
	     
	    addWindowListener(new java.awt.event.WindowAdapter() {
	      @Override
	      public void windowClosing(java.awt.event.WindowEvent e) {
	        shutdown();
	        System.exit(0);
	      }
	    });
	    
	    this.pack(); 
	    this.setTitle("Multimodal Client Connector");
	    this.setSize(900, 700);
		this.setVisible(true);
		
		return mainTabPanel;
	  
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
			 			JButton conn = (JButton) e.getSource();
			 		    int val = Integer.parseInt(conn.getName());
			 		    System.out.println("Value of button:"+val);
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
	  new ClientDemo();
  }

}
