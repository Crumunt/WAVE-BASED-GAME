package ai;

public class Node {
	
	Node parent;
	public int col;
	public int row;
	int gCost;
	int hCost;
	int fCost;
	public boolean solid;
	public boolean open;
	public boolean checked;
	public boolean goalReached;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
}
