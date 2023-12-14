package ai;

import java.util.ArrayList;

import main.GamePanel;

public class PathFinder {
	
	GamePanel gamePanel;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	
	public PathFinder(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		instantiateNodes();
	}
	
	public void instantiateNodes() {
		
		node = new Node[gamePanel.maxWorldColumn][gamePanel.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while(col < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow) {
			
			node[col][row] = new Node(col,row);
			
			col++;
			
			if(col == gamePanel.maxWorldColumn) {
				col = 0;
				row++;
			}
			
		}
		
	}
	
	//reset nodes for future use
	public void resetNodes() {
		
		int col = 0;
		int row = 0;
		
		while(col < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow) {
			
			//reset node state
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			if(col == gamePanel.maxWorldColumn) {
				col = 0;
				row++;
			}
			
		}
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		
	}
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		
		resetNodes();
		
		//set start and goal nodes
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while(col < gamePanel.maxWorldColumn && row < gamePanel.maxWorldRow) {
			
			int tileNum = gamePanel.tileManager.mapTileIndex[col][row];
			if(gamePanel.tileManager.tile[tileNum].collision == true) {
				node[col][row].solid = true;
			}
		
			getCost(node[col][row]);
			
			col++;
			if(col == gamePanel.maxWorldColumn) {
				col = 0;
				row++;
			}
		}
		
	}
	
	public void getCost(Node node) {
		
		//get gCost
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		//get hCost
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		//get fCost
		node.fCost = node.gCost + node.hCost;
		
	}
	
	public boolean search() {
		
		while(goalReached == false) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//open up node;
			if(row - 1 >= 0) {
				openNode(node[col][row - 1]);
			}
			
			//open left node;
			if(col - 1 >= 0) {
				openNode(node[col - 1][row]);
			}
			
			//open down node
			if(row + 1 < gamePanel.maxWorldRow) {
				openNode(node[col][row + 1]);
			}
			
			//open right node
			if(col + 1 < gamePanel.maxWorldColumn) {
				openNode(node[col + 1][row]);
			}
			
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for(int i = 0; i < openList.size(); i++) {
				
				//check if node fCost is less than bestNodefCost;
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodefCost = i;
				}
				
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
				
			}
			
			//If there is no node in the list break
			if(openList.size() == 0) {
				break;
			}
			
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
		}
		
		return goalReached;
	}
	
	public void trackThePath() {
		
		Node current = goalNode;
		
		while(current != startNode) {
			
			pathList.add(0, current);
			current = current.parent;
			
		}
	}

	public void openNode(Node node) {
		
		if(node.open == false && node.checked == false && node.solid == false) {
			
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
			
		}
		
	}
	
}
