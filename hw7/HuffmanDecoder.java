public class HuffmanDecoder {

    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        Object x = or.readObject();
        Object y = or.readObject();
        Object z = or.readObject();
        BinaryTrie trie = (BinaryTrie) x;
        int count = (int) y;
        BitSequence code = (BitSequence) z;
        char[] ch = new char[count];
        for (int i = 0; i < ch.length; i++) {
            Match m = trie.longestPrefixMatch(code);
            ch[i] = m.getSymbol();
            code = code.allButFirstNBits(m.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], ch);
    }
}
