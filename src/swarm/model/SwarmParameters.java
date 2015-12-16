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
	 * 
	 * test
	 */
	
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
	 * The number of "Communicator Drone" agents in the simulation.
	 */
	public int nbOfCommunicatorDroneAgents;
	
	/**
	 * The number of "Drone" agents in the simulation.
	 */
	public int nbOfDroneAgents;
	
	/**
	 * The number of "Microphone Drone" agents in the simulation.
	 */
	public int nbOfMicrophoneDroneAgents;
	
	/**
	 * The number of "Measurement Drone" agents in the simulation.
	 */
	public int nbOfMeasurementDroneAgents;
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
	 * objectiveType = 1 -> the drones have to find a point in space (a drone find the point when he is close enough to detect it)
	 * objectiveType = 2 -> the drones have to measure certain characteristic everywhere in space (in order to create a 2D or 3D map) 
	 */
	public int objectiveType;
	
	/**
	 * In case of a type 1 objective, the position of the point to find
	 */
	public Vector3d objectivePosition;

	
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
		this.measurementDroneColor = new Color3f(Color.cyan);
		
		this.repulsionDistance = 40;
		this.orientationDistance = 400;
		this.attractionDistance = 700;
		this.repulsionCoeff = 10;
		this.orientationCoeff = 50000;
		this.attractionCoeff = 1;
		
		
		this.maxInitialSpeed = 0.02;
		this.minInitialSpeed = 0.01;
		this.maxSpeed = 8;
		this.maxAcc = 2;
		this.initialSize = 5;
		this.xTorus = true;
		this.yTorus = true;
		this.cameraDroneMassFactor = 0.9;
		this.communicatorDroneMassFactor = 0.9;
		this.microphoneDroneMassFactor = 0.9;
		this.initialEnergy = 10;
		this.basicDroneMass= 0.04;
		
		this.nbOfCameraDroneAgents = 30;
		this.nbOfCommunicatorDroneAgents = 30;
		this.nbOfDroneAgents = 300;
		this.nbOfMicrophoneDroneAgents = 300;
		this.nbOfMeasurementDroneAgents = 10;
		
		this.roomBounds = new Vector3d(5000, 3000, 1000);
		this.securityDistance = 100;
		this.simulationTime = 500;
		this.objectiveType = 1;
		this.objectivePosition = new  Vector3d(999,999,999);
	}

}
