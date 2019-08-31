import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Loral {
	public static int objectiveFunctionCost=0;
	public static int totalPenalty=0;;
	public static HashMap<Integer, Integer> assignments = new HashMap<Integer,Integer>();
	public static int CONSTGREYCOL = 8224125;
	
	public static PriorityQueue<MinHeapNode> bestDNheap=new PriorityQueue<MinHeapNode>(1,new Comparator<MinHeapNode>() {
        @Override
        public int compare(MinHeapNode o1, MinHeapNode o2) {
        	if(o1.getDistance() == o2.getDistance()){
        		return o1.getDnIndex().compareTo(o2.getDnIndex());
        	} else{
        		return (o1.getDistance()-o2.getDistance());
        	}
        }
    });

	public static void startAssignment() {	
		while(true){
			if(bestDNheap.size() == 0)	break;
			MinHeapNode minHeapNode = bestDNheap.remove();
			Integer dnIndex = minHeapNode.getDnIndex();
			if(PreProcessor.demandVertices.containsKey(dnIndex)) {
				ServiceCenter serviceCenter = PreProcessor.allSC.get(minHeapNode.getScIndex());	
				/*
				 * 1-> color the demandNode of the same color as of Service center 
				 * 2-> reduce the current capacity of service center by 1
				 * 3-> update the boundary nodes 
				 * 4-> add distance cost to global objective function
				*/
				PreProcessor.demandVertices.remove(dnIndex);
				PreProcessor.allVertices.get(dnIndex).setColor(serviceCenter.getColor());
				assignments.put(dnIndex, serviceCenter.getScIndex());
				serviceCenter.addNewAllocation(dnIndex);
				objectiveFunctionCost += minHeapNode.getDistance() - serviceCenter.getPenalty();
				totalPenalty += serviceCenter.getPenalty();
				if(serviceCenter.getPenalty()>0) {
					System.out.println(serviceCenter.getScIndex()+" full for "+dnIndex);
				}
			}
//			System.out.println(minHeapNode.toString());
			updateSCheap(minHeapNode.getScIndex());
		}
	}

	private static void updateSCheap(Integer scIndex) {
		ServiceCenter sc = PreProcessor.allSC.get(scIndex);
		int penalty = sc.getPenaltyRate();
		if(sc.minheap.size() == 0) return;
		while(!PreProcessor.demandVertices.containsKey(sc.minheap.element().getDnIndex())) {
			sc.minheap.remove();
			if(sc.minheap.size() == 0) return;
		}
		MinHeapNode forHeap = sc.minheap.remove();
		if(sc.allocations.size()+1>sc.getMaxCapacity()) {
			forHeap.increaseCost(penalty);
		}
		bestDNheap.add(forHeap); //removed from SC and added to the heap	
	}
}