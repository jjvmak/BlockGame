import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import Pathfinder.Graph;
import Pathfinder.Node;
import Pathfinder.Shortestpath;

public class Back extends JPanel implements ActionListener, KeyListener {

	private int playerXPos = 0;
	private int playerYPos = 0;
	private Timer timer;
	private int enemyXPos = 550;
	private int enemyYPos = 0;
	public ArrayList<Rock> rocks = new ArrayList<>();
	private int matchNumber = 1;
	private boolean shouldRender = true;
	private Graph graph;
	private ArrayList<Node> nodes;

	public Back() {

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(50, this);
		timer.start();

	}

	public void initTestingMap() {
	
		rocks.add(new Rock(50, 50));
		rocks.add(new Rock(50, 100));
		rocks.add(new Rock(100, 300));
		rocks.add(new Rock(200, 200));
		rocks.add(new Rock(250, 200));
		rocks.add(new Rock(300, 200));
		rocks.add(new Rock(300, 250));
		rocks.add(new Rock(200, 250));
		//trap at 250,250
		rocks.add(new Rock(200, 150));
		rocks.add(new Rock(300, 150));
		//trap at 250, 150

		//setPlayerXPos(150);
		//setPlayerYPos(200);
		//setEnemyXPos(300);
		//setEnemyYPos(100);

	}

	public void paint(Graphics g) {

		if (shouldRender) {

			int das = 0;
			super.paint(g);

			for (int i = 0; i < 10; i++) {
				g.setColor(Color.BLUE);
				g.fillRect(550, das, 50, 50);
				das += 50;
			}

			for (int i = 0; i < rocks.size(); i++) {
				g.setColor(Color.GREEN);
				g.fillRect(rocks.get(i).getxPos(), rocks.get(i).getyPos(), 50, 50);
			}

			g.setColor(Color.black);
			g.fillRect(playerXPos, playerYPos, 50, 50);

			g.setColor(Color.WHITE);
			g.fillRect(enemyXPos, enemyYPos, 50, 50);

			g.dispose();
		}

		else {
			super.paint(g);
			g.dispose();
		}

		checkEndRule();
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight(); 

		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft();
		}

		if (e.getKeyCode() == KeyEvent.VK_W) {
			moveUp();
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			moveDown();
		}


	}

	public void moveRight() {
		if (playerXPos < 550 && isPossibleMove(playerXPos+50, playerYPos)) {
			playerXPos += 50;
			nextMove();
		}
	}

	public void moveLeft() {
		if (playerXPos > 0 && isPossibleMove(playerXPos-50, playerYPos)) {
			playerXPos -= 50;
			nextMove();
		}
	}

	public void moveUp() {
		if (playerYPos > 0 && isPossibleMove(playerXPos, playerYPos-50)) {
			playerYPos -= 50;
			nextMove();
		}
	}

	public void moveDown() {
		if (playerYPos < 350 && isPossibleMove(playerXPos, playerYPos+50)) {
			playerYPos += 50;
			nextMove();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		repaint();
	}

	public void generateRocks(int n) {
		int amount = n;
		for (int i = 0; i < amount; i++) {
			rocks.add(new Rock());
		}
		makeGraph();
	}

	public void checkEndRule() {

		int xEnd = Math.abs(playerXPos-enemyXPos);
		int yEnd = Math.abs(playerYPos-enemyYPos);

		if (xEnd <= 50 && yEnd <= 50) {
			Gui.gameOver();
			shouldRender = false;
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (playerXPos == 550) {
			matchNumber++;
			playerXPos = 0;
			playerYPos = 0;
			enemyXPos = 550;
			enemyYPos = 0;
			rocks.removeAll(rocks);
			generateRocks(20 - matchNumber);
			Gui.appendText(""+matchNumber);
		}
	}

	public boolean isPossibleMove(int xPos, int yPos) {
		for (int i = 0; i < rocks.size(); i++) {
			if (xPos == rocks.get(i).getxPos() &&  yPos ==  rocks.get(i).getyPos()) {
				return false;
			}
		}
		return true;
	}

	public void setMatch() {
		Gui.appendText(""+matchNumber);

	}

	public int getPlayerXPos() {
		return playerXPos;
	}

	public int getPlayerYPos() {
		return playerYPos;
	}

	public int getEnemyXPos() {
		return enemyXPos;
	}

	public int getEnemyYPos() {
		return enemyYPos;
	}

	public void setPlayerXPos(int playerXPos) {
		this.playerXPos = playerXPos;
	}

	public void setPlayerYPos(int playerYPos) {
		this.playerYPos = playerYPos;
	}

	public void setEnemyXPos(int enemyXPos) {
		this.enemyXPos = enemyXPos;
	}

	public void setEnemyYPos(int enemyYPos) {
		this.enemyYPos = enemyYPos;
	}

	public void makeGraph() {
		graph = new Graph();
		nodes = new ArrayList<Node>();
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

			if(!isPossibleMove(x, y)) {
				continue;
			}

			for (int j = 0; j < nodes.size(); j++) {

				if (!isPossibleMove(nodes.get(j).getx(), nodes.get(j).gety())) {
					continue;
				}

				if (nodes.get(j).getx() == x && nodes.get(j).gety() == y+50 ||
						nodes.get(j).getx() == x+50 && nodes.get(j).gety() == y ||
						nodes.get(j).getx() == x && nodes.get(j).gety() == y-50 ||
						nodes.get(j).getx() == x-50 && nodes.get(j).gety() == y){

					nodes.get(i).addDestination(nodes.get(j), 1);
					graph.addNode(nodes.get(i));
					// System.out.println("node "+nodes.get(i)+" is neighboring "+nodes.get(j));
				}
			}
		}
	}
	
	public void nextMove() {
		makeGraph();
		Node target = null;
	
		for (int t = 0; t < nodes.size(); t++) {
			if (nodes.get(t).getx() == playerXPos && nodes.get(t).gety() == playerYPos) {
				 target = nodes.get(t);
				 System.out.println("MY TARGET IS: " +playerXPos+","+playerYPos);
			}
		}
		
		graph = Shortestpath.calculateShortestPathFromSource(graph, target);
		Set<Node> n = graph.getNodes();
		
		for (Node i : n) {
			
				if (i.getx() == enemyXPos && i.gety() == enemyYPos) {
					System.out.println(i.getShortestPath());
					System.out.println("MOVE TO: "+ i.getShortestPath().get(i.getShortestPath().size()-1));
					Node tmp = i.getShortestPath().get(i.getShortestPath().size()-1);
					int x = tmp.getx();
					int y = tmp.gety();
					setEnemyXPos(x);
					setEnemyYPos(y);
					break;
				}
			} 
	}

}
