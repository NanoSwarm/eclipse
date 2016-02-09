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
 * @author Alexandre Jin, Corentin Muselet, Mathieu Varinas, Marc Verraes.
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
	private int z;
	/**
	 * the max parameter of temperature
	 */
	public int tempMax;
	/**
	 * the parameters of the simulation
	 */
	public SwarmParameters parameters;
	/**
	 * Factor for the drawing axis x
 	 */
	int IFACTOR;
	/**
	 * Factor for the drawing axis y
 	 */
	int JFACTOR;
	/**
	 * The constructor of the drawer
	 * @param param of the simulation
	 */
	MapDrawer(SwarmParameters param)
	{
		z = 0;
		parameters =param;
		tempMax=30;
	}
	/**
	 * The method to draw the map
	 */
   public void paint(Graphics g) 
   {		
	   IFACTOR=this.getWidth()/graph.getImax();
	   JFACTOR=this.getHeight()/graph.getJmax();
	   double valueToEvaluate=0 ;			
	   for (int i=0;i< graph.getImax();i++)
	   {
		  for (int j=0;j<graph.getJmax();j++)
		  { 
			  if ((graph.getSpaceGraph())[i][j][z].cubeIsVisited())
			  {				  
				  valueToEvaluate =((1/graph.getSpaceGraph()[i][j][z].getMeasuredValue())*tempMax/Math.sqrt( Math.pow(parameters.objectivePosition.x , 2) 
							+Math.pow(parameters.objectivePosition.y , 2)
							+Math.pow(parameters.objectivePosition.z , 2)
							   ));;
							   if (valueToEvaluate==Double.POSITIVE_INFINITY||valueToEvaluate==Double.NEGATIVE_INFINITY) valueToEvaluate=tempMax;
				  int valInt=(int) Math.floor(6*valueToEvaluate);
				  if (valInt>235) valInt=235;
				  if (valInt<20) valInt=20;
				  if (valueToEvaluate<tempMax/8 ) {		
					  g.setColor(new Color(255-valInt+20,valInt/6,valInt/6));
				  }else if (valueToEvaluate<tempMax/4){
					  g.setColor(new Color(255,255-valInt,valInt/6));
				  }else if (valueToEvaluate<tempMax/2){
					  g.setColor(new Color(valInt/6,255-valInt,255));
				  }else if (valueToEvaluate>tempMax/2){
					  g.setColor(new Color(valInt/6,valInt/6,255-valInt+20));				   
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
   public void setGraph(ISimulationEngine simulationEngine)
   {
	   EnvPLSInRoom casted;
	   casted = (EnvPLSInRoom)simulationEngine.getSimulationDynamicStates().get(
				   SwarmLevelList.ROOM).getPublicLocalStateOfEnvironment();
	   graph = casted.getGraph();
   }
   public void setZ(int newZ){z = newZ;}
   public int getZ(){return z;}
}
	  
	

