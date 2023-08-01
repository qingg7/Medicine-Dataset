#!/usr/bin/env python

import sys

# Process input from standard input (stdin)
for line in sys.stdin:
    # Convert the input line to string and remove leading/trailing whitespaces
    line = line.strip()
    
    # Split the line by commas (assuming it's a CSV file) to get the attributes
    attributes = line.split(",")

    # Check if the line has enough attributes and the "DrugName" attribute is available
    if len(attributes) >= 4:
        # Get the value of the "DrugName" attribute
        drug_name = attributes[3].strip()

        # Split the review into words using whitespace as delimiter
        words = drug_name.split()

        # Emit each word with a count of 1
        for word in words:
            # Emit the key-value pair to standard output (stdout)
            print(f"{word}\t1")
