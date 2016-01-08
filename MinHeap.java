import java.util.NoSuchElementException;

/**
 * @author Riley Auten
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        T swap;
        int old;
        if (backingArray[backingArray.length - 1] != null) {
            T[] other = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 1; i < backingArray.length; i++) {
                other[i] = backingArray[i];
            }
            backingArray = other;
        }
        if (size == 0) {
            size++;
            backingArray[1] = item;
        } else {
            size++;
            backingArray[size] = item;
            int i = size;
            while (i != 1) {
                if (i % 2 == 0) {
                    old = i;
                    i = (i / 2);
                } else {
                    old = i;
                    i = ((i - 1) / 2);
                }
                swap = backingArray[i];
                if (swap.compareTo(item) > 0) {
                    backingArray[i] = item;
                    backingArray[old] = swap;
                } else {
                    return;
                }
            }
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty. Cannot remove");
        }
        T first = backingArray[1];
        T swap;
        T last = backingArray[size];
        backingArray[1] = last;
        backingArray[size] = null;
        size--;
        int i = 1;
        while (((2 * i) + 1) <= size) {
            int uno = (2 * i) + 1;
            int dos = (2 * i);
            if (backingArray[uno].compareTo(backingArray[dos]) > 0) {
                swap = backingArray[dos];
                backingArray[dos] = backingArray[i];
                backingArray[i] = swap;
                i = dos;
            } else if (backingArray[uno].compareTo(backingArray[dos]) < 0) {
                swap = backingArray[uno];
                backingArray[uno] = backingArray[i];
                backingArray[i] = swap;
                i = uno;
            }
        }
        if ((2 * i) <= size
            && backingArray[i].compareTo(backingArray[(2 * i)]) > 0) {
            swap = backingArray[(2 * i)];
            backingArray[2 * i] = backingArray[i];
            backingArray[i] = swap;
        }
        return first;
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
        backingArray = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    /**
     * @return the backing array
     */
    public Comparable[] getBackingArray() {
        return backingArray;
    }
}
