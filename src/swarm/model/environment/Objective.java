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
	private static String ObjectiveType;
	
	public static void setObjective(Vector3d obj, String objType){
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
		if (ObjectiveType.equals("point")){
			res = Math.sqrt( Math.pow(pos.x-objectivePosition.x , 2) 
							+Math.pow(pos.y-objectivePosition.y , 2) 
							+Math.pow(pos.z-objectivePosition.z , 2)
						   );
		}else if (ObjectiveType.equals("measure")){

			res = 100 - Math.sqrt(pos.x*pos.x + pos.y*pos.y + pos.z*pos.z);
		}else{ 
			throw new IllegalArgumentException( "Wrong Objective argument" );
		}
		return res;
	}
	
}
