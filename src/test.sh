#!/bin/bash
java Rover < input.txt > output.txt
diff output.txt expected.txt
