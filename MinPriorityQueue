import java.util.NoSuchElementException;

/**
 * @author Riley Auten
 * @version 1.0
 */
public class MinPriorityQueue<T extends Comparable<? super T>>
    implements PriorityQueueInterface<T> {

    private MinHeap<T> backingHeap;

    /**
     * Creates a priority queue.
     */
    public MinPriorityQueue() {
        backingHeap = new MinHeap();
    }

    @Override
    public void enqueue(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot have null data");
        }
        backingHeap.add(item);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Your queue is empty.");
        }
        return backingHeap.remove();
    }

    @Override
    public boolean isEmpty() {
        return (backingHeap.isEmpty()); //could need to use size instead
    }

    @Override
    public int size() {
        return backingHeap.size();
    }

    @Override
    public void clear() {
        backingHeap.clear();
    }

    /**
     * @return the backing heap
     */
    public MinHeap<T> getBackingHeap() {
        return backingHeap;
    }
}
