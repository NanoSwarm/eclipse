package swarm.probes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import fr.lgi2a.similar.microkernel.IProbe;
import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.level.SwarmLevelList;
/**
 * 
 * @author Alexandre JIN
 * The probe displaying the simulation in a 3D way
 *
 */
@SuppressWarnings("serial")
public class ProbeJFrame3D extends Frame implements IProbe{
	
	/**
	 * the only and unique universe we are using
	 */
	public SimpleUniverse simpleUniverse;
	
	/**
	* 
	*/
	public BranchGroup branchGroup;
	
	/**
	* 
	*/
	public ProbeJFrame3D (){
		System.setProperty("sun.awt.noerasebackground", "true");
		Canvas3D canvas3D=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		add(BorderLayout.CENTER,canvas3D);
		this.simpleUniverse=new SimpleUniverse(canvas3D);	
		this.branchGroup=new BranchGroup();
		Background background = new Background(new Color3f(Color.gray));    
		background.setApplicationBounds(new BoundingBox()); 
		this.branchGroup.addChild(background);
		this.simpleUniverse.getViewingPlatform().setNominalViewingTransform();	
		BoundingSphere bounds = new BoundingSphere(new Point3d(), 10000.0);
		Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
	    AmbientLight ambientLightNode = new AmbientLight(ambientColor);
	    ambientLightNode.setInfluencingBounds(bounds);
	    branchGroup.addChild(ambientLightNode);
	    // Set up the directional lights
	    Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
	    Vector3f light1Direction = new Vector3f(0.0f, -0.2f, -1.0f);

	    DirectionalLight light1 = new DirectionalLight(light1Color,
	        light1Direction);
	    light1.setInfluencingBounds(bounds);
	    branchGroup.addChild(light1);
		setTitle("3D BOIDS");
	
		
		setBounds(0,0,1000,1000);
		setVisible(true);
		addWindowListener(
				new WindowAdapter(){ 
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				});
	}

	@Override
	public void prepareObservation() { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtInitialTimes(
			SimulationTimeStamp initialTimestamp,
			ISimulationEngine simulationEngine
	) {
		this.createagents(initialTimestamp,simulationEngine);
		this.simpleUniverse.addBranchGraph(this.branchGroup);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtPartialConsistentTime(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine){
		this.updateagents(timestamp,simulationEngine);

	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endObservation() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reactToError(
			String errorMessage, 
			Throwable cause
	) { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reactToAbortion(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	) { }
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtFinalTime(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	) { 
		
	}

	/**
	 * Create the agents at the initial position and state.
	 * @param timestamp The time stamp when the observation is made.
	 * @param simulationEngine The engine where the simulation is running.
	 */
	public void createagents(
		SimulationTimeStamp timestamp,
		ISimulationEngine simulationEngine
){
	IPublicLocalDynamicState chamberState = simulationEngine.getSimulationDynamicStates().get( 
			SwarmLevelList.ROOM
			
	);
	Vector3d centre=new Vector3d(0,0,2.41);
	int somme=0;
	for( ILocalStateOfAgent agtState : chamberState.getPublicLocalStateOfAgents() ){
		AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;

		if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.CAMERADRONE ) ){
			castedAgtState = (AgtCameraDronePLSInRoom) agtState;
		}
	
		else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) ){
		castedAgtState = (AgtCommunicatorDronePLSInRoom) agtState;
	}
		else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) ){
		 castedAgtState = (AgtMicrophoneDronePLSInRoom) agtState;
		 
	}	else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE ) ){
		 castedAgtState = (AgtMeasurementDronePLSInRoom) agtState;	
	}			 
			Transform3D translate=new Transform3D();
			Vector3d position=new Vector3d(
					castedAgtState.getLocation().x/1000,
					-castedAgtState.getLocation().x/1000,
					castedAgtState.getLocation().z/1000);
	 		translate.setTranslation(position);
	 		Transform3D rotate=new Transform3D();
	 		rotate.rotZ(Math.PI/2);
	 		translate.mul(rotate);
			castedAgtState.transformGroup= new TransformGroup();
			castedAgtState.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
			castedAgtState.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);	
			castedAgtState.transformGroup.setTransform(translate);
			castedAgtState.transformGroup.addChild(castedAgtState.forme);
			this.branchGroup.addChild(castedAgtState.transformGroup);
			// Center of the group of nanodrone
			centre.x+=castedAgtState.getLocation().x;
			centre.y+=castedAgtState.getLocation().y;

			somme++;
}		
	// Set the position of the camera
	
	TransformGroup tg = this.simpleUniverse.getViewingPlatform().getViewPlatformTransform();
	
	Transform3D transs=new Transform3D();
	centre.x/=1000*somme;
	centre.y=-(centre.y/(1000*somme));
	transs.setTranslation(centre);
	tg.setTransform(transs);
	 //sensitivity of the movement
	 double mousesensitivity=0.005;
	    // Creation of the rotation Y axis ( hold left click )
	    MouseRotate mouseRotate = new MouseRotate(MouseBehavior.INVERT_INPUT);
	    mouseRotate.setFactor(mousesensitivity);
	    mouseRotate.setTransformGroup(tg);
	    // Area of the rotation Y
	    mouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000));
	 	this.branchGroup.addChild(mouseRotate);

	    // Creation of the translation X(hold right click)
	    MouseTranslate mouseTranslate = new MouseTranslate(MouseBehavior.INVERT_INPUT);
	    mouseTranslate.setFactor(mousesensitivity);
	    mouseTranslate.setTransformGroup(tg);


	    // Area of the translation X
	    BoundingBox box= new BoundingBox(new Point3d (0,0,0),new Point3d(0.0001,0,0));
	    //   mouseTranslate.setSchedulingBounds(new BoundingSphere(new Point3d(10,10,10), 0.2));
	   mouseTranslate.setSchedulingBounds(box);
	    this.branchGroup.addChild(mouseTranslate);

	    // Creation of the zoom (hold alt+left click) 
	    MouseZoom mouseZoom = new MouseZoom(MouseBehavior.INVERT_INPUT);
	    mouseZoom.setFactor(mousesensitivity);
	    mouseZoom.setTransformGroup(tg);

	    // Area of the zoom
	    mouseZoom.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
	    this.branchGroup.addChild(mouseZoom);
	    
	    //Navigation using keypad
	    /* Down/up arrow : move backward/forward
	     * right/left_arrow : move right/left
	     * page up/down : move up/down
	     * +shift to slow movement
	     */
	    KeyNavigatorBehavior key=new KeyNavigatorBehavior(tg); 
	    key.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
	    this.branchGroup.addChild(key);
		branchGroup.compile();
	
	}
/**
 * Update the position of each agent at each stamptime.
 * @param timestamptimestamp The time stamp when the observation is made.
 * @param simulationEngine The engine where the simulation is running.
 */

public void updateagents(SimulationTimeStamp timestamp,
		ISimulationEngine simulationEngine
){
	IPublicLocalDynamicState chamberState = simulationEngine.getSimulationDynamicStates().get( 
			SwarmLevelList.ROOM
	);
	for( ILocalStateOfAgent agtState : chamberState.getPublicLocalStateOfAgents() ){
		AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;

		if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.CAMERADRONE ) ){
			castedAgtState = (AgtCameraDronePLSInRoom) agtState;
			
		}else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) ){
		castedAgtState = (AgtCommunicatorDronePLSInRoom) agtState;
		
		}else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) ){
		 castedAgtState = (AgtMicrophoneDronePLSInRoom) agtState;
		}else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE ) ){
		 castedAgtState = (AgtMeasurementDronePLSInRoom) agtState;	
		}
	
			Transform3D currenttrans=new Transform3D();
			Transform3D trans=new Transform3D();
			Transform3D rotate=new Transform3D();
			Vector3d currentvect=new Vector3d();
			Vector3d position =new Vector3d(
					castedAgtState.getLocation().x/1000,
					-castedAgtState.getLocation().y/1000,
					castedAgtState.getLocation().z/1000);			
			castedAgtState.transformGroup.getTransform(currenttrans);
			trans.get(currentvect);
			Vector3d newvect=new Vector3d(
					position.x-currentvect.x,
					position.y-currentvect.y,
					position.z-currentvect.z);
			
			trans.setTranslation(newvect);	
			Vector3d vit=new Vector3d(castedAgtState.getVelocity().getX(),castedAgtState.getVelocity().getY(),castedAgtState.getVelocity().getZ());
			
			
			
			
			rotate.rotX(0);
			rotate.rotY(0);
			rotate.rotZ(Math.PI + Math.atan2(vit.x, vit.y) );			
	 		trans.mul(rotate);
	 		
	 		rotate.rotX(0);
	 		rotate.rotY( Math.atan2( vit.z , Math.sqrt(Math.pow(vit.x,2) + Math.pow(vit.y, 2))) );
	 		rotate.rotZ(0);
	 		trans.mul(rotate);
	 		
	 		rotate.rotX( Math.PI/2 );
	 		rotate.rotY(0);
	 		rotate.rotZ(0);
	 		trans.mul(rotate);
	 		

			castedAgtState.transformGroup.setTransform(trans);
			
		}
	
	
}
}

