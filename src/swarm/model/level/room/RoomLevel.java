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
		
		//Calculation of different influences
		Vector3d repulsionAcc = new Vector3d();
		Vector3d orientationAcc = new Vector3d();
		Vector3d attractionAcc = new Vector3d();
		int nbOfDronesInRepulsionArea = 0;
		int nbOfDronesInOrientationArea = 0;
		int nbOfDronesInAttractionArea = 0;	
		
		//Influences on "drone" agents.
		for (AgtDronePLSInRoom agtDrone : droneUpdateList){
			
			agtDrone.setInfluence(0, 0, 0);
			repulsionAcc.set(0, 0,0);
			orientationAcc.set(0, 0,0);
			attractionAcc.set(0, 0,0);
			nbOfDronesInRepulsionArea = 0;
			nbOfDronesInOrientationArea = 0;
			nbOfDronesInAttractionArea = 0;	
			
			for( AgtDronePLSInRoom agtOtherDrone : droneUpdateList ){
				if(agtDrone != agtOtherDrone){
					double distance =Math.sqrt(Math.pow(agtDrone.getLocation().x - agtOtherDrone.getLocation().x, 2)
											 + Math.pow(agtDrone.getLocation().y - agtOtherDrone.getLocation().y, 2)
											 +Math.pow(agtDrone.getLocation().z - agtOtherDrone.getLocation().z, 2));
					if (distance > parameters.attractionDistance){
						//does nothing
					}
					else if(distance < parameters.attractionDistance && distance > parameters.orientationDistance){
						nbOfDronesInAttractionArea++;
						attractionAcc.set(
								attractionAcc.x + (agtOtherDrone.getLocation().x - agtDrone.getLocation().x)/distance,
								attractionAcc.y + (agtOtherDrone.getLocation().y - agtDrone.getLocation().y)/distance,
								attractionAcc.z + (agtOtherDrone.getLocation().z - agtDrone.getLocation().z)/distance
								);
						
 					}else if(distance < parameters.orientationDistance && distance > parameters.repulsionDistance){
						nbOfDronesInOrientationArea++;
						orientationAcc.set(
								orientationAcc.x + (agtOtherDrone.getAcceleration().x)/distance,
								orientationAcc.y + (agtOtherDrone.getAcceleration().y)/distance,
								orientationAcc.z + (agtOtherDrone.getAcceleration().z)/distance
								);
					}else{
						nbOfDronesInRepulsionArea++;
						if (distance > 0.001){
							repulsionAcc.set(
									repulsionAcc.x - (agtOtherDrone.getLocation().x - agtDrone.getLocation().x)/distance,
									repulsionAcc.y - (agtOtherDrone.getLocation().y - agtDrone.getLocation().y)/distance,
									repulsionAcc.z - (agtOtherDrone.getLocation().z - agtDrone.getLocation().z)/distance
									);
							
						}else{
							repulsionAcc.set(
									repulsionAcc.x - 1000*(agtOtherDrone.getLocation().x - agtDrone.getLocation().x),
									repulsionAcc.y - 1000*(agtOtherDrone.getLocation().y - agtDrone.getLocation().y),
									repulsionAcc.z - 1000*(agtOtherDrone.getLocation().z - agtDrone.getLocation().z)
									);
						}
						
					}
				}				
			}
			
			if (nbOfDronesInAttractionArea != 0){
				
				agtDrone.setInfluence(
						parameters.attractionCoeff * attractionAcc.x / nbOfDronesInAttractionArea,
						parameters.attractionCoeff * attractionAcc.y / nbOfDronesInAttractionArea,
						parameters.attractionCoeff * attractionAcc.z / nbOfDronesInAttractionArea
						);
			}
			if (nbOfDronesInRepulsionArea != 0){
				agtDrone.setInfluence(
						agtDrone.getInfluence().x + parameters.repulsionCoeff * repulsionAcc.x/nbOfDronesInRepulsionArea,
						agtDrone.getInfluence().y + parameters.repulsionCoeff * repulsionAcc.y/nbOfDronesInRepulsionArea,
						agtDrone.getInfluence().z + parameters.repulsionCoeff * repulsionAcc.z/nbOfDronesInRepulsionArea
						);
			}
			if (nbOfDronesInOrientationArea != 0){
				agtDrone.setInfluence(
						agtDrone.getInfluence().x + parameters.orientationCoeff * orientationAcc.x/nbOfDronesInOrientationArea,
						agtDrone.getInfluence().y + parameters.orientationCoeff * orientationAcc.y/nbOfDronesInOrientationArea,
						agtDrone.getInfluence().z + parameters.orientationCoeff * orientationAcc.z/nbOfDronesInOrientationArea
						);
			}
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
			
			agtDrone.setAcceleration(
					agtDrone.getAcceleration().x + agtDrone.getInfluence().x,
					agtDrone.getAcceleration().y + agtDrone.getInfluence().y,
					agtDrone.getAcceleration().z + agtDrone.getInfluence().z
					);
			//Keep the velocity vector under the maxSeed limit.
			double speed = Math.sqrt(Math.pow(agtDrone.getVelocity().x,2) +	Math.pow(agtDrone.getVelocity().y,2));
			if ( speed > parameters.maxSpeed){
				agtDrone.setVelocity(parameters.maxSpeed * agtDrone.getVelocity().x / speed ,
						parameters.maxSpeed * agtDrone.getVelocity().y / speed,
						parameters.maxSpeed * agtDrone.getVelocity().z / speed );
				agtDrone.setAcceleration(0, 0,0);
			}
			
			//Keep the acceleration vector under the maxAcc limit
			double acc = Math.sqrt(Math.pow(agtDrone.getAcceleration().x,2) +	Math.pow(agtDrone.getAcceleration().y,2));
			if ( acc > parameters.maxAcc){
				agtDrone.setAcceleration(parameters.maxAcc * agtDrone.getAcceleration().x / acc ,
						parameters.maxAcc * agtDrone.getAcceleration().y / acc,
						parameters.maxAcc * agtDrone.getAcceleration().z / acc);
			}
			
			agtDrone.setLocation(
					agtDrone.getLocation().x + agtDrone.getVelocity().x + agtDrone.getAcceleration().x / 2, 
					agtDrone.getLocation().y + agtDrone.getVelocity().y + agtDrone.getAcceleration().y / 2,
					agtDrone.getLocation().z + agtDrone.getVelocity().z + agtDrone.getAcceleration().z / 2
				);
			agtDrone.setVelocity(
						agtDrone.getVelocity().x + agtDrone.getAcceleration().x, 
						agtDrone.getVelocity().y + agtDrone.getAcceleration().y,
						agtDrone.getVelocity().z + agtDrone.getAcceleration().z
				);
			// Keep the Drones in the limits of the room
			if( agtDrone.getLocation().x > parameters.roomBounds.getWidth()) {
				agtDrone.setLocation(0,agtDrone.getLocation().y,agtDrone.getLocation().z);
			}
			if( agtDrone.getLocation().y > parameters.roomBounds.getHeight()) {
				agtDrone.setLocation(agtDrone.getLocation().x,0,agtDrone.getLocation().z);
			}
			if( agtDrone.getLocation().x < 0) {
				agtDrone.setLocation(parameters.roomBounds.getWidth(),agtDrone.getLocation().y,agtDrone.getLocation().z);
			}
			if( agtDrone.getLocation().y < 0) {
				agtDrone.setLocation(agtDrone.getLocation().x,parameters.roomBounds.getHeight(),agtDrone.getLocation().z);
			}
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
