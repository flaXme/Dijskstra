package backend.phase.two.programmierprojekt;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Stack;

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
	private static double dis;

	public QuadTree(int level, Boundary boundary,double dis) {
		this.level = level;
		this.boundary = boundary;
		id = new int[MAX_CAPACITY];
		latitude = new double[MAX_CAPACITY];
		longtiude = new double[MAX_CAPACITY];
		this.dis = dis;
	}
	void setDis(double d) {
		this.dis = d;
	}

	public void insert(int id, double latitude, double longtiude) {
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
				new Boundary(this.boundary.getxMin(), yOffset, xOffset, this.boundary.getyMax()),dis);
		northEast = new QuadTree(this.level + 1,
				new Boundary(xOffset, yOffset, this.boundary.getxMax(), this.boundary.getyMax()),dis);
		southWest = new QuadTree(this.level + 1,
				new Boundary(this.boundary.getxMin(), this.boundary.getyMin(), xOffset, yOffset),dis);
		southEast = new QuadTree(this.level + 1,
				new Boundary(xOffset, this.boundary.getyMin(), this.boundary.getxMax(), yOffset),dis);

	}
	
	
	public int nextNeighbor(double x, double y) {
		//Stack<QuadTree> stack = new Stack<>();
		QuadTree[] stack = new QuadTree[4];
		int nearestNeighbor = -1;
		for(int i = 0; i < this.size; i++) {
			if(distance(this.latitude[i],this.longtiude[i],x,y)<dis) {
				dis = distance(this.latitude[i],this.longtiude[i],x,y);
				nearestNeighbor = this.id[i];
			}
		}
		Circle c = new Circle(x,y,dis);
		
		if(this.northEast != null) {
//			stack.push(this.northEast);
//			stack.push(this.northWest);
//			stack.push(this.southEast);
//			stack.push(this.southWest);
			stack[0] = this.northEast;
			stack[1] = this.northWest;
			stack[2] = this.southEast;
			stack[3] = this.southWest;
		}
		while(!(stack[0] == null && stack[1] == null && stack[2] == null && stack[3] == null)) {
			QuadTree tr = null;
			for (int i = 0; i < 4; i++) {
				if(stack[i] != null) {
					tr = stack[i];
					stack[i] = null;
					break;
				}
			}
			if(c.intersect(tr.boundary)) {
				int nearestNeighbor1 = tr.nextNeighbor(x, y);
				if(nearestNeighbor1 == -1) {
					continue;
				}
				nearestNeighbor = nearestNeighbor1;
				c.setRadius(dis);
			}
		}
		return nearestNeighbor;
	}
	public int nextNeighbor2(double x,double y) {
		int i = nextNeighbor(x,y);
		setDis(Double.MAX_VALUE);
		return i;
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
		Graph graph = new Graph("/home/duncanjn/Downloads/germany.fmi");

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
//		System.out.println("latMin = " + latMin);
//		System.out.println("latMax = " + latMax);
//		System.out.println("longMin = " + longMin);
//		System.out.println("longMax = " + longMax);

		QuadTree tree = new QuadTree(1, new Boundary(latMin, longMin, latMax, longMax),Double.MAX_VALUE);
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < graph.getNodeNr(); i++) {
			tree.insert(i, graph.getLatitude(i), graph.getLongitude(i));
		}
		//System.out.println(numberOfinsertedNode);
		long end = System.currentTimeMillis();
//		System.out.println(end - start);
		int richtig = 0;
		for (int i = 0; i < 100; i++) {
			
		double lat = latMin + longMin + Math.random()*(longMax-longMin);
		double lon = longMin + Math.random()*(latMax-latMin);
//		System.out.println(lat);
//		System.out.println(lon);
		start = System.currentTimeMillis();
		int tree_next = tree.nextNeighbor2(lat, lon);
		end = System.currentTimeMillis();
//		System.out.println("mit QuadTree hat "+ (end - start) + "ms gedauert.");
//		System.out.println(graph.getLatitude(tree_next) +","+ graph.getLongitude(tree_next));
//		System.out.println(tree_next);
		
		Iterater tr = new Iterater(graph);
		start = System.currentTimeMillis();
		int it_next = tr.findNextNeighbor(lat, lon);
		end = System.currentTimeMillis();
		if(tree_next == it_next) {
			richtig++;
		}
		
//		System.out.println("mit iterater hat "+ (end - start) + "ms gedauert.");
//		System.out.println(graph.getLatitude(it_next) +","+ graph.getLongitude(it_next));
//		System.out.println(it_next );
		}
		System.out.println(richtig);
	}

}
