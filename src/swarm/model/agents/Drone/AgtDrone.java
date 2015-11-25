package swarm.model.agents.Drone;

import fr.lgi2a.similar.microkernel.libs.generic.PassiveAgent;
import swarm.model.agents.SwarmAgentCategoriesList;

public class AgtDrone extends PassiveAgent{
	
	/**
	 * Builds a agent instance without initializing the global state and the public
	 * local state of the agent.
	 * The setter of these elements have to be called manually.
	 */
	public AgtDrone( ) {
		super( SwarmAgentCategoriesList.DRONE );
	}
}
