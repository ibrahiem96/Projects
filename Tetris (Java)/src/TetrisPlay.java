import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * 
 * TETRIS
 * Version 1.0.2
 * UIC CS342 Spring 2016
 * 
 * Ibrahiem Mohammad
 * Anas Ahmad
 * 
 * 
 * The TetrisPlay class serves as the event handler for the main frame.
 * It initializes the frame, sets up the controls and the menu, and the
 * other parts of the GUI. It then initializes itself as an object in the
 * main method which calls the constructor--which then starts the game.
 * 
 * This class accesses itself and the GamePlay class only.
 * 
 * @author Ibrahiem Mohammad
 *
 */

public class TetrisPlay extends JFrame implements ActionListener{
	

	public static void main (String args[]){

		//initialize new game
		TetrisPlay game = new TetrisPlay();

	}

	//TODO: score updater [kind of done]
	//TODO: timer update after 10 lines cleared 
	//TODO: design pattern??

	//game object
	private GamePlay g;

	//menu bar + menu items
	private JMenuBar mb;
	private JMenu m;

	private JMenuItem quit = new JMenuItem("Quit");
	private JMenuItem about = new JMenuItem("About");
	private JMenuItem help = new JMenuItem("Help");
	private JMenuItem restart = new JMenuItem("Start New Game");

	// labels
	private JLabel scoreDetails;
	private JLabel statusDetails;

	//game constructor
	public TetrisPlay() {

		//create and add label
		scoreDetails = new JLabel("  0"); add(scoreDetails, BorderLayout.NORTH);
		statusDetails = new JLabel(); add(statusDetails, BorderLayout.SOUTH);

		//init menubar and menu
		mb = new JMenuBar();
		m = new JMenu("Game Options");

		mb.add(m);
		setJMenuBar(mb);

		quit.addActionListener(this);
		help.addActionListener(this);
		about.addActionListener(this);
		restart.addActionListener(this);

		//add menu options
		m.add(quit);
		m.add(help);
		m.add(about);
		m.add(restart);


		//initialize new gameplay object
		//start game
		g = new GamePlay(this); add(g);
		g.begin();

		//set board dimensions and title
		setSize (400, 500);
		setTitle("TETRIS");
		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * Menu bar handler
	 * 
	 * - handles the events for all the menu options
	 */
	public void actionPerformed (ActionEvent e){

		if (e.getSource() == quit) this.dispose();
		if (e.getSource() == about) {
			g.pause();
			JOptionPane.showMessageDialog(this,
					"This tetris game was developed by Ibrahiem Mohammad\n"
							+ "and Anas Ahmad for CS342 in the Spring 2016 Semester at UIC\n"
							+ "Version: 1.0.2", "ABOUT", JOptionPane.PLAIN_MESSAGE);
		}
		if (e.getSource() == restart) g.begin();
		if (e.getSource() == help) {
			g.pause();
			JOptionPane.showMessageDialog(this, 
					"The instructions on playing this game are as follows: \n"
							+ "OBJECTIVES:\n"
							+ "The objectives of this game are to stack the falling\n"
							+ "pieces on top of each other such that they fill in the\n"
							+ "lines at the bottom. Once the pieces have all filled up\n"
							+ "the grid to the top, the game is over. Good luck!\n"
							+ "\n"
							+ "MENU OPTIONS: \n"
							+ "About - infromation about the game\n"
							+ "Start New Game - restart the game fresh\n"
							+ "Quit - exit the game\n"
							+ "\n"
							+ "GAME CONTROLS:\n"
							+ "p - pause OR unpause\n"
							+ "up arrow - rotate block left\n"
							+ "down arrow - rotate block right\n"
							+ "right arrow - move block right\n"
							+ "left arrow - move block left\n"
							+ "s - soft drop (drop block one line below)\n"
							+ "h - hard drop (drop block all the way down)\n"
							+ "r - restart game",
							"HELP", JOptionPane.PLAIN_MESSAGE);
		}

	}

	public JLabel getScoreDetails(){
		return scoreDetails;
	}

	public JLabel getStatusDetails(){
		return statusDetails;
	}


}
