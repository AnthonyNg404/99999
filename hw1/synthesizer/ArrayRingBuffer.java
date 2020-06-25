package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;
        private ArrayRingBufferIterator() {
            pos = 0;
        }
        public boolean hasNext() {
            return pos < capacity;
        }
        public T next() {
            T returnItem = rb[pos];
            pos += 1;
            return returnItem;
        }
    }

    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        if (last == this.capacity - 1) {
            last = -1;
        }
        last += 1;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T temp;
        if (first == this.capacity - 1) {
            temp = rb[first];
            first = 0;
            fillCount -= 1;
        } else {
            temp = rb[first];
            first += 1;
            fillCount -= 1;
        }
        return temp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[first];
    }

}
