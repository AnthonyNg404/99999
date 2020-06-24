import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testAddFirst() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        for (int i = 0; i < 10000; i++) {
            sad.addFirst(i);
            ads.addFirst(i);
            assertEquals(ads.get(0), sad.get(0));
        }
    }

    @Test
    public void testAddLast() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        for (int i = 0; i < 10000; i++) {
            sad.addLast(i);
            ads.addLast(i);
            assertEquals(ads.get(ads.size() - 1), sad.get(sad.size() - 1));
        }
    }

    @Test
    public void testSize() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        for (int i = 0; i < 10000; i++) {
            double random = StdRandom.uniform();
            if (random < 0.5) {
                sad.addLast(i);
                ads.addLast(i);
                assertEquals(ads.size(), sad.size());
            } else {
                sad.addFirst(i);
                ads.addFirst(i);
                assertEquals(ads.size(), sad.size());
            }
        }
    }

    @Test
    public void testRemoveFirst() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String message = null;
        for (int i = 0; i < 10000; i++) {
            sad.addLast(i);
            ads.addLast(i);
            assertEquals(sad.get(sad.size() - 1), ads.get(ads.size() - 1));
        }
        for (int i = 0; i < 10000; i++) {
            message += "removeFirst()" + "\n";
            assertEquals(message, ads.removeFirst(), sad.removeFirst());
        }
    }

    @Test
    public void testRemoveLast() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String message = null;
        for (int i = 0; i < 10000; i++) {
            sad.addFirst(i);
            ads.addFirst(i);
            assertEquals(sad.get(0), ads.get(0));
        }
        for (int i = 0; i < 10000; i++) {
            message += "removeLast()" + "\n";
            assertEquals(message, ads.removeLast(), sad.removeLast());
        }

    }
}
