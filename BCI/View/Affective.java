package BCI.View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;

import BCI.controller.AffectiveController;
import BCI.Core.*;
import java.awt.Font;

public class Affective extends JPanel{

	AffectiveController affectiveController;
	public AffectiveController getAffectiveController() {
		return affectiveController;
	}
	public void setAffectiveController(AffectiveController affectiveController) {
		this.affectiveController = affectiveController;
	}
	public Affective(DataGenerator dg) {
		setLayout(null);
		
		JLabel lblAffective = new JLabel("Affective: ");
		lblAffective.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		lblAffective.setEnabled(false);
		lblAffective.setBounds(6, 16, 81, 16);
		add(lblAffective);
		
		JLabel lblFrustration = new JLabel("Frustration:");
		lblFrustration.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblFrustration.setBounds(160, 62, 110, 16);
		add(lblFrustration);
		
		JLabel lblEngagement = new JLabel("Engagement:");
		lblEngagement.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblEngagement.setBounds(151, 108, 171, 29);
		add(lblEngagement);
		
		JLabel lblMeditation = new JLabel("Meditation:");
		lblMeditation.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblMeditation.setBounds(168, 160, 171, 16);
		add(lblMeditation);
		
		JLabel lblSTExcitement = new JLabel("Short Term Excitement: ");
		lblSTExcitement.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblSTExcitement.setBounds(60, 205, 221, 16);
		add(lblSTExcitement);
		
		JLabel lblLTExcitement = new JLabel("Long Term Excitement:");
		lblLTExcitement.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblLTExcitement.setBounds(60, 245, 221, 34);
		add(lblLTExcitement);
		
		JSlider sliderFrustration = new JSlider();
		sliderFrustration.setPaintLabels(true);
		sliderFrustration.setMinorTickSpacing(1);
		sliderFrustration.setBounds(270, 60, 350, 29);
		add(sliderFrustration);
		
		JSlider sliderEngagement = new JSlider();
		sliderEngagement.setPaintLabels(true);
		sliderEngagement.setMinorTickSpacing(1);
		sliderEngagement.setBounds(270, 110, 350, 29);
		add(sliderEngagement);
		
		JSlider sliderMeditation = new JSlider();
		sliderMeditation.setPaintLabels(true);
		sliderMeditation.setMinorTickSpacing(1);
		sliderMeditation.setBounds(270, 160, 350, 29);
		add(sliderMeditation);
		
		JSlider sliderSTEngagement = new JSlider();
		sliderSTEngagement.setPaintLabels(true);
		sliderSTEngagement.setMinorTickSpacing(1);
		sliderSTEngagement.setBounds(270, 205, 350, 29);
		add(sliderSTEngagement);
		
		JSlider sliderLTEngagement = new JSlider();
		sliderLTEngagement.setPaintLabels(true);
		sliderLTEngagement.setMinorTickSpacing(1);
		sliderLTEngagement.setBounds(270, 250, 350, 29);
		add(sliderLTEngagement);
	
		
		this.affectiveController=new AffectiveController(sliderFrustration,sliderEngagement,sliderMeditation,sliderSTEngagement,sliderLTEngagement,dg);
		
	}
}
