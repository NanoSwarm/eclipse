package swarm.model.agents.measurementDrone;

import fr.lgi2a.similar.microkernel.libs.generic.PassiveAgent;
import swarm.model.agents.SwarmAgentCategoriesList;

public class AgtMeasurementDrone extends PassiveAgent{
	/**
	 * Builds a agent instance without initializing the global state and the public
	 * local state of the agent.
	 * The setter of these elements have to be called manually.
	 */
	public AgtMeasurementDrone( ) {
		super( SwarmAgentCategoriesList.MEASUREMENTDRONE );
	}
}
