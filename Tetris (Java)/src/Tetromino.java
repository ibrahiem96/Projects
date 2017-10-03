import java.util.Random;
import java.lang.Math;

/**
 * 
 * The Tetromino class serves as the base tetromino class which determines
 * the shapes of the tetrominoes and how they react to the controls given.
 * For example, how they behave when told to move right, rotate left, etc.
 *
 * This class also builds the tetromino through a system of coordinates which
 * connect with one another to form the shape. The design for this was gotten
 * through tutorials and guides on the web.
 * 
 * This class is accessed only by the GamePlay Class (and through the Gameplay
 * class, also the Controller class).
 * 
 * @author Ibrahiem Mohammad
 *
 */

public class Tetromino {

	private TetrominoShape shape;
	private int coordinates[][];
	private int coordinateTab[][][];
	
	public enum TetrominoShape {Square,
						 Line,
						 T_shape,
						 L_shape,
						 Z_shape,
						 S_shape,
						 J_shape,
						 None
						 }
	
	
	
	/**
	 * Constructor for Tetromino
	 * 
	 * All tetrominoes start with no shape at all
	 */
	public Tetromino(){
		coordinates = new int[4][2];
		setTetromino(TetrominoShape.None);
	}
	
	/**
     * select a random number to represent one of the option in the enum
     * then select that value to be the shape of the tetronimo
     */
    public void selectRandTetronimo(){
    	
    	Random r = new Random();
    	
    	//select random num from 1-7
    	int num = Math.abs(r.nextInt()) % 7 + 1;
    	TetrominoShape[] shape_values = TetrominoShape.values();
    	
    	//select tetromino based on the random number selected
    	setTetromino(shape_values[num]);
    }
    
    /**
     * rotates the tetromino left
     */
    public Tetromino rotateLeft() 
    {
        if (shape == TetrominoShape.Square) return this;

        Tetromino changedShape = new Tetromino(); changedShape.shape = shape;

        for (int i = 0; i < 4; ++i) {
            changedShape.setXCoordinate(i, y(i));
            changedShape.setYCoordinate(i, -x(i));
        }
        return changedShape;
    }
    
    /**
     * rotates the tetromino right
     * @return
     */
    public Tetromino rotateRight()
    {
        if (shape == TetrominoShape.Square) return this;

        Tetromino changedShape = new Tetromino(); changedShape.shape = shape;

        for (int i = 0; i < 4; ++i) {
            changedShape.setXCoordinate(i, -y(i));
            changedShape.setYCoordinate(i, x(i));
        }
        return changedShape;
    }
	
	
	/**
	 * Set Tetromino shape possibilities and store them
	 * 
	 * @param ts
	 */
	public void setTetromino(TetrominoShape ts){
		
		 //setting possibilities for tetromino shape
		 coordinateTab = new int[][][] {
	            { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
	            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
	            { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
	            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
	            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
	            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
	            { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
	            { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
	        };
		
	     //selecting one of the above coordinate possibilities and assigning to a shape
	     for (int i = 0 ; i < 4 ; i++){
	    	 for (int j = 0 ; j < 2 ; j++){
	    		 //ordinal returns position of tetromino
	    		 coordinates[i][j] = coordinateTab[ts.ordinal()][i][j];
	    	 }
	     }
	     
	     shape = ts;
	}
	
        
    /**
     * the following function retrieves the minimum x boundary
     * @return
     */
    public int get_minX(){
    	
      int bottom_g = coordinates[0][0];
      for (int i=0; i < 4; i++) {
          bottom_g = Math.min(bottom_g, coordinates[i][0]);
      }
      return bottom_g;
      
    }

    /**
     * the following function retrieves the minimum y boundary
     * @return
     */
    public int get_minY() {
    	
      int bottom_g = coordinates[0][1];
      for (int i=0; i < 4; i++) {
          bottom_g = Math.min(bottom_g, coordinates[i][1]);
      }
      return bottom_g;
      
    }
    
    private void setXCoordinate(int i, int x){
    	coordinates[i][0] = x; 
    }
    private void setYCoordinate(int i, int y){ 
    	coordinates[i][1] = y; 
    }
    public int x(int i){ 
    	return coordinates[i][0]; 
    }
    
    public int y(int i){ 
    	return coordinates[i][1];
    }
    
    //return selected shape from tetromino enum
    public TetrominoShape getShape(){
    	return shape; 
    }
    
}
