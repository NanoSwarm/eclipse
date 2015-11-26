package swarm.model.agents.Drone.room;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
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
	 * Builds an initialized instance of this public local state.
	 * @param owner The agent owning this public local state.
	 * @param initialX The initial x coordinate of the drone.
	 * @param initialY The initial y coordinate of the drone.
	 * @param initialVelocityAlongX The initial velocity of the drone along the X axis.
	 * @param initialVelocityAlongY The initial velocity of the drone along the Y axis.
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
		this.forme=new Cone(0.007f,0.009f);
		this.color=color2;
		ColoringAttributes ca=new ColoringAttributes();
		ca.setColor(color);	
		Appearance ap=new Appearance();
		ap.setColoringAttributes(ca);		
		this.forme.setAppearance(ap);
		
		
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
	 */
	public void setAcceleration( double dx2, double dy2,double dz2 ){
		this.acceleration.set( dx2, dy2, dz2 );
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
	 * @param x influences on the x axis
	 * @param y influences on the y axis
	 */
	public void setInfluence( double x, double y,double z){
		this.influence.set(x,y,z);		
	}
}
