package swarm.model.influences.toRoom;

import java.util.HashSet;
import java.util.Set;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.influences.RegularInfluence;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * This influence is sent by the environment to the "Room" level to trigger the computation 
 * of the new acceleration, speed and location of the "Communicator Drone" agents.
 */
public class RIUpdateCommunicatorDroneSpatialStateInRoom extends RegularInfluence{
	

	/**
	 * The category of this influence.
	 */
	public static final String CATEGORY = "Update communicator spatial state";
	
	/**
	 * The "communicator drones" which state has to be updated.
	 */
	private Set<AgtCommunicatorDronePLSInRoom> communicatorDrones;
	
	/**
	 * Builds an initialized instance of this influence.
	 * @param timeLowerBound The lower bound of the transitory period during which this influence was created.
	 * @param timeUpperBound The upper bound of the transitory period during which this influence was created.
	 */
	public RIUpdateCommunicatorDroneSpatialStateInRoom(
		SimulationTimeStamp timeLowerBound,
		SimulationTimeStamp timeUpperBound
	) {
		super(
			CATEGORY, 
			SwarmLevelList.ROOM,
			timeLowerBound, 
			timeUpperBound
		);
		this.communicatorDrones = new HashSet<AgtCommunicatorDronePLSInRoom>();
	}

	/**
	 * Gets the "communicator drones" which state has to be updated.
	 * @return The "communicator drones" which state has to be updated.
	 */
	public Set<AgtCommunicatorDronePLSInRoom> getParticlesToUpdate(){
		return this.communicatorDrones;
	}
	
	/**
	 * Adds a "communicator drones" to update in reaction to this influence.
	 * @param The "communicator drones" that has to be updated.
	 */
	public void addParticleToUpdate( AgtCommunicatorDronePLSInRoom communicatorDrone ) {
		this.communicatorDrones.add( communicatorDrone );
	}
}
