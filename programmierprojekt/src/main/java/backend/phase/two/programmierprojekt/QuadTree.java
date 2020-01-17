package backend.phase.two.programmierprojekt;

import backend.phase.one.programmierprojekt.Graph;

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
	/**
	 * 
	 * @param latitude
	 * @param longtiude
	 * @return -1 NO NEXT NODE
	 */
	int nextNeighbour(double latitude, double longtiude) {
		if (!this.boundary.inRange(latitude, longtiude)) {
			return -1;
		}
		int nextNeighbour = -2;
		double dis = Double.MAX_VALUE;
		for(int i = 0; i < this.size; i++) {
			if(distance(this.latitude[i],this.longtiude[i],latitude,longtiude)<dis) {
				dis = distance(this.latitude[i],this.longtiude[i],latitude,longtiude);
				nextNeighbour = this.id[i];
			}
		}
		if(northEast != null) {
			
			if (this.northWest.boundary.inRange(latitude, longtiude)) {
				if(this.northWest.size != 0) {
					nextNeighbour = this.northWest.nextNeighbour(latitude, longtiude);
				}
			}

			else if (this.northEast.boundary.inRange(latitude, longtiude)) {
				if(this.northEast.size != 0) {
					nextNeighbour = this.northEast.nextNeighbour(latitude, longtiude);
				}
			}

			else if (this.southWest.boundary.inRange(latitude, longtiude)) {
				if(this.southWest.size != 0) {
					nextNeighbour = this.southWest.nextNeighbour(latitude, longtiude);
				}
			}

			else if (this.southEast.boundary.inRange(latitude, longtiude)) {
				if(this.southEast.size != 0) {
					nextNeighbour = this.southEast.nextNeighbour(latitude, longtiude);
				}
			}
		}
		return nextNeighbour;
	}
	double distance(double x1,double y1,double x2,double y2) {
		double lat_diff_pow_2 = Math.pow(x1  - x2,  2);
    	double lon_diff_pow_2 = Math.pow(y1 - y2, 2);
    	double diff = Math.sqrt(lat_diff_pow_2 + lon_diff_pow_2);
		return diff;
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
		Graph graph = new Graph("/home/ad/Downloads/graph-files/MV.fmi");

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
		double lat = latMin + Math.random() + Math.random();
		double lon = longMin + Math.random() + Math.random();
		System.out.println(lat);
		System.out.println(lon);
		int tree_next = tree.nextNeighbour(lat, lon );
		System.out.println(graph.getLatitude(tree_next) +","+ graph.getLongitude(tree_next));
		System.out.println(tree_next);
		
		Iterater tr = new Iterater(graph);
		int it_next = tr.findNextNeighbor(lat, lon);
		System.out.println(graph.getLatitude(it_next) +","+ graph.getLongitude(it_next));
		System.out.println(it_next );
	}

}
