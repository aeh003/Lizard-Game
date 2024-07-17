package hw3;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import api.BodySegment;
import api.Cell;
import api.Exit;
import api.Wall;

/**
 * Utility class with static methods for loading game files.
 * 
 * @author aeh003 (Amelia Humphrey)
 */
public class GameFileUtil {
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, LizardGame game) { 
			
			
		File file = new File(filePath);
		Scanner scanner = null;	
		//tries the file
		try {
			scanner = new Scanner(file);
		}
		//if the file is not found
		catch (FileNotFoundException e)
		{
			System.err.println("FILE NOT FOUND!!!");
		}
		//local variables 
		int columns = 0;
		int rows = 0;
		int counter = 0;
		int numRow = 0; 
		//loops until there are not lines left to be read
		while(scanner.hasNextLine()) {
			String theLine = scanner.nextLine();
			//checks if it is the first line and set the columns and rows
			if(counter == 0) {
				columns = Integer.parseInt(theLine.substring(0, 1));
				rows = Integer.parseInt(theLine.substring(2));
				//creates the grid 
				game.resetGrid(columns, rows);
				
			}
			//checks to see if the line is the line of lizard segments
			else if (theLine.charAt(0) == 'L') {
				ArrayList<BodySegment> body = new ArrayList<BodySegment>(); 
				Lizard lizzy = new Lizard();
				Scanner scanCoord = new Scanner(theLine);
				//read the L so I do not have worry about it when I loop there the rest of the line
				scanCoord.next();
				//loops until there are not more coordinates(strings) in the line 
				while(scanCoord.hasNext()) {
					//splits the line up at the space making the str be #,#
					String str = scanCoord.next(); 
					//uses substring to get the numbers for the col and row variables 
					int col = Integer.parseInt(str.substring(0,1));
					int row = Integer.parseInt(str.substring(2));
					//adds the bodysegment to the arrayList of bodysegments called body
					body.add(new BodySegment(lizzy, game.getCell(col, row)));
				}
				lizzy.setSegments(body);
				//adds the lizard (lizzy) to the game
				game.addLizard(lizzy);
				scanCoord.close();
				
			}
			
			else {
				for(int numCol = 0; numCol < columns; numCol++) {
					char theChar = theLine.charAt(numCol);
					if(theChar == 'W') {
						//adds the wall to the game board
						Wall wall = new Wall(game.getCell(numCol, numRow));
						game.addWall(wall); 
					}
					
					if(theLine.charAt(numCol)== 'E') {
						//adds exit to the game board
						Exit exit = new Exit(game.getCell(numCol, numRow));
						game.addExit(exit); 
					}
					
				}
				//increases the row
				numRow++;
				
			}

			counter++;
		}		
		scanner.close();
	}
}




