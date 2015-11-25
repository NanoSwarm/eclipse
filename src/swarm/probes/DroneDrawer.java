package swarm.probes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicDynamicStateMap;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import fr.lgi2a.similar.microkernel.libs.probes.AbstractProbeImageSwingJPanel;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.environment.room.EnvPLSInRoom;
import swarm.model.level.SwarmLevelList;

/**
 * The probe displaying the content of the "liquid" level of the bubble chamber simulation using a 2 dimensional image.
 * 
 * @author <a href="http://www.yoannkubera.net" target="_blank">Yoann Kubera</a>
 * @author <a href="http://www.lgi2a.univ-artois.net/~morvan" target="_blank">Gildas Morvan</a>
 *
 */
public class DroneDrawer extends AbstractProbeImageSwingJPanel {

	/**
	 * The size in pixels of a simulation distance unit.
	 */
	private static final int MULTIPLICATION_FACTOR = 1;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Dimension computeSimulationImageDimensions(
			IPublicDynamicStateMap dynamicStateMap, SimulationTimeStamp timeStamp) {
		// Get the dimensions of the bubble chamber.
		EnvPLSInRoom chamberLevel = (EnvPLSInRoom) dynamicStateMap.get(SwarmLevelList.ROOM).getPublicLocalStateOfEnvironment();
		return new Dimension( 
				MULTIPLICATION_FACTOR * ((int) chamberLevel.getBounds().getWidth() +1),
				MULTIPLICATION_FACTOR * ((int) chamberLevel.getBounds().getHeight() +1)
				);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateGraphics(IPublicDynamicStateMap dynamicStateMap,
			SimulationTimeStamp timeStamp, Graphics2D graphics, int imgWidth, 
			int imgHeight) {
		// First draw the background.
		graphics.setColor( Color.LIGHT_GRAY );
		graphics.fillRect( 0, 0, imgWidth, imgHeight );
		// Then draw the agents.
		IPublicLocalDynamicState chamberLevel = dynamicStateMap.get(SwarmLevelList.ROOM);
		//Draw the bubbles
		for(ILocalStateOfAgent agentPLS : chamberLevel.getPublicLocalStateOfAgents()) {
			if(agentPLS.getCategoryOfAgent().equals(SwarmAgentCategoriesList.CAMERADRONE)) {
				AgtCameraDronePLSInRoom cameraDronePLS = (AgtCameraDronePLSInRoom) agentPLS;				
				
				graphics.setColor( Color.BLUE );
				Shape droneShape = new Ellipse2D.Double(
					cameraDronePLS.getLocation().x - 0.5,
					cameraDronePLS.getLocation().y + 0.5,
					4,
					8
				);
				AffineTransform at = AffineTransform.getRotateInstance(
						-Math.atan2(cameraDronePLS.getVelocity().x,cameraDronePLS.getVelocity().y),
						cameraDronePLS.getLocation().x,
						cameraDronePLS.getLocation().y
					);
				droneShape = at.createTransformedShape(droneShape);
				graphics.fill(droneShape);
				
			}else if(agentPLS.getCategoryOfAgent().equals(SwarmAgentCategoriesList.COMMUNICATORDRONE)) {
				AgtCommunicatorDronePLSInRoom communicatorDronePLS = (AgtCommunicatorDronePLSInRoom) agentPLS;				
				
				graphics.setColor( Color.RED );
				Shape droneShape = new Ellipse2D.Double(
					communicatorDronePLS.getLocation().x - 0.5,
					communicatorDronePLS.getLocation().y + 0.5,
					4,
					8
				);
				AffineTransform at = AffineTransform.getRotateInstance(
						-Math.atan2(communicatorDronePLS.getVelocity().x,communicatorDronePLS.getVelocity().y),
						communicatorDronePLS.getLocation().x,
						communicatorDronePLS.getLocation().y
					);
				droneShape = at.createTransformedShape(droneShape);
				graphics.fill(droneShape);
			}else if(agentPLS.getCategoryOfAgent().equals(SwarmAgentCategoriesList.DRONE)) {
				AgtDronePLSInRoom DronePLS = (AgtDronePLSInRoom) agentPLS;				
				
				graphics.setColor( Color.MAGENTA );
				Shape droneShape = new Ellipse2D.Double(
					DronePLS.getLocation().x - 0.5,
					DronePLS.getLocation().y + 0.5,
					4,
					8
				);
				AffineTransform at = AffineTransform.getRotateInstance(
						-Math.atan2(DronePLS.getVelocity().x,DronePLS.getVelocity().y),
						DronePLS.getLocation().x,
						DronePLS.getLocation().y
					);
				droneShape = at.createTransformedShape(droneShape);
				graphics.fill(droneShape);
			}else if(agentPLS.getCategoryOfAgent().equals(SwarmAgentCategoriesList.MICROPHONEDRONE)) {
				AgtMicrophoneDronePLSInRoom microphoneDronePLS = (AgtMicrophoneDronePLSInRoom) agentPLS;				
				
				graphics.setColor( Color.BLACK );
				Shape droneShape = new Ellipse2D.Double(
					microphoneDronePLS.getLocation().x - 0.5,
					microphoneDronePLS.getLocation().y + 0.5,
					4,
					8
				);
				AffineTransform at = AffineTransform.getRotateInstance(
						-Math.atan2(microphoneDronePLS.getVelocity().x,microphoneDronePLS.getVelocity().y),
						microphoneDronePLS.getLocation().x,
						microphoneDronePLS.getLocation().y
					);
				droneShape = at.createTransformedShape(droneShape);
				graphics.fill(droneShape);
			}
		}
	}
}


