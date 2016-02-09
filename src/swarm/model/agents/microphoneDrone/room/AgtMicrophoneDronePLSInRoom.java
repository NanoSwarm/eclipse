package swarm.model.agents.microphoneDrone.room;

import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;

import fr.lgi2a.similar.microkernel.agents.IAgent4Engine;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
/**
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 * create a microphone agent and initialize it in the room in public local state 
 *
 */
public class AgtMicrophoneDronePLSInRoom extends AgtDronePLSInRoom {
	/*
	 * the shape of the drone in the 3D Probe
	 */
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
	public AgtMicrophoneDronePLSInRoom(
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
			double detectionRange,
			Color3f microphoneDroneColor
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
				detectionRange,
				microphoneDroneColor
		);
	}
}