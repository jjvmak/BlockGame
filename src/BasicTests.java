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
	
	public void isTrap() {
		back.initTestingMap();
		assertTrue(back.istTrap(250, 150));
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

}
