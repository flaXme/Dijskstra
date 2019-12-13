package backend.phase.one.programmierprojekt;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Graph g = new Graph("/home/ad/Downloads/graph-files/toy.fmi");
		
		System.out.println(Arrays.toString(g.getEdgeArray()));
		System.out.println(Arrays.toString(g.getNodeArray()));
		Dijkstra d = new Dijkstra(g, 0);
		
		
		//System.out.println(d.getCostFromStartNodeToAllOtherNodes()[429466]);
		//System.out.println(Arrays.toString(Arrays.copyOfRange(d.getCostFromStartNodeToAllOtherNodes(), 0, 11)));
		//System.out.println(Arrays.toString(g.getOutgingEdgesArray(1)));
		//System.out.println(Arrays.toString(d.getCostFromStartNodeToAllOtherNodes()));
	}

}
