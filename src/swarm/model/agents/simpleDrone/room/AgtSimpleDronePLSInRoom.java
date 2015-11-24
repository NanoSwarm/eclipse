package swarm.model.agents.simpleDrone.room;

import java.awt.geom.Point2D;

import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.Cone;

import fr.lgi2a.similar.microkernel.agents.IAgent4Engine;
import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractLocalStateOfAgent;
import swarm.model.level.SwarmLevelList;

/**
 * The public local state of the "Simple Drone" agent in the "Room" level.
 */
public class AgtSimpleDronePLSInRoom extends AbstractLocalStateOfAgent {
	public Cone sphere;
	public TransformGroup transformGroup;

	/**
	 * Builds an initialized instance of this public local state.
	 * @param owner The agent owning this public local state.
	 * @param initialX The initial x coordinate of the drone.
	 * @param initialY The initial y coordinate of the drone.
	 * @param initialVelocityAlongX The initial velocity of the drone along the X axis.
	 * @param initialVelocityAlongY The initial velocity of the drone along the Y axis.
	 */
	public AgtSimpleDronePLSInRoom(
			IAgent4Engine owner,
			double initialX,
			double initialY,
			double initialZ,
			double initialVelocityAlongX,
			double initialVelocityAlongY,
			double initialVelocityAlongZ,
			double initialAccelerationAlongX,
			double initialAccelerationAlongY,
			double initialAccelerationAlongZ					
	) {
		
		super(
			SwarmLevelList.ROOM,
			owner
		);
		this.location = new Point2D.Double( initialX, initialY );
		this.velocity = new Point2D.Double(
				initialVelocityAlongX,
				initialVelocityAlongY
		);
		this.acceleration = new Point2D.Double(
				initialAccelerationAlongX,
				initialAccelerationAlongY
		);
		locationZ=initialZ;
		velocityZ=initialVelocityAlongZ;
		accelerationZ=initialAccelerationAlongZ;
	}
	
	//
	//
	// Declaration of the content of the public local state
	//
	//
	
	/**
	 * The location of the drone in the room.
	 */
	private Point2D location;
	private double locationZ;
	public double getLocationZ(){
		return locationZ;
	}
	public void setLocationZ(double z)
	{
		locationZ=z;
	}
	/**
	 * Gets the location of the drone in the room.
	 * @return The location of the drone in the room.
	 */
	public Point2D getLocation( ){
		return this.location;
	}

	/**
	 * Sets the location of the drone in the room.
	 * @param x The new x coordinate of the drone in the room.
	 * @param y The new y coordinate of the drone in the room.
	 */
	public void setLocation( double x, double y ){
		this.location.setLocation( x, y );
	}
	
	/**
	 * The vector determining the current velocity of the drone along both axes.
	 */
	private Point2D velocity;
	private double velocityZ;
	public double getVelocityZ(){
		return velocityZ;
	}
	public void setVelocityZ(double z)
	{
		velocityZ=z;
	}
	/**
	 * Gets the velocity of the drone in the room.
	 * @return The velocity of the drone in the room.
	 */
	public Point2D getVelocity( ){
		return this.velocity;
	}
	
	/**
	 * Sets the velocity of the drone in the room.
	 * @param dx The velocity of the drone along the x axis.
	 * @param dy The velocity of the drone along the y axis.
	 */
	public void setVelocity( double dx, double dy ){
		this.velocity.setLocation( dx, dy );
	}
	
	/**
	 * The vector determining the current acceleration of the drone along both axes.
	 */
	private Point2D acceleration;
	private double accelerationZ;
	public double getAccelerationZ(){
		return accelerationZ;
	}
	public void setAccelerationZ(double dz)
	{
		accelerationZ=dz;
	}
	/**
	 * Gets the acceleration of the drone in the room.
	 * @return The acceleration of the drone in the room.
	 */
	public Point2D getAcceleration( ){
		return this.acceleration;
	}
	
	/**
	 * Sets the acceleration of the drone in the room.
	 * @param dx2 The new acceleration of the drone in the room, along the x axis.
	 * @param dy2 The new acceleration of the drone in the room, along the y axis.
	 */
	public void setAcceleration( double dx2, double dy2 ){
		this.acceleration.setLocation( dx2, dy2 );
	}
}
