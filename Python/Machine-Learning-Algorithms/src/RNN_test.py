from logistic_regression_test import *
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Embedding, SimpleRNN, Dense

import numpy as np
from sklearn.metrics import accuracy_score, classification_report
import matplotlib.pyplot as plt


def test_RNN(x_train, y_train, x_test, y_test, filename):
    vocab_size = len(x_train[0])
    embedding_dim = 50
    max_sequence_length = len(x_train[0])
    rnn_units = 50
    num_epochs = 10
    model = Sequential()
    model.add(Embedding(input_dim=vocab_size, output_dim=embedding_dim, name='word_embeddings',
                        input_length=max_sequence_length))
    model.add(SimpleRNN(units=rnn_units))
    model.add(Dense(units=1, activation='sigmoid'))

    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['binary_accuracy'])

    history = model.fit(x_train, y_train, epochs=num_epochs, validation_split=0.2)

    plt.plot(history.history['loss'], label='Training Loss')
    plt.plot(history.history['val_loss'], label='Validation Loss')
    plt.xlabel('Epochs')
    plt.ylabel('Loss')
    plt.legend()
    plt.show()

    custom_learning_curve(model, x_train, y_train, x_test, y_test, 5, filename)

    with open(filename + 'report.txt', 'w') as file:
        file.write("Features:" + str(len(x_train[0])) + "\n")
        file.write("Data Length:" + str(len(x_train)) + " of total 25000.\n")
        file.write("Data Percentage:" + str((len(x_train) / 25000) * 100) + "%.\n\n")

        file.write("Training Metrics for RNN:\n")
        y_train_pred = (model.predict(x_train) > 0.5).astype(int)
        file.write(classification_report(y_train, y_train_pred))

        file.write("\nTesting Metrics for RNN:\n")
        y_test_pred = (model.predict(x_test) > 0.5).astype(int)
        file.write(classification_report(y_test, y_test_pred))


def custom_learning_curve(model, x_train, y_train, x_test, y_test, n_splits, filename):
    split_size = int(len(x_train) / n_splits)
    x_splits = np.split(x_train, n_splits)
    y_splits = np.split(y_train, n_splits)
    train_accuracies = list()
    test_accuracies = list()
    curr_x = x_splits[0]
    curr_y = y_splits[0]
    log_reg = model
    log_reg.fit(curr_x, curr_y)
    train_accuracies.append(accuracy_score(curr_y, (log_reg.predict(curr_x) > 0.5).astype(int)))
    test_accuracies.append(accuracy_score(y_test, (log_reg.predict(x_test) > 0.5).astype(int)))

    for i in range(1, len(x_splits)):
        log_reg = model
        curr_x = np.concatenate((curr_x, x_splits[i]), axis=0)
        curr_y = np.concatenate((curr_y, y_splits[i]), axis=0)
        log_reg.fit(curr_x, curr_y)
        train_accuracies.append(accuracy_score(curr_y, (log_reg.predict(curr_x) > 0.5).astype(int)))
        test_accuracies.append(accuracy_score(y_test, (log_reg.predict(x_test) > 0.5).astype(int)))

    plt.plot(list(range(split_size, len(x_train) + split_size, split_size)), train_accuracies, 'o-', color="b",
             label="Training accuracy")
    plt.plot(list(range(split_size, len(x_train) + split_size, split_size)), test_accuracies, 'o-', color="red",
             label="Testing accuracy")
    plt.text(0.5, 1.05, filename.split('\\', 2)[2], horizontalalignment='center', verticalalignment='center',
             transform=plt.gca().transAxes)
    plt.legend(loc="lower right")
    plt.xlabel('Percentage of data')
    plt.ylabel('Accuracy')
    plt.savefig(str(filename) + '.png', bbox_inches='tight')
    plt.show()


def testRNN():
    x_train, y_train, x_test, y_test = prepare_data(2000, 100, 100, 2000)
    test_RNN(x_train, y_train, x_test, y_test, "..\\RNN_reports\\RNN")


if __name__ == '__main__':
    testRNN()
