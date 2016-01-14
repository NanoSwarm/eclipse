package swarm.model.level.room;

import javax.vecmath.Vector3d;

public class Cube {
	
	private Vector3d position;
	
	public Cube(int x, int y, int z){
		isVisited = false;
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	
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
	
	public boolean cubeIsVisited(){
		return isVisited;
	}
	
	private double measuredValue = 0;
	
	public void setMeasuredValue(double measure){
		
	}
	
	public double getMeasuredValue(){
		
			return this.measuredValue;
		
	}
}
