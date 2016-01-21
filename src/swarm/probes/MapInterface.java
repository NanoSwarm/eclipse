package swarm.probes;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.lgi2a.similar.microkernel.IProbe;
import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.level.SwarmLevelList;


@SuppressWarnings("serial")
public class MapInterface extends JFrame implements IProbe,ActionListener{
	/** 
	 * The main panel 
	 */
	public JPanel mainPanel;
	public JButton NextButton;
	/**
	 * the drawer of the map
	 */
	private MapDrawer drawer; 
	/**
	 * 
	 * @param str the title of the window
	 */
    public MapInterface(String str)
    {
    	mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	this.setContentPane(mainPanel);
    	NextButton=new JButton("Next level");
    	NextButton.addActionListener(this);
    	NextButton.setActionCommand("suivant");
    	mainPanel.add(NextButton,BorderLayout.NORTH);
    	drawer=new MapDrawer();
    	mainPanel.add(drawer,BorderLayout.CENTER);	
    	this.setTitle(str);
        this.setSize(600, 1000);
        this.setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false); 
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		String command=new String();
		command= e.getActionCommand();
		if (command=="suivant") {
			drawer.setZ(drawer.getZ()+1);
			if (drawer.getZ() >= drawer.graph.getKmax()) drawer.setZ(0);
			mainPanel.invalidate();     
			mainPanel.repaint();
		};
	}

	@Override
	public void prepareObservation() { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtInitialTimes(
			SimulationTimeStamp initialTimestamp,
			ISimulationEngine simulationEngine
	) {
	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtPartialConsistentTime(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine){
		drawer.setGraph(simulationEngine);
	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endObservation() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reactToError(
			String errorMessage, 
			Throwable cause
	) { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reactToAbortion(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	) { }
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtFinalTime(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	) { 
		drawer.setGraph(simulationEngine);
        this.setVisible(true);
	}
	
}

