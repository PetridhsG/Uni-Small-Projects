## MaxPQ
The implementation of MaxPQ was done using Generics so that it can handle various data types. It is an improvement over the queue used in the course tutorial. Specifically, it includes additional methods: getSize() which returns the size of the heap and recoverHeap() method, which, when called, restores the property of the heap. The other methods remained unchanged.

## Greedy
Initially, the algorithm in Part B reads the sizes of folders from a file and stores them in an array. Then it creates a priority queue to store disks. At the root of the heap, there will always be the disk with the most available storage space. If, during the algorithm, a folder doesn't fit on a disk, a new disk is created for that folder. In each iteration, the recoverHeap() method is used to ensure that the heap property is always satisfied. The GreedyAlgorithm() method returns an object of type Triple, which is a helper object for other methods. Finally, when displaying the queue, the getMax() method is used to return the element at the root, which is the disk with the most free space. This way, the disks are presented to the user in descending order based on their free space.
The quicksort sorting algorithm was used on an array. Specifically, the algorithm takes an array as an argument and the segment in which the sorting should occur. The algorithm works recursively, and at each recursive call, the array is divided based on a pivot element, with smaller elements on the left and larger elements on the right. The above algorithm was used to sort an array containing folders based on their size. This algorithm was chosen because it is very fast for a large number of elements compared to other algorithms, and there is a high chance that a large number of folders will need to be sorted.

## RandomDisks
Initially, for each value of N, a subdirectory is created to store txt files with folder sizes. Then a txt file is created in which each line contains a number between [0, 1000000] representing the folder's size. Then, for each N, each file is read, and the size of each folder is recorded. Then, for each file, the number of disks needed to store the folders is recorded. Finally, for each N, the average number of disks needed is calculated. The above process is exactly the same for both algorithms. For each value of N, 10 files were read and analyzed, each containing N random folders. From the execution of the above algorithm, we have the following randomly generated data for specific values of N:

Number of Folders - Greedy Algorithm - Sorted Greedy Algorithm

100 - 60 - 54

500 - 293 - 256

1000 - 586 - 508

As shown in the table, the second algorithm, which sorts the input data, performs better, using fewer disks compared to the first algorithm. For small values of N, both algorithms have approximately the same performance. However, as N increases, the second algorithm appears to have better performance. Also, as N becomes very large (see Excel), both algorithms exhibit a specific behavior. The first algorithm tends to use 5.9N disks for large values of N, while the second uses 5.1N disks. In other words, the relationship "Number of disks as a function of the number of folders (N)" is well-approximated by the following functions:

GREEDY_ALGORITHM(N) = 5.9N

SORTED_GREEDY_ALGORITHM(N) = 5.1N

It holds true that 5.9N > 5.1N, so SORTED_GREEDY_ALGORITHM(N) > GREEDY_ALGORITHM(N). This means that, in addition to the experimental data, the functions that approximate the Disk-Folders relationship confirm that for large values of N, the second algorithm is more efficient. However, for small values, their performance is roughly the same.

## Run The Program
To compile the files, use ```javac *.java```. 

To run Thiseas.java, use ```java RandomDisks```

