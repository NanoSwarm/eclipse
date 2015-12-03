package swarm.initialization;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.LevelIdentifier;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.IAgent4Engine;
import fr.lgi2a.similar.microkernel.levels.ILevel;
import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractSimulationModel;
import fr.lgi2a.similar.microkernel.libs.generic.EmptyLocalStateOfEnvironment;
import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.AgtDroneFactory;
import swarm.model.agents.cameraDrone.AgtCameraDroneFactory;
import swarm.model.agents.communicatorDrone.AgtCommunicatorDroneFactory;
import swarm.model.agents.microphoneDrone.AgtMicrophoneDroneFactory;
import swarm.model.environment.SwarmEnvironment;
import swarm.model.environment.room.EnvPLSInRoom;
import swarm.model.level.SwarmLevelList;
import swarm.model.level.room.RoomLevel;
import swarm.tools.RandomValueFactory;

public class SwarmInitialization extends AbstractSimulationModel{
	
	/**
	 * The parameters being used in the simulation.
	 */
	private SwarmParameters parameters;
	/**
	 * The final time of the simulation.
	 */
	private SimulationTimeStamp finalTime;
	
	/**
	 * Builds a new model for the "Swarm" simulation.
	 * @param initialTime The initial time of the simulation.
	 * @param finalTime The final time of the simulation.
	 * @throws IllegalArgumentException If the <code>parameters</code> or the <code>finalTime</code>
	 * arguments are <code>null</code>.
	 */
	public SwarmInitialization(
			SimulationTimeStamp initialTime,
			SimulationTimeStamp finalTime,
			SwarmParameters parameters
	) {
		super(initialTime);
		if( parameters == null || finalTime == null ){
			throw new IllegalArgumentException( "The arguments cannot be null." );
		} else {
			this.parameters = parameters;
			this.finalTime = finalTime;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFinalTimeOrAfter(
			SimulationTimeStamp currentTime,
			ISimulationEngine engine
	) {
		return currentTime.compareTo( this.finalTime ) >= 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ILevel> generateLevels(
			SimulationTimeStamp initialTime
	) {
		List<ILevel> result = new LinkedList<ILevel>();
		
		// Create the "room" level of the simulation.
		RoomLevel chamber = new RoomLevel( initialTime, parameters );
		result.add( chamber );
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnvironmentInitializationData generateEnvironment(
			SimulationTimeStamp initialTime, 
			Map<LevelIdentifier, ILevel> levels
	) {
		// Create the environment.
		SwarmEnvironment environment = new SwarmEnvironment( );
		// Set the local state of the environment for each level.
		environment.includeNewLevel(
				SwarmLevelList.ROOM, 
				new EnvPLSInRoom( this.parameters.roomBounds ),
				new EmptyLocalStateOfEnvironment( SwarmLevelList.ROOM )
		);
		
		return new EnvironmentInitializationData( environment );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AgentInitializationData generateAgents(
			SimulationTimeStamp initialTime, Map<LevelIdentifier, ILevel> levels) {
		SwarmParameters castedParameters = (SwarmParameters) parameters;
		AgentInitializationData result = new AgentInitializationData();
		for(int i = 0; i < castedParameters.nbOfCameraDroneAgents; i++) {
			IAgent4Engine drone = AgtCameraDroneFactory.generate(RandomValueFactory.getStrategy().randomDouble(0,castedParameters.roomBounds.getWidth()),
								  								 RandomValueFactory.getStrategy().randomDouble(0,castedParameters.roomBounds.getHeight()),
								  								 0);
			result.getAgents().add( drone );
		}
		for(int i = 0; i < castedParameters.nbOfCommunicatorDroneAgents; i++) {
			IAgent4Engine drone = AgtCommunicatorDroneFactory.generate(RandomValueFactory.getStrategy().randomDouble(0,castedParameters.roomBounds.getWidth()),
								  								 RandomValueFactory.getStrategy().randomDouble(0,castedParameters.roomBounds.getHeight()), 
								  								0);
			result.getAgents().add( drone );
		}
		for(int i = 0; i < castedParameters.nbOfDroneAgents; i++) {
			IAgent4Engine drone = AgtDroneFactory.generate(RandomValueFactory.getStrategy().randomDouble(0,castedParameters.roomBounds.getWidth()),
								  								 RandomValueFactory.getStrategy().randomDouble(0,castedParameters.roomBounds.getHeight()), 
								  								0);
			result.getAgents().add( drone );
		}
		for(int i = 0; i < castedParameters.nbOfMicrophoneDroneAgents; i++) {
			IAgent4Engine drone = AgtMicrophoneDroneFactory.generate(RandomValueFactory.getStrategy().randomDouble(0,castedParameters.roomBounds.getWidth()),
								  								 RandomValueFactory.getStrategy().randomDouble(0,castedParameters.roomBounds.getHeight()),
								  								 0);
			result.getAgents().add( drone );
		}
		return result;
	}
	
}
