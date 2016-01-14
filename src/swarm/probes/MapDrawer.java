package swarm.probes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import swarm.model.level.room.Graph;


	public class MapDrawer extends JPanel
	{
		MapDrawer(Graph gg){
			graphique=gg;
		}
		Graph graphique;
		Graphics graph;
	   public void paint(Graphics g) {
		   int z=0;
		   graph=g;
		   double val=0 ;
		   this.setBackground(Color.LIGHT_GRAY);	
		   for (int i=0;i< graphique.getSpaceGraph().length;i++)
		   {
			   for (int j=0;j<graphique.getSpaceGraph()[i].length;j++)
				   if (graphique.getSpaceGraph()[i][j][z].cubeIsVisited())
				   {
				val =graphique.getSpaceGraph()[i][j][z].getMeasuredValue();
				   if (val<15){		   
					   graph.setColor(Color.BLUE);
					   }else if (val<20){
						graph.setColor(Color.YELLOW);
					   }else if (val<80){
						graph.setColor(Color.ORANGE);
					   }else {
						graph.setColor(Color.RED);
					   }
				   graph.fillRect(i, j, 5, 5);
		   }		
		   }
	      }		
 }
	  
	

