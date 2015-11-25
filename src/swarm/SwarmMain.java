package swarm;

import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.libs.engines.EngineMonothreadedDefaultdisambiguation;
import fr.lgi2a.similar.microkernel.libs.probes.ProbeExceptionPrinter;
import fr.lgi2a.similar.microkernel.libs.probes.ProbeExecutionTracker;
import fr.lgi2a.similar.microkernel.libs.probes.ProbeImageSwingJFrame;
import swarm.initialization.SwarmInitialization;
import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.AgtDroneFactory;
import swarm.model.agents.cameraDrone.AgtCameraDroneFactory;
import swarm.model.agents.communicatorDrone.AgtCommunicatorDroneFactory;
import swarm.model.agents.microphoneDrone.AgtMicrophoneDroneFactory;
import swarm.probes.DroneDrawer;
import swarm.probes.ProbeJFrame3D;
import swarm.probes.ProbePrintingParticleLocationOverTime;

public class SwarmMain {
	/**
	 * Private Constructor to prevent class instantiation.
	 */
	private SwarmMain() {
		
	}
	
	/**
	 * The main method of the simulation.
	 * @param args The command line arguments.
	 */
	public static void main(String[] Args)
	{
		// Create the parameters used in this simulation.
		SwarmParameters parameters = new SwarmParameters();
		// Register the parameters to the agent factories.
				AgtCameraDroneFactory.setParameters( parameters );
				AgtCommunicatorDroneFactory.setParameters( parameters );
				AgtDroneFactory.setParameters( parameters );
				AgtMicrophoneDroneFactory.setParameters( parameters );
		
		// Create the simulation engine that will run simulations
		ISimulationEngine engine = new EngineMonothreadedDefaultdisambiguation( );
		// Create the probes that will listen to the execution of the simulation.	
		
		engine.addProbe( 
				"Error printer", 
				new ProbeExceptionPrinter( )
		);
		engine.addProbe(
				"Trace printer", 
				new ProbeExecutionTracker( System.err, false )
		);
		engine.addProbe(
				"Drone location",
				new ProbePrintingParticleLocationOverTime( System.out )
		);
		engine.addProbe(
				"Chamber level Swing viewer3d",
				new ProbeJFrame3D()															// The frame is resized automatically
				
		);
			
		engine.addProbe(
				"Chamber level Swing viewer",
				new ProbeImageSwingJFrame( 
					"Room level", 													// The name of the frame
					new DroneDrawer(), 										
					ProbeImageSwingJFrame.ClosingManagementStrategy.ABORT_SIMULATION, 	// The simulation will abort if the frame is closed
					null																// The frame is resized automatically
				)
		);
		
			
			// Create the simulation model being used.
			SwarmInitialization simulationModel = new SwarmInitialization(
				new SimulationTimeStamp( 0 ), 
				new SimulationTimeStamp( parameters.simulationTime ), 
				parameters
			);
			// Run the simulation.
			engine.runNewSimulation( simulationModel );
		
	}

}
