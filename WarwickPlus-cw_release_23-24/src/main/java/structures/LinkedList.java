package structures;

import stores.Company;

public class LinkedList<T> {

    public T element;
    public LinkedList<T> next;
    public int size;

    public LinkedList(T element) {
        this.element = element;
        this.next = null;
        this.size = 1; // Initialize with 1 since we're adding the first element
    }

    public boolean add(T newElement) {
        if (this.element.equals(newElement)) {
            return false;
        }
        LinkedList<T> current = this;
        while (current.next != null) {
            current = current.next;
            if (current.element.equals(newElement)) {
                return false;
            }
        }
        current.next = new LinkedList<>(newElement);
        this.size++;
        return true;
    }

    public void appendList(LinkedList<T> other) {
        if (other == null) return;
        LinkedList<T> current = this;
        while (current.next != null) {
            current = current.next;
        }
        current.next = other;
        // Adjust the size to include the size of the other list
        LinkedList<T> temp = other;
        while (temp != null) {
            this.size++;
            temp = temp.next;
        }
    }

    public boolean remove(T elementToRemove) {
        if (this.element == null) {
            return false;
        }
    
        if (this.element.equals(elementToRemove)) {
            if (this.next != null) {
                this.element = this.next.element;
                this.next = this.next.next;
            } else {
                this.element = null;
            }
            this.size--;
            return true;
        }
    
        LinkedList<T> current = this;
        while (current.next != null) {
            if (current.next.element.equals(elementToRemove)) {
                current.next = current.next.next;
                this.size--;
                return true;
            }
            current = current.next;
        }
    
        return false;
    }
    

    public int[] getValues() {
        int[] values = new int[this.size];
        LinkedList<T> current = this;
        int index = 0;
        while (current != null) {
            values[index++] = (Integer) current.element;
            current = current.next;
        }
        return values;
    }

    public Company[] getValuesCompany() {
        Company[] values = new Company[this.size];
        LinkedList<T> current = this;
        int index = 0;
        while (current != null) {
            values[index++] = (Company) current.element;
            current = current.next;
        }
        return values;
    }

    public String[] getValuesCountry() {
        String[] values = new String[this.size];
        LinkedList<T> current = this;
        int index = 0;
        while (current != null) {
            values[index++] = (String) current.element;
            current = current.next;
        }
        return values;
    }
}