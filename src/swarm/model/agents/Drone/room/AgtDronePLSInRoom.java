package swarm.model.agents.Drone.room;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;

import fr.lgi2a.similar.microkernel.agents.IAgent4Engine;
import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractLocalStateOfAgent;
import swarm.model.level.SwarmLevelList;

public class AgtDronePLSInRoom extends AbstractLocalStateOfAgent {
	/**
	 * 
	 */
	public TransformGroup transformGroup;
	/**
	 * 
	 */
	public Primitive forme;
	/**
	 * 
	 */
	public Color3f color;
	
	/**
	 * 
	 * @param owner The agent owning this public local state.
	 * @param initialX The initial x coordinate of the drone.
	 * @param initialY The initial y coordinate of the drone.
	 * @param initialZ The initial z coordinate of the drone.
	 * @param initialVelocityAlongX The initial velocity of the drone along the X axis.
	 * @param initialVelocityAlongY The initial velocity of the drone along the Y axis.
	 * @param initialVelocityAlongZ The initial velocity of the drone along the Z axis.
	 * @param initialAccelerationAlongX The initial acceleration of the drone along the X axis.
	 * @param initialAccelerationAlongY The initial acceleration of the drone along the Y axis.
	 * @param initialAccelerationAlongZ The initial acceleration of the drone along the Z axis.
	 * @param initialEnergy The initial energy level of the drone.
	 * @param cameraDroneColor The color of the drone
	 */
	public AgtDronePLSInRoom(
			IAgent4Engine owner,
			double initialX,
			double initialY,
			double initialZ,
			double initialVelocityAlongX,
			double initialVelocityAlongY,
			double initialVelocityAlongZ,
			double initialAccelerationAlongX,
			double initialAccelerationAlongY,
			double initialAccelerationAlongZ,
			double initialEnergy,
			Color3f color2

	) {
		super(
			SwarmLevelList.ROOM,
			owner
		);
		this.location=new Vector3d(
				initialX,
				initialY,
				initialZ);
		this.velocity = new Vector3d(
				initialVelocityAlongX,
				initialVelocityAlongY,
				initialVelocityAlongZ);
		this.acceleration = new Vector3d(
				initialAccelerationAlongX,
				initialAccelerationAlongY,
				initialAccelerationAlongZ);
		this.influence = new Vector3d(0,0,0);
		this.energy = initialEnergy;
		this.forme=new Cone(0.007f,0.015f);
		this.color=color2;

		Color3f black=new Color3f(0.0f,0.0f,0.0f);
		Color3f white=new Color3f(1.0f,1.0f,1.0f);
		Appearance ap=new Appearance();
		Material material = new Material(color, black, color, white, 64);
		material.setLightingEnable(true);
		ap.setCapability(Appearance.ALLOW_MATERIAL_READ);
		ap.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
		ap.setMaterial(material);
		forme.setAppearance(ap);
		
	}
	
	//
	//
	// Declaration of the content of the public local state
	//
	//
	
	/**
	 * The location of the drone in the room.
	 */
	private Vector3d location;

	/**
	 * Gets the location of the drone in the room.
	 * @return The location of the drone in the room.
	 */
	public Vector3d getLocation( ){
		return this.location;
	}

	/**
	 * Sets the location of the drone in the room.
	 * @param x The new x coordinate of the drone in the room.
	 * @param y The new y coordinate of the drone in the room.
	 */
	public void setLocation( double x, double y,double z ){
		this.location.set( x, y,z );
	}
	
	/**
	 * The vector determining the current velocity of the drone along both axes.
	 */
	private Vector3d velocity;

	/**
	 * Gets the velocity of the drone in the room.
	 * @return The velocity of the drone in the room.
	 */
	public Vector3d getVelocity( ){
		return this.velocity;
	}
	
	/**
	 * Sets the velocity of the drone in the room.
	 * @param dx The velocity of the drone along the x axis.
	 * @param dy The velocity of the drone along the y axis.
	 * @param dz The velocity of the drone along the z axis.
	 */
	public void setVelocity( double dx, double dy ,double dz){
		this.velocity.set( dx, dy,dz );
	}
	
	/**
	 * The vector determining the current acceleration of the drone along both axes.
	 */
	private Vector3d acceleration;

	/**
	 * Gets the acceleration of the drone in the room.
	 * @return The acceleration of the drone in the room.
	 */
	public Vector3d getAcceleration( ){
		return this.acceleration;
	}
	
	/**
	 * Sets the acceleration of the drone in the room.
	 * @param dx2 The new acceleration of the drone in the room, along the x axis.
	 * @param dy2 The new acceleration of the drone in the room, along the y axis.
	 * @param dz2 The new acceleration of the drone in the room, along the z axis.
	 */
	public void setAcceleration( double dx2, double dy2,double dz2 ){
		this.acceleration.set( dx2, dy2, dz2 );
	}
	
	/**
	 * The point attracting the drone.
	 */
	private Vector3d destination;
	
	/**
	 * Change the destination of the drone.
	 * @param dest the new destination.
	 */
	public void setDestination(Vector3d dest){
		destination = dest;
	}
	
	/**
	 * 
	 * @return the destination of the drone.
	 */
	public Vector3d getDestination(){
		return this.destination;
	}
	
	/**
	 * The energy stored by the drone.
	 */
	private double energy;
	
	/**
	 * 
	 * @return the energy level of the drone.
	 */
	public double getEnergy(){
		return this.energy;
	}
	
	/**
	 * 
	 * @param en new energy level for the drone
	 */
	public void setEnergy(double en){
		this.energy = en;
	}
	/**
	 * values of the influences on the drone 
	 */
	private Vector3d influence;
	
	
	/**
	 * get the value of the influences on the drone
	 * @return the influence on the drone
	 */
	public Vector3d getInfluence(){
		
		return this.influence;
	}
	
	/**
	 * sets the sum of influences on the drone in the room
	 * @param x influences on the x axis.
	 * @param y influences on the y axis.
	 * @param z influences on the z axis.
	 */
	public void setInfluence( double x, double y,double z){
		this.influence.set(x,y,z);		
	}
	
	/**
	 * change the color of the drone
	 * @param color the new color of the drone
	 */
	public void setColor (Color3f color){
		this.color = color;
	}
	
	/**
	 * return the color of the drone
	 * @return the color of the drone
	 */
	public Color3f getColor(){
		return this.color;
	}
	
	/**
	 * The boolean telling if the drone is able to fly or not
	 */
	public Boolean flying = true;
	
	/**
	 *  get the state of the drone
	 * @return flying, the state of the drone
	 */
	public Boolean isFlying(){
		return flying;
	}
	
	/**
	 * set the state of the drone
	 * @param b the new state of the drone
	 */
	public void setFlying(Boolean b){
		flying = b;
	}
	
	
}

