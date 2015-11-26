package swarm.model.level.room;

import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

public class UpdateEnergyLevelInRoom {
	public static void updateEnergy(AgtDronePLSInRoom agtDrone){

			agtDrone.setEnergy(
					agtDrone.getEnergy()
					);
	}
}
