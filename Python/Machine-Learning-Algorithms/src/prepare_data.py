import re
import os
import csv
from itertools import islice

imdb_data_path = 'C:\\Users\\giann\\Desktop\\aclImdb'
working_path = 'C:\\Users\\giann\\OneDrive - aueb.gr\\sxolh\Mathimata\\5o Eksamhno\\Texnhth Nohmoshnh\\Ergasies\\erg2\\AIAssignment2'
working_data_path = working_path + "\\data\\"
stopwords_path = working_data_path + "stopwords.txt"
vocabulary_pos_path = working_data_path + "vocabulary_pos.csv"
vocabulary_pos_cut_path = working_data_path + "vocabulary_pos_cut.csv"
vocabulary_neg_path = working_data_path + "vocabulary_neg.csv"
vocabulary_neg_cut_path = working_data_path + "vocabulary_neg_cut.csv"


# returns a list with some
# of the most common stop words
def stop_words_list():
    with open(stopwords_path, "r") as file:
        return file.read().split()


# removes any special characters
# and stop words from the text
def clean_text(text):
    stop_words = stop_words_list()
    text = re.sub(r'[^a-zA-Z\s]', '', text)
    text = text.lower()
    text = text.replace('\n', '')
    text = text.replace('.', '')
    text = text.replace(',', '')
    text = text.replace(':', '')
    text = text.replace('?', '')
    text = text.replace('*', '')
    text = text.replace('!', '')
    text = text.replace('"', '')
    text = text.replace('/', '')
    text = text.replace('(', '')
    text = text.replace(')', '')
    text = text.replace('-', '')
    text = text.replace('&', '')
    text = text.replace('<br />', '')
    text = text.replace('//>', '')
    text = text.replace('/>', '')
    text = text.replace('<br', '')
    text = text.replace('/><br', '')
    text = text.replace('<', '')
    text = text.replace('>', '')
    text = text.replace('<br>', '')
    text = text.replace('<br >', '')
    text = text.replace('\\', '')
    text = text.split()
    text = [t for t in text if t.lower() not in stop_words]
    return text


# makes a vocabulary for every
# word and for every text file
# in a specific directory
# train_data_length : how many texts will be used (training percentage)
def make_vocabulary(path, vocabulary, train_data_length):
    files = os.listdir(path)
    files = files[:train_data_length]
    for file_name in files:
        with open(os.path.sep.join([path, file_name]), encoding="utf8") as file:
            text = file.read()
            text = clean_text(text)
            text = set(text)
            for word in text:
                if word in vocabulary:
                    vocabulary[word] += 1
                else:
                    vocabulary[word] = 1
    for word in vocabulary.keys():
        vocabulary[word] = float(vocabulary[word]) / float(train_data_length)
    vocabulary["files_count"] = train_data_length
    vocabulary["words_count"] = len(vocabulary) - 1


# saves the vocabulary
# to a specific file
def save_vocabulary(neg_pos, file_name, train_data_length):

    vocabulary = {}
    make_vocabulary(imdb_data_path + "\\train\\" + str(neg_pos), vocabulary, train_data_length)

    with open(file_name, 'w', newline='') as file:
        csv_writer = csv.writer(file)
        for key, value in vocabulary.items():
            csv_writer.writerow([key] + [value])


# m > n + k
# m: most common words excluding n and k
# n: n most common words
# k: least common words
def cut_words(m, n, k, file_to_read, file_to_write):
    vocabulary = {}
    with open(file_to_read, mode='r', encoding='utf8') as file:
        reader = csv.reader(file)
        for rows in reader:
            vocabulary[rows[0]] = float(rows[1])
    del vocabulary["files_count"]
    del vocabulary["words_count"]

    vocabulary = dict(sorted(vocabulary.items(), key=lambda item: item[1], reverse=True))   # sort in descending order
    if n != 0 :
        vocabulary = dict(list(vocabulary.items())[n:])     # remove the n most common words
    if k != 0 :
        vocabulary = dict(list(vocabulary.items())[:-k])    # remove the k least common words
    if m != 0 :
        vocabulary = dict(islice(vocabulary.items(), m))    # get the m most common words

    with open(file_to_write, 'w', newline='') as file:
        csv_writer = csv.writer(file)
        for key, value in vocabulary.items():
            csv_writer.writerow([key] + [value])


# file_to_read: the vocabulary file
# neg_pos: negative or positive path
# train_test: train or test path
# data_length: how many files we want to process (training percentage)
def make_vectors(file_to_read, neg_pos, train_test, data_length):
    vocabulary = []
    with open(file_to_read, mode='r', encoding='utf8') as file:
        reader = csv.reader(file)
        for rows in reader:
            vocabulary.append(rows[0])
    path = imdb_data_path + "\\" + str(train_test) + "\\" + str(neg_pos)

    vector = []
    files = os.listdir(path)
    files = files[:data_length]
    for file_name in files:
        with open(os.path.sep.join([path, file_name]), encoding="utf8") as file:
            text = file.read()
            text = clean_text(text)
            text = set(text)
            v = []
            for word in vocabulary:
                if word in text:
                    v.append(1)
                else:
                    v.append(0)
        vector.append(v)
    return vector


# data_length: how many files we want to process (training percentage)
def make_train_vectors(data_length):
    pos_vector = make_vectors(vocabulary_pos_cut_path, "pos", "train", data_length)
    neg_vector = make_vectors(vocabulary_neg_cut_path, "neg", "train", data_length)
    x_train = pos_vector + neg_vector
    y_train = [0 for y in pos_vector]     # 0 for positive
    y_train += [1 for y in neg_vector]    # 1 for negative
    return x_train, y_train


# data_length: how many files we want to process (training percentage)
def make_test_vectors(data_length):
    pos_vector = make_vectors(vocabulary_pos_cut_path, "pos", "test", data_length)
    neg_vector = make_vectors(vocabulary_neg_cut_path, "neg", "test", data_length)
    x_test = pos_vector + neg_vector
    return x_test


# m: most common words excluding n and k
# n: n most common words
# k: least common words
# train_data_length : how many texts will be used (training percentage)
def make_data_files(m, n, k, train_data_length):
    save_vocabulary("pos", vocabulary_pos_path, train_data_length)
    save_vocabulary("neg", vocabulary_neg_path, train_data_length)
    cut_words(m, n, k, vocabulary_pos_path, vocabulary_pos_cut_path)
    cut_words(m, n, k, vocabulary_neg_path, vocabulary_neg_cut_path)


