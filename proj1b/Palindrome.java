import com.sun.source.tree.BreakTree;

public class Palindrome {

    static Palindrome palindrome = new Palindrome();

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word.length() > 1) {
            if (word.charAt(0) == word.charAt(word.length() - 1)) {
                return isPalindrome(word.substring(1, word.length() - 1));
            }
            return false;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() > 1) {
            if (cc.equalChars(word.charAt(0), word.charAt(word.length() - 1))) {
                return isPalindrome(word.substring(1, word.length() - 1), cc);
            }
            return false;
        }
        return true;
    }
}
