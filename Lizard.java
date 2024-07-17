package hw3;

import static api.Direction.*;

import java.util.ArrayList;

import api.BodySegment;
import api.Cell;
import api.Direction;

/**
 * Represents a Lizard as a collection of body segments.
 * 
 * @author aeh003 (Amelia Humphrey)
 */

public class Lizard {
	
	
	/**
	 * list of the BodySegments
	 */
	private ArrayList <BodySegment> theLiz ; 
	/**
	 * Constructs a Lizard object.
	 */
	public Lizard() {
		theLiz = new ArrayList<BodySegment>();
	}

	/**
	 * Sets the segments of the lizard. Segments should be ordered from tail to
	 * head.
	 * 
	 * @param segments list of segments ordered from tail to head
	 */
	public void setSegments(ArrayList<BodySegment> segments) {
		theLiz= segments;
		
	}

	/**
	 * Gets the segments of the lizard. Segments are ordered from tail to head.
	 * 
	 * @return a list of segments ordered from tail to head
	 */
	public ArrayList<BodySegment> getSegments() {
		return theLiz; 
	}

	/**
	 * Gets the head segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the head segment
	 */
	public BodySegment getHeadSegment() {
		if(theLiz.size() > 0) {
			return theLiz.get(theLiz.size()-1);
		}
		return null;
	}

	/**
	 * Gets the tail segment of the lizard. Returns null if the segments have not
	 * been initialized or there are no segments.
	 * 
	 * @return the tail segment
	 */
	public BodySegment getTailSegment() {
		if(theLiz.size() < 0) {
			return null;
		}
		return theLiz.get(0);
	}

	/**
	 * Gets the segment that is located at a given cell or null if there is no
	 * segment at that cell.
	 * 
	 * @param cell to look for lizard
	 * @return the segment that is on the cell or null if there is none
	 */
	public BodySegment getSegmentAt(Cell cell) {
		//cycles through theLiz and finds the given cell's index
		for (int i = 0; i < theLiz.size(); i++) {
			if(theLiz.get(i).getCell() == cell) {
				return theLiz.get(i);
			}
		}
		return null;
	}

	/**
	 * Get the segment that is in front of (closer to the head segment than) the
	 * given segment. Returns null if there is no segment ahead.
	 * 
	 * @param segment the starting segment
	 * @return the segment in front of the given segment or null
	 */
	public BodySegment getSegmentAhead(BodySegment segment) {
		if(theLiz.size() == 1) {
			return null;
		}
		//gets the index of the given segment
		int index = theLiz.indexOf(segment);
		//checks if the segment ahead will exist
		if(theLiz.size()-1 <= index || index == -1) {
			return null;
		}
		//gets the segmentAhead
		return theLiz.get(index + 1);
	}

	/**
	 * Get the segment that is behind (closer to the tail segment than) the given
	 * segment. Returns null if there is not segment behind.
	 * 
	 * @param segment the starting segment
	 * @return the segment behind of the given segment or null
	 */
	public BodySegment getSegmentBehind(BodySegment segment) {
		if(theLiz.size() == 1) {
			return null;
		}
		//gets the index of the given segment
		int index = theLiz.indexOf(segment);
		if(index <= 0) {
			return null;
		}
		//gets the segmentBehind
		return theLiz.get(index - 1);
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment ahead (in front of) of it. Returns null if there is no segment ahead
	 * of the given segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment ahead of the given segment or null
	 */
	public Direction getDirectionToSegmentAhead(BodySegment segment) {
		int currCell = theLiz.indexOf(segment);
		//the cell ahead
		int nextCell = currCell + 1;
		if(currCell ==  theLiz.size()-1 || theLiz.size()==1) {
			return null;
		}
		return direct(theLiz.get(currCell).getCell(),theLiz.get(nextCell).getCell());
	}

	/**
	 * Gets the direction from the perspective of the given segment point to the
	 * segment behind it. Returns null if there is no segment behind of the given
	 * segment.
	 * 
	 * @param segment the starting segment
	 * @return the direction to the segment behind of the given segment or null
	 */
	public Direction getDirectionToSegmentBehind(BodySegment segment) { 
		
		int currCell = theLiz.indexOf(segment);
		//the cell behind
		int nextCell = currCell - 1;
		if(currCell == 0 || theLiz.size() == 1) {
			return null;
		}
		return direct(theLiz.get(currCell).getCell(),theLiz.get(nextCell).getCell());
		
		
	}
	/**
	 * Gets the direction of the nextCell based on where the nextCell is
	 * 
	 * @param currCell the current cell
	 * @param nextCell the cell next to the current cell
	 * @return the direction of the currCell relative to the nextCell
	 */
	private Direction direct(Cell currCell, Cell nextCell) {
		if((currCell.getCol() == nextCell.getCol()) && (currCell.getRow() == nextCell.getRow() + 1)) {
			return UP;
		}
		else if((currCell.getCol() == nextCell.getCol()) && (currCell.getRow() == nextCell.getRow() - 1)) {
			return DOWN;
		}
		else if((currCell.getCol() == nextCell.getCol()+ 1) && (currCell.getRow() == nextCell.getRow())){
			return LEFT; 
		}
		else {
			return RIGHT;
		}
	}

	/**
	 * Gets the direction in which the head segment is pointing. This is the
	 * direction formed by going from the segment behind the head segment to the
	 * head segment. A lizard that does not have more than one segment has no
	 * defined head direction and returns null.
	 * 
	 * @return the direction in which the head segment is pointing or null
	 */
	public Direction getHeadDirection() {
		if(theLiz.size() == 1) {
			return null;
		}
		
		return direct(theLiz.get(theLiz.size() - 2).getCell(),getHeadSegment().getCell());
	}

	/**
	 * Gets the direction in which the tail segment is pointing. This is the
	 * direction formed by going from the segment ahead of the tail segment to the
	 * tail segment. A lizard that does not have more than one segment has no
	 * defined tail direction and returns null.
	 * 
	 * @return the direction in which the tail segment is pointing or null
	 */
	public Direction getTailDirection() {
			if(theLiz.size() == 1) {
				return null;
			}
			return direct(theLiz.get(1).getCell(),getTailSegment().getCell());
	}

	@Override
	public String toString() {
		String result = "";
		for (BodySegment seg : getSegments()) {
			result += seg + " ";
		}
		return result;
	}
}
