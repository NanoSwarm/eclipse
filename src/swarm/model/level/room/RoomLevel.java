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
import swarm.SwarmMain;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.environment.room.EnvPLSInRoom;
import swarm.model.influences.toRoom.RIUpdateCameraDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateCommunicatorDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateDroneEnergyLevelInRoom;
import swarm.model.influences.toRoom.RIUpdateDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateMeasurementDroneSpatialStateInRoom;
import swarm.model.influences.toRoom.RIUpdateMicrophoneDroneSpatialStateInRoom;
import swarm.model.level.SwarmLevelList;

public class RoomLevel extends AbstractLevel {
	
	public static double bestAllFitness=0;
	public static Vector3d bestAllPos=new Vector3d(0,0,0);
	
	/**
	 * Builds an uninitialized instance of this level.
	 * <p>
	 * 	The public local state of the environment in the level has 
	 * 	to be set during the initialization phase of the simulation.
	 * </p>
	 * @param initialTime The initial time of the simulation.
	 * @param SwarmMain.getSimulationModel().getParameters() the different SwarmMain.getSimulationModel().getParameters() of the simulation
	 */
	public RoomLevel( SimulationTimeStamp initialTime) {
		super(
			initialTime,
			SwarmLevelList.ROOM
		);
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
		Set<AgtDronePLSInRoom> energyUpdateList = new HashSet<AgtDronePLSInRoom>();
		Set<AgtMeasurementDronePLSInRoom> measurementUpdateList = new HashSet<AgtMeasurementDronePLSInRoom>();
		
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
				
			}else if( influence.getCategory().equals( RIUpdateMeasurementDroneSpatialStateInRoom.CATEGORY )){
				RIUpdateMeasurementDroneSpatialStateInRoom castedInfluence = (RIUpdateMeasurementDroneSpatialStateInRoom) influence;
				measurementUpdateList.addAll( castedInfluence.getParticlesToUpdate() );	
				droneUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
				
			}else if( influence.getCategory().equals( RIUpdateDroneEnergyLevelInRoom.CATEGORY )){
				RIUpdateDroneEnergyLevelInRoom castedInfluence = (RIUpdateDroneEnergyLevelInRoom) influence;
				energyUpdateList.addAll( castedInfluence.getParticlesToUpdate() );
			
			}else{
				throw new UnsupportedOperationException( 
					"The influence '" + influence.getCategory() + "' is currently not supported in this reaction." 
				);
			}
		}
		
		if (SwarmMain.getSimulationModel().getParameters().resolutionType == 1 && (SwarmMain.getSimulationModel().getParameters().objectiveType == 2 || SwarmMain.getSimulationModel().getParameters().objectiveType == 1)){
			SwarmMain.getSimulationModel().getGraph().updateCostMatrix(droneUpdateList);
		}
		
		for (AgtDronePLSInRoom agtDrone : droneUpdateList){
			UpdateInfluenceInRoom.UpdateDroneInfluence(agtDrone, droneUpdateList, SwarmMain.getSimulationModel().getParameters());
		}
		
		//Influences on "camera drone" agents
		for(AgtCameraDronePLSInRoom agtCameraDrone: cameraUpdateList){
			UpdateInfluenceInRoom.UpdateCameraDroneInfluence(agtCameraDrone, SwarmMain.getSimulationModel().getParameters());
		}
		
		//Influences on "communicator drone" agents
		for(AgtCommunicatorDronePLSInRoom agtCommunicatorDrone: communicatorUpdateList){
			UpdateInfluenceInRoom.UpdateCommunicatorDroneInfluence(agtCommunicatorDrone, SwarmMain.getSimulationModel().getParameters());
		}
		
		//Influences on "microphone drones" agents
		for(AgtMicrophoneDronePLSInRoom agtMicrophoneDrone : microphoneUpdateList){
			UpdateInfluenceInRoom.UpdateMicrophoneDroneInfluence(agtMicrophoneDrone, SwarmMain.getSimulationModel().getParameters());
		}
		
		//Influences on "measurement drones" agents
		for(AgtMeasurementDronePLSInRoom agtMeasurementDrone : measurementUpdateList){
			if (agtMeasurementDrone.bestOwnFitness>bestAllFitness && SwarmMain.getSimulationModel().getParameters().resolutionType == 2 && SwarmMain.getSimulationModel().getParameters().objectiveType == 2)
				{
				bestAllFitness=agtMeasurementDrone.bestOwnFitness;
				bestAllPos.set(agtMeasurementDrone.getLocation().getX(),agtMeasurementDrone.getLocation().getY(),agtMeasurementDrone.getLocation().getZ());
				//System.out.println("4511"+bestAllFitness+" "+bestAllPos.getX()+" "+bestAllPos.getY()+" "+bestAllPos.getZ());
				}
			
			UpdateInfluenceInRoom.UpdateMeasurementDroneInfluence(agtMeasurementDrone,droneUpdateList, SwarmMain.getSimulationModel().getParameters(),bestAllPos);
		//	System.out.println(bestAllFitness+" "+bestAllPos.getX()+" "+bestAllPos.getY()+" "+bestAllPos.getZ());
			
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
		
		this.microphoneReactionTo( 
				transitoryTimeMin,
				transitoryTimeMax,
				(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
				microphoneUpdateList,
				remainingInfluences
		);
		
		this.measurementReactionTo(
				transitoryTimeMin,
				transitoryTimeMax,
				(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
				measurementUpdateList,
				remainingInfluences
		);
		
		this.droneReactionTo( 
				transitoryTimeMin,
				transitoryTimeMax,
				(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
				droneUpdateList,
				remainingInfluences
		);
		
		this.energyReactionTo(
				transitoryTimeMin,
				transitoryTimeMax,
				(EnvPLSInRoom) consistentState.getPublicLocalStateOfEnvironment(),
				energyUpdateList,
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
	 * The reaction to the sum of all the {@link RIUpdateMeasurementDroneSpatialStateInRoom} influences
	 * that were sent to this level.
	 * @param transitoryTimeMin The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param transitoryTimeMax The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param roomEnvState The public local state of the environment in the chamber level.
	 * @param dronesUpdateList The drones listed in these influences.
	 * @param remainingInfluences The data structure where the influences resulting from this user reaction have to be added.
	 */
	private void measurementReactionTo( 
		SimulationTimeStamp transitoryTimeMin,
		SimulationTimeStamp transitoryTimeMax,
		EnvPLSInRoom roomEnvState,
		Set<AgtMeasurementDronePLSInRoom> dronesUpdateList,
		InfluencesMap remainingInfluences
	){	
		if (SwarmMain.getSimulationModel().getParameters().resolutionType == 2 && SwarmMain.getSimulationModel().getParameters().objectiveType == 2) {
			for (AgtDronePLSInRoom agtDrone : dronesUpdateList) {
				UpdatePositionInRoom.UpdateDronePosition(agtDrone, SwarmMain.getSimulationModel().getParameters(), roomEnvState);

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
	private void droneReactionTo( 
		SimulationTimeStamp transitoryTimeMin,
		SimulationTimeStamp transitoryTimeMax,
		EnvPLSInRoom roomEnvState,
		Set<AgtDronePLSInRoom> dronesUpdateList,
		InfluencesMap remainingInfluences
	){		
		if (SwarmMain.getSimulationModel().getParameters().resolutionType != 2 || SwarmMain.getSimulationModel().getParameters().objectiveType != 2) {
			for (AgtDronePLSInRoom agtDrone : dronesUpdateList) {

				UpdatePositionInRoom.UpdateDronePosition(agtDrone, SwarmMain.getSimulationModel().getParameters(),roomEnvState);

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
	 * The reaction to the sum of all the {@link RIUpdateEnergyLevelInRoom} influences
	 * that were sent to this level.
	 * @param transitoryTimeMin The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param transitoryTimeMax The lower bound of the transitory period of the level for which this reaction is performed.
	 * @param roomEnvState The public local state of the environment in the chamber level.
	 * @param dronesUpdateList The drones listed in these influences.
	 * @param remainingInfluences The data structure where the influences resulting from this user reaction have to be added.
	 */
	private void energyReactionTo(
			SimulationTimeStamp transitoryTimeMin,
			SimulationTimeStamp transitoryTimeMax,
			EnvPLSInRoom roomEnvState,
			Set<AgtDronePLSInRoom> dronesUpdateList,
			InfluencesMap remainingInfluences
	){
		int i = 0;
		for(AgtDronePLSInRoom agtDrone : dronesUpdateList){
			
			i += UpdateEnergyLevelInRoom.updateEnergy(agtDrone, SwarmMain.getSimulationModel().getParameters()); //Count the number of dead drones and update energy levels
			
		}
		if (i == (SwarmMain.getSimulationModel().getParameters().nbOfCameraDroneAgents 
				+ SwarmMain.getSimulationModel().getParameters().nbOfCommunicatorDroneAgents
				+ SwarmMain.getSimulationModel().getParameters().nbOfDroneAgents 
				+ SwarmMain.getSimulationModel().getParameters().nbOfMeasurementDroneAgents 
				+ SwarmMain.getSimulationModel().getParameters().nbOfMicrophoneDroneAgents)){
			System.out.println("Objective not found, energy depleted. ");
			SwarmMain.abordSimulation();
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
			InfluencesMap remainingInfluences
	) {
		// Does nothing here.
	}
	
	
}
