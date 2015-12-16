package swarm.model.level.room;

import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

public class UpdatePositionInRoom {
	
	/**
	 * the method updating the position of drones in the RoomLevel.
	 * @param agtDrone drone send by RoomLevel for position update
	 * @param parameters the parameters of the simulation
	 */
	public static void UpdateDronePosition(AgtDronePLSInRoom agtDrone, SwarmParameters parameters){
		if(agtDrone.getEnergy()!=0 && agtDrone.getLocation().z >= 0){
		agtDrone.setAcceleration(
				agtDrone.getAcceleration().x + agtDrone.getInfluence().x,
				agtDrone.getAcceleration().y + agtDrone.getInfluence().y,
				agtDrone.getAcceleration().z + agtDrone.getInfluence().z
				);
		}else if (agtDrone.getEnergy()==0 && agtDrone.getLocation().z >= 0){
				agtDrone.setAcceleration(0, 0, -9.81);
		}else{
			agtDrone.setAcceleration(0, 0, 0);
			agtDrone.setVelocity(0, 0, 0);
			agtDrone.setLocation(agtDrone.getLocation().x, agtDrone.getLocation().y, -0.01);
			agtDrone.setEnergy(0);
		}
		//Keep the velocity vector under the maxSeed limit.
		double speed = Math.sqrt(
				Math.pow(agtDrone.getVelocity().x,2)+
				Math.pow(agtDrone.getVelocity().y,2)+	
				Math.pow(agtDrone.getVelocity().z,2));
		
		if ( speed > parameters.maxSpeed){
			agtDrone.setVelocity(parameters.maxSpeed * agtDrone.getVelocity().x / speed ,
					parameters.maxSpeed * agtDrone.getVelocity().y / speed,
					parameters.maxSpeed * agtDrone.getVelocity().z / speed );
			agtDrone.setAcceleration(0, 0,0);
		}
		
		//Keep the acceleration vector under the maxAcc limit
		double acc = Math.sqrt(
				Math.pow(agtDrone.getAcceleration().x,2)+	
				Math.pow(agtDrone.getAcceleration().y,2)+	
				Math.pow(agtDrone.getAcceleration().z,2));
		
		if ( acc > parameters.maxAcc){
			agtDrone.setAcceleration(
					parameters.maxAcc * agtDrone.getAcceleration().x / acc ,
					parameters.maxAcc * agtDrone.getAcceleration().y / acc,
					parameters.maxAcc * agtDrone.getAcceleration().z / acc
					);
		}
		
		//Update position and speed by integration (of speed and acceleration)
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
		
	}
}
