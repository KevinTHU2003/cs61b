package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private int N;
    private int[] open;
    private WeightedQuickUnionUF uf;
    //List<Integer> openLastRow;
    private int numberOfOpenSites; //标记open sites的个数
    private boolean percolates; //标记是否percolates了

    /** Create N-by-N grid, with all sites initially blocked。 */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        open = new int[N * N]; //一定要先new一个大小，否则里面还都是null，没法赋值！
        for (int i = 0; i < N * N; i++) {
            open[i] = 0;
        }
        uf = new WeightedQuickUnionUF(N * N + 2); //与N * N连接就代表 isFull, 与N * N + 1连接就代表percolate了
        numberOfOpenSites = 0;
        percolates = false;
    }

    private boolean isNeighbor(int row, int col, int i) {
        return !((i == 0 && col == 0) || (i == 1 && col == N - 1) || (i == 2 && row == 0) || (i == 3 && row == N - 1));
    }

    /** Open the site (row, col) if it is not open already。*/
    public void open(int row, int col) {
        validate(row);
        validate(col);
        int cur = row * N + col;
        int[] neighbour = new int[] {cur - 1, cur + 1, cur - N, cur + N}; //left, right, up, down
        if (!isOpen(row, col)) {
            open[cur] = 1;
            numberOfOpenSites += 1;
        }
        for (int i = 0; i < 4; i++) {
            if (isNeighbor(row, col, i) && open[neighbour[i]] == 1) { //小心！不要犯傻！明确open数组下标应该填什么！
                uf.union(cur, neighbour[i]);
            }
        }
        if (row == 0) {
            uf.union(cur, N * N); //full
        }
        if (row == N - 1) {
            uf.union(cur, N * N + 1);
        }
        if (uf.connected(cur, N * N + 1)) {
            percolates = true;
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return open[row * N + col] == 1;
    }

    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        return uf.connected(row * N + col, N * N);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    public boolean percolates() {
        return percolates;
    }

    private void validate(int x) {
        if (x < 0 || x >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {

    }
}
