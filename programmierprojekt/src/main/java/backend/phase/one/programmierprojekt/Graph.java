package backend.phase.one.programmierprojekt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

			nodeNr = Integer.parseInt(br.readLine());
			edgeNr = Integer.parseInt(br.readLine());
			latitude = new double[nodeNr];
			longitude = new double[nodeNr];
			nrOfOutgoingEdges = new int [nodeNr];
			edgeArray = new int[edgeNr*3];
			nodeArray = new int[nodeNr];
			String line;
			// fill nodeArray
			for (int i = 0; i < nodeNr; i++) {
				line = br.readLine();
				String[] tempString = line.split(" ");
				latitude[i] = Double.valueOf(tempString[2]);
				longitude[i] = Double.valueOf(tempString[3]);
				nodeArray[i] = -1;
				nrOfOutgoingEdges [i] = -1;
			}
			// fill edgeArray
			int index = 0;
			for (int i = 0; i < edgeNr; i++) {
				line = br.readLine();
				String[] tempStringArray = line.split(" ");
				edgeArray[index] = Integer.parseInt(tempStringArray[0]);
				index++;
				edgeArray[index] = Integer.parseInt(tempStringArray[1]);
				index++;
				edgeArray[index] = Integer.parseInt(tempStringArray[2]);
				index++;
			}
			
			for (int i = 0; i < edgeNr; i++) {// 0
				nodeArray[edgeArray[i*3]] = i*3;
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

}
