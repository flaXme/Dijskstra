import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Graph {

	private int nodeNr;
	private int edgeNr;
	private double[] latitude;
	private double[] longitude;
	public int[] nrOfOutgoingEdges;
	public int[] edgeArraySource;
	public int[] edgeArrayTarget;
	public int[] edgeArrayCost;
	public int[] nodeArray;

	Graph(String path) {
		System.out.println("Datei wird gelesen...");
		long startTime = System.currentTimeMillis();
		readGraphFile(path);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		System.out.println("Einlesen des Graphs Datei hat "+totalTime/1000+" Sekunden gedauert");
	}

	private void readGraphFile(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			for (int i = 0; i < 5; i++) {// skip the first five lines
				br.readLine();
			}

			nodeNr = Integer.parseInt(br.readLine());// read the line 6
			edgeNr = Integer.parseInt(br.readLine());// read the line 7
			latitude = new double[nodeNr];
			longitude = new double[nodeNr];
			nrOfOutgoingEdges = new int[nodeNr];
			
			edgeArraySource = new int[edgeNr];
			edgeArrayTarget = new int[edgeNr];
			edgeArrayCost = new int[edgeNr];
			
			nodeArray = new int[nodeNr];

			// fill nodeArray
			for (int i = 0; i < nodeNr; i++) {// read lines rang from 8 to 8+nodeNr-1
				String[] tempString = br.readLine().split(" ");
				
				latitude[i] = Double.valueOf(tempString[2]);
				longitude[i] = Double.valueOf(tempString[3]);
				nodeArray[i] = -1; // uses negative number, since it's off the array spectrum
				nrOfOutgoingEdges[i] = 0;
			}
			
			// fill edgeArray
			for (int i = 0; i < edgeNr; i++) {// read line range from (8+nodeNr) to (8+nodeNr+edgeNr)
				String[] tempStringArray = br.readLine().split(" ");
				
				edgeArraySource[i] = Integer.parseInt(tempStringArray[0]);
				edgeArrayTarget[i] = Integer.parseInt(tempStringArray[1]);
				edgeArrayCost[i] = Integer.parseInt(tempStringArray[2]);
			}

			for (int i = 0; i < edgeNr; i++) {
				if (nodeArray[edgeArraySource[i]] == -1) {
					nodeArray[edgeArraySource[i]] = i;
				}
				nrOfOutgoingEdges[edgeArraySource[i]] += 1;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	int[] getNodeArray() {
		return nodeArray;
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

	/**
	 * prints rang(0, @param) of the latitude Array
	 * 
	 * @param numberOfElements
	 */
	void printLatitudeArray(int numberOfElements) {
		if (numberOfElements <= latitude.length) {
			for (int i = 0; i < numberOfElements; i++) {
				System.out.println("node: " + i + " latitude: " + latitude[i]);
			}
		}

	}

	/**
	 * prints rang(0, @param) of the longitude Array
	 * 
	 * @param numberOfElementsToPrint
	 */
	void printLongitudeArray(int numberOfElementsToPrint) {
		if (numberOfElementsToPrint <= longitude.length) {
			for (int i = 0; i < numberOfElementsToPrint; i++) {
				System.out.println("node: " + i + " longitude: " + longitude[i]);
			}
		}

	}

	/**
	 * prints rang(0, @param) elements of the node Array
	 * 
	 * @param number of the elements to print
	 */
	void printNodeArray(int numberOfElemntToPrint) {
		if (numberOfElemntToPrint <= nodeArray.length) {
			for (int i = 0; i < numberOfElemntToPrint; i++) {
				System.out.println(nodeArray[i]);
			}
		}
	}
	
}
