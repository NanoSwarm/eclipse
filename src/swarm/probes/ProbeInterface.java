package swarm.probes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

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
public class ProbeInterface 	extends Frame 
								implements 	WindowListener,
											ActionListener,
											IProbe
{
	public ArrayList[] listDroneEnergy;
	public ArrayList[] listCameraDroneEnergy;
	public ArrayList[] listCommunicatorDroneEnergy;
	public ArrayList[] listMeasurementDroneEnergy;
	public ArrayList[] listMicrophoneDroneEnergy;
	public ArrayList<Double> listTotalEnergy=new ArrayList<Double>();
	public String[]listDrone;
	public String[]listCameraDrone;
	public String[]listMicrophoneDrone;
	public String[]listCommunicatorDrone;
	public String[]listMeasurementDrone;
	public JComboBox<String> typeDroneList;
	public JComboBox<String> numDroneList ;
	private SwarmParameters parameters;
	private JFreeChart chart;
	private XYPlot plot;
	private ChartPanel chartPanel;
	JRadioButton totalDrone;
	JRadioButton totalCamera;
	JRadioButton totalMicrophone;
	JRadioButton totalCommunicator;
	JRadioButton totalMeasurement;
	JRadioButton allDrone;
	JRadioButton allCameraDrone;
	JRadioButton allCommunicatorDrone;
	JRadioButton allMicrophoneDrone;
	JRadioButton allMeasurementDrone;
	JRadioButton total;
	JButton reset;
	public int numberOfGraph;
	public int positionTotalCamera;
	public int positionTotalDrone;
	public int positionTotalMicrophone;
	public int positionTotalMeasurement;
	public int positionTotalCommunicator;
	public int positionAllDrone;
	public int positionAllCameraDrone;
	public int positionAllCommunicatorDrone;
	public int positionAllMicrophoneDrone;
	public int positionAllMeasurementDrone;
	public int positionTotal;
	public boolean bloc=false;
	
	
	public ProbeInterface(SwarmParameters param)
	{
		parameters=param;
		numberOfGraph=0;
		listDroneEnergy=new ArrayList[parameters.nbOfDroneAgents+1];
		listCameraDroneEnergy=new ArrayList[parameters.nbOfCameraDroneAgents+1];
		listMicrophoneDroneEnergy=new ArrayList[parameters.nbOfMicrophoneDroneAgents+1];
		listCommunicatorDroneEnergy=new ArrayList[parameters.nbOfCommunicatorDroneAgents+1];
		listMeasurementDroneEnergy=new ArrayList[parameters.nbOfMeasurementDroneAgents+1];
		//Initialize arrays
		for (int k=0;k<parameters.nbOfDroneAgents+1;k++)listDroneEnergy[k]=new ArrayList<Double>();
		for (int k=0;k<parameters.nbOfCameraDroneAgents+1;k++)listCameraDroneEnergy[k]=new ArrayList<Double>();
		for (int k=0;k<parameters.nbOfCommunicatorDroneAgents+1;k++)listCommunicatorDroneEnergy[k]=new ArrayList<Double>();
		for (int k=0;k<parameters.nbOfMeasurementDroneAgents+1;k++)listMeasurementDroneEnergy[k]=new ArrayList<Double>();
		for (int k=0;k<parameters.nbOfMicrophoneDroneAgents+1;k++)listMicrophoneDroneEnergy[k]=new ArrayList<Double>();
		listCameraDrone=new String[parameters.nbOfCameraDroneAgents];
		listDrone=new String[parameters.nbOfDroneAgents];
		listMicrophoneDrone=new String[parameters.nbOfMicrophoneDroneAgents];
		listCommunicatorDrone=new String[parameters.nbOfCommunicatorDroneAgents];
		listMeasurementDrone=new String[parameters.nbOfMeasurementDroneAgents];
		setTitle("Energy consumption over time");
		setMinimumSize(new Dimension(400,400));
		setPreferredSize(new Dimension(800, 400));
		setLocation(0,0);
		setLayout(new BorderLayout());
		// Container in the north part for the Jcombobox
		Container content=new Container();
		BoxLayout ligne1=new BoxLayout(content, BoxLayout.Y_AXIS);
		content.setLayout(ligne1);
		add(BorderLayout.NORTH,content);
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
		content.add(typeDroneList);
		String []numDrone = {""};		
		numDroneList = new JComboBox<String>(new DefaultComboBoxModel<String>(numDrone));
		numDroneList.setEnabled(false);
		numDroneList.setEditable(false);
		numDroneList.addActionListener(this);
		numDroneList.setActionCommand("num");
		content.add(numDroneList);
		//Chart
		ArrayList<Double> temp=new ArrayList<Double>();
		temp.add(0.0);
		XYDataset dataset = createDataset(temp);
		chart = createChart(dataset); 
        chartPanel = new ChartPanel(chart);
    	plot.setDataset(0,null);
		plot.setRenderer(0,null);  
        chartPanel.setPreferredSize(new Dimension(500, 270));
        add(BorderLayout.CENTER,chartPanel);
        // Radio Buttons
    	Container content1=new Container();
		BoxLayout ligne2=new BoxLayout(content1, BoxLayout.Y_AXIS);
		content1.setLayout(ligne2);
        total=new JRadioButton("All Totals");
        total.setActionCommand("total");
        total.addActionListener(this);
        
        totalDrone=new JRadioButton("Total Drone");
        totalDrone.setActionCommand("total drone");
        totalDrone.addActionListener(this);
        totalCamera=new JRadioButton("Total Camera Drone");
        totalCamera.setActionCommand("total camera");
        totalCamera.addActionListener(this);
        totalMicrophone=new JRadioButton("Total Microphone Drone");
        totalMicrophone.setActionCommand("total microphone");
        totalMicrophone.addActionListener(this);
        totalCommunicator=new JRadioButton("Total Communicator Drone");
        totalCommunicator.setActionCommand("total communicator");
        totalCommunicator.addActionListener(this);
        totalMeasurement=new JRadioButton("Total Measurement Drone");
        totalMeasurement.setActionCommand("total measurement");
        totalMeasurement.addActionListener(this);
    
        allDrone=new JRadioButton("All Drones");
        allDrone.setActionCommand("all drone");
        allDrone.addActionListener(this);
        allCameraDrone=new JRadioButton("All CameraDrones");
        allCameraDrone.setActionCommand("all cameradrone");
        allCameraDrone.addActionListener(this);
        allCommunicatorDrone=new JRadioButton("All CommunicatorDrones");
        allCommunicatorDrone.setActionCommand("all communicatordrone");
        allCommunicatorDrone.addActionListener(this);
        allMicrophoneDrone=new JRadioButton("All MicrophoneDrones");
        allMicrophoneDrone.setActionCommand("all microphonedrone");
        allMicrophoneDrone.addActionListener(this);
        allMeasurementDrone=new JRadioButton("All MeasurementDrones");
        allMeasurementDrone.setActionCommand("all measurementdrone");
        allMeasurementDrone.addActionListener(this);
		reset=new JButton("Clear");
		reset.addActionListener(this);
		reset.setActionCommand("clear");
		
		content1.add(total);
		content1.add(totalDrone);
		content1.add(totalCamera);
		content1.add(totalMicrophone);
		content1.add(totalCommunicator);
		content1.add(totalMeasurement);
		content1.add(allDrone);
		content1.add(allCameraDrone);
		content1.add(allCommunicatorDrone);
		content1.add(allMicrophoneDrone);
		content1.add(allMeasurementDrone);
		content1.add(reset);
        add(BorderLayout.EAST,content1);
		addWindowListener(this);
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
            "Energy consumption",     //title
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
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){System.exit(0);}
	public void windowClosing(WindowEvent e){System.exit(0);}
	public void windowDeactivated(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}	
	public void actionPerformed(ActionEvent e)
	{		
			String com=e.getActionCommand();
			if(com.equals("type"))typeSelection();
			else if((com.equals("num"))&&bloc)numSelection();
			else if(com.equals("clear"))clearGraph();
			else if (com.equals("total camera"))positionTotalCamera=displayTotalGraph(totalCamera,positionTotalCamera,parameters.cameraDroneColor.get(),listCameraDroneEnergy);
			else if (com.equals("total microphone"))positionTotalMicrophone=displayTotalGraph(totalMicrophone,positionTotalMicrophone,parameters.microphoneDroneColor.get(),listMicrophoneDroneEnergy);
			else if (com.equals("total drone"))positionTotalDrone=displayTotalGraph(totalDrone,positionTotalDrone,parameters.droneColor.get(),listDroneEnergy);
			else if (com.equals("total communicator"))positionTotalCommunicator=displayTotalGraph(totalCommunicator,positionTotalCommunicator,parameters.communicatorDroneColor.get(),listCommunicatorDroneEnergy);
			else if (com.equals("total measurement"))positionTotalMeasurement=displayTotalGraph(totalMeasurement,positionTotalMeasurement,parameters.measurementDroneColor.get(),listMeasurementDroneEnergy);
			else if (com.equals("all drone"))positionAllDrone=displayAllGraph(allDrone,positionAllDrone,listDroneEnergy);
			else if (com.equals("all cameradrone"))positionAllCameraDrone=displayAllGraph(allCameraDrone,positionAllCameraDrone,listCameraDroneEnergy);
			else if (com.equals("all communicatordrone"))positionAllCommunicatorDrone=displayAllGraph(allCommunicatorDrone,positionAllCommunicatorDrone,listCommunicatorDroneEnergy);		
			else if (com.equals("all microphonedrone"))positionAllMicrophoneDrone=displayAllGraph(allMicrophoneDrone,positionAllMicrophoneDrone,listMicrophoneDroneEnergy);	
			else if (com.equals("all measurementdrone"))positionAllMeasurementDrone=displayAllGraph(allMeasurementDrone,positionAllMeasurementDrone,listMeasurementDroneEnergy);			
			else if (com.equals("total"))
			{
				if (total.isSelected())
				{		
					positionTotal=numberOfGraph;
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

	public void typeSelection()
	{
		String currentType=new String();
		int index;
		index=typeDroneList.getSelectedIndex();
		currentType=typeDroneList.getSelectedItem().toString();		
		if (currentType!="")
		{
				bloc=false;
				numDroneList.removeAllItems();	
			switch (index)
			{
			
				case 0:
					for(int k=0;k<listDrone.length;k++)numDroneList.addItem(listDrone[k].substring(47,listDrone[k].length()));					
					break;
				case 1:
					for(int k=0;k<listCameraDrone.length;k++)numDroneList.addItem(listCameraDrone[k].substring(59,listCameraDrone[k].length()));					
					break;
				case 2:
					for(int k=0;k<listCommunicatorDrone.length;k++)numDroneList.addItem(listCommunicatorDrone[k].substring(71,listCommunicatorDrone[k].length()));
					break;
				case 3:
					for(int k=0;k<listMeasurementDrone.length;k++)numDroneList.addItem(listMeasurementDrone[k].substring(67,listMeasurementDrone[k].length()));
					break;
				case 4 :
					for(int k=0;k<listMicrophoneDrone.length;k++)numDroneList.addItem(listMicrophoneDrone[k].substring(67,listMicrophoneDrone[k].length()));
					break;
				default:break;
			}
		}
		numDroneList.setEnabled(true);
		numDroneList.setSelectedIndex(-1);

		bloc=true;
	}

	public void numSelection()
	{
		int index;
		index=typeDroneList.getSelectedIndex();
		int index2;
		index2=numDroneList.getSelectedIndex();	
		XYDataset dataset1=createDataset(listCameraDroneEnergy[0]);
		if ((index2>=0))
		{
			switch (index)
			{
				case 0:
					dataset1=createDataset(listDroneEnergy[index2]);
					break;
				case 1:
					dataset1=createDataset(listCameraDroneEnergy[index2]);
					break;
				case 2:
					dataset1=createDataset(listCommunicatorDroneEnergy[index2]);
					break;
				case 3:
					dataset1=createDataset(listMeasurementDroneEnergy[index2]);
					break;
				case 4 :
					dataset1=createDataset(listMicrophoneDroneEnergy[index2]);
					break;
				default:
					break;
			}	
	      plot.setDataset(numberOfGraph,dataset1) ;             
	      plot.setRenderer(numberOfGraph, new StandardXYItemRenderer());
	      numberOfGraph++;
		}

	}	


	public int displayTotalGraph(JRadioButton button, int pos,Color color,ArrayList[] listEnergy)
	{
		if (button.isSelected())
		{			
		  pos=numberOfGraph;
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
	public int displayAllGraph(JRadioButton button, int pos, ArrayList[] listEnergy)
	{
		if (button.isSelected())
		{
			 
		  pos=numberOfGraph;
		  for (int f=0;f<listEnergy.length-1;f++)
			{		
			 this.plot.setDataset(pos+f,createDataset(listEnergy[f])) ;             
	         this.plot.setRenderer(pos+f, new StandardXYItemRenderer());         
			}
          numberOfGraph=pos+listEnergy.length-2;       
		}
		else
		{
			if (numberOfGraph>0)
			{
				for (int f=0;f<listEnergy.length-1;f++)
				{					
					plot.setDataset(pos+f,null) ;             
			        plot.setRenderer(pos+f, null);		         
				}
			}
		}
		return pos;
	}
	public void clearGraph()
	{
		for (int k=0;k<plot.getDatasetCount();k++)
		{
			plot.setDataset(k,null);
			plot.setRenderer(k,null);
		}
		numberOfGraph=0;
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
	private void initializedatas(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	){
		int indexCamera=0;
		int indexMeasurement=0;
		int indexMicrophone=0;
		int indexCommunicator=0;
		int indexDrone=0;
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

	private void updateenergylist(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine
	){
		int indexCamera=0;
		int indexMeasurement=0;
		int indexMicrophone=0;
		int indexCommunicator=0;
		int indexDrone=0;
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
	 * Displays the location of the particles on the print stream.
	 * @param timestamp The time stamp when the observation is made.
	 * @param simulationEngine The engine where the simulation is running.
	 */
	
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
	public void calculateTotals()
	{
		
		for (int i=0;i<listDroneEnergy[0].size();i++)
		{
			double somme=0;	
			for (int k=0;k<listDrone.length-2;k++)somme+=(double) listDroneEnergy[k].get(i);
			listDroneEnergy[listDroneEnergy.length-1].add(somme);
		}
		for (int i=0;i<listCameraDroneEnergy[0].size();i++)
		{
			double somme=0;	
			for (int k=0;k<listCameraDrone.length-2;k++)somme+=(double) listCameraDroneEnergy[k].get(i);
			listCameraDroneEnergy[listCameraDroneEnergy.length-1].add(somme);
	
		}
		for (int i=0;i<listCommunicatorDroneEnergy[0].size();i++)
		{
			double somme=0;	
			for (int k=0;k<listCommunicatorDrone.length-2;k++)somme+=(double) listCommunicatorDroneEnergy[k].get(i);
			listCommunicatorDroneEnergy[listCommunicatorDroneEnergy.length-1].add(somme);
	
		}
		for (int i=0;i<listMicrophoneDroneEnergy[0].size();i++)
			{
			 double somme=0;	
			for (int k=0;k<listMicrophoneDrone.length-2;k++)somme+=(double) listMicrophoneDroneEnergy[k].get(i);	
			listMicrophoneDroneEnergy[listMicrophoneDroneEnergy.length-1].add(somme);
		}
		for (int i=0;i<listMeasurementDroneEnergy[0].size();i++)
		{
			double somme=0;	
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
	 * {@inheritDoc}
	 */
	@Override
	public void endObservation() {
		calculateTotals();
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

