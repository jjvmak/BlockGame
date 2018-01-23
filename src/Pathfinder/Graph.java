/* Author: baeldung
 * 
 * Original implementation of Dijkstra algorithm is made by baeldung.
 * Slight changes has been made to implement algorithm to this game.
 * Tutorial for this algorithm can be found here:
 * http://www.baeldung.com/java-dijkstra*/

package Pathfinder;

import java.util.HashSet;
import java.util.Set;

public class Graph {

	public Set<Node> nodes = new HashSet<>();
    
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}
 
    
}
