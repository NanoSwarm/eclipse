package swarm.model.agents.cameraDrone.room;

import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;

import fr.lgi2a.similar.microkernel.agents.IAgent4Engine;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

public class AgtCameraDronePLSInRoom extends AgtDronePLSInRoom {
	public Primitive forme;
	
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
	public AgtCameraDronePLSInRoom(
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
			Color3f cameraDroneColor
	) {
		super(
				owner,
				initialX,
				initialY,
				initialZ,
				initialVelocityAlongX,
				initialVelocityAlongY,
				initialVelocityAlongZ,
				initialAccelerationAlongX,
				initialAccelerationAlongY,
				initialAccelerationAlongZ,
				initialEnergy,
				cameraDroneColor
		);

	}
}
