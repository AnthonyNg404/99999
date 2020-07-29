import edu.princeton.cs.algs4.MinPQ;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class BinaryTrie implements Serializable {
    //private static final int R = 256;
    private Node root;

    private static class Node implements Comparable<Node>, Serializable {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        // initialze priority queue with singleton trees

        MinPQ<Node> pq = new MinPQ<Node>();
        for (char c : frequencyTable.keySet()) {
            pq.insert(new Node(c, frequencyTable.get(c), null, null));
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        root = pq.delMin();
    }



    public Match longestPrefixMatch(BitSequence querySequence) {
        char symbol = root.ch;
        BitSequence b = new BitSequence();
        Node n = root;
        for (int i = 0; i < querySequence.length(); i++) {
            if (querySequence.bitAt(i) == 0) {
                b = b.appended(0);
                n = n.left;
            } else {
                b = b.appended(1);
                n = n.right;
            }
            symbol = n.ch;
            if (n.isLeaf()) {
                break;
            }
        }
        return new Match(b, symbol);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        return buildTable(root, new BitSequence(), new HashMap<Character, BitSequence>());
    }

    private Map<Character, BitSequence>
        buildTable(Node n, BitSequence s, Map<Character, BitSequence> table) {
        if (n.isLeaf()) {
            table.put(n.ch, s);
        }
        if (!(n.left == null)) {
            table = buildTable(n.left, s.appended(0), table);
        }
        if (!(n.right == null)) {
            table = buildTable(n.right, s.appended(1), table);
        }
        return table;
    }

}
