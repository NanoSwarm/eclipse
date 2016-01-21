package swarm.model;

import java.awt.Color;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import fr.lgi2a.similar2logo.kernel.model.LogoSimulationParameters;

public class SwarmParameters extends LogoSimulationParameters{
	
	/**
	 * the repulsion distance.
	 */
	public double repulsionDistance;
	
	/**
	 * the attraction distance.
	 */
	public double attractionDistance;
	
	/**
	 * the orientation distance.
	 */
	public double orientationDistance;
	
	/**
	 * the repulsion distance.
	 */
	public double repulsionCoeff;
	
	/**
	 * the attraction distance.
	 */
	public double attractionCoeff;
	
	/**
	 * the orientation distance.
	 */
	public double orientationCoeff;
	
	
	
	
	
	/**
	 * The maximal initial speed of drones.
	 */
	public double maxInitialSpeed;
	
	/**
	 * The minimal initial speed of drones.
	 */
	public double minInitialSpeed;
	
	/**
	 * The maximum speed of the drones
	 */	
	public double maxSpeed;
	
	/**
	 * The maximum acceleration of the drones (without special equipment).
	 */	
	public double maxAcc;
	
	/**
	 * The size of the drone
	 */
	public double initialSize;
	
	/**
	 * The mass factor of a "Drone" agent, it's 1 because he is the reference;
	 */
	public double droneMassFactor = 1;
	
	/**
	 * The mass factor of a "Camera Drone" agent (the coefficient between his mass and a "Drone" agent mass, <1);
	 */
	public double cameraDroneMassFactor;
	
	/**
	 * The mass factor of a "Communicator Drone" agent (the coefficient between his mass and a "Drone" agent mass, <1);
	 */
	public double communicatorDroneMassFactor;
	
	/**
	 * The mass factor of a "Microphone Drone" agent (the coefficient between his mass and a "Drone" agent mass, <1);
	 */
	public double microphoneDroneMassFactor;
	
	/**
	 * The initial energy in the battery of every drones
	 */
	public double initialEnergy;
	
	
	
	
	/**
	 * The number of "Camera Drone" agents in the simulation.
	 */
	public int nbOfCameraDroneAgents;
	
	/**
	 * The maximum detection range (when searching a point)
	 */
	public double cameraDroneDetectionRange;
	
	/**
	 * The number of "Communicator Drone" agents in the simulation.
	 */
	public int nbOfCommunicatorDroneAgents;
	
	/**
	 * The maximum detection range (when searching a point)
	 */
	public double communicatorDroneDetectionRange;
	
	/**
	 * The number of "Drone" agents in the simulation.
	 */
	public int nbOfDroneAgents;
	
	/**
	 * The maximum detection range (when searching a point)
	 */
	public double droneDetectionRange;
	
	/**
	 * The number of "Microphone Drone" agents in the simulation.
	 */
	public int nbOfMicrophoneDroneAgents;
	
	/**
	 * The maximum detection range (when searching a point)
	 */
	public double microphoneDroneDetectionRange;
	
	/**
	 * The number of "Measurement Drone" agents in the simulation.
	 */
	public int nbOfMeasurementDroneAgents;
	
	/**
	 * The maximum detection range (when searching a point)
	 */
	public double measurementDroneDetectionRange;
	/**
	 * The Mass of a drone with the tools to fly
	 */
	public double basicDroneMass;
	
	
	
	/**
	 * The bounds of the swarm room.
	 */
	public Vector3d roomBounds;
	
	/**
	 * The minimum distance to maintain between the drones and the limits of the room
	 */
	public int securityDistance;
	
	/**
	 * The length of the simulation
	 */
	public int simulationTime;

	/**
	 * The objective of the mission
	 * objectiveType = 1 -> the drones have to find a point in space (a drone find the point when he is close enough to detect it).
	 * objectiveType = 2 -> the drones have to measure certain characteristic everywhere in space (in order to create a 2D or 3D map).
	 * objectiveType = 3 -> just a demonstration of swarm movement using boids algorithm. 
	 */
	public int objectiveType;
	
	/**
	 * In case of a type 1 objective, the position of the point to find.
	 */
	public Vector3d objectivePosition;

	/**
	 * The method chosen to find the objective.
	 * resolutionType = "position minimum" -> the drones use a graph searching algorithm to do a full space search.
	 * resolutionType = "pso" -> the drones use pso algorithm to find a local maximum.
	 */
	public String resolutionType;
	
	/**
	 * The color of the different drones
	 */
	public Color3f cameraDroneColor;

	public Color3f droneColor;
	
	public Color3f communicatorDroneColor;
		
	public Color3f microphoneDroneColor;
	
	public Color3f measurementDroneColor;
	
	
	/**
	 * Builds a parameters set containing default values.
	 */
	public SwarmParameters () {
		super();
		
		this.cameraDroneColor = new Color3f(Color.blue);
		this.droneColor = new Color3f(Color.magenta);
		this.communicatorDroneColor = new Color3f(Color.red);
		this.microphoneDroneColor = new Color3f(Color.orange);
		this.measurementDroneColor = new Color3f(Color.green);
		
		this.repulsionDistance = 40;
		this.orientationDistance = 400;
		this.attractionDistance = 700;
		this.repulsionCoeff = 10;
		this.orientationCoeff = 50000;
		this.attractionCoeff = 1;
		
		
		this.maxInitialSpeed=0.00001;
		this.minInitialSpeed=0.0;
		this.maxSpeed = 8;
		this.maxAcc = 2;
		this.initialSize = 5;
		this.xTorus = true;
		this.yTorus = true;
		this.cameraDroneMassFactor = 0.9;
		this.communicatorDroneMassFactor = 0.9;
		this.microphoneDroneMassFactor = 0.9;

		this.initialEnergy = 5000;

		this.basicDroneMass= 0.04;
		
		this.nbOfCameraDroneAgents = 0;
		this.nbOfCommunicatorDroneAgents = 0;
		this.nbOfDroneAgents = 0;
		this.nbOfMicrophoneDroneAgents = 0;
		this.nbOfMeasurementDroneAgents = 600;
		this.cameraDroneDetectionRange = 100;
		this.communicatorDroneDetectionRange = 100;
		this.droneDetectionRange = 100;
		this.microphoneDroneDetectionRange = 100;
		this.measurementDroneDetectionRange = 100;


		this.cameraDroneDetectionRange = 100;
		
		this.roomBounds = new Vector3d(3000, 5000,1000);
		this.securityDistance = 100;
		this.simulationTime = 2000;
		this.objectiveType = 3;
		this.resolutionType = "pso"; // "pso" "position minimum"
		this.objectivePosition = new  Vector3d(1000,1000,500);

		
		
	}

}
