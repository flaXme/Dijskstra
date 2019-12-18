package backend.phase.one;

import java.util.Scanner;

public class Main {
	static Graph graph;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter path: ");
		String path = scanner.nextLine();
		graph = new Graph(path);

		while (true) {
			System.out.println("chose function:");
			System.out.println("-----------------------------------");
			System.out.println("| 1: Dijkstra with source-target  |");
			System.out.println("|---------------------------------|");
			System.out.println("| 2: Dijkstra with start          |");
			System.out.println("|---------------------------------|");
			System.out.println("| 3: File                         |");
			System.out.println("|---------------------------------|");
			System.out.println("| 4: End                          |");
			System.out.println("-----------------------------------");
			String function = scanner.nextLine();

			if (function.equals("1") || function.equals("2") || function.equals("3")) {
				if (function.equals("1")) 
				{
					System.out.println("source: ");
					int source = scanner.nextInt();
					System.out.println("target: ");
					int target = scanner.nextInt();
					Dijkstra dijk = new Dijkstra (graph, source);
					System.out.println("shortest path between "+ source +" and "+ target + ": "+ dijk.getShortestPathTo(target));
					
				} 
				else if (function.equals("2")) 
				{
					System.out.println("start:	");
					int start = scanner.nextInt();
					Dijkstra dijk = new Dijkstra (graph, start);
					
				} 
				else 
				{
					
				}
			} else if (function.equals("4")) {
				System.out.println("successfully ended");
				scanner.close();
				break;
			}
		}

	}

}
