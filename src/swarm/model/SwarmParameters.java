package swarm.model;

import java.awt.geom.Rectangle2D;

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
	 * The maximal initial speed of turtles.
	 */
	public double maxInitialSpeed;
	
	/**
	 * The minimal initial speed of turtles.
	 */
	public double minInitialSpeed;
	
	/**
	 * The maximum speed of the drones
	 */	
	public double maxSpeed;
	
	/**
	 * The maximum acceleration of the drones
	 */	
	public double maxAcc;
	
	/**
	 * The perception angle of turtles.
	 */
	public double perceptionAngle;
	
	/**
	 * The number of agents in the simulation.
	 */
	public int nbOfAgents;
	
	/**
	 * The size of the drone
	 */
	public double initialSize;
	
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
	public SwarmParameters () {
		super();
		this.repulsionDistance = 10;
		this.orientationDistance =20;
		this.attractionDistance = 250;
		this.repulsionCoeff = 1;
		this.orientationCoeff = 10;
		this.attractionCoeff = 0.05;
		this.maxInitialSpeed = 2;
		this.minInitialSpeed = 1;
		this.maxSpeed = 0.4;
		this.maxAcc = 0.04;
		this.perceptionAngle = 3*Math.PI/2;
		this.nbOfAgents = 200;
		this.xTorus = true;
		this.yTorus = true;
		this.initialSize = 5;
		this.roomBounds = new Rectangle2D.Double(0, 0, 1500, 750);
		this.simulationTime = 50000;
	}

}
