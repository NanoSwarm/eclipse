package swarm.model.influences.toRoom;

import java.util.HashSet;
import java.util.Set;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.influences.RegularInfluence;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * 
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 * Influence sent by the level room to trigger the computation of the energy of the drones
 */
public class RIUpdateDroneEnergyLevelInRoom extends RegularInfluence{
	/**
	 * The category of this influence.
	 */
	public static final String CATEGORY = "Update drone energy level";
	
	/**
	 * The "drones" which energy level has to be updated.
	 */
	private Set<AgtDronePLSInRoom> Drones;
	
	/**
	 * Builds an initialized instance of this influence.
	 * @param timeLowerBound The lower bound of the transitory period during which this influence was created.
	 * @param timeUpperBound The upper bound of the transitory period during which this influence was created.
	 */
	public RIUpdateDroneEnergyLevelInRoom(
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
