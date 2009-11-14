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
	
	private  Boundary  		_boundary;	
	private  RoverState 	_state;
	
	public RoverInstructionProcesser(Boundary boundary, String startingState) throws IOException {
		_boundary = boundary;
		_state = new RoverState(startingState);
	}

	public void processInstructionList(String instructionList) {
		boolean canMove = true;
		CharacterIterator it = new StringCharacterIterator(instructionList);
		for (char code=it.first(); code != CharacterIterator.DONE; code=it.next()) {
			canMove = processInstruction(code);
			if (!canMove)
				break;
	    }
	}

	private boolean processInstruction(char instruction) {
		RoverState newState = new RoverState(_state);
		newState.performInstruction(instruction);
		boolean canMove = isValidPosition(newState);
		if (canMove) {
			_state = newState;
		}
		return canMove;
	}
	
	private boolean isValidPosition(RoverState state) {
		return _boundary.contains(state._x, state._y); 
	}
	
	public String getState() {
		return _state.asString(); 
	}

	
	
}
