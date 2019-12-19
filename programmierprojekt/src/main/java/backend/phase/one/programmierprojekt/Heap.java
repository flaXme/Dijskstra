package backend.phase.one.programmierprojekt;

public class Heap {

	private int size;
	int[] cost; // heap
	int[] nodeID;
	int[] posInHeap; // index in Heap to decrees key if 0 then node is not in heap

	Heap(int capacity) {
		this.size = 0;
		this.cost = new int[capacity + 1];
		this.nodeID = new int[capacity + 1];
		this.posInHeap = new int[capacity];

		cost[0] = Integer.MIN_VALUE;
		nodeID[0] = Integer.MIN_VALUE;
		
		for (int i = 1; i < cost.length; i++) {
			cost[i] = Integer.MAX_VALUE;
		}
	}

	void add(int nodeID, int cost) {
		size++;
		this.nodeID[size] = nodeID;
		this.cost[size] = cost;
		this.posInHeap[nodeID] = size;
		heapifyUp(size);
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
		if (pos == 1) {
			return 1;
		}
		return pos / 2;
	}

	private boolean isLeaf(int pos) {
		if (pos >= (size / 2) + 1 && pos <= size) {
			return true;
		}
		return false;
	}

	private int rightChild(int pos) {
		return (2 * pos) + 1;
	}

	private int leftChild(int pos) {
		return (2 * pos);
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
		if (pos != 0) {
			cost[pos] = ncost;
			heapifyUp(pos);
		}
	}

	int[] remove() {
		int[] arr = { nodeID[1], cost[1] };
		this.posInHeap[nodeID[1]] = 0;// knoten vom heap entfernt
		if (this.size == 1) {
			this.posInHeap[nodeID[size]] = 0;
		}else {
			this.posInHeap[nodeID[size]] = 1;
		}
		
		nodeID[1] = nodeID[size];
		cost[1] = cost[size];
		this.nodeID[size] = 0;
		this.cost[size] = Integer.MAX_VALUE;
		size--;
		heapifyDown(1);
		return arr;
	}

	int getSize() {
		return size;
	}


}
