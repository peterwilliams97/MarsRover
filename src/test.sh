#!/bin/bash


echo This script tests the Rover program
echo If there is any text between the dashed lines then the
echo test has failed. Otherwise the test has succeeded.

echo -------------- Starting Test --------------
java -jar Rover.jar < input.txt > output.txt
diff output.txt expected.txt

echo --------------   Test Done   --------------
