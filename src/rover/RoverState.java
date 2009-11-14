package rover;

import java.util.Map;
import rover.DirectionBasedState.Direction;

/*
 * This class describes the state of a rover.
 */
class RoverState {
	
	/* The rover state comprises 
	 * 		x and y coordinates: _x _y  
	 * 		the direction the rover is pointing: _direction
	 * The x axis is west-east with west negative and east positive
	 * The y axis is south-north with south negative and north positive
	 */
	int 		_x, _y;
	Direction  _direction;
	
	// Look up table for computing rover actions based on current state
	private static  Map<Direction, DirectionBasedState> _directionStateTable = Tables_JSON.get().getDirectionStateTable(); 
	// Look up table for character code to Direction conversion
	private static  Map<Direction, Character> _directionCodeTable = Tables_JSON.get().getDirectionCodeTable();
	// Look up table for Direction to character code conversion
	private static  Map<Character, Direction> _codeDirectionTable = Tables_JSON.get().getCodeDirectionTable();
	
		
	/*
	 * RoverState's get copies by value so a copy ctor is needed
	 * @param state - state being copies
	 */
	RoverState(RoverState state) {
		_x = state._x;
		_y = state._y;
		_direction = state._direction;
	}
	
	/*
	 * Construct a rover state from a string description
	 * A rover comes into existence when its starting state is specified (see README.txt)
	 * @startingState - string representation of this rover's initial state
	 */
	public  RoverState(String startingState) {
		String[] parts = Utils.splitString(startingState);;
		assert(parts.length >= 3);
		_x = Integer.parseInt(parts[0]);
		_y = Integer.parseInt(parts[1]);
		_direction = stringToDirection(parts[2]);
	}
	
	/*
	 * Update state according to a single character instruction as described in README.txt
	 * @param instruction - a single character instruction
	 */
	void performInstruction(char instruction) {
		switch (instruction) {
		case 'M': move(); break;
		case 'L': turnLeft(); break;
		case 'R': turnRight(); break;
		}
	}
	
	/*
	 * Move ahead one unit
	 */
	void move() {
		_x += _directionStateTable.get(_direction).getDx();
		_y += _directionStateTable.get(_direction).getDy();
	}
	
	/*
	 * Turn 90 degrees counter clockwise
	 */
	void turnLeft() {
		_direction = _directionStateTable.get(_direction).getLeft();
	}
	
	/*
	 * Turn 90 degrees clockwise
	 */
	void turnRight() {
		_direction = _directionStateTable.get(_direction).getRight();
	}
	
	/*
	 * @return string representation of state
	 */
	public String asString() {
		return "" + _x + " " + _y + " " + directionToCode(_direction);
	}
	
	/*
	 * Convert a single character string to a direction
	 * @param s - string to convert
	 * @return direction matching s
	 */
	private static Direction stringToDirection(String s) {
		char code = s.toUpperCase().charAt(0);
		return _codeDirectionTable.get(code);
	}
	
	/*
	 * Convert a direction to a  single character  
	 * @param direction
	 * @return character matching direction
	 */
	private static char directionToCode(Direction d) {
		return _directionCodeTable.get(d);
	}
}
	