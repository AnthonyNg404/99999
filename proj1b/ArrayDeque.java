public class ArrayDeque<Item> implements Deque<Item> {

    private Item[] array;
    private int size;
    private int firstPosition;
    private int lastPosition;

    public ArrayDeque() {
        array = (Item[]) new Object [8];
        size = 0;
        firstPosition = 0;
        lastPosition = 1;
    }

    private void resize(int cap) {
        Item[] temp = (Item[]) new Object[cap];
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
        Item[] temp = (Item[]) new Object[cap];
        if (firstPosition == array.length - 1) {
            firstPosition = -1;
        }
        if (size - array.length + firstPosition + 1 <= 0) {
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

    @Override
    public void addFirst(Item item) {
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

    @Override
    public void addLast(Item item) {
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

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int k = 1; k <= size; k += 1) {
            int i = firstPosition + k;
            if (i >= array.length) {
                i -= array.length;
            }
            System.out.print(array[i] + " ");
        }
    }

    @Override
    public Item removeFirst() {
        if (size == 0) {
            return null;
        }
        if (firstPosition + 1 == array.length) {
            firstPosition = -1;
        }
        Item temp = array[firstPosition + 1];
        firstPosition += 1;
        size -= 1;
        if (size <= array.length / 4) {
            resizeDown(array.length / 2);
        }
        return temp;
    }

    @Override
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        if (lastPosition == 0) {
            lastPosition = array.length;
        }
        Item temp = array[lastPosition - 1];
        lastPosition -= 1;
        size -= 1;
        if (size <= array.length / 4) {
            resizeDown(array.length / 2);
        }
        return temp;
    }

    @Override
    public Item get(int index) {
        int nIndex = firstPosition + index + 1;
        if (nIndex >= array.length) {
            nIndex -= array.length;
        }
        return array[nIndex];
    }

}
