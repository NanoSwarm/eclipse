package swarm.model.level.room;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.dynamicstate.ConsistentPublicLocalDynamicState;
import fr.lgi2a.similar.microkernel.influences.IInfluence;
import fr.lgi2a.similar.microkernel.influences.InfluencesMap;
import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractLevel;
import swarm.model.SwarmParameters;
import swarm.model.agents.simpleDrone.room.AgtSimpleDronePLSInRoom;
import swarm.model.environment.room.EnvPLSInRoom;
import swarm.model.influences.toRoom.RIUpdateDronesSpatialStateInRoom;
import swarm.model.level.SwarmLevelList;

public class RoomLevel extends AbstractLevel{
	
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
		// This reaction manages the reaction to the RIUpdateParticlesSpatialStateInChamber influences last.
		Set<AgtSimpleDronePLSInRoom> particlesUpdateList = new HashSet<AgtSimpleDronePLSInRoom>();
		
		for( IInfluence influence : regularInfluencesOftransitoryStateDynamics ){
			if( influence.getCategory().equals( RIUpdateDronesSpatialStateInRoom.CATEGORY ) ){
				RIUpdateDronesSpatialStateInRoom castedInfluence = (RIUpdateDronesSpatialStateInRoom) influence;
				particlesUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
				
				// Manage the reaction to the particles that were listed by the influences.
				this.reactionTo( 
						transitoryTimeMin,
						transitoryTimeMax,
						(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
						particlesUpdateList,
						castedInfluence,
						remainingInfluences
				);
			}else {
				throw new UnsupportedOperationException( 
					"The influence '" + influence.getCategory() + "' is currently not supported in this reaction." 
				);
			}
		}
	}
	
	/**
	 * The reaction to the sum of all the {@link RIUpdateDronesSpatialStateInRoom} influences
	 * that were sent to this level.
	 * @param transitoryTimeMin The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param transitoryTimeMax The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param chamberEnvState The public local state of the environment in the chamber level.
	 * @param particlesUpdateList The particles listed in these influences.
	 * @param RIwindonparticle The type of influence in order to manage the function overload.
	 * @param newInfluencesToProcess The data structure where the influences resulting from this user reaction have to be added.
	 */
	private void reactionTo( 
		SimulationTimeStamp transitoryTimeMin,
		SimulationTimeStamp transitoryTimeMax,
		EnvPLSInRoom chamberEnvState,
		Set<AgtSimpleDronePLSInRoom> particlesUpdateList,
		RIUpdateDronesSpatialStateInRoom influence,
		InfluencesMap remainingInfluences
	){		
		//Manage the behavior of one drones base on other drones position.
		for( AgtSimpleDronePLSInRoom simpleDrone : particlesUpdateList ){
			double[] repulsionAcc = {0, 0,0};
			int nbOfDronesInRepulsionArea = 0;
			double[] orientationAcc = {0, 0,0};
			int nbOfDronesInOrientationArea = 0;
			double[] attractionAcc = {0, 0,0};
			int nbOfDronesInAttractionArea = 0;	
			double[] totalAcc = {0, 0,0};
			
			for( AgtSimpleDronePLSInRoom simpleOtherDrone : particlesUpdateList ){
				if(simpleDrone != simpleOtherDrone){
					double distance =Math.sqrt(Math.pow(simpleDrone.getLocation().getX() - simpleOtherDrone.getLocation().getX(), 2)
											 + Math.pow(simpleDrone.getLocation().getY() - simpleOtherDrone.getLocation().getY(), 2)
											 + Math.pow(simpleDrone.getLocationZ() - simpleOtherDrone.getLocationZ(), 2));
					if (distance > parameters.attractionDistance){
						//does nothing
					}
					else if(distance < parameters.attractionDistance && distance > parameters.orientationDistance){
						nbOfDronesInAttractionArea++;
						attractionAcc[0] += (simpleOtherDrone.getLocation().getX() - simpleDrone.getLocation().getX())/distance;
						attractionAcc[1] += (simpleOtherDrone.getLocation().getY() - simpleDrone.getLocation().getY())/distance;
						attractionAcc[2] += (simpleOtherDrone.getLocationZ() - simpleDrone.getLocationZ())/distance;
 					}else if(distance < parameters.orientationDistance && distance > parameters.repulsionDistance){
						nbOfDronesInOrientationArea++;
						orientationAcc[0] += (simpleOtherDrone.getAcceleration().getX())/distance;
						orientationAcc[1] += (simpleOtherDrone.getAcceleration().getY())/distance;
						orientationAcc[2] += (simpleOtherDrone.getAccelerationZ())/distance;
					}else{
						nbOfDronesInRepulsionArea++;
						repulsionAcc[0] -= (simpleOtherDrone.getLocation().getX() - simpleDrone.getLocation().getX())/distance;
						repulsionAcc[1] -= (simpleOtherDrone.getLocation().getY() - simpleDrone.getLocation().getY())/distance;
						repulsionAcc[2] -= (simpleOtherDrone.getLocationZ() - simpleDrone.getLocationZ())/distance;
					}
				}				
			}
			
			if (nbOfDronesInAttractionArea != 0){
				attractionAcc[0] /= nbOfDronesInAttractionArea;
				attractionAcc[1] /= nbOfDronesInAttractionArea;
				attractionAcc[2] /= nbOfDronesInAttractionArea;
			}
			if (nbOfDronesInRepulsionArea != 0){
				repulsionAcc[0] = 1000/(repulsionAcc[0]*nbOfDronesInRepulsionArea);
				repulsionAcc[1] = 1000/(repulsionAcc[1]*nbOfDronesInRepulsionArea);
				repulsionAcc[2] = 1000/(repulsionAcc[2]*nbOfDronesInRepulsionArea);
			}
			if (nbOfDronesInOrientationArea != 0){
				orientationAcc[0] /= nbOfDronesInOrientationArea;
				orientationAcc[1] /= nbOfDronesInOrientationArea;
				orientationAcc[2] /= nbOfDronesInOrientationArea;
			}
			totalAcc[0] = simpleDrone.getAcceleration().getX() + parameters.attractionCoeff * attractionAcc[0] + parameters.repulsionCoeff * repulsionAcc[0] + parameters.orientationCoeff * orientationAcc[0];
			totalAcc[1] = simpleDrone.getAcceleration().getY() + parameters.attractionCoeff * attractionAcc[1] + parameters.repulsionCoeff * repulsionAcc[1] + parameters.orientationCoeff * orientationAcc[1];
			totalAcc[2] = simpleDrone.getAccelerationZ() + parameters.attractionCoeff * attractionAcc[2] + parameters.repulsionCoeff * repulsionAcc[2] + parameters.orientationCoeff * orientationAcc[2];
			
			if ( totalAcc[0] > parameters.maxAcc){
				totalAcc[0] = parameters.maxAcc;
			}else if(totalAcc[0] < -parameters.maxAcc){
				totalAcc[0] = -parameters.maxAcc;
			}
			
			if ( totalAcc[1] > parameters.maxAcc){
				totalAcc[1] = parameters.maxAcc;
			}else if(totalAcc[1] < -parameters.maxAcc){
				totalAcc[1] = -parameters.maxAcc;
			}
			if ( totalAcc[2] > parameters.maxAcc){
				totalAcc[2] = parameters.maxAcc;
			}else if(totalAcc[2] < -parameters.maxAcc){
				totalAcc[2] = -parameters.maxAcc;
			}
			simpleDrone.setAcceleration(totalAcc[0],totalAcc[1]);
			simpleDrone.setAccelerationZ(totalAcc[2]);
		}
		
		//Update the position of drones in the update list.
		for( AgtSimpleDronePLSInRoom simpleDrone : particlesUpdateList ){
						
			simpleDrone.setLocation(
					simpleDrone.getLocation().getX() + simpleDrone.getVelocity().getX() + simpleDrone.getAcceleration().getX() / 2, 
					simpleDrone.getLocation().getY() + simpleDrone.getVelocity().getY() + simpleDrone.getAcceleration().getY() / 2
				);
			simpleDrone.setLocationZ(simpleDrone.getLocationZ() + simpleDrone.getVelocityZ() + simpleDrone.getAccelerationZ() / 2
				);
			simpleDrone.setVelocity(
						simpleDrone.getVelocity().getX() + simpleDrone.getAcceleration().getX(), 
						simpleDrone.getVelocity().getY() + simpleDrone.getAcceleration().getY()
				);
			simpleDrone.setVelocityZ(simpleDrone.getVelocityZ() + simpleDrone.getAccelerationZ() );
			//Keep the velocity under the maxSeed limit.
			if ( simpleDrone.getVelocity().getX() > parameters.maxSpeed){
				simpleDrone.setVelocity(parameters.maxSpeed ,simpleDrone.getVelocity().getY());
			}else if(simpleDrone.getVelocity().getX() < -parameters.maxSpeed){
				simpleDrone.setVelocity(-parameters.maxSpeed ,simpleDrone.getVelocity().getY());
			}
			
			if ( simpleDrone.getVelocity().getY() > parameters.maxSpeed){
				simpleDrone.setVelocity(simpleDrone.getVelocity().getX() ,parameters.maxSpeed);
			}else if(simpleDrone.getVelocity().getY() < -parameters.maxSpeed){
				simpleDrone.setVelocity(simpleDrone.getVelocity().getX() ,-parameters.maxSpeed);
			}
			if ( simpleDrone.getVelocityZ() > parameters.maxSpeed) simpleDrone.setVelocityZ(parameters.maxSpeed);
	
				// Keep the Drones in the limits of the room
				if( simpleDrone.getLocation().getX() > parameters.roomBounds.getWidth()) {
					simpleDrone.setLocation(0,
											simpleDrone.getLocation().getY());
				}
				if( simpleDrone.getLocation().getY() > parameters.roomBounds.getHeight()) {
					simpleDrone.setLocation(simpleDrone.getLocation().getX(),
											0);
				}
				if( simpleDrone.getLocation().getX() < 0) {
					simpleDrone.setLocation(parameters.roomBounds.getWidth(),
											simpleDrone.getLocation().getY());
				}
				if( simpleDrone.getLocation().getY() < 0) {
					simpleDrone.setLocation(simpleDrone.getLocation().getX(),
											parameters.roomBounds.getHeight());
				}
			}
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
			InfluencesMap newInfluencesToProcess
	) {
		// Does nothing here.
	}
	
	
}
