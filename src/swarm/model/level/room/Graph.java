package swarm.model.level.room;

import java.util.ArrayList;
import java.util.Set;

import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

/**
 * the graph use to ensure a full space coverage
 * @author Corentin Muselet
 *
 */

public class Graph {
	
	private SwarmParameters parameters;
	
	private int length;
	private int kmax;
	private int imax;
	private int jmax;
	
	public Graph(SwarmParameters param){
		this.parameters = param;
		
		if (parameters.objectiveType == 1){
			length = (int)Math.floor(
					Math.min(
						Math.min(
							Math.min(parameters.cameraDroneDetectionRange, parameters.communicatorDroneDetectionRange),
							Math.min(parameters.droneDetectionRange, parameters.measurementDroneDetectionRange)),
						parameters.microphoneDroneDetectionRange));

		}else if (parameters.objectiveType == 2){
			length = (int) Math.floor(parameters.measurementDroneDetectionRange / Math.sqrt(3));
			
		}else throw new IllegalArgumentException( "Objective type unknown" );
		
		kmax = (int) Math.ceil(parameters.roomBounds.x/length);
		imax = (int) Math.ceil(parameters.roomBounds.y/length);
		jmax = (int) Math.ceil(parameters.roomBounds.z/length);
		
		this.spaceGraph = new Cube[imax][jmax][kmax];
		
		for (int i = 0 ; i < imax ; i++){
			for (int j = 0 ; j < jmax ; j++){
				for (int k = 0 ; k < kmax ; k++){
					spaceGraph[i][j][k] = new Cube(i*length,j*length,k*length);
				}
			}
		}
		
		this.frontier = null;
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
	
	public void updateFrontier(double x, double y, double z, double detectionRange){
		int k,i,j,l;
		
		/**
		 * we search in which cube is the drone;
		 */
		k = Math.floorDiv((int)x, length);
		i = Math.floorDiv((int)y, length);
		j = Math.floorDiv((int)z, length);
		
		removeFrontier(k,i,j);
		
		l = (int) Math.floor(detectionRange/length*Math.sqrt(3) - length*Math.sqrt(3)); //positive due to the definition of length
		
		for (int in = -l ; in <= l ;in++){
			for (int jn = -l ; jn <= l ; jn++ ){
				for(int kn = -l; kn <= l ; kn++){
					
					if (Math.abs(in) + Math.abs(jn) + Math.abs(kn) < l ){
						removeFrontier(i+in,j+jn,k+kn);
					}				
					
				}
			}
		}
		
	}
	
	private void removeFrontier(int k, int i, int j){
		
		frontier.remove(spaceGraph[k][i][j]);
		spaceGraph[k][i][j].setVisited();
		
		if (k < kmax-1 && !spaceGraph[k+1][i][j].cubeIsVisited()){
			frontier.add(spaceGraph[k+1][i][j]);
		}
		if (i < imax-1 && !spaceGraph[k][i+1][j].cubeIsVisited()){
			frontier.add(spaceGraph[k][i+1][j]);
		}
		if (j < jmax-1 && !spaceGraph[k][i][j+1].cubeIsVisited()){
			frontier.add(spaceGraph[k][i][j+1]);
		}
		if (k > 0 && !spaceGraph[k-1][i][j].cubeIsVisited()){
			frontier.add(spaceGraph[k-1][i][j]);
		}
		if (i > 0 && !spaceGraph[k][i-1][j].cubeIsVisited()){
			frontier.add(spaceGraph[k][i-1][j]);
		}
		if (j > 0 && !spaceGraph[k][i][j-1].cubeIsVisited()){
			frontier.add(spaceGraph[k][i][j-1]);
		}
		
	}
	
	private CostLowMatrix[] costMatrix;
	
	public void updateCostMatrix(Set<AgtDronePLSInRoom> droneUpdateList){
		
		costMatrix = new CostLowMatrix[droneUpdateList.size()]; //one cost low matrix per drone.
		int l = 0;
		for (AgtDronePLSInRoom drone : droneUpdateList){
			costMatrix[l] = new CostLowMatrix( frontier.size(), drone.hashCode()); // one value per frontier per drone.
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
	
	public void assignedDrone(AgtDronePLSInRoom drone){
		int[] closerDrones = new int[frontier.size()];
		int n = 0;
		CostLowMatrix cost;
		for(int m = 0 ; m < costMatrix.length ; m++){
			if (costMatrix[m].hashCode == drone.hashCode()){
				cost = costMatrix[m];
			}
		}
		
		for (Cube cube : frontier){
			closerDrones[n] = 0;
			
			for(int m = 0 ; m < costMatrix.length ; m++){
				if (costMatrix[m].hashCode != drone.hashCode()){
					
				}
			}
			n++;
		}
	}
	
	
	private class CostLowMatrix{
		
		int hashCode;
		double[] costLowMatrix;
		
		public CostLowMatrix(int size, int hash){
			this.hashCode = hash;
			this.costLowMatrix = new double[size];
		}		
		
	}
}


