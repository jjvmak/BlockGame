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
	private HashMap<Integer, Rock> hs = new HashMap();
	private int enemyXPos = 550;
	private int enemyYPos = 0;

	public ArrayList<Rock> rocks = new ArrayList<>();
	private Random rnd = new Random();



	private boolean didMoveAlready;

	private int matchNumber = 1;

	private boolean shouldRender = true;

	public Back() {
		generateRocks(30);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(50, this);
		timer.start();
		
	}

	public void paint(Graphics g) {

		if (shouldRender) {
				
			int das = 0;
			super.paint(g);

			for (int i = 0; i < 10; i++) {
				g.setColor(Color.BLUE);
				g.fillRect(551, das, 50, 50);
				das += 50;
			}
			
			for (int x = 0; x < 550; x += 50) {
				for (int y = 0; y < 400; y += 50) {
					System.out.println(x+", "+y);
					if (isTrap(x, y)) {
						g.setColor(Color.RED);
					
						g.fillRect(x, y, 50, 50);
					}
				}
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
			if (playerXPos < 501 && isPossibleMoveRight(playerXPos, playerYPos)) {
				playerXPos += 50;
				enemyLogic();

			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (playerXPos > 0 && isPossibleMoveLeft(playerXPos, playerYPos)) {
				playerXPos -= 50;
				enemyLogic();

			}
		}

		if (e.getKeyCode() == KeyEvent.VK_W) {
			if (playerYPos > 0 && isPossibleMoveUp(playerXPos, playerYPos)) {
				playerYPos -= 50;
				enemyLogic();

			}
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (playerYPos < 301 && isPossibleMoveDown(playerXPos, playerYPos)) {
				playerYPos += 50;
				enemyLogic();
			}
		}


		checkEndRule();

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
			//Rock temp = new Rock();
			//hs.put(temp.hashCode(), temp);
		}
		deBugRocks();
	}

	public void checkEndRule() {
		int tempHash = hashCode(playerXPos, playerYPos);
		System.out.println(playerXPos+", "+playerYPos+", "+enemyXPos+", "+enemyYPos+",    "+tempHash);
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
		didMoveAlready = false;

		if ((!isYbalanced() && isPossibleMoveDown(enemyXPos, enemyXPos)) && (!isYbalanced() && isPossibleMoveUp(enemyXPos, enemyYPos))) {
			System.out.println("YOLO");
			
			if(enemyYPos < playerYPos && isPossibleMoveDown(enemyXPos, enemyYPos) && !isTrap(enemyXPos, enemyYPos + 50)) {
				enemyYPos += 50;
				didMoveAlready = true;
			}
			else if(enemyYPos > playerYPos && isPossibleMoveUp(enemyXPos, enemyYPos) && !isTrap(enemyXPos, enemyYPos - 50)) {
				enemyYPos -= 50;
				didMoveAlready = true;
			}
		}
		if ((isYbalanced() && isPossibleMoveRight(enemyXPos, enemyYPos) && !didMoveAlready) && (isYbalanced() && isPossibleMoveLeft(enemyXPos, enemyYPos) && !didMoveAlready)) {
			
			System.out.println("PITS");
			if (playerXPos < enemyXPos && isPossibleMoveLeft(enemyXPos, enemyYPos) && !isTrap(enemyXPos - 50, enemyYPos)) {
				enemyXPos -= 50;
				didMoveAlready = true;
			}

			else if (playerXPos > enemyXPos && isPossibleMoveRight(enemyXPos, enemyYPos) && !isTrap(enemyXPos + 50, enemyYPos)) {	
				enemyXPos += 50;
				didMoveAlready = true;
			}
		}
		if ((!isYbalanced() && isPossibleMoveRight(enemyXPos, enemyYPos) && !didMoveAlready) && (!isYbalanced() && isPossibleMoveLeft(enemyXPos, enemyYPos) && !didMoveAlready)) {
			
			System.out.println("MATAFAKA");
			if (playerXPos < enemyXPos && isPossibleMoveLeft(enemyXPos, enemyYPos) && !isTrap(enemyXPos - 50, enemyYPos)) {
				enemyXPos -= 50;
				didMoveAlready = true;
			}


			else if (playerXPos > enemyXPos && isPossibleMoveRight(enemyXPos, enemyYPos) && !isTrap(enemyXPos + 50, enemyYPos)) {	
				enemyXPos += 50;
				didMoveAlready = true;
			}
		}

		if (!didMoveAlready) {
			System.out.println("WAAAAAAAAAAAAAAAAAAAAAA");
			int attemp = 0;
			while (!didMoveAlready) {

				Random rnd = new Random();
				int yolotus = rnd.nextInt(4);
				System.out.println(attemp);



				if ((isPossibleMoveLeft(enemyXPos, enemyYPos)) && yolotus < 2 && !isTrap(enemyXPos - 50, enemyYPos)) {
					enemyXPos -= 50;
					didMoveAlready = true;
				}

//				else if((isPossibleMoveDown(enemyXPos, enemyYPos)) && yolotus < 2 && !isTrap(enemyXPos, enemyYPos + 50)) {
//					enemyYPos += 50;
//					didMoveAlready = true;
//				}
//				else if((isPossibleMoveUp(enemyXPos, enemyYPos)) && yolotus == 3 && !isTrap(enemyXPos , enemyYPos - 50)) {
//					enemyYPos -= 50;
//					didMoveAlready = true;
//				}

				else if ((isPossibleMoveRight(enemyXPos, enemyYPos)) && yolotus > 2 && !isTrap(enemyXPos + 50, enemyYPos)) {	
					enemyXPos += 50;
					didMoveAlready = true;
				}

				else if (attemp > 10) {
					System.out.println("----------------------------------");
					break;
				}

				attemp++;
			}


		}
	}

	private boolean isYbalanced() {
		if (playerYPos == enemyYPos) return true;
		return false;
	}

		public boolean isPossibleMoveRight(int xPos, int yPos) {
			for (int i = 0; i < rocks.size(); i++) {
				if (xPos == rocks.get(i).getxPos()-50 && yPos ==  rocks.get(i).getyPos()) {
					return false;
				} 
			}
			return true;
		}


	public boolean isPossibleMoveLeft(int xPos, int yPos) {
		for (int i = 0; i < rocks.size(); i++) {
			if (xPos == rocks.get(i).getxPos()+50 && yPos ==  rocks.get(i).getyPos()) {
				return false;
			} 
		}
		return true;
	}

	public boolean isPossibleMoveUp(int xPos, int yPos) {
		for (int i = 0; i < rocks.size(); i++) {
			if (xPos == rocks.get(i).getxPos() && yPos ==  rocks.get(i).getyPos()+50) {
				return false;
			} 
		}
		return true;
	}

	public boolean isPossibleMoveDown(int xPos, int yPos) {
		for (int i = 0; i < rocks.size(); i++) {
			if (xPos == rocks.get(i).getxPos() && yPos ==  rocks.get(i).getyPos()-50) {
				return false;
			} 
		}
		return true;
	}
	
	public boolean isTrap(int xPos, int yPos) {
		int possibleMoves = 0;
		if (isPossibleMoveRight(xPos, yPos)) possibleMoves++;
		if (isPossibleMoveLeft(xPos, yPos)) possibleMoves++;
		if (isPossibleMoveDown(xPos, yPos)) possibleMoves++;
		if (isPossibleMoveUp(xPos, yPos)) possibleMoves++;
		
		if (possibleMoves <= 1) return true;
		return false;
	}

	public void setMatch() {
		Gui.appendText(""+matchNumber);

	}

	public int hashCode(int xPos, int yPos) {
		final int prime = 5;
		int result = 1;
		result = prime * result + xPos;
		result = prime * result + yPos;
		return result;
	}
	
	public void deBugRocks() {
		
	}
	
	



}
