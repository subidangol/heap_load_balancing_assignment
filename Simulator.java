import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Simulator {
	public static void main(String[] args) throws IOException { 
		int scCount = PreProcessor.loadAllUniqueServiceCenters("serviceCenters1.txt");
		int nVertices = PreProcessor.loadAllVertices("all1.txt");
		int nEdges = PreProcessor.loadCCNVD("edges1.txt");
		PreProcessor.LoadDistanceMatrixAndBuildMinHeap("distance.txt");
		Loral.startAssignment();
		Iterator it = Loral.assignments.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		System.out.println();
		
		System.out.println("Objective function cost: "+Loral.objectiveFunctionCost);
		System.out.println("Penalty cost: "+Loral.totalPenalty);
		
//		Iterator it = PreProcessor.allSC.entrySet().iterator();
//	    while (it.hasNext()) {
//	        Map.Entry pair = (Map.Entry)it.next();
//	        ServiceCenter sc = (ServiceCenter) pair.getValue();
//	        System.out.println(sc.getScIndex() + " = " + sc.allocations);
//	        it.remove(); // avoids a ConcurrentModificationException
//	    }
//		System.out.println();
	}
}