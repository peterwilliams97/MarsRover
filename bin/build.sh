#!/bin/bash

pushd rover

javac DirectionBasedState.java
javac DirectionTable.java
javac Vehicle.java

popd

javac EntryPoint.java

