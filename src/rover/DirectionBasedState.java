package rover;





/*
 * This class describes where a Mars Rover pointing in a given direction 
 *  will move or turn.
 *  
 *  _ahead is the direction the Rover is currently pointing
 *  _left is the direction it will point if it turns left
 *  _right is the direction it will point if it turns right
 * 	_dx is the distance it will move eastward in one standard move (the x axis runs west to east)
 *  _dy is the distance it will move northeward in one standard move (the y axis runs south to north)
 *  
 *  _code is the input/output code for direction _ahead
 */

public class DirectionBasedState {
	public static enum Direction { INVALID, NORTH, EAST, SOUTH, WEST ; }
	
	private Direction _ahead, _left, _right;
	private int       _dx, _dy;
	private char      _code;

	public Direction getAhead() 	{ return _ahead; }
	public Direction getLeft()      { return _left; }
	public Direction getRight()     { return _right; }
	public int       getDx()  		{ return _dx; }
	public int       getDy()  		{ return _dy; }
	public char      getCode()  	{ return _code; }
}
