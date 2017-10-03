import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * GAMEPLAY CLASS
 * 
 * The gameplay class serves as the middle class. It is initialized as an object
 * by the TetrisPlay class. The GamePlay constructor then initializes the timer
 * which sets in motion all the other events needed to start the game.
 * 
 * The design and implementation of a few of the parts of this class were
 * obtained through resources on the web. For example to figure out how to
 * 'paint' and 'repaint' the tetris pieces and show those changes on the grid
 * I had to search and find that the usage of the graphics class was needed.
 * 
 * This class access itself and the Tetromino class, and extends its usgae to
 * the TetrisPlay class.
 * 
 * @author Ibrahiem Mohammad
 *
 */

public class GamePlay extends JPanel implements ActionListener{

	//timer variable that will control gameplay
	Timer t;
	int score = 0;

	//check to see if game is currently paused
	boolean isPaused = false;
	//check to see if game has begun
	boolean hasBegun = false;
	//check to see if piece has reached the bottom or not
	boolean isOnBottom = false;

	//game level
	int level = 1;
	int timerDelay = 0;

	int linesRemoved_counter = 0;

	JLabel scoreD;
	JLabel statusD;

	Tetromino currPiece;
	Tetromino.TetrominoShape[] grid;

	//starting positions
	int currXPos = 0;
	int currYPos = 0;

	//board dimensions
	final int height = 20;
	final int width = 10;

	/**
	 * GamePlay constructor. Sets up the new pieces and board.
	 * @param tp
	 */
	public GamePlay(TetrisPlay tp){

		setFocusable(true);

		//init new game piece
		currPiece = new Tetromino();

		//init timer
		t = new Timer(600, this);
		t.start();

		//init board
		scoreD = tp.getScoreDetails();
		statusD = tp.getStatusDetails();
		grid = new Tetromino.TetrominoShape[width * height];

		addKeyListener(new Controller());
		wipeBoard();

	}
	
	//event handler for tetromino gravity
	public void actionPerformed (ActionEvent e){
		if (isOnBottom){
			isOnBottom = false;
			setNewPiece();
		}

		else { setOnNextLine(); }

	}

	//returns grid width
	int gridDimenW() { 
		return (int) getSize().getWidth() / width; 
	}
	
	//returns grid height
	int gridDimenH() { 
		return (int) getSize().getHeight() / height; 
	}

	// returns shape type at a given position
	Tetromino.TetrominoShape getShapeAtPos(int x, int y) { 
		return grid[(y * width) + x]; 
	}

	/**
	 * method to start the game
	 * 
	 * - set all boolean flags
	 * - set all counters
	 * - 
	 */
	public void begin(){

		if (isPaused) return;

		//set bool checks
		hasBegun = true;
		isOnBottom = false;

		linesRemoved_counter = 0;
		wipeBoard();

		setNewPiece();
		t.start();

	}

	/**
	 * method to pause the game
	 */
	public void pause() {

		if (!hasBegun) return;

		isPaused = !isPaused;
		statusD.setText("");

		if (isPaused) {
			t.stop();
			statusD.setText("Paused");
		} 

		else {
			t.start();
			scoreD.setText(String.valueOf(score));
		}
		repaint();
	}

	/**
	 * method to make changes to the tetromino pieces as the game goes on
	 * if tetromino shape is real then draw it out
	 * watch for upper boundaries on the grid
	 * keep track of current positions of all squares drawn and those to be drawn
	 */
	public void paint(Graphics g)
	{ 
		super.paint(g);

		Dimension size = getSize();

		//get upper boundary of the game board
		int upperBound = (int) size.getHeight() - height * gridDimenH();


		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				//TODO: get shape
				Tetromino.TetrominoShape tetromino = getShapeAtPos(j, height - i - 1);

				//if shape is tetromino piece then draw the shape
				//watch for the upper bound
				if (tetromino != Tetromino.TetrominoShape.None)
					drawTetromino(g, 0 + j * gridDimenW(),
							upperBound + i * gridDimenH(), 
							tetromino);
			}
		}

		if (currPiece.getShape() != Tetromino.TetrominoShape.None) {
			for (int i = 0; i < 4; ++i) {
				int x = currXPos + currPiece.x(i);
				int y = currYPos - currPiece.y(i);

				//if shape is tetromino piece then draw the shape
				//watch for the upper bound
				drawTetromino(g, 0 + x * gridDimenW(),
						upperBound + (height - y - 1) * gridDimenH(),
						currPiece.getShape());
			}
		}
	}
	
	/**
	 * method to check if piece can fall
	 * - if yes then decrements piece position and allows the piece to fall
	 * - if not then the piece position remains the same
	 * - however piece still falls gravitationally (unless at bottom)
	 */
	private void allowPieceFall(){

		//set temp position
		int newYPos = currYPos;

		//while new position is still in grid
		while (newYPos > 0) {

			//if the tetromino cannot be moved then break the loop
			//else keep dropping the tetromino (decrement position)
			if (!moveTo(currPiece, currXPos, newYPos - 1))
				break;

			--newYPos;
		}

		tetrominoDrop();
	}

	/**
	 * method to set a tetromino on the next line once a line has been cleared
	 * needs to match condition for moving inability
	 */
	private void setOnNextLine() {

		//if the tetronimo cannot be moved drop it down a line
		if (!moveTo(currPiece, 
				currXPos, 
				currYPos - 1)) tetrominoDrop();
	}

	/**
	 * method to clean board of all pieces
	 */
	private void wipeBoard(){
		for (int i = 0; i < height*width; ++i)
			grid[i] = Tetromino.TetrominoShape.None;
	}
	
	/**
	 * method to simulate gravity fot the tetromino pieces
	 * - checks if pieces can fall
	 * - if yes, allows them to drop
	 * - if not (meaning the piece is at the bottom), calls function to
	 * initialize new piece
	 */
	private void tetrominoDrop() {

		for (int i = 0; i < 4; ++i) {

			//set the temp positions for the piece to be dropped based on current positions
			int x = currXPos + currPiece.x(i); int y = currYPos - currPiece.y(i);

			//set shape of tetromino at new position on grid
			grid[(y*width) + x] = currPiece.getShape();
		}

		removeOneLine();

		//if the tetromino has been set get new piece
		if (!isOnBottom) setNewPiece();
	}
	
	/**
	 * initialize new piece
	 * - if piece cannot be set anywhere then game is over
	 * - because grid is filled
	 */
	private void setNewPiece()
	{
		//select random tet
		currPiece.selectRandTetronimo();

		//get the height and width of new piece
		//make sure they start at top of grid
		currXPos = width/2 + 1; currYPos = height-1 + currPiece.get_minY();

		//if shape cannot be moved anywhere (i.e game is about to be over)
		if (!moveTo(currPiece, currXPos, currYPos)) {

			//set the current piece to blank
			//game over means that no shape selection is necessary
			//TODO: should include shape selection?
			currPiece.setTetromino(Tetromino.TetrominoShape.None);

			// game is over
			t.stop();
			hasBegun = false; statusD.setText("Game Over! :("); 
		}
	}
	
	/** the move controls function
	 * - if tetromino piece can be moved right or left then move and return true
	 * - else return false
	 * 
	 * @param newTPos
	 * @param newXPos
	 * @param newYPos
	 * @return
	 */
	private boolean moveTo(Tetromino newTPos, int newXPos, int newYPos) {
		for (int i = 0; i < 4; ++i) {

			//set positions for new tetromino to move to
			int x = newXPos + newTPos.x(i); int y = newYPos - newTPos.y(i);

			//conditions to check if tetromino can move
			//or will move outside grid (illegal)
			if (x < 0 || x >= width || y < 0 || y >= height) return false;

			//also if shape exists at new position, then cannot move
			if (getShapeAtPos(x, y) != Tetromino.TetrominoShape.None) return false;
		}

		//piece in hand is now the new piece
		currPiece = newTPos;

		//set current positions to the new tetromino positions
		//if this point is reached then is movable
		currXPos = newXPos; currYPos = newYPos;

		repaint();
		return true;
	}
	
	/**
	 * method to determine score, delay timer, calculate level, and 
	 * determine of a line has been filled in order for it to be removed.
	 * 
	 * - if line can be removed then update class line counter
	 * - based on line counter determine level and score
	 * - based on level determine timer delay
	 */
	private void removeOneLine()
	{
		int _level = level;
		int linesToBeRemoved = 0;

		timerDelay = (50 - (level*2))/60000;

		for (int i = height-1; i >= 0; --i) {

			//check to see if line can be removed (is it filled)
			boolean isRemovable = true;

			for (int j = 0; j < width; ++j) {

				//if shape at given position is not a valid shape break
				if (getShapeAtPos(j, i) == Tetromino.TetrominoShape.None) {
					isRemovable = false;
					break;
				}
			}
			//else if it is removable:
			//increment counter
			//set new shapes that will be changed due to line removal
			if (isRemovable) {
				++linesToBeRemoved;
				for (int k = i; k < height-1; ++k) {
					for (int j = 0; j < width; ++j)
						grid[(k*width) + j] = getShapeAtPos(j, k + 1);
				}
			}
		}

		//can any lines be removed?
		//if yes, remove and repaint
		//else exit
		if (linesToBeRemoved > 0) {

			//increment line counter of class
			linesRemoved_counter += linesToBeRemoved;

			System.out.println("lines removed: " + linesRemoved_counter /* times the number of lines removed*/);

			//update visual counter
			switch(linesToBeRemoved){
			case 1: //scoreD.setText(String.valueOf(40 * level));
				score += 40 * level;
				break;
			case 2: //scoreD.setText(String.valueOf(100 * level));
				score += 100 * level;
				break;
			case 3: //scoreD.setText(String.valueOf(300 * level));
				score += 300 * level;
				break;
			case 4: //scoreD.setText(String.valueOf(1200 * level));
				score += 1200 * level;
				break;	
			}

			scoreD.setText(String.valueOf(score));


			//update consequential info
			isOnBottom = true;
			currPiece.setTetromino(Tetromino.TetrominoShape.None);
			repaint();

			if (linesRemoved_counter % 10 == 0){
				level++;
			}

			//update timer
			if (_level != level){
				t.setDelay(timerDelay);
			}
		}
	}

	public Tetromino getPiece(){
		return currPiece;
	}
	
	/**
	 * 
	 * paint method obtained from tetris tutorial:
	 * - allows changes to be shown (redrawn)
	 * - through use of graphics and draw
	 * 
	 * @param g
	 * @param xPos
	 * @param yPos
	 * @param ts
	 */
	private void drawTetromino(Graphics g, int xPos, int yPos, 
			Tetromino.TetrominoShape ts)
	{
		//set sample colors
		Color possibleColors[] = { 
				new Color(0, 0, 0), 
				new Color(103, 107, 219), 
				new Color(22, 219, 140), 
				new Color(213, 22, 219), 
				new Color(247, 244, 42), 
				new Color(7, 245, 241), 
				new Color(7, 245, 15), 
				new Color(245, 7, 23)
		};

		//get initial color in c
		Color c = possibleColors[ts.ordinal()];

		g.setColor(c);

		//to differentiate active/inactive
		g.setColor(c.brighter());

		/*
		 * painting details (differentiating between active and inactive pieces)
		 */
		g.drawLine(
				xPos, 
				yPos + gridDimenH() - 1, 
				xPos, yPos
				);

		g.drawLine(
				xPos, yPos, 
				xPos + gridDimenW() - 1, 
				yPos
				);

		//to differentiate active/inactive
		g.setColor(c.darker());

		//fill the grid position where tetromino is to be
		g.fillRect(
				xPos + 1, yPos + 1, 
				gridDimenW() - 2, 
				gridDimenH() - 2
				);
		
		//drawing outline
		g.drawLine(
				xPos + 1, 
				yPos + gridDimenH() - 1,
				xPos + gridDimenW() - 1, 
				yPos + gridDimenH() - 1
				);
		
		//drawing outline
		g.drawLine(
				xPos + gridDimenW() - 1,
				yPos + gridDimenH() - 1,
				xPos + gridDimenW() - 1,
				yPos + 1
				);
	}	
	
	// determine if game is over or not
	public boolean getGameStatus(){
		return hasBegun;
	}

	/**
	 * Event handler class which extends from Java KeyAdapter which implements the Keylistener
	 *
	 *  - handles possible key presses
	 *  - main controller flow for game
	 *
	 *	This class is accessed only within the GamePlay class
	 *
	 * @author Ibrahiem Mohammad
	 * 
	 * 
	 */
	class Controller extends KeyAdapter {


		/**
		 * Event handler for each type of key that can be pressed to
		 * have an effect in the game. 
		 */
		public void keyPressed(KeyEvent ke) {

			if (!hasBegun 
					|| currPiece.getShape() == Tetromino.TetrominoShape.None) return;

			int options = ke.getKeyCode();

			if (options == KeyEvent.VK_P) {
				pause();
				return;
			}

			if (isPaused) return;

			// up down left right options
			if (options == KeyEvent.VK_LEFT) moveTo (currPiece, currXPos-1, currYPos);
			if (options == KeyEvent.VK_RIGHT) moveTo (currPiece, currXPos+1, currYPos);
			if (options == KeyEvent.VK_UP) moveTo (currPiece.rotateLeft(), currXPos, currYPos);
			if (options == KeyEvent.VK_DOWN) moveTo (currPiece.rotateRight(), currXPos, currYPos);

			// hard drop
			if (options == KeyEvent.VK_H) allowPieceFall();

			// soft drop
			if (options == KeyEvent.VK_S) setOnNextLine();

			//restart game
			if (options == KeyEvent.VK_R) begin();

		}
	}

}
