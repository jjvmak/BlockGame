import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
	public Map<String, Rock> rocks = new HashMap<String, Rock>();
	private int matchNumber = 1;
	private boolean shouldRender = true;
	private Graph graph;
	private Map<String, Node> nodes = new HashMap<String, Node>();

	public Back() {

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(50, this);
		timer.start();

	}

	public void initTestingMap() {
		// TODO: fix
//		rocks.add(new Rock(50, 50));
//		rocks.add(new Rock(50, 100));
//		rocks.add(new Rock(100, 300));
//		rocks.add(new Rock(200, 200));
//		rocks.add(new Rock(250, 200));
//		rocks.add(new Rock(300, 200));
//		rocks.add(new Rock(300, 250));
//		rocks.add(new Rock(200, 250));
		// trap at 250,250
//		rocks.add(new Rock(200, 150));
//		rocks.add(new Rock(300, 150));
		// trap at 250, 150

		// setPlayerXPos(150);
		// setPlayerYPos(200);
		// setEnemyXPos(300);
		// setEnemyYPos(100);

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

			rocks.forEach((key, value) -> {
				g.setColor(Color.GREEN);
				g.fillRect(value.getxPos(), value.getyPos(), 50, 50);
			});

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
		if (playerXPos < 550 && isPossibleMove(playerXPos + 50, playerYPos)) {
			playerXPos += 50;
			nextMove();
		}
	}

	public void moveLeft() {
		if (playerXPos > 0 && isPossibleMove(playerXPos - 50, playerYPos)) {
			playerXPos -= 50;
			nextMove();
		}
	}

	public void moveUp() {
		if (playerYPos > 0 && isPossibleMove(playerXPos, playerYPos - 50)) {
			playerYPos -= 50;
			nextMove();
		}
	}

	public void moveDown() {
		if (playerYPos < 350 && isPossibleMove(playerXPos, playerYPos + 50)) {
			playerYPos += 50;
			nextMove();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		repaint();
	}

	public void generateRocks(int n) {
		int amount = n;
		for (int i = 0; i < amount; i++) {
			Rock rock = new Rock();
			String key = rock.getxPos() + "x" + rock.getyPos() + "y";
			rocks.put(key, rock);
		}
		makeGraph();
	}

	public void checkEndRule() {

		int xEnd = Math.abs(playerXPos - enemyXPos);
		int yEnd = Math.abs(playerYPos - enemyYPos);

		if (xEnd <= 50 && yEnd <= 50) {
			Gui.gameOver();
			shouldRender = false;
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (playerXPos == 550) {
			matchNumber++;
			playerXPos = 0;
			playerYPos = 0;
			enemyXPos = 550;
			enemyYPos = 0;
			rocks = new HashMap<String, Rock>();
			generateRocks(20 - matchNumber);
			Gui.appendText("" + matchNumber);
		}
	}

	public boolean isPossibleMove(int xPos, int yPos) {
		String location = xPos + "x" + yPos + "y";
		if (rocks.containsKey(location))
			return false;
		return true;
	}

	public void setMatch() {
		Gui.appendText("" + matchNumber);

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
		nodes = new HashMap<String, Node>();
		for (int x = 0; x <= 550; x += 50) {
			for (int y = 0; y <= 350; y += 50) {
				Node node = new Node(x, y);
				String key = node.getx() + "x" + node.gety() + "y";
				nodes.put(key, node);
			}
		}

		nodes.forEach((key, node) -> {

			int x = node.getx();
			int y = node.gety();

			if (!isPossibleMove(x, y)) {
				return;
			}

			Map<String, Node> neighbouring = nodes.entrySet().stream()
					.filter(neigh -> (neigh.getValue().getx() == x && neigh.getValue().gety() == y + 50
							|| neigh.getValue().getx() == x + 50 && neigh.getValue().gety() == y
							|| neigh.getValue().getx() == x && neigh.getValue().gety() == y - 50
							|| neigh.getValue().getx() == x - 50 && neigh.getValue().gety() == y)
							&& isPossibleMove(neigh.getValue().getx(), neigh.getValue().gety()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

			neighbouring.forEach((k, val) -> {
				node.addDestination(val, 1);
			});
			graph.addNode(node);
		});
	}

	public void nextMove() {
		makeGraph();
		Node target = nodes.get(playerXPos + "x" + playerYPos + "y");
		graph = Shortestpath.calculateShortestPathFromSource(graph, target);
		Set<Node> n = graph.getNodes();
		Optional<Node> nodeOpt = n.stream().filter(nod -> (nod.getx() == enemyXPos && nod.gety() == enemyYPos))
				.findFirst();
		Node node = nodeOpt.get();
		Node tmp = node.getShortestPath().get(node.getShortestPath().size()-1);
		setEnemyXPos(tmp.getx());
		setEnemyYPos(tmp.gety());
	}
}
