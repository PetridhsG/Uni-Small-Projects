public class LRUCache<K,V> implements Cache<K,V> {

    private int cache_size;                      // cache size
    private HashTable<K,V> table;                // cache hashtable
    private Queue<K> order = new Queue<K>();     // least recently used key

    private int misses,hits,lookups = 0;

    public LRUCache(){}                         // default constructor

    public LRUCache(int size) {                 // custom constructor
        this.cache_size = size;
        table = new HashTable<K, V>(cache_size);
    }
    /**
     * Look for data associated with key.
     * @param key the key to look for
     * @return The associated data or null if it is not found
     */
    public V lookUp(K key){
        if (table.get(key) !=null){
            if (table.contains(table.get(key))){
                hits++;
            }
            else{
                misses++;
            }
        }
        lookups++;
        return table.get(key);
    }

    /**
     * Stores data with associated with the given key. If required, it evicts a
     * data record to make room for the new one
     * @param key the key to associate with the data
     * @param value the actual data
     */
    public void store(K key, V value){
        if (table.contains(value)){
            table.remove(order.get());
        }
        else{
            order.put(key);
        }
        table.add(key,value);
    }

    /**
     * Returns the hit ratio, i.e. the number of times a lookup was successful divided by the number of lookup
     * @return the cache hit ratio
     */
    public double getHitRatio(){
        double x = hits;
        double y = lookups;
        return x/y;
    }

    /**
     * Returns the absolute number of cache hits, i.e. the number of times a lookup found data in the cache
     */
    public long getHits(){
        return hits;
    }

    /**
     * Returns the absolute number of cache misses, i.e. the number of times a lookup returned null
     */
    public long getMisses(){
        return misses;
    }

    /**
     * Returns the total number of lookups performed by this cache
     */
    public long getNumberOfLookUps(){
        return lookups;
    }
}
