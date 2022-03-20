package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Kevin Zhang
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
    //为什么递归时总会有helper函数？还是因为数据结构的封装抽象特性！
    // public的函数只想看到一个标签key，但真正能够递归实现的基础结构是Node
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (key.equals(p.key)) { //千万注意!!!习惯用equals而不是==!!!
            return p.value;
        } else if (key.compareTo(p.key) < 0) { //习惯用compareTo而不是>/<!!!
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }
    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        } else if (key.equals(p.key)) {
            p.value = value;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
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
        return keySet(root);
    }

    private Set<K> keySet(Node p) {
        if (p == null) {
            return new TreeSet<>();
        }
        Set<K> curLeft = keySet(p.left);
        Set<K> curRight = keySet(p.right);
        curLeft.addAll(curRight);
        return curLeft;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */

    //以下为站在Node的角度对remove的实现
    @Override
    public V remove(K key) {
        Node removedNode = remove(key, root, null);
        if (removedNode == null) {
            return null;
        }
        size--;
        return removedNode.value;
    }

    private Node remove(K key, Node cur, Node prev) {
        if (cur == null) {
            return null;
        } else if (key.compareTo(cur.key) < 0) {
            return remove(key, cur.left, cur);
        } else if (key.compareTo(cur.key) > 0) {
            return remove(key, cur.right, cur);
        } else {
            if (cur.left == null && cur.right == null) {
                update(prev, cur, null);
            } else if (cur.left == null) {
                update(prev, cur, cur.right);
            } else if (cur.right == null) {
                update(prev, cur, cur.left);
            } else {
                Node newRoot = getNewRoot(cur);
                newRoot.left = cur.left;
                newRoot.right = cur.right;
                update(prev, cur, newRoot);
            }
            return cur;
        }
    }

    private void update(Node prev, Node cur, Node newCur) {
        if (prev == null) {
            root = newCur;
        } else if (cur.key.compareTo(prev.key) < 0) {
            prev.left = newCur;
        } else {
            prev.right = newCur;
        }
    }

    private Node getNewRoot(Node p) {
        Node prev = p;
        Node cur = p.left;
        while (cur.right != null) {
            prev = cur;
            cur = cur.right;
        }
        return remove(cur.key, cur, prev);
    }

    //以下为一种更好得多的remove的实现方法：真正站在tree的角度思考，每个Node实际上代表了它下面的整棵树，你要做的是更新这棵树的左/右节点，
    //以此为标准考虑最后一次递归的直接返回处的返回值更新，据此设计base case
    //所以base case里找到那个要删掉的Node时，你返回的是它的left或right，用于回去更新递归返回处那棵树的左/右节点

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    public V remove2(K key) {
        V retValue = get(key);
        if (retValue == null) {
            return null;
        }
        root = remove2(key, root);
        size--;
        return retValue;
    }

    /** return a new tree which removes the smallest key
     *  and associated value in the given tree.
     */
    private Node removeMin(K key, Node p) {
        if (p.left == null) {
            return p.right;
        }
        p.left = removeMin(key, p.left); //把这个锅往下传！
        return p;
    }

    /** return the node with smallest key in the given tree. */
    private Node min(Node p) {
        if (p.left == null) {
            return p;
        }
        return min(p.left);
    }


    /** return a new tree with the given key removed.
     *  assume that the key is in the tree.
     */
    private Node remove2(K key, Node p) {
        int cmp = p.key.compareTo(key);
        if (cmp < 0) {
            p.right = remove2(key, p.right);
        } else if (cmp > 0) {
            p.left = remove2(key, p.left);
        } else {
            if (p.left == null) return p.right;
            if (p.right == null) return p.left;
            Node t = p;
            p = min(t.right);
            p.right = removeMin(key, t.right);
            p.left = t.left;
        }
        return p;
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            size--;
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    /*public static void main(String[] args) {
        BSTMap<Integer,String> b = new BSTMap<>();
        b.put(5,"hi5");
        b.put(3,"hi3");
        b.put(6,"hi6");
        b.put(9,"hi9");
        b.put(8,"hi8");
        b.put(1,"hi1");
        System.out.println(b.remove(6,"hi6"));
        System.out.println(b.remove(5,"hi3"));
        System.out.println(b.remove(5,"hi5"));
    }*/
}
