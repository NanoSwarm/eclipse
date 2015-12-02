package swarm.model;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;

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
	 * 
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
	 * The bounds of the swarm room.
	 */
	public Rectangle2D roomBounds;
	
	/**
	 * The length of the simulation
	 */
	public int simulationTime;
	
	/**
	 * Builds a parameters set containing default values.
	 */	

	
	public Color3f cameraDroneColor;

	public Color3f droneColor;
	
	public Color3f communicatorDroneColor;
		
	public Color3f microphoneDroneColor;
	
	public SwarmParameters () {
		super();
		
		this.cameraDroneColor=new Color3f(Color.blue);
		this.droneColor=new Color3f(Color.magenta);
		this.communicatorDroneColor=new Color3f(Color.red);
		this.microphoneDroneColor=new Color3f(Color.orange);
		
		this.repulsionDistance = 20;
		this.orientationDistance = 90;
		this.attractionDistance = 100;
		this.repulsionCoeff = 50;
		this.orientationCoeff = 500;
		this.attractionCoeff = 0.002;
		
		
		this.maxInitialSpeed = 0.02;
		this.minInitialSpeed = 0.01;
		this.maxSpeed = 0.5;
		this.maxAcc = 0.08;
		this.initialSize = 5;
		this.xTorus = true;
		this.yTorus = true;
		this.cameraDroneMassFactor = 0.9;
		this.communicatorDroneMassFactor = 0.9;
		this.microphoneDroneMassFactor = 0.9;
		this.initialEnergy = 100;
		
		
		this.nbOfCameraDroneAgents = 30;
		this.nbOfCommunicatorDroneAgents = 30;
		this.nbOfDroneAgents = 10;
		this.nbOfMicrophoneDroneAgents = 30;
		
		
		this.roomBounds = new Rectangle2D.Double(0, 0, 1500, 750);
		this.simulationTime = 5000;
	}

}
