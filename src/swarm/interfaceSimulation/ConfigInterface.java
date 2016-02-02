package swarm.interfaceSimulation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import swarm.model.SwarmParameters;

/**
 * 
 * @author akanash
 * The main interface, used to change the parameters before the simulation.
 */
public class ConfigInterface extends JFrame
							 implements WindowListener, ActionListener
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * The flag checking if the configuration is finished.
	 */
	public boolean configurationOK=false;
	
	/**
	 * The spinners needed for our interface.
	 */
	private SpinnerLine maxSpeedLine = null;
	private SpinnerLine maxAccLine = null;
	private SpinnerLine initialEnergyLine = null;
	
	private SpinnerLine nbOfCameraDroneLine = null;
	private SpinnerLine nbOfCommunicatorDroneLine = null;
	private SpinnerLine nbOfMeasurementDroneLine = null;
	private SpinnerLine nbOfMicrophoneDroneLine = null;
	private SpinnerLine cameraDetectionRangeLine = null;
	private SpinnerLine measurementDetectionRangeLine = null;
	private SpinnerLine microphoneDetectionRangeLine = null;
	private SpinnerLine communicatorDetectionRangeLine = null;
	
	private SpinnerLine attractionDistanceLine = null;
	private SpinnerLine orientationDistanceLine = null;
	private SpinnerLine repulsionDistanceLine = null;
	private SpinnerLine attractionCoeffLine = null;
	private SpinnerLine orientationCoeffLine = null;
	private SpinnerLine repulsionCoeffLine = null;
	 
	
	/**
	 * 
	 * @param parameters, the parameters of the simulation.
	 */
	public ConfigInterface(SwarmParameters parameters)
	{
		super("Configuration");
		
		JPanel container = new JPanel();		
		GridBagLayout gblConfigInterface = new GridBagLayout();
		GridBagConstraints configCons = new GridBagConstraints();
		container.setLayout(gblConfigInterface);
		addWindowListener(this);
		
		
		ChangeListener changeListener = new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				/*
				 * General parameters
				 */
				if(e.getSource().hashCode() == maxSpeedLine.getSliderHashcode()){
					parameters.maxSpeed = maxSpeedLine.sliderChanged();
				}else if (e.getSource().hashCode() == maxSpeedLine.getSpinnerHashcode()){
					parameters.maxSpeed = maxSpeedLine.spinnerChanged();
				}
				
				else if(e.getSource().hashCode() == maxAccLine.getSliderHashcode()){
					parameters.maxAcc = maxAccLine.sliderChanged();
				}else if (e.getSource().hashCode() == maxAccLine.getSpinnerHashcode()){
					parameters.maxAcc = maxAccLine.spinnerChanged();
				}
				
				else if(e.getSource().hashCode() == initialEnergyLine.getSliderHashcode()){
					parameters.initialEnergy = initialEnergyLine.sliderChanged();
				}else if (e.getSource().hashCode() == initialEnergyLine.getSpinnerHashcode()){
					parameters.initialEnergy = initialEnergyLine.spinnerChanged();
				}
				
				
				/*
				 * Camera drone parameters
				 */
				else if(e.getSource().hashCode() == nbOfCameraDroneLine.getSliderHashcode()){
					parameters.nbOfCameraDroneAgents = (int)nbOfCameraDroneLine.sliderChanged();
				}else if (e.getSource().hashCode() == nbOfCameraDroneLine.getSpinnerHashcode()){
					parameters.nbOfCameraDroneAgents = (int)nbOfCameraDroneLine.spinnerChanged();
				}
				
				if(e.getSource().hashCode() == cameraDetectionRangeLine.getSliderHashcode()){
					parameters.cameraDroneDetectionRange = cameraDetectionRangeLine.sliderChanged();
				}else if (e.getSource().hashCode() == cameraDetectionRangeLine.getSpinnerHashcode()){
					parameters.cameraDroneDetectionRange = cameraDetectionRangeLine.spinnerChanged();
				}
				
				/*
				 * Communicator drone parameters
				 */
				else if(e.getSource().hashCode() == nbOfCommunicatorDroneLine.getSliderHashcode()){
					parameters.nbOfCommunicatorDroneAgents = (int)nbOfCommunicatorDroneLine.sliderChanged();
				}else if (e.getSource().hashCode() == nbOfCommunicatorDroneLine.getSpinnerHashcode()){
					parameters.nbOfCommunicatorDroneAgents = (int)nbOfCommunicatorDroneLine.spinnerChanged();
				}
				
				if(e.getSource().hashCode() == communicatorDetectionRangeLine.getSliderHashcode()){
					parameters.communicatorDroneDetectionRange = communicatorDetectionRangeLine.sliderChanged();
				}else if (e.getSource().hashCode() == communicatorDetectionRangeLine.getSpinnerHashcode()){
					parameters.communicatorDroneDetectionRange = communicatorDetectionRangeLine.spinnerChanged();
				}
				
				/*
				 * Measurement drone parameters
				 */
				else if(e.getSource().hashCode() == nbOfMeasurementDroneLine.getSliderHashcode()){
					parameters.nbOfMeasurementDroneAgents = (int)nbOfMeasurementDroneLine.sliderChanged();
				}else if (e.getSource().hashCode() == nbOfMeasurementDroneLine.getSpinnerHashcode()){
					parameters.nbOfMeasurementDroneAgents = (int)nbOfMeasurementDroneLine.spinnerChanged();
				}
				
				if(e.getSource().hashCode() == measurementDetectionRangeLine.getSliderHashcode()){
					parameters.measurementDroneDetectionRange = measurementDetectionRangeLine.sliderChanged();
				}else if (e.getSource().hashCode() == measurementDetectionRangeLine.getSpinnerHashcode()){
					parameters.measurementDroneDetectionRange = measurementDetectionRangeLine.spinnerChanged();
				}
				
				/*
				 * Microphone drone parameters
				 */
				else if(e.getSource().hashCode() == nbOfMicrophoneDroneLine.getSliderHashcode()){
					parameters.nbOfMicrophoneDroneAgents = (int)nbOfMicrophoneDroneLine.sliderChanged();
				}else if (e.getSource().hashCode() == nbOfMicrophoneDroneLine.getSpinnerHashcode()){
					parameters.nbOfMicrophoneDroneAgents = (int)nbOfMicrophoneDroneLine.spinnerChanged();
				}
				
				if(e.getSource().hashCode() == microphoneDetectionRangeLine.getSliderHashcode()){
					parameters.microphoneDroneDetectionRange = microphoneDetectionRangeLine.sliderChanged();
				}else if (e.getSource().hashCode() == microphoneDetectionRangeLine.getSpinnerHashcode()){
					parameters.microphoneDroneDetectionRange = microphoneDetectionRangeLine.spinnerChanged();
				}
				
				/*
				 * Boids parameters
				 */				
				else if(e.getSource().hashCode() == attractionDistanceLine.getSliderHashcode()){
					parameters.attractionDistance = attractionDistanceLine.sliderChanged();
				}else if (e.getSource().hashCode() == attractionDistanceLine.getSpinnerHashcode()){
					parameters.attractionDistance = attractionDistanceLine.spinnerChanged();
				}
				
				else if(e.getSource().hashCode() == orientationDistanceLine.getSliderHashcode()){
					parameters.orientationDistance = orientationDistanceLine.sliderChanged();
				}else if (e.getSource().hashCode() == orientationDistanceLine.getSpinnerHashcode()){
					parameters.orientationDistance = orientationDistanceLine.spinnerChanged();
				}
				
				else if(e.getSource().hashCode() == repulsionDistanceLine.getSliderHashcode()){
					parameters.repulsionDistance = repulsionDistanceLine.sliderChanged();
				}else if (e.getSource().hashCode() == repulsionDistanceLine.getSpinnerHashcode()){
					parameters.repulsionDistance = repulsionDistanceLine.spinnerChanged();
				}
				
				else if(e.getSource().hashCode() == attractionCoeffLine.getSliderHashcode()){
					parameters.attractionCoeff = attractionCoeffLine.sliderChanged();
				}else if (e.getSource().hashCode() == attractionCoeffLine.getSpinnerHashcode()){
					parameters.attractionCoeff = attractionCoeffLine.spinnerChanged();
				}
				
				else if(e.getSource().hashCode() == orientationCoeffLine.getSliderHashcode()){
					parameters.orientationCoeff = orientationCoeffLine.sliderChanged();
				}else if (e.getSource().hashCode() == orientationCoeffLine.getSpinnerHashcode()){
					parameters.orientationCoeff = orientationCoeffLine.spinnerChanged();
				}
				
				else if(e.getSource().hashCode() == repulsionCoeffLine.getSliderHashcode()){
					parameters.repulsionCoeff = repulsionCoeffLine.sliderChanged();
				}else if (e.getSource().hashCode() == repulsionCoeffLine.getSpinnerHashcode()){
					parameters.repulsionCoeff = repulsionCoeffLine.spinnerChanged();
				}
			}
		};
		
		
		/*
		 * General parameters
		 */
		new MyLabel("General parameters",gblConfigInterface,container);
		maxSpeedLine = new SpinnerLine(
				"Maximum speed :",
				(int)parameters.maxSpeed,
				0,
				20,
				100,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		maxAccLine = new SpinnerLine(
				"Maximum acceleration :",
				(int)parameters.maxAcc,
				0,
				20,
				100,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		initialEnergyLine = new SpinnerLine(
				"Initial energy :",
				(int)parameters.initialEnergy,
				0,
				10000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		
		/*
		 * Camera drone parameters
		 */	
		new MyLabel("Camera drone parameters",gblConfigInterface,container);
		
		nbOfCameraDroneLine = new SpinnerLine(
				"Number of \"CameraDrone\" agents :",
				parameters.nbOfCameraDroneAgents,
				0,
				1500,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		cameraDetectionRangeLine = new SpinnerLine(
				"Camera drone Detection Range :",
				(int)parameters.cameraDroneDetectionRange,
				0,
				1000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		/*
		 * Communicator drone parameters
		 */	
		new MyLabel("Communicator drone parameters",gblConfigInterface,container);
		nbOfCommunicatorDroneLine = new SpinnerLine(
				"Number of \"CommunicatorDrone\" agents :",
				parameters.nbOfCommunicatorDroneAgents,
				0,
				1500,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		communicatorDetectionRangeLine = new SpinnerLine(
				"Communicator drone Detection Range :",
				(int)parameters.communicatorDroneDetectionRange,
				0,
				1000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
				
		/*
		 * Measurement drone parameters
		 */		
		new MyLabel("Measurement drone parameters",gblConfigInterface,container);
		nbOfMeasurementDroneLine = new SpinnerLine(
				"Number of \"MeasurementDrone\" agents :",
				parameters.nbOfMeasurementDroneAgents,
				0,
				1500,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		measurementDetectionRangeLine = new SpinnerLine(
				"Measurement drone Detection Range :",
				(int)parameters.measurementDroneDetectionRange,
				0,
				1000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		/*
		 * Microphone drone parameters
		 */	
		new MyLabel("Microphone drone parameters",gblConfigInterface,container);
		nbOfMicrophoneDroneLine = new SpinnerLine(
				"Number of \"MicrohponeDrone\" agents :",
				parameters.nbOfMicrophoneDroneAgents,
				0,
				1500,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		microphoneDetectionRangeLine = new SpinnerLine(
				"Microphone drone Detection Range :",
				(int)parameters.microphoneDroneDetectionRange,
				0,
				1000, 
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		/*
		 * Boids parameters
		 */			
		new MyLabel("Boids model parameters",gblConfigInterface,container);
		attractionDistanceLine = new SpinnerLine(
				"Attraction distance :",
				(int)parameters.attractionDistance,
				0,
				1500,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		orientationDistanceLine = new SpinnerLine(
				"Orientation distance :",
				(int)parameters.orientationDistance,
				0,
				1500, 
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		repulsionDistanceLine = new SpinnerLine(
				"Repulsion distance :",
				(int)parameters.repulsionDistance,
				0,
				1500, 
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		attractionCoeffLine = new SpinnerLine(
				"Attraction coefficient :",
				(int)parameters.attractionCoeff,
				0,
				100,
				100,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		orientationCoeffLine = new SpinnerLine(
				"Orientation coefficient :",
				(int)parameters.orientationCoeff,
				0,
				10000,
				100,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		repulsionCoeffLine = new SpinnerLine(
				"Repulsion coefficient :",
				(int)parameters.repulsionCoeff,
				0,
				10000,
				100,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		JLabel filling1 = new JLabel(" ");
		gblConfigInterface.setConstraints(filling1, configCons);
		container.add(filling1);

		//Last line
		JButton launchSimu = new JButton("Launch Simulation");
		configCons.anchor = GridBagConstraints.WEST;
		gblConfigInterface.setConstraints(launchSimu, configCons);
		container.add(launchSimu);
		launchSimu.addActionListener(this);
		launchSimu.setActionCommand("Launch Simulation");
		
		JScrollPane scrollpane = new JScrollPane(container, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollpane);
		
		//
		pack();
		setVisible(true);
		//setSize(300, 500);
	}
	
	/**
	 * Check if the user want to launch the simulation.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Launch Simulation"))
		{
			configurationOK=true;
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {System.exit(0);}
	@Override
	public void windowClosed(WindowEvent e) {System.exit(0);}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	/**
	 * Load the initial values of the parameters from a external file.
	 * @param filename the name of the file.
	 * @return The parameters from the file.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Properties load(String filename) throws IOException, FileNotFoundException{
	      Properties properties = new Properties();

	      FileInputStream input = new FileInputStream(filename); 
	      try{

	         properties.load(input);
	         return properties;
	      }

	              finally{

	         input.close();

	      }
	}
}
