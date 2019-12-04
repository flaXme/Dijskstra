package backend.phase.one.programmierprojekt;

public class MinHeap {
	private int[] heap; //NodeIds
	private int[] costs;
	private int[] heapIds; //Indizes von Knoten im Heap
	private boolean[] inHeap; //beschreibt ob Knoten im Heap vorhanden ist
	private int size; //aktuelle Heapgroesse
	
	public MinHeap(int capacity) {
		heap = new int[capacity];
		costs = new int[capacity];
		heapIds = new int[capacity];
		inHeap = new boolean[capacity];
		size = 0;
	}
	
	public boolean getInHeap(int nodeId) {
		return inHeap[nodeId];
	}
	public int getCost(int nodeId) {
		return costs[nodeId];
	}
	public void setCost(int nodeId, int cost) {
		costs[nodeId] = cost;
	}
	public int getSize() {
		return size;
	}
	public int getHeapId(int nodeId) {
		return heapIds[nodeId];
	}
	
	private int getLeftHeapIndex(int heapIndex) {
		return 2 * heapIndex + 1;
	}
	private int getRightHeapIndex(int heapIndex) {
		return 2 * heapIndex + 2;
	}
	private int getParentHeapIndex(int heapIndex) {
		return (heapIndex -1) / 2;
	}
	
	private int getLeft(int heapIndex) {
		return heap[getLeftHeapIndex(heapIndex)];
	}
	private int getRight(int heapIndex) {
		return heap[getRightHeapIndex(heapIndex)];
	}
	private int getParent(int heapIndex) {
		return heap[getParentHeapIndex(heapIndex)];
	}
	
	private boolean hasLeft(int heapIndex) {
		return getLeftHeapIndex(heapIndex) < size;
	}
	private boolean hasRight(int heapIndex) {
		return getRightHeapIndex(heapIndex) < size;
	}
	
	private void swap(int firstHeapIndex, int secondHeapIndex) {
		//heapIds vertauschen
		heapIds[heap[firstHeapIndex]] = secondHeapIndex;
		heapIds[heap[secondHeapIndex]] = firstHeapIndex;
		//Knoten vertauschen
		int temp = heap[firstHeapIndex];
		heap[firstHeapIndex] = heap[secondHeapIndex];
		heap[secondHeapIndex] = temp;
	}
	
	//Knoten hinzufuegen
	public void add(int nodeId) {
		size++;
		heap[size - 1] = nodeId;
		heapIds[nodeId] = size -1;
		inHeap[nodeId] = true;
		heapifyUp(size - 1);
	}
	
	//Knoten mit minimalen Kosten rausziehen
	public int extractMin() {
		int node = heap[0];
		swap(0, size - 1);
		heapIds[node] = -1; //beschreibt besucht
		size--;
		inHeap[node] = false;
		heapifyDown();
		return node;
	}
	
	//Kosten eines Knoten aktualisieren und Heap-Eigenschaft wiederherstellen
	public void decreaseKey(int nodeId, int cost) {
		costs[nodeId] = cost;
		heapifyUp(heapIds[nodeId]);
	}
	
	//Heap-Eigenschaft nach oben wiederherstellen
	private void heapifyUp(int heapIndex) {
		while(heapIndex != 0 && costs[getParent(heapIndex)] > costs[heap[heapIndex]]) {
			int parentIndex = getParentHeapIndex(heapIndex);
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

}
