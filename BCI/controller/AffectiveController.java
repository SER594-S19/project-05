package BCI.controller;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BCI.Model.AffectiveData;
import BCI.Core.*;
public class AffectiveController {
	
	
	JSlider sliderFrustration, sliderEngagement, sliderMeditation, sliderSTEngagement, sliderLTEngagement; 
	public AffectiveData affectivedata ;
	private DataGenerator dg;
	public AffectiveController(final JSlider sliderFrustration,
			final JSlider sliderEngagement,final JSlider sliderMeditation,
			final JSlider sliderSTEngagement,final JSlider sliderLTEngagement,DataGenerator dg) { 
		this.dg=dg;
		
		this.sliderFrustration = sliderFrustration;
		this.sliderEngagement = sliderEngagement;
		this.sliderMeditation = sliderMeditation;
		this.sliderSTEngagement = sliderSTEngagement; 
		this.sliderLTEngagement = sliderLTEngagement; 
		
		sliderFrustration.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateDataModel();
			}
		});
		
		sliderLTEngagement.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateDataModel();
			}
		});
		
		sliderSTEngagement.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateDataModel();
			}
		});
		
		sliderMeditation.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateDataModel();
			}
		});
		
		sliderEngagement.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateDataModel();
				
			} 
		});
			
	}
	
	private double getDecimalBasedOnTick(int sliderValue) {
		
		return (double)sliderValue/100;
	}
	
	private void updateDataModel() {
		
		affectivedata = new AffectiveData();
		double frustrationLevel = getDecimalBasedOnTick(this.sliderFrustration.getValue());
		double meditationLevel  = getDecimalBasedOnTick(this.sliderMeditation.getValue());
		double engagementLevel = getDecimalBasedOnTick(this.sliderEngagement.getValue());
		double shortTermEngagementLevel = getDecimalBasedOnTick(this.sliderSTEngagement.getValue());
		double longTermEngagementLevel = getDecimalBasedOnTick(this.sliderLTEngagement.getValue());
		Data data= (Data)dg.getObject();
		
		affectivedata.updateAffectiveData(frustrationLevel, engagementLevel, meditationLevel, shortTermEngagementLevel, longTermEngagementLevel);
		data.setAffective(affectivedata);
		
		
	}
	
	public void setFrustration(double vl) {
		sliderFrustration.setValue((int)(vl*10));
	}
	public void setEngagement(double vl) {
		sliderEngagement.setValue((int)(vl*10));
	}
	public void setStExcitement(double vl) {
		sliderSTEngagement.setValue((int)(vl*10));
	}
	public void setltExcitement(double vl) {
		sliderLTEngagement.setValue((int)(vl*10));
	}
	public void setMeditation(double vl) {
		sliderMeditation.setValue((int)(vl*10));
	}
	

}
