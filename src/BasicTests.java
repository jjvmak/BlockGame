import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import Pathfinder.Graph;
import Pathfinder.Node;
import Pathfinder.Shortestpath;

public class BasicTests {

	private Gui gui;
	private Back back;

	@Before
	public void init() {
		gui = new Gui();
		back = new Back();
	}

	//LOGIC
	@Test
	public void isPossibleMove() {
		back.initTestingMap();
		assertTrue(back.isPossibleMove(250, 150));
		assertFalse(back.isPossibleMove(250, 200));

	}


	//PLAYER TESTS
	@Test
	public void playerStartingPos() {
		back.initTestingMap();
		assertEquals(0, back.getPlayerXPos());
		assertEquals(0, back.getPlayerYPos());
	}

	@Test
	public void playerMoveRight() {
		back.initTestingMap();
		back.moveRight();	
		assertEquals(50, back.getPlayerXPos());
		assertEquals(0, back.getPlayerYPos());

	}

	@Test
	public void playerMoveLeft() {
		back.initTestingMap();
		back.setPlayerXPos(50);
		back.moveLeft();	
		assertEquals(0, back.getPlayerXPos());
		assertEquals(0, back.getPlayerYPos());

	}

	@Test
	public void playerMoveDown() {
		back.initTestingMap();
		back.moveDown();	
		assertEquals(0, back.getPlayerXPos());
		assertEquals(50, back.getPlayerYPos());

	}

	@Test
	public void playerMoveUp() {
		back.initTestingMap();
		back.setPlayerYPos(50);
		back.moveUp();	
		assertEquals(0, back.getPlayerXPos());
		assertEquals(0, back.getPlayerYPos());

	}

	//ENEMY TESTS
	@Test
	public void enemyStartingPos() {
		back.initTestingMap();
		assertEquals(550, back.getEnemyXPos());
		assertEquals(0, back.getEnemyYPos());
	}

	@Test
	public void movingTowardsPlayer() {
		//IF PATH IS NOT BLOCKED!
		back.moveRight();
		assertEquals(500, back.getEnemyXPos());
		back.moveRight();
		assertEquals(450, back.getEnemyXPos());

		back.setPlayerXPos(200);
		back.setEnemyXPos(0);
		back.moveRight();
		assertEquals(50, back.getEnemyXPos());
		back.moveRight();
		assertEquals(100, back.getEnemyXPos());
	}
	
	@Test
	public void shortestPath() {
		back.initTestingMap();
		Graph graph = new Graph();
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int x = 0; x <= 550; x += 50) {
			for (int y = 0; y <= 350; y += 50) {
				//System.out.println(x + " "+y);
				Node node = new Node(x, y);
				nodes.add(node);
			}
		}
		
		for (int i = 0; i < nodes.size(); i++) {
			
			int x = nodes.get(i).getx();
			int y = nodes.get(i).gety();
			
			if(!back.isPossibleMove(x, y)) {
				continue;
			}
			
			for (int j = 0; j < nodes.size(); j++) {
				
				if (!back.isPossibleMove(nodes.get(j).getx(), nodes.get(j).gety())) {
					continue;
				}
				
				if (nodes.get(j).getx() == x && nodes.get(j).gety() == y+50 ||
				    nodes.get(j).getx() == x+50 && nodes.get(j).gety() == y ||
				    nodes.get(j).getx() == x && nodes.get(j).gety() == y-50 ||
				    nodes.get(j).getx() == x-50 && nodes.get(j).gety() == y){
					
					nodes.get(i).addDestination(nodes.get(j), 1);
					graph.addNode(nodes.get(i));
					//System.out.println("node "+nodes.get(i)+" is neighboring "+nodes.get(j));
				}
				
			}
		}
		Node target = null;
		for (int t = 0; t < nodes.size(); t++) {
			if (nodes.get(t).getx() == 550 && nodes.get(t).gety() == 350) {
				 target = nodes.get(t);
			}
		}
		
		graph = Shortestpath.calculateShortestPathFromSource(graph, target);
		Set<Node> n = graph.getNodes();
		// System.out.println(n);
		for (Node i : n) {
		 //	System.out.println(i + " "+ i.getShortestPath());
			if (i.getx() == 0 && i.gety() == 50) {
				System.out.println(i + " "+ i.getShortestPath().get(i.getShortestPath().size()-1));
			}
		} 
	}
	
	
}
