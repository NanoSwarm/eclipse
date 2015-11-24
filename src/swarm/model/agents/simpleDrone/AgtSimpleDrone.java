package swarm.model.agents.simpleDrone;

import fr.lgi2a.similar.microkernel.libs.generic.PassiveAgent;
import swarm.model.agents.SwarmAgentCategoriesList;

public class AgtSimpleDrone extends PassiveAgent{
	/**
	 * Builds a agent instance without initializing the global state and the public
	 * local state of the agent.
	 * The setter of these elements have to be called manually.
	 */
	public AgtSimpleDrone( ) {
		super( SwarmAgentCategoriesList.SIMPLEDRONE );
	}
}
