package hw3;


import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;
import api.Exit;
import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Wall;

/**
 * Class that models a game.
 * 
 * @author aeh003 (Amelia Humphrey)
 */
public class LizardGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	/*
	 * the width of the grid
	 */
	private int theWidth;
	/*
	 *the height of the grid 
	 */
	private int theHeight;
	/*
	 * hold different lizards
	 */
	private ArrayList<Lizard> numLiz = new ArrayList<>();
	/*
	 * the grid of cells 
	 */
	private Cell [][] grid;
	

	/**
	 * Constructs a new LizardGame object with given grid dimensions.
	 * 
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public LizardGame(int width, int height) {
		grid = new Cell[height][width];
		resetGrid(width,height);
		theWidth = width;
		theHeight = height;
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width of the grid
	 */
	public int getWidth() {
		return theWidth;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height of the grid
	 */
	public int getHeight() {
		return theHeight;
	}

	/**
	 * Adds a wall to the grid.
	 * <p>
	 * Specifically, this method calls placeWall on the Cell object associated with
	 * the wall (see the Wall class for how to get the cell associated with the
	 * wall). This class assumes a cell has already been set on the wall before
	 * being called.
	 * 
	 * @param wall to add
	 */
	public void addWall(Wall wall) {
		for(int i = 0; i < theHeight; i++) {
			for(int j = 0; j < theWidth; j++) {
				if(grid[i][j] == wall.getCell()) {
					grid[i][j].placeWall(wall);
				}
				
			}
			
		} 
	}

	/**
	 * Adds an exit to the grid.
	 * <p>
	 * Specifically, this method calls placeExit on the Cell object associated with
	 * the exit (see the Exit class for how to get the cell associated with the
	 * exit). This class assumes a cell has already been set on the exit before
	 * being called.
	 * 
	 * @param exit to add
	 */
	public void addExit(Exit exit) {
		for(int i = 0; i < theHeight; i++) {
			for(int j = 0; j < theWidth; j++) {
				if(grid[i][j] == exit.getCell()) {
					grid[i][j].placeExit(exit);
				}
				
			}
			
		}
	}

	/**
	 * Gets a list of all lizards on the grid. Does not include lizards that have
	 * exited.
	 * 
	 * @return lizards list of lizards
	 */
	public ArrayList<Lizard> getLizards() {
		return numLiz; 
	}

	/**
	 * Adds the given lizard to the grid.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards.
	 * 
	 * @param lizard to add
	 */
	public void addLizard(Lizard lizard) {
		// add lizard to the arrayList of lizards
		numLiz.add(lizard);
		for(int row = 0; row < theHeight; row++){
			for(int col = 0; col < theWidth; col++) {
				//loops through the lizard segments and places a lizard on the correct coordinates of the grid 
				for(int k = 0; k < lizard.getSegments().size(); k++)
				if(grid[row][col] == lizard.getSegments().get(k).getCell()) {
					grid[row][col].placeLizard(lizard);
				}
			}
		}
		scoreListener.updateScore(numLiz.size());
	}

	/**
	 * Removes the given lizard from the grid. Be aware that each cell object knows
	 * about a lizard that is placed on top of it. It is expected that this method
	 * updates all cells that the lizard used to be on, so that they now have no
	 * lizard placed on them.
	 * <p>
	 * The scoreListener to should be updated with the number of lizards using
	 * updateScore().
	 * 
	 * @param lizard to remove
	 */
	public void removeLizard(Lizard lizard) { 
		//loop through numLiz and finds the lizard to be removed and removes it
		for(int i = 0; i  < numLiz.size(); i++ ) { 
			if(numLiz.get(i) == lizard) {
				numLiz.remove(i); 
			}
		}
		//loops through the grid and removes the lizard from the cells
		for (int row = 0; row < theHeight; row++) {
			for(int col = 0; col < theWidth; col++) {
				if(grid[row][col].getLizard() == lizard){
				    grid[row][col].removeLizard();
				}
			}
		}
		scoreListener.updateScore(numLiz.size());
		
	}

	/**
	 * Gets the cell for the given column and row.
	 * <p>
	 * If the column or row are outside of the boundaries of the grid the method
	 * returns null.
	 * 
	 * @param col column of the cell
	 * @param row of the cell
	 * @return the cell or null
	 */
	public Cell getCell(int col, int row) {
		//checks if the cell is in grid
		if(col >= theWidth || row >= theHeight || row < 0 || col < 0) {
			return null;
		}
		else {
			return grid[row][col]; 
		}
	}

	/**
	 * Gets the cell that is adjacent to (one over from) the given column and row,
	 * when moving in the given direction. For example (1, 4, UP) returns the cell
	 * at (1, 3).
	 * <p>
	 * If the adjacent cell is outside of the boundaries of the grid, the method
	 * returns null.
	 * 
	 * @param col the given column
	 * @param row the given row
	 * @param dir the direction from the given column and row to the adjacent cell
	 * @return the adjacent cell or null
	 */
	public Cell getAdjacentCell(int col, int row, Direction dir) {
		if(dir == UP) {
			if(row <= 0) {
				return null;
			}
			return grid[row-1][col]; 
		}
		else if (dir == DOWN) {
			if(row >= theHeight-1) {
				return null;
			}
			return grid[row+1][col];
		}
		else if(dir == LEFT) {
			if(col <= 0) {
				return null;
			}
			return grid[row][col-1];
		}
		else{
			if (col >= theWidth-1) {
				return null;
			}
			return grid[row][col+1];			
		}
	}

	/**
	 * Resets the grid. After calling this method the game should have a grid of
	 * size width x height containing all empty cells. Empty means cells with no
	 * walls, exits, etc.
	 * <p>
	 * All lizards should also be removed from the grid.
	 * 
	 * @param width  number of columns of the resized grid
	 * @param height number of rows of the resized grid
	 */
	public void resetGrid(int width, int height) {
		grid = new Cell[height][width];
		numLiz = new ArrayList<Lizard>();
		theWidth = width;
		theHeight = height;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				grid[i][j] = new Cell(j,i);
		    }
	   }
		
	}

	/**
	 * Returns true if a given cell location (col, row) is available for a lizard to
	 * move into. Specifically the cell cannot contain a wall or a lizard. Any other
	 * type of cell, including an exit is available.
	 * 
	 * @param row of the cell being tested
	 * @param col of the cell being tested
	 * @return true if the cell is available, false otherwise
	 */
	public boolean isAvailable(int col, int row) {
		if(grid[row][col].getWall() == null && grid[row][col].getLizard() == null){
			return true;
		}
		return false;
	}

	/**
	 * Move the lizard specified by its body segment at the given position (col,
	 * row) one cell in the given direction. The entire body of the lizard must move
	 * in a snake like fashion, in other words, each body segment pushes and pulls
	 * the segments it is connected to forward or backward in the path of the
	 * lizard's body. The given direction may result in the lizard moving its body
	 * either forward or backward by one cell.
	 * <p>
	 * The segments of a lizard's body are linked together and movement must always
	 * be "in-line" with the body. It is allowed to implement movement by either
	 * shifting every body segment one cell over or by creating a new head or tail
	 * segment and removing an existing head or tail segment to achieve the same
	 * effect of movement in the forward or backward direction.
	 * <p>
	 * If any segment of the lizard moves over an exit cell, the lizard should be
	 * removed from the grid.
	 * <p>
	 * If there are no lizards left on the grid the player has won the puzzle the
	 * the dialog listener should be used to display (see showDialog) the message
	 * "You win!".
	 * <p>
	 * It is possible that the given direction is not in-line with the body of the
	 * lizard (as described above), in that case this method should do nothing.
	 * <p>
	 * It is possible that the given column and row are outside the bounds of the
	 * grid, in that case this method should do nothing.
	 * <p>
	 * It is possible that there is no lizard at the given column and row, in that
	 * case this method should do nothing.
	 * <p>
	 * It is possible that the lizard is blocked and cannot move in the requested
	 * direction, in that case this method should do nothing.
	 * <p>
	 * <b>Developer's note: You may have noticed that there are a lot of details
	 * that need to be considered when implement this method method. It is highly
	 * recommend to explore how you can use the public API methods of this class,
	 * Grid and Lizard (hint: there are many helpful methods in those classes that
	 * will simplify your logic here) and also create your own private helper
	 * methods. Break the problem into smaller parts are work on each part
	 * individually.</b>
	 * 
	 * @param col the given column of a selected segment
	 * @param row the given row of a selected segment
	 * @param dir the given direction to move the selected segment
	 */
	public void move(int col, int row, Direction dir) {
		//checks if the cell is in bounds
		if(col >= 0 && col <= theWidth-1 && row >= 0 && row <= theHeight-1) {
			//checks if the cell has a lizard
			if(grid[row][col].getLizard() != null) {
				//local variables to make the lines shorter
				int tailCol = grid[row][col].getLizard().getTailSegment().getCell().getCol();
				int tailRow = grid[row][col].getLizard().getTailSegment().getCell().getRow();
				int headCol = grid[row][col].getLizard().getHeadSegment().getCell().getCol();
				int headRow = grid[row][col].getLizard().getHeadSegment().getCell().getRow();
				Cell adTailCell = getAdjacentCell(tailCol, tailRow, grid[row][col].getLizard().getTailDirection());
				Cell adHeadCell = getAdjacentCell(headCol, headRow, grid[row][col].getLizard().getHeadDirection());
				Cell adCell = getAdjacentCell(col, row, dir); 
					//if the segment is the headSegment
					if(grid[row][col].getLizard().getHeadSegment() == grid[row][col].getLizard().getSegmentAt(grid[row][col])) {
						//going backwards
						if (grid[row][col].getLizard().getDirectionToSegmentBehind(grid[row][col].getLizard().getSegmentAt(grid[row][col])) == dir) {
							//checks if the spot the tail is going to move to is empty
							if(isAvailable(adTailCell.getCol(), adTailCell.getRow())){
								backward(dir, grid[row][col].getLizard(), tailCol, tailRow);
							}
							 
						}
						//going forwards
						else {
							//adjacent cell exists
							if(adCell != null) {
								//checks if the spot the head is going to move to is empty
								if(isAvailable(adCell.getCol(), adCell.getRow())){
									forward(dir, grid[row][col].getLizard(), headCol, headRow);								
								}
							}
						}
					}
					
					//if the segment is the tailSegment 
					else if(grid[row][col].getLizard().getTailSegment() == grid[row][col].getLizard().getSegmentAt(grid[row][col])) {
						if(grid[row][col].getLizard().getDirectionToSegmentAhead(grid[row][col].getLizard().getSegmentAt(grid[row][col])) == dir) {
							//checks if the spot the head is going to move to is empty
							if(isAvailable(adHeadCell.getCol(), adHeadCell.getRow())){
								forward(dir, grid[row][col].getLizard(), headCol, headRow);							}
						}
						else {
							//adjacent cell exists
							if(adCell != null) {
								//checks if the spot the tail is going to move to is empty
								if(isAvailable(getAdjacentCell(col, row, dir).getCol(), getAdjacentCell(col,row,dir).getRow())){
									backward(dir, grid[row][col].getLizard(), tailCol, tailRow);								
								}
							}
						}
					}
					//if the segment is a middle segment
					else {
						//going forwards
						if(grid[row][col].getLizard().getDirectionToSegmentAhead(grid[row][col].getLizard().getSegmentAt(grid[row][col])) == dir) {
							//checks if the spot the head is going to move to is empty
							if(isAvailable(adHeadCell.getCol(), adHeadCell.getRow())){
								forward(dir, grid[row][col].getLizard(), headCol, headRow);
							}
						}
						// going backwards
						else if(grid[row][col].getLizard().getDirectionToSegmentBehind(grid[row][col].getLizard().getSegmentAt(grid[row][col])) == dir) {
							//checks if the spot the tail is going to move to is empty
							if(isAvailable(adTailCell.getCol(), adTailCell.getRow())){
								backward(dir, grid[row][col].getLizard(), tailCol, tailRow);							
							}
						}
					}
				}
			
			
			//checks if the lizard is on an exit and removes the lizard
			for(int i = 0; i < theHeight; i++) {
				for(int j = 0; j < theWidth; j++) {
					onExit(j,i);
				}
			}
			//displays the winning message when there are no lizard left on the grid
			if (numLiz.size() == 0) {
				dialogListener.showDialog("You Win!");
			}
		}
		
	}
	/**
	 * Moves the lizard forward in the given direction by looping through the lizard
	 * and setting the segment to the previous segment
	 * 
	 * @param dir direction of movement
	 * @param temp lizard on the grid
	 * @param col column on grid
	 * @param row row on grid
	 */
	private void forward(Direction dir, Lizard temp, int col, int row) {
		for(int i = 0; i < temp.getSegments().size()-1; i++) {
			if(i == 0) {
				temp.getSegments().get(i).getCell().removeLizard();
			}
			temp.getSegments().get(i).setCell(temp.getSegments().get(i+1).getCell());
		}
		BodySegment theHead = temp.getSegments().get(temp.getSegments().size()-1);
		theHead.setCell(getAdjacentCell(col, row, dir));
		//done?
	}
	/**
	 * Moves the lizard backwards in the given direction by looping through the lizard
	 * and setting the segment to the previous segment
	 * 
	 * @param dir direction of movement
	 * @param temp lizard on the grid
	 * @param col column on grid
	 * @param row row on grid
	 */
	private void backward(Direction dir, Lizard temp, int col, int row) {
		for(int i = temp.getSegments().size()-1; i > 0; i--) {
			//removes the head
			if(i == temp.getSegments().size()-1) {
				temp.getSegments().get(i).getCell().removeLizard();
			}
			temp.getSegments().get(i).setCell(temp.getSegments().get(i-1).getCell());
		}
		//sets the tail segment
		BodySegment theTail = temp.getSegments().get(0);
		theTail.setCell(getAdjacentCell(col, row, dir));
	}
	/**
	 * Checks if the lizard is on the exit 
	 * by comparing the cell the exit is on and the cell the lizard is on
	 * if lizard and the exit of on the same cell the lizard is removed
	 * 
	 * @param col column of the grid
	 * @param row row of the grid
	 */
	private void onExit(int col, int row) {
		if(grid[row][col].getExit() != null && grid[row][col].getLizard() != null) {
			removeLizard(grid[row][col].getLizard());
		}
	}
	
	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}

	@Override
	public String toString() {
		String str = "---------- GRID ----------\n";
		str += "Dimensions:\n";
		str += getWidth() + " " + getHeight() + "\n";
		str += "Layout:\n";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			for (int x = 0; x < getWidth(); x++) {
				str += getCell(x, y);
			}
		}
		str += "\nLizards:\n";
		for (Lizard l : getLizards()) {
			str += l;
		}
		str += "\n--------------------------\n";
		return str;
	}
}
