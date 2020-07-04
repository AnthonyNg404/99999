package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (p.key.compareTo(key) < 0) {
            return getHelper(key, p.right);
        } else if (p.key.compareTo(key) > 0) {
            return getHelper(key, p.left);
        }
        return null;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        size += 1;
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> kSet = new TreeSet<>();
        addSet(kSet, root);
        return kSet;
    }

    private void addSet(Set<K> s, Node p) {
        if (p == null) {
            return;
        }
        addSet(s, p.left);
        s.add(p.key);
        addSet(s, p.right);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (!keySet().contains(key)) {
            return null;
        }
        V value = get(key);
        removeHelp(key, null);
        size --;
        return value;
    }

    private void removeHelp(K key, V specVal) {
        V foundVal = get(key);
        if (foundVal == null) { return; } /* key not found */
        if (specVal != null && !foundVal.equals(specVal)) { return; } /* not matched */
        /* By now, we're sure that: 1. key exists 2. key and val match */
        root = delete(root, key); /* root might be deleted */
    }

    /* It's certain that dkey is in T; no need to check value now */
    private Node delete(Node T, K dKey) {
        if (T == null) { /* T == null and base case */
            return T; /* T is always null */
        }
        int cmp = dKey.compareTo(T.key);
        if (cmp < 0) {
            T.left = delete(T.left, dKey);
        } else if (cmp > 0) {
            T.right = delete(T.right, dKey);
        } else { /* dKey == T.key */
            if (T.left == null || T.right == null) { /* No Children & One Child */
                if (T.left != null) { /* Has a left child */
                    T = T.left; /* connect left child to its parent */
                } else { /* Has a right child or no child */
                    T = T.right; /* connect to right child or null */
                }
            } else { /* 2 Children */
                Node pred = T.left; // 1. Find pred (largest node in the left subtree)
                while (pred.right != null) { pred = pred.right; }
                T.left = delete(T.left, pred.key); // 2. Delete pred
                T.key = pred.key; // 3. Move pred to T
                T.value = pred.value;
            }
        }
        /* T might be null */
        return T;
    }

    private Node leftMost(Node p) {
        if (p.left == null && p.right == null) {
            Node q = p;
            p = null;
            return q;
        }
        return leftMost(p.left);
    }

    private Node rightMost(Node p) {
        if (p.left == null && p.right == null) {
            Node q = p;
            p = null;
            return q;
        }
        return rightMost(p.right);
    }



    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (!keySet().contains(key) || !get(key).equals(value)) {
            return null;
        }
        removeHelp(key, value);
        size --;
        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
