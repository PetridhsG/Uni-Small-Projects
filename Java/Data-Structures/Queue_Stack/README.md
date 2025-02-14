## Stack

The Stack class implements the stack data structure as follows:
Since it is a Last-In-First-Out (LIFO) data structure, it requires a pointer, head, which always points to the top of the stack, indicating the element that was most recently added. It also uses a counter, size, which simulates the number of elements in the stack by keeping track of the push and pop operations. The implementation also requires the Node class, which contains the value of an element and a pointer to the next Node in the stack. Element insertion occurs at the top of the stack using the push method, and element removal occurs again from the top of the stack using the pop method. Both methods operate in constant time, O(1). Additionally, there are other methods: peek, which reveals the element at the top of the stack without removing it, size, which returns the current size of the stack, isEmpty, which checks if the stack is empty, and printStack, which displays the elements in the stack one by one. Finally, the implementation is done using Generics to handle data of any data type.

## Queue

The Queue class implements the queue data structure as follows:
Since it is a First-In-First-Out (FIFO) data structure, it requires two pointers: head, which points to the beginning of the queue, indicating the element that was added first, and tail, which points to the end of the queue, indicating the element that was added last. The implementation also requires the Node class, which contains the value of an element and a pointer to the next Node in the queue. Element insertion occurs at the end of the queue using the put method, and element removal occurs from the beginning of the queue using the get method. Both methods operate in constant time, O(1). Additionally, there are other methods: peek, which reveals the element at the beginning of the queue without removing it, size, which returns the current size of the queue, isEmpty, which checks if the queue is empty, and printQueue, which displays the elements in the queue one by one. Finally, the implementation is done using Generics to handle data of any data type.

## QueueWithOnePointer

To implement the queue with a single pointer, a circular linked list is used. The unique pointer, head, always points to the end of the queue, and the next element is located at the beginning. The put method inserts a node at the end of the queue as follows: If the queue is empty, it creates a new node and sets the pointer to point to itself. Otherwise, it creates a new node, sets the pointer to point to the next element of head, updates the head pointer to point to the new element, and finally, sets head to equal this new element. The get method removes an element from the queue, returning the value of the element at the beginning of the queue, and then updates the head pointer to point to the next element, effectively removing the element at the beginning of the queue.

## Thiseas

In this section, the Stack class is used to implement the program. Initially, the program checks the validity of the input data, and if the data is valid, it is stored in arrays. Then, an object of the Stack class is created, which specifically handles data of the type Point. It starts with the coordinates of the entrance point in the labyrinth. The program then checks for available movement in the labyrinth. If movement is possible, it marks the current position with "X" and proceeds by pushing the coordinates of the new position onto the stack. The algorithm continues until it finds an exit. If the algorithm encounters a dead-end, it returns one step back by popping the coordinates from the stack. This process continues until the algorithm either finds the exit or exhaustively explores all possible paths. If the exit is found, the algorithm terminates and displays the coordinates of the exit. If the algorithm has checked all possible paths and does not find the exit, the stack is emptied, reaching back to the entrance coordinates, and the program terminates.

## Run The Program
To compile the files, use ```javac *.java```. 

To run Thiseas.java, use ```java Thiseas lab.txt```

The lab.txt represents the labyrinth for Thiseas to search.
First line is the dimensions,second line is the entrance and for the other lines
0 represent a valid move and 1 represent a invalid move.