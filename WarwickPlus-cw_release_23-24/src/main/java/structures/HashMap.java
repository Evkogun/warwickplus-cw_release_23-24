package structures;
public class HashMap<K, V> {

    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K, V>[] map;
    private int size;
    private int capacity;
    private final double loadFactor = 0.75;
    private int[] primes = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969, 52679969, 105359939, 210719881, 421439783, 842879579, 1685759167};
    private int primecount = 2;

    @SuppressWarnings("unchecked")
    public HashMap() {
        this.capacity = 5; 
        map = new Entry[capacity];
    }

    public int hash(K key) {
        int h = key.hashCode();
        h = h & 0x7fffffff;
        return h % capacity;
    }

    private void resize() {
        int newCapacity = primes[primecount];
        primecount++;
        
        @SuppressWarnings("unchecked")
        Entry<K, V>[] tempMap = new Entry[newCapacity];
    
        for (Entry<K, V> entry : map) {
            while (entry != null) {
                int newIndex = Math.abs(entry.key.hashCode()) % newCapacity;
                Entry<K, V> nextEntry = entry.next;
                entry.next = tempMap[newIndex];
                tempMap[newIndex] = entry;
                entry = nextEntry;
            }
        }
    
        map = tempMap;
        this.capacity = newCapacity;
    }

    public boolean put(K key, V value) {
        while ((size + 1) >= capacity * loadFactor) {
            resize();
        }
    
        int index = hash(key);
        for (Entry<K, V> entry = map[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return false;
            }
        }
    
        map[index] = new Entry<>(key, value, map[index]);
        this.size++;
        return true;
    }

    public V get(K key) {
        if (size == 0) return null;
        int index = hash(key);
        for (Entry<K, V> entry = map[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public V take(K key) {
        if (size == 0) return null;
        int index = hash(key);
        Entry<K, V> prev = null;
        for (Entry<K, V> entry = map[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                V oldValue = entry.value; 
    
                if (prev == null) {
                    map[index] = entry.next;
                } else {
                    prev.next = entry.next; 
                }
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

    public boolean isEmpty(){
        return size == 0;
    }


    public float[] values() {
        float[] valuesArray = new float[size];
        if (size == 0) return valuesArray;
        int i = 0;
    
        for (Entry<K, V> entry : map) {
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
        
        for (Entry<K, V> entry : map) {
            while (entry != null) {
                keyListArray[i++] = (int) entry.key;
                entry = entry.next;
            }
        }
        return keyListArray;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] valuez(Class<T> clazz) {
        T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
        if (size == 0) return array;
        int i = 0;

        for (Entry<K, V> entry : map) {
            while (entry != null) {
                array[i++] = clazz.cast(entry.value);
                entry = entry.next;
            }
        }

        return array;
    }
}










