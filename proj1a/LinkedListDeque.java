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

    public LinkedListDeque(T item) {
        this.first = new StuffNode(null, null, null);
        this.first.next = new StuffNode(item, this.first, this.first);
        this.first.prev = this.first.next;
        this.size = 1;
    }


    public void addFirst(T item){
        this.first.next = new StuffNode(item, this.first.next,this.first);
        this.first.next.next.prev = this.first.next;
        this.size += 1;
    }

    public void addLast(T item){
        this.first.prev = new StuffNode(item, this.first, this.first.prev);
        this.first.prev.prev.next = this.first.prev;
        this.size += 1;
    }

    public boolean isEmpty(){
        if (this.size == 0){
            return true;
        }
        return false;
    }

    public int size(){
        return this.size;
    }

    private void printLauncher(StuffNode x){
        if (x.next.item != null) {
            System.out.print(x.next.item + " ");
            printLauncher(x.next);
        }
        else{System.out.println(" ");}
    }

    public void printDeque(){
        printLauncher(this.first);
    }

    public T removeFirst(){
        T remove = this.first.next.item;
        this.first.next = this.first.next.next;
        this.first.next.prev = this.first;
        this.size -= 1;
        return remove;
    }

    public T removeLast(){
        T remove = this.first.prev.item;
        this.first.prev = this.first.prev.prev;
        this.first.prev.next = this.first;
        this.size -= 1;
        return remove;
    }

    public T get(int index){
        StuffNode getOne = this.first.next;
        while (index != 1){
            index -= 1;
            getOne = getOne.next;
        }
        return getOne.item;
    }

    private T getRecursiveLauncher(StuffNode x, int index){
        StuffNode getOne = x.next;
        if (index != 1){
            getRecursiveLauncher(getOne.next, index - 1);
        }
        return getOne.item;
    }

    public T getRecursive(int index){
        return getRecursiveLauncher(this.first, index);
    }
}