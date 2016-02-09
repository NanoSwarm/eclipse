package swarm.model.influences.toRoom;

import java.util.HashSet;
import java.util.Set;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.influences.RegularInfluence;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 * This influence is sent by the environment to the "Room" level to trigger the computation 
 * of the new acceleration, speed and location of the "Microphone Drone" agents.
 */
public class RIUpdateMicrophoneDroneSpatialStateInRoom extends RegularInfluence{
	

	/**
	 * The category of this influence.
	 */
	public static final String CATEGORY = "Update microphone drone spatial state";
	
	/**
	 * The "microphone drones" which state has to be updated.
	 */
	private Set<AgtMicrophoneDronePLSInRoom> microphoneDrones;
	
	/**
	 * Builds an initialized instance of this influence.
	 * @param timeLowerBound The lower bound of the transitory period during which this influence was created.
	 * @param timeUpperBound The upper bound of the transitory period during which this influence was created.
	 */
	public RIUpdateMicrophoneDroneSpatialStateInRoom(
		SimulationTimeStamp timeLowerBound,
		SimulationTimeStamp timeUpperBound
	) {
		super(
			CATEGORY, 
			SwarmLevelList.ROOM,
			timeLowerBound, 
			timeUpperBound
		);
		this.microphoneDrones = new HashSet<AgtMicrophoneDronePLSInRoom>();
	}

	/**
	 * Gets the "microphone drones" which state has to be updated.
	 * @return The "microphone drones" which state has to be updated.
	 */
	public Set<AgtMicrophoneDronePLSInRoom> getParticlesToUpdate(){
		return this.microphoneDrones;
	}
	
	/**
	 * Adds a "microphone drones" to update in reaction to this influence.
	 * @param "microphone drones" The particle that has to be updated.
	 */
	public void addParticleToUpdate( AgtMicrophoneDronePLSInRoom microphoneDrone ) {
		this.microphoneDrones.add( microphoneDrone );
	}
}
