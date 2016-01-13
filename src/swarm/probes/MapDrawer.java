package swarm.probes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.lgi2a.similar.microkernel.IProbe;
import fr.lgi2a.similar.microkernel.ISimulationEngine;
import fr.lgi2a.similar.microkernel.SimulationTimeStamp;


	public class MapDrawer extends JPanel
	{
		Graphics graph;
	   public void paint(Graphics g) {
		   graph=g;
	    	this.setBackground(Color.LIGHT_GRAY);		    
	      }		
	   public void add(double x,double y, double val)
	   {	    
		   
		   this.setBackground(Color.LIGHT_GRAY);
		   val*=10000;
		   System.out.println(val);
		   if (val<15){		   
		   graph.setColor(Color.BLUE);
		   }else if (val<20){
			graph.setColor(Color.YELLOW);
		   }else if (val<80){
			graph.setColor(Color.ORANGE);
		   }else {
			graph.setColor(Color.RED);
		   }
		   graph.fillRect(2*(int)x/10, 2*(int)y/10, 2, 2);
		 repaint();
		
	   } 
 }
	  
	

