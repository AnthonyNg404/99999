package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF siteOpen;
    private WeightedQuickUnionUF siteFull;
    private int dimension;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be larger or equal to 0");
        }
        siteOpen = new WeightedQuickUnionUF(N * N + 1);
        siteFull = new WeightedQuickUnionUF(N * N);
        dimension = N;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= dimension || col < 0 || col >= dimension) {
            throw new IllegalArgumentException("row or col should be between 0 and N - 1");
        }
        siteOpen.union(row * dimension + col + 1, 0);
        //System.out.println(siteOpen.connected(row * dimension + col + 1, 0));
        //System.out.println(siteOpen.find(row * dimension + col + 1));
        if (col == 0) {
            if (isOpen(row, col + 1)) {
                //System.out.println("connect!" + row + "" + col);
                siteFull.union(row * dimension + col, row * dimension + col + 1);
            }
        } else if (col == dimension - 1) {
            if (isOpen(row, col - 1)) {
                //System.out.println("connect!" + row + "" + col);
                siteFull.union(row * dimension + col, row * dimension + col - 1);
            }
        } else {
            if (isOpen(row, col + 1)) {
                //System.out.println("connect!" + row + "" + col);
                siteFull.union(row * dimension + col, row * dimension + col + 1);
            }
            if (isOpen(row, col - 1)) {
                //System.out.println("connect!" + row + "" + col);
                siteFull.union(row * dimension + col, row * dimension + col - 1);
            }
        }
        if (row == 0) {
            if (isOpen(row + 1, col)) {
                //System.out.println("connect! " + row + " " + col + " down");
                siteFull.union(row * dimension + col, (row + 1) * dimension + col);
            }
        } else if (row == dimension - 1) {
            if (isOpen(row - 1, col)) {
                //System.out.println("connect! " + row + " " + col + " up");
                siteFull.union(row * dimension + col, (row - 1) * dimension + col);
            }
        } else {
            if (isOpen(row + 1, col)) {
                //System.out.println("connect! " + row + " " + col + " down");
                siteFull.union(row * dimension + col, (row + 1) * dimension + col);
            }
            if (isOpen(row - 1, col)) {
                //System.out.println("connect! " + row + " " + col + " up");
                siteFull.union(row * dimension + col, (row - 1) * dimension + col);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= dimension || col < 0 || col >= dimension) {
            throw new IllegalArgumentException("row or col should be between 0 and N - 1");
        }
        //System.out.println(siteOpen.find(row * dimension + col + 1) + "  !");
        return siteOpen.connected(row * dimension + col + 1, 0);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= dimension || col < 0 || col >= dimension) {
            throw new IllegalArgumentException("row or col should be between 0 and N - 1");
        }
        for (int i = 0; i < dimension; i++) {
            if (isOpen(0, i)) {
                //System.out.println(siteFull.connected(12, 3));
                if (siteFull.connected(row * dimension + col, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return dimension * dimension + 1 - siteOpen.count();
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < dimension; i++) {
            if (isOpen(dimension - 1, i)) {
                if (isFull(dimension - 1, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        /**Percolation hw2 = new Percolation(9);
        for (int j = 0; j < 6; j += 1) {
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
        System.out.println(hw2.percolates());*/
    }

}
