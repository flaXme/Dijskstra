package backend.phase.one.programmierprojekt;

import java.util.Arrays;

/*
 * Min Heap with based on the even Index, the odd Index contains the nodeIDs
 */
public class MinHeap {
	static int[] heap;
	private int[] refNodeIndex;// if node in heap and should update cost
	private static boolean[] inHeap;
	private int size;
	private int maxsize;

	public MinHeap(int capacity) {
		heap = new int[capacity * 2 + 1];
		inHeap = new boolean[capacity];
		this.refNodeIndex = new int[capacity];
		this.size = 1;
		this.maxsize = capacity * 2;
		heap[0] = Integer.MIN_VALUE;
	}

	void insert(int knoten, int cost) {
		if (size <= maxsize && !inHeap[knoten]) {
			inHeap[knoten] = true;
			heap[size] = knoten;
			refNodeIndex[knoten] = size;
			heap[size + 1] = cost;
			int current = size + 1;
			while (heap[current] < heap[parent(current)]) {
				swap(current, parent(current));
				current = parent(current);
			}
			size += 2;
		}

	}

	private void swap(int fpos, int spos) {
		int tmp;
		int tmp2;
		refNodeIndex[heap[fpos - 1]] = spos - 1;
		refNodeIndex[heap[spos - 1]] = fpos - 1;
		tmp = heap[fpos];
		tmp2 = heap[fpos - 1];
		heap[fpos] = heap[spos];
		heap[fpos - 1] = heap[spos - 1];
		heap[spos] = tmp;
		heap[spos - 1] = tmp2;
	}

	int[] extractMin() {
		int[] min = { heap[1], heap[2] };
		inHeap[heap[1]] = false;
		if (size > 1) {
			heap[1] = heap[size - 2];
			heap[2] = heap[size - 1];
			heap[size - 2] = 0;
			heap[size - 1] = 0;
			size -= 2;
			heapify(2);
		}

		return min;
	}

	void heapify(int pos) {
		if (!isLeaf(pos)) {
			if (heap[pos] > heap[leftChild(pos)] || heap[pos] > heap[rightChild(pos)]) {
				if (heap[leftChild(pos)] < heap[rightChild(pos)]) {
					swap(pos, leftChild(pos));
					heapify(leftChild(pos));
				} else {
					swap(pos, rightChild(pos));
					heapify(rightChild(pos));
				}
			}
		}

	}

	private boolean isLeaf(int pos) {
		if (pos >= (size / 2) && pos <= size) {
			return true;
		}
		return false;
	}

	private int parent(int pos) {
		if (pos == 2) {
			return 2;
		}
		return (pos / 4) * 2;
	}

	private int leftChild(int pos) {
		return (2 * pos);
	}

	private int rightChild(int pos) {
		return (2 * pos) + 2;
	}

	int getsize() {
		return this.size;
	}

	boolean inheap(int pos) {
		return inHeap[pos];
	}

	int getFromHeap(int pos) {
		return heap[pos];
	}

	int getRefNodeIndex(int index) {
		return refNodeIndex[index];
	}

	void costUpdate(int pos, int newCost) {
		heap[pos+1] = newCost;
		int current = pos+1;
		while (heap[current] < heap[parent(current)]) {
			swap(current, parent(current));
			current = parent(current);
		}

	}

	/*public static void main(String[] args) {
		MinHeap h = new MinHeap(10);

		System.out.println(Arrays.toString(heap) + "  size: " + h.getsize());
		h.insert(0, 0);
		System.out.println("<0,0> "+ Arrays.toString(heap) + "  size: " + h.getsize()+" is in Heap 0: "+inHeap[0]+ " ref: "+h.getRefNodeIndex(0));
		System.out.println("min: "+Arrays.toString(h.extractMin())+" was deleted");
		h.insert(1, 9);
		System.out.println("<1,9> "+ Arrays.toString(heap) + "  size: " + h.getsize()+" is in Heap 1: "+inHeap[1]+ " ref: "+h.getRefNodeIndex(1));
		h.insert(2, 8);
		System.out.println("<2,8> "+ Arrays.toString(heap) + "  size: " + h.getsize()+" is in Heap 2: "+inHeap[2]+ " ref: "+h.getRefNodeIndex(2));
		h.insert(4, 7);
		System.out.println("<4,7> "+ Arrays.toString(heap) + "  size: " + h.getsize()+" is in Heap 4: "+inHeap[4]+ " ref: "+h.getRefNodeIndex(4));
		System.out.println("min: "+Arrays.toString(h.extractMin())+" was deleted "+Arrays.toString(heap)+" size: "+h.getsize());
		System.out.println("jetzt muss man <3,1> einfuegen "+inHeap[3]+" die <1,2> update "+inHeap[1]);
		h.insert(3, 8);
		System.out.println("<3,1> "+ Arrays.toString(heap) + "  size: " + h.getsize()+" is in Heap 4: "+inHeap[3]+ " ref: "+h.getRefNodeIndex(3));
		h.insert(1, 7);
		System.out.println("<1,7> "+ Arrays.toString(heap) + "  size: " + h.getsize()+" is in Heap 1: "+inHeap[1]+ " ref: "+h.getRefNodeIndex(1));
		h.costUpdate(3, 5);
		System.out.println(Arrays.toString(heap));
	}*/
}
