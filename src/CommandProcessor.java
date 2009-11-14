import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileReader;
import java.io.IOException;
import rover.RoverInstructionProcesser;
import rover.RoverInstructionProcesser.Boundary;



/*
 * Entry point for Mars Rover simulator

 */

public class  CommandProcessor {
	
	
	/**
	 * 
	 * Program starts here.
	 * Set up input and output character streams and call processCommandStream()
	 * with these streams
	 * 
	 * @param args - command line arguments
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException  {
		BufferedWriter output;
	    BufferedReader input; 
	    boolean externFiles = true;
		if (externFiles) {
			assert(args.length >= 1);		
			String infilename = args[0];
			String outfilename = infilename + ".out";
			input =  new BufferedReader(new FileReader(new File(infilename)));
			output = new BufferedWriter(new FileWriter(new File(outfilename)));
		}
		else {
			input= new BufferedReader(new InputStreamReader(System.in));
			output = new BufferedWriter(new OutputStreamWriter(System.out));
		}
		
		try {
			processCommandStream(input, output) ;
		} finally {
			input.close();
			output.close();
		}
		
	}
	
	/*
	 * Process a stream of Rover commands from input and write reponse to output
	 *  The format of command stream is given in README.TXT. The summary is
	 *  
	 *  	The first line of input is the upper-right coordinates of the plateau
	 *  	Each rover has two lines of input. The first line gives the 
	 *  	rover's position, and the second line is a series of instructions telling
	 *  	the rover how to explore the plateau.
	 *  	The output for each rover should be its final co-ordinates and heading.
	 *  
	 *  @param input - input is read from this character stream
	 *  @param input - output is written to this character stream 
	 */
	static void processCommandStream(BufferedReader input, BufferedWriter output) throws IOException {
		String line;
		Boundary boundary = null;
		
		if ((line = input.readLine()) != null) 
			boundary = new Boundary(line);
		assert(boundary != null);
		
		while (line != null) {
			if ((line = input.readLine()) != null) {
				RoverInstructionProcesser vehicle = new  RoverInstructionProcesser(boundary, line);
				if ((line = input.readLine()) != null) 
					vehicle.processInstructionList(line);
				output.write(vehicle.getState() + System.getProperty("line.separator"));
				output.flush();
			}
		}
	}

	
	
}
