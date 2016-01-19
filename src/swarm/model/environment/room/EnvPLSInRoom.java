package swarm.model.environment.room;

import javax.vecmath.Vector3d;

import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractLocalStateOfEnvironment;
import swarm.SwarmMain;
import swarm.model.level.SwarmLevelList;

/**
 * The public local state of the environment in the "Room" level.
 */
public class EnvPLSInRoom extends AbstractLocalStateOfEnvironment {

	
	
	/**
	 * Builds an initialized instance of this public local state.
	 * @param owner The agent owning this public local state.
	 * @param bounds The bounds of the chamber.
	 * @throws IllegalArgumentException If the <code>bounds</code> are null
	 * or if its dimensions are lower or equal to 0.
	 */
	public EnvPLSInRoom( ) {
		super(
			SwarmLevelList.ROOM
		);
		if( SwarmMain.getSimulationModel().getParameters().roomBounds == null ){
			throw new IllegalArgumentException( "The argument cannot be null." );
		} else if( SwarmMain.getSimulationModel().getParameters().roomBounds.x <=0 || SwarmMain.getSimulationModel().getParameters().roomBounds.y <= 0 || SwarmMain.getSimulationModel().getParameters().roomBounds.z <= 0 ){
			throw new IllegalArgumentException( "The dimensions cannot be lower or equal to 0." );
		} else {
			this.bounds = SwarmMain.getSimulationModel().getParameters().roomBounds;
		}
	}
	
	//
	//
	// Declaration of the content of the public local state
	//
	//

	/**
	 * The bounds of the room.
	 */	
	private Vector3d bounds;
	
	
	/**
	 * 
	 * @return the bounds of the room
	 */
	public Vector3d getBounds( ) {
		return this.bounds;
	}
	
	
	
	
}
