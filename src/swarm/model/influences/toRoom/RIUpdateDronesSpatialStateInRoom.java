package swarm.model.influences.toRoom;

import java.util.HashSet;
import java.util.Set;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.influences.RegularInfluence;
import swarm.model.agents.simpleDrone.room.AgtSimpleDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * This influence is sent by the environment to the "Room" level to trigger the computation 
 * of the new acceleration, speed and location of the "Drones" agents.
 */
public class RIUpdateDronesSpatialStateInRoom extends RegularInfluence {

	/**
	 * The category of this influence.
	 */
	public static final String CATEGORY = "Update particles spatial state";
	
	/**
	 * The "simple drones" which state has to be updated.
	 */
	private Set<AgtSimpleDronePLSInRoom> simpleDrones;
	
	/**
	 * Builds an initialized instance of this influence.
	 * @param timeLowerBound The lower bound of the transitory period during which this influence was created.
	 * @param timeUpperBound The upper bound of the transitory period during which this influence was created.
	 */
	public RIUpdateDronesSpatialStateInRoom(
		SimulationTimeStamp timeLowerBound,
		SimulationTimeStamp timeUpperBound
	) {
		super(
			CATEGORY, 
			SwarmLevelList.ROOM,
			timeLowerBound, 
			timeUpperBound
		);
		this.simpleDrones = new HashSet<AgtSimpleDronePLSInRoom>();
	}

	/**
	 * Gets the "simple drones" which state has to be updated.
	 * @return The "simple drones" which state has to be updated.
	 */
	public Set<AgtSimpleDronePLSInRoom> getParticlesToUpdate(){
		return this.simpleDrones;
	}
	
	/**
	 * Adds a "simple drones" to update in reaction to this influence.
	 * @param "simple drones" The particle that has to be updated.
	 */
	public void addParticleToUpdate( AgtSimpleDronePLSInRoom simpleDrone ) {
		this.simpleDrones.add( simpleDrone );
	}
}
