package BCI.Core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BCI.View.*;

import java.awt.FlowLayout;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Gui extends JFrame implements ActionListener{

	private JPanel contentPane;
	private static Model model;

	private final int PORT = 1594;
	protected JLabel labelPublishPort;
	private static DataGenerator dataGenerator =new DataGenerator();
	public static List<JLabel> channelLabels=new ArrayList<>(); 
	public static List<JButton> channelButtons = new ArrayList<>();
	
	JButton btnNewButton = new JButton("Start");
	JLabel brainLabel = new JLabel("");
	JLabel portLabel = new JLabel("Publishing at port:"+ PORT);
	BCI.View.Expressions expressivePanel=new BCI.View.Expressions(dataGenerator);
	public static Affective affectivePanel = new Affective(dataGenerator);
	private final ButtonGroup settingButtonGroup = new ButtonGroup();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
					frame.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent e) {
							model.shutdown();
							System.exit(0);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Gui() throws IOException {
		setTitle("BCI Simulator"); 
		setResizable(false);
		JPanel brainImagePanel = new JPanel(); 
		model = new Model(dataGenerator, new Publisher(PORT)); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1398, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.decode("#4267b2"));
		topPanel.setBounds(0, 0, 1389, 75); 
		contentPane.add(topPanel);
		topPanel.setLayout(null);

		
		portLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		portLabel.setForeground(Color.WHITE);
		portLabel.setBounds(1011, 16, 225, 32);
		portLabel.setVisible(false);
		topPanel.add(portLabel);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.decode("#79bc64"));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));

		btnNewButton.setToolTipText("");
		btnNewButton.addActionListener(this);

		btnNewButton.setIcon(null);
		btnNewButton.setBounds(1251, 0, 138, 75);
		topPanel.add(btnNewButton);
		

		brainImagePanel.setBackground(Color.WHITE);
		brainImagePanel.setBounds(0, 75, 690, 725);
		contentPane.add(brainImagePanel);
		brainImagePanel.setLayout(null);

		JLabel ch1 = new JLabel("1");
		ch1.setBounds(80, 101, 43, 20);
		brainImagePanel.add(ch1);
		channelLabels.add(ch1);

		JLabel ch2Label = new JLabel("2");
		ch2Label.setBounds(380, 101, 33, 20);
		brainImagePanel.add(ch2Label);
		channelLabels.add(ch2Label);

		JLabel ch3Label = new JLabel("3");
		ch3Label.setBounds(46, 167, 69, 20);
		brainImagePanel.add(ch3Label);
		channelLabels.add(ch3Label);

		JLabel ch4Label = new JLabel("4");
		ch4Label.setBounds(429, 167, 69, 20);
		brainImagePanel.add(ch4Label);
		channelLabels.add(ch4Label);

		JLabel ch5Label = new JLabel("5");
		ch5Label.setBounds(36, 241, 69, 20);
		brainImagePanel.add(ch5Label);
		channelLabels.add(ch5Label);

		JLabel ch6Label = new JLabel("6");
		ch6Label.setBounds(451, 241, 99, 20);
		brainImagePanel.add(ch6Label);
		channelLabels.add(ch6Label);

		JLabel ch7Label = new JLabel("7");
		ch7Label.setBounds(15, 314, 69, 20);
		brainImagePanel.add(ch7Label);
		channelLabels.add(ch7Label);

		JLabel label_4 = new JLabel("8");
		label_4.setBounds(470, 314, 69, 20);
		brainImagePanel.add(label_4);
		channelLabels.add(label_4);

		JLabel label = new JLabel("9");
		label.setBounds(15, 350, 69, 20);
		brainImagePanel.add(label);
		channelLabels.add(label);

		JLabel label_1 = new JLabel("11");
		label_1.setBounds(15, 432, 69, 20);
		brainImagePanel.add(label_1);
		channelLabels.add(label_1);

		JLabel label_2 = new JLabel("13");
		label_2.setBounds(15, 497, 69, 20);
		brainImagePanel.add(label_2);
		channelLabels.add(label_2);

		JLabel label_3 = new JLabel("10");
		label_3.setBounds(480, 350, 99, 20);
		brainImagePanel.add(label_3);
		channelLabels.add(label_3);

		JLabel label_5 = new JLabel("12");
		label_5.setBounds(451, 432, 69, 20);
		brainImagePanel.add(label_5);
		channelLabels.add(label_5);

		JLabel label_6 = new JLabel("14");
		label_6.setBounds(451, 497, 69, 20);
		brainImagePanel.add(label_6);
		channelLabels.add(label_6);

		JButton color1Label = new JButton("");
		color1Label.setBackground(Color.GREEN);
		color1Label.setBounds(192, 101, 13, 12);
		color1Label.setBorder(new RoundedBorder(20)); 
		brainImagePanel.add(color1Label);
		channelButtons.add(color1Label);

		JButton button = new JButton("");
		button.setBorder(new RoundedBorder(20));
		button.setBackground(Color.GREEN);
		button.setBounds(282, 97, 13, 12);
		brainImagePanel.add(button);
		channelButtons.add(button);

		JButton button_1 = new JButton("");
		button_1.setBorder(new RoundedBorder(20));
		button_1.setBackground(Color.GREEN);
		button_1.setBounds(146, 163, 13, 12);
		brainImagePanel.add(button_1);
		channelButtons.add(button_1);

		JButton button_2 = new JButton("");
		button_2.setBorder(new RoundedBorder(20));
		button_2.setBackground(Color.GREEN);
		button_2.setBounds(316, 163, 13, 12);
		brainImagePanel.add(button_2);
		channelButtons.add(button_2);

		JButton button_3 = new JButton("");
		button_3.setBorder(new RoundedBorder(20));
		button_3.setBackground(Color.GREEN);
		button_3.setBounds(120, 237, 13, 12);
		brainImagePanel.add(button_3);
		channelButtons.add(button_3);

		JButton button_4 = new JButton("");
		button_4.setBorder(new RoundedBorder(20));
		button_4.setBackground(Color.GREEN);
		button_4.setBounds(356, 237, 13, 12);
		brainImagePanel.add(button_4);
		channelButtons.add(button_4);

		JButton button_5 = new JButton("");
		button_5.setBorder(new RoundedBorder(20));
		button_5.setBackground(Color.GREEN);
		button_5.setBounds(99, 310, 13, 12);
		brainImagePanel.add(button_5);
		channelButtons.add(button_5);

		JButton button_6 = new JButton("");
		button_6.setBorder(new RoundedBorder(20));
		button_6.setBackground(Color.GREEN);
		button_6.setBounds(372, 310, 13, 12);
		brainImagePanel.add(button_6);
		channelButtons.add(button_6);

		JButton button_7 = new JButton("");
		button_7.setBorder(new RoundedBorder(20));
		button_7.setBackground(Color.GREEN);
		button_7.setBounds(80, 346, 13, 12);
		brainImagePanel.add(button_7);
		channelButtons.add(button_7);

		JButton button_8 = new JButton("");
		button_8.setBorder(new RoundedBorder(20));
		button_8.setBackground(Color.GREEN);
		button_8.setBounds(84, 428, 13, 12);
		brainImagePanel.add(button_8);
		channelButtons.add(button_8);

		JButton button_9 = new JButton("");
		button_9.setBorder(new RoundedBorder(20));
		button_9.setBackground(Color.GREEN);
		button_9.setBounds(160, 493, 13, 12);
		brainImagePanel.add(button_9);
		channelButtons.add(button_9);

		JButton button_10 = new JButton("");
		button_10.setBorder(new RoundedBorder(20));
		button_10.setBackground(Color.GREEN);
		button_10.setBounds(336, 493, 13, 12);
		brainImagePanel.add(button_10);
		channelButtons.add(button_10);

		JButton button_11 = new JButton("");
		button_11.setBorder(new RoundedBorder(20));
		button_11.setBackground(Color.GREEN);
		button_11.setBounds(390, 428, 13, 12);
		brainImagePanel.add(button_11);
		channelButtons.add(button_11);

		JButton button_12 = new JButton("");
		button_12.setBorder(new RoundedBorder(20));
		button_12.setBackground(Color.GREEN);
		button_12.setBounds(392, 360, 13, 12);
		brainImagePanel.add(button_12);
		channelButtons.add(button_12);


		brainLabel.setBounds(36, 0, 580, 606);
		brainImagePanel.add(brainLabel);
		brainLabel.setIcon(new ImageIcon(Gui.class.getResource("/BCI/Core/brain.jpg")));


		
		expressivePanel.setBounds(730, 142, 647, 316);
		getContentPane().add(expressivePanel);
		expressivePanel.setLayout(null);

		
		affectivePanel.setSize(614, 289);
		affectivePanel.setLocation(730, 471);
		getContentPane().add(affectivePanel);
		
		JLabel lblSelectSetting = new JLabel("Select Setting");
		lblSelectSetting.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelectSetting.setBounds(730, 106, 113, 20);
		contentPane.add(lblSelectSetting);
		
		JRadioButton rdbtnHappy = new JRadioButton("Happy");
		rdbtnHappy.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				dataGenerator.setting="happy";
			}
		});
		rdbtnHappy.setBounds(846, 101, 92, 29);
		contentPane.add(rdbtnHappy);
		settingButtonGroup.add(rdbtnHappy);
		
		JRadioButton rdbtnNeutral = new JRadioButton("Neutral");
		rdbtnNeutral.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				dataGenerator.setting="neutral";
			}
		});
		rdbtnNeutral.setBounds(933, 102, 92, 29);
		contentPane.add(rdbtnNeutral);
		settingButtonGroup.add(rdbtnNeutral);
		
		JRadioButton rdbtnSad = new JRadioButton("Sad");
		rdbtnSad.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				dataGenerator.setting="sad";
			}
		});
		rdbtnSad.setBounds(1042, 102, 78, 29);
		contentPane.add(rdbtnSad);
		settingButtonGroup.add(rdbtnSad);
		
		JRadioButton rdbtnRandom = new JRadioButton("Random");
		rdbtnRandom.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				dataGenerator.setting="random";
			}
		});
		rdbtnRandom.setSelected(true);
		rdbtnRandom.setBounds(1132, 101, 92, 29);
		contentPane.add(rdbtnRandom);
		settingButtonGroup.add(rdbtnRandom);
		
		JRadioButton rdbtnManual = new JRadioButton("Manual");
		rdbtnManual.setBounds(1234, 102, 155, 29);
		contentPane.add(rdbtnManual);
		settingButtonGroup.add(rdbtnManual);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("listener trigger");
		if (e.getSource() == btnNewButton) {
			if (btnNewButton.getText().compareTo("Start") == 0) {
				portLabel.setVisible(true);
				btnNewButton.setText("Stop");
//				btnNewButton.setIcon(new ImageIcon(Gui.class.getResource("/BCI/Core/stop2.jpeg")));

				model.start();
			} else{
				System.out.println("stop");
				model.stop();
				btnNewButton.setText("Start");
				portLabel.setVisible(false);
				//btnNewButton.setIcon(new ImageIcon(Gui.class.getResource("/BCI/Core/play.jpg")));
			}
		}
	}
}