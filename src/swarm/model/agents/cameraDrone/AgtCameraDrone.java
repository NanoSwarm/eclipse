package swarm.model.agents.cameraDrone;

import fr.lgi2a.similar.microkernel.libs.generic.PassiveAgent;
import swarm.model.agents.SwarmAgentCategoriesList;
/**
 * Builds a camera agent instance without initializing the global state and the public
 * local state of the agent.
 * The setter of these elements have to be called manually.
 */
public class AgtCameraDrone extends PassiveAgent{
	

	public AgtCameraDrone( ) {
		super( SwarmAgentCategoriesList.CAMERADRONE );
	}
}
