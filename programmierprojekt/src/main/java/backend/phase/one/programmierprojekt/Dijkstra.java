package backend.phase.one.programmierprojekt;

import java.util.Arrays;

public class Dijkstra {
	private int[] shortestpath;
	private MinHeap heap;
	private Graph graph;
	private int startNodeID;

	Dijkstra(Graph graph, int startNodeID) {
		this.graph = graph;
		this.startNodeID = startNodeID;
		this.shortestpath = new int[graph.getNodeNr()];
		this.startNodeID = startNodeID;
		this.heap = new MinHeap(graph.getNodeNr());
		dijkstraOneToAll(graph, startNodeID);
	}

	private void dijkstraOneToAll(Graph graph, int startNodeID) {
		for (int i = 0; i < shortestpath.length; i++) {
			shortestpath[i] = Integer.MAX_VALUE;
		}
		// shortestpath[startNodeID] = 0;
		heap.insert(startNodeID, 0);

		while (heap.getsize() > 1) {
			int[] minArr = heap.extractMin();
			shortestpath[minArr[0]] = minArr[1];
			int[] minOut = graph.getOutgingEdgesArray(minArr[0]);

			if (minOut != null) {

				for (int i = 1; i <= minOut.length; i += 3) {
					if (!heap.inheap(minOut[i])) {// falls knoten noch nicht im heap
						if (shortestpath[minOut[i]] < shortestpath[minOut[i - 1]] + minOut[i + 1]) {
							;
						}
						else {
							heap.insert(minOut[i], shortestpath[minOut[i - 1]] + minOut[i + 1]);
						}
						//System.out.println(Arrays.toString(heap.heap));
					} else {// falls die knoten in heap und die neue path guenster ist dann update
						if (shortestpath[minOut[i - 1]] + minOut[i + 1] < heap
								.getFromHeap(heap.getRefNodeIndex(i) + 1)) {
							heap.costUpdate(heap.getRefNodeIndex(i), shortestpath[i - 1] + minOut[i + 1]);
						}
					}
				}
			}

		}

	}

	public static void main(String[] args) {
		Graph g = new Graph("/home/ad/Downloads/graph-files/MV.fmi");
		Dijkstra d = new Dijkstra(g, 0);
		//System.out.println(Arrays.toString(d.shortestpath));
		// System.out.println(d.heap.getsize());
	}

}
