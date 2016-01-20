package swarm.model.level.room;

import java.util.Set;

import javax.vecmath.Vector3d;

import swarm.SwarmMain;
import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.environment.Objective;
import swarm.tools.RandomValueFactory;

public class UpdateInfluenceInRoom {
	
	/**
	 * General influences on all drones, this one is calculated last
	 * @param agtDrone the drone concerned by the influence
	 * @param droneUpdateList the list of all drones in the simulation
	 * @param parameters the parameters of the simulation
	 */

	public static void UpdateDroneInfluence(AgtDronePLSInRoom agtDrone, Set<AgtDronePLSInRoom> droneUpdateList, SwarmParameters parameters){
		
		
		//Calculation of different influences
		Vector3d repulsionAcc = new Vector3d();
		Vector3d orientationAcc = new Vector3d();
		Vector3d attractionAcc = new Vector3d();
		int nbOfDronesInRepulsionArea = 0;
		int nbOfDronesInOrientationArea = 0;
		int nbOfDronesInAttractionArea = 0;
		agtDrone.setInfluence(0, 0, 0);
		repulsionAcc.set(0, 0, 0);
		orientationAcc.set(0, 0, 0);
		attractionAcc.set(0, 0, 0);
		nbOfDronesInRepulsionArea = 0;
		nbOfDronesInOrientationArea = 0;
		nbOfDronesInAttractionArea = 0;
		for (AgtDronePLSInRoom agtOtherDrone : droneUpdateList) {
			if (agtDrone != agtOtherDrone) {
				double distance = Math.sqrt(Math.pow(agtDrone.getLocation().x - agtOtherDrone.getLocation().x, 2)
						+ Math.pow(agtDrone.getLocation().y - agtOtherDrone.getLocation().y, 2)
						+ Math.pow(agtDrone.getLocation().z - agtOtherDrone.getLocation().z, 2));
				if (distance > parameters.attractionDistance) {
					//does nothing
				} else if (distance < parameters.attractionDistance && distance > parameters.orientationDistance
						&& agtOtherDrone.getEnergy() != 0 && parameters.objectiveType == 3) {
					nbOfDronesInAttractionArea++;
					attractionAcc.set(
							attractionAcc.x + (agtOtherDrone.getLocation().x - agtDrone.getLocation().x) / distance,
							attractionAcc.y + (agtOtherDrone.getLocation().y - agtDrone.getLocation().y) / distance,
							attractionAcc.z
									+ (agtOtherDrone.getLocation().z - agtDrone.getLocation().z) / distance);

				} else if (distance < parameters.orientationDistance && distance > parameters.repulsionDistance
						&& agtOtherDrone.getEnergy() != 0 && parameters.objectiveType == 3) {
					nbOfDronesInOrientationArea++;
					orientationAcc.set(orientationAcc.x + (agtOtherDrone.getAcceleration().x) / distance,
							orientationAcc.y + (agtOtherDrone.getAcceleration().y) / distance,
							orientationAcc.z + (agtOtherDrone.getAcceleration().z) / distance);
				} else if (distance < parameters.repulsionDistance) {
					nbOfDronesInRepulsionArea++;
					if (distance > 0.001) {
						repulsionAcc.set(
								repulsionAcc.x
										- (agtOtherDrone.getLocation().x - agtDrone.getLocation().x) / distance,
								repulsionAcc.y
										- (agtOtherDrone.getLocation().y - agtDrone.getLocation().y) / distance,
								repulsionAcc.z
										- (agtOtherDrone.getLocation().z - agtDrone.getLocation().z) / distance);

					} else {
						repulsionAcc.set(
								repulsionAcc.x - 1000 * (agtOtherDrone.getLocation().x - agtDrone.getLocation().x),
								repulsionAcc.y - 1000 * (agtOtherDrone.getLocation().y - agtDrone.getLocation().y),
								repulsionAcc.z - 1000 * (agtOtherDrone.getLocation().z - agtDrone.getLocation().z));
					}

				}

			}
		}
		
		if (parameters.resolutionType == "position minimum" && (parameters.objectiveType == 1 || parameters.objectiveType == 2) ){
			double distance = Math.sqrt(Math.pow(agtDrone.getLocation().x - agtDrone.getDestination().x, 2)
					+ Math.pow(agtDrone.getLocation().y - agtDrone.getDestination().y, 2)
					+ Math.pow(agtDrone.getLocation().z - agtDrone.getDestination().z, 2));
			
			SwarmMain.getSimulationModel().getGraph().assignedDrone(agtDrone);
			attractionAcc.set(
					(agtDrone.getLocation().x - agtDrone.getDestination().x)/distance,
					(agtDrone.getLocation().y - agtDrone.getDestination().y)/distance,
					(agtDrone.getLocation().z - agtDrone.getDestination().z)/distance);
			nbOfDronesInAttractionArea = 1;
		}
		
		
		if (agtDrone.getLocation().x < parameters.securityDistance) {
			agtDrone.setInfluence(5 * parameters.maxAcc, 0, 0);
		} else if (agtDrone.getLocation().x > parameters.roomBounds.x - parameters.securityDistance) {
			agtDrone.setInfluence(-5 * parameters.maxAcc, 0, 0);
		}
		if (agtDrone.getLocation().y < parameters.securityDistance) {
			agtDrone.setInfluence(0, 5 * parameters.maxAcc, 0);
		} else if (agtDrone.getLocation().y > parameters.roomBounds.y - parameters.securityDistance) {
			agtDrone.setInfluence(0, -5 * parameters.maxAcc, 0);
		}
		if (agtDrone.getLocation().z < parameters.securityDistance) {
			agtDrone.setInfluence(0, 0, 5 * parameters.maxAcc);
		} else if (agtDrone.getLocation().z > parameters.roomBounds.z - parameters.securityDistance) {
			agtDrone.setInfluence(0, 0, -5 * parameters.maxAcc);
		}
		//Keep the influences vector under the maxAcc limit
		double acc = Math.sqrt(Math
				.pow(parameters.attractionCoeff * attractionAcc.x + parameters.orientationCoeff * orientationAcc.x
						+ parameters.repulsionCoeff * repulsionAcc.x, 2)
				+ Math.pow(parameters.attractionCoeff * attractionAcc.y
						+ parameters.orientationCoeff * orientationAcc.y
						+ parameters.repulsionCoeff * repulsionAcc.y, 2)
				+ Math.pow(parameters.attractionCoeff * attractionAcc.z
						+ parameters.orientationCoeff * orientationAcc.z
						+ parameters.repulsionCoeff * repulsionAcc.z, 2));
		if (acc > parameters.maxAcc) {

			attractionAcc.set(parameters.maxAcc * (parameters.attractionCoeff * attractionAcc.x) / acc,
					parameters.maxAcc * (parameters.attractionCoeff * attractionAcc.y) / acc,
					parameters.maxAcc * (parameters.attractionCoeff * attractionAcc.z) / acc);
			orientationAcc.set(parameters.maxAcc * (parameters.orientationCoeff * orientationAcc.x) / acc,
					parameters.maxAcc * (parameters.orientationCoeff * orientationAcc.y) / acc,
					parameters.maxAcc * (parameters.orientationCoeff * orientationAcc.z) / acc);
			repulsionAcc.set(parameters.maxAcc * (parameters.repulsionCoeff * repulsionAcc.x) / acc,
					parameters.maxAcc * (parameters.repulsionCoeff * repulsionAcc.y) / acc,
					parameters.maxAcc * (parameters.repulsionCoeff * repulsionAcc.z) / acc);
		}
		if (nbOfDronesInAttractionArea != 0) {

			agtDrone.setInfluence(agtDrone.getInfluence().x + attractionAcc.x / nbOfDronesInAttractionArea,
					agtDrone.getInfluence().y + attractionAcc.y / nbOfDronesInAttractionArea,
					agtDrone.getInfluence().z + attractionAcc.z / nbOfDronesInAttractionArea);
		}
		if (nbOfDronesInRepulsionArea != 0) {
			agtDrone.setInfluence(agtDrone.getInfluence().x + repulsionAcc.x / nbOfDronesInRepulsionArea,
					agtDrone.getInfluence().y + repulsionAcc.y / nbOfDronesInRepulsionArea,
					agtDrone.getInfluence().z + repulsionAcc.z / nbOfDronesInRepulsionArea);
		}
		if (nbOfDronesInOrientationArea != 0) {
			agtDrone.setInfluence(agtDrone.getInfluence().x + orientationAcc.x / nbOfDronesInOrientationArea,
					agtDrone.getInfluence().y + orientationAcc.y / nbOfDronesInOrientationArea,
					agtDrone.getInfluence().z + orientationAcc.z / nbOfDronesInOrientationArea);
		}
	}
		
	
	
	/**
	 * 
	 * @param agtCameraDrone
	 */
	public static void UpdateCameraDroneInfluence(AgtCameraDronePLSInRoom agtCameraDrone, SwarmParameters parameters){


		if (parameters.objectiveType == 1) {
			if (Objective.getObjective(agtCameraDrone.getLocation()) < parameters.cameraDroneDetectionRange) {
				System.out.println("Objective found by " + agtCameraDrone.hashCode() + "as cameraDrone agent.\n");
				SwarmMain.abordSimulation();
			} 
		}else if(parameters.objectiveType == 2){
			
		}
	}
	
	/**
	 * 
	 * @param agtCommunicatorDrone
	 */
	public static void UpdateCommunicatorDroneInfluence(AgtCommunicatorDronePLSInRoom agtCommunicatorDrone, SwarmParameters parameters){

		if (parameters.objectiveType == 1) {
			if (Objective.getObjective(agtCommunicatorDrone.getLocation()) < parameters.communicatorDroneDetectionRange ){
				System.out.println("Objective found by " + agtCommunicatorDrone.hashCode() + "as communicatorDrone agent.\n");
				SwarmMain.abordSimulation();
			}
		}else if(parameters.objectiveType == 2){
			
		}
	}
	
	/**
	 * 
	 * @param agtMicrophoneDrone
	 */
	public static void UpdateMicrophoneDroneInfluence(AgtMicrophoneDronePLSInRoom agtMicrophoneDrone, SwarmParameters parameters){
		
		if (parameters.objectiveType == 1) {
			if (Objective.getObjective(agtMicrophoneDrone.getLocation()) < parameters.microphoneDroneDetectionRange ){
				System.out.println("Objective found by " + agtMicrophoneDrone.hashCode() + "as microphoneDrone agent.\n");
				SwarmMain.abordSimulation();
			}
		}else if(parameters.objectiveType == 2){
			
		}
	}
	
	/**
	 * 
	 * @param agtmeasurementDrone
	 */
	public static void UpdateMeasurementDroneInfluence(AgtMeasurementDronePLSInRoom agtMeasurementDrone,Set<AgtDronePLSInRoom> droneUpdateList, SwarmParameters parameters, Vector3d bestPos){
		
		if (parameters.objectiveType == 1) {
			if (Objective.getObjective(agtMeasurementDrone.getLocation()) < parameters.measurementDroneDetectionRange ){
				System.out.println("Objective found by " + agtMeasurementDrone.hashCode() + "as measurementDrone agent.\n");
				SwarmMain.abordSimulation();
			}
		}else if(parameters.resolutionType == "pso" && parameters.objectiveType == 2){
			Vector3d repulsionAcc = new Vector3d(); 
			int nbOfDronesInRepulsionArea;
			//Calculation of different influences	
			repulsionAcc.set(0, 0,0);
			nbOfDronesInRepulsionArea = 0;
			agtMeasurementDrone.calculateFitness();
			agtMeasurementDrone.updateFitness();
			double alpha=0.99;
			double beta=0.5;
			agtMeasurementDrone.setInfluence(
					alpha*agtMeasurementDrone.getInfluence().getX()
					+RandomValueFactory.getStrategy().randomDouble(0,beta)*(agtMeasurementDrone.bestOwnPos.getX()-agtMeasurementDrone.getLocation().getX())
					+RandomValueFactory.getStrategy().randomDouble(0,beta)*(bestPos.getX()-agtMeasurementDrone.getLocation().getX()),
					alpha*agtMeasurementDrone.getInfluence().getY()
					+RandomValueFactory.getStrategy().randomDouble(0,beta)*(agtMeasurementDrone.bestOwnPos.getY()-agtMeasurementDrone.getLocation().getY())
					+RandomValueFactory.getStrategy().randomDouble(0,beta)*(bestPos.getY()-agtMeasurementDrone.getLocation().getY()),
					alpha*agtMeasurementDrone.getInfluence().getZ()
					+RandomValueFactory.getStrategy().randomDouble(0,beta)*(agtMeasurementDrone.bestOwnPos.getZ()-agtMeasurementDrone.getLocation().getZ())
					+RandomValueFactory.getStrategy().randomDouble(0,beta)*(bestPos.getZ()-agtMeasurementDrone.getLocation().getZ())
					);
			
			for (AgtDronePLSInRoom agtOtherDrone : droneUpdateList) {
				if (agtMeasurementDrone != agtOtherDrone) {
					double distance = Math.sqrt(Math.pow(agtMeasurementDrone.getLocation().x - agtOtherDrone.getLocation().x, 2)
							+ Math.pow(agtMeasurementDrone.getLocation().y - agtOtherDrone.getLocation().y, 2)
							+ Math.pow(agtMeasurementDrone.getLocation().z - agtOtherDrone.getLocation().z, 2));
			if (distance < parameters.repulsionDistance) {
				nbOfDronesInRepulsionArea++;
				if (distance > 0.001) {
					repulsionAcc.set(
							repulsionAcc.x
									- (agtOtherDrone.getLocation().x - agtMeasurementDrone.getLocation().x) / distance,
							repulsionAcc.y
									- (agtOtherDrone.getLocation().y - agtMeasurementDrone.getLocation().y) / distance,
							repulsionAcc.z
									- (agtOtherDrone.getLocation().z - agtMeasurementDrone.getLocation().z) / distance);

				} else {
					repulsionAcc.set(
							repulsionAcc.x - 1000 * (agtOtherDrone.getLocation().x - agtMeasurementDrone.getLocation().x),
							repulsionAcc.y - 1000 * (agtOtherDrone.getLocation().y - agtMeasurementDrone.getLocation().y),
							repulsionAcc.z - 1000 * (agtOtherDrone.getLocation().z - agtMeasurementDrone.getLocation().z));
				}
			}
			if (nbOfDronesInRepulsionArea != 0) {
				agtMeasurementDrone.setInfluence(agtMeasurementDrone.getInfluence().x + repulsionAcc.x / nbOfDronesInRepulsionArea,
						agtMeasurementDrone.getInfluence().y + repulsionAcc.y / nbOfDronesInRepulsionArea,
						agtMeasurementDrone.getInfluence().z + repulsionAcc.z / nbOfDronesInRepulsionArea);
			}
				}
			}
			if (agtMeasurementDrone.getLocation().x < parameters.securityDistance ){
				agtMeasurementDrone.setInfluence(5*parameters.maxAcc,0,0);
			}else if (agtMeasurementDrone.getLocation().x > parameters.roomBounds.x - parameters.securityDistance){
				agtMeasurementDrone.setInfluence(-5*parameters.maxAcc,0,0);
			}
			
			if (agtMeasurementDrone.getLocation().y < parameters.securityDistance ){
				agtMeasurementDrone.setInfluence(0,5*parameters.maxAcc,0);
			}else if (agtMeasurementDrone.getLocation().y > parameters.roomBounds.y - parameters.securityDistance){
				agtMeasurementDrone.setInfluence(0,-5*parameters.maxAcc,0);
			}

			if (agtMeasurementDrone.getLocation().z < parameters.securityDistance ){
				agtMeasurementDrone.setInfluence(0,0,5*parameters.maxAcc);
			}else if (agtMeasurementDrone.getLocation().z > parameters.roomBounds.z - parameters.securityDistance){
				agtMeasurementDrone.setInfluence(0,0,-5*parameters.maxAcc);
			}
		}else if (parameters.resolutionType == "position minimum" && (parameters.objectiveType == 1 || parameters.objectiveType == 2)){
			
		}
	}
}
