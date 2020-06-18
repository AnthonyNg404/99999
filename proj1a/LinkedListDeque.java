public class LinkedListDeque<T> {
    private StuffNode first;
    private int size;

    private class StuffNode {
        public T item;
        public StuffNode next;
        public StuffNode prev;

        public StuffNode(T i, StuffNode n, StuffNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }


    public LinkedListDeque() {
        this.first = new StuffNode(null, null, null);
        this.first.next = this.first;
        this.first.prev = this.first;
        this.size = 0;
    }

    public void addFirst(T item) {
        this.first.next = new StuffNode(item, this.first.next,this.first);
        this.first.next.next.prev = this.first.next;
        this.size += 1;
    }

    public void addLast(T item) {
        this.first.prev = new StuffNode(item, this.first, this.first.prev);
        this.first.prev.prev.next = this.first.prev;
        this.size += 1;
    }

    public boolean isEmpty() {
        if (this.size == 0){
            return true;
        }
        return false;
    }

    public int size() {
        return this.size;
    }

    private void printLauncher(StuffNode x) {
        if (x.next.item != null) {
            System.out.print(x.next.item + " ");
            printLauncher(x.next);
        }
        else{System.out.println(" ");}
    }

    public void printDeque() {
        printLauncher(this.first);
    }

    public T removeFirst() {
        T remove = this.first.next.item;
        this.first.next = this.first.next.next;
        this.first.next.prev = this.first;
        if (size > 0) {
            this.size -= 1;
        }
        return remove;
    }

    public T removeLast() {
        T remove = this.first.prev.item;
        this.first.prev = this.first.prev.prev;
        this.first.prev.next = this.first;
        if (size > 0) {
            this.size -= 1;
        }
        return remove;
    }

    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        StuffNode getOne = this.first.next;
        while (index > 0) {
            getOne = getOne.next;
            index -= 1;
        }
        return getOne.item;
    }

    private T getRecursiveLauncher(StuffNode x, int index) {
        StuffNode getReOne = x.next;
        while (index > 0) {
            getReOne = getReOne.next;
            index -= 1;
        }
        return getReOne.item;
    }

    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        return getRecursiveLauncher(this.first, index);
    }
}
