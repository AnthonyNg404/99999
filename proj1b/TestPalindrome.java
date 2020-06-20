import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome() {
        String a = "NooooN";
        String b = "Car";
        String c = "B3a66a3B";
        String d = "QwEr9TyyT9rEwQ";
        assertTrue(palindrome.isPalindrome(a));
        assertFalse(palindrome.isPalindrome(b));
        assertTrue(palindrome.isPalindrome(c));
        assertTrue(palindrome.isPalindrome(d));
    }

    @Test
    public void testisPalindromeOffOne() {
        String a = "flake";
        assertTrue(palindrome.isPalindrome(a, new OffByOne()));
    }

    //Uncomment this class once you've created your Palindrome class.
}
