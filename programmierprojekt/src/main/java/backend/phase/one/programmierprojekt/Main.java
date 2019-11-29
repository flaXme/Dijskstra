package backend.phase.one.programmierprojekt;

public class Main {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		Graph g = new Graph("/home/ad/Downloads/graph-files/toy.fmi");
		long endTime = System.currentTimeMillis();
		long totalTime = endTime-startTime;
		System.out.println("Time: "+ totalTime/1000 +" s");
		
		g.printEdgeArray(g.getEdgeNr()*3);
		g.printNodeArray(g.getNodeNr());
	}

}
