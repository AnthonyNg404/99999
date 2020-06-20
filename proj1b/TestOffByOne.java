import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    static CharacterComparator offByN = new OffByN(5);

    @Test
    public void testequalChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('X', 'Y'));
        assertFalse(offByOne.equalChars('9', 'Y'));
        assertFalse(offByOne.equalChars('9', '%'));
    }

    @Test
    public void testequalCharsN() {
        assertTrue(offByN.equalChars('a', 'f'));
        assertTrue(offByN.equalChars('c', 'h'));
        assertFalse(offByN.equalChars('a', 'b'));
    }

    // Your tests go here.
    /**Uncomment this class once you've created your
    CharacterComparator interface and OffByOne class.*/
}
