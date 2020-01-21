package backend.tests.nextNodeTest.programmierprojekt;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

import backend.phase.one.programmierprojekt.Graph;
import backend.phase.two.programmierprojekt.Boundary;
import backend.phase.two.programmierprojekt.Iterater;
import backend.phase.two.programmierprojekt.QuadTree;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * for Germany Graph is 
	 * latMin = 47.284206700000006
	 * latMax = 55.052937500000006
	 * lat_diff = 7.7687308
	 * longMin = 5.8630797
	 * longMax = 15.0834433
	 * long_diff = 9.2203636
	 */
	@Test
	public void testNextNode() {
		System.out.println("bitte path eingeben: ");
		Scanner s = new Scanner(System.in);
		String path = s.nextLine();
		Graph graph = new Graph (path);
		
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
		long start = System.currentTimeMillis();
		QuadTree tree = new QuadTree(1, new Boundary(latMin, longMin, latMax, longMax),Double.MAX_VALUE);
		Iterater it = new Iterater(graph);
		for (int i = 0; i < graph.getNodeNr(); i++) {
			tree.insert(i, graph.getLatitude(i), graph.getLongitude(i));
		}
		long end = System.currentTimeMillis();
		System.out.println("QuadTree aufgebaut in "+(end - start)+"ms.");
		int nrOfRichtig = 0;
		double timeQuadTree = 0;
		double timeIterater =0;
		for (int i = 0; i < 100; i++) {
			//double random_lat = 47.284206700000006d + random.nextDouble() + random.nextInt(5); 
			double random_long = longMin + Math.random()*(longMax-longMin); 
			//double random_long = 5.8630797d + random.nextDouble() + random.nextInt(7);
			double random_lat = latMin + Math.random()*(latMax-latMin);
			double start1 = System.currentTimeMillis();
			int tree_next = tree.nextNeighbor2(random_lat, random_long);
			double end1 = System.currentTimeMillis();
			timeQuadTree += (end1 - start1);
			
			start1 = System.currentTimeMillis();
			int it_next = it.findNextNeighbor(random_lat, random_long);
			end1 = System.currentTimeMillis();
			timeIterater += (end1 - start1);
			
//			System.out.println("it_next: " + it_next + "tree_next: " + tree_next);
//			System.out.println("Latitude: " + random_lat + "Longitude: " + random_long);
			if(it_next==tree_next) {
				nrOfRichtig++;
			}
			assertEquals(it_next, tree_next);
		}
		timeQuadTree /= 100;
		timeIterater /= 100;
		System.out.println("Mit QuadTree hat es durchschnittlich "+timeQuadTree+"ms gedauert.");
		System.out.println("Mit Iterater hat es durchschnittlich "+timeIterater+"ms gedauert.");
		System.out.println(nrOfRichtig + " von 100 sind gleich.");
	}
}
