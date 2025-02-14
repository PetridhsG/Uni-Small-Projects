import numpy as np

class NaiveBayesSGA:

    def __init__(self):
        self.classprob = None
        self.featureprob = None
        self.initialized = False

    def initialize(self):
        if not self.initialized:
            self.initialized = True
            self.classprob = {}
            self.featureprob = {}

    def classprobCalc(self, y_train):
        classes, classCount = np.unique(y_train, return_counts=True)
        total = len(y_train)
        for classLabel, count in zip(classes, classCount):
            self.classprob[classLabel] = count / total


    def featureprobCalc(self, x_train, y_train):
        for index in range(x_train.shape[1]):
            values, valueCount = np.unique(x_train[:, index], return_counts=True)
            for classLabel in np.unique(y_train):
                tclass = (y_train == classLabel)
                for value,count in zip(values, valueCount):
                    feature = (index, value, classLabel)
                    numerator = np.sum(tclass & (x_train[:, index] == value))+1
                    denominator = np.sum(tclass)+1*len(values)
                    self.featureprob[feature] = numerator/denominator

    def fit(self, x_train, y_train):
        self.initialize()
        self.classprobCalc(y_train)
        self.featureprobCalc(x_train, y_train)

    def predict(self, x_train):
        predictions = []
        for f in x_train:
            class_scores = {}
            for class_label, class_prob in self.classprob.items():
                featureprobsum = 0
                for feature_index, feature_value, _ in self.featureprob.keys():
                    if feature_index < len(f) and f[feature_index] == feature_value:
                        featureprobsum += np.log(self.featureprob[(feature_index, feature_value, class_label)])
                class_scores[class_label] = np.log(class_prob) + featureprobsum
            predicted_class = max(class_scores, key=class_scores.get)
            predictions.append(predicted_class)

        return np.array(predictions)
