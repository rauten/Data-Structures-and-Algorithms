/**
 * @author Riley Auten
 * @version 1.0
 */
import java.util.NoSuchElementException;
public class SinglyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(int index, T data) throws IndexOutOfBoundsException,
        IllegalArgumentException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index not valid");
        } else if (data == null) {
            throw new IllegalArgumentException("Please provide valid data");
        }
        int counter = 0;
        LinkedListNode<T> added = new LinkedListNode<T>(data);
        LinkedListNode<T> current = head;
        LinkedListNode<T> after = null;
        if (index == 0) {
            head = new LinkedListNode<T>(data, head);
            size++;
        } else {
            while (counter != index) {
                after = current;
                current = current.getNext();
                counter++;
            }
            after.setNext(added);
            after = after.getNext();
            added.setNext(current);
            size++;
        }
    }

    @Override //done
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Your index is out of bounds");
        }
        int counter = 0;
        LinkedListNode<T> current = head;
        while (counter != index) {
            current = current.getNext();
            counter++;
        }
        return current.getData(); //return the data value at the current node
    }

    @Override
    public T removeAtIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Yo bro");
        }
        int counter = 0;
        LinkedListNode<T> current = head;
        LinkedListNode<T> previous = null;
        if (index == 0) {
            removeFromFront();
            return current.getData();
        } else {
            while (counter != index) {
                previous = current;
                current = current.getNext();
                counter++;
            }
        }
        T removed = current.getData();
        current = current.getNext();
        previous.setNext(current);
        size--;
        return removed;
    }

    @Override
    public void addToFront(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Please provide valid data");
        }
        head = new LinkedListNode<T>(data, head);
        size++;

    }

    @Override //done
    public void addToBack(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Please provide valid data");
        }
        LinkedListNode<T> back = new LinkedListNode<T>(data, null);
        if (head == null) {
            head = back;
        } else {
            LinkedListNode<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(back);
        }
        size++;
    }

    @Override
    public T removeFromFront() {
        if (head == null) {
            return null;
        }
        T removed = head.getData();
        if (size == 0) {
            return null;
        } else {
            head = head.getNext();
            size--;
        }
        return removed;

    }

    @Override
    public T removeFromBack() {
        LinkedListNode<T> current = head;
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T data = head.getData();
            head = null;
            return data;
        }
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        T data = current.getNext().getData();
        current.setNext(null);
        size--;
        return data;
    }
    @Override
    public int removeFirstOccurrence(T data) throws NoSuchElementException,
        IllegalArgumentException {
        int counter = 0;
        LinkedListNode<T> current = head;
        LinkedListNode<T> previous = null;
        if (head == null) {
            throw new IllegalArgumentException("You suck");
        }
        if (head.getData().equals(data)) {
            removeFromFront();

        } else {
            while (current.getData() != data) {
                previous = current;
                current = current.getNext();
                if (current == null) {
                    throw new NoSuchElementException("Your data");
                }
                counter++;
            }
            current = current.getNext();
            previous.setNext(current);
            size--;
        }
        return counter;
    }

    @Override //done
    public Object[] toArray() {
        int counter = 0;
        Object[] listArray = new Object[size];
        LinkedListNode current = head;
        while (current != null) {
            listArray[counter] = current.getData();
            current = current.getNext();
            counter++;
        }
        return listArray;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
    @Override
    public int size() {
        return this.size;
    }

    @Override //done
    public void clear() {
        head = null;
        size = 0;
    }

    @Override //done
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
    @Override
    public String toString() {
        String answer = "";
        LinkedListNode<T> current = head;
        while (current != null) {
            answer += current + " ";
            current = current.getNext();
        }
        return answer;
    }
    public static void main(String[] args) {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        System.out.println(list);
    }
}
