package BCI.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BCI.Core.Data;
import BCI.Core.DataGenerator;
import BCI.Model.ExpressionData;

public class ExpressionController {

	private JSlider smirkSlider;
	private JSlider clenchSlider;
	private JSlider laughSlider;
	private ButtonGroup blinkButtonGroup;
	private ButtonGroup visionButtonGroup;
	private ButtonGroup eyeBrowButtonGroup;
	private ExpressionData expressionData;
	private DataGenerator dg ;

	private double blink, smirkLeft, smirkRight, lookLeft, lookRight, smile, clench, laugh, winkLeft, winkRight, raiseBrow, furrow = 0;

	public ExpressionController(JSlider smirkSlider, JSlider clenchSlider, JSlider laughSlider,
			ButtonGroup blinkButtonGroup, ButtonGroup visionButtonGroup, ButtonGroup eyeBrowButtonGroup, DataGenerator dg) {

		this.smirkSlider = smirkSlider;
		this.clenchSlider = clenchSlider;
		this.laughSlider = laughSlider;
		this.blinkButtonGroup = blinkButtonGroup;
		this.visionButtonGroup = visionButtonGroup;
		this.eyeBrowButtonGroup = eyeBrowButtonGroup;
		this.dg = dg;



		smirkSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				updateExpressionModel();
			}
		});

		clenchSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				updateExpressionModel();
			}
		});

		laughSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				updateExpressionModel();
			}
		});

		setItemListeners(blinkButtonGroup);
		setItemListeners(visionButtonGroup);
		setItemListeners(eyeBrowButtonGroup);
	}

	/*
	 * method to set change listeners for each button in a button group.
	 * @param: buttonGroup of type ButtonGroup
	 * referred from: https://stackoverflow.com/questions/201287/
	 */
	private void setItemListeners(ButtonGroup buttonGroup) {
		for(Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			buttons.nextElement().addItemListener((new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if(event.getStateChange() == ItemEvent.SELECTED)
						updateExpressionModel();
				}
			}));
		}
	}

	/*
	 * method to set change listeners for each button in a button group.
	 * @param: buttonGroup of type ButtonGroup
	 * referred from: https://stackoverflow.com/questions/201287/
	 */
	private String getSelectedOption(ButtonGroup buttonGroup) {
		for(Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if(button.isSelected()) {
				return button.getText();
			}
		}
		return "None";
	}

	private double getTickDecimal(int tickValue) {
		return (double)tickValue/100;
	}

	private void updateExpressionModel() {
		expressionData = new ExpressionData();

		double smirkSliderValue = getTickDecimal(this.smirkSlider.getValue());
		double clenchSliderValue = getTickDecimal(this.clenchSlider.getValue());
		laugh = getTickDecimal(this.laughSlider.getValue());
		String blinkOption = getSelectedOption(this.blinkButtonGroup);
		String visionOption = getSelectedOption(this.visionButtonGroup);
		String eyeBrowOption = getSelectedOption(this.eyeBrowButtonGroup);
		Data data= (Data)dg.getObject();

		if (smirkSliderValue < 0) {
			smirkLeft = Math.abs(smirkSliderValue);
			smirkRight = 0.0;
		}
		else {
			smirkLeft = 0.0;
			smirkRight = Math.abs(smirkSliderValue);
		}

		if (clenchSliderValue < 0) {
			clench = Math.abs(clenchSliderValue);
			smile = 0.0;
		}
		else {
			clench = 0.0;
			smile = Math.abs(clenchSliderValue);
		}

		switch(blinkOption) {
		case "None" :{
			blink = 0;
			winkLeft = 0;
			winkRight = 0;
			break;

		}
		case "Blink" :{
			blink = 1;
			winkLeft = 0;
			winkRight = 0;
			break;

		}
		case "Wink Left" :{
			blink = 0;
			winkLeft = 1;
			winkRight = 0;
			break;

		}
		case "Wink Right" :{
			blink = 0;
			winkLeft = 0;
			winkRight = 1;

		}
		}

		switch(visionOption) {
		case "None" :{
			lookLeft = 0;
			lookRight = 0;
			break;
		}


		case "Look Left" :{
			lookLeft = 1;
			lookRight = 0;
			break;
		}
		case "Look Right" :{
			lookLeft = 0;
			lookRight = 1;
		}
		}

		switch(eyeBrowOption) {
		case "None" :{
			raiseBrow = 0;
			furrow = 0;
			break;
		}
		case "Rasie Brow" :{
			raiseBrow = 1;
			furrow = 0;
			break;
		}
		case "Furrow" :{
			raiseBrow = 0;
			furrow = 1;
		}
		}

		System.out.println(blink+" "+ winkLeft+" "+winkRight+" "+lookLeft+" "+lookRight+" "+smirkLeft+" "+smirkRight+" "+clench+" "+smile+" "+laugh+" "+raiseBrow+" "+furrow);
		expressionData.updateExpressionData(blink,winkLeft,winkRight,lookLeft,lookRight,smirkLeft,smirkRight,clench,smile,laugh,raiseBrow,furrow);
		data.setExpressive(expressionData);
	}

}
