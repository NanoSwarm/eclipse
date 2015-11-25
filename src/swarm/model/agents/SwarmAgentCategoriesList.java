package swarm.model.agents;

import fr.lgi2a.similar.microkernel.AgentCategory;

/**
 * The list of levels of a "Swarm" simulation.
 */
public class SwarmAgentCategoriesList {
	/**
	 * This constructor is unused since this class only defines static values.
	 * It is declared as protected to prevent the instantiation of this class while 
	 * supporting inheritance.
	 */
	protected SwarmAgentCategoriesList ( ){ }
	
	/**
	 * The "camera drone" agent category
	 */
	public static final AgentCategory CAMERADRONE = new AgentCategory( "camera drone" );
	
	/**
	 * The "microphone drone" agent category
	 */
	public static final AgentCategory MICROPHONEDRONE = new AgentCategory( "microphone drone" );
	
	/**
	 * The "communicator drone" agent category
	 */
	public static final AgentCategory COMMUNICATORDRONE = new AgentCategory( "communicator drone" );
	
	/**
	 * The "drone" agent category
	 */
	public static final AgentCategory DRONE = new AgentCategory( "drone" );
}
