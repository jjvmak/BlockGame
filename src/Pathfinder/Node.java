/* Author: baeldung
 * 
 * Original implementation of Dijkstra algorithm is made by baeldung.
 * Slight changes has been made to implement algorithm to this game.
 * Tutorial for this algorithm can be found here:
 * http://www.baeldung.com/java-dijkstra*/

package Pathfinder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
	
	private int x;
	private int y;
    
    private List<Node> shortestPath = new LinkedList<>();
     
    private Integer distance = Integer.MAX_VALUE;
     
    Map<Node, Integer> adjacentNodes = new HashMap<>();
 
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
  
    public Node(int x, int y) {
       this.x = x;
       this.y = y;
    		 
    }

	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}

	public List<Node> getShortestPath() {
		return shortestPath;
	}

	public Integer getDistance() {
		return distance;
	}

	public Map<Node, Integer> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}

	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + "]";
	}
     
    // getters and setters

}
