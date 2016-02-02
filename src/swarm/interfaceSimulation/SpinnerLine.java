package swarm.interfaceSimulation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Corentin
 * The object including all the elements of a line in our interface.
 */
public class SpinnerLine {

	/**
	 * The slider of our interface.
	 */
	private JSlider slider;
	
	/**
	 * The spinner of our interface.
	 */
	private JSpinner spinner;
	
	/**
	 * the scale factor use to change the precision of the values.
	 */
	private int scaleFactor;
	
	/**
	 * 
	 * @param name The string for the first Jlabel.
	 * @param init The initial value of the spinner and slider.
	 * @param min The minimal value of the spinner and slider.
	 * @param max The maximal value of the spinner and slider.
	 * @param scaleFactor The precision factor.
	 * @param gblConfigInterface The layout.
	 * @param configInterface The Panel of our interface.
	 * @param changeListener The main changeListener.
	 */
	public SpinnerLine(
			String name,
			double init,
			double min,
			double max, 
			int scaleFactor,
			GridBagLayout gblConfigInterface, 
			JPanel configInterface,
			ChangeListener changeListener			
			){
		
		// a multiplicative factor use when more precision is needed
		this.scaleFactor = scaleFactor;
		
		GridBagConstraints configCons = new GridBagConstraints();
		
		//The label used in the line
		JLabel configInterNbrDronesLabel = new JLabel(name);
		configCons.anchor = GridBagConstraints.WEST;
		configCons.insets = new Insets(10,2,2,10);
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(configInterNbrDronesLabel, configCons);
		configInterface.add(configInterNbrDronesLabel);
		
		//change the stepSize of the spinner to have more precision
		double stepSize = 1.0/scaleFactor;
		SpinnerModel model = new SpinnerNumberModel(init,min,max,stepSize);
		
		spinner = new JSpinner(model);
		
		//add more decimal to the format editor
		JSpinner.NumberEditor editor = (JSpinner.NumberEditor)spinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(Integer.toString(scaleFactor).length()-1);
        
        //change the size of the spinner
        Dimension d = spinner.getPreferredSize();
        d.width = 80;
        spinner.setPreferredSize(d);
		editor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
		
		//add the general changeListener from ConfigInterface as a listener
		spinner.addChangeListener(changeListener);
		
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(spinner, configCons);
		configInterface.add(spinner);
		
				
		JLabel configInterNbrDronesDefaultValueLabel = new JLabel("Default :"+init);
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(configInterNbrDronesDefaultValueLabel, configCons);
		configInterface.add(configInterNbrDronesDefaultValueLabel);
		
		//add a slider scaled depending of the values needed
		slider = new JSlider();
		slider.setMaximum((int)max*scaleFactor);
		slider.setMinimum((int)min*scaleFactor);
		slider.setValue((int)init*scaleFactor);
		slider.setPaintTicks(false);
		slider.addChangeListener(changeListener);
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		gblConfigInterface.setConstraints(slider, configCons);
		configInterface.add(slider);
	}
	
	/**
	 * Needed for event source identification.
	 * @return the hasCode of the Spinner.
	 */
	public int getSpinnerHashcode(){
		return spinner.hashCode();
	}
	
	/**
	 * Needed for event source identification.
	 * @return the hasCode of the Slider.
	 */
	public int getSliderHashcode(){
		return slider.hashCode();
	}
	
	/**
	 * create a link between the spinner and the slider.
	 * @return The new value of the parameter.
	 */
	public double spinnerChanged(){
		slider.setValue((int)((double)spinner.getValue()*scaleFactor));
		return (double)spinner.getValue();
	}
	
	/**
	 * create a link between the slider and the spinner.
	 * @return the new value of the parameter.
	 */
	public double sliderChanged(){
		spinner.setValue( (double)slider.getValue()/scaleFactor );
		return (double)(int)slider.getValue()/scaleFactor;
	}
}
