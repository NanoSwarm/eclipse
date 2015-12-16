package swarm.model.level.room;

import java.util.Set;

import javax.vecmath.Vector3d;

import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;

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
		repulsionAcc.set(0, 0,0);
		orientationAcc.set(0, 0,0);
		attractionAcc.set(0, 0,0);
		nbOfDronesInRepulsionArea = 0;
		nbOfDronesInOrientationArea = 0;
		nbOfDronesInAttractionArea = 0;	
		
		for( AgtDronePLSInRoom agtOtherDrone : droneUpdateList ){
			if(agtDrone != agtOtherDrone){
				double distance = Math.sqrt(Math.pow(agtDrone.getLocation().x - agtOtherDrone.getLocation().x, 2)
										  + Math.pow(agtDrone.getLocation().y - agtOtherDrone.getLocation().y, 2)
										  + Math.pow(agtDrone.getLocation().z - agtOtherDrone.getLocation().z, 2));
				if (distance > parameters.attractionDistance){
					//does nothing
				}else if(distance < parameters.attractionDistance && distance > parameters.orientationDistance  && agtOtherDrone.getEnergy()!=0){
					nbOfDronesInAttractionArea++;
					attractionAcc.set(
							attractionAcc.x + (agtOtherDrone.getLocation().x - agtDrone.getLocation().x)/distance,
							attractionAcc.y + (agtOtherDrone.getLocation().y - agtDrone.getLocation().y)/distance,
							attractionAcc.z + (agtOtherDrone.getLocation().z - agtDrone.getLocation().z)/distance
							);
					
				}else if(distance < parameters.orientationDistance && distance > parameters.repulsionDistance  && agtOtherDrone.getEnergy()!=0){
					nbOfDronesInOrientationArea++;
					orientationAcc.set(
							orientationAcc.x + (agtOtherDrone.getAcceleration().x)/distance,
							orientationAcc.y + (agtOtherDrone.getAcceleration().y)/distance,
							orientationAcc.z + (agtOtherDrone.getAcceleration().z)/distance
							);
				}else if(distance < parameters.repulsionDistance) {
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
		
		if (agtDrone.getLocation().x < parameters.securityDistance ){
			agtDrone.setInfluence(5*parameters.maxAcc,0,0);
		}else if (agtDrone.getLocation().x > parameters.roomBounds.x - parameters.securityDistance){
			agtDrone.setInfluence(-5*parameters.maxAcc,0,0);
		}
		
		if (agtDrone.getLocation().y < parameters.securityDistance ){
			agtDrone.setInfluence(0,5*parameters.maxAcc,0);
		}else if (agtDrone.getLocation().y > parameters.roomBounds.y - parameters.securityDistance){
			agtDrone.setInfluence(0,-5*parameters.maxAcc,0);
		}

		if (agtDrone.getLocation().z < parameters.securityDistance ){
			agtDrone.setInfluence(0,0,5*parameters.maxAcc);
		}else if (agtDrone.getLocation().z > parameters.roomBounds.z - parameters.securityDistance){
			agtDrone.setInfluence(0,0,-5*parameters.maxAcc);
		}
		
		//Keep the influences vector under the maxAcc limit
		double acc = Math.sqrt(
				Math.pow(parameters.attractionCoeff * attractionAcc.x + parameters.orientationCoeff * orientationAcc.x + parameters.repulsionCoeff * repulsionAcc.x ,2)+	
				Math.pow(parameters.attractionCoeff * attractionAcc.y + parameters.orientationCoeff * orientationAcc.y + parameters.repulsionCoeff * repulsionAcc.y ,2)+	
				Math.pow(parameters.attractionCoeff * attractionAcc.z + parameters.orientationCoeff * orientationAcc.z + parameters.repulsionCoeff * repulsionAcc.z ,2));
		
		if ( acc > parameters.maxAcc){
			
			
			attractionAcc.set(
					parameters.maxAcc * ( parameters.attractionCoeff * attractionAcc.x) / acc ,
					parameters.maxAcc * ( parameters.attractionCoeff * attractionAcc.y) / acc,
					parameters.maxAcc * ( parameters.attractionCoeff * attractionAcc.z) / acc
					);
			orientationAcc.set(
					parameters.maxAcc * (parameters.orientationCoeff * orientationAcc.x) / acc ,
					parameters.maxAcc * (parameters.orientationCoeff * orientationAcc.y) / acc,
					parameters.maxAcc * (parameters.orientationCoeff * orientationAcc.z) / acc
					);
			repulsionAcc.set(
					parameters.maxAcc * (parameters.repulsionCoeff * repulsionAcc.x) / acc ,
					parameters.maxAcc * (parameters.repulsionCoeff * repulsionAcc.y) / acc,
					parameters.maxAcc * (parameters.repulsionCoeff * repulsionAcc.z) / acc
					);
		}
		
		if (nbOfDronesInAttractionArea != 0){
			
			agtDrone.setInfluence(
					agtDrone.getInfluence().x + attractionAcc.x / nbOfDronesInAttractionArea,
					agtDrone.getInfluence().y + attractionAcc.y / nbOfDronesInAttractionArea,
					agtDrone.getInfluence().z + attractionAcc.z / nbOfDronesInAttractionArea
					);
		}
		if (nbOfDronesInRepulsionArea != 0){
			agtDrone.setInfluence(
					agtDrone.getInfluence().x + repulsionAcc.x/nbOfDronesInRepulsionArea,
					agtDrone.getInfluence().y + repulsionAcc.y/nbOfDronesInRepulsionArea,
					agtDrone.getInfluence().z + repulsionAcc.z/nbOfDronesInRepulsionArea
					);
		}
		if (nbOfDronesInOrientationArea != 0){
			agtDrone.setInfluence(
					agtDrone.getInfluence().x + orientationAcc.x/nbOfDronesInOrientationArea,
					agtDrone.getInfluence().y + orientationAcc.y/nbOfDronesInOrientationArea,
					agtDrone.getInfluence().z + orientationAcc.z/nbOfDronesInOrientationArea
					);
		}
	}
	
	/**
	 * 
	 * @param agtCameraDrone
	 */
	public static void UpdateCameraDroneInfluence(AgtCameraDronePLSInRoom agtCameraDrone){
		agtCameraDrone.setInfluence(
				agtCameraDrone.getInfluence().x, 
				agtCameraDrone.getInfluence().y,
				agtCameraDrone.getInfluence().z
				);
	}
	
	/**
	 * 
	 * @param agtCommunicatorDrone
	 */
	public static void UpdateCommunicatorDroneInfluence(AgtCommunicatorDronePLSInRoom agtCommunicatorDrone){
		agtCommunicatorDrone.setInfluence(
				agtCommunicatorDrone.getInfluence().x,
				agtCommunicatorDrone.getInfluence().y,
				agtCommunicatorDrone.getInfluence().z
				);
	}
	
	/**
	 * 
	 * @param agtMicrophoneDrone
	 */
	public static void UpdateMicrophoneDroneInfluence(AgtMicrophoneDronePLSInRoom agtMicrophoneDrone){
		agtMicrophoneDrone.setInfluence(
				agtMicrophoneDrone.getInfluence().x, 
				agtMicrophoneDrone.getInfluence().y, 
				agtMicrophoneDrone.getInfluence().z
				);
	}
	
	/**
	 * 
	 * @param agtmeasurementDrone
	 */
	public static void UpdateMeasurementDroneInfluence(AgtMeasurementDronePLSInRoom agtmeasurementDrone){
		agtmeasurementDrone.setInfluence(
				agtmeasurementDrone.getInfluence().x, 
				agtmeasurementDrone.getInfluence().y, 
				agtmeasurementDrone.getInfluence().z
				);
	}
}
