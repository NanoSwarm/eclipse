package swarm.model.level.room;

import javax.vecmath.Vector3d;

public class Cube {
	
	private Vector3d position;
	
	public Cube(double x, double y, double z){
		isVisited = false;
		position = new Vector3d(x,y,z);
	}
	
	/**
	 * 
	 * @return position, the position of the corner closest to the origin
	 */
	public Vector3d getPosition(){
		return position;
	}
	
	/**
	 * True if the cube has already been "visited", meaning that a drone has fully checked this volume.
	 */
	private boolean isVisited;
	
	/**
	 * Set to true the value of isVisited, since it's not reversible we don't need to do the reverse operation.
	 */
	public void setVisited(){
		isVisited = true;
	}
	
	/**
	 * 
	 * @return isVisited, 1 if the cube was visited, 0 otherwise.
	 */
	public boolean cubeIsVisited(){
		return isVisited;
	}
	
	/**
	 * the last measured value in this cube.
	 */
	private double measuredValue = 0;
	
	/**
	 * 
	 * @param measure the new value measured in this cube.
	 */
	public void setMeasuredValue(double measure){
		measuredValue = measure;
	}
	
	/**
	 * 
	 * @return measuredValue the last measured value in this cube.
	 */
	public double getMeasuredValue(){
		
			return this.measuredValue;
		
	}
}
