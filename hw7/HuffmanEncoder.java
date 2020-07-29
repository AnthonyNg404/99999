import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : inputSymbols) {
            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.replace(c, map.get(c) + 1);
            }
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        /*In text = new In("C:\\Users\\David\\Desktop\\61B\\hw7\\watermelonsugar.txt");
        In text1 = new In("C:\\Users\\David\\Desktop\\61B\\hw7\\watermelonsugar.txt");
        int count = 0;
        while (text.hasNextChar()) {
            char a = text.readChar();
            count += 1;
        }
        char[] input = new char[count];
        for (int i = 0; i < count; i++) {
            input[i] = text1.readChar();
        }*/
        char[] input = FileUtils.readFile(args[0]);
        int count = input.length;
        /*for (char c : input) {
            System.out.println(c);
        }*/
        Map<Character, Integer> frequency = buildFrequencyTable(input);
        BinaryTrie trie = new BinaryTrie(frequency);
        Map<Character, BitSequence> table = trie.buildLookupTable();
        BitSequence[] codes = new BitSequence[count];
        List<BitSequence> code = new ArrayList<>();
        for (char c : input) {
            code.add(table.get(c));
        }
        BitSequence encoded = BitSequence.assemble(code);
        //System.out.println(encoded);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(trie);
        ow.writeObject(count);
        ow.writeObject(encoded);
    }

}
