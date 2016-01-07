package swarm.model.environment.room;

import javax.vecmath.Vector3d;

import fr.lgi2a.similar.microkernel.libs.abstractimpl.AbstractLocalStateOfEnvironment;
import swarm.model.SwarmParameters;
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
			SwarmParameters parameters
	) {
		super(
			SwarmLevelList.ROOM
		);
		if( parameters.roomBounds == null ){
			throw new IllegalArgumentException( "The argument cannot be null." );
		} else if( parameters.roomBounds.x <=0 || parameters.roomBounds.y <= 0 || parameters.roomBounds.z <= 0 ){
			throw new IllegalArgumentException( "The dimensions cannot be lower or equal to 0." );
		} else {
			this.bounds = parameters.roomBounds;
			int length;
			if (parameters.objectiveType.equals("point")){
				length = (int)Math.floor(
						Math.min(
							Math.min(
								Math.min(parameters.cameraDroneDetectionRange, parameters.communicatorDroneDetectionRange),
								Math.min(parameters.droneDetectionRange, parameters.measurementDroneDetectionRange)),
							parameters.microphoneDroneDetectionRange));

			}else if (parameters.objectiveType.equals("measure")){
				length = (int) Math.floor(parameters.measurementDroneDetectionRange);
				
			}else throw new IllegalArgumentException( "Objective type unknown" );
			
			spaceGraph = new int
					[(int) Math.ceil(parameters.roomBounds.x/length)]
					[(int) Math.ceil(parameters.roomBounds.y/length)]
					[(int) Math.ceil(parameters.roomBounds.z/length)]
					[1];
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
	
	/**
	 * The table containing informations about the different sub-volume of our environment. Used for full coverage search
	 */
	private int[][][][] spaceGraph;
	
	public Vector3d getFrontier(double x, double y, double z){
		
		
		return new Vector3d(x,y,z);
	}
	
}
