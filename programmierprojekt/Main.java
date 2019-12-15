import java.util.Arrays;

//import backend.phase.one.programmierprojekt.Dijkstra_0;

public class Main {

	public static void main(String[] args) {
		Graph g = new Graph("/home/user/MV/MV.fmi");
		

		System.out.println("hi");
		System.out.println(g.getNodeNr());
		System.out.println(g.getEdgeNr());
		
		//g.printEdgeArray(g.getEdgeNr()*3);
		//g.printNodeArray(g.getNodeNr());
		//System.out.println(Arrays.toString(g.getEdgeArray()));
		//System.out.println(Arrays.toString(g.getNodeArray()));
		Dijkstra_2 d = new Dijkstra_2(g, 0);
		
		System.out.println(Arrays.toString(Arrays.copyOfRange(d.getCostFromStartNodeToAllOtherNodes(), 0, 5)));
				
		//System.out.println(Arrays.toString(d.getCostFromStartNodeToAllOtherNodes()));
		//System.out.println(Arrays.toString(g.getOutgingEdgesArray(1)));
		
		//System.out.println(nodeArray);

	}

}
