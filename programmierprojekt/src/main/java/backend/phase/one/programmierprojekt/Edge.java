package backend.phase.one.programmierprojekt;

public class Edge implements Comparable<Edge>{
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

	@Override
	public int compareTo(Edge edge) {
		if (this.cost < edge.getCost()) {
			return -1;
		}else {
			return 0;
		}
	}
}
