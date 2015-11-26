package swarm.model.level.room;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Vector3d;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.dynamicstate.ConsistentPublicLocalDynamicState;
import fr.lgi2a.similar.microkernel.influences.IInfluence;
import fr.lgi2a.similar.microkernel.influences.InfluencesMap;
import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractLevel;
import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.environment.room.EnvPLSInRoom;
import swarm.model.influences.toRoom.RIUpdateCameraDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateCommunicatorDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateMicrophoneDroneSpatialStateInRoom;
import swarm.model.level.SwarmLevelList;

public class RoomLevel extends AbstractLevel {
	
	/**
	 * Builds an uninitialized instance of this level.
	 * <p>
	 * 	The public local state of the environment in the level has 
	 * 	to be set during the initialization phase of the simulation.
	 * </p>
	 * @param initialTime The initial time of the simulation.
	 */
	private SwarmParameters parameters;
	
	public RoomLevel( 
		SimulationTimeStamp initialTime,
		SwarmParameters parameters
	) {
		super(
			initialTime,
			SwarmLevelList.ROOM
		);
		this.parameters = parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimulationTimeStamp getNextTime(
			SimulationTimeStamp currentTime
	) {
		return new SimulationTimeStamp( currentTime.getIdentifier() + 1 );
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void makeRegularReaction(
			SimulationTimeStamp transitoryTimeMin,
			SimulationTimeStamp transitoryTimeMax,
			ConsistentPublicLocalDynamicState consistentState,
			Set<IInfluence> regularInfluencesOftransitoryStateDynamics,
			InfluencesMap remainingInfluences
	) {
		Set<AgtCameraDronePLSInRoom> cameraUpdateList = new HashSet<AgtCameraDronePLSInRoom>();
		Set<AgtCommunicatorDronePLSInRoom> communicatorUpdateList = new HashSet<AgtCommunicatorDronePLSInRoom>();
		Set<AgtDronePLSInRoom> droneUpdateList = new HashSet<AgtDronePLSInRoom>();
		Set<AgtMicrophoneDronePLSInRoom> microphoneUpdateList = new HashSet<AgtMicrophoneDronePLSInRoom>();
		
		for( IInfluence influence : regularInfluencesOftransitoryStateDynamics ){
			if( influence.getCategory().equals( RIUpdateCameraDroneSpatialStateInRoom.CATEGORY ) ){
				RIUpdateCameraDroneSpatialStateInRoom castedInfluence = (RIUpdateCameraDroneSpatialStateInRoom) influence;
				cameraUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
				droneUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
				
			}else if( influence.getCategory().equals( RIUpdateCommunicatorDroneSpatialStateInRoom.CATEGORY ) ){
				RIUpdateCommunicatorDroneSpatialStateInRoom castedInfluence = (RIUpdateCommunicatorDroneSpatialStateInRoom) influence;
				communicatorUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
				droneUpdateList.addAll( castedInfluence.getParticlesToUpdate() );		
				
			}else if( influence.getCategory().equals( RIUpdateDroneSpatialStateInRoom.CATEGORY ) ){
				RIUpdateDroneSpatialStateInRoom castedInfluence = (RIUpdateDroneSpatialStateInRoom) influence;
				droneUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
				
				
			}else if( influence.getCategory().equals( RIUpdateMicrophoneDroneSpatialStateInRoom.CATEGORY ) ){
				RIUpdateMicrophoneDroneSpatialStateInRoom castedInfluence = (RIUpdateMicrophoneDroneSpatialStateInRoom) influence;
				microphoneUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
				droneUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
				
			}else {
				throw new UnsupportedOperationException( 
					"The influence '" + influence.getCategory() + "' is currently not supported in this reaction." 
				);
			}
		}
		
		
		
		//Influences on "drone" agents.
		for (AgtDronePLSInRoom agtDrone : droneUpdateList){
			
			UpdateInfluenceInRoom.UpdateDroneInfluence(agtDrone, droneUpdateList, parameters);
			
		}
		
		//Influences on "camera drone" agents
		for(AgtCameraDronePLSInRoom agtCameraDrone: cameraUpdateList){
			agtCameraDrone.setInfluence(
					agtCameraDrone.getInfluence().x + 1 /*(parameters.roomBounds.getWidth()/2 + 400*Math.cos((double)super.getLastConsistentState().getTime().getIdentifier()/400) - agtCameraDrone.getLocation().x)/400*/,
					agtCameraDrone.getInfluence().y /* + (parameters.roomBounds.getHeight()/2 + 400*Math.sin((double)super.getLastConsistentState().getTime().getIdentifier()/400) - agtCameraDrone.getLocation().y)/400*/,
					agtCameraDrone.getInfluence().z
					);
		}
		
		//Influences on "communicator drone" agents
		for(AgtCommunicatorDronePLSInRoom agtCommunicatorDrone: communicatorUpdateList){
			agtCommunicatorDrone.setInfluence(
					agtCommunicatorDrone.getInfluence().x,
					agtCommunicatorDrone.getInfluence().y + 1,
					agtCommunicatorDrone.getInfluence().z
					);
		}
		
		// Manage the reaction to the drones that were listed by the influences.
		this.cameraReactionTo( 
				transitoryTimeMin,
				transitoryTimeMax,
				(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
				cameraUpdateList,
				remainingInfluences
		);
		
		this.communicatorReactionTo( 
				transitoryTimeMin,
				transitoryTimeMax,
				(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
				communicatorUpdateList,
				remainingInfluences
		);
		
		this.droneReactionTo( 
				transitoryTimeMin,
				transitoryTimeMax,
				(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
				droneUpdateList,
				remainingInfluences
		);
		
		this.microphoneReactionTo( 
				transitoryTimeMin,
				transitoryTimeMax,
				(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
				microphoneUpdateList,
				remainingInfluences
		);
		
	}
	
	/**
	 * The reaction to the sum of all the {@link RIUpdateCameraDroneSpatialStateInRoom} influences
	 * that were sent to this level.
	 * @param transitoryTimeMin The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param transitoryTimeMax The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param roomEnvState The public local state of the environment in the chamber level.
	 * @param dronesUpdateList The drones listed in these influences.
	 * @param remainingInfluences The data structure where the influences resulting from this user reaction have to be added.
	 */
	private void cameraReactionTo( 
		SimulationTimeStamp transitoryTimeMin,
		SimulationTimeStamp transitoryTimeMax,
		EnvPLSInRoom roomEnvState,
		Set<AgtCameraDronePLSInRoom> dronesUpdateList,
		InfluencesMap remainingInfluences
	){
		//Does nothing
	}
	
		
		/**
		 * The reaction to the sum of all the {@link RIUpdateCommunicatorDroneSpatialStateInRoom} influences
		 * that were sent to this level.
		 * @param transitoryTimeMin The lower bound of the transitory period of the level for which this reaction is performed.
		 * @param transitoryTimeMax The lower bound of the transitory period of the level for which this reaction is performed.
		 * @param roomEnvState The public local state of the environment in the chamber level.
		 * @param dronesUpdateList The drones listed in these influences.
		 * @param remainingInfluences The data structure where the influences resulting from this user reaction have to be added.
		 */
		private void communicatorReactionTo( 
			SimulationTimeStamp transitoryTimeMin,
			SimulationTimeStamp transitoryTimeMax,
			EnvPLSInRoom roomEnvState,
			Set<AgtCommunicatorDronePLSInRoom> dronesUpdateList,
			InfluencesMap remainingInfluences
		){	
			//Does nothing
		}
	
	/**
	 * The reaction to the sum of all the {@link RIUpdateDroneSpatialStateInRoom} influences
	 * that were sent to this level.
	 * @param transitoryTimeMin The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param transitoryTimeMax The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param roomEnvState The public local state of the environment in the chamber level.
	 * @param dronesUpdateList The drones listed in these influences.
	 * @param remainingInfluences The data structure where the influences resulting from this user reaction have to be added.
	 */
	private void droneReactionTo( 
		SimulationTimeStamp transitoryTimeMin,
		SimulationTimeStamp transitoryTimeMax,
		EnvPLSInRoom roomEnvState,
		Set<AgtDronePLSInRoom> dronesUpdateList,
		InfluencesMap remainingInfluences
	){		
		
		for(AgtDronePLSInRoom agtDrone : dronesUpdateList){
			
			UpdatePositionInRoom.UpdateDronePosition(agtDrone, parameters);
			
		}
	}
	
	
	/**
	 * The reaction to the sum of all the {@link RIUpdateDroneSpatialStateInRoom} influences
	 * that were sent to this level.
	 * @param transitoryTimeMin The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param transitoryTimeMax The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param roomEnvState The public local state of the environment in the chamber level.
	 * @param dronesUpdateList The drones listed in these influences.
	 * @param remainingInfluences The data structure where the influences resulting from this user reaction have to be added.
	 */
	private void microphoneReactionTo( 
		SimulationTimeStamp transitoryTimeMin,
		SimulationTimeStamp transitoryTimeMax,
		EnvPLSInRoom roomEnvState,
		Set<AgtMicrophoneDronePLSInRoom> dronesUpdateList,
		InfluencesMap remainingInfluences
	){
		//Does nothing
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void makeSystemReaction(
			SimulationTimeStamp transitoryTimeMin,
			SimulationTimeStamp transitoryTimeMax,
			ConsistentPublicLocalDynamicState consistentState,
			Collection<IInfluence> systemInfluencesToManage,
			boolean happensBeforeRegularReaction,
			InfluencesMap remainingInfluences
	) {
		// Does nothing here.
	}
	
	
}
