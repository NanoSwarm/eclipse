package swarm.interfaceSimulation;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	
	private SpinnerLine simulationTimeLine = null;
	private SpinnerLine roomBoundsXLine = null;
	private SpinnerLine roomBoundsYLine = null;
	private SpinnerLine roomBoundsZLine = null;
	
	private SpinnerLine objectivePositionXLine = null;
	private SpinnerLine objectivePositionYLine = null;
	private SpinnerLine objectivePositionZLine = null;

	public JComboBox<String> typeObjectiveList = null;
	private JComboBox<String> resObjectiveList = null;
	
	private SwarmParameters param;
	/**
	 * 
	 * @param parameters, the parameters of the simulation.
	 */
	public ConfigInterface(SwarmParameters parameters)
	{
		super("Configuration");
		param=parameters;
		JPanel container = new JPanel();		
		GridBagLayout gblConfigInterface = new GridBagLayout();
		GridBagConstraints configCons = new GridBagConstraints();
		container.setLayout(gblConfigInterface);
		addWindowListener(this);

		configCons.gridwidth = GridBagConstraints.REMAINDER;
		JLabel filling1 = new JLabel(" ");
		gblConfigInterface.setConstraints(filling1, configCons);
		container.add(filling1);
		
		//Launch button
		JButton launchSimu = new JButton("Launch Simulation");
		configCons.anchor = GridBagConstraints.CENTER;
		gblConfigInterface.setConstraints(launchSimu, configCons);
		container.add(launchSimu);
		launchSimu.addActionListener(this);
		launchSimu.setActionCommand("Launch Simulation");
		
		
		
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
				
				/*
				 * Simulation parameters
				 */	
				else if(e.getSource().hashCode() == simulationTimeLine.getSliderHashcode() && !configurationOK){
					parameters.simulationTime = (int)simulationTimeLine.sliderChanged();
					parameters.refreshVect();
				}else if (e.getSource().hashCode() == simulationTimeLine.getSpinnerHashcode() && !configurationOK){
					parameters.simulationTime = (int)simulationTimeLine.spinnerChanged();
					parameters.refreshVect();
				}
				
				else if(e.getSource().hashCode() == roomBoundsXLine.getSliderHashcode()){
					parameters.roomBoundsX = (int)roomBoundsXLine.sliderChanged();
					parameters.refreshVect();
				}else if (e.getSource().hashCode() == roomBoundsXLine.getSpinnerHashcode()){
					parameters.roomBoundsX = (int)roomBoundsXLine.spinnerChanged();
					parameters.refreshVect();
				}
				
				else if(e.getSource().hashCode() == roomBoundsYLine.getSliderHashcode()){
					parameters.roomBoundsY = (int)roomBoundsYLine.sliderChanged();
					parameters.refreshVect();
				}else if (e.getSource().hashCode() == roomBoundsYLine.getSpinnerHashcode()){
					parameters.roomBoundsY = (int)roomBoundsYLine.spinnerChanged();
					parameters.refreshVect();
				}
				
				else if(e.getSource().hashCode() == roomBoundsZLine.getSliderHashcode()){
					parameters.roomBoundsZ = (int)roomBoundsZLine.sliderChanged();
					parameters.refreshVect();
				}else if (e.getSource().hashCode() == roomBoundsZLine.getSpinnerHashcode()){
					parameters.roomBoundsZ = (int)roomBoundsZLine.spinnerChanged();
					parameters.refreshVect();
				}
				
				/*
				 * Mission parameters
				 */
				
				
				else if(e.getSource().hashCode() == objectivePositionXLine.getSliderHashcode() && !configurationOK){
					parameters.objectivePositionX = (int)objectivePositionXLine.sliderChanged();
					parameters.refreshVect();
				}else if (e.getSource().hashCode() == objectivePositionXLine.getSpinnerHashcode() && !configurationOK){
					parameters.objectivePositionX = (int)objectivePositionXLine.spinnerChanged();
					parameters.refreshVect();
				}
				
				else if(e.getSource().hashCode() == objectivePositionYLine.getSliderHashcode() && !configurationOK){
					parameters.objectivePositionY = (int)objectivePositionYLine.sliderChanged();
					parameters.refreshVect();
				}else if (e.getSource().hashCode() == objectivePositionYLine.getSpinnerHashcode() && !configurationOK){
					parameters.objectivePositionY = (int)objectivePositionYLine.spinnerChanged();
					parameters.refreshVect();
				}
				
				else if(e.getSource().hashCode() == objectivePositionZLine.getSliderHashcode() && !configurationOK){
					parameters.objectivePositionZ = (int)objectivePositionZLine.sliderChanged();
					parameters.refreshVect();
				}else if (e.getSource().hashCode() == objectivePositionZLine.getSpinnerHashcode() && !configurationOK){
					parameters.objectivePositionZ = (int)objectivePositionZLine.spinnerChanged();
					parameters.refreshVect();
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
		
		/*
		 * Simulation parameters
		 */
		
		new MyLabel("Simulation parameters",gblConfigInterface,container);
		simulationTimeLine = new SpinnerLine(
				"Simulation time :",
				(int)parameters.simulationTime,
				0,
				10000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		roomBoundsXLine = new SpinnerLine(
				"Room bounds X :",
				(int)parameters.roomBoundsX,
				0,
				30000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		roomBoundsYLine = new SpinnerLine(
				"Room bounds Y :",
				(int)parameters.roomBoundsY,
				0,
				30000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		roomBoundsZLine = new SpinnerLine(
				"Room bounds Z :",
				(int)parameters.roomBoundsZ,
				0,
				30000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		/*
		 * Mission parameters
		 */
		new MyLabel("Mission parameters",gblConfigInterface,container);

		String objectiveType = new String();
		switch(parameters.objectiveType)
		{
			case 1 : objectiveType="point search";break;
			case 2 : objectiveType="maximum search";break;
			case 3 : objectiveType="boids";break;
		}
		
		JLabel configInterObjTypeLabel = new JLabel("Objective Type :");
		//GridBagConstraints configCons = new GridBagConstraints();
		configCons.anchor = GridBagConstraints.WEST;
		configCons.insets = new Insets(10,2,2,10);
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(configInterObjTypeLabel, configCons);
		container.add(configInterObjTypeLabel);
		

		
		String[] typeObjective = { "point search", "maximum search", "boids"};			
			
		
		typeObjectiveList = new JComboBox<String>(new DefaultComboBoxModel<String>(typeObjective));
	        
		Dimension d = typeObjectiveList.getPreferredSize();
        d.width = 140;
        typeObjectiveList.setPreferredSize(d);

		typeObjectiveList.setSelectedIndex(parameters.objectiveType-1);
		typeObjectiveList.setEditable(false);
		typeObjectiveList.addActionListener(this);
		typeObjectiveList.setActionCommand("Objectivetype");	
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(typeObjectiveList, configCons);
		container.add(typeObjectiveList);
				
		JLabel configInterObjTypeDefaultValueLabel = new JLabel("Default : "+objectiveType);
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		gblConfigInterface.setConstraints(configInterObjTypeDefaultValueLabel, configCons);
		container.add(configInterObjTypeDefaultValueLabel);
		


		String resType = new String();
		switch(parameters.resolutionType)
		{
			case 1 : resType = "position minimum";break;
			case 2 : resType = "pso";break;
		}
		
		JLabel configInterResTypeLabel = new JLabel("Resolution Type :");
		//GridBagConstraints configCons = new GridBagConstraints();
		configCons.anchor = GridBagConstraints.WEST;
		configCons.insets = new Insets(10,2,2,10);
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(configInterResTypeLabel, configCons);
		container.add(configInterResTypeLabel);
		

		
		String[] resObjective = { "position minimum", "pso"};			
			
		
		resObjectiveList = new JComboBox<String>(new DefaultComboBoxModel<String>(resObjective));
	        
		Dimension d1 = resObjectiveList.getPreferredSize();
        d1.width = 140;
        resObjectiveList.setPreferredSize(d);

        resObjectiveList.setSelectedIndex(parameters.resolutionType-1);
        resObjectiveList.setEditable(false);
        resObjectiveList.addActionListener(this);
        resObjectiveList.setActionCommand("Resolutiontype");	
		configCons.gridwidth = 1;
		gblConfigInterface.setConstraints(resObjectiveList, configCons);
		container.add(resObjectiveList);
				
		JLabel configInterResTypeDefaultValueLabel = new JLabel("Default : "+resType);
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		gblConfigInterface.setConstraints(configInterResTypeDefaultValueLabel, configCons);
		container.add(configInterResTypeDefaultValueLabel);	
		
		
		
		objectivePositionXLine = new SpinnerLine(
				"Objective position X :",
				parameters.objectivePositionX,
				0,
				30000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		objectivePositionYLine = new SpinnerLine(
				"Objective position Y :",
				parameters.objectivePositionY,
				0,
				30000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		objectivePositionZLine = new SpinnerLine(
				"Objective position Z :",
				parameters.objectivePositionZ,
				0,
				30000,
				1,
				gblConfigInterface, 
				container,
				changeListener
				);
		
		configCons.gridwidth = GridBagConstraints.REMAINDER;
		JLabel filling2 = new JLabel(" ");
		gblConfigInterface.setConstraints(filling2, configCons);
		container.add(filling2);
		
		JScrollPane scrollpane = new JScrollPane(container, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollpane);
		
		
		//parameters of the window
	    GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();       
		//get maximum window bounds
		Rectangle maximumWindowBounds=graphicsEnvironment.getMaximumWindowBounds();
		pack();
		setSize((int)(maximumWindowBounds.getWidth()*0.45),(int)(maximumWindowBounds.getHeight()*0.98));
		setVisible(true);
	}
	
	/**
	 * Check if the user want to launch the simulation.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Launch Simulation"))
		{
			configurationOK=true;
			typeObjectiveList.setEnabled(false);

		}
		else if ((e.getActionCommand().equals("Objectivetype"))&& !configurationOK)
		{
			param.objectiveType = (typeObjectiveList.getSelectedIndex()+1);
			String currentObjectiveType = new String();
			currentObjectiveType = typeObjectiveList.getSelectedItem().toString();
			System.out.println(currentObjectiveType);
			if (currentObjectiveType=="boids")
			{
				resObjectiveList.setEnabled(false);
			}
			else
			{
				resObjectiveList.setEnabled(true);
			}
			

		
		}
		else if ((e.getActionCommand().equals("Resolutiontype"))&& !configurationOK)
		{		
			param.resolutionType=(resObjectiveList.getSelectedIndex()+1);

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
	public void setEnabled(boolean bool)
	{

		 initialEnergyLine.slider.setEnabled(bool);
		 initialEnergyLine.spinner.setEnabled(bool);
		 
		 nbOfCameraDroneLine.slider.setEnabled(bool);
		 nbOfCameraDroneLine.spinner.setEnabled(bool);
		 nbOfCommunicatorDroneLine.slider.setEnabled(bool);
		 nbOfCommunicatorDroneLine.spinner.setEnabled(bool);
		 nbOfMeasurementDroneLine.slider.setEnabled(bool);
		 nbOfMeasurementDroneLine.spinner.setEnabled(bool);
		 nbOfMicrophoneDroneLine.slider.setEnabled(bool);
		 nbOfMicrophoneDroneLine.spinner.setEnabled(bool);
		 
		 simulationTimeLine.slider.setEnabled(bool);
		 simulationTimeLine.spinner.setEnabled(bool);
		 roomBoundsXLine.slider.setEnabled(bool);
		 roomBoundsXLine.spinner.setEnabled(bool);
		 roomBoundsYLine.slider.setEnabled(bool);
		 roomBoundsYLine.spinner.setEnabled(bool);
		 roomBoundsZLine.slider.setEnabled(bool);
		 roomBoundsZLine.spinner.setEnabled(bool);
		 
		 objectivePositionXLine.spinner.setEnabled(bool);
		 objectivePositionXLine.slider.setEnabled(bool);
		 objectivePositionYLine.spinner.setEnabled(bool);
		 objectivePositionYLine.slider.setEnabled(bool);
		 objectivePositionZLine.spinner.setEnabled(bool);
		 objectivePositionZLine.slider.setEnabled(bool);
		 typeObjectiveList.setEnabled(bool);
		 resObjectiveList.setEnabled(bool);
		
		String currentType = new String();
		currentType=resObjectiveList.getSelectedItem().toString();
		if (currentType=="pso")
		{
			orientationDistanceLine.slider.setEnabled(bool);
			orientationDistanceLine.spinner.setEnabled(bool);
			orientationCoeffLine.spinner.setEnabled(bool);
			orientationCoeffLine.slider.setEnabled(bool);
			attractionDistanceLine.slider.setEnabled(bool);
			attractionDistanceLine.spinner.setEnabled(bool);
			attractionCoeffLine.spinner.setEnabled(bool);
			attractionCoeffLine.slider.setEnabled(bool);
						
		}


	}
}
