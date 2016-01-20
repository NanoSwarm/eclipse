package swarm.probes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import fr.lgi2a.similar.microkernel.ISimulationEngine;
import swarm.model.environment.room.EnvPLSInRoom;
import swarm.model.level.SwarmLevelList;
import swarm.model.level.room.Cube;
import swarm.model.level.room.Graph;


	public class MapDrawer extends JPanel
	{
		Graph graphique;
		Graphics graph;
		public int z;
		MapDrawer(){
			z=0;
		}
	   public void paint(Graphics g) {
		  // int z=(int) Math.floor(800/graphique.getLength());	   
		   graph=g;
		   double val=0 ;			
		   this.setBackground(Color.LIGHT_GRAY);	
		 for (int i=0;i< graphique.getImax();i++)
		   {
			  for (int j=0;j<graphique.getJmax();j++)
			   { 
				   if ((graphique.getSpaceGraph())[i][j][z].cubeIsVisited())
				   {
					   val =10000*(graphique.getSpaceGraph()[i][j][z].getMeasuredValue());
					   if (val<15)
					   {		   
						   graph.setColor(Color.BLUE);
					   }else if (val<20){
						   graph.setColor(Color.YELLOW);
					   }else if (val<80){
						   graph.setColor(Color.ORANGE);
					   }else {
						   graph.setColor(Color.RED);
					   }
				   graph.fillRect(i*20, j*20,20, 20);
				   }
				}
		   
	      }
	   }
	   
	   public void setGraph(ISimulationEngine simulationEngine){
		   EnvPLSInRoom casted;
		   casted=(EnvPLSInRoom)simulationEngine.getSimulationDynamicStates().get(
				   SwarmLevelList.ROOM).getPublicLocalStateOfEnvironment();
		   graphique=casted.getGraph();
	   }
 }
	  
	

