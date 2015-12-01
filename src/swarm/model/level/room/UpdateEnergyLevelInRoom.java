package swarm.model.level.room;

import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

public class UpdateEnergyLevelInRoom {
	
	/**
	 *  the method updating the energy level of the drones.
	 * @param agtDrone a drone send by RoomLevel if he is eligible to this influence.
	 */
	public static void updateEnergy(AgtDronePLSInRoom agtDrone){

		/**
		 * The loss in energy is calculated directly from the influences 
		 *(ie the forced appl:ied by the drones on the environment).
		 */
		double energyDiff = Math.sqrt(	
									Math.pow(agtDrone.getInfluence().x, 2) +
								    Math.pow(agtDrone.getInfluence().y, 2) +
								    Math.pow(agtDrone.getInfluence().z, 2)
								    );
		
		//the energy level can't be negative.
		if (energyDiff < agtDrone.getEnergy()){
			agtDrone.setEnergy(agtDrone.getEnergy() - energyDiff);
		}else{
			agtDrone.setEnergy(0);
			agtDrone
		}

			
			
	}
}
