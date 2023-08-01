#!/usr/bin/env python

import sys

current_word = None
current_count = 0

# Process the output of the Mapper from standard input (stdin)
# and perform the Reduce operation
for line in sys.stdin:
    # Parse the input from Mapper
    word, count = line.strip().split('\t')

    # Convert count to integer
    count = int(count)

    # If the word is the same as the previous one, increase the count
    if current_word == word:
        current_count += count
    else:
        # If the word is different, output the previous word and its count
        if current_word:
            print(f"{current_word}\t{current_count}")

        # Update current word and count
        current_word = word
        current_count = count

# Output the last word and its count
if current_word:
    print(f"{current_word}\t{current_count}")
