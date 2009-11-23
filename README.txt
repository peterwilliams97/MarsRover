JAVA SOLUTION TO THE MARS ROVER PROBLEM
=======================================

The accompanying code and programs are a solution to the Mars Rover problem 
described below.

INSTALLATION AND TESTING
------------------------
Start a bash shell
Unpack the zip archive and cd to the directory it was unpacked  to.

To build the code (there is no need to do this since the code will run as supplied)
	ant jar

To test the code (guidelines on interpreting results will be displayed)
	sh ./test.sh

To build and test the code in one step
	ant test


THE CODE
--------
The code is contained in single file called Rover.java and is described in that file's
comments. Its key elements are

	a Rover location/direction state, 
	transforms on that state, and
	mappings from the specified input/output formats to those states and transforms.

There are no JUnit tests since the code in its current form is completely exercised 
from the command line and I currently have no intention to re-use any of the code.


NOTE ON PROBLEM STATEMENT
-------------------------

The problem is under-specified in that it does not describe what do if a Rover
hits the boundary or another Rover. There are several ways to handle this. I 
chose to stop the Rover and report its position. I thought that would be the easiest 
behaviour for the Rover controllers in NASA to deal with since it leaves them
with a Rover in a known position.

The problem statement did not raise the possibilty of malformed input so I did
not handle this.


PROBLEM
-------
A squad of robotic rovers are to be landed by NASA on a plateau on Mars.
This plateau, which is curiously rectangular, must be navigated by the
rovers so that their on-board cameras can get a complete view of the
surrounding terrain to send back to Earth.

A rover's position and location is represented by a combination of x and y
co-ordinates and a letter representing one of the four cardinal compass
points. The plateau is divided up into a grid to simplify navigation. An
example position might be 0, 0, N, which means the rover is in the bottom
left corner and facing North.

In order to control a rover, NASA sends a simple string of letters. The
possible letters are 'L', 'R' and 'M'. 'L' and 'R' makes the rover spin 90
degrees left or right respectively, without moving from its current spot.
'M' means move forward one grid point, and maintain the same heading.

Assume that the square directly North from (x, y) is (x, y+1).

INPUT:
The first line of input is the upper-right coordinates of the plateau, the
lower-left coordinates are assumed to be 0,0.

The rest of the input is information pertaining to the rovers that have
been deployed. Each rover has two lines of input. The first line gives the
rover's position, and the second line is a series of instructions telling
the rover how to explore the plateau.

The position is made up of two integers and a letter separated by spaces,
corresponding to the x and y co-ordinates and the rover's orientation.

Each rover will be finished sequentially, which means that the second rover
won't start to move until the first one has finished moving.


OUTPUT
The output for each rover should be its final co-ordinates and heading.

INPUT AND OUTPUT

Test Input:
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM

Expected Output:
1 3 N
5 1 E






