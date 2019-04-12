package Core;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javafx.scene.text.Text;
import java.awt.*;
import java.util.Calendar;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**This runs the GUI simulator for Eye Tracking Device
 *  The simulator shows movement of eyes and validation scroller value from 0 - 4
 * @author Bharat Goel
 */
public class Gui extends JPanel implements ActionListener {

  private static Model model;
  private final int PORT = 1594;
  protected JLabel labelPublishPort = new JLabel("" + PORT);;
  private final JButton buttonConnect = new JButton("Run");
  static JFrame frame = new JFrame();
  private Scale mousePosition = new Scale(0, 0);
  private int validation;
  private long pupily;
  private long pupilx;
  static long timeStamp = 0;
  private long fixation;
  private long aoi=0;
  static Data data;
  JPanel panels = new JPanel();
  JLabel panelLabel = new JLabel("  Publishing at port: ");
  JLabel valLabel = new JLabel(" Validation Points: ");
  JLabel pupilxLabel = new JLabel(" PupilLeft: ");
  JLabel pupilyLabel = new JLabel(" PupilRight: ");
  private Eye leftEye = new Eye(398 - 140, 340, 100, 30);
  private Eye rightEye = new Eye(398 + 150, 340, 100, 30);
  private Font font = new Font("Arial", Font.PLAIN, 50);
  private Font font2 = new Font("Arial", Font.PLAIN, 150);
  final DecimalFormat df = new DecimalFormat("0.####");
  static JSlider Validation = new JSlider();
  final static JTextField text1 = new JTextField(50);
  final static JTextField text2 = new JTextField(50);
  final static pupilControl Pupilx = new pupilControl(0, 5000, 0, 1000);
  final static pupilControl Pupily = new pupilControl(0, 5000, 0, 1000);
  HashMap<Set<Integer>,Long> grid = new HashMap<>();

  public Gui() {

    model = new Model(new DataGenerator(), new Publisher(PORT));
    panels.setBackground(Color.WHITE);  
    panelLabel.setFont(new Font("Courier",Font.BOLD, 20));
    panels.add(panelLabel);    
    labelPublishPort.setFont(new Font("Courier",Font.BOLD, 20));
    panels.add(labelPublishPort);
   
    setPreferredSize(new Dimension(800, 600));
    addMouseMotionListener(new MouseHandler());
    setLayout(null);
    this.setBackground(new Color(255,255,153));
    //this.requestFocus();
    Validation.setMaximum(4);
    Validation.setMajorTickSpacing(1);
    Validation.setPaintTicks(true);
    Validation.setValue(0);
    Validation.setToolTipText("Validation Points");
    Validation.setForeground(Color.WHITE);
    Validation.setBackground(new Color(255,192,203));
    Validation.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
    Validation.setPaintLabels(true);
    Validation.addChangeListener(new ChangeListener(){ public void stateChanged(ChangeEvent e) {
        validation = Validation.getValue(); }});
    
    Pupilx.setValue(2500);
    Pupilx.setLocation(20, 20);
    Pupilx.setBounds(20, 20, 30, 100);
    Pupilx.setFont(new Font("Calibri", Font.BOLD, 20));
    Pupilx.setToolTipText("PUPIL RIGHT");
    Pupilx.setForeground(Color.WHITE);
    Pupilx.setBackground(new Color(105,105,105));
    Pupilx.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
    Pupilx.setPaintLabels(true);
    Pupilx.setPaintTicks(true);
    Pupilx.setPreferredSize(new Dimension(150,150));
    Pupilx.addChangeListener(new ChangeListener(){
        @Override
        public void stateChanged(ChangeEvent e) {
            text1.setText(df.format(Pupilx.getScaledValue()));
            pupily = (long) Pupilx.getScaledValue();
            rightEye.setR2(pupily*15);
            repaint();
        }
    });
    text1.addKeyListener(new KeyAdapter(){
        @Override
        public void keyReleased(KeyEvent ke) {
            String typed = text1.getText();
            Pupilx.setValue(0);
            if(!typed.matches("\\d+(\\.\\d*)?")) {
                return;
            }
            double value = Double.parseDouble(typed)*Pupilx.scale;
            Pupilx.setValue((int)value);
        }
    });
    
    Pupily.setValue(2500);
    Pupily.setFont(new Font("Calibri", Font.BOLD, 16));
    Pupily.setToolTipText("PUPIL LEFT");
    Pupily.setForeground(Color.WHITE);
    Pupily.setBackground(new Color(105,105,105));
    Pupily.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
    Pupily.setPaintLabels(true);
    Pupily.setPaintTicks(true);
    Pupily.setPreferredSize(new Dimension(150,150));
    Pupily.addChangeListener(new ChangeListener(){
        @Override
        public void stateChanged(ChangeEvent e) {
            text2.setText(df.format(Pupily.getScaledValue()));
            pupilx = (long) Pupily.getScaledValue();
            leftEye.setR2(pupilx*15);
            repaint();
        }
    });
    text2.addKeyListener(new KeyAdapter(){
        @Override
        public void keyReleased(KeyEvent ke) {
            String typed = text2.getText();
            Pupily.setValue(0);
            if(!typed.matches("\\d+(\\.\\d*)?")) {
                return;
            }
            double value = Double.parseDouble(typed)*Pupily.scale;
            Pupily.setValue((int)value);
        }
    });
    
    Validation.setBounds(260, 560, 300, 100);
    Pupily.setBounds(160, 140, 200, 70);
    Pupilx.setBounds(440, 140, 200, 70);
    panels.setBounds(1, 770, 600, 40);
    text2.setFont(new Font("Calibri", Font.BOLD, 20));
    text2.setBounds(160, 110, 200, 30);
    text1.setFont(new Font("Calibri", Font.BOLD, 20));
    text1.setBounds(440, 110, 200, 30);
    valLabel.setFont(new Font("Calibri", Font.BOLD, 25));
    valLabel.setBounds(318, 557, 200, 40);
    valLabel.setForeground(Color.WHITE);
    pupilxLabel.setFont(new Font("Calibri", Font.BOLD, 20));
    pupilxLabel.setBounds(215, 173, 200, 40);
    pupilxLabel.setForeground(Color.WHITE);
    pupilyLabel.setFont(new Font("Calibri", Font.BOLD, 20));
    pupilyLabel.setBounds(490, 173, 200, 40);
    pupilyLabel.setForeground(Color.WHITE);
    buttonConnect.setBackground(new Color(255,255,254));
    buttonConnect.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
    buttonConnect.setForeground(Color.BLACK);
    buttonConnect.setFont(new Font("Courier",Font.BOLD, 30));
    buttonConnect.setBounds(601,770,193,40);
    buttonConnect.addActionListener(this);
    buttonConnect.setEnabled(true);
    this.add(valLabel);
    this.add(pupilxLabel);
    this.add(pupilyLabel);
    this.add(text1);
    this.add(text2);
    this.add(Pupilx);
    this.add(Pupily);   
    this.add(Validation);  
    this.add(panels);    
    this.add(buttonConnect);

    return;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    leftEye.draw(g, mousePosition);
    rightEye.draw(g, mousePosition);
  }


  private class MouseHandler extends MouseAdapter {

    @Override
    public void mouseMoved(MouseEvent e) {
      mousePosition.setX(e.getX());
      mousePosition.setY(e.getY());
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      long initialTime = calendar.getTimeInMillis();
      long currentTimeStamp = (long)((System.currentTimeMillis() - initialTime) * .001);
      fixation= currentTimeStamp-timeStamp;
      timeStamp = (long)((System.currentTimeMillis() - initialTime) * .001);
      aoi=calculateAoi(e.getX(),e.getY(),fixation);
      data=new Data(timeStamp,e.getX(),e.getY(),pupily,pupilx,validation,fixation,aoi);
      repaint();
    }

      public long calculateAoi(int x,int y,long fixation){
        long value;
        long max=0;
        Set<Integer> pixel=new HashSet<>();
          pixel.add(x);
          pixel.add(y);
          if(grid.containsKey(pixel)){
              value=grid.get(pixel)+fixation;
              if(value>max)
                  max=value;
              grid.put(pixel,value);
          }
          else{
              grid.put(pixel,fixation);
              if(fixation>max)
                  max=fixation;
          }
          return max;
      }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("listener trigger");
    if (e.getSource() == buttonConnect) {
      if (buttonConnect.getText().compareTo("Run") == 0) {
        System.out.println("Start");
        model.start();
        buttonConnect.setText("Stop");
      }else if (buttonConnect.getText().compareTo("Stop") == 0) {
        System.out.println("Stop");
        model.stop();
        buttonConnect.setText("Run");
      }
    }
  }

  public static Data getData(){
    return data;

  }
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Gui gui = new Gui();
        Container pane = frame.getContentPane();
        pane.add(gui);
        frame.setTitle("Eye Tracking Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBounds(500, 90, 800, 850);
        gui.requestFocus();
      }
    });
  }
}

class pupilControl extends JSlider {

    final int scale;

    public pupilControl(int min, int max, int value, int scale) {
        super(min, max, value);
        this.scale = scale;
    }

    public double getScaledValue() {
        return ((double)super.getValue()) / this.scale;
    }
}
