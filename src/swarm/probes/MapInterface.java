package swarm.probes;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.lgi2a.similar.microkernel.IProbe;
import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;
import swarm.model.SwarmParameters;


@SuppressWarnings("serial")
/**
 * 
 * @author Alexandre JIN
 * The probe displaying the map of the space explored.
 *
 */
public class MapInterface extends JFrame implements IProbe,ActionListener{
	/** 
	 * The main panel 
	 */
	public JPanel mainPanel;
	public JLabel niveauZ;
	public JPanel actions;
	public JButton NextButton;
	public SwarmParameters parameters;
	/**
	 * the drawer of the map
	 */
	private MapDrawer drawer; 
	/**
	 * 
	 * @param str the title of the window
	 */
    public MapInterface(String str,SwarmParameters param)
    {
    	parameters=param;
    	mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
    	this.setContentPane(mainPanel);
    	NextButton=new JButton("Next level");
    	NextButton.addActionListener(this);
    	NextButton.setActionCommand("suivant");
    	niveauZ=new JLabel("Z = 0"+"("+parameters.roomBoundsZ+")");
    	niveauZ.setHorizontalAlignment(JLabel.CENTER);
    	niveauZ.setFont(new Font(niveauZ.getName(), Font.BOLD, 2*niveauZ.getFont().getSize()));
    	actions=new JPanel();
    	actions.setLayout(new BorderLayout());
    	mainPanel.add(actions,BorderLayout.NORTH);
    	actions.add(niveauZ,BorderLayout.NORTH);
    	actions.add(NextButton,BorderLayout.SOUTH);
    	
    	drawer=new MapDrawer(param);
    	mainPanel.add(drawer,BorderLayout.CENTER);
    	
	    //parameters of the window
	    GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();       
		//get maximum window bounds
		Rectangle maximumWindowBounds=graphicsEnvironment.getMaximumWindowBounds();
    	this.setTitle(str);
        this.setSize((int)maximumWindowBounds.getWidth()/2,(int)maximumWindowBounds.getHeight());
        this.setLocation((int)maximumWindowBounds.getWidth()/2,0);
        //this.setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false); 
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		String command=new String();
		command= e.getActionCommand();
		if (command=="suivant") {
			drawer.setZ(drawer.getZ()+1);
			if (drawer.getZ() >= drawer.graph.getKmax()) drawer.setZ(0);
			niveauZ.setText("Z = "+drawer.getZ()*drawer.graph.getLength()+"("+parameters.roomBoundsZ+")");
			mainPanel.invalidate();     
			mainPanel.repaint();
		};
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
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void observeAtPartialConsistentTime(
			SimulationTimeStamp timestamp,
			ISimulationEngine simulationEngine){
	
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
		drawer.setGraph(simulationEngine);
		this.setVisible(true);
	}
	
}

