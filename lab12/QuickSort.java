import edu.princeton.cs.algs4.Queue;

/**comment.
 * @author anthony
 * */

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     * @param <Item>  A Queue in sorted order from least to greatest.
     * @param q1  A Queue in sorted order from least to greatest.
     * @param q2  A Queue in sorted order from least to greatest.
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item>
        catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue.
     * @param <Item>  A Queue in sorted order from least to greatest.
     * @param items  A Queue in sorted order from least to greatest.
     */
    private static <Item extends Comparable> Item
        getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function
     *                  completes, this queue will contain
     *                  all of the items in unsorted that
     *                  are less than the given pivot.
     * @param equal     An empty Queue. When the function
     *                  completes, this queue will contain
     *                  all of the items in unsorted that
     *                  are equal to the given pivot.
     * @param greater   An empty Queue. When the function
     *                  completes, this queue will contain
     *                  all of the items in unsorted that
     *                  are greater than the given pivot.
     * @param <Item>  A Queue in sorted order from least to greatest.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        for (Item i : unsorted) {
            if (i.compareTo(pivot) < 0) {
                less.enqueue(i);
            } else if (i.compareTo(pivot) > 0) {
                greater.enqueue(i);
            } else {
                equal.enqueue(i);
            }
        }
    }

    /** Returns a Queue that contains the.
     * @param <Item>  A Queue in sorted order from least to greatest.
     * @param items  A Queue in sorted order from least to greatest.
     * given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        Item item = getRandomItem(items);
        Queue<Item> less = new Queue<Item>();
        Queue<Item> equal = new Queue<Item>();
        Queue<Item> greater = new Queue<Item>();
        partition(items, item, less, equal, greater);
        System.out.println(less);
        if (less.size() > 1) {
            less = quickSort(less);
        }
        if (greater.size() > 1) {
            greater = quickSort(greater);
        }
        Queue<Item> a = catenate(less, equal);
        items = catenate(a, greater);
        return items;
    }
}
