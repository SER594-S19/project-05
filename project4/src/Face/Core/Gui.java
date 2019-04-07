package Face.Core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Gui extends JPanel implements ActionListener {

private static final long serialVersionUID = 1L;

private static Model model;
  
  static String agreementString = "Agreement";
  static String concentratingString = "Concentrating";
  static String disagreementString = "Disagreement";
  static String interestedString = "Interested";
  static String thinkingString = "Thinking";
  static String unsureString = "Unsure";
  private final int PORT = 1597;
  protected JLabel labelPublishPort;
  private final JButton buttonConnect = new JButton("RUN");
  private static String setEmotion = "Agreement";
  
  JLabel moodGif;

  private Component createBottomPanel() {

    JPanel portNumber = new JPanel();
    portNumber.setBackground(Color.RED);
    portNumber.add(new JLabel("  Publishing at port: "));
    labelPublishPort = new JLabel("" + PORT);
    portNumber.add(labelPublishPort);
    
    JRadioButton agreementButton = new JRadioButton(agreementString);
    agreementButton.setActionCommand(agreementString);
    agreementButton.setSelected(true);

    JRadioButton concentratingButton = new JRadioButton(concentratingString);
    concentratingButton.setActionCommand(concentratingString);
    
    JRadioButton disagreementButton = new JRadioButton(disagreementString);
    disagreementButton.setActionCommand(disagreementString);
    
    JRadioButton interestedButton = new JRadioButton(interestedString);
    interestedButton.setActionCommand(interestedString);
    
    JRadioButton thinkingButton = new JRadioButton(thinkingString);
    thinkingButton.setActionCommand(thinkingString);
    
    JRadioButton unsureButton = new JRadioButton(unsureString);
    unsureButton.setActionCommand(unsureString);
    
    ButtonGroup selectMood = new ButtonGroup();
    selectMood.add(agreementButton);
    selectMood.add(concentratingButton);
    selectMood.add(disagreementButton);
    selectMood.add(interestedButton);
    selectMood.add(thinkingButton);
    selectMood.add(unsureButton);
    
    agreementButton.addActionListener(this);
    concentratingButton.addActionListener(this);
    disagreementButton.addActionListener(this);
    interestedButton.addActionListener(this);
    thinkingButton.addActionListener(this);
    unsureButton.addActionListener(this);
    
    moodGif = new JLabel(createImageIcon(agreementString
            + ".gif"));
    
    moodGif.setPreferredSize(new Dimension(177, 122));
    
    JPanel moodPanel = new JPanel(new GridLayout(0, 1));
    moodPanel.add(agreementButton);
    moodPanel.add(concentratingButton);
    moodPanel.add(disagreementButton);
    moodPanel.add(interestedButton);
    moodPanel.add(thinkingButton);
    moodPanel.add(unsureButton);
    
    add(moodPanel, BorderLayout.LINE_START);
    add(moodGif, BorderLayout.CENTER);
    setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(portNumber, BorderLayout.WEST);
    bottomPanel.add(buttonConnect, BorderLayout.EAST);
    buttonConnect.addActionListener(this);
    buttonConnect.setEnabled(true);
    return bottomPanel;  
    
  }

  public Gui() {

    model = new Model(new DataGenerator(), new Publisher(PORT));
    this.setBackground(Color.WHITE);
    this.setLayout(new BorderLayout());
    this.add(createBottomPanel(), BorderLayout.SOUTH);
    Dimension screen = getToolkit().getScreenSize();
    this.setSize(screen.width / 2, 3 * screen.height / 4);
    this.setLocation((screen.width - getSize().width) / 2, (screen.height - getSize().height) / 2);
    System.out.println("gui done");
  }

  @Override
  public void actionPerformed(ActionEvent e) { 
	String emotion = e.getActionCommand();
	if(!emotion.equals("RUN")) {
		setEmotion = emotion;
		moodGif.setIcon(createImageIcon(e.getActionCommand()
              + ".gif"));
	}
	  
    System.out.println("listener trigger");
    if (e.getSource() == buttonConnect) {
      if (buttonConnect.getText().compareTo("RUN") == 0) {
            System.out.println("start");
        model.start(setEmotion);
        buttonConnect.setText("STOP");
      } else if (buttonConnect.getText().compareTo("STOP") == 0) {
                    System.out.println("stop");
        model.stop();
        buttonConnect.setText("RUN");
      }
    }
  }
  
  protected static ImageIcon createImageIcon(String path) {
      java.net.URL gifURL = Gui.class.getResource(path);
      if (gifURL != null) {
          return new ImageIcon(gifURL);
      } else {
          System.err.println("Cannot find : " + path);
          return null;
      }
  }
  
  
  public static void main(String[] args) {

    JFrame frame = new JFrame("Face Detection Simulator");
    frame.setLayout(new GridLayout(1, 1));
    frame.add(new Gui());
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        model.shutdown();
        System.exit(0);
      }
    });
    frame.pack();
    frame.setSize(500, 300);
    frame.setVisible(true);
    
   
  }
  
}
