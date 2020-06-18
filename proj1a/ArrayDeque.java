public class ArrayDeque<T> {

    private T[] array;
    private int size;
    private int firstPosition;
    private int lastPosition;

    public ArrayDeque() {
        array = (T[]) new Object [8];
        size = 0;
        firstPosition = 0;
        lastPosition = 1;
    }

    private void resize(int cap) {
        T[] temp = (T[]) new Object[cap];
        if (firstPosition == array.length - 1) {
            firstPosition = -1;
        }
        System.arraycopy(array, firstPosition + 1, temp, 1, array.length - firstPosition - 1);
        if (size - array.length + firstPosition + 1 > 0) {
            System.arraycopy(array, 0, temp, array.length - firstPosition,
                    size - array.length + firstPosition + 1);
        }
        firstPosition = 0;
        lastPosition = size + 1;
        array = temp;
    }

    private void resizeDown(int cap) {
        T[] temp = (T[]) new Object[cap];
        if (firstPosition == array.length - 1) {
            firstPosition = -1;
        }
        if (size - array.length + firstPosition + 1 <= 0 ) {
            System.arraycopy(array, firstPosition + 1, temp, 1, size);
        } else {
            System.arraycopy(array, firstPosition + 1, temp, 1, array.length - firstPosition - 1);
            System.arraycopy(array, 0, temp, array.length - firstPosition,
                    size - array.length + firstPosition + 1);
        }
        firstPosition = 0;
        lastPosition = size + 1;
        array = temp;
    }

    public void addFirst(T item) {
        if (array.length / 2 == size) {
            resize(array.length * 2);
        }
        array[firstPosition] = item;
        if (firstPosition == 0) {
            firstPosition = array.length;
        }
        firstPosition -= 1;
        size += 1;
    }

    public void addLast(T item) {
        if (array.length / 2 == size) {
            resize(array.length * 2);
        }
        array[lastPosition] = item;
        if (lastPosition == array.length - 1) {
            lastPosition = -1;
        }
        lastPosition += 1;
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int k = 1; k <= size; k += 1) {
            int i = firstPosition + k;
            if (i >= array.length) {
                i -= array.length;
            }
            System.out.print(array[i] + " ");
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (firstPosition + 1 == array.length) {
            firstPosition = -1;
        }
        T temp = array[firstPosition + 1];
        array[firstPosition + 1] = null;
        firstPosition += 1;
        size -= 1;
        if (size <= array.length / 4) {
            resizeDown(array.length / 2);
        }
        return temp;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (lastPosition == 0) {
            lastPosition = array.length;
        }
        T temp = array[lastPosition - 1];
        array[lastPosition - 1] = null;
        lastPosition -= 1;
        size -= 1;
        if (size <= array.length / 4) {
            resizeDown(array.length / 2);
        }
        return temp;
    }

    public T get(int index) {
        int nIndex = firstPosition + index + 1;
        if (nIndex >= array.length) {
            nIndex -= array.length;
        }
        return array[nIndex];
    }

}
