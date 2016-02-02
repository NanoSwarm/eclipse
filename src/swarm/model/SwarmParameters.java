package swarm.model;

import java.awt.Color;
import java.util.Properties;

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
	
	public int roomBoundsX;
	public int roomBoundsY;
	public int roomBoundsZ;
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
	public int objectivePositionX;
	public int objectivePositionY;
	public int objectivePositionZ;
	/**
	 * The method chosen to find the objective.
	 * resolutionType = "position minimum" -> the drones use a graph searching algorithm to do a full space search.
	 * resolutionType = "pso" -> the drones use pso algorithm to find a local maximum.
	 */
	public String resolutionType;

	public double alphaPSO;
	public double betaPSO;
	public double gammaPSO;
	/**
	 * The color of the different drones
	 */
	public Color3f cameraDroneColor;

	public Color3f droneColor;
	
	public Color3f communicatorDroneColor;
		
	public Color3f microphoneDroneColor;
	
	public Color3f measurementDroneColor;
	
	public Properties properties;
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

		this.roomBounds = new Vector3d(roomBoundsX, roomBoundsY,roomBoundsZ);

		this.objectivePosition = new  Vector3d(objectivePositionX,objectivePositionY,objectivePositionZ);

		
	}
	public void refreshVect()
	{
		this.objectivePosition = new  Vector3d(objectivePositionX,objectivePositionY,objectivePositionZ);
		this.roomBounds = new Vector3d(roomBoundsX, roomBoundsY,roomBoundsZ);
	}
	public void  setProperties(Properties prop)
	{
		this.properties=prop;
		this.nbOfMeasurementDroneAgents=Integer.parseInt(prop.getProperty("nbOfMeasurementDroneAgents"));
		this.nbOfCameraDroneAgents=Integer.parseInt(prop.getProperty("nbOfCameraDroneAgents"));
		this.nbOfCommunicatorDroneAgents=Integer.parseInt(prop.getProperty("nbOfCommunicatorDroneAgents"));
		this.nbOfDroneAgents=Integer.parseInt(prop.getProperty("nbOfDroneAgents"));
		this.nbOfMicrophoneDroneAgents=Integer.parseInt(prop.getProperty("nbOfMicrophoneDroneAgents"));
		  
		this.cameraDroneDetectionRange=Integer.parseInt(prop.getProperty("cameraDroneDetectionRange"));
		this.communicatorDroneDetectionRange=Integer.parseInt(prop.getProperty("communicatorDroneDetectionRange"));
		this.droneDetectionRange=Integer.parseInt(prop.getProperty("droneDetectionRange"));
		this.microphoneDroneDetectionRange=Integer.parseInt(prop.getProperty("microphoneDroneDetectionRange"));
		this.measurementDroneDetectionRange=Integer.parseInt(prop.getProperty("measurementDroneDetectionRange"));
		this.cameraDroneDetectionRange=Integer.parseInt(prop.getProperty("cameraDroneDetectionRange"));
		  
		  
		 this.repulsionDistance=Integer.parseInt(prop.getProperty("repulsionDistance"));
		 this.orientationDistance=Integer.parseInt(prop.getProperty("orientationDistance"));
		  this.attractionDistance=Integer.parseInt(prop.getProperty("attractionDistance"));
		  this.repulsionCoeff=Integer.parseInt(prop.getProperty("repulsionCoeff"));
		  this.orientationCoeff=Integer.parseInt(prop.getProperty("orientationCoeff"));
		  this.attractionCoeff=Integer.parseInt(prop.getProperty("attractionCoeff"));
		  
		  this.maxSpeed=Integer.parseInt(prop.getProperty("maxSpeed"));
		  this.maxAcc=Integer.parseInt(prop.getProperty("maxAcc"));
		  this.initialSize=Integer.parseInt(prop.getProperty("initialSize"));
		  this.initialEnergy=Integer.parseInt(prop.getProperty("initialEnergy"));
		  
		  this.securityDistance=Integer.parseInt(prop.getProperty("securityDistance"));
		  this.simulationTime=Integer.parseInt(prop.getProperty("simulationTime"));
		  this.objectiveType=Integer.parseInt(prop.getProperty("objectiveType"));
		  
		  if (Integer.parseInt(prop.getProperty("resolutionType")) == 1){
			  this.resolutionType = "position minimum";
		  }else if (Integer.parseInt(prop.getProperty("resolutionType")) == 2){
			  this.resolutionType = "pso";
		  }
		  
		  this.maxInitialSpeed=Double.parseDouble(prop.getProperty("maxInitialSpeed"));
		  this.minInitialSpeed=Double.parseDouble(prop.getProperty("minInitialSpeed"));
		  
		  this.cameraDroneMassFactor=Double.parseDouble(prop.getProperty("cameraDroneMassFactor"));
		  this.communicatorDroneMassFactor=Double.parseDouble(prop.getProperty("communicatorDroneMassFactor"));
		  this.microphoneDroneMassFactor=Double.parseDouble(prop.getProperty("microphoneDroneMassFactor"));
		  this.basicDroneMass=Double.parseDouble(prop.getProperty("basicDroneMass"));
		  
		  this.xTorus=Boolean.getBoolean(prop.getProperty("xTorus"));
		  this.yTorus=Boolean.getBoolean(prop.getProperty("yTorus"));
		 
		
		  
		  this.roomBoundsX=Integer.parseInt(prop.getProperty("roomBoundsX"));
		  this.roomBoundsY=Integer.parseInt(prop.getProperty("roomBoundsY"));
		  this.roomBoundsZ=Integer.parseInt(prop.getProperty("roomBoundsZ"));
	
		  this.objectivePositionX=Integer.parseInt(prop.getProperty("objectivePositionX"));
		  this.objectivePositionY=Integer.parseInt(prop.getProperty("objectivePositionY"));
		  this.objectivePositionZ=Integer.parseInt(prop.getProperty("objectivePositionZ"));
		  
		  this.alphaPSO=Double.parseDouble(prop.getProperty("alphaPSO"));
		  this.betaPSO=Double.parseDouble(prop.getProperty("betaPSO"));
		  this.gammaPSO=Double.parseDouble(prop.getProperty("gammaPSO"));
		  this.refreshVect();
	}
}
