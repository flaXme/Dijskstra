package backend.phase.two.programmierprojekt;

import backend.phase.one.programmierprojekt.Graph;

/**
 * Find next neighbor with Iteration over all Nodes
 * @author Ahmed Ebrahim Aldekal
 * 
 */
public class Iterater{
    private Graph graph;
    
    public Iterater(Graph graph) {
    	this.graph = graph;
    }
    
	/**
	 * Find next neighbor NoodeID
	 * 
	 * @param latitude
	 * @param longitude
	 * @return next neighbor Node ID
	 */
    public int findNextNeighbor (double latitude, double longitude){
        int nextNeighbor = -1;
        double current_diff = Double.MAX_VALUE;
        for (int i = 0; i < graph.getNodeNr(); i++){
        	
        	double lat_diff_pow_2 = Math.pow(latitude  - graph.getLatitude(i),  2);
        	double lon_diff_pow_2 = Math.pow(longitude - graph.getLongitude(i), 2);
        	double diff = Math.sqrt(lat_diff_pow_2 + lon_diff_pow_2);
        	
        	if (diff < current_diff) {
        		current_diff = diff;
        		nextNeighbor = i;
        	}
        }
        return nextNeighbor;
    }
    
    public static void main(String[] args) {
		Graph graph = new Graph("/home/ad/Downloads/graph-files/MV.fmi");
		
		Iterater it = new Iterater(graph);
		
		System.out.println(it.findNextNeighbor(53.8227497, 10.728119300000001));
	}
    

}