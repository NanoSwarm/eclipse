package swarm.model.environment.room;

import java.awt.geom.Rectangle2D;

import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractLocalStateOfEnvironment;
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
	public EnvPLSInRoom(
			Rectangle2D bounds
	) {
		super(
			SwarmLevelList.ROOM
		);
		if( bounds == null ){
			throw new IllegalArgumentException( "The argument cannot be null." );
		} else if( bounds.getWidth() <=0 || bounds.getHeight() <= 0 ){
			throw new IllegalArgumentException( "The dimensions cannot be lower or equal to 0." );
		} else {
			this.bounds = new Rectangle2D.Double(
					bounds.getX(), 
					bounds.getY(), 
					bounds.getWidth(), 
					bounds.getHeight()
			);
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
	private Rectangle2D bounds;

	/**
	 * The bounds of the room.
	 */
	public Rectangle2D getBounds( ) {
		return this.bounds;
	}
}
