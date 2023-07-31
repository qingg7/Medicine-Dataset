import pandas as pd
import time

# Function to perform Word Count on the 'DrugName' attribute
def word_count(data_frame):
    word_count_dict = {}
    for review in data_frame['DrugName']:
        words = review.split()
        for word in words:
            word = word.lower()
            word = word.strip('.,!?()[]{}":;')
            if word not in word_count_dict:
                word_count_dict[word] = 1
            else:
                word_count_dict[word] += 1
    return word_count_dict

# Load the CSV file from the raw link into a DataFrame
file_url = 'https://raw.githubusercontent.com/qingg7/Medicine-Dataset/main/Sem%209.csv'
data_frame = pd.read_csv(file_url)

# Start the timer
start_time = time.time()

# Perform Word Count on the 'DrugName' attribute
word_count_result = word_count(data_frame)

# End the timer
end_time = time.time()

# Display the word count results
for word, count in word_count_result.items():
    print(f"{word}: {count}")

# Calculate processing time in seconds
processing_time = end_time - start_time
print("Processing time: {:.2f} seconds".format(processing_time))