package backend.tests.nextNodeTest.programmierprojekt;

import java.util.Random;

import backend.phase.one.programmierprojekt.Graph;
import backend.phase.two.programmierprojekt.Iterater;

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
	public void testNextNode(String path) {
		
		Graph graph = new Graph (path);
		Iterater it = new Iterater(graph);
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			double random_lat = 47.284206700000006d + random.nextDouble() + random.nextInt(6); 
			double random_long = 5.8630797d + random.nextDouble() + random.nextInt(8);
			
			
		}
	}
}
