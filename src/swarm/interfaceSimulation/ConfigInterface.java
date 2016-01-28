package swarm.interfaceSimulation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;

import swarm.model.SwarmParameters;

public class ConfigInterface extends JFrame
							 implements WindowListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public boolean configurationOK=false;
	JFrame configInterface;
	
	
	public ConfigInterface(SwarmParameters parameters)
	{
		super("Configuration");
		
		GridBagLayout gblConfigInterface = new GridBagLayout();
		configInterface = new JFrame();
		GridBagConstraints configCons = new GridBagConstraints();
		
		configInterface.setLayout(gblConfigInterface);
		
		//1er ligne
		JLabel configInterNbrDronesLabel = new JLabel("Nombre de drones :");
		configCons.anchor = GridBagConstraints.WEST;
		configCons.insets = new Insets(10,2,2,10);			
		gblConfigInterface.setConstraints(configInterNbrDronesLabel, configCons);
		configInterface.add(configInterNbrDronesLabel);
		
		JSpinner configInterNbrDronesText = new JSpinner();
		getTextField(configInterNbrDronesText).setColumns(4);
		gblConfigInterface.setConstraints(configInterNbrDronesText, configCons);
		configCons.gridwidth = 1;
		configInterface.add(configInterNbrDronesText);
				
		JLabel configInterNbrDronesDefaultValueLabel = new JLabel("Default : 100");
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		gblConfigInterface.setConstraints(configInterNbrDronesDefaultValueLabel, configCons);
		configCons.gridwidth = 1;
		configInterface.add(configInterNbrDronesDefaultValueLabel);
		
		
		//2�me ligne
		JLabel configInterNbrDronesMicroLabel = new JLabel("Nombre de drones microphones:");
		configCons.anchor = GridBagConstraints.WEST;
		configCons.insets = new Insets(10,2,2,10);			
		gblConfigInterface.setConstraints(configInterNbrDronesMicroLabel, configCons);
		configInterface.add(configInterNbrDronesMicroLabel);
		
		JSpinner configInterNbrDronesMicroText = new JSpinner(); 
		getTextField(configInterNbrDronesMicroText).setColumns(4);
		gblConfigInterface.setConstraints(configInterNbrDronesMicroText, configCons);
		configCons.gridwidth = 1;
		configInterface.add(configInterNbrDronesMicroText);
		
		JLabel configInterNbrDronesMicroDefaultValueLabel = new JLabel("Default : 20");
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		gblConfigInterface.setConstraints(configInterNbrDronesMicroDefaultValueLabel, configCons);
		configCons.gridwidth = 1;
		configInterface.add(configInterNbrDronesMicroDefaultValueLabel);
		
		//3�me ligne
		JButton launchSimu = new JButton("Launch Simulation");
		configCons.anchor = GridBagConstraints.WEST;
		gblConfigInterface.setConstraints(launchSimu, configCons);
		configInterface.add(launchSimu);
		launchSimu.addActionListener(this);
		launchSimu.setActionCommand("Launch Simulation");
		
		
		JButton quitSimu = new JButton("Quit");
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		gblConfigInterface.setConstraints(quitSimu, configCons);
		configCons.gridwidth = 1;
		configInterface.add(quitSimu);
		quitSimu.addActionListener(this);
		quitSimu.setActionCommand("Quit");
		
		//
		configInterface.pack();
		configInterface.setVisible(true);
		//configInterface.setSize(300, 500);
		
		/*
		TextField serverAdress = new TextField("localhost",40);
		g1.setConstraints(serverAdress, constraints);
		interface1.add(serverAdress);
						
		JButton connect = new JButton("Connect");
		g1.setConstraints(connect, constraints);
		interface1.add(connect);
		connect.addActionListener(this);
		connect.setActionCommand("Connect");

						
		JButton disconnect = new JButton("Disconnect");
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		g1.setConstraints(disconnect, constraints);
		constraints.gridwidth = 1;
		interface1.add(disconnect);
		disconnect.addActionListener(this);
		disconnect.setActionCommand("Disconnect");
		*/
	}

	
	
	public void GriserConfigInterface()
	{
		
	}
	

	public JFormattedTextField getTextField(JSpinner spinner) {
	    JComponent editor = spinner.getEditor();
	    if (editor instanceof JSpinner.DefaultEditor) {
	        return ((JSpinner.DefaultEditor)editor).getTextField();
	    } else {
	        System.err.println("Unexpected editor type: "
	                           + spinner.getEditor().getClass()
	                           + " isn't a descendant of DefaultEditor");
	        return null;
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Quit"))
		{
			System.exit(0);
		}
		else if (e.getActionCommand().equals("Launch Simulation"))
		{
			configurationOK=true;
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static Properties load(String filename) throws IOException, FileNotFoundException{
	      Properties properties = new Properties();

	      FileInputStream input = new FileInputStream(filename); 
	      try{

	         properties.load(input);
	         return properties;
	      }

	              finally{

	         input.close();

	      }
	}
}
