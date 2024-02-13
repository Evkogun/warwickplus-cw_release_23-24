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
}











/* 

    
    public boolean add(E element) {
        try {
             if (this.size >= this.capacity) {
                this.capacity = (capacity * 2);
                Object[] tmp = new Object[this.capacity];
                for (int i = 0; i < this.size; i++) {
                    tmp[i] = this.array[i];
                }        
                this.array = tmp;
            }

            this.array[size] = element;
            this.size++;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(this.array[i])) {return true;}
        }
        return false;
    }

    public void clear() {
        this.capacity = 100;
        this.array = new Object[capacity];
        this.size = 0;
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public int size() {
        return size;
    }
    
    // This line allows us to cast our object to type (E) without any warnings.
    // For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
    @SuppressWarnings("unchecked") 
    public E get(int index) {
        return (E) this.array[index];
    }
    
    public int indexOf(E element) {
        for (int i=0;i<this.size();i++) {
            if (element.equals(this.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public boolean remove(E element) {
        int index = this.indexOf(element);
        if (index >= 0) {
            for (int i=index+1;i<this.size();i++) {
                this.set(i-1, this.get(i));
            }
            this.array[size-1] = null;
            size--;
            return true;
        }
        return false;
    }

    public E set(int index, E element) {
        if (index >= this.size()) {
            throw new ArrayIndexOutOfBoundsException("index > size: "+index+" >= "+size);
        }
        E replaced = this.get(index);
        this.array[index] = element;
        return replaced;
    }
    
    
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        StringBuilder ret = new StringBuilder("[");
        for (int i=0;i<size;i++) {
            ret.append(this.get(i)).append(", ");
        }
        ret.deleteCharAt(ret.length()-1);
        ret.setCharAt(ret.length()-1, ']');
        return ret.toString();
    }
    
}
*/