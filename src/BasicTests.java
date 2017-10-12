import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;


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
	@Test
	public void isTrap() {
		back.initTestingMap();
		assertTrue(back.istTrap(250, 150));
	}
	@Test
	public void isYbalanced() {
		back.setPlayerXPos(0);
		back.setPlayerYPos(0);
		back.setEnemyXPos(200);
		back.setEnemyYPos(0);
		assertTrue(back.isYbalanced());

		back.setPlayerXPos(0);
		back.setPlayerYPos(0);
		back.setEnemyXPos(200);
		back.setEnemyYPos(50);
		assertFalse(back.isYbalanced());
	}

	@Test
	public void isPlayerLower() {
		back.setPlayerXPos(0);
		back.setPlayerYPos(0);
		back.setEnemyXPos(200);
		back.setEnemyYPos(50);
		assertFalse(back.isPlayerLower());

		back.setPlayerXPos(0);
		back.setPlayerYPos(50);
		back.setEnemyXPos(200);
		back.setEnemyYPos(0);
		assertTrue(back.isPlayerLower());
	}

	@Test
	public void isPlayerFoward() {
		back.setPlayerXPos(0);
		back.setPlayerYPos(0);
		back.setEnemyXPos(200);
		back.setEnemyYPos(50);
		assertTrue(back.isPlayerFoward());

		back.setPlayerXPos(200);
		back.setPlayerYPos(0);
		back.setEnemyXPos(0);
		back.setEnemyYPos(0);
		assertFalse(back.isPlayerFoward());
	}
	@Test
	public void bestMove() {
		back.initTestingMap();
		back.setPlayerXPos(150);
		back.setPlayerYPos(200);
		back.setEnemyXPos(300);
		back.setEnemyYPos(100);
		//playerMoveDown();
		//1 up
		//2 down
		//3 right
		//4 left
		assertEquals(4, back.calculateBestMove());

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

	@Test public void balancingY() {
		back.setPlayerXPos(0);
		back.setPlayerYPos(0);
		back.enemyLogic();

		assertEquals(0, back.getEnemyYPos());

		back.moveDown();

		assertEquals(50, back.getEnemyYPos());

		back.moveUp();

		assertEquals(0, back.getEnemyYPos());

		back.initTestingMap();
		back.setEnemyXPos(250);
		back.setEnemyYPos(100);
		back.setPlayerXPos(0);
		back.setPlayerYPos(100);
		back.moveDown();
		assertEquals(100, back.getEnemyYPos());
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

}
