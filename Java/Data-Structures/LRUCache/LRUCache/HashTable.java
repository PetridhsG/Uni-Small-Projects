public class HashTable<K,V> {

    private class HashNode<K,V>{        // this class handles <K,V> values
        K key;
        V value;
        final int hashCode;
        HashNode<K, V> next;
        HashNode(K key, V value,int hashCode,HashNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.hashCode = hashCode;
            this.next = next;
        }
    }

    private int table_size;           // hashtable size
    private HashNode<K,V> [] table;   // hashtable (HashNode of V type Objects for every element of table)

    public HashTable(int size){         // default constructor
        if (size <= 0 ){
            throw new IllegalArgumentException("Negative or zero size!");
        }
        table_size = size;
        table = new HashNode[size];     // initialize the size of the table
    }

    // given a key,get the index of a HashNode
    private int getIndex(K key){
        int hashCode = key.hashCode();
        int index = hashCode % table_size;

        if (index < 0){         // hashcode maybe negative
            return -index;
        }
        return index;

    }

    // adds an object to the hashtable
    public boolean add(K key,V data){
        if (data == null){      // if data is null
            throw new NullPointerException("Data is null!");
        }
        int hash = key.hashCode();
        int index =  getIndex(key);     // key is of type K - cast to Object /index in the hashtable
        HashNode<K,V> tab[] = table;
        HashNode<K,V> chain = tab[index];   // find the HashNode in which data is going to be inserted,depending on its hash code
        for(; chain != null ; chain = chain.next) {
            if ((chain.hashCode == hash) && chain.key.equals(key)) {    // if is already in the table
                return false;
            }
        }
        tab[index] = new HashNode<>(key,data,hash,tab[index]);     // add HashNode to the HashNode chain
        return true;
    }

    // removes an item from the hash table
    public boolean remove(K key) {
        int hash = key.hashCode();
        int index =  getIndex(key);     // key is of type K - cast to Object /index in the hashtable
        HashNode<K,V> tab[] = table;
        HashNode<K,V> chain = tab[index];       // find the HashNode in which data is going to be inserted,depending on its hash code
        for(HashNode<K,V> prev = null; chain != null ; prev = chain, chain = chain.next) {
            if ((chain.hashCode== hash) && chain.key.equals(key)) {
                if (prev != null) {         // remove safely the given element from HashNode chain
                    prev.next = chain.next;
                } else {
                    tab[index] = chain.next;
                }
                return true;
            }
        }
        return false;
    }

    // checks if an item is present in the hash table
    public boolean contains(V data) {
        if (data == null){
            throw new NullPointerException();
        }
        HashNode<K,V> tab[] = table;
        for (int i = tab.length ; i-- > 0 ;) {      // for every HashNode chain check if the value is contained
            for (HashNode<K,V> chain = tab[i]; chain != null ; chain = chain.next) {
                if (chain.value.equals(data)) {
                    return true;
                }
            }
        }
        return false;

    }

    // get the value of the given key
    public V get(K key){
        int hash = key.hashCode();
        int index =  getIndex(key);     // key is of type K - cast to Object /index in the hashtable
        HashNode<K,V> tab[] = table;
        for (HashNode<K,V> chain = tab[index]; chain != null ; chain = chain.next){     // for every HashNode in the chain check if the value is contained
            if ((chain.hashCode == hash) && chain.key.equals(key)) {
                return chain.value;
            }
        }
        return null;
    }

}
