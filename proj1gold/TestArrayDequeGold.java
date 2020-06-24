import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testAddFirst() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
        for (int i = 0; i < 10000; i++) {
            sad1.addFirst(i);
            ads1.addFirst(i);
            assertEquals(ads1.get(0), sad1.get(0));
        }
    }

    @Test
    public void testAddLast() {
        StudentArrayDeque<Integer> sad2 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads2 = new ArrayDequeSolution<>();
        for (int i = 0; i < 10000; i++) {
            sad2.addLast(i);
            ads2.addLast(i);
            assertEquals(ads2.get(ads2.size() - 1), sad2.get(sad2.size() - 1));
        }
    }

    @Test
    public void testSize() {
        StudentArrayDeque<Integer> sad3 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads3 = new ArrayDequeSolution<>();
        for (int i = 0; i < 10000; i++) {
            double random = StdRandom.uniform();
            if (random < 0.5) {
                sad3.addLast(i);
                ads3.addLast(i);
                assertEquals(ads3.size(), sad3.size());
            } else {
                sad3.addFirst(i);
                ads3.addFirst(i);
                assertEquals(ads3.size(), sad3.size());
            }
        }
    }

    @Test
    public void testRemoveFirst() {
        StudentArrayDeque<Integer> sad4 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads4 = new ArrayDequeSolution<>();
        for (int j = 1; j < 10000; j++) {
            String message = "\n";
            for (int i = 0; i < 2; i++) {
                double random = StdRandom.uniform();
                if (random < 0.5) {
                    sad4.addLast(j + i);
                    ads4.addLast(j + i);
                    message += "addLast(" + j + i + ")" + "\n";
                } else {
                    sad4.addFirst(j + i);
                    ads4.addFirst(j + i);
                    message += "addFirst(" + j + i + ")" + "\n";
                }
            }
            message += "removeFirst()" + "\n";
            assertEquals(message, ads4.removeFirst(), sad4.removeFirst());
        }
    }


    @Test
    public void testRemoveLast() {
        StudentArrayDeque<Integer> sad5 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads5 = new ArrayDequeSolution<>();
        for (int j = 1; j < 10000; j++) {
            String message = "\n";
            for (int i = 0; i < 2; i++) {
                double random = StdRandom.uniform();
                if (random < 0.5) {
                    sad5.addLast(j + i);
                    ads5.addLast(j + i);
                    message += "addLast(" + j + i + ")" + "\n";
                } else {
                    sad5.addFirst(j + i);
                    ads5.addFirst(j + i);
                    message += "addFirst(" + j + i + ")" + "\n";
                }
            }
            message += "removeLast()" + "\n";
            assertEquals(message, ads5.removeLast(), sad5.removeLast());
        }
    }
}
