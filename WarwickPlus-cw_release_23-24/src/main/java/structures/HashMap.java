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

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private final double loadFactor = 0.75;

    @SuppressWarnings("unchecked")
    public HashMap() {
        this.capacity = 23503; // A prime number greater than 17,627 / 0.75
        table = new Entry[capacity];
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
        Entry<K, V>[] tempTable = new Entry[newCapacity];

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                int newIndex = Math.abs(entry.key.hashCode()) % newCapacity;
                Entry<K, V> nextEntry = entry.next;
                entry.next = tempTable[newIndex];
                tempTable[newIndex] = entry;
                entry = nextEntry;
            }
        }

        table = tempTable;
        this.capacity = newCapacity;
    }

    public boolean add(K key, V value) {
        if ((size + 1) >= capacity * loadFactor) {
            resize();
        }

        int index = hash(key);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                e.value = value;
                return true;
            }
        }

        table[index] = new Entry<>(key, value, table[index]);
        this.size++;
        return true;
    }

    public V get(K key) {
        int index = hash(key);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }
        return null;
    }

    public boolean remove(K key) {
        int index = hash(key);
        Entry<K, V> prev = null;
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                this.size--;
                return true;
            }
            prev = e;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.table = new Entry[capacity]; 
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