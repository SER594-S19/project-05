package facialgestures;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.*;

import Core.Publisher;

public class Gui extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static Model model;
	private final int PORT = 1598;
	protected JLabel labelPublishPort;
	private final JButton buttonConnect = new JButton("run");
	static String engageIcon = "Engagement";
	static String shortTermIcon = "Short Term Excitement";
	static String longTermIcon = "Long Term Excitement";
	static String meditateIcon = "Meditation";
	static String frustrateIcon = "Frustration";

	static String blinkTrue = "Blink_0";
	static String leftWinkTrue = "LWink_0";
	static String rightWinkTrue = "RWink_0";
	static String lookLeftTrue = "LookL_0";
	static String lookRightTrue = "LookR_0";

	static String leftSmirk = "Left Smirk";
	static String rightSmirk = "Right Smirk";
	static String raiseBrow = "Raise Brow";
	static String furrowBrow = "Furrow Brow";
	static String smile = "Smile";
	static String laugh = "Laugh";
	static String clench = "Clench";
	JLabel gifIcon;
	public int flag = 0;
	HashMap<String, Integer> listOfExpressions = new HashMap<>();
	ArrayList<Double> arrayList = new ArrayList<Double>();
	JSlider slider;
	DecimalFormat one = new DecimalFormat("#0.0");

	private Eye leftOfEye = new Eye(300 - 50, 110, 50, 20);
	private Eye rightOfEye = new Eye(300 + 58, 110, 50, 20);
	private VectorForEye position = new VectorForEye(300, 0);
	public int mode = 0;// 1, 2, 3, 4, 5
	public JPanel expressive_bin;
	public JPanel expressive_cont;
	
	private static class Line{
	    final int x1; 
	    final int y1;
	    final int x2;
	    final int y2;   
	    final Color color;

	public Line(int x1, int y1, int x2, int y2, Color color) {
	        this.x1 = x1;
	        this.y1 = y1;
	        this.x2 = x2;
	        this.y2 = y2;
	        this.color = color;
	    }               
	}
	public void clearLines() {
	    lines.clear();
	    repaint();
	}
	private final LinkedList<Line> lines = new LinkedList<Line>();
	
	public void addLine(int x1, int y1, int x2, int y2, Color color) { 
		lines.add(new Line(x1, y1, x2, y2, color));
	    repaint();
	}

	private Component createPanelSouth() {
		JPanel labels = new JPanel();
		labels.setBackground(Color.white);
	 	labels.add(new JLabel(" Publishing at port: ")); 
	 	labelPublishPort = new JLabel("" + PORT); 
	 	labels.add(labelPublishPort);
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.setBackground(Color.white);
		//panel.add(labels, BorderLayout.WEST); 
//		panel.add(new JLabel());
		panel.add(buttonConnect);
//		panel.add(new JLabel());
		buttonConnect.addActionListener(this);
		//buttonConnect.setPreferredSize(new Dimension(25, 30));
		buttonConnect.setEnabled(true);
		return panel;
	}

	public JRadioButton radio_button(String name, int y_axis) {
		JRadioButton radioButton = new JRadioButton(name);
		radioButton.setBounds(120, y_axis, 21, 23);
		return radioButton;
	}

	public JLabel label(String name, int x_axis, int y_axis) {
		JLabel label = new JLabel(name);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(x_axis, y_axis, 135, 25);
		return label;
	}

	public JSlider addSlider(JLabel labelNum, int x_axis, int y_axis, int val) {
	JSlider slider = new JSlider(0, 10, 5);
	slider.setMajorTickSpacing(5);  
	java.util.Hashtable<Integer,JLabel> labelTable = new java.util.Hashtable<Integer,JLabel>();
	labelTable.put(new Integer(0), new JLabel("0"));
    labelTable.put(new Integer(5), new JLabel("0.5"));
    labelTable.put(new Integer(10), new JLabel("1"));
    slider.setBackground(Color.white);
    slider.setLabelTable( labelTable );
	slider.setPaintLabels(true);  
	slider.setBounds(x_axis, y_axis, 180, 50);
	slider.setMaximum(10);
	slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double value = ((JSlider) e.getSource()).getValue() * 0.1;
				System.out.println(labelNum.getText());
			if (labelNum.getText()== "Left Smirk") {
				Gui.this.flag = 1;
					if (((JSlider) e.getSource()).getValue() <3) { 
						Gui.this.clearLines();
						Gui.this.addLine(x_axis + 186, y_axis + 25, x_axis + 203, y_axis + 25, Color.BLACK);
						}
					else if (((JSlider) e.getSource()).getValue() >= 3 && ((JSlider) e.getSource()).getValue() < 7) {
						Gui.this.clearLines();
						Gui.this.addLine(x_axis + 186, y_axis + 25, x_axis + 205, y_axis + 20, Color.BLACK);
						}
					else if (((JSlider) e.getSource()).getValue() >= 7){
						Gui.this.clearLines();
						Gui.this.addLine(x_axis + 186, y_axis + 25, x_axis + 215, y_axis + 20, Color.BLACK);
					}
					//labelNum.setText(String.valueOf(value));
					//labelNum.setVisible(true);
			}
			if (labelNum.getText()== "Right Smirk") {
				Gui.this.flag = 2;

				if (((JSlider) e.getSource()).getValue() <3) { 
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 16, x_axis + 203, y_axis + 16, Color.BLACK);
					}
				else if (((JSlider) e.getSource()).getValue() >= 3 && ((JSlider) e.getSource()).getValue() < 7) {
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 16, x_axis + 205, y_axis + 21, Color.BLACK);
					}
				else if (((JSlider) e.getSource()).getValue() > 7){
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 16, x_axis + 221, y_axis + 21, Color.BLACK);
					}
			}
			if (labelNum.getText()== "Raise Brow") {
				Gui.this.flag = 3;
				if (((JSlider) e.getSource()).getValue() <3) { 
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 20, x_axis + 199, y_axis + 20, Color.BLACK);
					Gui.this.addLine(x_axis + 206, y_axis + 20, x_axis + 219, y_axis + 20, Color.BLACK);
					}
				else if (((JSlider) e.getSource()).getValue() >=3 && ((JSlider) e.getSource()).getValue() < 7) {
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 19, x_axis + 199, y_axis + 22, Color.BLACK);
					Gui.this.addLine(x_axis + 206, y_axis + 22, x_axis + 219, y_axis + 19, Color.BLACK);
				}
				else if (((JSlider) e.getSource()).getValue() > 7){
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 16, x_axis + 200, y_axis + 22, Color.BLACK);
					Gui.this.addLine(x_axis + 208, y_axis + 22, x_axis + 222, y_axis + 16, Color.BLACK);
				}
			}
			if (labelNum.getText()== "Furrow Brow") {
				Gui.this.flag = 4;
				if (((JSlider) e.getSource()).getValue() <3) { 
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 20, x_axis + 199, y_axis + 20, Color.BLACK);
					Gui.this.addLine(x_axis + 206, y_axis + 20, x_axis + 219, y_axis + 20, Color.BLACK);
					}
				else if (((JSlider) e.getSource()).getValue() >=3 && ((JSlider) e.getSource()).getValue() < 7) {
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 22, x_axis + 199, y_axis + 19, Color.BLACK);
					Gui.this.addLine(x_axis + 206, y_axis + 19, x_axis + 219, y_axis + 22, Color.BLACK);
				}
				else if (((JSlider) e.getSource()).getValue() > 7){
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 24, x_axis + 200, y_axis + 18, Color.BLACK);
					Gui.this.addLine(x_axis + 208, y_axis + 18, x_axis + 222, y_axis + 24, Color.BLACK);
					}
			}
			if (labelNum.getText()== "Smile") {
				Gui.this.flag = 1;
				if (((JSlider) e.getSource()).getValue() <3) { 
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 25, x_axis + 203, y_axis + 25, Color.BLACK);
						}
				else if (((JSlider) e.getSource()).getValue() >= 3 && ((JSlider) e.getSource()).getValue() < 7) {
					Gui.this.flag = 5;
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 193, y_axis + 25, x_axis + 210, y_axis + 25, Color.BLACK);
						}
				else if (((JSlider) e.getSource()).getValue() >= 7){
					Gui.this.flag = 5;
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 190, y_axis + 25, x_axis + 215, y_axis + 25, Color.BLACK);
					}
				}
			if (labelNum.getText()== "Laugh") {
				Gui.this.flag = 1;
				if (((JSlider) e.getSource()).getValue() <3) { 
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 25, x_axis + 203, y_axis + 25, Color.BLACK);
						}
				else if (((JSlider) e.getSource()).getValue() >= 3 && ((JSlider) e.getSource()).getValue() < 7) {
					Gui.this.flag = 6;
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 193, y_axis + 25, x_axis + 206, y_axis + 25, Color.BLACK);
						}
				else if (((JSlider) e.getSource()).getValue() >= 7){
					Gui.this.flag = 9;
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 190, y_axis + 25, x_axis + 215, y_axis + 25, Color.BLACK);
					}
				}
			if (labelNum.getText()== "Clench") {
				if (((JSlider) e.getSource()).getValue() <3) { 
					Gui.this.flag = 1;
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 186, y_axis + 25, x_axis + 198, y_axis + 25, Color.BLACK);
					}
				else if (((JSlider) e.getSource()).getValue() >=3 && ((JSlider) e.getSource()).getValue() < 7) {
					Gui.this.flag = 7;
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 199, y_axis + 25, x_axis + 196, y_axis + 25, Color.BLACK);
				}
				else if (((JSlider) e.getSource()).getValue() > 7){
					Gui.this.flag = 8;
					Gui.this.clearLines();
					Gui.this.addLine(x_axis + 184, y_axis + 25, x_axis + 196, y_axis + 21, Color.BLACK);
				}
			}
				arrayList.set(5, value);
			}
			});
		return slider;
	}

	
	public JSlider addSlider_2(JLabel labelNum, int x_axis, int y_axis, int val) {
		JSlider slider = new JSlider(0, 10, 5); 
		slider.setMajorTickSpacing(5);  
		java.util.Hashtable<Integer,JLabel> labelTable = new java.util.Hashtable<Integer,JLabel>();
		labelTable.put(new Integer(0), new JLabel("0"));
	    labelTable.put(new Integer(5), new JLabel("0.5"));
	    labelTable.put(new Integer(10), new JLabel("1"));
	    slider.setLabelTable( labelTable );
		slider.setPaintLabels(true);  
		slider.setBounds(x_axis, y_axis, 180, 50);
		slider.setMaximum(10);
		slider.setBackground(Color.white);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double value = ((JSlider) e.getSource()).getValue() * 0.1;
				labelNum.setText(String.valueOf(one.format(value)));
				
			}
		});
		return slider;
	}

	public Gui() {
		expressionToIndexMapping();
		for(int i=0;i<17;i++)
			arrayList.add(0.0);
    model = new Model(new FacialDataGenerator(), new Publisher(PORT));
    this.setBackground(Color.WHITE);
    this.setLayout(new BorderLayout());
    this.add(createPanelSouth(), BorderLayout.SOUTH);
    Dimension screen = getToolkit().getScreenSize();
    this.setSize(screen.width / 2, 3 * screen.height / 4);
    this.setLocation((screen.width - getSize().width) / 2, (screen.height - getSize().height) / 2);
   Color eyeLidColor = new Color(255, 231, 226);
   Color eyeIrisColor = new Color(237,237,237);
    expressive_bin = new JPanel()
    		 {
				private static final long serialVersionUID = 1L;

					@Override
    		        public void paintComponent(Graphics g) {
    		            super.paintComponent(g);
    		            if (mode == 1) {
    						leftOfEye.draw(g, position, mode, false, eyeLidColor);
    						rightOfEye.draw(g, position, mode, false, eyeLidColor);
    					} else if (mode == 2) {
    						leftOfEye.draw(g, position, mode, false, eyeLidColor);
    						rightOfEye.draw(g, new VectorForEye(357, 100), mode, true, eyeIrisColor);
    					} else if (mode == 3) {
    						leftOfEye.draw(g, new VectorForEye(249, 100), mode, true, eyeIrisColor);
    						rightOfEye.draw(g, position, mode, false, eyeLidColor);
    					} else if (mode == 4) {
    						leftOfEye.draw(g, new VectorForEye(0, 0), mode, true, eyeIrisColor);
    						rightOfEye.draw(g, new VectorForEye(0, 0), mode, true,eyeIrisColor);
    					} else if (mode == 5) {
    						leftOfEye.draw(g, new VectorForEye(700, 0), mode, true, eyeIrisColor);
    						rightOfEye.draw(g, new VectorForEye(700, 0), mode, true,eyeIrisColor);
    					} else {
    						leftOfEye.draw(g, new VectorForEye(249.5, 99), mode, true, eyeIrisColor);
    						rightOfEye.draw(g, new VectorForEye(358, 99), mode, true, eyeIrisColor);
    					}
    		        }
    		    };
    expressive_bin.setBounds(0, 28, 331, 497);
    expressive_bin.setLayout(null);
    expressive_bin.setBackground(Color.white);
    
    JLabel lblBlink = label("Blink", 16, 31);
    expressive_bin.add(lblBlink);
    JLabel lblLeftWink = label("Left Wink", 16, 67);
    expressive_bin.add(lblLeftWink);
    JLabel lblRightWink = label("Right Wink", 16, 103);
    expressive_bin.add(lblRightWink);
    JLabel lblLookLeft = label("Look Left", 16, 139);
    expressive_bin.add(lblLookLeft);
    JLabel lblLookRight = label("Look Right", 16, 175);
    expressive_bin.add(lblLookRight);
 
    JRadioButton radioButtonBlink = radio_button("Blink_0", 33);
    expressive_bin.add(radioButtonBlink);
    radioButtonBlink.setBackground(Color.white);
    
    JRadioButton radioButtonLWink = radio_button("LWink_0", 68);
    expressive_bin.add(radioButtonLWink);
    radioButtonLWink.setBackground(Color.white);
    
    JRadioButton radioButtonRwink = radio_button("RWink_0", 104);
    expressive_bin.add(radioButtonRwink);
    radioButtonRwink.setBackground(Color.white);
    
    JRadioButton radioButtonLLeft = radio_button("LookL_0", 140);
    expressive_bin.add(radioButtonLLeft);
    radioButtonLLeft.setBackground(Color.white);
    
    JRadioButton radioButtonLRight = radio_button("LookR_0", 176);
    expressive_bin.add(radioButtonLRight);
    radioButtonLRight.setBackground(Color.white);
    
    ButtonGroup group1 = new ButtonGroup();
    group1.add(radioButtonBlink);
    group1.add(radioButtonLWink);
    group1.add(radioButtonRwink);
    group1.add(radioButtonLLeft);
    group1.add(radioButtonLRight);
    
    radioButtonBlink.addActionListener(this);
    radioButtonLWink.addActionListener(this);
    radioButtonRwink.addActionListener(this);
    radioButtonLLeft.addActionListener(this);
    radioButtonLRight.addActionListener(this);
    
    expressive_cont = new JPanel() {
		private static final long serialVersionUID = 1L;

		@Override
		
    	protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);
    	    	for (Line line : lines) {
    	    	g.setColor(line.color);
    	    	if(Gui.this.flag == 5) {
    	    		QuadCurve2D q = new QuadCurve2D.Float();
    	    		q.setCurve(line.x1, line.y1, 310, 190 , line.x2, line.y2);
    	    		g2.draw(q);
    	    	}
    	    	else if(Gui.this.flag == 6) {
    	    		g.drawLine(line.x1, line.y1, line.x2+5, line.y2);
    	    		QuadCurve2D q = new QuadCurve2D.Float();
    	    		q.setCurve(line.x1, line.y1, 310, 235 , line.x2+7, line.y2);
    	    		g2.draw(q);
    	    	}
    	    	else if(Gui.this.flag == 9) {
    	    		g.drawLine(line.x1, line.y1, line.x2+5, line.y2);
    	    		QuadCurve2D q = new QuadCurve2D.Float();
    	    		q.setCurve(line.x1, line.y1, 310, 240 , line.x2+5, line.y2);
    	    		g2.draw(q);
    	    	}
    	    	else if(Gui.this.flag==7) {
    	    		g2.draw(new RoundRectangle2D.Double(line.x1-3, line.y1, 18, 5, 10, 10));
    	    	}
    	    	else if(Gui.this.flag==8) {
    	    		g2.draw(new RoundRectangle2D.Double(line.x1+5, line.y1, 22, 5, 10, 10));
    	    	}
    	    	else
        	    	{
    	        g.drawLine(line.x1+5, line.y1, line.x2+5, line.y2);
    	    	}  
    	    }
    	 }
		};
    expressive_cont.setBounds(334, 28, 339, 507);
    expressive_cont.setLayout(null);
    expressive_cont.setBackground(Color.white);
    
    JLabel lblLeftSmirk = label("Left Smirk", 0, 25);
    expressive_cont.add(lblLeftSmirk);
    JLabel lblRightSmirk = label("Right Smirk", 0, 61);
    expressive_cont.add(lblRightSmirk);
    JLabel lblRaiseBurrow = label("Raise Brow", 0, 97);
    expressive_cont.add(lblRaiseBurrow);
    JLabel lblFurrowBrow = label("Furrow Brow", 0, 137);
    expressive_cont.add(lblFurrowBrow);
    JLabel lblSmile = label("Smile", 0, 173);
    expressive_cont.add(lblSmile);
    JLabel lblLaugh = label("Laugh", 0, 209);
    expressive_cont.add(lblLaugh);
    JLabel lblClench = label("Clench", 0, 243);
    expressive_cont.add(lblClench);
    
    JLabel num = label("",215, 25);
    num.setText("Left Smirk");
    num.setVisible(false);
    expressive_cont.add(num);
    JLabel num1 = label("", 215, 65);
    num1.setText("Right Smirk");
    num1.setVisible(false);
    expressive_cont.add(num1);
    JLabel num2 = label("", 215, 100);
    num2.setText("Raise Brow");
    num2.setVisible(false);
    expressive_cont.add(num2);
    JLabel num3 = label("", 215, 135);
    num3.setText("Furrow Brow");
    num3.setVisible(false);
    expressive_cont.add(num3);
    JLabel num4 = label("", 215, 170);
    num4.setText("Smile");
    num4.setVisible(false);
    expressive_cont.add(num4);
    JLabel num5 = label("", 215, 200);
    num5.setText("Laugh");
    num5.setVisible(false);
    expressive_cont.add(num5);
    JLabel num6 = label("", 215, 240);
    num6.setText("Clench");
    num6.setVisible(false);
    expressive_cont.add(num6);
    
//  Adding Sliders
    
    JSlider slider = addSlider(num, 107, 15, 5);
    JSlider slider_1 = addSlider(num1, 107, 55, 6);
    JSlider slider_2 = addSlider(num2, 107, 90, 7);
    JSlider slider_3 = addSlider(num3, 107, 125, 8);
    JSlider slider_4 = addSlider(num4, 107, 160, 9);
    JSlider slider_5 = addSlider(num5, 107, 195, 10);
    JSlider slider_6 = addSlider(num6, 107, 230, 11);

    expressive_cont.add(slider);
    expressive_cont.add(slider_1);
    expressive_cont.add(slider_2);    
    expressive_cont.add(slider_3);
    expressive_cont.add(slider_4);
    expressive_cont.add(slider_5);
    expressive_cont.add(slider_6);
	
//    Panel 3 : Affective Panel starts from here
    JPanel affective = new JPanel();
    affective.setBounds(334, 28, 339, 497);
    affective.setLayout(null);
    setBorder(BorderFactory.createEmptyBorder(15,15,15,15));  
    affective.setBackground(Color.white);
    
    JLabel engage = label("Engagement", 16, 35);
    affective.add(engage);
    JLabel sExcite = label("Short Term Excitement", 16, 80);
    affective.add(sExcite);
    JLabel lExcite = label("Long Term Excitement", 16, 125);
    affective.add(lExcite);
    JLabel med = label("Meditation", 16, 170);
    affective.add(med);
    JLabel fru = label("Frustration", 16, 215);
    affective.add(fru);
  
    JLabel lbl_en = label("0.5", 280, 35);
    affective.add(lbl_en);
    JLabel lbl_st = label("0.5", 280, 80);
    affective.add(lbl_st);
    JLabel lbl_lt = label("0.5", 280, 125);
    affective.add(lbl_lt);
    JLabel lbl_me = label("0.5", 280, 170);
    affective.add(lbl_me);
    JLabel lbl_frus = label("0.5", 280, 215);
    affective.add(lbl_frus);
    
    JSlider slider_en = addSlider_2(lbl_en, 150, 30, 10);
    JSlider slider_st = addSlider_2(lbl_st, 150, 75, 11);
    JSlider slider_lt = addSlider_2(lbl_lt, 150, 120, 12);
    JSlider slider_me = addSlider_2(lbl_me, 150, 165, 13);
    JSlider slider_frus = addSlider_2(lbl_frus, 150, 210, 14);
    
    affective.add(slider_en);
    affective.add(slider_st);
    affective.add(slider_lt);    
    affective.add(slider_me);
    affective.add(slider_frus);
    
    expressive_cont.add(slider);
    expressive_cont.add(slider_1);
    expressive_cont.add(slider_2);    
    expressive_cont.add(slider_3);
    expressive_cont.add(slider_4);
    expressive_cont.add(slider_5);
    expressive_cont.add(slider_6);

//	gifIcon = new JLabel(createImageIcon("" + ".gif"));
//	gifIcon.setBounds(197, 30, 280, 200);
//	affective.add(gifIcon, BorderLayout.CENTER);
   
    JTabbedPane tp= new JTabbedPane();  
    tp.setBounds(50,50,200,200);  
    tp.add("Expressive I",expressive_bin);  
    tp.add("Expressive II",expressive_cont);  
    tp.add("Affective",affective);  
    JPanel lablePanel = new JPanel(new GridLayout(1,3));
    lablePanel.setBackground(Color.white);
	//panel.add(labels, BorderLayout.WEST); 
    lablePanel.add(new JLabel());
    
//	lablePanel.add(buttonConnect);
	lablePanel.add(new JLabel("Publishing at Port: " + PORT));
	lablePanel.add(new JLabel());
	add(lablePanel, BorderLayout.NORTH);
    add(tp);
    System.out.println("gui done");
  }

	private void expressionToIndexMapping() {
		listOfExpressions.put(blinkTrue, 0);
		listOfExpressions.put(leftWinkTrue, 1);
		listOfExpressions.put(rightWinkTrue, 2);
		listOfExpressions.put(lookLeftTrue, 3);
		listOfExpressions.put(lookRightTrue, 4);
		listOfExpressions.put(leftSmirk, 5);
		listOfExpressions.put(rightSmirk, 6);
		listOfExpressions.put(raiseBrow, 7);
		listOfExpressions.put(furrowBrow, 8);
		listOfExpressions.put(smile, 9);
		listOfExpressions.put(laugh, 10);
		listOfExpressions.put(clench, 11);
		listOfExpressions.put(engageIcon, 12);
		listOfExpressions.put(shortTermIcon, 13);
		listOfExpressions.put(longTermIcon, 14);
		listOfExpressions.put(meditateIcon, 15);
		listOfExpressions.put(frustrateIcon, 16);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		gifIcon.setIcon(createImageIcon(e.getActionCommand() + ".gif"));

		System.out.println(e.getActionCommand());
		System.out.println("listener trigger");

		for (int i = 0; i < 17; i++) {
			arrayList.add(0.0);
		}

		setExpressiveBinary(e, arrayList);
		setAffective(e, arrayList);
		model.setFacialValues(arrayList);

		if (e.getSource() == buttonConnect) {
			if (buttonConnect.getText().compareTo("run") == 0) {
				System.out.println("start");
				model.setFacialValues(arrayList);
				model.start();

				buttonConnect.setText("stop");
			} else if (buttonConnect.getText().compareTo("stop") == 0) {
				System.out.println("stop");
				model.stop();
				buttonConnect.setText("run");
			}
		}
	}

	private void setAffective(ActionEvent e, ArrayList<Double> arrayList) {
		if (e.getActionCommand().equals(engageIcon)) {
			arrayList.set(listOfExpressions.get(engageIcon), (double) 1);
		} else if (e.getActionCommand().equals(shortTermIcon)) {
			arrayList.set(listOfExpressions.get(shortTermIcon), (double) 1);
		} else if (e.getActionCommand().equals(longTermIcon)) {
			arrayList.set(listOfExpressions.get(longTermIcon), (double) 1);
		} else if (e.getActionCommand().equals(meditateIcon)) {
			arrayList.set(listOfExpressions.get(meditateIcon), (double) 1);
		} else if (e.getActionCommand().equals(frustrateIcon)) {
			arrayList.set(listOfExpressions.get(frustrateIcon), (double) 1);
		}
	}

	private void setExpressiveBinary(ActionEvent e, ArrayList<Double> arrayList) {
		if (e.getActionCommand().equals(blinkTrue)) {
			this.mode = 1;
			arrayList.set(listOfExpressions.get(blinkTrue), (double) 1);
			this.expressive_bin.repaint();
			new Thread(new Runnable() {
				public void run() {
					if (Gui.this.mode != 1) {
						return;
					}
					while (true) {
						if (Gui.this.mode != 1) {
							return;
						}
						try {

							Thread.sleep(750);
							if (Gui.this.mode != 1) {
								return;
							}
							Gui.this.mode = 0;
							Gui.this.expressive_bin.repaint();
						} catch (InterruptedException e) {

							e.printStackTrace();
						}

					}
				}
			}).start();
		}
		if (e.getActionCommand().equals(leftWinkTrue)) {
			this.mode = 2;
			arrayList.set(listOfExpressions.get(leftWinkTrue), (double) 1);
			this.expressive_bin.repaint();
			new Thread(new Runnable() {
				public void run() {
					if (Gui.this.mode != 2) {
						return;
					}
					while (true) {
						if (Gui.this.mode != 2) {
							return;
						}
						try {

							Thread.sleep(750);
							if (Gui.this.mode != 2) {
								return;
							}
							Gui.this.mode = 0;
							Gui.this.expressive_bin.repaint();
						} catch (InterruptedException e) {

							e.printStackTrace();
						}

					}
				}
			}).start();
		}
		if (e.getActionCommand().equals(rightWinkTrue)) {
			this.mode = 3;
			arrayList.set(listOfExpressions.get(rightWinkTrue), (double) 1);
			this.expressive_bin.repaint();
			new Thread(new Runnable() {
				public void run() {
					if (Gui.this.mode != 3) {
						return;
					}
					while (true) {
						if (Gui.this.mode != 3) {
							return;
						}
						try {

							Thread.sleep(750);
							if (Gui.this.mode != 3) {
								return;
							}
							Gui.this.mode = 0;
							Gui.this.expressive_bin.repaint();
						} catch (InterruptedException e) {

							e.printStackTrace();
						}

					}
				}
			}).start();
		}
		if (e.getActionCommand().equals(lookLeftTrue)) {
			this.mode = 4;
			arrayList.set(listOfExpressions.get(lookLeftTrue), (double) 1);
			this.expressive_bin.repaint();
		}
		if (e.getActionCommand().equals(lookRightTrue)) {
			this.mode = 5;
			arrayList.set(listOfExpressions.get(lookRightTrue), (double) 1);
			this.expressive_bin.repaint();
		}
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL gifURL = Gui.class.getResource(path);
		if (gifURL != null) {
			return new ImageIcon(gifURL);
		} else {
			System.err.println("");
			return null;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Simulator11");
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
		frame.setSize(500, 450);
		frame.setVisible(true);
	}
}
