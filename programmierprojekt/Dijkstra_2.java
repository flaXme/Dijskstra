import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

//import backend.phase.one.programmierprojekt.Edge;
//import backend.phase.one.programmierprojekt.Graph;



/**
 * 
 * Dijkstra implementation, December 10th '19
 * 
 * Differences between the current Dijkstra_0 implementation and this one:
 * - no Edge class: The data is not stored inside an Object; instead the data is put into
 *   a public array inside the Graph class 
 * - no getter methods: for performance reasons, the method does not use any getter methods
 *   to get any data from a different class; instead, the necessary arrays are declared public.
 * - in case a graph has 0 edges, it will be marked as done, but it will not be added to the
 *   queue, since nothing needs to be done
 * 
 * @author Jonathan
 *
 */
public class Dijkstra_2 {

	Graph graph;
	int startNodeID;
	static int[] costFromStartNodeToAllOtherNodes;
	boolean[] addedToQueue;
	private PriorityQueue<Integer> priorityQueue;
	

	public Dijkstra_2(Graph graph, int startNodeID) {
		
		this.graph = graph;
		this.startNodeID = startNodeID;
		
		priorityQueue = new PriorityQueue<Integer>();
		
		System.out.println("Dijkstra one-to-all wird berechnet...");
		long startTime = System.currentTimeMillis();
		costFromStartNodeToAllOtherNodes = dijkstraOneToAll(graph, startNodeID);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Dijkstra one-to-all berchnung hat "+totalTime/1000+" Sekunden gedauert");
			
		System.out.println(Arrays.copyOfRange(getCostFromStartNodeToAllOtherNodes(), 0, 10));
	}
	
	private int[] dijkstraOneToAll(Graph graph, int startNodeID) {
		int[] costToOther = new int[graph.getNodeNr()];
		for (int i = 0; i < costToOther.length; i++) {// for all nodes make the shortest path cost -1
			costToOther[i] = Integer.MAX_VALUE;
		}
		costToOther[startNodeID] = 0; // shortest path from startNode to itself is zero
		
		addedToQueue = new boolean[graph.getNodeNr()];
		
		priorityQueue.add(startNodeID);
		
		addedToQueue[startNodeID] = true;
		
		while(!priorityQueue.isEmpty()) {
			int node = priorityQueue.remove();
			
			for(int i = 0; i < graph.nrOfOutgoingEdges[node]; i++) {
				
				int calculatedCost = graph.edgeArrayCost[graph.nodeArray[node] + i] + costToOther[node];
				int currentTargetNumber = graph.edgeArrayTarget[graph.nodeArray[node] + i];
				int currentTargetCost = costToOther[graph.edgeArrayTarget[graph.nodeArray[node] + i]];
				
				//System.out.println("Edge Array Source: " + graph.edgeArraySource[graph.nodeArray[node] + i]);
				//System.out.println("Edge Array Target: " + graph.edgeArrayTarget[graph.nodeArray[node] + i]);
				//System.out.println("Cost to Other: " + costToOther[node]);
				//System.out.println("Distance to the node: " + graph.edgeArrayCost[graph.nodeArray[node] + i]);
				//System.out.println("current edge : " + graph.nodeArray[node]);
				//System.out.println("calculated cost: " + calculatedCost);
				//System.out.println("current target number: " + currentTargetNumber);
				
				if(calculatedCost < currentTargetCost) {
					//System.out.println("Updating current cost from " + costToOther[currentTargetNumber] + " to " + calculatedCost);
					costToOther[currentTargetNumber] = calculatedCost;
				}
				
				if(!addedToQueue[currentTargetNumber]) {
					
					if(graph.nrOfOutgoingEdges[currentTargetNumber] != 0) 
					{
					priorityQueue.add(currentTargetNumber);
					}
					
					addedToQueue[currentTargetNumber] = true;
				}
			}
		}
		
		return costToOther;
		
	}
	

	int[] getCostFromStartNodeToAllOtherNodes() {
		return costFromStartNodeToAllOtherNodes;
	}
}
