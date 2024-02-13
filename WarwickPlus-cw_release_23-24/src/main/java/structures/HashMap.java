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

    @SuppressWarnings("unchecked")
    public HashMap() {
        this.capacity = 16; 
        map = new Entry[capacity];
    }

    public int hash(K key) {
        int h = key.hashCode();
        if (h == Integer.MIN_VALUE) h = 0;  
        h ^= (h >>> 20) ^ (h >>> 12);  // Mix the bits of the hashcode
        h = h ^ (h >>> 7) ^ (h >>> 4);
        return Math.abs(h) % capacity;
    }

    private void resize() {
        int newCapacity = 0; 
        switch(capacity) {
            case 16:
                newCapacity = 29;
            case 29: 
                newCapacity = 23503;
            case 23503:
              newCapacity = 31357;
              break;
            case 31357:
              newCapacity = 41813;
              break;
            default:
              newCapacity = capacity/4*3;
              break;
        }   // order does not need to be preserved when resizing

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
        if ((size + 1) >= capacity * loadFactor) {
            resize();
        }

        int index = hash(key);
        for (Entry<K, V> entry = map[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return true;
            }
        }

        map[index] = new Entry<>(key, value, map[index]);
        this.size++;
        return true;
    }

    public V get(K key) {
        int index = hash(key);
        for (Entry<K, V> entry = map[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public V take(K key) {
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

    public boolean containsKey(K userid){
        int index = hash(userid);  
        Entry<K, V> entry = map[index]; 

        while (entry != null) {  
            if (entry.key.equals(userid)) {
                return true;  
            }
            entry = entry.next;  
        }
        return false;
    }


    public float[] values() {
        float[] valuesArray = new float[size];
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
        int i = 0;
        
        for (Entry<K, V> entry : map) {
            while (entry != null) {
                keyListArray[i++] = (int) entry.key;
                entry = entry.next;
            }
        }
        return keyListArray;
    }

    
    
}










