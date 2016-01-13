package swarm.model.agents.measurementDrone.room;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Primitive;

import fr.lgi2a.similar.microkernel.agents.IAgent4Engine;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.environment.Objective;

public class AgtMeasurementDronePLSInRoom extends AgtDronePLSInRoom{

	public double fitness;
	public Vector3d bestOwnPos;
	public double bestOwnFitness;
	public double getFitness(){
		return fitness;
	}
	public void calculateFitness()
	{
		fitness=Objective.getObjective(this.getLocation());
	}
	public void updateFitness()
	{
		if (fitness>bestOwnFitness)
		{
			bestOwnFitness=fitness;
			bestOwnPos.set(this.getLocation().getX(),this.getLocation().getY(),this.getLocation().getZ());
		}
	}
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
	 * @param measurementDroneColor The color of the drone
	 */
	public AgtMeasurementDronePLSInRoom(
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
			Color3f measurementDroneColor
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
				measurementDroneColor
		);
		fitness=Objective.getObjective(this.getLocation());
		bestOwnFitness=fitness;
		bestOwnPos=new Vector3d(initialX,initialY,initialZ);
	}	
}
