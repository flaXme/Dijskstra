package backend.phase.one.programmierprojekt;

public class Heap {

	private int size;
	int[] cost; // heap
	int[] nodeID;
	int[] posInHeap; // index in Heap to decrees key if 0 then node is not in heap

	Heap(int capacity) {
		this.size = 0;
		this.cost = new int[capacity];
		this.nodeID = new int[capacity];
		this.posInHeap = new int[capacity];
		
		for (int i = 0; i < cost.length; i++) {
			cost[i] = Integer.MAX_VALUE;
		}
		
		for (int i = 0; i < posInHeap.length; i++) {
			posInHeap[i] = -1;
		}
	}

	void add(int nodeID, int cost) {
		this.nodeID[size] = nodeID;
		this.cost[size] = cost;
		this.posInHeap[nodeID] = size;
		heapifyUp(size);
		size++;
	}

	private void heapifyUp(int from) {
		int current = from;
		while (cost[current] < cost[parent(current)]) {
			swap(current, parent(current));
			current = parent(current);
		}
	}

	private void swap(int fpos, int spos) {
		int tmp0;
		int tmp1;
		// position or index of node
		posInHeap[nodeID[fpos]] = spos;
		posInHeap[nodeID[spos]] = fpos;
		// swap Id
		tmp1 = nodeID[fpos];
		nodeID[fpos] = nodeID[spos];
		nodeID[spos] = tmp1;
		// swap cost
		tmp0 = cost[fpos];
		cost[fpos] = cost[spos];
		cost[spos] = tmp0;
	}

	private int parent(int pos) {
		if (pos == 0) {
			return 0;
		}
		return (pos - 1) / 2;
	}

	private boolean isLeaf(int pos) {
		if (pos >= (size / 2) + 1 && pos <= size) {
			return true;
		}
		return false;
	}

	private int rightChild(int pos) {
		return (2 * pos) + 2;
	}

	private int leftChild(int pos) {
		return (2 * pos) + 1;
	}

	private void heapifyDown(int pos) {
		if (!isLeaf(pos)) {
			if (cost[pos] > cost[leftChild(pos)] || cost[pos] > cost[rightChild(pos)]) {
				if (cost[leftChild(pos)] < cost[rightChild(pos)]) {
					swap(pos, leftChild(pos));
					heapifyDown(leftChild(pos));
				}
				else {
					swap(pos, rightChild(pos));
					heapifyDown(rightChild(pos));
				}
			}
		}
	}
	
	public void decreaseKey(int nodeId, int ncost) {
		int pos = posInHeap[nodeId];
		if (pos != -1) {
			cost[pos] = ncost;
			heapifyUp(pos);
		}
	}

	int[] remove() {
		int[] arr = { nodeID[0], cost[0] };
		this.posInHeap[nodeID[0]] = -1;// knoten vom heap entfernt
//		if (this.size == 1) {
//			this.posInHeap[nodeID[size]] = -1;
//		}else {
//			this.posInHeap[nodeID[size]] = 1;
//		}
		
		nodeID[0] = nodeID[size];
		cost[0] = cost[size];
		this.nodeID[size] = 0;
		this.cost[size] = Integer.MAX_VALUE;
		size--;
		heapifyDown(0);
		return arr;
	}

	int getSize() {
		return size;
	}


}
