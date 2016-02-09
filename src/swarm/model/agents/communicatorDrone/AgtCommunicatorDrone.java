package swarm.model.agents.communicatorDrone;

import fr.lgi2a.similar.microkernel.libs.generic.PassiveAgent;
import swarm.model.agents.SwarmAgentCategoriesList;

/**
 * 
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 *
 */
public class AgtCommunicatorDrone extends PassiveAgent{
	
	/**
	 * Builds a agent instance without initializing the global state and the public
	 * local state of the agent.
	 * The setter of these elements have to be called manually.
	 */
	public AgtCommunicatorDrone( ) {
		super( SwarmAgentCategoriesList.COMMUNICATORDRONE );
	}
}
