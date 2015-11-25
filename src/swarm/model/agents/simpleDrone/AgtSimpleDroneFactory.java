package swarm.model.agents.simpleDrone;

import fr.lgi2a.similar.microkernel.libs.generic.EmptyGlobalState;
import fr.lgi2a.similar.microkernel.libs.generic.EmptyLocalStateOfAgent;
import swarm.model.SwarmParameters;
import swarm.model.agents.simpleDrone.room.AgtSimpleDronePLSInRoom;
import swarm.model.level.SwarmLevelList;
import swarm.tools.RandomValueFactory;

/**
 * The factory creating instances of agents the "Simple Drone" category.
 */
public class AgtSimpleDroneFactory {
	
	/**
     * The parameters that are used in this agent factory.
     */
    private static SwarmParameters PARAMETERS = null;
	
    /**
     * Gets the parameters used in this agent factory.
     * @return The parameters used in this agent factory.
     * @throws IllegalArgumentException If the parameters are not set.
     */
    public static SwarmParameters getParameters() {
        if( PARAMETERS == null ){
            throw new IllegalArgumentException( 
                "The parameters are not set." 
            );
        } else {
            return PARAMETERS;
        }
    }
    
    /**
     * Sets the parameters used in this agent factory.
     * @param parameters The new parameters used in this agent factory.
     */
    public static void setParameters(
    		SwarmParameters parameters
    ){
        PARAMETERS = parameters;
    }
	
	/**
	 * Generates a new agent of the "Simple Drone" category.
	 * @param initialX The initial x coordinate of the bubble in the "Room" level.
	 * @param initialY The initial y coordinate of the bubble in the "Room" level.
	 * @return The newly created instance.
	 */
	public static AgtSimpleDrone generate(
			double initialX,
			double initialY
			
	){
		AgtSimpleDrone result = new AgtSimpleDrone( );
		// Define the initial global state of the agent.
		result.initializeGlobalState( new EmptyGlobalState( ) );
		// Define the agent in the "Room" level.
		result.includeNewLevel(
				SwarmLevelList.ROOM,
				new AgtSimpleDronePLSInRoom( 
						result, 
						initialX, 
						initialY, 
						0.0,
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxSpeed, PARAMETERS.maxSpeed),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxSpeed, PARAMETERS.maxSpeed),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxSpeed, PARAMETERS.maxSpeed),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxAcc, PARAMETERS.maxAcc),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxAcc, PARAMETERS.maxAcc),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxAcc, PARAMETERS.maxAcc)
				),
				new EmptyLocalStateOfAgent(
					SwarmLevelList.ROOM, 
					result
				)
		);
		return result;
	}
}
