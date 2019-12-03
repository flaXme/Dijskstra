package backend.phase.one.programmierprojekt;

public class Edge {
	private int src;
	private int targ;
	private int cost;
	
	Edge (int src, int targ, int cost){
		this.src = src;
		this.targ = targ;
		this.cost = cost;
	}

	int getSrc() {
		return src;
	}

	int getTarg() {
		return targ;
	}

	int getCost() {
		return cost;
	}
}
