package swarm.probes;


import java.awt.Color;

import javax.swing.JFrame;

import fr.lgi2a.similar.microkernel.IProbe;
import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.environment.Objective;
import swarm.model.level.SwarmLevelList;


	public class MapInterface extends JFrame implements IProbe{
		private MapDrawer pan;  
	    public MapInterface(String str)
	    {
	    	pan=new MapDrawer();
	    	this.setTitle(str);
	        this.setSize(600, 1000);
	        this.setBackground(Color.LIGHT_GRAY);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.getContentPane().add(pan);
	        //this.pack();
	        this.setVisible(true);
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
			this.updateDraw( timestamp, simulationEngine );
		
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
				 pan.add(Agt.getLocation().getX(),Agt.getLocation().getY(),Objective.getObjective(Agt.getLocation()));
				}
			}
}
}

