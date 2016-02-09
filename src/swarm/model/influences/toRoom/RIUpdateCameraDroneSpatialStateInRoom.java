package swarm.model.influences.toRoom;

import java.util.HashSet;
import java.util.Set;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.influences.RegularInfluence;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 * This influence is sent by the environment to the "Room" level to trigger the computation 
 * of the new acceleration, speed and location of the "Camera Drone" agents.
 */
public class RIUpdateCameraDroneSpatialStateInRoom extends RegularInfluence{
	

	/**
	 * The category of this influence.
	 */
	public static final String CATEGORY = "Update Camera Drone spatial state";
	
	/**
	 * The "camera drones" which state has to be updated.
	 */
	private Set<AgtCameraDronePLSInRoom> cameraDrones;
	
	/**
	 * Builds an initialized instance of this influence.
	 * @param timeLowerBound The lower bound of the transitory period during which this influence was created.
	 * @param timeUpperBound The upper bound of the transitory period during which this influence was created.
	 */
	public RIUpdateCameraDroneSpatialStateInRoom(
		SimulationTimeStamp timeLowerBound,
		SimulationTimeStamp timeUpperBound
	) {
		super(
			CATEGORY, 
			SwarmLevelList.ROOM,
			timeLowerBound, 
			timeUpperBound
		);
		this.cameraDrones = new HashSet<AgtCameraDronePLSInRoom>();
	}

	/**
	 * Gets the "camera drones" which state has to be updated.
	 * @return The "camera drones" which state has to be updated.
	 */
	public Set<AgtCameraDronePLSInRoom> getParticlesToUpdate(){
		return this.cameraDrones;
	}
	
	/**
	 * Adds a "camera drones" to update in reaction to this influence.
	 * @param the "camera drones" that has to be updated.
	 */
	public void addParticleToUpdate( AgtCameraDronePLSInRoom cameraDrone ) {
		this.cameraDrones.add( cameraDrone );
	}
}
