package swarm.model.level.room;

import java.util.ArrayList;
import java.util.Set;

import javax.vecmath.Vector3d;

import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

/**
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 * the graph use to ensure a full space coverage the space is divided in cubes depending on the 
 * precisions of the different measures.
 */

public class Graph {
	
	/**
	 * The parameters of the simulation
	 */
	private SwarmParameters parameters;
	
	/**
	 * the length of our mesh, basically the length of our cubes' size
	 */
	private int length;
	
	/**
	 * the number of cube in each direction
	 */
	private int imax; //along x
	private int jmax; //along y
	private int kmax; //along z
	public int getImax(){return imax;}
	public int getJmax(){return jmax;}
	public int getKmax(){return kmax;}
	
	/**
	 * The builder of this method, is calculate the size of our mesh and initialize the cubes.
	 * @param param The parameters of the simulation
	 */
	public Graph(SwarmParameters param){
		this.parameters = param;
		
		/*
		 * The length is calculated so that any drones concerned by the mission will be able to see the
		 * entire cube as long as it is inside it.
		 */
		if (parameters.objectiveType == 1 ){
			length = (int)Math.floor(
					Math.min(
						Math.min(
							Math.min(parameters.cameraDroneDetectionRange, parameters.communicatorDroneDetectionRange),
							Math.min(parameters.droneDetectionRange, parameters.measurementDroneDetectionRange)),
						parameters.microphoneDroneDetectionRange));

		}else if (parameters.objectiveType == 2){
			length = (int) Math.floor(parameters.measurementDroneDetectionRange);
			
		}else if (parameters.objectiveType == 3){
			throw new IllegalArgumentException( "Wrong objective type" );
		}else throw new IllegalArgumentException( "Objective type unknown" );
		
		/*
		 * when the size of our mesh is calculated we can divide it in cubes (the last ones may be partially outside)
		 */
		imax = (int) Math.ceil(parameters.roomBounds.x/length);
		jmax = (int) Math.ceil(parameters.roomBounds.y/length);
		kmax = (int) Math.ceil(parameters.roomBounds.z/length);
		
		this.spaceGraph = new Cube[imax][jmax][kmax];
		
		/*
		 * each cube position is initialized.
		 */
		for (int i = 0 ; i < imax ; i++){
			for (int j = 0 ; j <  jmax ; j++){
				for (int k = 0 ; k < kmax ; k++){
					spaceGraph[i][j][k] = new Cube(i*length,j*length,k*length);
				}
			}
		}
		frontier = new ArrayList<Cube>();
	}
	
	
	/**
	 * The table containing informations about the different sub-volume of our environment. Used for full coverage search
	 */
	private Cube[][][] spaceGraph;
	
	public Cube[][][] getSpaceGraph(){
		return spaceGraph;
	}
	/**
	 * The set of frontier cube: non explored cube adjacent to an explored cube
	 */
	private ArrayList<Cube> frontier;
	
	/**
	 * The function that update frontier list based on new exploration.
	 * @param x the position of the drone
	 * @param y the position of the drone
	 * @param z the position of the drone
	 * @param detectionRange the maximum distance of detection for this drone
	 */
	public void updateFrontier(Vector3d pos, double detectionRange){
		int i,j,k,l;
		
		/*
		 * we search in which cube is the drone;
		 */
		i = Math.floorDiv((int)pos.x, length);
		j = Math.floorDiv((int)pos.y, length);
		k = Math.floorDiv((int)pos.z, length);

		if(i > imax-1) i = imax-1;
		if(j > jmax-1) j = jmax-1;
		if(k > kmax-1) k = kmax-1;
		if(i < 0) i = 0;
		if(j < 0) j = 0;
		if(k < 0) k = 0;
		
		/*
		 * the cube where the drone stands is counted as visited.
		 */
		removeFrontier(i,j,k);
		
		/*
		 * we check if the drone have extra range to check other cubes in addition to the one where he is flying.
		 */
		l = (int) Math.floor(detectionRange/length); //positive due to the definition of length
		
		/*
		 * every other cubes in range are counted as visited.
		 */
		for (int in = -l ; in <= l ; in++ ){
			for (int jn = -l ; jn <= l ; jn++ ){
				for(int kn = -l ; kn <= l ; kn++ ){
					
					if (Math.abs(in) + Math.abs(jn) + Math.abs(kn) < l && 
							i+in < imax-1 && 
							j+jn < jmax -1 && 
							k+kn < kmax -1 &&
							i+in >= 0 &&
							j+jn >= 0 &&
							k+kn >= 0){
						removeFrontier(i+in,j+jn,k+kn);
					}				
					
				}
			}
		}
		
	}
	
	/**
	 * In the function the list of frontier is modified, visited frontier are removed 
	 * and any adjacent non visited cubes are now listed as frontiers
	 * @param k the coordinates of the cube to be removed.
	 * @param i 
	 * @param j 
	 */
	private void removeFrontier(int i, int j, int k){
		
		/*
		 * First the visited cube is removed from the list
		 */
		spaceGraph[i][j][k].setVisited();
		if (frontier.contains(spaceGraph[i][j][k])){
			frontier.remove(spaceGraph[i][j][k]);
		}
		
		/*
		 * we check for adjacent non visited cube
		 */
		if (i < imax-1 ){
			if(!spaceGraph[i+1][j][k].cubeIsVisited() && !frontier.contains(spaceGraph[i+1][j][k])){
				frontier.add(spaceGraph[i+1][j][k]);
			}
		}
		
		if (j < jmax-1 ){
			if(!spaceGraph[i][j+1][k].cubeIsVisited() && !frontier.contains(spaceGraph[i][j+1][k])){
				frontier.add(spaceGraph[i][j+1][k]);
			}
		}
		
		if (k < kmax-1 ){
			if(!spaceGraph[i][j][k+1].cubeIsVisited() && !frontier.contains(spaceGraph[i][j][k+1])){
				frontier.add(spaceGraph[i][j][k+1]);
			}
		}
		
		if (i > 0 ){
			if(!spaceGraph[i-1][j][k].cubeIsVisited() && !frontier.contains(spaceGraph[i-1][j][k])){
				frontier.add(spaceGraph[i-1][j][k]);
			}
		}
		
		if (j > 0 ){
			if(!spaceGraph[i][j-1][k].cubeIsVisited() && !frontier.contains(spaceGraph[i][j-1][k])){
				frontier.add(spaceGraph[i][j-1][k]);
			}
		}
		
		if (k > 0 ){
			if(!spaceGraph[i][j][k-1].cubeIsVisited() && !frontier.contains(spaceGraph[i][j][k-1])){
				frontier.add(spaceGraph[i][j][k-1]);
			}
		}
		
		if (frontier.isEmpty()){
			System.out.println("all space covered");
			parameters.repulsionDistance = 40;
			parameters.attractionCoeff = 1;
			parameters.objectiveType = 3;
		}
		
	}
	
	/**
	 * The table containing every information about the cost for each drone to go to each frontier;
	 */
	private CostLowMatrix[] costMatrix;
	
	/**
	 * Calculate a new costMatrix
	 * @param droneUpdateList the list of drones concerned by the full search coverage.
	 */
	public void updateCostMatrix(Set<AgtDronePLSInRoom> droneUpdateList){
		
		costMatrix = new CostLowMatrix[droneUpdateList.size()]; //one cost low matrix per drone.
		int l = 0;
		for (AgtDronePLSInRoom drone : droneUpdateList){
			costMatrix[l] = new CostLowMatrix( frontier.size(), drone.hashCode()); // one value per frontier per drone.
			
			
			/*
			 * The cost are calculated as a distance between the drone and the frontier cube.
			 */
			int m = 0;
			for(Cube cube : frontier){
				costMatrix[l].costLowMatrix[m] = Math.sqrt(
													Math.pow((cube.getPosition().x - drone.getLocation().x),2) +
													Math.pow((cube.getPosition().y - drone.getLocation().y),2) +
													Math.pow((cube.getPosition().z - drone.getLocation().z),2));
				m++;
			}
			l++;
		}
		
	}
	
	/**
	 * Change the destination of the drone to the frontier defined by the algorithm.
	 * @param drone the drone looking for a new destination.
	 */
	public void assignedDrone(AgtDronePLSInRoom drone){
		
		/*
		 * we first need to identified the cost matrix for our drone (cost relative to each frontier).
		 */
		CostLowMatrix cost = null;
		for(int m = 0 ; m < costMatrix.length ; m++){
			if (costMatrix[m].hashCode == drone.hashCode()){
				cost = costMatrix[m];
			}
		}
		
		/*
		 * For each frontier we calculate the number of drones closer to the frontier than our drone.
		 */
		int[] closerDrones = new int[frontier.size()];
		for (int n = 0; n < frontier.size(); n++){
			
			for(int m = 0 ; m < costMatrix.length ; m++){
				if (costMatrix[m].hashCode != drone.hashCode()){
					if (costMatrix[m].costLowMatrix[n] < cost.costLowMatrix[n]){
						closerDrones[n]++;
					}
				}
			}
		}
		
		/*
		 * then we search the frontier with the fewer number of drones closer to it than our drone.
		 */
		int minPosition = 0;
		int minValue = 1000;
		
		for (int n = 0 ; n< frontier.size() ; n++){
			if (closerDrones[n] <= minValue){
				if (closerDrones[n] == minValue){
					/*
					 * If two frontiers have the same number of drones closer to them than our drone we choose the one with a minimal cost.
					 */
					if ( cost.costLowMatrix[n] < cost.costLowMatrix[minPosition]){
						minValue = closerDrones[n];
						minPosition = n; 
					}else{
						// we keep our values
					}
				}else{ //If closerDrones[n] < minValue
					minValue = closerDrones[n];
					minPosition = n; 
				}
			}
		}
		
		/*
		 * the chosen frontier is now send as a new destination for the drone.
		 */
		int n =0;
		for (Cube cube : frontier){
			if (n == minPosition){
				drone.setDestination(cube);
				break;
			}else{
				n++;
			}
		}
	}
	
	/**
	 * The cost matrix for each drone.
	 *
	 */
	private class CostLowMatrix{
		
		/**
		 * the hashCode of the drone which own this matrix
		 */
		int hashCode;
		
		/**
		 * the cost needed to go to each frontier relative to this drone.
		 */
		double[] costLowMatrix;
		
		public CostLowMatrix(int size, int hash){
			this.hashCode = hash;
			this.costLowMatrix = new double[size];
		}		
		
	}
	public int getLength(){
		return length;
	}
}


