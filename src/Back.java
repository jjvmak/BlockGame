import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Back extends JPanel implements ActionListener, KeyListener {

	private int playerXPos = 0;
	private int playerYPos = 0;
	private Timer timer;
	private int enemyXPos = 550;
	private int enemyYPos = 0;

	public ArrayList<Rock> rocks = new ArrayList<>();
	private Random rnd = new Random();

	private boolean didMoveAlready;

	private int matchNumber = 1;

	private boolean shouldRender = true;

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
			enemyLogic();

		}
	}

	public void moveLeft() {
		if (playerXPos > 0 && isPossibleMove(playerXPos-50, playerYPos)) {
			playerXPos -= 50;
			enemyLogic();

		}
	}

	public void moveUp() {
		if (playerYPos > 0 && isPossibleMove(playerXPos, playerYPos-50)) {
			playerYPos -= 50;
			enemyLogic();

		}
	}

	public void moveDown() {
		if (playerYPos < 350 && isPossibleMove(playerXPos, playerYPos+50)) {
			playerYPos += 50;
			enemyLogic();
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
	}

	public void checkEndRule() {

		int xEnd = Math.abs(playerXPos-enemyXPos);
		int yEnd = Math.abs(playerYPos-enemyYPos);

		if (xEnd <= 0 && yEnd <= 0) {
			repaint();
			Gui.gameOver();

			repaint();
			shouldRender = false;
			try {
				Thread.sleep(500);
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

	public void enemyLogic() {

		if (playerXPos == enemyXPos && playerYPos == enemyYPos) {
			checkEndRule();
		}

		/* If y-coordinate is not balanced, enemy should always first try to move
		 * inline with the player, before moving left or right,
		 * if there is no rock on the path, or the move 
		 * would cause enemy to step in trap.
		 */
		if (!isYbalanced()) {
			if (isPlayerLower() && isPossibleMove(enemyXPos, enemyYPos+50) && !istTrap(enemyXPos, enemyYPos+50)) {
				setEnemyYPos(enemyYPos + 50);
				return;
			}
			if (!isPlayerLower() && isPossibleMove(enemyXPos, enemyYPos-50) && !istTrap(enemyXPos, enemyYPos-50)) {
				setEnemyYPos(enemyYPos - 50);
				return;
			}
		}

		/* If y-coordinate is balanced, enemy should try to move towards player,
		 * if there is no rock blocking path or next move is a trap.
		 */
		if (isYbalanced()) {
			if (isPlayerFoward() && isPossibleMove(enemyXPos-50, enemyYPos) && !istTrap(enemyXPos-50, enemyYPos)) {
				setEnemyXPos(enemyXPos - 50);
				return;
			}
			if (!isPlayerFoward() && isPossibleMove(enemyXPos+50, enemyYPos) && !istTrap(enemyXPos+50, enemyYPos)) {
				setEnemyXPos(enemyXPos + 50);
				return;
			}
		}


		/*
		 * If all above fails, then try to calculate best possible move
		 * 
		 */
		//1 up
		//2 down
		//3 right
		//4 left
		int direction = calculateBestMove();
		
		switch (direction) {
		case 1:
			setEnemyYPos(enemyYPos-50);
			break;
		
		case 2:
			setEnemyYPos(enemyYPos+50);
			break;
		
		case 3:
			setEnemyXPos(enemyXPos+50);
			break;
		
		case 4:
			setEnemyXPos(enemyXPos-50);
			break;
		}
		
	}

	public boolean isPlayerLower() {
		if (playerYPos < enemyYPos) return false;
		return true;
	}

	public boolean isYbalanced() {
		if (playerYPos == enemyYPos) return true;
		return false;
	}

	public boolean isPlayerFoward() {
		if (playerXPos <= enemyXPos) return true;
		return false;
	}

	public boolean isKillPossible() {
		int xEnd = Math.abs(playerXPos-enemyXPos);
		int yEnd = Math.abs(playerYPos-enemyYPos);

		if (xEnd <= 0 && yEnd <= 0 && isPossibleMove(playerXPos, playerYPos)) {
			return true;
		}

		return false;
	}

	public boolean isPossibleMove(int xPos, int yPos) {
		for (int i = 0; i < rocks.size(); i++) {
			if (xPos == rocks.get(i).getxPos() &&  yPos ==  rocks.get(i).getyPos()) {
				return false;
			}
		}
		return true;
	}

	public boolean istTrap(int x, int y) {

		int surrounding = 0;

		if (!isPossibleMove(x+50, y)) surrounding++;
		if (!isPossibleMove(x-50, y)) surrounding++;
		if (!isPossibleMove(x, y+50)) surrounding++;
		if (!isPossibleMove(x, y-50)) surrounding++;

		if (surrounding > 2) {
			return true;
		} 
		else {
			return false;
		}

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


	public int calculateBestMove() {
		int diffUp = 9001;
		int diffDown = 9001;
		int diffRight = 9001;
		int diffLeft = 9001;

		//1 up
		//2 down
		//3 right
		//4 left
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		//UP
		if (isPossibleMove(enemyXPos, enemyYPos-50) && !istTrap(enemyXPos, enemyYPos-50)) {
			diffUp = Math.abs(playerXPos - enemyXPos) + Math.abs(playerYPos - (enemyYPos - 50));

		}
		//DOWN
		if (isPossibleMove(enemyXPos, enemyYPos+50) && !istTrap(enemyXPos, enemyYPos+50)) {
			diffDown = Math.abs(playerXPos - enemyXPos) + Math.abs(playerYPos - (enemyYPos + 50));

		}
		//RIGHT
		if (isPossibleMove(enemyXPos+50, enemyYPos) && !istTrap(enemyXPos+50, enemyYPos)) {
			diffRight = Math.abs(playerXPos - enemyXPos) + Math.abs((playerYPos+50) - enemyYPos);

		}
		//LEFT
		if (isPossibleMove(enemyXPos-50, enemyYPos) && !istTrap(enemyXPos-50, enemyYPos)) {
			diffLeft = Math.abs(playerXPos - enemyXPos) + Math.abs((playerYPos-50) - enemyYPos);

		}
		map.put(1, diffUp);
		map.put(2, diffDown);
		map.put(3, diffRight);
		map.put(4, diffLeft);

		int currentBestValue = map.get(1);
		System.out.println("init bestvalue from key 1 = " + map.get(1));
		int currentBestKey = 1;
		System.out.println("init bestkey = 1");

		for (int i = 2; i < 5; i++) {
			System.out.println("checking key "+i+". value = "+map.get(i));
			System.out.println("comparing currentvalue:" + currentBestValue +" and next value: "+ map.get(i) );
			if (currentBestValue > map.get(i)) {
				currentBestValue = map.get(i);
				System.out.println("bestvalue updated: "+map.get(i));
				currentBestKey = i;
				System.out.println("bestkey updated: "+i);
			}
		}
		System.out.println("returning key: "+currentBestKey);
		return currentBestKey;
	}


}
