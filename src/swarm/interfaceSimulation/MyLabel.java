package swarm.interfaceSimulation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 *
 * A custom label with pre-builded specifications.
 */
public class MyLabel {
	
	/**
	 * create a new label in the layout.
	 * @param txt the text in the label.
	 * @param gblConfigInterface the layout.
	 * @param configInterface the Panel.
	 */
	public MyLabel(String txt, GridBagLayout gblConfigInterface,JPanel configInterface){
		

		GridBagConstraints configCons = new GridBagConstraints();
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		JLabel filling1 = new JLabel(" ");
		gblConfigInterface.setConstraints(filling1, configCons);
		configInterface.add(filling1);
		
		JLabel myLabel = new JLabel(txt);
		configCons.insets = new Insets(10,2,2,10);
		gblConfigInterface.setConstraints(myLabel, configCons);
		configInterface.add(myLabel);
	}
}
