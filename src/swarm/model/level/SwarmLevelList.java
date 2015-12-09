package swarm.model.level;

import fr.lgi2a.similar.microkernel.LevelIdentifier;

public class SwarmLevelList {
	
	/**
	 * This constructor is unused since this class only defines static values.
	 * It is declared as protected to prevent the instantiation of this class while 
	 * supporting inheritance.
	 */
	protected SwarmLevelList ( ){ }

	/**
	 * The identifier of the "Room" level.
	 */
	public static final LevelIdentifier ROOM = new LevelIdentifier( "ROOM" );
	
}
