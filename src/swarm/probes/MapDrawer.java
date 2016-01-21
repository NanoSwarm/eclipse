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
				   if (val<15)
				   {		   
					   g.setColor(Color.BLUE);
				   }else if (val<20){
					   g.setColor(Color.YELLOW);
				   }else if (val<80){
					   g.setColor(Color.ORANGE);
				   }else {
					   g.setColor(Color.RED);
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
	  
	

