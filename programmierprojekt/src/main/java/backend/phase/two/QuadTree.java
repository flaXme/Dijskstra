package backend.phase.two;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
	final int MAX_CAPACITY = 4;
	int level = 0;
	List<Node> nodes;
	Boundary boundary;
	private QuadTree northEast = null;
	private QuadTree northWest = null;
	private QuadTree southEast = null;
	private QuadTree southWest = null;
	
	QuadTree(int level, Boundary boundary){
		this.level = level;
		this.boundary = boundary;
		nodes = new ArrayList<Node>();
	}
	

	void insert(int x, int y, int value) {
		if (!this.boundary.inRange(x, y)) {
			return;
		}

		Node node = new Node(x, y, value);
		if (nodes.size() < MAX_CAPACITY) {
			nodes.add(node);
			return;
		}
		// Exceeded the capacity so split it in FOUR
		if (northWest == null) {
			split();
		}

		// Check coordinates belongs to which partition 
		if (this.northWest.boundary.inRange(x, y))
			this.northWest.insert(x, y, value);
		else if (this.northEast.boundary.inRange(x, y))
			this.northEast.insert(x, y, value);
		else if (this.southWest.boundary.inRange(x, y))
			this.southWest.insert(x, y, value);
		else if (this.southEast.boundary.inRange(x, y))
			this.southEast.insert(x, y, value);
		else
			System.out.printf("ERROR : Unhandled partition %d %d", x, y);
	}
	
	void split() {
		int xOffset = this.boundary.getxMin()
				+ (this.boundary.getxMax() - this.boundary.getxMin()) / 2;
		int yOffset = this.boundary.getyMin()
				+ (this.boundary.getyMax() - this.boundary.getyMin()) / 2;

		northWest = new QuadTree(this.level + 1, new Boundary(
				this.boundary.getxMin(), this.boundary.getyMin(), xOffset,
				yOffset));
		northEast = new QuadTree(this.level + 1, new Boundary(xOffset,
				this.boundary.getyMin(), xOffset, yOffset));
		southWest = new QuadTree(this.level + 1, new Boundary(
				this.boundary.getxMin(), xOffset, xOffset,
				this.boundary.getyMax()));
		southEast = new QuadTree(this.level + 1, new Boundary(xOffset, yOffset,
				this.boundary.getxMax(), this.boundary.getyMax()));

	}
	
	

}
