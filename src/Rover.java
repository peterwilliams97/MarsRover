import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("serial")
public class Rover {
	static class Xform {
		int _distance, _dtheta;
		Xform(int distance, int dtheta) {
			_distance = distance;
			_dtheta = dtheta;
		}
	};
	static class State {
		int _x , _y, _theta;
		State (State state) {
			_x = state._x;
			_y = state._y;
			_theta = state._theta;
		}
		
		State(String description) {
			String[] parts = description.split(" ");
			_x = Integer.parseInt(parts[0]);
			_y = Integer.parseInt(parts[1]);
			_theta = _codeAngleMap.get(parts[2].charAt(0));
		}
		String asString() {
			return "" + _x + " " + _y + " " + _angleCodeMap.get(_theta);
		}
		void xform(Xform m) {
			_theta = (_theta + m._dtheta) % 4;
			if (_theta < 0)
				_theta += 4;
			_x += Math.round(Math.cos((double)_theta * Math.PI/2.0) * (double)m._distance);
			_y += Math.round(Math.sin((double)_theta * Math.PI/2.0) * (double)m._distance);
		}
	};
	
	
	static final Map<Character, Integer> _codeAngleMap = new HashMap<Character, Integer>() {{
	    put('E', 0);
	    put('N', 1);
	    put('W', 2);
	    put('S', 3);
	}};
	static final Map<Integer, Character> _angleCodeMap = new HashMap<Integer, Character>() {{
	    put(0, 'E');
	    put(1, 'N');
	    put(2, 'W');
	    put(3, 'S');
	}};
	static final Map<Character, Xform> _codeXformMap = new HashMap<Character, Xform>() {{
	    put('M', new Xform(1, 0));
	    put('L', new Xform(0, 1));
	    put('R', new Xform(0, -1));
	}};
	
	
	State _state;
	
	Rover(String description) {
		_state = new State(description);
	}

		
	boolean performInstruction(char code) {
		Xform m = _codeXformMap.get(code);
		State newState = new State(_state);
		newState.xform(m);
		boolean valid = isValidState(newState);
		if (valid)
			_state = newState;
		return valid;
	}
	
	void processInstructionList(String instructionList) {
		if (isValidState(_state)) {
			CharacterIterator it = new StringCharacterIterator(instructionList);
			for (char code=it.first(); code != CharacterIterator.DONE; code=it.next()) {
				if (!performInstruction(code))
					break;
		    }
		}
	}
	
	String getState() {
		return _state.asString();
	}
	
	static public class Boundary  { 
		private int _x0 = 0, _y0 = 0, _x1 = 0, _y1 = 0;
		public  Boundary(String description) {
			String[] parts = description.split(" ");
			_x1 = Integer.parseInt(parts[0]);
			_y1 = Integer.parseInt(parts[1]);
		}
		public boolean contains(int x, int y) {
			return(_x0 <= x && x <= _x1 && 
				   _y0 <= y && y <= _y1);
		}
	};
	
	static Boundary _boundary;
	static List<Rover> _completedRovers = new ArrayList<Rover>();
	
	static boolean isValidState(State state) {
		boolean valid = true;
		if (!_boundary.contains(state._x, state._y))
			valid = false;
		for (Rover rover: _completedRovers) {
			if (rover._state._x == state._x && rover._state._y == state._y) {
				valid = false;
				break;
			}
		}
		return valid;
	}
	
	static void processCommandStream(BufferedReader input, BufferedWriter output) throws IOException {
		String line;
		if ((line = input.readLine()) != null) 
			_boundary = new Boundary(line);
		while (line != null) {
			if ((line = input.readLine()) != null) {
				Rover rover = new Rover(line);
				if ((line = input.readLine()) != null) {
					rover.processInstructionList(line);
				}
				_completedRovers.add(rover);
				output.write(rover.getState() + System.getProperty("line.separator"));
				output.flush();
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException  {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));   
	    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out)); 
	   	try {
			processCommandStream(input, output);
		} finally {
			input.close();
			output.close();
		}
	}

}
