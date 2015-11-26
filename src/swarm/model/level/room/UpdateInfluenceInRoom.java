package swarm.model.level.room;

import java.util.Set;

import javax.vecmath.Vector3d;

import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

public class UpdateInfluenceInRoom {
	
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
				double distance =Math.sqrt(Math.pow(agtDrone.getLocation().x - agtOtherDrone.getLocation().x, 2)
										 + Math.pow(agtDrone.getLocation().y - agtOtherDrone.getLocation().y, 2)
										 + Math.pow(agtDrone.getLocation().z - agtOtherDrone.getLocation().z, 2));
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
}
