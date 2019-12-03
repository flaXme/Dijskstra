package backend.phase.one.programmierprojekt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * read static Graph Date from File represent it as Arrays
 *
 */
public class Graph {
	private int nodeNr;
	private int edgeNr;
	private double[] latitude;
	private double[] longitude;
	private int[] nrOfOutgoingEdges;
	private int[] edgeArray;
	private int[] nodeArray;

	Graph(String path) {
		readGraphFile(path);
	}

	private void readGraphFile(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			for (int i = 0; i < 5; i++) {// skip the first five lines
				br.readLine();
			}

			nodeNr = Integer.parseInt(br.readLine());//read the line 6
			edgeNr = Integer.parseInt(br.readLine());//read the line 7
			latitude = new double[nodeNr];
			longitude = new double[nodeNr];
			nrOfOutgoingEdges = new int [nodeNr];
			edgeArray = new int[edgeNr*3];
			nodeArray = new int[nodeNr];
			String line;
			// fill nodeArray
			for (int i = 0; i < nodeNr; i++) {//read lines rang from 8 to 8+nodeNr-1
				line = br.readLine();
				String[] tempString = line.split(" ");
				latitude[i] = Double.valueOf(tempString[2]);
				longitude[i] = Double.valueOf(tempString[3]);
				nodeArray[i] = -1;
			}
			// fill edgeArray
			int index = 0;
			for (int i = 0; i < edgeNr; i++) {//read line range from (8+nodeNr) to (8+nodeNr+edgeNr)
				line = br.readLine();
				String[] tempStringArray = line.split(" ");
				edgeArray[index] = Integer.parseInt(tempStringArray[0]);
				index++;
				edgeArray[index] = Integer.parseInt(tempStringArray[1]);
				index++;
				edgeArray[index] = Integer.parseInt(tempStringArray[2]);
				index++;
			}
			
			for (int i = 0; i < edgeNr; i++) {
				if (nodeArray[edgeArray[i*3]] == -1) {
					nodeArray[edgeArray[i*3]] = i*3;
				}
				nrOfOutgoingEdges[edgeArray[i*3]] += 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	int getNrOfOutgoingEdges(int nodeID) {
		return nrOfOutgoingEdges[nodeID];
	}

	int getNodeNr() {
		return nodeNr;
	}

	int getEdgeNr() {
		return edgeNr;
	}

	double getLatitude(int nodeID) {
		return latitude[nodeID];
	}

	double getLongitude(int nodeID) {
		return longitude[nodeID];
	}
	
	int[] getOutgingEdgesArray(int nodeID) {
		int startIndex = nodeArray[nodeID];
		int endIndex = nodeArray[nodeID+1];
		while (endIndex == -1) {
			endIndex = nodeArray[nodeID++];
		}
		return Arrays.copyOfRange(nodeArray, startIndex, endIndex);
	}
	/**
	 * prints rang(0, @param) of the latitude Array
	 * @param numberOfElements
	 */
	void printLatitudeArray(int numberOfElements) {
		if (numberOfElements <= latitude.length) {
			for (int i = 0; i < numberOfElements; i++) {
				System.out.println("node: "+i+" latitude: "+ latitude[i]);
			}
		}
		
	}
	/**
	 * prints rang(0, @param) of the longitude Array 
	 * @param numberOfElementsToPrint
	 */
	void printLongitudeArray(int numberOfElementsToPrint) {
		if (numberOfElementsToPrint <= longitude.length) {
			for (int i = 0; i < numberOfElementsToPrint; i++) {
				System.out.println("node: "+i+" latitude: "+ longitude[i]);
			}
		}
		
	}
	/**
	 * to return the number of outgoing edges for specific node
	 * @param nodeID
	 * @return the number of outgoing edges for the node
	 */
	int getNumberOfOutgoingEdge(int nodeID) {
		return nrOfOutgoingEdges[nodeID];
	}
	/**
	 * prints rang(0, @param) elements of the node Array
	 * @param number of the elements to print
	 */
	void printNodeArray(int numberOfElemntToPrint) {
		if (numberOfElemntToPrint <= nodeArray.length) {
			for (int i = 0; i < numberOfElemntToPrint; i++) {
				System.out.println(nodeArray[i]);
			}
		}
	}
	/**
	 * 
	 * @param numberOfElemntToPrint
	 */
	void printEdgeArray(int numberOfElemntToPrint) {
		if (numberOfElemntToPrint <= edgeArray.length) {
			for (int i = 0; i < numberOfElemntToPrint; i++) {
				System.out.println(edgeArray[i]);
			}
		}
	}
	/**
	 * 
	 * @param numberOfElemntToPrint
	 */
	void printOutgoingEdges(int numberOfElemntToPrint) {
		if (numberOfElemntToPrint <= nodeArray.length) {
			for (int i = 0; i < numberOfElemntToPrint; i++) {
				System.out.println("ndoe: "+i+" outgoing edges number: "+ nrOfOutgoingEdges[i]);
			}
		}
		
	}

}
