package backend.phase.two;
/**
 * Class represent Graph Node
 * @author Ahmed Ebrahim Aldekal
 *
 */

public class Node {
	int nodeID;
	double latitude;
	double longitude;
	
	Node(int nodeID, double latitude, double longitude){
		this.nodeID = nodeID;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
