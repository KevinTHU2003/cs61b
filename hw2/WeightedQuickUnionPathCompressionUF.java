import java.util.*;

public class WeightedQuickUnionPathCompressionUF {
    /**Record parent node's number. Specially, if it is the root, record the size of the tree (with a negative number). */
    private int[] parent;

    public WeightedQuickUnionPathCompressionUF(int size) {
        parent = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = -1; 
        }
    }

    private int find(int x) {
        validate(x); //for a robust program, you must always consider exception input and validate it!
        int p = parent[x]; //时刻注意parent的二义性！！！在迭代parent[x]的时候同时迭代x
        if (p < 0) {
            return x;
        }
        List<Integer> alongway = new ArrayList<>();
        while (p >= 0) {
            alongway.add(x);
            x = p;
            p = parent[x];
        }
        for (int node : alongway) {
            parent[node] = x;
        }
        return x;
    }

    public void connect(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (parent[px] >= parent[py]) {
            parent[py] += parent[px];
            parent[px] = py;
        } else {
            parent[px] += parent[py];
            parent[py] = px;
        }
    }

    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }

    public static void main(String[] args) {
        WeightedQuickUnionPathCompressionUF uf = new WeightedQuickUnionPathCompressionUF(10);
        System.out.println(uf.isConnected(1, 2)); 
        uf.connect(1, 2);
        uf.connect(3, 4);
        System.out.println(uf.isConnected(1, 2)); 
        uf.connect(0, 2);
        uf.connect(0, 4);
        System.out.println(uf.isConnected(0, 1)); 
    }
}