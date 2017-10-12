import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
		//rocks.add(new Rock(250, 150));
		
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

		checkEndRule();
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

		if (playerXPos == enemyXPos && playerYPos == enemyYPos) {
			Gui.gameOver();
			shouldRender = false;
			repaint();
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

	}

	private boolean isYbalanced() {
		if (playerYPos == enemyYPos) return true;
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

	public boolean istTrap(int x, int y) {
		
		int surrounding = 0;
		System.out.println(x +", "+y);
		
		if (!isPossibleMove(x+50, y)) surrounding++;
		if (!isPossibleMove(x-50, y)) surrounding++;
		if (!isPossibleMove(x, y+50)) surrounding++;
		if (!isPossibleMove(x, y-50)) surrounding++;
		
		System.out.println(surrounding);
		
		if (surrounding > 2) {
			return true;
		} 
		else {
			return false;
		}
		
	}
}
