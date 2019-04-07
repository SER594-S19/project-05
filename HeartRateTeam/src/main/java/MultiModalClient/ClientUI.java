package MultiModalClient;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import MultiModalClient.ClientSubscriber;
import MultiModalClient.UIElement;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to design the Client UI for getting data from different servers
 *
 * @version 20190213
 *
 */


public class ClientUI extends JPanel {

    private static ClientUI instance = null;
    private List<UIElement> simulators = new ArrayList<>();
    public GraphModel graphModel;
    public Graph graph;
    public PredictData predictData;
    public MultiLayerNetwork model;
    private final JPanel gifPanel = new JPanel();
    
	
    
    String[] colors= {"#0d3d56","#4a6b7c","#0d3d56","#4a6b7c","#0d3d56"};
    // Method used to get instance of the Client UI class
    public static ClientUI getInstance() {
        if (instance == null)
            instance = new ClientUI();

        return instance;
    }

    // Constructor for creating the client UI to collect data from servers
    private ClientUI() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(600, 500));
        this.setLayout(new GridLayout(2, 2));

        for(int i=0 ;i<2;i++) {
            UIElement uiElement = new UIElement(new ClientSubscriber("",-1),colors[0]);
            simulators.add(uiElement);
            this.add(uiElement);
        }
        graphModel = new GraphModel();
        graph = new Graph(graphModel);
        this.add(graph);
        graph.initializeView();
        ImageIcon ii = new ImageIcon(this.getClass().getClassLoader().getResource("neutral.png"));
       
        JLabel imageLabel = new JLabel();
        //imageLabel.setPreferredSize(new Dimension(50, 50));
        imageLabel.setIcon(ii);
        gifPanel.add(imageLabel);
        //gifPanel.setPreferredSize(new Dimension(15, 15));
        gifPanel.setBackground(Color.WHITE);
        this.add(gifPanel);

        predictData = new PredictData();
        try {
            loadModel();
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }

    public void loadModel() throws IOException {
        File location = new File("model.zip");
        model = ModelSerializer.restoreMultiLayerNetwork(location);
    }
    
    public  void updateSmileImage(String image) {
		ImageIcon ii = new ImageIcon(this.getClass().getClassLoader().getResource(image));
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(ii);
		gifPanel.removeAll();
		gifPanel.add(imageLabel);
		this.getParent().revalidate();
		this.getParent().repaint();
	}


    // Method called when the application is shut down
    private void shutdown() {
        for (UIElement uiElement: simulators) {
            uiElement.getSubscriber().stop();
            uiElement.getService().shutdown();
            try {
                if (!uiElement.getService().awaitTermination(10, TimeUnit.SECONDS)) {
                    uiElement.getService().shutdownNow();
                }
            } catch (InterruptedException ex) {
                System.out.println("Exception: " + ex);
            }
        }
    }

    // Method to run the Client UI application
    public static void main(String[] args) {
        JFrame frame = new JFrame("Client");
        frame.getContentPane().setLayout(new GridLayout(1, 1));
        frame.setLayout(new GridLayout(1, 1));
        frame.getContentPane().add(ClientUI.getInstance());
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ClientUI.getInstance().shutdown();
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}