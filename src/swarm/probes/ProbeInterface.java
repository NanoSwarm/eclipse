package swarm.probes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import fr.lgi2a.similar.microkernel.IProbe;
import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import fr.lgi2a.similar.microkernel.agents.ILocalStateOfAgent;
import fr.lgi2a.similar.microkernel.dynamicstate.IPublicLocalDynamicState;
import swarm.model.SwarmParameters;
import swarm.model.agents.SwarmAgentCategoriesList;
import swarm.model.agents.Drone.room.AgtDronePLSInRoom;
import swarm.model.agents.cameraDrone.room.AgtCameraDronePLSInRoom;
import swarm.model.agents.communicatorDrone.room.AgtCommunicatorDronePLSInRoom;
import swarm.model.agents.measurementDrone.room.AgtMeasurementDronePLSInRoom;
import swarm.model.agents.microphoneDrone.room.AgtMicrophoneDronePLSInRoom;
import swarm.model.level.SwarmLevelList;

@SuppressWarnings("serial")
/**
 * 
 * @author Alexandre JIN
 * The probe displaying the chart of the energy consumption and the creation of the files
 *
 */
public class ProbeInterface 	extends Frame 
								implements 	ActionListener,
											IProbe
{
	/**
	 * The list of the energy of each drone
	 */
	private ArrayList<Double>[] listDroneEnergy,listCameraDroneEnergy,listCommunicatorDroneEnergy,listMeasurementDroneEnergy,listMicrophoneDroneEnergy;
	private ArrayList<Double> listTotalEnergy=new ArrayList<Double>();
	/**
	 * The list of the drones
	 */
	private String[]listDrone,listCameraDrone,listMicrophoneDrone,listCommunicatorDrone,listMeasurementDrone;
	/**
	 * The comboboxes for the selection
	 */
	private JComboBox<String> typeDroneList,numDroneList;
	/**
	 * the parameters of the simulation
	 */
	private SwarmParameters parameters;
	/**
	 * The chart displaying the level of the energy
	 */
	private JFreeChart chart;
	/**
	 * the plot of the chart
	 */
	private XYPlot plot;
	/**
	 * the panel of the chart
	 */
	private ChartPanel chartPanel;
	/**
	 * The RadioButtons for the selection of which drone to display
	 */
	JRadioButton totalDrone,totalCamera,total,allMicrophoneDrone,allMeasurementDrone,allCommunicatorDrone,totalMicrophone,totalCommunicator,totalMeasurement,allDrone,allCameraDrone;
	/**
	 * The clear button
	 */
	JButton reset;
	/**
	 * The number of chart being displayed
	 */
	private int numberOfGraph;
	/**
	 * The position of each different chart
	 */
	private int positionTotalCamera,positionTotal,positionAllMeasurementDrone,positionAllMicrophoneDrone,positionAllCommunicatorDrone,positionAllCameraDrone,positionAllDrone,positionTotalCommunicator,positionTotalMeasurement,positionTotalDrone,positionTotalMicrophone;
	/**
	 * A condition to block the selection of the number of drone before selecting the type of the drone
	 */
	private boolean bloc = false;
	/**
	 * the constructor of the interface
	 * @param parameters of the simulation
	 */
	public ProbeInterface(SwarmParameters param)
	{
		initParameters(param);
		initGUI();
	}
	/**
	 * Set up the initial parameters
	 * @param param of the simulation
	 */
	@SuppressWarnings("unchecked")
	public void initParameters(SwarmParameters param)
	{
		this.parameters = param;
		this.numberOfGraph = 0;
		this.listDroneEnergy = new ArrayList[parameters.nbOfDroneAgents+1];
		this.listCameraDroneEnergy = new ArrayList[parameters.nbOfCameraDroneAgents+1];
		this.listMicrophoneDroneEnergy = new ArrayList[parameters.nbOfMicrophoneDroneAgents+1];
		this.listCommunicatorDroneEnergy = new ArrayList[parameters.nbOfCommunicatorDroneAgents+1];
		this.listMeasurementDroneEnergy = new ArrayList[parameters.nbOfMeasurementDroneAgents+1];
		//Initialize arrays
		for (int k=0;k<parameters.nbOfDroneAgents+1;k++)listDroneEnergy[k] = new ArrayList<Double>();
		for (int k=0;k<parameters.nbOfCameraDroneAgents+1;k++)listCameraDroneEnergy[k] = new ArrayList<Double>();
		for (int k=0;k<parameters.nbOfCommunicatorDroneAgents+1;k++)listCommunicatorDroneEnergy[k] = new ArrayList<Double>();
		for (int k=0;k<parameters.nbOfMeasurementDroneAgents+1;k++)listMeasurementDroneEnergy[k] = new ArrayList<Double>();
		for (int k=0;k<parameters.nbOfMicrophoneDroneAgents+1;k++)listMicrophoneDroneEnergy[k] = new ArrayList<Double>();
		this.listCameraDrone = new String[parameters.nbOfCameraDroneAgents];
		this.listDrone = new String[parameters.nbOfDroneAgents];
		this.listMicrophoneDrone = new String[parameters.nbOfMicrophoneDroneAgents];
		this.listCommunicatorDrone = new String[parameters.nbOfCommunicatorDroneAgents];
		this.listMeasurementDrone = new String[parameters.nbOfMeasurementDroneAgents];
	}
	/**
	 * Set the graphical user interface
	 */
	public void initGUI()
	{
		setLayout(new BorderLayout());
		// Container in the north part for the Jcombobox
		Container comboBoxContent = new Container();
		BoxLayout comboBoxLayout = new BoxLayout(comboBoxContent, BoxLayout.Y_AXIS);
		comboBoxContent.setLayout(comboBoxLayout);
		add(BorderLayout.NORTH,comboBoxContent);
		String[] typeDrone = {
				SwarmAgentCategoriesList.DRONE.toString(),
				SwarmAgentCategoriesList.CAMERADRONE.toString(),
				SwarmAgentCategoriesList.COMMUNICATORDRONE.toString(),
				SwarmAgentCategoriesList.MEASUREMENTDRONE.toString(),
				SwarmAgentCategoriesList.MICROPHONEDRONE.toString()
		};
		typeDroneList = new JComboBox<String>(new DefaultComboBoxModel<String>(typeDrone));
		typeDroneList.setSelectedIndex(-1);
		typeDroneList.setEditable(false);
		typeDroneList.addActionListener(this);
		typeDroneList.setActionCommand("type");		
		comboBoxContent.add(typeDroneList);
		//Set up the list of the number of the drone 
		String[] numDrone = {""};		
		numDroneList = new JComboBox<String>(new DefaultComboBoxModel<String>(numDrone));
		numDroneList.setEnabled(false);
		numDroneList.setEditable(false);
		numDroneList.addActionListener(this);
		numDroneList.setActionCommand("num");
		comboBoxContent.add(numDroneList);
		//Chart
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.add(0.0);
		XYDataset dataset = createDataset(temp);
		chart = createChart(dataset); 
        chartPanel = new ChartPanel(chart);
    	plot.setDataset(0,null);
		plot.setRenderer(0,null);  
		// chartPanel.setPreferredSize(new Dimension(500, 270));
        add(BorderLayout.CENTER,chartPanel);
        // Radio Buttons
    	Container radioButtonContent=new Container();
		BoxLayout radioButtonLayout=new BoxLayout(radioButtonContent, BoxLayout.Y_AXIS);
		radioButtonContent.setLayout(radioButtonLayout);
        total = new JRadioButton("All Totals");
        total.setActionCommand("total");
        total.addActionListener(this);  
        totalDrone = new JRadioButton("Total Drone");
        totalDrone.setActionCommand("total drone");
        totalDrone.addActionListener(this);
        totalCamera = new JRadioButton("Total Camera Drone");
        totalCamera.setActionCommand("total camera");
        totalCamera.addActionListener(this);
        totalMicrophone = new JRadioButton("Total Microphone Drone");
        totalMicrophone.setActionCommand("total microphone");
        totalMicrophone.addActionListener(this);
        totalCommunicator = new JRadioButton("Total Communicator Drone");
        totalCommunicator.setActionCommand("total communicator");
        totalCommunicator.addActionListener(this);
        totalMeasurement = new JRadioButton("Total Measurement Drone");
        totalMeasurement.setActionCommand("total measurement");
        totalMeasurement.addActionListener(this);    
        allDrone = new JRadioButton("All Drones");
        allDrone.setActionCommand("all drone");
        allDrone.addActionListener(this);
        allCameraDrone = new JRadioButton("All CameraDrones");
        allCameraDrone.setActionCommand("all cameradrone");
        allCameraDrone.addActionListener(this);
        allCommunicatorDrone = new JRadioButton("All CommunicatorDrones");
        allCommunicatorDrone.setActionCommand("all communicatordrone");
        allCommunicatorDrone.addActionListener(this);
        allMicrophoneDrone = new JRadioButton("All MicrophoneDrones");
        allMicrophoneDrone.setActionCommand("all microphonedrone");
        allMicrophoneDrone.addActionListener(this);
        allMeasurementDrone = new JRadioButton("All MeasurementDrones");
        allMeasurementDrone.setActionCommand("all measurementdrone");
        allMeasurementDrone.addActionListener(this);
		reset = new JButton("Clear");
		reset.addActionListener(this);
		reset.setActionCommand("clear");	
		radioButtonContent.add(total);
		radioButtonContent.add(totalDrone);
		radioButtonContent.add(totalCamera);
		radioButtonContent.add(totalMicrophone);
		radioButtonContent.add(totalCommunicator);
		radioButtonContent.add(totalMeasurement);
		radioButtonContent.add(allDrone);
		radioButtonContent.add(allCameraDrone);
		radioButtonContent.add(allCommunicatorDrone);
		radioButtonContent.add(allMicrophoneDrone);
		radioButtonContent.add(allMeasurementDrone);
		radioButtonContent.add(reset);
        add(BorderLayout.EAST,radioButtonContent);
        addWindowListener(
				new WindowAdapter()
				{ 
					public void windowClosing(WindowEvent e)
					{
						setVisible(false);
						dispose(); //Destroy the JFrame object
					}
				});
	    //parameters of the window
	    GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();       
		//get maximum window bounds
		Rectangle maximumWindowBounds=graphicsEnvironment.getMaximumWindowBounds();
		setTitle("Energy consumption over time");
		setSize((int)maximumWindowBounds.getWidth()/2,(int)maximumWindowBounds.getHeight());
		setLocation(0,0);
		pack();
		setVisible(false);
	}
	/**Creates dataset
	 * 
	 * @param a array with the list of the value at each timestamp
	 * @return the dataset corresponding to the values to make the chart
	 */
	private XYDataset createDataset(ArrayList<Double> s) 
	{   			
		final XYSeries series = new XYSeries("");
	    for (int k=0;k<s.size();k++)series.add(k,s.get(k));
	    final XYSeriesCollection dataset = new XYSeriesCollection();
	    dataset.addSeries(series);              
	    return dataset;	                       
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final XYDataset dataset) 
    {
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Energy consumption",     // the title
            "Time Interval",          // x axis label
            "Energy",                 // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            false,                    // include legend
            true,                     // tooltips
            false                     // urls
        );
        // Customisation
        chart.setBackgroundPaint(Color.white);
        plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);     
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        plot.getRenderer().setSeriesPaint(0, Color.RED);   
        return chart;
    }
    
    //Actionlistener methods implemented
	public void actionPerformed(ActionEvent e)
	{		
		String com=e.getActionCommand();
		if(com.equals("type"))typeSelection();
		else if((com.equals("num"))&&bloc)numSelection();
		else if(com.equals("clear"))clearGraph();
		else if (com.equals("total camera"))positionTotalCamera = displayTotalGraph(totalCamera,positionTotalCamera,parameters.cameraDroneColor.get(),listCameraDroneEnergy);
		else if (com.equals("total microphone"))positionTotalMicrophone = displayTotalGraph(totalMicrophone,positionTotalMicrophone,parameters.microphoneDroneColor.get(),listMicrophoneDroneEnergy);
		else if (com.equals("total drone"))positionTotalDrone = displayTotalGraph(totalDrone,positionTotalDrone,parameters.droneColor.get(),listDroneEnergy);
		else if (com.equals("total communicator"))positionTotalCommunicator = displayTotalGraph(totalCommunicator,positionTotalCommunicator,parameters.communicatorDroneColor.get(),listCommunicatorDroneEnergy);
		else if (com.equals("total measurement"))positionTotalMeasurement = displayTotalGraph(totalMeasurement,positionTotalMeasurement,parameters.measurementDroneColor.get(),listMeasurementDroneEnergy);
		else if (com.equals("all drone"))positionAllDrone = displayAllGraph(allDrone,positionAllDrone,listDroneEnergy);
		else if (com.equals("all cameradrone"))positionAllCameraDrone = displayAllGraph(allCameraDrone,positionAllCameraDrone,listCameraDroneEnergy);
		else if (com.equals("all communicatordrone"))positionAllCommunicatorDrone = displayAllGraph(allCommunicatorDrone,positionAllCommunicatorDrone,listCommunicatorDroneEnergy);		
		else if (com.equals("all microphonedrone"))positionAllMicrophoneDrone = displayAllGraph(allMicrophoneDrone,positionAllMicrophoneDrone,listMicrophoneDroneEnergy);	
		else if (com.equals("all measurementdrone"))positionAllMeasurementDrone = displayAllGraph(allMeasurementDrone,positionAllMeasurementDrone,listMeasurementDroneEnergy);			
		else if (com.equals("total"))
		{
			if (total.isSelected())
			{		
				positionTotal = numberOfGraph;
				plot.setDataset(positionTotal,createDataset(listTotalEnergy)) ;             
				plot.setRenderer(positionTotal, new StandardXYItemRenderer());
				numberOfGraph++;	          
			}			
			else
			{
				if (numberOfGraph>0)
				{
					plot.setDataset(positionTotal,null);
					plot.setRenderer(positionTotal, null);
					numberOfGraph--;
				}
			}
		}
	}
/**
 * Selection of the type
 */
	public void typeSelection()
	{
		//type selected
		String currentType=new String();
		// index of the type selected
		int indexType;
		indexType = typeDroneList.getSelectedIndex();
		currentType = typeDroneList.getSelectedItem().toString();		
		if (currentType!="")
		{
			// unblocking the selection of the drone
			bloc = false;
			numDroneList.removeAllItems();	
			switch (indexType)
			{		
				case 0:
					for(int k = 0;k<listDrone.length;k++)numDroneList.addItem(listDrone[k].substring(47,listDrone[k].length()));					
					break;
				case 1:
					for(int k = 0;k<listCameraDrone.length;k++)numDroneList.addItem(listCameraDrone[k].substring(59,listCameraDrone[k].length()));					
					break;
				case 2:
					for(int k = 0;k<listCommunicatorDrone.length;k++)numDroneList.addItem(listCommunicatorDrone[k].substring(71,listCommunicatorDrone[k].length()));
					break;
				case 3:
					for(int k = 0;k<listMeasurementDrone.length;k++)numDroneList.addItem(listMeasurementDrone[k].substring(69,listMeasurementDrone[k].length()));
					break;
				case 4 :
					for(int k = 0;k<listMicrophoneDrone.length;k++)numDroneList.addItem(listMicrophoneDrone[k].substring(67,listMicrophoneDrone[k].length()));
					break;
				default:break;
			}
		}
		numDroneList.setEnabled(true);
		numDroneList.setSelectedIndex(-1);
		bloc = true;
	}
/**
 * Selection of the number of the drone
 */
	public void numSelection()
	{
		// index of the type selected previously
		int indexType;
		indexType = typeDroneList.getSelectedIndex();
		// index of the number of the drone selected
		int indexNum;
		indexNum = numDroneList.getSelectedIndex();	
		XYDataset dataset1=createDataset(listCameraDroneEnergy[0]);
		if ((indexNum >= 0))
		{
			switch (indexType)
			{
				case 0:
					dataset1 = createDataset(listDroneEnergy[indexNum]);
					break;
				case 1:
					dataset1 = createDataset(listCameraDroneEnergy[indexNum]);
					break;
				case 2:
					dataset1 = createDataset(listCommunicatorDroneEnergy[indexNum]);
					break;
				case 3:
					dataset1 = createDataset(listMeasurementDroneEnergy[indexNum]);
					break;
				case 4 :
					dataset1 = createDataset(listMicrophoneDroneEnergy[indexNum]);
					break;
				default:
					break;
			}	
	      plot.setDataset(numberOfGraph,dataset1) ;             
	      plot.setRenderer(numberOfGraph, new StandardXYItemRenderer());
	      numberOfGraph++;
		}

	}	

/**
 * function to add the total of a type of drone
 * @param button selected
 * @param pos position of the chart
 * @param color color of the chart
 * @param listEnergy datas of the energy level
 * @return
 */
	public int displayTotalGraph(JRadioButton button, int pos,Color color,ArrayList[] listEnergy)
	{
		if (button.isSelected())
		{			
		  pos = numberOfGraph;
		  this.plot.setDataset(pos,createDataset(listEnergy[listEnergy.length-1])) ;             
          this.plot.setRenderer(pos, new StandardXYItemRenderer());
          numberOfGraph++;       
		}		
		else
		{
			if (numberOfGraph>0)
			{
				this.plot.setDataset(pos,null);
				this.plot.setRenderer(pos, null);
				numberOfGraph--;
			}
		}
		return pos;
	}
	/**
	 * function to add the total of a type of drone
	 * @param button
	 * @param pos
	 * @param listEnergy
	 * @return
	 */
	public int displayAllGraph(JRadioButton button, int pos, ArrayList[] listEnergy)
	{
		if (button.isSelected())
		{			 
			pos = numberOfGraph;
			for (int f = 0;f<listEnergy.length-1;f++)
			{		
				this.plot.setDataset(pos+f,createDataset(listEnergy[f])) ;             
				this.plot.setRenderer(pos+f, new StandardXYItemRenderer());         
			}
			numberOfGraph = pos+listEnergy.length-2;       
		}
		else
		{
			if (numberOfGraph>0)
			{
				for (int f = 0;f<listEnergy.length-1;f++)
				{					
					plot.setDataset(pos+f,null) ;             
			        plot.setRenderer(pos+f, null);		         
				}
			}
		}
		return pos;
	}
	/**
	 * clear all the graph
	 */
	public void clearGraph()
	{
		for (int k = 0;k<plot.getDatasetCount();k++)
		{
			plot.setDataset(k,null);
			plot.setRenderer(k,null);
		}
		numberOfGraph = 0;
		totalDrone.setSelected(false);
		totalCamera.setSelected(false);
		totalMicrophone.setSelected(false);
		totalMeasurement.setSelected(false);
		totalCommunicator.setSelected(false);
		allDrone.setSelected(false);
		allCameraDrone.setSelected(false);
		allMicrophoneDrone.setSelected(false);
		allMeasurementDrone.setSelected(false);
		allCommunicatorDrone.setSelected(false);
		total.setSelected(false);
	}
	/**
	 * {@inheritDoc}
	 */
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
		this.initializedatas(initialTimestamp,simulationEngine);
	}
	/**
	 * Initialize the datas
	 * @param timestamp
	 * @param simulationEngine
	 */
	private void initializedatas(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	){
		int indexCamera = 0;
		int indexMeasurement = 0;
		int indexMicrophone = 0;
		int indexCommunicator = 0;
		int indexDrone = 0;
		IPublicLocalDynamicState chamberState = simulationEngine.getSimulationDynamicStates().get( 
				SwarmLevelList.ROOM
		);						
			for( ILocalStateOfAgent agtState : chamberState.getPublicLocalStateOfAgents() )
			{
				if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.CAMERADRONE ) )
				{
					AgtCameraDronePLSInRoom castedAgtState = (AgtCameraDronePLSInRoom) agtState;
					listCameraDrone[indexCamera]=castedAgtState.toString();
					indexCamera++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.DRONE ) )
				{
					AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;
					listDrone[indexDrone]=castedAgtState.toString();																	
					indexDrone++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) )
				{
					AgtCommunicatorDronePLSInRoom castedAgtState = (AgtCommunicatorDronePLSInRoom) agtState;
					listCommunicatorDrone[indexCommunicator]=castedAgtState.toString();																			
					indexCommunicator++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE ) )
				{
					AgtMeasurementDronePLSInRoom castedAgtState = (AgtMeasurementDronePLSInRoom) agtState;
					listMeasurementDrone[indexMeasurement]=castedAgtState.toString();																		
					indexMeasurement++;
				}							
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) )
				{
					AgtMicrophoneDronePLSInRoom castedAgtState = (AgtMicrophoneDronePLSInRoom) agtState;
					listMicrophoneDrone[indexMicrophone]=castedAgtState.toString();	
					indexMicrophone++;
				}			
			}

		}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtPartialConsistentTime(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	) {
		this.updateenergylist( timestamp, simulationEngine );
	}
	/**
	 * update the list with the energy of each drone a each time
	 * @param timestamp
	 * @param simulationEngine
	 */
	private void updateenergylist(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	){
		int indexCamera = 0;
		int indexMeasurement = 0;
		int indexMicrophone = 0;
		int indexCommunicator = 0;
		int indexDrone = 0;
		IPublicLocalDynamicState chamberState = simulationEngine.getSimulationDynamicStates().get( 
				SwarmLevelList.ROOM
		);		
		for( ILocalStateOfAgent agtState : chamberState.getPublicLocalStateOfAgents() ){
			if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.CAMERADRONE ) )
			{
				AgtCameraDronePLSInRoom castedAgtState = (AgtCameraDronePLSInRoom) agtState;
				listCameraDroneEnergy[indexCamera].add(castedAgtState.getEnergy());	
				indexCamera++;
			}
			else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.DRONE ) )
			{
				AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;
				listDroneEnergy[indexDrone].add(castedAgtState.getEnergy());																			
				indexDrone++;
			}
			else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) )
			{
				AgtCommunicatorDronePLSInRoom castedAgtState = (AgtCommunicatorDronePLSInRoom) agtState;
				listCommunicatorDroneEnergy[indexCommunicator].add(castedAgtState.getEnergy());																			
				indexCommunicator++;
			}
			else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE ) )
			{
				AgtMeasurementDronePLSInRoom castedAgtState = (AgtMeasurementDronePLSInRoom) agtState;
				listMeasurementDroneEnergy[indexMeasurement].add(castedAgtState.getEnergy());																			
				indexMeasurement++;
			}							
			else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) )
			{
				AgtMicrophoneDronePLSInRoom castedAgtState = (AgtMicrophoneDronePLSInRoom) agtState;
				listMicrophoneDroneEnergy[indexMicrophone].add(castedAgtState.getEnergy());	
				indexMicrophone++;
			}				
		}
	}
					
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtFinalTime(
			SimulationTimeStamp finalTimestamp,
			ISimulationEngine simulationEngine
	) 
	{
	
		setVisible(true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endObservation() {
		calculateTotals();
		createfiles();
	}
	/**
	 * calculates the the totals of each type of drone.
	 */
	public void calculateTotals()
	{
		double somme = 0;
		for (int i=0;i<listDroneEnergy[0].size();i++)
		{
			somme=0;	
			for (int k=0;k<listDrone.length-2;k++)somme+=(double) listDroneEnergy[k].get(i);
			listDroneEnergy[listDroneEnergy.length-1].add(somme);
		}
		for (int i=0;i<listCameraDroneEnergy[0].size();i++)
		{
			somme=0;	
			for (int k=0;k<listCameraDrone.length-2;k++)somme+=(double) listCameraDroneEnergy[k].get(i);
			listCameraDroneEnergy[listCameraDroneEnergy.length-1].add(somme);
		}
		for (int i=0;i<listCommunicatorDroneEnergy[0].size();i++)
		{
			somme=0;	
			for (int k=0;k<listCommunicatorDrone.length-2;k++)somme+=(double) listCommunicatorDroneEnergy[k].get(i);
			listCommunicatorDroneEnergy[listCommunicatorDroneEnergy.length-1].add(somme);	
		}
		for (int i=0;i<listMicrophoneDroneEnergy[0].size();i++)
		{
			somme=0;	
			for (int k=0;k<listMicrophoneDrone.length-2;k++)somme+=(double) listMicrophoneDroneEnergy[k].get(i);	
			listMicrophoneDroneEnergy[listMicrophoneDroneEnergy.length-1].add(somme);
		}
		for (int i=0;i<listMeasurementDroneEnergy[0].size();i++)
		{
			somme=0;	
			for (int k=0;k<listMeasurementDrone.length-2;k++)somme+=(double) listMeasurementDroneEnergy[k].get(i);
			listMeasurementDroneEnergy[listMeasurementDroneEnergy.length-1].add(somme);
		}
		for (int i=0;i<listMeasurementDroneEnergy[0].size();i++)
		{
			listTotalEnergy.add((Double)listDroneEnergy[listDroneEnergy.length-1].get(i)
					+(Double)listCameraDroneEnergy[listCameraDroneEnergy.length-1].get(i)
					+(Double)listCommunicatorDroneEnergy[listCommunicatorDroneEnergy.length-1].get(i)
					+(Double)listMicrophoneDroneEnergy[listMicrophoneDroneEnergy.length-1].get(i)
					+(Double)listMeasurementDroneEnergy[listMeasurementDroneEnergy.length-1].get(i)
					);
		}
	}
	/**
	 * create the files of the results
	 */
	public void createfiles()
	{	
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
		Calendar cal = Calendar.getInstance();
		String res=new String("Results "+dateFormat.format(cal.getTime()));
		File measurementDroneFolder=new File("Results\\"+res+"\\MeasurementDrone"); 
		File microphoneDroneFolder=new File("Results\\"+res+"\\MicrophoneDrone"); 
		File cameraDroneFolder=new File("Results\\"+res+"\\CameraDrone"); 
		File communicatorDroneFolder=new File("Results\\"+res+"\\CommunicatorDrone"); 	
		File droneFolder=new File("Results\\"+res+"\\Drone"); 
		
		deleteFolder(measurementDroneFolder);
		deleteFolder(microphoneDroneFolder);
		deleteFolder(cameraDroneFolder);
		deleteFolder(communicatorDroneFolder);
		deleteFolder(droneFolder);
		// Creation of the folders 
		measurementDroneFolder.mkdirs();
		microphoneDroneFolder.mkdirs();
		cameraDroneFolder.mkdirs();
		communicatorDroneFolder.mkdirs();
		droneFolder.mkdirs();
		//Creation of the files
		String name=new String();
		for (int k=0;k<listDrone.length;k++)
		{
			 name=listDrone[k].substring(47,listDrone[k].length());
			File f = new File ("Results\\"+res+"\\Drone\\"+name+".txt");
			try
			{
			    FileWriter fw = new FileWriter (f);
			    fw.write ("Energy List of"+name+"\r\n");
			    int index=0;
			    for (double energy:listDroneEnergy[k])
			    {
				   	fw.write(index+"\t"+energy+"\r\n");
				   	index++;
			   	}
			    fw.close();
			}
			catch (IOException exception)
			{
				System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
			}
		}
		for (int k=0;k<listMeasurementDrone.length;k++)
		{
			name=listMeasurementDrone[k].substring(69,listMeasurementDrone[k].length());
			File f = new File ("Results\\"+res+"\\MeasurementDrone\\"+name+".txt");
			try
			{
			    FileWriter fw = new FileWriter (f);
			    fw.write ("Energy List of"+name+"\r\n");
			    int index=0;
			    for (double energy:listMeasurementDroneEnergy[k])
			    {
				   	fw.write(index+"\t"+energy+"\r\n");
				   	index++;
			   	}
			    fw.close();
			}
			catch (IOException exception)
			{
				System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
			}
		}
		for (int k=0;k<listMicrophoneDrone.length;k++)
		{
			name=listMicrophoneDrone[k].substring(67,listMicrophoneDrone[k].length());
			File f = new File ("Results\\"+res+"\\MicrophoneDrone\\"+name+".txt");
			try
			{
			    FileWriter fw = new FileWriter (f);
			    fw.write ("Energy List of"+name+"\r\n");
			    int index=0;
			    for (double energy:listMicrophoneDroneEnergy[k])
			    {
				   	fw.write(index+"\t"+energy+"\r\n");
				   	index++;
			   	}
			    fw.close();
			}
			catch (IOException exception)
			{
				System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
			}
		}
		for (int k=0;k<listCameraDrone.length;k++)
		{
			name=listCameraDrone[k].substring(59,listCameraDrone[k].length());
			File f = new File ("Results\\"+res+"\\CameraDrone\\"+name+".txt");
			try
			{
			    FileWriter fw = new FileWriter (f);
			    fw.write ("Energy List of"+name+"\r\n");
			    int index=0;
			    for (double energy:listCameraDroneEnergy[k])
			    {
				   	fw.write(index+"\t"+energy+"\r\n");
				   	index++;
			   	}
			    fw.close();
			}
			catch (IOException exception)
			{
				System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
			}
		}
		for (int k=0;k<listCommunicatorDrone.length;k++)
		{
			name=listCommunicatorDrone[k].substring(71,listCommunicatorDrone[k].length());
			File f = new File ("Results\\"+res+"\\CommunicatorDrone\\"+name+".txt");
			try
			{
			    FileWriter fw = new FileWriter (f);
			    fw.write ("Energy List of"+name+"\r\n");
			    int index=0;
			    for (double energy:listCommunicatorDroneEnergy[k])
			    {
				   	fw.write(index+"\t"+energy+"\r\n");
				   	index++;
			   	}
			    fw.close();
			}
			catch (IOException exception)
			{
				System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
			}
		}
		File f = new File ("Results\\"+res+"\\Propertiesused.txt");
		try
		{
		    FileWriter fw = new FileWriter (f);
		    Properties p = parameters.properties;
		    Enumeration keys = p.keys();
		    while (keys.hasMoreElements()) {
		      String key = (String)keys.nextElement();
		      String value = (String)p.get(key);
		      fw.write (key+" = "+ value+"\r\n");
		    }
		    fw.close();
		}
		catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
	/**
	 * delete a folder 
	 * @param folder is the file of the folder to delete
	 */
	public void deleteFolder(File folder)
	{
		if (folder.isDirectory())
		{
			File[] list=folder.listFiles();
			for (int k=0;k<list.length;k++)
			{
				list[k].delete();
			}
		}
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

}

