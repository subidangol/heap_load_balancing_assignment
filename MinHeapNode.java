public class MinHeapNode {
	//stores the minimum distance of demandNode to particular Service center
	private int distance;
	//stores demand Node id
    private Integer dnIndex;
    //stores service center id of service center from which distance of demand node is minimum
    private Integer scIndex;
    
	public MinHeapNode(int distanceOfDemandNodeToR, Integer dnIndex, Integer scID) {
		super();
		this.distance = distanceOfDemandNodeToR;
		this.dnIndex = dnIndex;
		this.scIndex = scID;
	}
	
	@Override
	public String toString() {
		return "("+dnIndex+" to SC "+scIndex+"="+distance+")";
	}

	public int getDistance() {
		return distance;
	}
	
	public Integer getDnIndex() {
		return dnIndex;
	}
	
	public Integer getScIndex() {
		return scIndex;
	}

	public void increaseCost(int penalty) {
		// TODO Auto-generated method stub
		this.distance+=penalty;
	}

}