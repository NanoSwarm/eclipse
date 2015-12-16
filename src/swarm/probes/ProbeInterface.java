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
	JRadioButton total;
	JRadioButton totalCamera;
	JRadioButton totalMicrophone;
	JRadioButton totalCommunicator;
	JRadioButton totalMeasurement;
	JButton reset;
	public ProbeInterface(SwarmParameters param)
	{
		parameters=param;
		listDroneEnergy=new ArrayList[parameters.nbOfDroneAgents+1];
		listCameraDroneEnergy=new ArrayList[parameters.nbOfCameraDroneAgents+1];
		listMicrophoneDroneEnergy=new ArrayList[parameters.nbOfMicrophoneDroneAgents+1];
		listCommunicatorDroneEnergy=new ArrayList[parameters.nbOfCommunicatorDroneAgents+1];
		listMeasurementDroneEnergy=new ArrayList[parameters.nbOfMeasurementDroneAgents+1];
		for (int k=0;k<parameters.nbOfDroneAgents+1;k++)
		{
			listDroneEnergy[k]=new ArrayList<Double>();
		}
		for (int k=0;k<parameters.nbOfCameraDroneAgents+1;k++)
		{
			listCameraDroneEnergy[k]=new ArrayList<Double>();
		}
		for (int k=0;k<parameters.nbOfCommunicatorDroneAgents+1;k++)
		{
			listCommunicatorDroneEnergy[k]=new ArrayList<Double>();
		}
		for (int k=0;k<parameters.nbOfMeasurementDroneAgents+1;k++)
		{
			listMeasurementDroneEnergy[k]=new ArrayList<Double>();
		}
		for (int k=0;k<parameters.nbOfMicrophoneDroneAgents+1;k++)
		{
			listMicrophoneDroneEnergy[k]=new ArrayList<Double>();
		}
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
		ArrayList<Double> test=new ArrayList<Double>();
		test.add(0.0);
		XYDataset dataset = createDataset(test);
		chart = createChart(dataset); 
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        add(BorderLayout.CENTER,chartPanel);
        total=new JRadioButton("Total");
        totalCamera=new JRadioButton("Total Camera Drone");
        totalMicrophone=new JRadioButton("Total Microphone Drone");
        totalCommunicator=new JRadioButton("Total Communicator Drone");
        totalMeasurement=new JRadioButton("Total Measurement Drone");
        
    	Container content1=new Container();
		BoxLayout ligne2=new BoxLayout(content1, BoxLayout.Y_AXIS);
		reset=new JButton("Clear");
		reset.addActionListener(this);
		reset.setActionCommand("clear");
		content1.setLayout(ligne2);
		content1.add(total);
		content1.add(totalCamera);
		content1.add(totalMicrophone);
		content1.add(reset);
        add(BorderLayout.EAST,content1);
		addWindowListener(this);
		pack();
		setVisible(false);
	}
	private XYDataset createDataset(ArrayList<Double> s) 
	{      
	        final XYSeries series = new XYSeries("");
	        for (int k=0;k<s.size();k++)
	        {
	        	series.add(k,s.get(k));
	        }
      
	        final XYSeriesCollection dataset = new XYSeriesCollection();
	        dataset.addSeries(series);              
	        return dataset;
	        
                
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Energy consumption",      // chart title
            "Time Interval",                      // x axis label
            "Energy",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            false,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
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

		plot.setDataset(0,null);
		plot.setRenderer(0,null);       
        return chart;
    }
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
			if(com.equals("type"))
			{
				typeSelection();
			}
			else if(com.equals("num"))
			{
				numSelection();
			}
			else if(com.equals("clear"))
			{
				clearGraph();
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
		switch (index)
		{
			case 0:
				numDroneList.removeAllItems();	
				for(int k=0;k<listDrone.length;k++)
				//numDroneList.addItem(listDrone[k].substring(47,listDrone[k].length()));	
				numDroneList.addItem(listDrone[k]);
				break;
			case 1:
				numDroneList.removeAllItems();	
				for(int k=0;k<listCameraDrone.length;k++)
				//numDroneList.addItem(listCameraDrone[k].substring(59,listCameraDrone[k].length()));	
					numDroneList.addItem(listCameraDrone[k]);
				break;
			case 2:
				numDroneList.removeAllItems();	
				for(int k=0;k<listCommunicatorDrone.length;k++)
				numDroneList.addItem(listCommunicatorDrone[k]);//.substring(71,listCommunicatorDrone[k].length()));
				break;
			case 3:
				numDroneList.removeAllItems();	
				for(int k=0;k<listMeasurementDrone.length;k++)
				numDroneList.addItem(listMeasurementDrone[k]);//.substring(67,listMeasurementDrone[k].length()));
				break;
			case 4 :
				numDroneList.removeAllItems();	
				for(int k=0;k<listMicrophoneDrone.length;k++)
				numDroneList.addItem(listMicrophoneDrone[k]);//.substring(67,listMicrophoneDrone[k].length()));
				break;
			default:
				break;
		}
				
			}
		
		
		numDroneList.setEnabled(true);
	}
	@SuppressWarnings("unchecked")
	public int numberOfGraph=0;
	public void numSelection()
	{
		int index;
		index=typeDroneList.getSelectedIndex();
		int index2;
		index2=numDroneList.getSelectedIndex();
		
		String currentType =new String();
	currentType=typeDroneList.getSelectedItem().toString();	
	XYDataset dataset1=createDataset(listCameraDroneEnergy[0]);
	if ((currentType!="")&&(index2>=0))
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
          this.plot.setDataset(numberOfGraph,dataset1) ;             
          this.plot.setRenderer(numberOfGraph, new StandardXYItemRenderer());
          plot.getRenderer().setSeriesPaint(0, Color.RED);
          numberOfGraph++;
	}
	}
	public void clearGraph()
	{
		for (int k=0;k<numberOfGraph;k++)
		{
			plot.setDataset(k,null);
			plot.setRenderer(k,null);
		}
		numberOfGraph=0;
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
			for( ILocalStateOfAgent agtState : chamberState.getPublicLocalStateOfAgents() ){
				if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.CAMERADRONE ) )
				{
					AgtCameraDronePLSInRoom castedAgtState = (AgtCameraDronePLSInRoom) agtState;
					listCameraDrone[indexCamera]=castedAgtState.toString();
					//listCameraDroneEnergy.get(indexCamera).add(castedAgtState.getEnergy());	
					indexCamera++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.DRONE ) )
				{
					AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;
					listDrone[indexDrone]=castedAgtState.toString();
					//listDroneEnergy.get(indexDrone).add(castedAgtState.getEnergy());																			
					indexDrone++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) )
				{
					AgtCommunicatorDronePLSInRoom castedAgtState = (AgtCommunicatorDronePLSInRoom) agtState;
					listCommunicatorDrone[indexCommunicator]=castedAgtState.toString();
					//listCommunicatorDroneEnergy.get(indexCommunicator).add(castedAgtState.getEnergy());																			
					indexCommunicator++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE ) )
				{
					AgtMeasurementDronePLSInRoom castedAgtState = (AgtMeasurementDronePLSInRoom) agtState;
					listMeasurementDrone[indexMeasurement]=castedAgtState.toString();
					//listMeasurementDroneEnergy.get(indexMeasurement).add(castedAgtState.getEnergy());																			
					indexMeasurement++;
				}							
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) )
				{
					AgtMicrophoneDronePLSInRoom castedAgtState = (AgtMicrophoneDronePLSInRoom) agtState;
					listMicrophoneDrone[indexMicrophone]=castedAgtState.toString();
					//listMicrophoneDroneEnergy.get(indexMicrophone).add(castedAgtState.getEnergy());	
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
	@SuppressWarnings("unchecked")
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
		this.setVisible(true);
	}
	@SuppressWarnings("unchecked")
	public void calculateTotals()
	{
		
		for (int i=0;i<listDroneEnergy[0].size();i++)
		{
			double somme=0;	
			for (int k=0;k<listDrone.length-2;k++)
			{
				somme+=(double) listDroneEnergy[k].get(i);
			}

			listDroneEnergy[listDroneEnergy.length-1].add(somme);
		}
		for (int i=0;i<listCameraDroneEnergy[0].size();i++)
		{
			double somme=0;	
			for (int k=0;k<listCameraDrone.length-2;k++)
			{
				somme+=(double) listCameraDroneEnergy[k].get(i);
			}
			listCameraDroneEnergy[listCameraDroneEnergy.length-1].add(somme);
	
		}
		for (int i=0;i<listCommunicatorDroneEnergy[0].size();i++)
			{
			 double somme=0;	
			for (int k=0;k<listCommunicatorDrone.length-2;k++)
			{
				somme+=(double) listCommunicatorDroneEnergy[k].get(i);
			}
			listCommunicatorDroneEnergy[listCommunicatorDroneEnergy.length-1].add(somme);
	
		}
		for (int i=0;i<listMicrophoneDroneEnergy[0].size();i++)
			{
			 double somme=0;	
			for (int k=0;k<listMicrophoneDrone.length-2;k++)
			{
				somme+=(double) listMicrophoneDroneEnergy[k].get(i);
			}
	
			listMicrophoneDroneEnergy[listMicrophoneDroneEnergy.length-1].add(somme);
		}
		for (int i=0;i<listMeasurementDroneEnergy[0].size();i++)
			{
			 double somme=0;	
			for (int k=0;k<listMeasurementDrone.length-2;k++)
			{
				somme+=(double) listMeasurementDroneEnergy[k].get(i);
			}
	
			listMeasurementDroneEnergy[listMeasurementDroneEnergy.length-1].add(somme);
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

