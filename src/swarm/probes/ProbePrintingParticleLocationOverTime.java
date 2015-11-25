package swarm.probes;

import java.io.PrintStream;

import fr.lgi2a.similar.microkernel.IProbe;
import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * A simulation probe displaying on a print stream the 
 * location of the particles over time.
 * @author <a href="http://www.yoannkubera.net" target="_blank">Yoann Kubera</a>
 */
public class ProbePrintingParticleLocationOverTime implements IProbe {
	/**
	 * The stream where the data are written.
	 */
	private PrintStream target;
	
	/**
	 * Creates an instance of this probe writing in a specific print stream.
	 * @param target The stream where the data are written.
	 * @throws IllegalArgumentException If the <code>target</code> is <code>null</code>.
	 */
	public ProbePrintingParticleLocationOverTime(
		PrintStream target
	){
		this.target = target;
	}

	/**
	 * {@inheritDoc}
	 */
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
		this.displayLocations( initialTimestamp, simulationEngine );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtPartialConsistentTime(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	) {
		this.displayLocations( timestamp, simulationEngine );
	}
	
	/**
	 * Displays the location of the particles on the print stream.
	 * @param timestamp The time stamp when the observation is made.
	 * @param simulationEngine The engine where the simulation is running.
	 */
	private void displayLocations(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	){
		IPublicLocalDynamicState chamberState = simulationEngine.getSimulationDynamicStates().get( 
				SwarmLevelList.ROOM
		);
		for( ILocalStateOfAgent agtState : chamberState.getPublicLocalStateOfAgents() ){
			if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.CAMERADRONE ) ){
				AgtCameraDronePLSInRoom castedAgtState = (AgtCameraDronePLSInRoom) agtState;
				this.target.println( 
						timestamp.getIdentifier() + 
						"\t" + castedAgtState + 
						"\t" + castedAgtState.getLocation().x  + 
						"\t" + castedAgtState.getLocation().y  +
						"\t" + castedAgtState.getLocation().z 
				);
			}else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) ){
				AgtCommunicatorDronePLSInRoom castedAgtState = (AgtCommunicatorDronePLSInRoom) agtState;
				this.target.println( 
						timestamp.getIdentifier() + 
						"\t" + castedAgtState + 
						"\t" + castedAgtState.getLocation().x  + 
						"\t" + castedAgtState.getLocation().y  +
						"\t" + castedAgtState.getLocation().z 
				);
			}else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.DRONE ) ){
				AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;
				this.target.println( 
						timestamp.getIdentifier() + 
						"\t" + castedAgtState + 
						"\t" + castedAgtState.getLocation().x  + 
						"\t" + castedAgtState.getLocation().y +
						"\t" + castedAgtState.getLocation().z 
				);
			}else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) ){
				AgtMicrophoneDronePLSInRoom castedAgtState = (AgtMicrophoneDronePLSInRoom) agtState;
				this.target.println( 
						timestamp.getIdentifier() + 
						"\t" + castedAgtState + 
						"\t" + castedAgtState.getLocation().x + 
						"\t" + castedAgtState.getLocation().y +
						"\t" + castedAgtState.getLocation().z 
				);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtFinalTime(
			SimulationTimeStamp finalTimestamp,
			ISimulationEngine simulationEngine
	) {	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endObservation() {
		this.target.flush();
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
}