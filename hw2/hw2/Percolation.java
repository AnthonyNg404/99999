package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF siteFull;
    private int dimension;
    private int[][] grid;
    private int openCount = 0;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        siteFull = new WeightedQuickUnionUF(N * N + 2);
        dimension = N;
        grid = new int[N][N];
        setup();
    }

    private void setup() {
        for (int i = 0; i < dimension; i++) {
            siteFull.union(toIndex(0, i), dimension * dimension);
            siteFull.union(toIndex(dimension - 1, i), dimension * dimension + 1);
        }
    }

    private int toIndex(int row, int col) {
        return row * dimension + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndex(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            openCount += 1;
            //System.out.println(siteOpen.connected(row * dimension + col + 1, 0));
            //System.out.println(siteOpen.find(row * dimension + col + 1));
            if (dimension == 1) {
                return;
            }
            fill(row, col);
        }
    }

    private void fill(int row, int col) {
        if (!validIndex(row, col)) {
            return;
        }
        if (row == 0) {
            if (isOpen(row + 1, col)) {
                siteFull.union(toIndex(row, col), toIndex(row + 1, col));
            }
        } else if (row == dimension - 1) {
            if (isOpen(row - 1, col)) {
                siteFull.union(toIndex(row, col), toIndex(row - 1, col));
            }
        } else {
            if (isOpen(row + 1, col)) {
                siteFull.union(toIndex(row, col), toIndex(row + 1, col));
            }
            if (isOpen(row - 1, col)) {
                siteFull.union(toIndex(row, col), toIndex(row - 1, col));
            }
        }
        if (col == 0) {
            if (isOpen(row, col + 1)) {
                siteFull.union(toIndex(row, col), toIndex(row, col + 1));
            }
        } else if (col == dimension - 1) {
            if (isOpen(row, col - 1)) {
                siteFull.union(toIndex(row, col), toIndex(row, col - 1));
            }
        } else {
            if (isOpen(row, col + 1)) {
                siteFull.union(toIndex(row, col), toIndex(row, col + 1));
            }
            if (isOpen(row, col - 1)) {
                siteFull.union(toIndex(row, col), toIndex(row, col - 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        //System.out.println(siteOpen.find(row * dimension + col + 1) + "  !");
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        if (dimension == 1) {
            return isOpen(0, 0);
        }
        if (isOpen(row, col)) {
            return siteFull.connected(toIndex(row, col), dimension * dimension);
        }
        return false;
    }

    /**private boolean checkAround(int row, int col) {
        if (checkIndexNew(row + 1, col)) {
            if (isOpen(row + 1, col)) {
                return true;
            }
        }
        if (checkIndexNew(row - 1, col)) {
            if (!isOpen(row - 1, col)) {
                return true;
            }
        }
        if (checkIndexNew(row, col + 1)) {
            if (isOpen(row, col + 1)) {
                return true;
            }
        }
        if (checkIndexNew(row, col - 1)) {
            if (isOpen(row, col - 1)) {
                return true;
            }
        }
        return false;
    }*/

    /**private boolean goToTop(int row, int col) {
        if (row == 0) {
            return true;
        }
        if (col > 0 && col < dimension - 1) {
            if (isOpen(row - 1, col)) {
                return false || goToTop(row - 1, col);
            }
            if (isOpen(row, col + 1)) {
                return false || goToTop(row, col + 1);
            }
            if (isOpen(row, col - 1)) {
                return false || goToTop(row, col - 1);
            }
        }
        return false;
    }*/

    // number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (dimension == 1) {
            return isOpen(0, 0);
        }
        return siteFull.connected(dimension * dimension, dimension * dimension + 1);
        /**for (int i = 0; i < dimension; i++) {
            if (isOpen(dimension - 1, i)) {
                if (isOpen(dimension - 2, i)) {
                    return isFull(dimension - 1, i);
                }
            }
        }
        return false;*/
    }

    private boolean validIndex(int row, int col) {
        if (row < 0 || row > dimension - 1) {
            return false;
        }
        return col >= 0 && col <= dimension - 1;
    }

    private void checkIndex(int row, int col) {
        if (!validIndex(row, col)) {
            throw new IndexOutOfBoundsException();
        }
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        /**Percolation hw2 = new Percolation(9);
        hw2.open(-1, 5);
        /**for (int j = 0; j < 6; j += 1) {
            hw2.open(j, 3);
        }
        for (int i = 5; i < 7; i++) {
            hw2.open(i, 4);
        }
        for (int k = 6; k < 8; k++) {
            hw2.open(k, 5);
        }
        for (int k = 6; k < 9; k++) {
            hw2.open(7, k);
        }
        for (int k = 0; k < 9; k++) {
            hw2.open(k, 8);
        }
        hw2.open(0, 3);
        System.out.println(hw2.isOpen(0, 3));
        hw2.open(1, 3);
        System.out.println(hw2.isOpen(1, 3));
        System.out.println(hw2.isFull(1, 3));
        System.out.println(hw2.numberOfOpenSites());
        System.out.println(hw2.siteFull.find(80));
        /**for (int j = 0; j < 3; j += 1) {
            hw2.open(j, 3);
        }
        System.out.println(hw2.percolates());
        System.out.println(hw2.siteFull.find(3));
        System.out.println(hw2.siteFull.find(12));
        System.out.println(hw2.siteFull.find(21));*/
    }

}
