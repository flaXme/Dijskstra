package backend.phase.one.programmierprojekt;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Graph g = new Graph("/home/ad/Downloads/graph-files/toy.fmi");
		
		//g.printEdgeArray(g.getEdgeNr()*3);
		//g.printNodeArray(g.getNodeNr());
		//System.out.println(Arrays.toString(g.getEdgeArray()));
		//System.out.println(Arrays.toString(g.getNodeArray()));
		Dijkstra_0 d = new Dijkstra_0(g, 0);
		
		
		//System.out.println(d.getCostFromStartNodeToAllOtherNodes()[16743651]);
		//System.out.println(Arrays.toString(Arrays.copyOfRange(d.getCostFromStartNodeToAllOtherNodes(), 0, 11)));
		//System.out.println(Arrays.toString(g.getOutgingEdgesArray(1)));
		System.out.println(Arrays.toString(d.getCostFromStartNodeToAllOtherNodes()));
	}

}
