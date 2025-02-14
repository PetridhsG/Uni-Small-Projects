#!/bin/bash

javac Main.java

# Check if the compilation was successful
if [ $? -eq 0 ]; then
    # Run the Java program with the specified command line arguments
	# The last argument is the totalTime and the others the time of each family member
    java Main 1 3 6 8 12 30
else
    echo "Compilation failed. Check for errors in your .java files."
fi


