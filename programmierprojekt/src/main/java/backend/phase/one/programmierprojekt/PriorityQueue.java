package backend.phase.one.programmierprojekt;

public class PriorityQueue {
	private final int[] heap; // NodeIds
	private final int[] costs;
	private final int[] heapIds; // Indizes von Knoten im Heap
	private final boolean[] inHeap; // beschreibt ob Knoten im Heap vorhanden ist
	private int size; // aktuelle Heapgroesse

	public PriorityQueue(final int capacity) {
		heap = new int[capacity];
		costs = new int[capacity];
		heapIds = new int[capacity];
		inHeap = new boolean[capacity];
		size = 0;
	}

	public boolean getInHeap(final int nodeId) {
		return inHeap[nodeId];
	}

	public int getCost(final int nodeId) {
		return costs[nodeId];
	}

	public void setCost(final int nodeId, final int cost) {
		costs[nodeId] = cost;
	}

	public int getSize() {
		return size;
	}

	public int getHeapId(final int nodeId) {
		return heapIds[nodeId];
	}

	private int getLeftHeapIndex(final int heapIndex) {
		return 2 * heapIndex + 1;
	}

	private int getRightHeapIndex(final int heapIndex) {
		return 2 * heapIndex + 2;
	}

	private int getParentHeapIndex(final int heapIndex) {
		return (heapIndex - 1) / 2;
	}

	private int getLeft(final int heapIndex) {
		return heap[getLeftHeapIndex(heapIndex)];
	}

	private int getRight(final int heapIndex) {
		return heap[getRightHeapIndex(heapIndex)];
	}

	private int getParent(final int heapIndex) {
		return heap[getParentHeapIndex(heapIndex)];
	}

	private boolean hasLeft(final int heapIndex) {
		return getLeftHeapIndex(heapIndex) < size;
	}

	private boolean hasRight(final int heapIndex) {
		return getRightHeapIndex(heapIndex) < size;
	}

	private void swap(final int firstHeapIndex, final int secondHeapIndex) {
		// heapIds vertauschen
		heapIds[heap[firstHeapIndex]] = secondHeapIndex;
		heapIds[heap[secondHeapIndex]] = firstHeapIndex;
		// Knoten vertauschen
		final int temp = heap[firstHeapIndex];
		heap[firstHeapIndex] = heap[secondHeapIndex];
		heap[secondHeapIndex] = temp;
	}

	// Knoten hinzufuegen
	public void add(final int nodeId) {
		size++;
		heap[size - 1] = nodeId;
		heapIds[nodeId] = size - 1;
		inHeap[nodeId] = true;
		heapifyUp(size - 1);
	}

	// Knoten mit minimalen Kosten rausziehen
	public int extractMin() {
		final int node = heap[0];
		swap(0, size - 1);
		heapIds[node] = -1; // beschreibt besucht
		size--;
		inHeap[node] = false;
		heapifyDown();
		return node;
	}

	// Kosten eines Knoten aktualisieren und Heap-Eigenschaft wiederherstellen
	public void decreaseKey(final int nodeId, final int cost) {
		costs[nodeId] = cost;
		heapifyUp(heapIds[nodeId]);
	}

	// Heap-Eigenschaft nach oben wiederherstellen
	private void heapifyUp(int heapIndex) {
		while (heapIndex != 0 && costs[getParent(heapIndex)] > costs[heap[heapIndex]]) {
			final int parentIndex = getParentHeapIndex(heapIndex);
			swap(parentIndex, heapIndex);
			heapIndex = parentIndex;
		}
	}
	
	//Heap-Eigenschaft nach unten wiederherstellen
	private void heapifyDown() {
		int heapIndex = 0;
		while(hasLeft(heapIndex)) {
			int minChildIndex = getLeftHeapIndex(heapIndex);
			if(hasRight(heapIndex) && costs[getRight(heapIndex)] < costs[getLeft(heapIndex)]) {
				minChildIndex = getRightHeapIndex(heapIndex);
			}
			if(costs[heap[heapIndex]] < costs[heap[minChildIndex]]) {
				break;
			} else {
				swap(heapIndex, minChildIndex);
			}
			heapIndex = minChildIndex;
		}
	}

	public void printHeap() {
		for(int i = 0; i < size; i++) {
			System.out.print(costs[i] + "(" + heap[i] + ") , ");
		}
	}
	
	public static void main(String[] args) {
		PriorityQueue p = new PriorityQueue(10);
		p.printHeap();
		p.add(0);
		p.add(7);
		p.decreaseKey(7, 0);
		p.printHeap();
	}
}
