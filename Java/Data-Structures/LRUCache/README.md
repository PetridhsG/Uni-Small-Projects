# LRU Cache

For the implementation of the LRUCache, the following data structures were used: Queue and HashTable. The Queue stores elements in a way that the element with the longest elapsed time is always at the beginning of the queue. This implements the LRU (Least Recently Used) functionality of the Cache. When you want to store a new element and the cache is full, the element at the beginning of the queue is removed, and the new element is added to the end of the queue.

## Cache
The specific Cache has the following functions:

lookup(K key): Searches in the HashTable to find if an element with key K exists, and if it does, returns it. Internally, the lookup method calls the methods of the HashTable class, get and contains. (O(n))

store(K key, V value): Stores the key-value pair <K, V> in the HashTable based on the LRU policy described above. Internally, the store method calls the methods of the HashTable class, get, contains, and add, as well as the methods get and put of the Queue class. (O(n))

getHitRatio(): Returns the percentage of successful lookups compared to the total number of lookups in the Cache. (O(1))

getHits(): Returns the number of successful lookups. (O(1))

getMisses(): Returns the number of unsuccessful lookups. (O(1))

getNumberOfLookUps(): Returns the total number of lookups. (O(1))

## Queue

The Queue class has the following main functions:

get(): Returns the element at the beginning of the queue. (O(1))

put(T data): Places the element at the end of the queue. (O(1))

## HashTable

The HashTable class has the following main functions:

add(K key, V data): Stores the pair <K, V> in the table. It finds the corresponding position and then checks if the value already exists in the table. If it doesn't, it adds it. (O(1))

remove(K key): Removes the element with the key key from the table. It finds the corresponding position and then removes the element if it exists. (O(1))

contains(V data): Checks if the element with value data exists in the table. It searches all positions of the table and each element. (O(n))

get(K key): Returns the value associated with the key key. (O(1))

The key-value pairs <K, V> are internally stored using the HashNode<K, V> class in the table.

## Data
Some experimental data:

Average HitRatio = 93%

AVERAGE RUN TIME:

* DATA-1000:

    * Requests-5000: 520ms

    * Requests-10000: 715ms

* DATA-5000:

  * Requests-5000: 3500ms

  * Requests-10000: 6500ms

  * Requests-50000: 2210ms

  * Requests-100000: 9300ms

## Run The Program
To compile the files, use ```javac *.java```. 

To run Thiseas.java, use ```java TestCacheSpeed```
