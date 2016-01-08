/**
 * @author Riley Auten
 * @version 1.0
 */
import java.util.NoSuchElementException;
public class CircularDoublyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    /**
     * Creates an empty circular doubly-linked list.
     */
    public CircularDoublyLinkedList() {
    }

    /**
     * Creates a circular doubly-linked list with
     * {@code data} added to the list in order.
     * @param data The data to be added to the LinkedList.
     * @throws java.lang.IllegalArgumentException if {@code data} is null or any
     * item in {@code data} is null.
     */
    public CircularDoublyLinkedList(T[] data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        addToBack(data[0]);
        for (int i = 1; i < data.length; i++) {
            if (data[i] == null) {
                throw new IllegalArgumentException();
            } else {
                addToBack(data[i]);
            }
        }
    }

    @Override //done
    public void addAtIndex(int index, T data) throws IndexOutOfBoundsException,
        IllegalArgumentException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index not valid");
        } else if (data == null) {
            throw new IllegalArgumentException("Please provide valid data");
        }
        int counter = 0;
        LinkedListNode<T> current = head;
        LinkedListNode<T> after = null;
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            current = current.getPrevious();
            LinkedListNode<T> last = new LinkedListNode<T>(data, current, head);
            last.getNext().setPrevious(last);
            current.setNext(last);
            size++;
        } else {
            while (counter != index) {
                after = current;
                current = current.getNext();
                counter++;
            }
            LinkedListNode<T> added =
                new LinkedListNode<T>(data, after, current);
            added.getNext().setPrevious(added);
            after.setNext(added);
            size++;
        }
    }

    @Override //done
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Your index is out of bounds");
        }
        LinkedListNode<T> current = head;
        int counter = 0;
        if (index == 0) {
            return head.getData();
        } else if (index == (size - 1)) {
            return head.getPrevious().getData();
        } else {
            while (counter != index) {
                current = current.getNext();
                counter++;
            }
        }
        return current.getData();
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int counter = 0;
        LinkedListNode<T> current = head;
        LinkedListNode<T> previous = null;
        if (index == 0) {
            removeFromFront();
            return current.getData();
        } else if (index == (size  - 1)) {
            current = head.getPrevious();
            removeFromBack();
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

    @Override //done
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Please provide valid data");
        }
        if (isEmpty()) {
            head = new LinkedListNode<T>(data);
            head.setNext(head);
            head.setPrevious(head);
            size++;
        } else {
            head = new LinkedListNode<T>(data, head.getPrevious(), head);
            head.getPrevious().setNext(head);
            head.getNext().setPrevious(head);
            size++;
        }
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Please provide valid data");
        }
        if (size == 0) {
            addToFront(data);
        } else if (size == 1) {
            addAtIndex(1, data);
        } else {
            addAtIndex(size, data);
        }
    }

    @Override //done
    public T removeFromFront() {
        LinkedListNode<T> current = head;
        T removed = null;
        if (size == 0) {
            return null;
        } else if (size == 1) {
            removed = head.getData();
            clear();
        } else if (size == 2) {
            removed = head.getData();
            current = current.getPrevious();
            current.setNext(current);
            current.getNext().setPrevious(current);
            size--;
        } else {
            removed = head.getData();
            current = current.getPrevious();
            current.setNext(current.getNext().getNext());
            current.getNext().setPrevious(current);
            head = current.getNext();
            size--;
        }
        return removed;
    }

    @Override //done
    public T removeFromBack() {
        T removed = null;
        if (size == 0) {
            return null;
        } else if (size == 1) {
            removed = head.getData();
            clear();
        } else if (size == 2) {
            removed = head.getPrevious().getData();
            head.setPrevious(head);
            head.getPrevious().setNext(head);
            size--;
        } else {
            removed = head.getPrevious().getData();
            head.setPrevious(head.getPrevious().getPrevious());
            head.getPrevious().setNext(head);
            size--;
        }
        return removed;
    }

    @Override //done
    public int removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        }
        LinkedListNode<T> current = head;
        int counter = 0;
        if (head.getData().equals(data)) {
            removeFromFront();
            return counter;
        }
        counter++;
        while (current.getNext() != head) {
            if (current.getNext().getData().equals(data)) {
                current.setNext(current.getNext().getNext());
                current.getNext().setPrevious(current);
                size--;
                return counter;
            } else {
                current = current.getNext();
                counter++;
            }
        }
        throw new NoSuchElementException("Data not in list");
    }

    @Override //done
    public boolean removeAllOccurrences(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        }
        LinkedListNode<T> current = head;
        boolean truth = false;
        int first = 0;
        int second = 0;
        if (head.getData().equals(data)) {
            first = 1;
            truth = true;
        }
        while (current.getNext() != head) {
            if (current.getNext().getData().equals(data)) {
                current.setNext(current.getNext().getNext());
                current.getNext().setPrevious(current);
                truth = true;
                size--;
            } else {
                current = current.getNext();
            }
        }
        if (first == 1) {
            removeFromFront();
            size--;
        }
        return truth;
    }

    @Override
    public Object[] toArray() {
        Object[] empty = new Object[0];
        if (size == 0) {
            return empty;
        }
        int counter = 1;
        Object[] listArray = new Object[size];
        listArray[0] = head.getData();
        LinkedListNode current = head.getNext();
        while (current != head) {
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
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * @return LikedListNode: node that is the head of the Linked List
     */
    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }
}
