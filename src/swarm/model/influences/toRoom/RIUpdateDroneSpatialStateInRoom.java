package swarm.model.influences.toRoom;

import java.util.HashSet;
import java.util.Set;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.influences.RegularInfluence;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * This influence is sent by the environment to the "Room" level to trigger the computation 
 * of the new acceleration, speed and location of the "Drone" agents.
 */
public class RIUpdateDroneSpatialStateInRoom extends RegularInfluence{
	

	/**
	 * The category of this influence.
	 */
	public static final String CATEGORY = "Update Drone spatial state";
	
	/**
	 * The "drones" which state has to be updated.
	 */
	private Set<AgtDronePLSInRoom> Drones;
	
	/**
	 * Builds an initialized instance of this influence.
	 * @param timeLowerBound The lower bound of the transitory period during which this influence was created.
	 * @param timeUpperBound The upper bound of the transitory period during which this influence was created.
	 */
	public RIUpdateDroneSpatialStateInRoom(
		SimulationTimeStamp timeLowerBound,
		SimulationTimeStamp timeUpperBound
	) {
		super(
			CATEGORY, 
			SwarmLevelList.ROOM,
			timeLowerBound, 
			timeUpperBound
		);
		this.Drones = new HashSet<AgtDronePLSInRoom>();
	}

	/**
	 * Gets the "drones" which state has to be updated.
	 * @return The "drones" which state has to be updated.
	 */
	public Set<AgtDronePLSInRoom> getParticlesToUpdate(){
		return this.Drones;
	}
	
	/**
	 * Adds a "drones" to update in reaction to this influence.
	 * @param The "drones" that has to be updated.
	 */
	public void addParticleToUpdate( AgtDronePLSInRoom Drone ) {
		this.Drones.add( Drone );
	}
}
