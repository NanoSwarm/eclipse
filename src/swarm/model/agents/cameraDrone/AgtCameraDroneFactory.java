package swarm.model.agents.cameraDrone;

import java.awt.Color;

import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Cone;

import fr.lgi2a.similar.microkernel.libs.generic.EmptyGlobalState;
import fr.lgi2a.similar.microkernel.libs.generic.EmptyLocalStateOfAgent;
import swarm.model.SwarmParameters;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.level.SwarmLevelList;
import swarm.tools.RandomValueFactory;

public class AgtCameraDroneFactory {
	
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
	 * Generates a new agent of the "Camera Drone" category.
	 * @param initialX The initial x coordinate of the bubble in the "Room" level.
	 * @param initialY The initial y coordinate of the bubble in the "Room" level.
	 * @return The newly created instance.
	 */
	public static AgtCameraDrone generate(
			double initialX,
			double initialY,
			double initialZ
	){
		AgtCameraDrone result = new AgtCameraDrone( );
		// Define the initial global state of the agent.
		result.initializeGlobalState( new EmptyGlobalState( ) );
		// Define the agent in the "Room" level.
		result.includeNewLevel(
				SwarmLevelList.ROOM,
				new AgtCameraDronePLSInRoom( 
						result, 
						initialX, 
						initialY, 
						initialZ,
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxInitialSpeed, PARAMETERS.maxInitialSpeed),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxInitialSpeed, PARAMETERS.maxInitialSpeed),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxInitialSpeed, PARAMETERS.maxInitialSpeed),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxInitialSpeed, PARAMETERS.maxInitialSpeed),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxInitialSpeed, PARAMETERS.maxInitialSpeed),
						RandomValueFactory.getStrategy().randomDouble(-PARAMETERS.maxInitialSpeed, PARAMETERS.maxInitialSpeed),
						PARAMETERS.cameraDroneColor
						),
				new EmptyLocalStateOfAgent(
					SwarmLevelList.ROOM, 
					result
				)
		);
		return result;
	}
}
