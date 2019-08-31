import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class ServiceCenter {

	private String serviceCenterId;
	private Integer color;
	private int scIndex;
	private int maxCapacity;
	private int penaltyRate;
	HashSet<Integer> allocations;
	public ArrayList<Float> distances = new ArrayList<Float>();
	public PriorityQueue<MinHeapNode> minheap;

	public ServiceCenter(String serviceCenterId, int scIndex, Integer color, int maxCapacity, int penaltyRate){
		this.serviceCenterId = serviceCenterId;
		this.scIndex         = scIndex;
		this.color           = color;
		this.maxCapacity     = maxCapacity;
		this.penaltyRate     = penaltyRate;
		this.allocations     = new HashSet<>();
		this.distances       = new ArrayList<>();
		this.minheap         = new PriorityQueue<MinHeapNode>(1,new Comparator<MinHeapNode>() {
			@Override
			public int compare(MinHeapNode o1, MinHeapNode o2) {
				if(o1.getDistance() == o2.getDistance()){
					return o1.getDnIndex().compareTo(o2.getDnIndex());
				} else{
					return (int) (o1.getDistance()-o2.getDistance());
				}
			}
		});
	}

	public void addNewAllocation(Integer dnIndex){
		allocations.add(dnIndex);
	}

	public HashSet<Integer> getAllocations() {
		return this.allocations;
	}

	public int getScIndex() {
		return this.scIndex;
	}
	public void setScIndex(int scIndex) {
		this.scIndex = scIndex;
	}

	public String getServiceCenterId() {
		return this.serviceCenterId;
	}

	public Integer getColor() {
		return this.color;
	}

	public int getMaxCapacity() {
		return this.maxCapacity;
	}

	public int getPenalty() {
		if(this.allocations.size()>this.maxCapacity) {
			return this.penaltyRate;		
		}
		return 0;
	}   
	
	public int getPenaltyRate() {
		return this.penaltyRate;
	}
}