package swarm.interfaceSimulation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import swarm.model.SwarmParameters;

public class ConfigInterface extends JFrame
							 implements WindowListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean configurationOK=false;
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
	
	
	
	public ConfigInterface(SwarmParameters parameters)
	{
		super("Configuration");
		
		GridBagLayout gblConfigInterface = new GridBagLayout();
		GridBagConstraints configCons = new GridBagConstraints();
		getContentPane().setLayout(gblConfigInterface);
		addWindowListener(this);
		
		
		ChangeListener changeListener = new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				
				/*
				 * camera drone parameters
				 */
				if(e.getSource().hashCode() == nbOfCameraDroneLine.getSliderHashcode()){
					parameters.nbOfCameraDroneAgents = nbOfCameraDroneLine.sliderChanged();
				}else if (e.getSource().hashCode() == nbOfCameraDroneLine.getSpinnerHashcode()){
					parameters.nbOfCameraDroneAgents = nbOfCameraDroneLine.spinnerChanged();
				}
				
				if(e.getSource().hashCode() == cameraDetectionRangeLine.getSliderHashcode()){
					parameters.cameraDroneDetectionRange = cameraDetectionRangeLine.sliderChanged();
				}else if (e.getSource().hashCode() == cameraDetectionRangeLine.getSpinnerHashcode()){
					parameters.cameraDroneDetectionRange = cameraDetectionRangeLine.spinnerChanged();
				}
				
				/*
				 * communicator drone parameters
				 */
				else if(e.getSource().hashCode() == nbOfCommunicatorDroneLine.getSliderHashcode()){
					parameters.nbOfCommunicatorDroneAgents = nbOfCommunicatorDroneLine.sliderChanged();
				}else if (e.getSource().hashCode() == nbOfCommunicatorDroneLine.getSpinnerHashcode()){
					parameters.nbOfCommunicatorDroneAgents = nbOfCommunicatorDroneLine.spinnerChanged();
				}
				
				if(e.getSource().hashCode() == communicatorDetectionRangeLine.getSliderHashcode()){
					parameters.communicatorDroneDetectionRange = communicatorDetectionRangeLine.sliderChanged();
				}else if (e.getSource().hashCode() == communicatorDetectionRangeLine.getSpinnerHashcode()){
					parameters.communicatorDroneDetectionRange = communicatorDetectionRangeLine.spinnerChanged();
				}
				
				/*
				 * measurement drone parameters
				 */
				else if(e.getSource().hashCode() == nbOfMeasurementDroneLine.getSliderHashcode()){
					parameters.nbOfMeasurementDroneAgents = nbOfMeasurementDroneLine.sliderChanged();
				}else if (e.getSource().hashCode() == nbOfMeasurementDroneLine.getSpinnerHashcode()){
					parameters.nbOfMeasurementDroneAgents = nbOfMeasurementDroneLine.spinnerChanged();
				}
				
				if(e.getSource().hashCode() == measurementDetectionRangeLine.getSliderHashcode()){
					parameters.measurementDroneDetectionRange = measurementDetectionRangeLine.sliderChanged();
				}else if (e.getSource().hashCode() == measurementDetectionRangeLine.getSpinnerHashcode()){
					parameters.measurementDroneDetectionRange = measurementDetectionRangeLine.spinnerChanged();
				}
				
				/*
				 * microphone drone parameters
				 */
				else if(e.getSource().hashCode() == nbOfMicrophoneDroneLine.getSliderHashcode()){
					parameters.nbOfMicrophoneDroneAgents = nbOfMicrophoneDroneLine.sliderChanged();
				}else if (e.getSource().hashCode() == nbOfMicrophoneDroneLine.getSpinnerHashcode()){
					parameters.nbOfMicrophoneDroneAgents = nbOfMicrophoneDroneLine.spinnerChanged();
				}
				
				if(e.getSource().hashCode() == microphoneDetectionRangeLine.getSliderHashcode()){
					parameters.microphoneDroneDetectionRange = microphoneDetectionRangeLine.sliderChanged();
				}else if (e.getSource().hashCode() == microphoneDetectionRangeLine.getSpinnerHashcode()){
					parameters.microphoneDroneDetectionRange = microphoneDetectionRangeLine.spinnerChanged();
				}
				
				/*
				 * boids parameters
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
		 * camera drone parameters
		 */	
		new MyLabel("Camera drone parameters",gblConfigInterface,this);
		
		nbOfCameraDroneLine = new SpinnerLine(
				"Number of \"CameraDrone\" agents :",
				parameters.nbOfCameraDroneAgents,
				0,
				1500,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		cameraDetectionRangeLine = new SpinnerLine(
				"Camera drone Detection Range :",
				(int)parameters.cameraDroneDetectionRange,
				0,
				1000,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		/*
		 * communicator drone parameters
		 */	
		new MyLabel("Communicator drone parameters",gblConfigInterface,this);
		nbOfCommunicatorDroneLine = new SpinnerLine(
				"Number of \"CommunicatorDrone\" agents :",
				parameters.nbOfCommunicatorDroneAgents,
				0,
				1500, 
				gblConfigInterface, 
				this,
				changeListener
				);
		
		communicatorDetectionRangeLine = new SpinnerLine(
				"Communicator drone Detection Range :",
				(int)parameters.communicatorDroneDetectionRange,
				0,
				1000,
				gblConfigInterface, 
				this,
				changeListener
				);
				
		/*
		 * measurement drone parameters
		 */		
		new MyLabel("Measurement drone parameters",gblConfigInterface,this);
		nbOfMeasurementDroneLine = new SpinnerLine(
				"Number of \"MeasurementDrone\" agents :",
				parameters.nbOfMeasurementDroneAgents,
				0,
				1500,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		measurementDetectionRangeLine = new SpinnerLine(
				"Measurement drone Detection Range :",
				(int)parameters.measurementDroneDetectionRange,
				0,
				1000,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		/*
		 * microphone drone parameters
		 */	
		new MyLabel("Microphone drone parameters",gblConfigInterface,this);
		nbOfMicrophoneDroneLine = new SpinnerLine(
				"Number of \"MicrohponeDrone\" agents :",
				parameters.nbOfMicrophoneDroneAgents,
				0,
				1500,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		microphoneDetectionRangeLine = new SpinnerLine(
				"Microphone drone Detection Range :",
				(int)parameters.microphoneDroneDetectionRange,
				0,
				1000, 
				gblConfigInterface, 
				this,
				changeListener
				);
		
		/*
		 * boids parameters
		 */			
		new MyLabel("Boids model parameters",gblConfigInterface,this);
		attractionDistanceLine = new SpinnerLine(
				"Attraction distance :",
				(int)parameters.attractionDistance,
				0,
				1500,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		orientationDistanceLine = new SpinnerLine(
				"Orientation distance :",
				(int)parameters.orientationDistance,
				0,
				1500, 
				gblConfigInterface, 
				this,
				changeListener
				);
		
		repulsionDistanceLine = new SpinnerLine(
				"Repulsion distance :",
				(int)parameters.repulsionDistance,
				0,
				1500, 
				gblConfigInterface, 
				this,
				changeListener
				);
		
		attractionCoeffLine = new SpinnerLine(
				"Attraction coefficient :",
				(int)parameters.attractionCoeff,
				0,
				10000,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		orientationCoeffLine = new SpinnerLine(
				"Orientation coefficient :",
				(int)parameters.orientationCoeff,
				0,
				10000,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		repulsionCoeffLine = new SpinnerLine(
				"Repulsion coefficient :",
				(int)parameters.repulsionCoeff,
				0,
				10000,
				gblConfigInterface, 
				this,
				changeListener
				);
		
		//Dernière ligne
		JButton launchSimu = new JButton("Launch Simulation");
		configCons.anchor = GridBagConstraints.WEST;
		gblConfigInterface.setConstraints(launchSimu, configCons);
		add(launchSimu);
		launchSimu.addActionListener(this);
		launchSimu.setActionCommand("Launch Simulation");
		
		
		
		//
		pack();
		setVisible(true);
		//setSize(300, 500);
	}

	
	public static JFormattedTextField getTextField(JSpinner spinner) {
	    JComponent editor = spinner.getEditor();
	    if (editor instanceof JSpinner.DefaultEditor) {
	        return ((JSpinner.DefaultEditor)editor).getTextField();
	    } else {
	        System.err.println("Unexpected editor type: "
	                           + spinner.getEditor().getClass()
	                           + " isn't a descendant of DefaultEditor");
	        return null;
	    }
	}
	

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
