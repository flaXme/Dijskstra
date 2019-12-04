package backend.phase.one.programmierprojekt;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 
 * @author Ahmed Ebrahim Aldekal
 *
 */
public class Dijkstra_0 {
	Graph graph;
	int startNodeID;
	static int[] costFromStartNodeToAllOtherNodes;
	PriorityQueue<Edge> priorityQ;
	boolean[] addedToQueue;

	public Dijkstra_0(Graph graph, int startNodeID) {
		this.graph = graph;
		this.startNodeID = startNodeID;
		System.out.println("Dijkstra one-to-all wird berechnet...");
		long startTime = System.currentTimeMillis();
		costFromStartNodeToAllOtherNodes = dijkstraOneToAll(graph, startNodeID);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Dijkstra one-to-all berchnung hat "+totalTime/1000+" Sekunden gedauert");
	}

	private int[] dijkstraOneToAll(Graph graph, int startNodeID) {
		
		int[] costToOther = new int[graph.getNodeNr()];
		for (int i = 0; i < costToOther.length; i++) {// for all nodes make the shortest path cost -1
			costToOther[i] = Integer.MAX_VALUE;
		}
		costToOther[startNodeID] = 0; // shortest path from startNode to itself is zero

		priorityQ = new PriorityQueue<Edge>();

		addedToQueue = new boolean[graph.getNodeNr()];

		addOutgoingEdgeFromMinamalToPQ(startNodeID); // add the outgoing edges from start node to priority queue

		addedToQueue[startNodeID] = true;

		while (!priorityQ.isEmpty()) {
			Edge tempEdge = priorityQ.remove();// Extract minimal edge also edge with the minimal cost

			if (tempEdge.getCost() + costToOther[tempEdge.getSrc()] < costToOther[tempEdge.getTarg()]) {
				costToOther[tempEdge.getTarg()] = tempEdge.getCost() + costToOther[tempEdge.getSrc()];
			}
			if (!addedToQueue[tempEdge.getTarg()]) {
				addOutgoingEdgeFromMinamalToPQ(tempEdge.getTarg());
				addedToQueue[tempEdge.getTarg()] = true;
			}

		}
		
		return costToOther;
	}

	int[] getCostFromStartNodeToAllOtherNodes() {
		return costFromStartNodeToAllOtherNodes;
	}

	private void addOutgoingEdgeFromMinamalToPQ(int nodeID) {
		int[] out = graph.getOutgingEdgesArray(nodeID);
		int index = 0;
		for (int i = 0; i < graph.getNrOfOutgoingEdges(nodeID); i++) {
			Edge edge = new Edge(out[index], out[index + 1], out[index + 2]);
			priorityQ.add(edge);
			index += 3;
		}
	}

}
