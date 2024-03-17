package structures;

import stores.Company;
import stores.Person;

public class LinkedList<T> {

    private static class Node<T> {
        T element;
        Node<T> next;

        Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node<T> head = null; 
    private int size = 0; 

    public LinkedList() {
    }
    // Linkedlist acts as a stack
    public boolean add(T newElement) {
        if (newElement == null) return false; 
        if (head != null && head.element.equals(newElement)) return false; // Check for duplicates directly at the head.

        head = new Node<>(newElement, head); // New element becomes the new head.
        size++;
        return true;
    }

    public void appendList(LinkedList<T> other) {
        if (other == null || other.head == null) return; 
        if (this.head == null) { // If the current list is empty, point head to other's head.
            this.head = other.head;
        } else {
            Node<T> current = this.head;
            while (current.next != null) current = current.next; // Find the last node of the current list.
            current.next = other.head; // Link the last node of the current list to the head of the other list.
        }
        this.size += other.size; 
    }


    public boolean remove(T elementToRemove) {
        if (head == null) return false; 
        if (head.element.equals(elementToRemove)) {
            head = head.next; // Remove the head by pointing it to the next element.
            size--;
            return true;
        }

        // Traverse the list to find the element to remove.
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.element.equals(elementToRemove)) {
                current.next = current.next.next; // Bypass the removed node.
                size--;
                return true;
            }
            current = current.next;
        }

        return false; // Element not found.
    }

    public int[] getValues() {
        int[] values = new int[this.size];
        if (size == 0) return values;
        Node<T> current = this.head; // Start from the head node.
        int index = this.size - 1;  // Adds elements in reverse to counter stack effect.
        while (current != null) {
            values[index--] = (Integer) current.element; // Assumes all elements are Integers.
            current = current.next;
        }
        return values;
    }

    public Company[] getValuesCompany() {
        Company[] values = new Company[this.size];
        if (size == 0) return values;
        Node<T> current = this.head;
        int index = this.size - 1;
        while (current != null) {
            values[index--] = (Company) current.element;
            current = current.next;
        }
        return values;
    }

    public String[] getValuesCountry() {
        String[] values = new String[this.size];
        if (size == 0) return values;
        Node<T> current = this.head;
        int index = this.size - 1;
        while (current != null) {
            values[index--] = (String) current.element;
            current = current.next;
        }
        return values;
    }

    public Person[] getValuesPerson() {
        if (this.size == 0) return new Person[0];
        Person[] values = new Person[this.size];
        if (size == 0) return values;
        Node<T> current = this.head;
        int index = this.size - 1;
        while (current != null) {
            values[index--] = (Person) current.element;
            current = current.next;
        }
        return values;
    }

    public boolean contains(T element) { // Checks For Duplicates.
        Node<T> current = head;
        while (current != null) {
            if (current.element.equals(element)) return true; 
            current = current.next;
        }
        return false; 
    }

    // Old generic method.

    // public <E> E[] getValuez(Class<E> clazz) {
    //     @SuppressWarnings("unchecked")
    //     E[] values = (E[]) Array.newInstance(clazz, this.size);
    //     if (this.size == 0) return values;
    //     LinkedList<T> current = this;
    //     int index = 0;
    //     while (current != null) {
    //         values[index++] = clazz.cast(current.element);
    //         current = current.next;
    //     }
    //     return values;
    // }
    
    public int getSize(){
        return size;
    }
}