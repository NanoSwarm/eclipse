package swarm.probes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.lgi2a.similar.microkernel.ISimulationEngine;
import swarm.model.SwarmParameters;
import swarm.model.environment.room.EnvPLSInRoom;
import swarm.model.level.SwarmLevelList;
import swarm.model.level.room.Graph;


@SuppressWarnings("serial")
/**
 * 
 * @author Alexandre JIN
 * The tool to repaint the map space after each change of z level
 *
 */
public class MapDrawer extends JPanel
{
	/**
	 * Graph of the simulation
	 */
	Graph graph;
	/**
	 * the Z of the level
	 */
	public int tempMax;
	public SwarmParameters parameters;
	int IFACTOR;
	int JFACTOR;
	private int z;
	MapDrawer(SwarmParameters param){
		z = 0;
		parameters =param;
		tempMax=30;
	}
	/**
	 * 
	 */
   public void paint(Graphics g) {
	  // int z=(int) Math.floor(800/graphique.getLength());	   
		
		IFACTOR=this.getWidth()/graph.getImax();
		JFACTOR=this.getHeight()/graph.getJmax();
	   double val=0 ;			
	   this.setBackground(Color.LIGHT_GRAY);	
	 for (int i=0;i< graph.getImax();i++)
	   {
		  for (int j=0;j<graph.getJmax();j++)
		   { 
			   if ((graph.getSpaceGraph())[i][j][z].cubeIsVisited())
			   {
				  
				   val =(1/graph.getSpaceGraph()[i][j][z].getMeasuredValue())*tempMax/Math.sqrt( Math.pow(parameters.objectivePosition.x , 2) 
							+Math.pow(parameters.objectivePosition.y , 2)
							+Math.pow(parameters.objectivePosition.z , 2)
							   );;
						
				   System.out.println(val);
				/*   if (val<0)
				   {		   
					   g.setColor(new Color(14,14,241));
				   }else if (val<tempMax/8){
					   g.setColor(new Color(10,85,244));
				   }else if (val<tempMax/7){
					   g.setColor(new Color(15,173,244));
				   }else if (val<tempMax/6){
					   g.setColor(new Color(11,243,244));
				   }else if (val<20){
					   g.setColor(new Color(10,244,228));
				   }else if (val<30){
					   g.setColor(new Color(236,244,10));
				   }else if (val<40){
					   g.setColor(new Color(238,190,16));
				   }else if (val<50){
					   g.setColor(new Color(234,92,20));
				   }else if (val<80){
					   g.setColor(new Color(249,45,5));
				   }else {
					   g.setColor(new Color(255,0,0));
				   }*/
				   int valInt=(int) Math.floor(3*val);
				   if (val<5 ) {		   
					   g.setColor(new Color(14,14,241));
				   }else if (val<24){
				   g.setColor(new Color(10,255-valInt,255));
				   }else if (val<tempMax){
					 g.setColor(new Color(255,255-valInt,10));
				   }else {
						 g.setColor(new Color(255,0,0));
					 
						 
					 }
				   g.fillRect(i*IFACTOR+IFACTOR, j*JFACTOR+JFACTOR,IFACTOR, JFACTOR);
			   }
			}
	   
      }
   }
   /**
    * get and set the graph of the simulation
    * @param simulationEngine
    */
	   public void setGraph(ISimulationEngine simulationEngine){
		   EnvPLSInRoom casted;
		   casted = (EnvPLSInRoom)simulationEngine.getSimulationDynamicStates().get(
				   SwarmLevelList.ROOM).getPublicLocalStateOfEnvironment();
		   graph = casted.getGraph();
	   }
	   public void setZ(int newZ)
	   {
		   z = newZ;
	   }
	   public int getZ()
	   {
		   return z;
	   }
 }
	  
	

