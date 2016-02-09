package swarm;
/**
 * Copyright 
 * 
 * ENSIAME
 * Université de Valenciennes et du Hainaut-Cambrésis
 * Le Mont-Houy 59313 Valenciennes Cedex 9
 * Tél : 03 27 51 12 02 - Fax : 03 27 51 12 00
 * ensiame@univ-valenciennes.fr
 * 
 * Email: 
 * 	alexandre.jin@etu.univ-valenciennes.fr
 * 	corentin.muselet@etu.univ-valenciennes.fr
 * 	mathieu.varinas@etu.univ-valenciennes.fr
 * 	marc.verraes@etu.univ-valenciennes.fr
 * 
 * Contributors:
 * 	Jin Alexandre
 * 	Varinas Matthieu
 * 	Verraes Marc
 * 	Corentin Muselet
 * 
 * This software is a simulation of a swarm of drones whose purpose is 
 * to compare the different behavior of the swarm in order to 
 * choose the best one considering the energy aspect and parameters.
 * It also fullfills differents missions shown in a 3D way.
 * 
 * This software is governed by the CeCILL-B license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-B
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-B license and that you accept its terms.
 */
import java.util.Properties;

import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.libs.engines.EngineMonothreadedDefaultdisambiguation;
import fr.lgi2a.similar.microkernel.libs.probes.ProbeExceptionPrinter;
import fr.lgi2a.similar.microkernel.libs.probes.ProbeExecutionTracker;
import swarm.initialization.SwarmInitialization;
import swarm.interfaceSimulation.ConfigInterface;
import swarm.model.SwarmParameters;
import swarm.model.agents.Drone.AgtDroneFactory;
import swarm.model.agents.cameraDrone.AgtCameraDroneFactory;
import swarm.model.agents.communicatorDrone.AgtCommunicatorDroneFactory;
import swarm.model.agents.measurementDrone.AgtMeasurementDroneFactory;
import swarm.model.agents.microphoneDrone.AgtMicrophoneDroneFactory;
import swarm.probes.MapInterface;
import swarm.probes.ProbeInterface;
import swarm.probes.ProbeJFrame3D;


/**
 * 
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
 * The main class starting the simulation.
 */
public class SwarmMain {
	
	public static SwarmInitialization simulationModel;
	private static ISimulationEngine engine;
	private static ProbeInterface resultInterface;
	private static SwarmParameters parameters;
	/**
	 * Private Constructor to prevent class instantiation.
	 */
	private SwarmMain() 
	{
		
	}	
	public static void main(String[] Args)
	{
		
		// Create the parameters used in this simulation.
		parameters = new SwarmParameters();
		try{
	         Properties prop = ConfigInterface.load("SwarmParameters.properties"); 
	        
	         parameters.setProperties(prop);    
	       
			}
	      catch(Exception f){
	         f.printStackTrace();
	      } 
		// Register the parameters to the agent factories.
		AgtCameraDroneFactory.setParameters( parameters );
		AgtCommunicatorDroneFactory.setParameters( parameters );
		AgtDroneFactory.setParameters( parameters );
		AgtMicrophoneDroneFactory.setParameters( parameters );
		AgtMeasurementDroneFactory.setParameters( parameters );

		ConfigInterface configInterface = new ConfigInterface(parameters);	
		
		while (true) {
			while (configInterface.configurationOK == false) {
				System.out.print("");
			}
			configInterface.setEnabled(false);
			// Create the simulation engine that will run simulations
			engine = new EngineMonothreadedDefaultdisambiguation();
			// Create the probes that will listen to the execution of the simulation.	
			engine.addProbe("Error printer", new ProbeExceptionPrinter());
			engine.addProbe("Trace printer", new ProbeExecutionTracker(System.err, false));
			engine.addProbe("Chamber level Swing viewer3d", new ProbeJFrame3D(parameters));
			engine.addProbe("Energy consumption results", resultInterface = new ProbeInterface(parameters));
			if (parameters.objectiveType == 2) {
				engine.addProbe("Mapping result", new MapInterface("Map of the space", parameters));
			}
			// Create the simulation model being used.
			simulationModel = new SwarmInitialization(new SimulationTimeStamp(0),
					new SimulationTimeStamp(parameters.simulationTime), parameters);
			// Run the simulation.
			engine.runNewSimulation(simulationModel);
			configInterface.configurationOK = false;
			configInterface.setEnabled(true);
		}
	}
	
	public static void abordSimulation(){
		engine.requestSimulationAbortion();
	}
	
	public static SwarmInitialization getSimulationModel(){
		return simulationModel;
	}
	
}
	
