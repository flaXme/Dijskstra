package backend.phase.two;

import backend.phase.one.Graph;

/**
 * 
 * @author Ahmed Ebrahim Aldekal
 *
 */

public class QuadTree {
	final int MAX_CAPACITY = 4;
	int size = 0;
	int level = 0;
	int[] id;
	double[] latitude;
	double[] longtiude;
	Boundary boundary;
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

		northWest = new QuadTree(this.level + 1, new Boundary(
				this.boundary.getxMin(), yOffset,  xOffset, this.boundary.getyMax()));
		northEast = new QuadTree(this.level + 1, new Boundary(
				xOffset, yOffset, this.boundary.getxMax(), this.boundary.getyMax()));
		southWest = new QuadTree(this.level + 1, new Boundary(
				this.boundary.getxMin(), this.boundary.getyMin(), xOffset, yOffset));
		southEast = new QuadTree(this.level + 1, new Boundary(
				xOffset, this.boundary.getyMin(), this.boundary.getxMax(), yOffset));


	}

	public static void main(String[] args) {
		Graph graph = new Graph("/home/ebrahiad/Downloads/germany.fmi");
		QuadTree tree = new QuadTree(1, new Boundary(0, 0, Double.MAX_VALUE, Double.MAX_VALUE));
		long start = System.currentTimeMillis();
		for (int i = 0; i < graph.getNodeNr(); i++) {
			tree.insert(i, graph.getLatitude(i), graph.getLongitude(i));
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
