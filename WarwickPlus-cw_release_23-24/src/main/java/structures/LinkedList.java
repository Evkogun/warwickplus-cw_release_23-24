package structures;
import stores.Person;
import stores.Company;

public class LinkedList<T> {

    public T element;
    public LinkedList<T> next;
    public int size;

    public LinkedList() {
        this.element = null;
        this.next = null;
        this.size = 0;
    }

    public boolean add(T newElement) {
        
        if (this.size == 0) {
            this.element = newElement;
            this.size = 1;
            return true;
        } else {
            LinkedList<T> current = this;
            while (current.next != null) {
                current = current.next;
                if (current.element.equals(newElement)) {
                    return false;
                }
            }
            current.next = new LinkedList<>();
            current.next.element = newElement;
            this.size++;
            return true;
        }
    }

    public void appendList(LinkedList<T> other) {
        if (other == null) return;
        LinkedList<T> current = this;
        while (current.next != null) {
            current = current.next;
        }
        current.next = other;
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
        if (size == 0) return new int[0];
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

    public Person[] getValuesPerson() {
        if (this.size == 0) return new Person[0];
        Person[] values = new Person[this.size];
        LinkedList<T> current = this;
        int index = 0;
        while (current != null) {
            values[index++] = (Person) current.element;
            current = current.next;
        }
        return values;
    }
}