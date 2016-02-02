package swarm.interfaceSimulation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;

public class SpinnerLine {

	private JSlider slider;
	private JSpinner spinner;
	
	public SpinnerLine(
			String name,
			int init,
			int min,
			int max, 
			GridBagLayout gblConfigInterface, 
			JFrame configInterface,
			ChangeListener changeListener			
			){

		GridBagConstraints configCons = new GridBagConstraints();
		JLabel configInterNbrDronesLabel = new JLabel(name);
		configCons.anchor = GridBagConstraints.WEST;
		configCons.insets = new Insets(10,2,2,10);
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(configInterNbrDronesLabel, configCons);
		configInterface.add(configInterNbrDronesLabel);
		
		spinner = new JSpinner();
		ConfigInterface.getTextField(spinner).setColumns(4);
		spinner.setValue(init);
		spinner.addChangeListener(changeListener);
		
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(spinner, configCons);
		configInterface.add(spinner);
		
				
		JLabel configInterNbrDronesDefaultValueLabel = new JLabel("Default :"+init);
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(configInterNbrDronesDefaultValueLabel, configCons);
		configInterface.add(configInterNbrDronesDefaultValueLabel);
		
		slider = new JSlider();
		slider.setMaximum(max);
		slider.setMinimum(min);
		slider.setValue(init);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(100);
		slider.setMajorTickSpacing(500);
		slider.addChangeListener(changeListener);
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		gblConfigInterface.setConstraints(slider, configCons);
		configInterface.add(slider);
	}
	
	public int getSpinnerHashcode(){
		return spinner.hashCode();
	}
	
	public int getSliderHashcode(){
		return slider.hashCode();
	}
	
	public int spinnerChanged(){
		slider.setValue((int)spinner.getValue());
		return (int)spinner.getValue();
	}
	
	public int sliderChanged(){
		spinner.setValue((int)slider.getValue());
		return (int)slider.getValue();
	}
}
