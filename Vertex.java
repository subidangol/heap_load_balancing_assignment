public class Vertex {

    private String  nodeID;
    private Integer color;
    private int     vertexIndex;
    
    public String getnodeID() {
		return nodeID;
	}

	public int getVertexIndex() {
		return vertexIndex;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public Vertex() {
	super();
    }

    public Vertex(String nodeID, int vertexIndex, Integer color) {
	super();
	this.nodeID = nodeID;
	this.color = color;
	this.vertexIndex = vertexIndex;
    }

}