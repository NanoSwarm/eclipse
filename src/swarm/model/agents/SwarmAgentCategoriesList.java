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
	 * The "Simple drone" agent category
	 */
	
	public static final AgentCategory SIMPLEDRONE = new AgentCategory( "simple drone" );
}
