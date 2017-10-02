//import statements
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.Font;
import java.util.Random;


/*
 * Authors: Ibrahiem Mohammad
 * 		  Jacky Duong
 * 
 * Date: 2/22/16
 * 
 * UIC CS 342
 * 
 * Description: This project was developed to emulate the Windows MineSweeper game. It creates a grid of 10x10 to serve as the board for the minesweeper.
 * From then on the program behaves very similarly to your standard minesweeper game.
 * 
 * Issues:
 * - top ten score is not updated
 * - issues with right click functionality
 * 
 * 
 */



//main MineSweeper class
public class MineSweeper extends JFrame
implements ActionListener
{
	//class data members
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem reset;
	private JMenuItem top;
	private JMenuItem resetScore;
	private JMenuItem exit;
	private JMenuItem help;
	private JMenuItem about;
	private JPanel grid;
	private Container container;
	private String name = "";
	private MyJButton buttons[][];
	public static int minesCount = 10;
	public static int left = minesCount;
	private MyJButton buttonStart;
	private JLabel timer;
	private JLabel mineLeft;
	private JLabel timeText;
	private int mines[][];
	private int clicks = 90;
	private boolean begin=false;
	private javax.swing.Timer timer1;
	private int timeCount=0;
	private int lineCounter = 0;
	private int scores[] = new int[10];
	private int lowest=0;
	private int lowestIndex=0;
	private String lowestName;

	//main minesweeper contructor
	public MineSweeper() throws IOException
	{
		super("MineSweeper");
		//create menu items
		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		menuBar.add(menu);
		reset = new JMenuItem("Reset");
		reset.addActionListener( this );
		reset.setMnemonic(KeyEvent.VK_R);
		menu.add(reset);

		top = new JMenuItem("Top Ten");
		top.addActionListener( this );
		top.setMnemonic(KeyEvent.VK_T);
		menu.add(top);
		exit = new JMenuItem("Exit");
		exit.addActionListener( this );
		exit.setMnemonic(KeyEvent.VK_X);
		menu.add(exit);

		menu = new JMenu("Help");
		menuBar.add(menu);
		help = new JMenuItem("Help");
		help.addActionListener( this );
		help.setMnemonic(KeyEvent.VK_H);
		menu.add(help);
		about = new JMenuItem("About");
		about.addActionListener( this );
		about.setMnemonic(KeyEvent.VK_A);
		menu.add(about);

		JPanel topPanel = new JPanel( new GridLayout(2,3));
		JLabel minesLeft = new JLabel("Mines left");
		minesLeft.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(minesLeft);

		buttonStart = new MyJButton();
		buttonStart.setIcon(new ImageIcon("face-smile.gif"));
		buttonStart.setPreferredSize(new Dimension(15,15));
		buttonStart.addMouseListener(new MouseClickHandler());
		topPanel.add(buttonStart);

		timer = new JLabel("Time");
		timer.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(timer);

		mineLeft = new JLabel(""+left);
		mineLeft.setHorizontalAlignment( JLabel.CENTER );
		mineLeft.setForeground( Color.red );
		mineLeft.setFont( new Font( "SERIF", Font.BOLD, 20 ) );
		topPanel.add( mineLeft );
		topPanel.add( new JLabel(""));

		timer1 = new javax.swing.Timer(100, this);
		timeText = new JLabel("000");
		timeText.setHorizontalAlignment(JLabel.CENTER);
		timeText.setForeground(Color.red);
		timeText.setFont( new Font("SERIF",Font.BOLD,20));
		topPanel.add(timeText);

		this.setJMenuBar(menuBar);



		grid = new JPanel( new GridLayout(10,10));
		container = getContentPane();
		container.add(topPanel, BorderLayout.NORTH );
		buttons = new MyJButton[10][10];
		//creates the grid
		for( int count = 0; count <10;count++)
		{
			for(int i = 0; i < 10; i++)
			{
				buttons[count][i] = new MyJButton(name,count,i);
				buttons[count][i].addMouseListener(new MouseClickHandler());
				grid.add(buttons[count][i]);

			}
		}

		container.add(grid);

		mines = new int[10][10];

		setSize(750,500);
		setVisible(true);
		initEmptyMines();
		setMines();
		setSurroundingMineHints();

		File file = new File("scores.txt");
		if(!file.exists())
			file.createNewFile();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String aLineFromFile = null, msg = null;
		while ((aLineFromFile = br.readLine()) != null){
			lineCounter++;
		}        
		br.close();
	}
	
	public void lowerClick()
	{
		clicks--;
	}
	public void lowerMine()
	{
		left = left -1;
	}
	public void upMine()
	{
		left = left +1;
	}

	//main method which starts the minesweeper game
	public static void main( String args[] ) throws IOException
	{
		MineSweeper application = new MineSweeper();
		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	} 

	//initialize the mines to be randomly placed on the minesweeper board
	public void setMines(){
		boolean isShuffled;
		int row, column;
		Random gen = new Random(); 
		for(int i=0 ; i<10 ; i++){

			do{
				row = gen.nextInt(10) ;
				column = gen.nextInt(10);

				if(mines[row][column] == -1)
					isShuffled=true;
				else
					isShuffled = false;
			}while(isShuffled);

			mines[row][column] = -1;
		}
	}

	//initialize empty spots on the minesweeper board
	public void initEmptyMines(){
		for(int i=0 ; i<10 ; i++)
			for(int j=0 ; j<10 ; j++)
				mines[i][j]=0;
	}

	//algorithm for setting the empty spots on the board with hints surrounding mine(s)
	//these will be placed with respective buttons later on
	public void setSurroundingMineHints(){
		for(int row=0 ; row < 10 ; row++)
		{
			for(int column=0 ; column < 10 ; column++)
			{
				for(int i=-1 ; i<=1 ; i++)
				{
					for(int j=-1 ; j<=1 ; j++)
					{

						if(mines[row][column] != -1)
						{
							if((row+i) <0 || (column+j) <0 ||(row+i) >9 || (column+j) >9)
							{
								//do nothing
							}
							else{
								if(mines[row+i][column+j] == -1)
								{
									mines[row][column]++;
								}
							}
						}
					}
				}
			}
		}

	}

	//shows all the mines on the board
	public void showMines(){
		for(int i=0 ; i < 10; i++)
		{
			for(int j=0 ; j < 10 ; j++)
			{
				if(mines[i][j] == -1)
				{
					buttons[i][j].setIcon(new ImageIcon("button_bomb_blown.gif"));
					buttons[i][j].setDisabledIcon(new ImageIcon("button_bomb_blown.gif"));
					timer1.stop();
					//buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
				}
			}
		}
		//show();
		buttonStart.setIcon(new ImageIcon("head_dead.gif"));
		String name = JOptionPane.showInputDialog(null, "GAME OVER!\n Enter your name: ");


		// if line counter > 10 then check if there is new score is better
		// if it is then add it OTHERWISE no
		// if line counter < 10 then just add without checking anything
		if (lineCounter == 10){
			for (int i = 0; i < scores.length; i++){
				if (timeCount < scores[i]){
					writeFile(name + " " + timeCount);
					break;
				}
			}
		}
		else writeFile(name + " " + timeCount);			
	}

	//resets board back to the initial state--empty
	//re-enables all buttons
	//resets timer
	public void resetBoard()
	{

		buttonStart.setIcon(new ImageIcon("face-smile.gif"));

		timer1.stop();
		timeCount = 0;
		timeText.setText( "00" + timeCount );

		initEmptyMines();
		setMines();
		setSurroundingMineHints();
		left = 10;
		mineLeft.setText(""+left);
		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j <10; j++)
			{
				buttons[i][j].setIcon(new ImageIcon());

				buttons[i][j].setEnabled(true);
			}
		}


	}
	//selections for what happens if a certain menu option is clicked
	//options are self explanatory
	public void actionPerformed (ActionEvent e)
	{
		Object source = e.getSource();
		if(source == reset)
		{
			resetBoard();
		}
		else if(source == exit)
		{
			System.exit(0);
		}
		else if(source ==resetScore)
	    {
	      try{
	        PrintWriter pw = new PrintWriter(new FileWriter("scores.txt"));
	        pw.print("");
	        pw.close();
	      }catch (IOException ee) {
	        ee.printStackTrace();
	      }
	      
	     
	    }

		else if(source == top)
		{
			try {
				readFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		else if(source == about)
		{
			JOptionPane.showMessageDialog( this,"Jacky Duong\nIbrahiem Mohammad\nCS342\nUIC\n2016\nSimple 10x10 example of minesweeper");
		}
		else if(source == help)
		{
			JOptionPane.showMessageDialog( this,"Left click to find the mines. The numbers shown are how much mines are around that one block.\n "
					+ "Right click if you think there is a mine there. There are 10 mines. ");
		}
		else if (source == timer1){
			timeCount++;
			if (timeCount < 10)timeText.setText("000" + timeCount);
			else if (timeCount < 1000) timeText.setText("0" + timeCount);
			else timeText.setText("" + timeCount);
		}
	}

	//checks surrounding spots for mines
	//also checks for mine hints
	//if a spot is checked, that button is disabled
	public void checkZero(int x, int y)
	{

		if(x >9 ||y >9 || x < 0|| y <0 || !buttons[x][y].isEnabled())
			return;

		switch(mines[x][y]){
		case 1:
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setIcon(new ImageIcon("button_1.gif"));
			buttons[x][y].setDisabledIcon(new ImageIcon("button_1.gif"));
			break;
		case 2:
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setIcon(new ImageIcon("button_2.gif"));
			buttons[x][y].setDisabledIcon(new ImageIcon("button_2.gif"));
			break;
		case 3: 
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setIcon(new ImageIcon("button_3.gif"));
			buttons[x][y].setDisabledIcon(new ImageIcon("button_3.gif"));
			break;
		case 4:
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setIcon(new ImageIcon("button_4.gif"));
			buttons[x][y].setDisabledIcon(new ImageIcon("button_4.gif"));
			break;
		case 5:
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setIcon(new ImageIcon("button_5.gif"));
			buttons[x][y].setDisabledIcon(new ImageIcon("button_5.gif"));
			break;
		case 6:
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setIcon(new ImageIcon("button_6.gif"));
			buttons[x][y].setDisabledIcon(new ImageIcon("button_6.gif"));
			break;
		case 7:
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setIcon(new ImageIcon("button_7.gif"));
			buttons[x][y].setDisabledIcon(new ImageIcon("button_7.gif"));
			break;
		case 8:
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setIcon(new ImageIcon("button_8.gif"));
			buttons[x][y].setDisabledIcon(new ImageIcon("button_8.gif"));
			break;
		default: break;
		}

		if(mines[x][y] == 0 )
		{
			lowerClick();
			buttons[x][y].setEnabled(false);
			buttons[x][y].setText("");

			checkZero( x - 1, y - 1 );
			checkZero( x	, y - 1 );
			checkZero( x + 1, y - 1 );
			checkZero( x - 1, y		);
			checkZero( x + 1, y		);
			checkZero( x - 1, y + 1 );
			checkZero( x	, y + 1 );
			checkZero( x + 1, y + 1 );  

		}
		else return;
	}

	//reads in text file for scores and displays to user
	public void readFile() throws IOException{
		File file = new File("scores.txt");
		if(!file.exists())
			file.createNewFile();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String aLineFromFile = null, msg = null;
		//String msg = null;
		lineCounter=0;
		while ((aLineFromFile = br.readLine()) != null){
			//JOptionPane.showMessageDialog(null, aLineFromFile);
			if (msg == null) msg = aLineFromFile + "\n";
			else msg = msg + aLineFromFile + "\n";
			//if(lineCounter != 9){
			lineCounter++;
			//}
		}        
		br.close();
		JOptionPane.showMessageDialog(null, msg);
		return;
	}

	//writes to file the scores of each turn/user
	public void writeFile(String person){

		try {


			//update scores
			File file = new File("scores.txt");
			if(!file.exists())
				file.createNewFile();
			BufferedReader br = new BufferedReader(new FileReader(file));

			String line = null, temp = null,startLine;
			int i = 0, score = 0;
			while ((line = br.readLine()) != null){
				startLine = line.substring(0,line.indexOf(" ")+1);
				temp = line.substring(line.indexOf(" "));

				score = Integer.parseInt(temp.substring(1));

				if (lineCounter<11 )
					scores[i] = score;
				if(score > lowest)
				{
					lowestName = startLine;
					lowest = score;
					lowestIndex= i;
				}
				i++;
			}


			//TODO: FIX THIS TO REPLACE OLD SCORE AND NAME WITH NEW NAME AND SCORE
			if (lineCounter == 10){
				File tempFile = File.createTempFile("string", ".txt", file.getParentFile());
				String delete = lowestName + lowest;
				//JOptionPane.showInputDialog (null, lineCounter+"work?! Enter your name");
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
				BufferedReader reader = new BufferedReader(new FileReader(file));

				for (String line1; (line1 = reader.readLine()) != null;) {
					if(line1.equals(delete))
					{
						JOptionPane.showInputDialog (null, line1+ " " + delete+ " d");
						//do nothing
					}
					else
					{

						writer.println(line1);
					}
				}
				reader.close();
				writer.close();
				file.delete();
				tempFile.renameTo(file);
				PrintWriter pw = new PrintWriter(new FileWriter("scores.txt", true));
				pw.write(person + "\n");
				pw.close();
			}
			else {
				PrintWriter pw = new PrintWriter(new FileWriter("scores.txt", true));
				pw.write(person + "\n");
				lineCounter++;
				pw.close();         
			}




		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//click handling for right and left clicks
	private class MouseClickHandler extends MouseAdapter
	{
		public void mouseClicked (MouseEvent e)
		{
			String s = "";
			MyJButton temp = (MyJButton) e.getSource();
			int num = temp.getRow();
			int num1 = temp.getCol();
			timer1.start();
			if(temp ==buttonStart)
			{
				resetBoard();
			}
			else if (SwingUtilities.isLeftMouseButton(e)){
				s = "Left Mouse Click";
				if(buttons[num][num1].getText().equals("?")||buttons[num][num1].getText().equals("M"))
				{
					//do nothing

				}
				else if(mines[num][num1] == -1)
				{
					//e.pushed();
					//setVisible(true);
					for(int i = 0; i < 10; i++)
					{
						for(int j = 0; j <10; j++)
						{
							buttons[i][j].setEnabled(false);
						}
					}
					showMines();
					setVisible(true);
				}
				else
				{
					lowerClick();
					checkZero(num,num1);
					setVisible(true);
				}
			}  
			else if (SwingUtilities.isRightMouseButton(e)){
				//s = "Right Mouse Click";
				if(buttons[num][num1].getText().equals("")){
					buttons[num][num1].setIcon(new ImageIcon("button_bomb_x.gif"));
					buttons[num][num1].setDisabledIcon (new ImageIcon("button_bomb_x.gif"));	
					lowerMine();
					mineLeft.setText(""+left);
				}
				else if(buttons[num][num1].getIcon().equals(new ImageIcon("button_bomb_x.gif"))){
					buttons[num][num1].setIcon(new ImageIcon("button_question.gif"));
					buttons[num][num1].setDisabledIcon (new ImageIcon("button_question_pressed.gif"));
					upMine();
					mineLeft.setText(""+left);
				}
				else if(buttons[num][num1].getIcon().equals(new ImageIcon ("button_question_pressed.gif")))
					buttons[num][num1].setIcon(new ImageIcon());

				setVisible(true);
			}
			else if (SwingUtilities.isMiddleMouseButton(e))
				s = "Middle Mouse Click";   


		}
	}
	
	//button class
	//constructors for grid
	//constructors for how buttons behave when pressed/unpressed
	class MyJButton extends JButton
	{
		private int row;
		private int col;
		private boolean pressed;
		public MyJButton ( String string )
		{
			super (string);
		}


		public MyJButton ( String text , int n,int m)
		{
			super (text);
			row = n;
			col = m;
			pressed = false;
		}

		public MyJButton() {
			// TODO Auto-generated constructor stub
		}


		public void setRow (int n)
		{
			row = n;
		}
		public void setCol(int n)
		{
			col = n;
		}
		public int getRow ()
		{
			return row;
		}
		public int getCol()
		{
			return col;
		}
		public boolean isPushed()
		{
			return pressed;
		}
		public void pushed()
		{
			if (pressed!=true)
				pressed = true;

		}
	}
}