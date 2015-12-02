package swarm.model.influences.toRoom;

import java.util.HashSet;
import java.util.Set;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.influences.RegularInfluence;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

public class RIUpdateMeasurementDroneSpatialStateInRoom extends RegularInfluence{
	/**
	 * The category of this influence.
	 */
	public static final String CATEGORY = "Update measurement Drone spatial state";
	
	/**
	 * The "drones" which state has to be updated.
	 */
	private Set<AgtMeasurementDronePLSInRoom> Drones;
	
	/**
	 * Builds an initialized instance of this influence.
	 * @param timeLowerBound The lower bound of the transitory period during which this influence was created.
	 * @param timeUpperBound The upper bound of the transitory period during which this influence was created.
	 */
	public RIUpdateMeasurementDroneSpatialStateInRoom(
		SimulationTimeStamp timeLowerBound,
		SimulationTimeStamp timeUpperBound
	) {
		super(
			CATEGORY, 
			SwarmLevelList.ROOM,
			timeLowerBound, 
			timeUpperBound
		);
		this.Drones = new HashSet<AgtMeasurementDronePLSInRoom>();
	}

	/**
	 * Gets the "measurement drones" which state has to be updated.
	 * @return The "measurement drones" which state has to be updated.
	 */
	public Set<AgtMeasurementDronePLSInRoom> getParticlesToUpdate(){
		return this.Drones;
	}
	
	/**
	 * Adds a "measurement drones" to update in reaction to this influence.
	 * @param The "drones" that has to be updated.
	 */
	public void addParticleToUpdate( AgtMeasurementDronePLSInRoom Drone ) {
		this.Drones.add( Drone );
	}
}
