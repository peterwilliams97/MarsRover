package rover;


import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;


public class RoverInstructionProcesser {
	
	static public class Boundary  { 
		/* In this simple case the boundary is a rectangle with lower left at (_x0,_y0)
		 *  and upper right at (_x1, _x1) in the x,y coordinate space described in README.txt
		 */
		private int _x0, _y0, _x1, _y1;
		
		public  Boundary(String description) {
			String[] parts = Utils.splitString(description);;
			assert(parts.length >= 2);
			_x0 = 0;
			_y0 = 0;
			_x1 = Integer.parseInt(parts[0]);
			_y1 = Integer.parseInt(parts[1]);
		}
		
		public boolean contains(int x, int y) {
			return(_x0 <= x && x <= _x1 && 
				   _y0 <= y && y <= _y1);
		}
	};
	
	/*
	 * 	The rover must stay within this boundary
	 */
	private  Boundary  	_boundary;	
	
	/*
	 * Current state of this rover.
	 */
	private  RoverState _state;
	
	/*
	 * Construct a rover
	 * over comes into exsitence when its starting state is specified (see README.txt)
	 * By this time its movement boundary is known
	 * @param boundary - rover must remain within this 
	 * @startingState - string repesentation of this rover's initial state
	 */
	public RoverInstructionProcesser(Boundary boundary, String startingState) throws IOException {
		_boundary = boundary;
		_state = new RoverState(startingState);
	}

	/*
	 * Move the rover according to a string of single character instructions as specified in README.txt
	 * If a move is invalid then terminate movement, remaining at last valid state
	 * @param instructionList - string of single character instructions
	 */
	public void processInstructionList(String instructionList) {
		boolean canMove = true;
		CharacterIterator it = new StringCharacterIterator(instructionList);
		for (char code=it.first(); code != CharacterIterator.DONE; code=it.next()) {
			canMove = processInstruction(code);
			if (!canMove)
				break;
	    }
	}

	/*
	 *  Move the rover according to a single instruction if the move is valid, otherwise do
	 *  nothing and report a failure to move
	 *  @param instuction - single character instruction. See README.txt
	 *  @return true if move was valid, false if not.
	 */
	private boolean processInstruction(char instruction) {
		RoverState newState = new RoverState(_state);
		newState.performInstruction(instruction);
		boolean canMove = isValidPosition(newState);
		if (canMove) {
			_state = newState;
		}
		return canMove;
	}
	
	/*
	 * Check if a state is valid
	 * @param state - a rover state
	 * @return true if state is valid, false if it is not
	 */
	private boolean isValidPosition(RoverState state) {
		return _boundary.contains(state._x, state._y); 
	}
	
	/*
	 * @return rover state
	 */
	public String getState() {
		return _state.asString(); 
	}

	
	
}
