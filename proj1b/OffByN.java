public class OffByN implements CharacterComparator {
    private int digit;

    public OffByN(int N) {
        digit = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int a = x;
        int b = y;
        if (Math.abs(a - b) == digit) {
            return true;
        }
        return false;
    }

}
