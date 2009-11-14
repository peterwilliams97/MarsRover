package rover;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rover.DirectionBasedState.Direction;

/*
 * Read some tables from a JSON file 
 * Storing tables in JSON files makes them easier to maintain than
 * if they were embedded in a code file.
 */


public class Tables_JSON {
	
	/*
	 * Tables_JSON is a singleton since it implements a state machine 
	 * that is common to all Vehicles
	 * 
	 * The next few lines are the singleton implementation
	 */
	private static Tables_JSON _instance;
	public static Tables_JSON get() {
		if (_instance == null)
			_instance = new Tables_JSON(_tableFile);
		return _instance;
	}

	/* 
	 * The single state label is loaded from this JSON file. 
	 */
	private static final String _tableFile = "direction_based_state.json";
	
	/*
	 * The class implementation is below
	 */
	
	/*
	 * _directionStateTable is a state  table for directions.
	 * _directionStateTable.get(p) is the DirectionBasedState for compass point p
	 * (@see DirectionBasedState)
	 */
	private  Map<Direction, DirectionBasedState> _directionStateTable;
	
	/*
	 * _directionCodeTable and _codeDirectionTable are look-up tables
	 * that convert between Direction enum and single character codes.
	 */
	private  Map<Direction, Character> _directionCodeTable;
	private  Map<Character, Direction> _codeDirectionTable;
		
	/*
	 * Initialisation is just reading filling the tables with values
	 * read from a JSON file then validating the tables
	 * @param fileName - name of JSON file
	 */
	private Tables_JSON(final String fileName)   {
		readFromFile(fileName);
		validateTable();
	}
	
	/*
	 * getters
	 */
	public  Map<Direction, DirectionBasedState> getDirectionStateTable() {
		return _directionStateTable;
	}
		public  Map<Direction, Character> getDirectionCodeTable() {
		return _directionCodeTable;
	}
		public  Map<Character, Direction> getCodeDirectionTable() {
		return _codeDirectionTable;
	}
	
	
	/*
	 * Load _directionStateTable from a JSON file
	 * @param filename - name of JSON file
	 */
	private void readFromFile(final String fileName)  {
		_directionStateTable = new HashMap<Direction, DirectionBasedState>(); 
		_directionCodeTable = new HashMap<Direction, Character>(); 
		_codeDirectionTable = new HashMap<Character, Direction>(); 
		Gson gson = new Gson();
		DirectionBasedState[] dbsArray = null;
		String dbsJson = getFileContents(fileName);	
		try {
			dbsArray = gson.fromJson(dbsJson, DirectionBasedState[].class);
		} catch (JsonParseException e) {
			System.err.println("Error parsing " + fileName + ": " + e.getMessage());
		}
		for (int i = 0; i < dbsArray.length; i++){
			DirectionBasedState dbs = dbsArray[i];
			_directionStateTable.put(dbs.getAhead(), dbs);
			_directionCodeTable.put(dbs.getAhead(), dbs.getCode());
			_codeDirectionTable.put(dbs.getCode(), dbs.getAhead());
		}
 	}	
	
	/*
	 * Read a text file into a string 
	 * @param fileName - name of text file
	 * @return string with contents of file
	 */
	static public String getFileContents(String fileName) {
		StringBuilder contents = new StringBuilder();
		try {
			BufferedReader input = new BufferedReader(new FileReader(new File(fileName)));
			try {
			    String line = null  ; 
			    while (( line = input.readLine()) != null) {
			        contents.append(line);
			        contents.append(System.getProperty("line.separator"));
			    }
			} finally {
				input.close();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Configuration file not found: " + e.getMessage());
		}	catch (IOException e) {
			System.err.println("Configuration file error: " + e.getMessage());
		}
		return contents.toString();
	}
	
	/*
	 * Validate _directionStateTable state table
	 * Checks basic symmetries of the 4 compass points
	 * Throws exception when table is invalid. 
	 * NOTE: This assumes that asserts are enabled -ea command line switch
	 */
	private void validateTable() {
		assert(_directionStateTable.size() == 4);
		for (Direction d: _directionStateTable.keySet()) {
			Direction left = _directionStateTable.get(d).getLeft();
			Direction right = _directionStateTable.get(d).getRight();
			assert(_directionStateTable.get(left).getRight() == d);
			assert(_directionStateTable.get(right).getLeft() == d);
			assert(isHorizontal(d) == isVertical(right));
			assert(isVertical(d) == isHorizontal(right));
		}
	}
	
	private boolean isHorizontal(Direction d) {
		return _directionStateTable.get(d).getDy() == 0;
	}
	
	private boolean isVertical(Direction d) {
		return _directionStateTable.get(d).getDx() == 0;
	}
	
	
}
