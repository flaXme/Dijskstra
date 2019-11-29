package backend.phase.one.programmierprojekt;
/**
 * 
 * @author Ahmed Ebrahim Aldekal
 *
 */
public class Dijkstra_0 {
	Graph graph;
	int startNodeID;
	int[] costFromStartNodeToAllOtherNodes;
	
	public Dijkstra_0(Graph graph, int startNodeID) {
		this.graph = graph;
		this.startNodeID = startNodeID;
		costFromStartNodeToAllOtherNodes = dijkstraOneToAll(graph, startNodeID);
	}
	
	private int[] dijkstraOneToAll(Graph graph, int startNodeID) {
		System.out.println("calculate shortest path");
		int [] costToOther = new int[graph.getNodeNr()];
		for (int i = 0; i < costToOther.length; i++) {// for all nodes make the shortest path cost -1
			costToOther[i] = -1;
		}
		costToOther[startNodeID] = 0; // shortest path from startNode to itself is zero
		
		
		return costToOther;
	}
	
	private int[] getOutgoingEdgeWithCost(int nodeID) {
		int[] outgoingEdges = new int[graph.getNrOfOutgoingEdges(nodeID)*2];
		
		
		
		return outgoingEdges;
	}
}
