import edu.princeton.cs.algs4.Queue;

/**comment.
 * @author anthony
 * */

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order,
     * with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @param   <Item>  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items.
     * @param   <Item>  A Queue in sorted order from least to greatest.
     * @param items  A Queue in sorted order from least to greatest.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> toReturn = new Queue<>();
        for (Item a : items) {
            Queue<Item> b = new Queue<>();
            b.enqueue(a);
            toReturn.enqueue(b);
        }
        return toReturn;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear
     * in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty,
     * and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order,
     * @param <Item>  A Queue in sorted order from least to greatest.
     * from least to
     *              greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> a = new Queue<>();
        while (q1.size() != 0 || q2.size() != 0) {
            a.enqueue(getMin(q1, q2));
        }
        return a;
    }

    /** Returns a Queue that contains the.
     * @param <Item>  A Queue in sorted order from least to greatest.
     * @param items  A Queue in sorted order from least to greatest.
    given items sorted from least to greatest.*/
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> a = makeSingleItemQueues(items);
        while (a.size() > 1) {
            Queue<Item> b = a.dequeue();
            Queue<Item> c = a.dequeue();
            a.enqueue(mergeSortedQueues(b, c));
        }
        items = a.dequeue();
        return items;
    }
}
