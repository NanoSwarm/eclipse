package swarm.model.environment;

import javax.vecmath.Vector3d;

public class Objective {
	
	
	/**
	 * 
	 */
	private static Vector3d objectivePosition;
	
	/**
	 * 
	 */
	private static int ObjectiveType;
	
	public static void setObjective(Vector3d obj, int objType){
		objectivePosition = obj;
		ObjectiveType = objType;
	}
	
	/**
	 * 
	 * @param x the position of the drone
	 * @param y the position of the drone
	 * @param z the position of the drone
	 * @return the value of the measured characteristic
	 */
	public static Double getObjective(Vector3d pos){
		
		Double res;
		switch(ObjectiveType){
		
		case 1: 
			res = Math.sqrt( Math.pow(pos.x-objectivePosition.x , 2) 
							+Math.pow(pos.y-objectivePosition.y , 2) 
							+Math.pow(pos.z-objectivePosition.z , 2)
						   );
			break;
			
		case 2:
			res = 100 - Math.sqrt(pos.x*pos.x + pos.y*pos.y + pos.z*pos.z);
			break;
			
			default : 
			throw new IllegalArgumentException( "Wrong Objective number" );
		}
		return res;
	}
	
}
