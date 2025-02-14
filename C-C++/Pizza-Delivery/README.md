
# Pizza Delivery
## This app implements the logic of a pizza delivery service, using threads and synchronization

In the "config.h" file, you can find the declarations of program constants according to the specifications. In the "pizzeria.c" file, initially, all the required includes and declarations of necessary variables, mutexes, and condition variables are present.

Two functions are used: "main" and "order." The "main" function takes the number of customers (Ncust) and a seed for generating random numbers as arguments. Then, it initializes mutexes and condition variables and creates and connects exactly Ncust threads, passing their unique IDs as arguments. Finally, it destroys the mutexes and condition variables and displays the order statistics.

The "order" function takes the customer's ID as an argument. It initializes the necessary variables and calculates the number of special and plain pizzas the customer will order, as well as the entry and payment times for the order. If the order fails, the thread terminates its operation. If not, it continues by calculating some statistics for the order, and the order is ready to be prepared.

For each stage of the order, there is a timer, a mutex, and a condition variable. The timer calculates the time from the beginning to the end of the stage, the mutex locks the critical section where available resources are modified, and the condition variable signals and waits for when a resource is available. Sleep functions are used in between to simulate the time for each stage.

After all stages of the order are completed, some useful statistics are calculated, and finally, the space allocated for the customer's ID is released, and the thread terminates its operation.

## Run the project
This project can only be run in Unix systems that have pthread library installed.
To run this, just run the test_res.sh file.
