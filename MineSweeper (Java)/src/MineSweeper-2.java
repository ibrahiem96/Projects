/*
 * Authors: Ibrahiem Mohammad
 *     Jacky Duong
 * 
 * Date: 2/22/16
 * 
 * UIC CS 342
 * 
 * Description: This project was developed to emulate the Windows MineSweeper game. It creates a grid of 10x10 to serve as the board for the minesweeper.
 * From then on the program behaves very similary to your standard minesweeper game.
 * 
 * Issues:
 * - top ten score is not updated
 * - NOTE THIS VERSION DOES NOT CONTAIN IMAGES BUT GAME FUNCTIONALITY IMPROVED
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.Font;
import java.util.Random;


public class MineSweeper extends JFrame
implements ActionListener
{

  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem resetScore;
  private JMenuItem reset;
  private JMenuItem top;
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
  private int[] scores = new int[10];
  private int lowest=0;
  private int lowestIndex=0;
  private String lowestName;
  
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
    resetScore = new JMenuItem("Reset Score");
    resetScore.addActionListener( this );
    resetScore.setMnemonic(KeyEvent.VK_E);
    menu.add(reset);
    
    top = new JMenuItem("Top Ten");
    top.addActionListener( this );
    top.setMnemonic(KeyEvent.VK_T);
    menu.add(top);
    menu.add(resetScore);
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

    //add smile

    buttonStart = new MyJButton(":)");
    buttonStart.setPreferredSize(new Dimension(15,15));
    buttonStart.addMouseListener(new MouseClickHandler());
    topPanel.add(buttonStart);



    //add timer
    timer = new JLabel("Time");
    timer.setHorizontalAlignment(JLabel.CENTER);
    topPanel.add(timer);

    mineLeft = new JLabel(""+left);
    mineLeft.setHorizontalAlignment( JLabel.CENTER );
    mineLeft.setForeground( Color.red );
    mineLeft.setFont( new Font( "SERIF", Font.BOLD, 20 ) );
    topPanel.add( mineLeft );
    topPanel.add( new JLabel(""));

    timer1 = new javax.swing.Timer(1000, this);
    timeText = new JLabel("000");
    timeText.setHorizontalAlignment(JLabel.CENTER);
    timeText.setForeground(Color.red);
    timeText.setFont( new Font("SERIF",Font.BOLD,20));
    topPanel.add(timeText);

    //add mines left

    //setJMenuBar(theJMenuBar);
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
        //buttons[count].addActionListener(this);
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

    //gets line counter at start of game
    File file = new File("scores.txt");
    if(!file.exists())
      file.createNewFile();
    BufferedReader br = new BufferedReader(new FileReader(file));
    String aLineFromFile = null, msg = null;
    //String msg = null;
    while ((aLineFromFile = br.readLine()) != null){
      //JOptionPane.showMessageDialog(null, aLineFromFile);
      //if (msg == null) msg = aLineFromFile + "\n";
      //else msg = msg + aLineFromFile + "\n";
      //if(lineCounter != 10)
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
  public static void main( String args[] ) throws IOException
  {
    MineSweeper application = new MineSweeper();
    application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
  } 
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
  public void initEmptyMines(){
    for(int i=0 ; i<10 ; i++)
      for(int j=0 ; j<10 ; j++)
        mines[i][j]=0;
  }
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
                //donothing
                // break;
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
  public void showMines(){
    for(int i=0 ; i < 10; i++)
    {
      for(int j=0 ; j < 10 ; j++)
      {
        if(mines[i][j] == -1)
        {
          buttons[i][j].setText("*");
          timer1.stop();
          //buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
        }
      }
    }
    //show();
    //String name = JOptionPane.showInputDialog(null, "GAME OVER!\n Enter your name: ");
    
    

    // if line counter > 10 then check if there is new score is better
    // if it is then add it OTHERWISE no
    // if line counter < 10 then just add without checking anything
    

      
      
  }
  public void resetBoard()
  {

    timer1.stop();
    timeCount =0;
    timeText.setText( "00" + timeCount );
    clicks = 90;
    initEmptyMines();
    setMines();
    setSurroundingMineHints();
    left = 10;
    mineLeft.setText(""+left);
    for(int i = 0; i < 10; i++)
    {
      for(int j = 0; j <10; j++)
      {
        buttons[i][j].setText("");
        buttons[i][j].setEnabled(true);
      }
    }


  }
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
    else if(source ==about)
    {
      JOptionPane.showMessageDialog( this,"Jacky Duong\nIbrahiem Mohammad\nSimple 10x10 example of minesweeper");
    }
    else if(source ==help)
    {
      JOptionPane.showMessageDialog( this,"Left click to find the mines. The numbers shown are how much mines are around that one block.\n Right click if you think there is a mine there. There are 10 mines. ");
    }
    else if (source == timer1){
      timeCount++;
      if (timeCount < 10)timeText.setText("00" + timeCount);
      else if (timeCount < 100) timeText.setText("0" + timeCount);
      else timeText.setText(" " + timeCount);
    }
  }

  /**
   * 
   * HERE IS HOW I TRIED DOING RECURSION. LOGIC IS CORRECT BUT STACK OVER FLOW
   * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
   * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
   * #####################################################################
   * $#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
   */
  public void checkZero(int x, int y)
  {
    if(x >9 ||y >9 || x < 0|| y <0 || !buttons[x][y].isEnabled())
      return;

    if (mines[x][y] == 1 || mines[x][y] == 2 ||
      mines[x][y] == 3 || mines[x][y] == 4 ||
      mines[x][y] == 5 || mines [x][y] == 6 ||
      mines[x][y] == 7 || mines [x][y] == 8){
      lowerClick();
      buttons[x][y].setEnabled(false);
      buttons[x][y].setText(""+mines[x][y]);
    }

    else if(mines[x][y] == 0 )
    {
      lowerClick();
      buttons[x][y].setEnabled(false);
      buttons[x][y].setText("");

      checkZero( x - 1, y - 1 );
      checkZero( x  , y - 1 );
      checkZero( x + 1, y - 1 );
      checkZero( x - 1, y   );
      checkZero( x + 1, y   );
      checkZero( x - 1, y + 1 );
      checkZero( x  , y + 1 );
      checkZero( x + 1, y + 1 );  

    }

    else return;

  }

  public void readFile() throws IOException{
    File file = new File("scores.txt");
    if(!file.exists())
      file.createNewFile();
    else{
      BufferedReader br = new BufferedReader(new FileReader(file));
      String aLineFromFile = null, msg = null;
      //String msg = null;
      lineCounter=0;
      
      while ((aLineFromFile = br.readLine()) != null){
        //JOptionPane.showMessageDialog(null, aLineFromFile);
        
                    //JOptionPane.showInputDialog (null, scores[i]);
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
  }

  public void writeFile(String person){
    
    try {
        
      
        //update scores
        File file = new File("scores.txt");
        if(!file.exists())
          file.createNewFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        String line = null, temp = null,startLine;
        int i = 0, score = 0;
        //int derp =0;
        while ((line = br.readLine()) != null){
          startLine = line.substring(0,line.indexOf(" ")+1);
          temp = line.substring(line.indexOf(" "));
          
          score = Integer.parseInt(temp.substring(1));
          
          if(lineCounter <11)
            scores[i] = score;
          
          //JOptionPane.showInputDialog (null, scores[i]);
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
                            //JOptionPane.showInputDialog (null, line1+ " " + delete+ " d");
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

          //lowerClick();
          //buttons[num][num1].setEnabled(false);
          /* for(int i=-1 ; i<=1 ; i++)
          {
            for(int j=-1 ; j<=1 ; j++)
            {


                if((num+i) <0 || (num1+j) <0 ||(num+i) >9 || (num1+j) >9)
                {
                  //donothing
                }
                else{
                  if(mines[num+i][num1+j] != -1)
                  {

                    buttons[num+i][num1+j].setText(""+mines[num+i][num1+j]);
                  }
                }

            }
          }*/
          checkZero(num,num1);

          setVisible(true);
        }
      }  
      else if (SwingUtilities.isRightMouseButton(e)){
        //s = "Right Mouse Click";
        if(buttons[num][num1].getText().equals("")){
          buttons[num][num1].setText("M");
          lowerMine();
          mineLeft.setText(""+left);
        }
        else if(buttons[num][num1].getText().equals("M")){
          buttons[num][num1].setText("?");
          upMine();
          mineLeft.setText(""+left);
        }
        else if(buttons[num][num1].getText().equals("?"))
          buttons[num][num1].setText("");

        setVisible(true);
        //setSize(300,100);


        //setSize(500,300);
        //setVisible(true);
      }
      else if (SwingUtilities.isMiddleMouseButton(e))
        s = "Middle Mouse Click";   

      if (clicks == 0){
        timer1.stop();
        for(int i = 0; i < 10; i++)
          {
            for(int j = 0; j <10; j++)
            {
              buttons[i][j].setEnabled(false);
            }
          }
        showMines();
        name = JOptionPane.showInputDialog (null,"Finished! Enter your name");
        name = name + " " + timeCount+""; 
                       // JOptionPane.showInputDialog (null, lineCounter+"Why");
        if (lineCounter == 10){
          
          for (int i = 0; i < scores.length; i++){
            //JOptionPane.showInputDialog (null, scores[i]+"WA");
            if (timeCount < scores[i]){
              writeFile(name);
              break;
            }
          }
        }
        else writeFile(name);
        //JOptionPane.showInputDialog (null, name);
      }
    }
  }
  class MyJButton extends JButton
  {
    private int row;
    private int col;
    private boolean pressed;
    public MyJButton ( String string )
    {
      super (string);
      //setText (text);
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