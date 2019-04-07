package BCI.View;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JSlider;

import BCI.Core.DataGenerator;
import BCI.controller.ExpressionController;
import java.awt.Font;

public class Expressions extends JPanel {
	private final ButtonGroup blinkButtonGroup = new ButtonGroup();
	private final ButtonGroup visionButtonGroup = new ButtonGroup();
	private final ButtonGroup eyeBrowButtonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public Expressions(DataGenerator dg) {
		setLayout(null);
		
		//label for eye blink
		JLabel blinkActionLabel = new JLabel("Select Blink Action:");
		blinkActionLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		blinkActionLabel.setBounds(6, 30, 180, 23);
		add(blinkActionLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 32, 137, 2);
		add(separator);
		
		//radio buttons for eye blink actions
		JRadioButton rdbtnBlink = new JRadioButton("Blink");
		rdbtnBlink.setFont(new Font("Dialog", Font.PLAIN, 20));
		blinkButtonGroup.add(rdbtnBlink);
		rdbtnBlink.setBounds(0, 58, 90, 23);
		add(rdbtnBlink);
		
		JRadioButton rdbtnWinkLeft = new JRadioButton("Wink Left");
		rdbtnWinkLeft.setFont(new Font("Dialog", Font.PLAIN, 20));
		blinkButtonGroup.add(rdbtnWinkLeft);
		rdbtnWinkLeft.setBounds(143, 58, 130, 23);
		add(rdbtnWinkLeft);
		
		JRadioButton rdbtnWinkRight = new JRadioButton("Wink Right");
		rdbtnWinkRight.setFont(new Font("Dialog", Font.PLAIN, 20));
		blinkButtonGroup.add(rdbtnWinkRight);
		rdbtnWinkRight.setBounds(0, 93, 143, 23);
		add(rdbtnWinkRight);
		
		JRadioButton rdbtnNone = new JRadioButton("None");
		rdbtnNone.setSelected(true);
		rdbtnNone.setFont(new Font("Dialog", Font.PLAIN, 20));
		blinkButtonGroup.add(rdbtnNone);
		rdbtnNone.setBounds(143, 94, 109, 23);
		add(rdbtnNone);
		
		//label for eye vision
		JLabel lblSelectVision = new JLabel("Select Vision:");
		lblSelectVision.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		lblSelectVision.setBounds(6, 128, 137, 23);
		add(lblSelectVision);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 130, 137, 2);
		add(separator_1);
		
		//radio buttons for eye vision actions.
		JRadioButton rdbtnLookLeft = new JRadioButton("Look Left");
		rdbtnLookLeft.setFont(new Font("Dialog", Font.PLAIN, 20));
		visionButtonGroup.add(rdbtnLookLeft);
		rdbtnLookLeft.setBounds(0, 163, 123, 23);
		add(rdbtnLookLeft);
		
		JRadioButton rdbtnLookRight = new JRadioButton("Look Right");
		rdbtnLookRight.setFont(new Font("Dialog", Font.PLAIN, 20));
		visionButtonGroup.add(rdbtnLookRight);
		rdbtnLookRight.setBounds(130, 163, 143, 23);
		add(rdbtnLookRight);
		
		JRadioButton rdbtnNone_1 = new JRadioButton("None");
		rdbtnNone_1.setSelected(true);
		rdbtnNone_1.setFont(new Font("Dialog", Font.PLAIN, 20));
		visionButtonGroup.add(rdbtnNone_1);
		rdbtnNone_1.setBounds(0, 198, 109, 23);
		add(rdbtnNone_1);
		
		//label for eye brow actions
		JLabel lblSelectEyeBrow = new JLabel("Select Eye Brow Action:");
		lblSelectEyeBrow.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		lblSelectEyeBrow.setBounds(0, 233, 205, 25);
		add(lblSelectEyeBrow);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 235, 137, 2);
		add(separator_2);
		
		//radio buttons for eye brow actions.
		JRadioButton rdbtnRaiseBrow = new JRadioButton("Raise Brow");
		rdbtnRaiseBrow.setFont(new Font("Dialog", Font.PLAIN, 20));
		eyeBrowButtonGroup.add(rdbtnRaiseBrow);
		rdbtnRaiseBrow.setBounds(-4, 270, 137, 23);
		add(rdbtnRaiseBrow);
		
		JRadioButton rdbtnFurrow = new JRadioButton("Furrow");
		rdbtnFurrow.setFont(new Font("Dialog", Font.PLAIN, 20));
		eyeBrowButtonGroup.add(rdbtnFurrow);
		rdbtnFurrow.setBounds(143, 271, 109, 23);
		add(rdbtnFurrow);
		
		JRadioButton rdbtnNone_2 = new JRadioButton("None");
		rdbtnNone_2.setSelected(true);
		rdbtnNone_2.setFont(new Font("Dialog", Font.PLAIN, 20));
		eyeBrowButtonGroup.add(rdbtnNone_2);
		rdbtnNone_2.setBounds(-4, 295, 109, 23);
		add(rdbtnNone_2);
		
		
		//slider for smirk
		JSlider smirkSlider = new JSlider();
		smirkSlider.setMinorTickSpacing(1);
		smirkSlider.setValue(0);
		smirkSlider.setMaximum(100);
		smirkSlider.setMinimum(-100);
		smirkSlider.setBounds(361, 48, 280, 30);
		add(smirkSlider);
		
		JLabel lblLeftSmirk = new JLabel("Left Smirk");
		lblLeftSmirk.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblLeftSmirk.setBounds(314, 81, 100, 23);
		add(lblLeftSmirk);
		
		JLabel lblRightSmirk = new JLabel("Right Smirk");
		lblRightSmirk.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblRightSmirk.setBounds(536, 81, 105, 23);
		add(lblRightSmirk);
		
		//slider for clench and smile.
		JSlider clenchSlider = new JSlider();
		clenchSlider.setValue(0);
		clenchSlider.setMinimum(-100);
		clenchSlider.setMaximum(100);
		clenchSlider.setBounds(361, 146, 280, 30);
		add(clenchSlider);
		
		JLabel lblClench = new JLabel("Clench");
		lblClench.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblClench.setBounds(314, 173, 70, 23);
		add(lblClench);
		
		JLabel lblSmile = new JLabel("Smile");
		lblSmile.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblSmile.setBounds(586, 173, 55, 23);
		add(lblSmile);
		
		//sliders for laugh.
		JSlider laughSlider = new JSlider();
		laughSlider.setValue(0);
		laughSlider.setMaximum(100);
		laughSlider.setBounds(361, 252, 280, 30);
		add(laughSlider);
		
		JLabel lblLaugh = new JLabel("Laugh");
		lblLaugh.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblLaugh.setBounds(483, 275, 63, 32);
		add(lblLaugh);
		
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(224, 47, 1, 2);
		add(separator_3);

		new ExpressionController(smirkSlider,clenchSlider, laughSlider,
				blinkButtonGroup, visionButtonGroup, eyeBrowButtonGroup,dg);
		
		JLabel lblExpressive = new JLabel("Expressive:");
		lblExpressive.setEnabled(false);
		lblExpressive.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		lblExpressive.setBounds(6, 6, 154, 16);
		add(lblExpressive);
	
		
	}
}
