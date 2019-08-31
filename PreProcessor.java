import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
/*
 * This class loads service centers, demand vertices and distance matrix from a file.
 */
public class PreProcessor {
	public static HashMap<Integer, Vertex> allVertices    = new HashMap<Integer, Vertex>();
	public static HashMap<Integer, Vertex> demandVertices = new HashMap<Integer, Vertex>();
	public static HashMap<Integer, ServiceCenter> allSC   = new HashMap<Integer, ServiceCenter>();

	public static ArrayList<Integer> dnIDs               = new ArrayList<Integer>();  
	public static ArrayList<Integer> scIDs               = new ArrayList<Integer>();  
	public static Graph graph                            = null;
	public static int CONSTGREYCOL                       = 8224125;

	//this routine takes path of file containing service center details as argument and load in the Hash map
	public static int loadAllUniqueServiceCenters(String filePath) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String line = bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null) {
			String[] details = line.split(" ");
			ServiceCenter serviceCenter = new ServiceCenter(details[1],Integer.parseInt(details[1]),
					Constants.color[allSC.size()],Integer.parseInt(details[4]),Integer.parseInt(details[5]));

			allSC.put(Integer.parseInt(details[1]), serviceCenter);
			scIDs.add(Integer.parseInt(details[1]));
//			System.out.println("SC in Vornoi "+serviceCenter.getServiceCenterId()+" at Pos: "+serviceCenter.getScIndex());
//			System.out.println("Color, Capacity and Penalty added "+serviceCenter.getColor()+","
//					+serviceCenter.getMaxCapacity()+","+serviceCenter.getPenalty());
		}
		bufferedReader.close();
		System.out.println("Total no of service center added: "+allSC.size());
		return allSC.size();
	}

	//this function load all the vertex id along with vertex object(id and color) in HashMap
	public static int loadAllVertices(String filePath) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String line = bufferedReader.readLine();
		int index = 0;
		while((line = bufferedReader.readLine()) != null){
			String[] details = line.split(" ");
			Vertex vertex = new Vertex(details[1],index,CONSTGREYCOL); // grey color
			allVertices.put(Integer.parseInt(details[1]), vertex);
			if(!allSC.containsKey(Integer.parseInt(details[1]))){
				demandVertices.put(Integer.parseInt(details[1]),vertex);
				dnIDs.add(Integer.parseInt(details[1]));
				//				System.out.println("It is demand node "+vertex.getnodeID()+" at Pos: "+index+"/"+vertex.getVertexIndex());
			} else {
				//				System.out.println("It is service node "+vertex.getnodeID()+" at Pos: "+index+"/"+vertex.getVertexIndex());
			}
			index++;

		}
		System.out.println("Total no of all vertices are "+allVertices.size());
		System.out.println("Total no of demand vertices are "+demandVertices.size());
		bufferedReader.close();
		return allVertices.size();
	}

	/* Load everything in CCNVD */
	public static int loadCCNVD(String filePath) throws IOException {
		int edgesCount = 0;
		graph = new Graph(allVertices.size());
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String line = bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null) {
			String[] details  = line.split(" ");
			if (allVertices.containsKey((Integer.parseInt(details[1])))&&allVertices.containsKey(Integer.parseInt(details[2]))) {
				edgesCount++;
				//				System.out.println(allVertices.get(details[1]).getVertexIndex()+"--"+allVertices.get(details[2]).getVertexIndex()+" "+details[3]);
				graph.adj[allVertices.get(Integer.parseInt(details[1])).getVertexIndex()]
						[allVertices.get(Integer.parseInt(details[2])).getVertexIndex()] = Integer.parseInt(details[3]);
			}
		}
		bufferedReader.close();
		System.out.println("Graph size: " + edgesCount + " edges.");
		return edgesCount;
	}

	public static void LoadDistanceMatrixAndBuildMinHeap(String filePath) throws IOException{
		int index = 0;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String line = "";

		while((line = bufferedReader.readLine()) != null){
			String details[] = line.split(",");
			MinHeapNode bestDNforSC = null;
			int minDistance = Integer.MAX_VALUE;
			ServiceCenter sc = allSC.get(scIDs.get(index));
			int dnIDsIndex = 0;
			for (int indexCount = 0; indexCount<allVertices.size(); indexCount++) {
				int distance = Integer.parseInt(details[indexCount]);
				if (distance > 0 && demandVertices.containsKey(indexCount)) {
					MinHeapNode minHeapNode = new MinHeapNode(distance,dnIDs.get(dnIDsIndex),sc.getScIndex());
					sc.minheap.add(minHeapNode);
					if(distance <= minDistance) {
						minDistance = distance;
						bestDNforSC = new MinHeapNode(minDistance,dnIDs.get(dnIDsIndex),sc.getScIndex());
					}
					dnIDsIndex++;
				}	
			}
			Loral.bestDNheap.add(bestDNforSC);
			//			System.out.println("Best demand for "+bestDNforSC.getScIndex()+" is "+bestDNforSC.getDnIndex());
			index++;
		}	
		bufferedReader.close();
	}
}
