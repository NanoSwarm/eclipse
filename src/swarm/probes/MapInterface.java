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


	public class MapInterface extends JFrame implements IProbe,ActionListener{
		/**
		 * 
		 */
		public JPanel panel;
		public JButton suivant;
		private static final long serialVersionUID = 9164220726804899844L;
		private MapDrawer pan;  
	    public MapInterface(String str)
	    {
	    	panel = new JPanel();
	    	panel.setLayout(new BorderLayout());
	    	this.setContentPane(panel);
	    	suivant=new JButton("Next");
	    	suivant.addActionListener(this);
	    	suivant.setActionCommand("suivant");
	    	panel.add(suivant,BorderLayout.NORTH);
	    	pan=new MapDrawer();
	    	panel.add(pan,BorderLayout.CENTER);	
	    	this.setTitle(str);
	        this.setSize(600, 1000);
	        this.setBackground(Color.LIGHT_GRAY);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setVisible(false); 
	     /*   pan.invalidate();
	        pan.repaint();
	        panel.invalidate();     
	        panel.repaint();*/
	    }
	    
		@Override
		public void actionPerformed(ActionEvent e) {
			String command=new String();
			command= e.getActionCommand();
			if (command=="suivant") {
				pan.z++;
				if (pan.z>=pan.graphique.getKmax()) pan.z=0;
				 panel.invalidate();     
			      panel.repaint();
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
		pan.setGraph(simulationEngine);
		
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
			pan.setGraph(simulationEngine);
	        this.setVisible(true);
		}
		
		public void updateDraw(SimulationTimeStamp timestamp,
				ISimulationEngine simulationEngine
		){
			
			IPublicLocalDynamicState chamberState = simulationEngine.getSimulationDynamicStates().get( 
					SwarmLevelList.ROOM
			);
			for( ILocalStateOfAgent agtState : chamberState.getPublicLocalStateOfAgents() )	
			{
				AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;
				if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE ) ){
				 castedAgtState = (AgtMeasurementDronePLSInRoom) agtState;	
				 AgtMeasurementDronePLSInRoom Agt=(AgtMeasurementDronePLSInRoom)  castedAgtState;
				// pan.add(Agt.getLocation().getX(),Agt.getLocation().getY(),Objective.getObjective(Agt.getLocation()));
				}
			}
			}
	}

