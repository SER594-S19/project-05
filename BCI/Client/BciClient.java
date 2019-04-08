package BCI.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BciClient extends JPanel {

    private List<SimulatorUI> uiSim = new ArrayList<>();
    
    
   
    public BciClient() {
    	this.setLayout(new GridLayout(5, 1));
        this.setBackground(Color.WHITE);
        
        for(int i=0 ;i<5;i++) {
            SimulatorUI uiElement = new SimulatorUI(new NewSubscriber("",-1));
            uiSim.add(uiElement);
            this.add(uiElement);
        }
    }

   
    private void shutdown() {
        for (SimulatorUI uiElement: uiSim) {
            uiElement.getSubscriber().stop();
            uiElement.getService().shutdown();
            try {
                if (!uiElement.getService().awaitTermination(10, TimeUnit.SECONDS)) {
                    uiElement.getService().shutdownNow();
                }
            } catch (InterruptedException ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
        }
    }

 
    public static void main(String[] args) throws IOException {
        JFrame clientFrame = new JFrame("BCI Client");
        clientFrame.getContentPane().setLayout(new GridLayout(1, 1));
        clientFrame.setLayout(new GridLayout(1, 2));
        BciClient bciClient=new BciClient();
        clientFrame.getContentPane().add(bciClient);
        PlotPanel p = PlotPanel.getIntance();
        clientFrame.add(p);
        p.repaint();
        clientFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                bciClient.shutdown();
                System.exit(0);
            }
        });
        clientFrame.pack();
        clientFrame.setVisible(true);
       
      
    }
}