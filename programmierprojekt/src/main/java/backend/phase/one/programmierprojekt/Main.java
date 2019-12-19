package backend.phase.one.programmierprojekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
			System.out.println("---------------------------------------");
			System.out.println("| 1: Dijkstra with start-target query |");
			System.out.println("|-------------------------------------|");
			System.out.println("| 2: Dijkstra with start and query    |");
			System.out.println("|-------------------------------------|");
			System.out.println("| 3: File                             |");
			System.out.println("|-------------------------------------|");
			System.out.println("| 4: End                              |");
			System.out.println("---------------------------------------");
			String function = scanner.next();

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
					while(true) {
						System.out.println("Do you want to query? y/n  ");
						String answer = scanner.next();
						if (answer.equals("n")) {
							break;
						}else if (answer.equals("y")) {
							System.out.println("target: ");
							int target = scanner.nextInt();
							System.out.println("shortest path between "+ start +" and "+ target + ": "+ dijk.getShortestPathTo(target));
						}
					}
					
				} 
				else if (function.equals("3")) 
				{	
					System.out.println("Enter file path: ");
					String path2 = scanner.next();
					BufferedReader br = null;
					String line = null;
					String[] split;
					int acutalStart = -1;
					Dijkstra dijk = null;
					try {
						br = new BufferedReader(new FileReader(path2));
						while((line = br.readLine()) != null) {
							split = line.split(" ");
							int start = Integer.parseInt(split[0]);
							int target = Integer.parseInt(split[1]);
							if (acutalStart != start) {
								dijk = new Dijkstra (graph, start);
							}
							System.out.println("shortest path between "+ start +" and "+ target + ": "+ dijk.getShortestPathTo(target));
							acutalStart = start;
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (function.equals("4")) {
				System.out.println("successfully ended");
				scanner.close();
				break;
			}
		}

	}

}
