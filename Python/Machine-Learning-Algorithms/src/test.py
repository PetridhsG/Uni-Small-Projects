import numpy as np
from sklearn.metrics import accuracy_score
from sklearn.linear_model import LogisticRegression as SKLogisticRegression
from sklearn.model_selection import train_test_split
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Embedding, SimpleRNN, Dense
import matplotlib.pyplot as plt
from sklearn import metrics
from LogisticRegression import *
from prepare_data import *


def prepare_data(m, n, k, data_length):
    # Assume you have a function make_data_files and make_train_vectors
    # that prepares the data. Modify accordingly.
    make_data_files(m, n, k, data_length)
    x_train, y_train = make_train_vectors(data_length)

    x_train = np.array(x_train)
    y_train = np.array(y_train)
    x_test = make_test_vectors(data_length)
    x_test = np.array(x_test)
    x_train, x_test, y_train, y_test = train_test_split(x_train, y_train, test_size=0.5, random_state=42)
    return x_train, y_train, x_test, y_test


def test_model(model, x_train, y_train, x_test, y_test, filename):
    log_model = model
    log_model.fit(x_train, y_train)
    y_test_pred = log_model.predict(x_test)

    custom_learning_curve(log_model, x_train, y_train, x_test, y_test, 10, filename)

    with open(str(filename) + 'report.txt', 'w') as file:
        file.write("Features:" + str(len(x_train[0])))
        file.write("\nData Length:" + str(len(x_train)) + " of total 25000.")
        file.write("\nData Percentage:" + str((len(x_train) / 25000) * 100) + "%.\n\n")

        file.write("Training Metrics for " + filename.split('\\', 2)[2] + ":\n")
        file.write(metrics.classification_report(y_train, log_model.predict(x_train)))
        file.write("\nTesting Metrics for " + filename.split('\\', 2)[2] + ":\n")
        file.write(metrics.classification_report(y_test, y_test_pred))

    # Plot training and test accuracy curves
    plot_learning_curves(log_model.history, filename)

def custom_learning_curve(model, x_train, y_train, x_test, y_test, n_splits, filename):
    history = model.fit(x_train, y_train, epochs=10, validation_split=0.2, verbose=0)

    plt.plot(history.history['accuracy'], label='Training Accuracy')
    plt.plot(history.history['val_accuracy'], label='Validation Accuracy')
    plt.xlabel('Epochs')
    plt.ylabel('Accuracy')
    plt.legend()
    plt.savefig(str(filename) + '_accuracy.png', bbox_inches='tight')
    plt.show()

def plot_learning_curves(history, filename):
    plt.plot(history['accuracy'], label='Training Accuracy')
    plt.plot(history['val_accuracy'], label='Validation Accuracy')
    plt.xlabel('Epochs')
    plt.ylabel('Accuracy')
    plt.legend()
    plt.savefig(str(filename) + '_accuracy.png', bbox_inches='tight')
    plt.show()

def test_logistic_regression():
    x_train, y_train, x_test, y_test = prepare_data(2000, 100, 100, 2000)

    test_model(LogisticRegressionSGA(0.1, 10, 100), x_train, y_train, x_test, y_test,
               "..\\logistic_regression_reports\\LogisticRegressionSGA")
    test_model(SKLogisticRegression(), x_train, y_train, x_test, y_test,
               "..\\logistic_regression_reports\\SKLogisticRegression")
    test_RNN(x_train, y_train, x_test, y_test)

def test_RNN(x_train, y_train, x_test, y_test):
    vocab_size = len(x_train[0])
    embedding_dim = 50
    max_sequence_length = len(x_train[0])
    rnn_units = 50
    num_epochs = 10
    model = Sequential()
    model.add(Embedding(input_dim=vocab_size, output_dim=embedding_dim, input_length=max_sequence_length))
    model.add(SimpleRNN(units=rnn_units))
    model.add(Dense(units=1, activation='sigmoid'))

    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

    history = model.fit(x_train, y_train, epochs=num_epochs, validation_split=0.2, verbose=0)

    plt.plot(history.history['accuracy'], label='Training Accuracy')
    plt.plot(history.history['val_accuracy'], label='Validation Accuracy')
    plt.xlabel('Epochs')
    plt.ylabel('Accuracy')
    plt.legend()
    plt.show()

if __name__ == '__main__':
    test_logistic_regression()
