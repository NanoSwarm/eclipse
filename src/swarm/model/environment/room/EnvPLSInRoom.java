package swarm.model.environment.room;

import javax.vecmath.Vector3d;

import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractLocalStateOfEnvironment;
import swarm.model.SwarmParameters;
import swarm.model.level.SwarmLevelList;
import swarm.model.level.room.Graph;

/**
 * The public local state of the environment in the "Room" level.
 */
public class EnvPLSInRoom extends AbstractLocalStateOfEnvironment {

	private Graph graph;
	
	/**
	 * Builds an initialized instance of this public local state.
	 * @param owner The agent owning this public local state.
	 * @param bounds The bounds of the chamber.
	 * @throws IllegalArgumentException If the <code>bounds</code> are null
	 * or if its dimensions are lower or equal to 0.
	 */
	public EnvPLSInRoom(
			SwarmParameters parameters
	) {
		super(
			SwarmLevelList.ROOM
		);
		graph=new Graph(parameters);
		if( parameters.roomBounds == null ){
			throw new IllegalArgumentException( "The argument cannot be null." );
		} else if( parameters.roomBounds.x <=0 || parameters.roomBounds.y <= 0 || parameters.roomBounds.z <= 0 ){
			throw new IllegalArgumentException( "The dimensions cannot be lower or equal to 0." );
		} else {
			this.bounds = parameters.roomBounds;
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
	public Graph getGraph(){
			return graph;
	}
	
	
	
	
	
}
