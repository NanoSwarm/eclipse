package swarm.model.agents.communicatorDrone.room;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;

import fr.lgi2a.similar.microkernel.agents.IAgent4Engine;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;

public class AgtCommunicatorDronePLSInRoom extends AgtDronePLSInRoom {
	public Primitive forme;
	/**
	 * Builds an initialized instance of this public local state.
	 * @param owner The agent owning this public local state.
	 * @param initialX The initial x coordinate of the drone.
	 * @param initialY The initial y coordinate of the drone.
	 * @param initialVelocityAlongX The initial velocity of the drone along the X axis.
	 * @param initialVelocityAlongY The initial velocity of the drone along the Y axis.
	 */
	public AgtCommunicatorDronePLSInRoom(
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
			Color3f communicatorDroneColor
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
				communicatorDroneColor
		);
	}	
}
