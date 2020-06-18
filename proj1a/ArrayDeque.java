public class ArrayDeque<T> {

    private T[] array;
    private int size;
    private int firstPosition;
    private int lastPosition;

    public ArrayDeque() {
        array = (T[]) new Object [8];
        size = 0;
        firstPosition = 3;
        lastPosition = 4;
    }

    private void resize(int cap) {
        T[] temp = (T[]) new Object[cap];
        System.arraycopy(array, 0, temp, cap / 4, cap / 2);
        array = temp;
        firstPosition += cap/4;
        lastPosition += cap/4;
    }

    public void addFirst(T item){
        if(array.length / 2 == size){
            resize(array.length * 2);
        }
        array[firstPosition] = item;
        firstPosition -= 1;
        size += 1;
    }

    public void addLast(T item){
        if(array.length / 2 == size){
            resize(array.length * 2);
        }
        array[lastPosition] = item;
        lastPosition += 1;
        size += 1;
    }

    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        return false;
    }

    public int size(){
        return array.length;
    }

    public void printDeque(){
        for(int k = 1; k > size; k += 1){
            System.out.print(array[firstPosition + k] + " ");
        }
    }

    public T removeFirst(){
        T temp = array[firstPosition + 1];
        array[firstPosition + 1] = null;
        return array[firstPosition + 1];
    }

    public T removeLast(){
        T temp = array[lastPosition - 1];
        array[lastPosition - 1] = null;
        return array[lastPosition - 1];
    }

    public T get(int index){
        return array[firstPosition + index + 1];
    }

}