package swarm.probes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicDynamicStateMap;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import fr.lgi2a.similar.microkernel.libs.probes.AbstractProbeImageSwingJPanel;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.simpleDrone.room.AgtSimpleDronePLSInRoom;
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
			if(agentPLS.getCategoryOfAgent().equals(SwarmAgentCategoriesList.SIMPLEDRONE)) {
				AgtSimpleDronePLSInRoom simpleDronePLS = (AgtSimpleDronePLSInRoom) agentPLS;
				graphics.setColor( Color.GREEN );
				graphics.fillOval( 
					(int) Math.floor( MULTIPLICATION_FACTOR * ( simpleDronePLS.getLocation().getX() - 0.5 ) ), 
					(int) Math.floor( MULTIPLICATION_FACTOR * ( simpleDronePLS.getLocation().getY() - 0.5 ) ), 
					
					MULTIPLICATION_FACTOR * 2, 
					MULTIPLICATION_FACTOR * 2
				);
				graphics.setColor( Color.BLACK );
				graphics.drawOval(
					(int) Math.floor( MULTIPLICATION_FACTOR * ( simpleDronePLS.getLocation().getX() - 0.5 ) ), 
					(int) Math.floor( MULTIPLICATION_FACTOR * ( simpleDronePLS.getLocation().getY() - 0.5 ) ), 
					MULTIPLICATION_FACTOR * 2, 
					MULTIPLICATION_FACTOR * 2
				);
			}
		}

	}
}


