package swarm.model.environment;

import java.util.Map;

import fr.lgi2a.similar.microkernel.LevelIdentifier;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicDynamicStateMap;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import fr.lgi2a.similar.microkernel.environment.ILocalStateOfEnvironment;
import fr.lgi2a.similar.microkernel.influences.InfluencesMap;
import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractEnvironment;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.influences.toRoom.RIUpdateCameraDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateCommunicatorDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateDroneEnergyLevelInRoom;
import swarm.model.influences.toRoom.RIUpdateDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateMeasurementDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateMicrophoneDroneSpatialStateInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 * The environment used in the "swarm" simulation.
 */
public class SwarmEnvironment extends AbstractEnvironment {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void natural(
		LevelIdentifier level,
		SimulationTimeStamp timeLowerBound,
		SimulationTimeStamp timeUpperBound,
		Map<LevelIdentifier, ILocalStateOfEnvironment> publicLocalStates,
		ILocalStateOfEnvironment privateLocalState,
		IPublicDynamicStateMap dynamicStates,
		InfluencesMap producedInfluences
	) {
		if( level.equals( SwarmLevelList.ROOM ) ){
			this.naturalForRoomLevel(
				timeLowerBound,
				timeUpperBound, 
				publicLocalStates,
				privateLocalState,
				dynamicStates,
				producedInfluences
			);
		} else {
			throw new UnsupportedOperationException( 
				"The '" + level + "' does not belong to the " +
				"initial specification of the 'bubble chamber' simulation." 
			);
		}
	}
	
	/**
	 * The natural action of the environment for the "Room" level.
	 */
	public void naturalForRoomLevel(
		SimulationTimeStamp timeLowerBound,
		SimulationTimeStamp timeUpperBound,
		Map<LevelIdentifier, ILocalStateOfEnvironment> publicLocalStates,
		ILocalStateOfEnvironment privateLocalState,
		IPublicDynamicStateMap dynamicStates,
		InfluencesMap producedInfluences
	) {
		
		// First create the influences asking for the update.
		RIUpdateCameraDroneSpatialStateInRoom cameraUpdateInfluence = new RIUpdateCameraDroneSpatialStateInRoom(
				timeLowerBound,
				timeUpperBound
			);
		RIUpdateCommunicatorDroneSpatialStateInRoom communicatorUpdateInfluence = new RIUpdateCommunicatorDroneSpatialStateInRoom(
				timeLowerBound,
				timeUpperBound
			);
		RIUpdateDroneSpatialStateInRoom droneUpdateInfluence = new RIUpdateDroneSpatialStateInRoom(
				timeLowerBound,
				timeUpperBound
			);
		RIUpdateMicrophoneDroneSpatialStateInRoom microphoneUpdateInfluence = new RIUpdateMicrophoneDroneSpatialStateInRoom(
				timeLowerBound,
				timeUpperBound
			);
		RIUpdateDroneEnergyLevelInRoom energyUpdateInfluence = new RIUpdateDroneEnergyLevelInRoom(
				timeLowerBound,
				timeUpperBound
			);
		RIUpdateMeasurementDroneSpatialStateInRoom measurementUpdateInfluence = new RIUpdateMeasurementDroneSpatialStateInRoom(
				timeLowerBound,
				timeUpperBound
			);
		
		// Add these influences to the produced influences (the drones are registered
		// to the influence later).
		producedInfluences.add( cameraUpdateInfluence );	
		producedInfluences.add( communicatorUpdateInfluence );
		producedInfluences.add( droneUpdateInfluence );
		producedInfluences.add( microphoneUpdateInfluence );
		producedInfluences.add( energyUpdateInfluence );
		producedInfluences.add( measurementUpdateInfluence );
		
		// Then get the dynamic state of the "Chamber" level, to list the 
		// particles
		IPublicLocalDynamicState dynamicState = dynamicStates.get( 
			SwarmLevelList.ROOM 
		);
		
		// Search for particles among the agents lying in that state.
		for( ILocalStateOfAgent state : dynamicState.getPublicLocalStateOfAgents() ){
			
			//all drones are added to the "RIUpdateEnergyLevelInRoom" influence
			AgtDronePLSInRoom castedDrone = (AgtDronePLSInRoom) state;
			energyUpdateInfluence.addParticleToUpdate( castedDrone );

			//Drones are added to their related influences
			if( state.getCategoryOfAgent().isA( SwarmAgentCategoriesList.CAMERADRONE ) ){
				// Cast the public local state into the appropriate type.
				AgtCameraDronePLSInRoom castedState = (AgtCameraDronePLSInRoom) state;
				// Add the particle to the particles to update.
				cameraUpdateInfluence.addParticleToUpdate( castedState );
				
			}else if( state.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) ){
				// Cast the public local state into the appropriate type.
				AgtCommunicatorDronePLSInRoom castedState = (AgtCommunicatorDronePLSInRoom) state;
				// Add the particle to the particles to update.
				communicatorUpdateInfluence.addParticleToUpdate( castedState );
				
			}else if( state.getCategoryOfAgent().isA( SwarmAgentCategoriesList.DRONE ) ){
				// Cast the public local state into the appropriate type.
				AgtDronePLSInRoom castedState = (AgtDronePLSInRoom) state;
				// Add the particle to the particles to update.
				droneUpdateInfluence.addParticleToUpdate( castedState );
				
			}else if( state.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) ){
				// Cast the public local state into the appropriate type.
				AgtMicrophoneDronePLSInRoom castedState = (AgtMicrophoneDronePLSInRoom) state;
				// Add the particle to the particles to update.
				microphoneUpdateInfluence.addParticleToUpdate( castedState );
				
			}else if( state.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE)) {
				// Cast the public local state into the appropriate type.
				AgtMeasurementDronePLSInRoom castedState = (AgtMeasurementDronePLSInRoom) state;
				// Add the particle to the particles to update.
				measurementUpdateInfluence.addParticleToUpdate( castedState );
			}
		}		
	}
}

