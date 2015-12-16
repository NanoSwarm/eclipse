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

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
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

public class ProbeInterface 	extends Frame 
								implements 	WindowListener,
											ActionListener,
											IProbe
{
	public String[][]listDroneEnergy;
	public String[][]listCameraDroneEnergy;
	public String[][]listMicrophoneDroneEnergy;
	public String[][]listCommunicatorDroneEnergy;
	public String[][]listMeasurementDroneEnergy;
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
	public ProbeInterface(SwarmParameters param)
	{
		parameters=param;
		listCameraDroneEnergy=new String[parameters.nbOfCameraDroneAgents][parameters.simulationTime+1];
		listDroneEnergy=new String[parameters.nbOfDroneAgents][parameters.simulationTime+1];
		listMicrophoneDroneEnergy=new String[parameters.nbOfMicrophoneDroneAgents][parameters.simulationTime+1];
		listCommunicatorDroneEnergy=new String[parameters.nbOfCommunicatorDroneAgents][parameters.simulationTime+1];
		listMeasurementDroneEnergy=new String[parameters.nbOfMeasurementDroneAgents][parameters.simulationTime+1];		
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
		String[] test={"0","0","0","0","0","0","0"};
		XYDataset dataset = createDataset(test);
		chart = createChart(dataset); 
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        chartPanel.setVisible(false);
        add(BorderLayout.CENTER,chartPanel);
        total=new JRadioButton("Total");
        totalCamera=new JRadioButton("Total Camera Drone");
        totalMicrophone=new JRadioButton("Total Microphone Drone");
        totalCommunicator=new JRadioButton("Total Communicator Drone");
        totalMeasurement=new JRadioButton("Total Measurement Drone");
    	Container content1=new Container();
		BoxLayout ligne2=new BoxLayout(content1, BoxLayout.Y_AXIS);
		content1.setLayout(ligne2);
		content1.add(total);
		content1.add(totalCamera);
		content1.add(totalMicrophone);
        add(BorderLayout.EAST,content1);
		addWindowListener(this);
		pack();
		setVisible(false);
	}
	private XYDataset createDataset(String[] s) 
	{      
	        final XYSeries series = new XYSeries("Energy line");
	        for (int k=0;k<s.length;k++)
	        {
	        	series.add(k,Double.parseDouble(s[k]));
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
            true,                     // include legend
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
				typeselection();
			}
			else if(com.equals("num"));
				numselection();
			
	}

	public void typeselection()
	{
		numDroneList.setEnabled(true);
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
				numDroneList.addItem(listDrone[k].substring(47,listDrone[k].length()));				
				break;
			case 1:
				numDroneList.removeAllItems();	
				for(int k=0;k<listCameraDrone.length;k++)
				numDroneList.addItem(listCameraDrone[k].substring(59,listCameraDrone[k].length()));		
				break;
			case 2:
				numDroneList.removeAllItems();	
				for(int k=0;k<listMeasurementDrone.length;k++)
				numDroneList.addItem(listMeasurementDrone[k].substring(69,listMeasurementDrone[k].length()));
				break;
			case 3:
				numDroneList.removeAllItems();	
				for(int k=0;k<listCommunicatorDrone.length;k++)
				numDroneList.addItem(listCommunicatorDrone[k].substring(71,listCommunicatorDrone[k].length()));
				break;
			case 4 :
				numDroneList.removeAllItems();	
				for(int k=0;k<listMicrophoneDrone.length;k++)
				numDroneList.addItem(listMicrophoneDrone[k].substring(67,listMicrophoneDrone[k].length()));
				break;
			default:
				break;
		}
				
			}
		
		
	}
	public void numselection()
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
			dataset1=createDataset(listMeasurementDroneEnergy[index2]);
			break;
		case 3:
			dataset1=createDataset(listCommunicatorDroneEnergy[index2]);
			break;
		case 4 :
			dataset1=createDataset(listMicrophoneDroneEnergy[index2]);
			break;
		default:
			break;
	}
	chartPanel.setVisible(true);
          this.plot.setDataset(0, null);
          this.plot.setRenderer(0, null);
          this.plot.setDataset(0,dataset1) ;             
          this.plot.setRenderer(0, new StandardXYItemRenderer());
          plot.getRenderer().setSeriesPaint(0, Color.RED);
               
	}
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
					listCameraDroneEnergy[indexCamera][0]=castedAgtState.getEnergy()+"";	
					indexCamera++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.DRONE ) )
				{
					AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;
					listDrone[indexDrone]=castedAgtState.toString();
					listDroneEnergy[indexDrone][0]=castedAgtState.getEnergy()+"";																			
					indexDrone++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) )
				{
					AgtCommunicatorDronePLSInRoom castedAgtState = (AgtCommunicatorDronePLSInRoom) agtState;
					listCommunicatorDrone[indexCommunicator]=castedAgtState.toString();
					listCommunicatorDroneEnergy[indexCommunicator][0]=castedAgtState.getEnergy()+"";																			
					indexCommunicator++;
				}
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE ) )
				{
					AgtMeasurementDronePLSInRoom castedAgtState = (AgtMeasurementDronePLSInRoom) agtState;
					listMeasurementDrone[indexMeasurement]=castedAgtState.toString();
					listMeasurementDroneEnergy[indexMeasurement][0]=castedAgtState.getEnergy()+"";																			
					indexMeasurement++;
				}							
				else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) )
				{
					AgtMicrophoneDronePLSInRoom castedAgtState = (AgtMicrophoneDronePLSInRoom) agtState;
					listMicrophoneDrone[indexMicrophone]=castedAgtState.toString();
					listMicrophoneDroneEnergy[indexMicrophone][0]=castedAgtState.getEnergy()+"";	
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
				listCameraDroneEnergy[indexCamera][(int)timestamp.getIdentifier()]=castedAgtState.getEnergy()+"";	
				indexCamera++;
			}
			else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.DRONE ) )
			{
				AgtDronePLSInRoom castedAgtState = (AgtDronePLSInRoom) agtState;
				listDroneEnergy[indexDrone][(int)timestamp.getIdentifier()]=castedAgtState.getEnergy()+"";																			
				indexDrone++;
			}
			else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.COMMUNICATORDRONE ) )
			{
				AgtCommunicatorDronePLSInRoom castedAgtState = (AgtCommunicatorDronePLSInRoom) agtState;
				listCommunicatorDroneEnergy[indexCommunicator][(int)timestamp.getIdentifier()]=castedAgtState.getEnergy()+"";																			
				indexCommunicator++;
			}
			else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MEASUREMENTDRONE ) )
			{
				AgtMeasurementDronePLSInRoom castedAgtState = (AgtMeasurementDronePLSInRoom) agtState;
				listMeasurementDroneEnergy[indexMeasurement][(int)timestamp.getIdentifier()]=castedAgtState.getEnergy()+"";																			
				indexMeasurement++;
			}							
			else if( agtState.getCategoryOfAgent().isA( SwarmAgentCategoriesList.MICROPHONEDRONE ) )
			{
				AgtMicrophoneDronePLSInRoom castedAgtState = (AgtMicrophoneDronePLSInRoom) agtState;
				listMicrophoneDroneEnergy[indexMicrophone][(int)timestamp.getIdentifier()]=castedAgtState.getEnergy()+"";	
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

}

