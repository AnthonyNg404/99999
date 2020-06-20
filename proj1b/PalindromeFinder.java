/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {

    public static void main(String[] args) {
        int minLength = 4;
        int count = 0;
        In in = new In("C:/Users/antho/Desktop/61B/proj1b/words.txt");
        Palindrome palindrome = new Palindrome();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, new OffByN(0))) {
                System.out.println(word);
                count++;
            }
        }
        System.out.println(count);
    }
    //Uncomment this class once you've written isPalindrome. */
}
