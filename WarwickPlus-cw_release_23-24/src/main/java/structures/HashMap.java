package structures;
import stores.*;

public class HashMap<V> {

    private static class Entry<V> {
        final Integer key;
        V value;
        Entry<V> next; // Pointer to next value in bucket.

        Entry(Integer key, V value, Entry<V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<V>[] map;
    private int size = 0;
    private int capacity = 5;
    private final double loadFactor = 0.75; // Default loadfactor used in javas implementation of hashmap.
    private int[] primes = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969, 52679969, 105359939, 210719881, 421439783, 842879579, 1685759167};
    // Precalculated set of primes emulating the method 'prime = nextPrime(prime * 2)'.
    private int primecount = 2; // Starts the primes at 11.

    @SuppressWarnings("unchecked")
    public HashMap() {
        map = new Entry[capacity]; // Initialises the hashmap.
    }

    public int hash(Integer key) {
        int hash = key;
        hash ^= (hash >>> 20) ^ (hash >>> 12); // Method works by swapping the bits of the key.
        hash ^= (hash >>> 7) ^ (hash >>> 4); 
        return hash & (capacity - 1); // Acts as a modulus, but is optimised due to frequent usage of hash.
    }

    private void resize() {
        this.capacity = primes[primecount];
        primecount++;
        
        @SuppressWarnings("unchecked")
        Entry<V>[] tempMap = new Entry[capacity]; // Makes the replacement hashmap.
    
        for (Entry<V> entry : map) { // Replaces every bucket within the hashmap.
            while (entry != null) {
                int newIndex = hash(entry.key);
                Entry<V> nextEntry = entry.next;
                entry.next = tempMap[newIndex];
                tempMap[newIndex] = entry;
                entry = nextEntry;
            }
        }
    
        map = tempMap;
    }

    public boolean put(int key, V value) {
        while ((size + 1) >= capacity * loadFactor) resize(); // Resizes if the loadfactor is exceeded.
    
        int index = hash(key);
        for (Entry<V> entry = map[index]; entry != null; entry = entry.next) if (entry.key.equals(key)) return false; // Checks for existing keys.

        map[index] = new Entry<>(key, value, map[index]); // Adds new entry.
        this.size++;
        return true;
    }

    public V get(int key) {
        int hashedkey = hash(key);
        for (Entry<V> entry = map[hashedkey]; entry != null; entry = entry.next) if (entry.key.equals(key)) return entry.value; // Checks for key.
        return null; // Key not found.
    }

    public V take(int key) {
        int index = hash(key);
        Entry<V> prev = null;     // Initialize a reference to keep track of the previous entry in the chain.
        for (Entry<V> entry = map[index]; entry != null; entry = entry.next) { // Go through bucket.
            if (entry.key.equals(key)) {
                V oldValue = entry.value; // Store old value for returning if found. 
                if (prev == null) map[index] = entry.next; // If this entry is the first in the bucket (no previous entries), then set the next entry as the first entry of this bucket.
                else prev.next = entry.next; // If there are previous entries, link the previous entry directly to the next entry, effectively removing the current entry from the chain.
                this.size--; 
    
                return oldValue; 
            }
            prev = entry; 
        }
        return null; 
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.map = new Entry[capacity]; 
        this.size = 0; 
    }

    public int size(){
        return size;
    }
    public int capacity(){
        return capacity;
    }

    // Various methods for returning all associated keys and values.
    // Used to have a generic implementation, but without access to javas newinstance, casting manually was deemed too slow.

    public float[] values() {
        float[] valuesArray = new float[size];
        if (size == 0) return valuesArray;
        int i = 0;
    
        for (Entry<V> entry : map) {
            while (entry != null) {
                valuesArray[i++] = (Float) entry.value;
                entry = entry.next;
            }
        }
    
        return valuesArray;
    }

    public int[] keyList(){
        int[] keyListArray = new int[size];
        if (size == 0) return keyListArray;
        int i = 0;
        
        for (Entry<V> entry : map) {
            while (entry != null) {
                keyListArray[i++] = entry.key;
                entry = entry.next;
            }
        }
        return keyListArray;
    }

    public MovieInfoData[] movieInfoList(){
        MovieInfoData[] movieInfoArray = new MovieInfoData[size];
        int i = 0;
        
        for (Entry<V> entry : map) {
            while (entry != null) {
                movieInfoArray[i++] = (MovieInfoData) entry.value;
                entry = entry.next;
            }
        }
        return movieInfoArray;
    }

    public Person[] personList() {            
        if (size == 0) return new Person[0];
        Person[] valuesArray = new Person[size];
        int i = 0;
    
        for (Entry<V> entry : map) {
            while (entry != null) {
                valuesArray[i++] = (Person) entry.value;
                entry = entry.next;
            }
        }
        
        return valuesArray;
    }

    public float[] ratingValuesTimePair(){
        TimePair temp;
        float[] returnArr = new float[size];
        if (size == 0) return new float[0];
        int i = 0;
    
        for (Entry<V> entry : map) {
            while (entry != null) {
                temp = (TimePair) entry.value;
                returnArr[i++] = temp.getRating();
                entry = entry.next;
            }
        }
    
        return returnArr;
    }
   
    // @SuppressWarnings("unchecked")
    // public <T> T[] valuez(Class<T> clazz) {
    //     T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
    //     if (size == 0) return array;
    //     int i = 0;

    //     for (Entry<K, V> entry : map) {
    //         while (entry != null) {
    //             array[i++] = clazz.cast(entry.value);
    //             entry = entry.next;
    //         }
    //     }

    //     return array;
    // }
}










