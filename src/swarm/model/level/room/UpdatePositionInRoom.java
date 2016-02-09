package swarm.model.level.room;

import swarm.model.SwarmParameters;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.environment.Objective;
import swarm.model.environment.room.EnvPLSInRoom;
/**
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 * Calculates the new position after the influences on agent are made.
 *
 */
public class UpdatePositionInRoom {
	
	/**
	 * the method updating the position of drones in the RoomLevel.
	 * @param agtDrone drone send by RoomLevel for position update
	 * @param parameters the parameters of the simulation
	 */
	public static void UpdateDronePosition(AgtDronePLSInRoom agtDrone, SwarmParameters parameters,EnvPLSInRoom roomEnvState){
		if(agtDrone.getEnergy()!=0 && agtDrone.getLocation().z >= 0){
		agtDrone.setAcceleration(
				agtDrone.getAcceleration().x + agtDrone.getInfluence().x,
				agtDrone.getAcceleration().y + agtDrone.getInfluence().y,
				agtDrone.getAcceleration().z + agtDrone.getInfluence().z
				);
		}else if (agtDrone.getEnergy()==0 && agtDrone.getLocation().z >= 0){
				agtDrone.setAcceleration(0, 0, -9.81);
		}else{
			agtDrone.setAcceleration(0, 0, 0);
			agtDrone.setVelocity(0, 0, 0);
			agtDrone.setLocation(agtDrone.getLocation().x, agtDrone.getLocation().y, -0.01);
			agtDrone.setEnergy(0);
		}
		//Keep the velocity vector under the maxSeed limit.
		double speed = Math.sqrt(
				Math.pow(agtDrone.getVelocity().x,2)+
				Math.pow(agtDrone.getVelocity().y,2)+	
				Math.pow(agtDrone.getVelocity().z,2));
		
		if ( speed > parameters.maxSpeed){
			agtDrone.setVelocity(parameters.maxSpeed * agtDrone.getVelocity().x / speed ,
					parameters.maxSpeed * agtDrone.getVelocity().y / speed,
					parameters.maxSpeed * agtDrone.getVelocity().z / speed );
			agtDrone.setAcceleration(0, 0,0);
		}
		
		//Keep the acceleration vector under the maxAcc limit
		double acc = Math.sqrt(
				Math.pow(agtDrone.getAcceleration().x,2)+	
				Math.pow(agtDrone.getAcceleration().y,2)+	
				Math.pow(agtDrone.getAcceleration().z,2));
		
		if ( acc > parameters.maxAcc){
			agtDrone.setAcceleration(
					parameters.maxAcc * agtDrone.getAcceleration().x / acc ,
					parameters.maxAcc * agtDrone.getAcceleration().y / acc,
					parameters.maxAcc * agtDrone.getAcceleration().z / acc
					);
		}
		
		//Update position and speed by integration (of speed and acceleration)
		agtDrone.setLocation(
				agtDrone.getLocation().x + agtDrone.getVelocity().x + agtDrone.getAcceleration().x / 2, 
				agtDrone.getLocation().y + agtDrone.getVelocity().y + agtDrone.getAcceleration().y / 2,
				agtDrone.getLocation().z + agtDrone.getVelocity().z + agtDrone.getAcceleration().z / 2
			);
			
		agtDrone.setVelocity(
					agtDrone.getVelocity().x + agtDrone.getAcceleration().x, 
					agtDrone.getVelocity().y + agtDrone.getAcceleration().y,
					agtDrone.getVelocity().z + agtDrone.getAcceleration().z
			);
		
		if (parameters.objectiveType == 1){
			
			if (agtDrone.getCategoryOfAgent().isA(SwarmAgentCategoriesList.MEASUREMENTDRONE)){
				roomEnvState.getGraph().updateFrontier(agtDrone.getLocation(),parameters.measurementDroneDetectionRange);				
			}else if (agtDrone.getCategoryOfAgent().isA(SwarmAgentCategoriesList.CAMERADRONE)){
				roomEnvState.getGraph().updateFrontier(agtDrone.getLocation(),parameters.cameraDroneDetectionRange);				
			}else if (agtDrone.getCategoryOfAgent().isA(SwarmAgentCategoriesList.COMMUNICATORDRONE)){
				roomEnvState.getGraph().updateFrontier(agtDrone.getLocation(),parameters.communicatorDroneDetectionRange);				
			}else if (agtDrone.getCategoryOfAgent().isA(SwarmAgentCategoriesList.DRONE)){
				roomEnvState.getGraph().updateFrontier(agtDrone.getLocation(),parameters.droneDetectionRange);
			}
			
		}else if (parameters.objectiveType == 2) {
			
			if (agtDrone.getCategoryOfAgent().isA(SwarmAgentCategoriesList.MEASUREMENTDRONE))
			{
				
				Graph graph;
				Cube[][][] spaceGraph;
				AgtMeasurementDronePLSInRoom castedAgt=(AgtMeasurementDronePLSInRoom) agtDrone;
				graph=roomEnvState.getGraph();
				spaceGraph=graph.getSpaceGraph();
				int i,j,k;
			
				i=(int)(Math.floor(castedAgt.getLocation().getX()/graph.getLength()));
				j=(int)(Math.floor(castedAgt.getLocation().getY()/graph.getLength()));
				k=(int)(Math.floor(castedAgt.getLocation().getZ()/graph.getLength()));
				
				if(i > graph.getImax()-1) i = graph.getImax()-1;
				if(j > graph.getJmax()-1) j = graph.getJmax()-1;
				if(k > graph.getKmax()-1) k = graph.getKmax()-1;
				if(i < 0) i = 0;
				if(j < 0) j = 0;
				if(k < 0) k = 0;
				if (!(spaceGraph[i][j][k].cubeIsVisited()))
				{
					spaceGraph[i][j][k].setMeasuredValue(Objective.getObjective(castedAgt.getLocation()));
				}
				
				roomEnvState.getGraph().updateFrontier(agtDrone.getLocation(), parameters.measurementDroneDetectionRange);
				
			}
			
		}
	}
}
