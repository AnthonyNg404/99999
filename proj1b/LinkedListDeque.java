public class LinkedListDeque<Item> implements Deque<Item> {
    private StuffNode first;
    private int size;

    private class StuffNode {
        private Item item;
        private StuffNode next;
        private StuffNode prev;

        public StuffNode(Item i, StuffNode n, StuffNode p) {
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

    @Override
    public void addFirst(Item item) {
        this.first.next = new StuffNode(item, this.first.next, this.first);
        this.first.next.next.prev = this.first.next;
        this.size += 1;
    }

    @Override
    public void addLast(Item item) {
        this.first.prev = new StuffNode(item, this.first, this.first.prev);
        this.first.prev.prev.next = this.first.prev;
        this.size += 1;
    }

    @Override
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    private void printLauncher(StuffNode x) {
        if (x.next.item != null) {
            System.out.print(x.next.item + " ");
            printLauncher(x.next);
        } else {
            System.out.println(" ");
        }
    }

    @Override
    public void printDeque() {
        printLauncher(this.first);
    }

    @Override
    public Item removeFirst() {
        Item remove = this.first.next.item;
        this.first.next = this.first.next.next;
        this.first.next.prev = this.first;
        if (size > 0) {
            this.size -= 1;
        }
        return remove;
    }

    @Override
    public Item removeLast() {
        Item remove = this.first.prev.item;
        this.first.prev = this.first.prev.prev;
        this.first.prev.next = this.first;
        if (size > 0) {
            this.size -= 1;
        }
        return remove;
    }

    @Override
    public Item get(int index) {
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

    private Item getRecursiveLauncher(StuffNode x, int index) {
        StuffNode getReOne = x.next;
        while (index > 0) {
            getReOne = getReOne.next;
            index -= 1;
        }
        return getReOne.item;
    }

    public Item getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        return getRecursiveLauncher(this.first, index);
    }
}
