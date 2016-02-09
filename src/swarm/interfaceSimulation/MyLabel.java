package swarm.interfaceSimulation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 
 * custom label 
 *
 */
public class MyLabel {
	
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
