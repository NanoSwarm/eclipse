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
			//if (((pos.x>objectivePosition.x-500)&&(pos.x<objectivePosition.x+500))&&((pos.y>objectivePosition.y-300)&&(pos.y<objectivePosition.y+500)))
				 res=1/Math.sqrt( Math.pow(pos.x-objectivePosition.x , 2) 
							+Math.pow(pos.y-objectivePosition.y , 2)
						   );
		//	else res=0.0;
			break;
			
			default : 
			throw new IllegalArgumentException( "Wrong Objective number" );
		}
		return res;
	}
	
}
