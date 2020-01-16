package backend.phase.two;

import backend.phase.one.Graph;

/**
 * Quad Tree for efficient search for next Node
 * 
 * @author Ahmed Ebrahim Aldekal
 *
 */

public class QuadTree {
	public static int numberOfinsertedNode = 0;
	private final int MAX_CAPACITY = 100;
	private int size = 0;
	private int level = 0;
	private int[] id;
	private double[] latitude;
	private double[] longtiude;
	private Boundary boundary;
	private QuadTree northEast = null;
	private QuadTree northWest = null;
	private QuadTree southEast = null;
	private QuadTree southWest = null;

	QuadTree(int level, Boundary boundary) {
		this.level = level;
		this.boundary = boundary;
		id = new int[MAX_CAPACITY];
		latitude = new double[MAX_CAPACITY];
		longtiude = new double[MAX_CAPACITY];
	}

	void insert(int id, double latitude, double longtiude) {
		if (!this.boundary.inRange(latitude, longtiude)) {
			return;
		}

		if (size < MAX_CAPACITY) {
			this.id[size] = id;
			this.latitude[size] = latitude;
			this.longtiude[size] = longtiude;
			size++;
			numberOfinsertedNode++;
			return;
		}
		// Exceeded the capacity so split it in FOUR
		if (northWest == null) {
			split();
		}

		// Check coordinates belongs to which partition
		if (this.northWest.boundary.inRange(latitude, longtiude)) {
			this.northWest.insert(id, latitude, longtiude);
		}

		else if (this.northEast.boundary.inRange(latitude, longtiude)) {
			this.northEast.insert(id, latitude, longtiude);
		}

		else if (this.southWest.boundary.inRange(latitude, longtiude)) {
			this.southWest.insert(id, latitude, longtiude);
		}

		else if (this.southEast.boundary.inRange(latitude, longtiude)) {
			this.southEast.insert(id, latitude, longtiude);
		}

		else {
			System.out.println("ERROR");
		}

	}

	void split() {
		double xOffset = this.boundary.getxMin() + (this.boundary.getxMax() - this.boundary.getxMin()) / 2;
		double yOffset = this.boundary.getyMin() + (this.boundary.getyMax() - this.boundary.getyMin()) / 2;

		northWest = new QuadTree(this.level + 1,
				new Boundary(this.boundary.getxMin(), yOffset, xOffset, this.boundary.getyMax()));
		northEast = new QuadTree(this.level + 1,
				new Boundary(xOffset, yOffset, this.boundary.getxMax(), this.boundary.getyMax()));
		southWest = new QuadTree(this.level + 1,
				new Boundary(this.boundary.getxMin(), this.boundary.getyMin(), xOffset, yOffset));
		southEast = new QuadTree(this.level + 1,
				new Boundary(xOffset, this.boundary.getyMin(), this.boundary.getxMax(), yOffset));

	}

	public static void main(String[] args) {
		/*
		 * latMin = 47.284206700000006
		 *  latMax = 50.244289 
		 *  longMin = 5.8630797 
		 *  longMax = 7.0079849
		 *  
		 *  latMin = 47.284206700000006
		 *  latMax = 55.052937500000006
		 *  longMin = 5.8630797
		 *  longMax = 15.0834433
		 *  
		 *  QuadTree tree = new QuadTree(1, new Boundary(0, 0, Double.MAX_VALUE, Double.MAX_VALUE));
		 *  
		 *  dauert 11 Minuten
		 */
		Graph graph = new Graph("/home/ad/Downloads/graph-files/germany.fmi");

		double latMin = graph.getLatitude(0);
		double latMax = graph.getLatitude(0);
		double longMin = graph.getLongitude(0);
		double longMax = graph.getLongitude(0);

		for (int i = 1; i < graph.getNodeNr(); i++) {
			if (graph.getLatitude(i) < latMin) {
				latMin = graph.getLatitude(i);
			}
		}
		for (int i = 1; i < graph.getNodeNr(); i++) {
			if (graph.getLatitude(i) > latMax) {
				latMax = graph.getLatitude(i);
			}
			
		}
		for (int i = 1; i < graph.getNodeNr(); i++) {
			if (graph.getLongitude(i) < longMin) {
				longMin = graph.getLongitude(i);
			}
		}
		for (int i = 1; i < graph.getNodeNr(); i++) {
			if (graph.getLongitude(i) > longMax) {
				longMax = graph.getLongitude(i);
			}
			
		}
		System.out.println("latMin = " + latMin);
		System.out.println("latMax = " + latMax);
		System.out.println("longMin = " + longMin);
		System.out.println("longMax = " + longMax);

		QuadTree tree = new QuadTree(1, new Boundary(latMin, longMin, latMax, longMax));
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < graph.getNodeNr(); i++) {
			tree.insert(i, graph.getLatitude(i), graph.getLongitude(i));
		}
		System.out.println(numberOfinsertedNode);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
