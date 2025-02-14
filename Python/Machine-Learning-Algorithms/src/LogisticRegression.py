import numpy as np


# SGA : Stochastic Gradient Ascent
class LogisticRegressionSGA:

    def __init__(self, learning_rate=0.01, regularization=0.1, max_iterations=1000):
        self.regularization = regularization
        self.learning_rate = learning_rate
        self.max_iterations = max_iterations
        self.weights = None
        self.initialized = False

    # t = wx (w = weight vector, x = input vector)
    def sigmoid(self, t):
        return 1 / (1 + np.exp(-np.clip(t, -700, 700)))

    def initialize(self, features_length):
        if not self.initialized:
            self.initialized = True
            self.weights = np.random.rand(features_length)

    def fit(self, x_train, y_train):
        examples_length, features_length = x_train.shape
        m = examples_length
        n = features_length
        self.initialize(n)

        for _ in range(self.max_iterations):
            for i in range(m):
                x = x_train[i]
                y = y_train[i]
                t = np.dot(x, self.weights)
                p = self.sigmoid(t)

                dw = np.dot(x.T, (p - y))

                self.weights += self.learning_rate * (dw - 2 * self.regularization * self.weights)
                self.weights = np.clip(self.weights, -1e10, 1e10)

    def predict(self, x_train):
        t = np.dot(x_train, self.weights)
        p = self.sigmoid(t)
        predictions = (p > 0.5).astype(int)
        return predictions

