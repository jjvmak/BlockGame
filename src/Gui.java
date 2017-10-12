import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
//EBIN :D
//TEST
//TEST
public class Gui {

	private JFrame frame;
	private static JTextField textField;
	private static Back back;
	private static JTextField gameOver;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(0, 0, 607, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		back = new Back();
		//back.generateRocks(30);
		back.initTestingMap();
		
		back.setBackground(Color.GRAY);
		
		frame.getContentPane().add(back);
		
		back.setLayout(null);
		
		textField = new JTextField();
		textField.setBackground(Color.GRAY);
		textField.setBounds(0, 355, 46, 46);
		back.add(textField);
		textField.setColumns(10);
		back.setMatch();
		
	}
	
	public static void appendText(String s) {
		textField.setText(s);
	}
	
	public static void gameOver() {
		gameOver = new JTextField();
		gameOver.setBounds(0, 0, 600, 400);
		gameOver.setBackground(Color.GRAY);
		gameOver.setText("Game Over");
		gameOver.setHorizontalAlignment(JTextField.CENTER);
		back.add(gameOver);
	}
}
