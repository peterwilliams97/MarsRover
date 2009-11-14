package rover;



import java.util.Map;

import rover.DirectionBasedState.Direction;


class RoverState {
	private static  Map<Direction, DirectionBasedState> _directionStateTable;
	private static  Map<Direction, Character> _directionCodeTable;
	private static  Map<Character, Direction> _codeDirectionTable;
	
	int 		_x, _y;
	Direction  _direction;
	RoverState(RoverState state) {
		_x = state._x;
		_y = state._y;
		_direction = state._direction;
	}
	/*
	 * According to  README.txt the vehicle state is always set by an
	 * input command. Therefore we construct Rovers from this input command
	 */
	public  RoverState(String startingState) {
		_directionStateTable = Tables_JSON.get().getDirectionStateTable(); 
		_directionCodeTable = Tables_JSON.get().getDirectionCodeTable();  
		_codeDirectionTable = Tables_JSON.get().getCodeDirectionTable();  
		
		String[] parts = Utils.splitString(startingState);;
		assert(parts.length >= 3);
		_x = Integer.parseInt(parts[0]);
		_y = Integer.parseInt(parts[1]);
		_direction = stringToDirection(parts[2]);
	}
	
	void performInstruction(char instruction) {
		switch (instruction) {
		case 'M': move(); break;
		case 'L': turnLeft(); break;
		case 'R': turnRight(); break;
		}
	}
	void move() {
		_x += _directionStateTable.get(_direction).getDx();
		_y += _directionStateTable.get(_direction).getDy();
	}
	void turnLeft() {
		_direction = _directionStateTable.get(_direction).getLeft();
	}
	void turnRight() {
		_direction = _directionStateTable.get(_direction).getRight();
	}
	
	public String asString() {
		return "" + _x + " " + _y + " " + directionToCode(_direction);
	}
	
	
	private static Direction stringToDirection(String s) {
		char code = s.toUpperCase().charAt(0);
		return _codeDirectionTable.get(code);
	}
	
	private static char directionToCode(Direction d) {
		return _directionCodeTable.get(d);
	}
}
	