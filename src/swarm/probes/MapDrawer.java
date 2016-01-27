package swarm.probes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.lgi2a.similar.microkernel.ISimulationEngine;
import swarm.model.environment.room.EnvPLSInRoom;
import swarm.model.level.SwarmLevelList;
import swarm.model.level.room.Cube;
import swarm.model.level.room.Graph;


@SuppressWarnings("serial")
public class MapDrawer extends JPanel
{
	/**
	 * Graph of the simulation
	 */
	Graph graph;
	/**
	 * the Z of the level
	 */
	int IFACTOR;
	int JFACTOR;
	private int z;
	MapDrawer(){
		z = 0;
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
				   val =10000*(graph.getSpaceGraph()[i][j][z].getMeasuredValue());
				   if (val<0)
				   {		   
					   g.setColor(new Color(14,14,241));
				   }else if (val<5){
					   g.setColor(new Color(10,85,244));
				   }else if (val<10){
					   g.setColor(new Color(15,173,244));
				   }else if (val<15){
					   g.setColor(new Color(11,243,244));
				   }else if (val<18){
					   g.setColor(new Color(10,244,228));
				   }else if (val<20){
					   g.setColor(new Color(236,244,10));
				   }else if (val<30){
					   g.setColor(new Color(238,190,16));
				   }else if (val<35){
					   g.setColor(new Color(234,92,20));
				   }else if (val<80){
					   g.setColor(new Color(249,45,5));
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
	  
	

