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

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.universe.SimpleUniverse;

import fr.lgi2a.similar.microkernel.IProbe;
import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.simpleDrone.room.AgtSimpleDronePLSInRoom;
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

private void createagents(
		SimulationTimeStamp timestamp,
		ISimulationEngine simulationEngine
){
	IPublicLocalDynamicState chamberState = simulationEngine.getSimulationDynamicStates().get( 
			SwarmLevelList.ROOM			
	);	
// Create each agents 
	for( ILocalStateOfAgent agtState : chamberState.getPublicLocalStateOfAgents() ){
		if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.SIMPLEDRONE ) ){
			AgtSimpleDronePLSInRoom castedAgtState = (AgtSimpleDronePLSInRoom) agtState;
			castedAgtState.sphere=new Cone(0.007f,0.009f);
			// set up the directional light to display the 3D Shape
			   BoundingSphere bound =new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
			   Color3f light1Color = new Color3f(0.0f, 2.0f, 0.0f);
			   Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
			   DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
			   light1.setInfluencingBounds(bound);
			   this.branchGroup.addChild(light1);
			 // Set up the ambient light
			   Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
			   AmbientLight ambientLightNode = new AmbientLight(ambientColor);
			   ambientLightNode.setInfluencingBounds(bound);
			   this.branchGroup.addChild(ambientLightNode);
			  // Place the agent at the initial position
			   Transform3D translate=new Transform3D();
					Vector3d vitesse=new Vector3d(castedAgtState.getLocation().getX()/1000
							,-castedAgtState.getLocation().getY()/1000,castedAgtState.getLocationZ()/1000);
				translate.setTranslation(vitesse);
					castedAgtState.transformGroup= new TransformGroup();
					castedAgtState.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
					castedAgtState.transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);	
					castedAgtState.transformGroup.setTransform(translate);
					castedAgtState.transformGroup.addChild(castedAgtState.sphere);
						this.branchGroup.addChild(castedAgtState.transformGroup);

		}
	}
	
	
	
	// Set the position of the camera
	 TransformGroup tg = this.simpleUniverse.getViewingPlatform().getViewPlatformTransform();
	//sensitivity of the movement
	 double mousesensitivity=0.005;
	    // Creation of the rotation Y axis ( hold left click )
	    MouseRotate mouseRotate = new MouseRotate(MouseBehavior.INVERT_INPUT);
	    mouseRotate.setFactor(mousesensitivity);
	    mouseRotate.setTransformGroup(tg);
	    // Area of the rotation Y
	    mouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
	 	this.branchGroup.addChild(mouseRotate);

	    // Creation of the translation X(hold right click)
	    MouseTranslate mouseTranslate = new MouseTranslate(MouseBehavior.INVERT_INPUT);
	    mouseTranslate.setFactor(mousesensitivity);
	    mouseTranslate.setTransformGroup(tg);

	    // Area of the translation X
	    mouseTranslate.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
	    this.branchGroup.addChild(mouseTranslate);

	    // Creation of the zoom (hold alt+left click) 
	    MouseZoom mouseZoom = new MouseZoom(MouseBehavior.INVERT_INPUT);
	    mouseZoom.setFactor(mousesensitivity);
	    mouseZoom.setTransformGroup(tg);

	    // Area of the zoom
	    mouseZoom.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
	    this.branchGroup.addChild(mouseZoom);
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
		if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.SIMPLEDRONE ) ){
			AgtSimpleDronePLSInRoom castedAgtState = (AgtSimpleDronePLSInRoom) agtState;
			
			Transform3D currenttrans=new Transform3D();
			Transform3D trans=new Transform3D();
			Vector3d currentvect=new Vector3d();
			Vector3d vitesse=new Vector3d(castedAgtState.getLocation().getX()/1000
							,-castedAgtState.getLocation().getY()/1000,castedAgtState.getLocationZ()/1000);
			// get the current transform3D
			castedAgtState.transformGroup.getTransform(currenttrans);
			trans.get(currentvect);
			// update the current vect with the new vect.
			Vector3d newvect=new Vector3d(vitesse.x-currentvect.x,vitesse.y-currentvect.y,vitesse.z-currentvect.z);
					trans.setTranslation(newvect);	
					castedAgtState.transformGroup.setTransform(trans);
		}
	}
}
	}

