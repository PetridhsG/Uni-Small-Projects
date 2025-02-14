
from sklearn.metrics import accuracy_score
import matplotlib.pyplot as plt
from Bayes import *
from prepare_data import *
from sklearn.naive_bayes import GaussianNB as SKBayes
from sklearn import metrics
from sklearn.model_selection import train_test_split

# m: most common words excluding n and k
# n: n most common words
# k: least common words
# train_data_length : how many texts will be used for each category (training percentage)
def prepare_data(m, n, k, data_length):
    make_data_files(m, n, k, data_length)
    x_train, y_train = make_train_vectors(data_length)

    x_train = np.array(x_train)
    y_train = np.array(y_train)
    x_test = make_test_vectors(data_length)
    x_test = np.array(x_test)
    x_train, x_test, y_train, y_test = train_test_split(x_train, y_train, test_size=0.5, random_state=42)
    return x_train, y_train, x_test, y_test

# model: the model we are testing
# filename: the file for the model to be saved
def test_model(model, x_train, y_train, x_test, y_test, filename):
    log_model = model
    log_model.fit(x_train, y_train)
    y_test = log_model.predict(x_test)

    custom_learning_curve(log_model, x_train, y_train, x_test, y_test, 10, filename)

    #with open(str(filename) + "_" + str(len(x_train[0])) + "f_" + str(len(x_train)) + 'd_report.txt', 'w') as file:
    with open(str(filename) + 'report.txt', 'w') as file:
        file.write("Features:" + str(len(x_train[0])))
        file.write("\nData Length:" + str(len(x_train)) + " of total 25000.")
        file.write("\nData Percentage:" + str((len(x_train) / 25000) * 100) + "%.\n\n")

        file.write("Training Metrics for " + filename.split('\\', 2)[2] + ":\n")
        file.write(metrics.classification_report(y_train, log_model.predict(x_train)))
        file.write("\nTesting Metrics for " + filename.split('\\', 2)[2] + ":\n")
        file.write(metrics.classification_report(y_test, log_model.predict(x_test)))


# create the learning curve
def custom_learning_curve(model, x_train, y_train, x_test, y_test, n_splits, filename):
    split_size = int(len(x_train) / n_splits)
    x_splits = np.split(x_train, n_splits)
    y_splits = np.split(y_train, n_splits)
    train_accuracies = list()
    test_accuracies = list()

    for i in range(n_splits):
        curr_x = x_splits[i]
        curr_y = y_splits[i]

        log_model = model  # Create a new instance of the model inside the loop
        log_model.fit(curr_x, curr_y)

        train_accuracies.append(accuracy_score(curr_y, log_model.predict(curr_x)))
        test_accuracies.append(accuracy_score(y_test, log_model.predict(x_test.copy())))  # Copy x_test to avoid overwriting

    # Ensure that the directory exists before saving the figure
    os.makedirs(os.path.dirname(filename), exist_ok=True)

    plt.plot(list(range(split_size, len(x_train) + split_size, split_size)), train_accuracies, 'o-', color="b",
             label="Training accuracy")
    plt.plot(list(range(split_size, len(x_train) + split_size, split_size)), test_accuracies, 'o-', color="red",
             label="Testing accuracy")
    plt.text(0.5, 1.05, filename.split('\\', 2)[2], horizontalalignment='center', verticalalignment='center',
             transform=plt.gca().transAxes)
    plt.legend(loc="lower right")
    plt.xlabel('Percentage of data')
    plt.ylabel('Accuracy')

    # Save the figure
   # plt.savefig(str(filename) + "_" + str(len(x_train[0])) + "f_" + str(len(x_train)) + 'd_.png', bbox_inches='tight')
    plt.savefig(str(filename) + '.png', bbox_inches='tight')
    plt.show()

def test_bayes():
    x_train, y_train, x_test, y_test = prepare_data(1000,100,100,1000)
    test_model(NaiveBayesSGA(), x_train, y_train, x_test, y_test,
               "..\\bayes_reports\\NaiveBayes")
    test_model(SKBayes(), x_train, y_train, x_test, y_test,
               "..\\bayes_reports\\SKBayes")


if __name__=='__main__':
    test_bayes()