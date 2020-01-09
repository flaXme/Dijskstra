package backend.phase.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * read static Graph date from File represent it as Arrays
 * 
 * @author Ahmed Ebrahim Aldekal
 */
public class Graph {
	private int nodeNr;
	private int edgeNr;
	private double[] latitude;
	private double[] longitude;
	private int[] nrOfOutgoingEdges;
	private int[] edgeArray;
	private int[] nodeArray;

	public Graph(String path) {
		System.out.println();
		System.out.print("reading file ");
		long startTime = System.currentTimeMillis();
		readGraphFile(path);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println();
		System.out.println("reading file took: " + totalTime / 1000 + "s");
	}

	private void readGraphFile(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			for (int i = 0; i < 5; i++) {// skip the first five lines
				br.readLine();
			}

			nodeNr = Integer.parseInt(br.readLine());// read the line 6
			edgeNr = Integer.parseInt(br.readLine());// read the line 7
			latitude = new double[nodeNr];
			longitude = new double[nodeNr];
			nrOfOutgoingEdges = new int[nodeNr];
			edgeArray = new int[edgeNr * 3];
			nodeArray = new int[nodeNr];
			String line;
			// fill nodeArray
			for (int i = 0; i < nodeNr; i++) {// read lines rang from 8 to 8+nodeNr-1
				if (i % 1000000 == 0){
					System.out.print("#");
				}
				line = br.readLine();
				String[] tempString = line.split(" ");
				latitude[i] = Double.valueOf(tempString[2]);
				longitude[i] = Double.valueOf(tempString[3]);
				nodeArray[i] = -1;
			}
			// fill edgeArray
			int index = 0;
			for (int i = 0; i < edgeNr; i++) {// read line range from (8+nodeNr) to (8+nodeNr+edgeNr)
				if (i % 1000000 == 0){
					System.out.print("#");
				}
				line = br.readLine();
				String[] tempStringArray = line.split(" ");
				edgeArray[index] = Integer.parseInt(tempStringArray[0]);
				index++;
				edgeArray[index] = Integer.parseInt(tempStringArray[1]);
				index++;
				edgeArray[index] = Integer.parseInt(tempStringArray[2]);
				index++;
			}
			
			br.close();

			for (int i = 0; i < edgeNr; i++) {
				if (nodeArray[edgeArray[i * 3]] == -1) {
					nodeArray[edgeArray[i * 3]] = i * 3;
				}
				nrOfOutgoingEdges[edgeArray[i * 3]] += 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	int[] getEdgeArray() {
		return edgeArray;
	}

	int[] getNodeArray() {
		return nodeArray;
	}

	int getNrOfOutgoingEdges(int nodeID) {
		return nrOfOutgoingEdges[nodeID];
	}

	public int getNodeNr() {
		return nodeNr;
	}

	int getEdgeNr() {
		return edgeNr;
	}

	public double getLatitude(int nodeID) {
		return latitude[nodeID];
	}

	public double getLongitude(int nodeID) {
		return longitude[nodeID];
	}

	/**
	 * 
	 * @param nodeID
	 * @return Array with number of outgoing edges * 3
	 */
	int[] getOutgingEdgesArray(int nodeID) {
		if (nrOfOutgoingEdges[nodeID] >= 1) {
			int startIndex = nodeArray[nodeID];
			int endIndex = getNrOfOutgoingEdges(nodeID) * 3 + startIndex;
			return Arrays.copyOfRange(edgeArray, startIndex, endIndex);
		}
		return null;
	}

	/**
	 * to return the number of outgoing edges for specific node
	 * 
	 * @param nodeID
	 * @return the number of outgoing edges for the node
	 */
	int getNumberOfOutgoingEdge(int nodeID) {
		return nrOfOutgoingEdges[nodeID];
	}

}
