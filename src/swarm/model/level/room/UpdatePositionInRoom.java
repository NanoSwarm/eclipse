package swarm.model.level.room;

import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

public class UpdatePositionInRoom {
	public static void UpdateDronePosition(AgtDronePLSInRoom agtDrone, SwarmParameters parameters){
		
		agtDrone.setAcceleration(
				agtDrone.getAcceleration().x + agtDrone.getInfluence().x,
				agtDrone.getAcceleration().y + agtDrone.getInfluence().y,
				agtDrone.getAcceleration().z + agtDrone.getInfluence().z
				);
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