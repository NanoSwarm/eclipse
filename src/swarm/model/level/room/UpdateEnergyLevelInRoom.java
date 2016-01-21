package swarm.model.level.room;

import javax.media.j3d.Material;
import javax.vecmath.Color3f;

import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

public class UpdateEnergyLevelInRoom {
	
	/**
	 *  the method updating the energy level of the drones.
	 * @param agtDrone a drone send by RoomLevel if he is eligible to this influence.
	 */
	public static int updateEnergy(AgtDronePLSInRoom agtDrone,SwarmParameters parameters){

		/**
		 * The loss in energy is calculated directly from the influences 
		 *(ie the forced applied by the drones on the environment).
		 */
		
		double energyDiff = parameters.basicDroneMass*(Math.abs(agtDrone.getAcceleration().x)+
													   Math.abs(agtDrone.getAcceleration().y)+
													   Math.abs(agtDrone.getAcceleration().z)+
										       3.54465*Math.pow(10,-7)*(Math.pow(agtDrone.getAcceleration().x, 2)+
												       Math.pow(agtDrone.getAcceleration().y, 2)+
													   Math.pow(agtDrone.getAcceleration().z, 2)))*
													   Math.sqrt(	
															   Math.pow(agtDrone.getAcceleration().x, 2) +
															   Math.pow(agtDrone.getAcceleration().y, 2) +
															   Math.pow(agtDrone.getAcceleration().z, 2)
															   );
								    
		
		//the energy level can't be negative.
		if (energyDiff < agtDrone.getEnergy()){
			agtDrone.setEnergy(agtDrone.getEnergy() - energyDiff);
			return 0;
		}else{
			agtDrone.setEnergy(0);
			Color3f black=new Color3f(0.0f,0.0f,0.0f);
			Material material = new Material(
					black
					, black
					, black
					, black
					, 64);
			material.setLightingEnable(true);
			agtDrone.forme.getAppearance().setMaterial(material);
			return 1;

		}
		
	}
	
}
