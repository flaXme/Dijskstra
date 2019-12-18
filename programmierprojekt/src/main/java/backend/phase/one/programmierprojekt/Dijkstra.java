package backend.phase.one;


public class Dijkstra {
	private int[] dis;
	private int[] parent;
	
	
	Dijkstra(Graph graph, int start){
		System.out.println("computing dijkstra...");
		long sTime = System.currentTimeMillis();
		this.dis = new int[graph.getNodeNr()];
		this.parent = new int[graph.getNodeNr()];
		
		for (int i = 0; i < parent.length; i++) {
			dis[i] = Integer.MAX_VALUE;
			parent[i] = -1; // no parent
		}
		
		parent[start] = start;
		dis[start] = 0;
		
		Heap heap = new Heap(graph.getNodeNr());
		
		heap.add(start, 0);
		
		while(heap.getSize() > 0) {
			int[] min = heap.remove();
			int[] out = graph.getOutgingEdgesArray(min[0]);
			
			if(out != null) {
				for (int i = 0; i < out.length; i += 3) {
					if (dis[out[i]] + out[i+2] < dis[out[i+1]]) {
						dis[out[i+1]] = dis[out[i]] + out[i+2];
						parent[out[i+1]] = out[i];
						if (heap.posInHeap[out[i]] != 0) {// in heap
							heap.decreaseKey(out[i+1], dis[out[i]] + out[i+2]);
						}else {
							heap.add(out[i+1], dis[out[i]] + out[i+2]);
						}
					}
				}
				
				
			}
			
		}
		long eTime = System.currentTimeMillis();
		long time = eTime - sTime;
		System.out.println("Dijkstra Computation took "+time/1000+"s");
		
	}
	
	int getShortestPathTo(int nodeID) {
		return this.dis[nodeID];
	}

}
