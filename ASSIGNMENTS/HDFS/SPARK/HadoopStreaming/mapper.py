#!/usr/bin/python3

import sys

# Read the lines from STDIN
for line in sys.stdin:
    # Strip the code
    line = line.strip()
    # Split into words
    words = line.split()
    # Print onto the screen with appending 1
    for word in words:
        print(word, 1)
