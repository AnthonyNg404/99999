package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF siteFull;
    private int dimension;
    private int[][] grid;
    private int openCount = 0;
    private boolean per = false;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        siteFull = new WeightedQuickUnionUF(N * N);
        dimension = N;
        grid = new int[N][N];
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

    private boolean checkIndexNew(int row, int col) {
        if (row < 0 || row > dimension - 1) {
            return false;
        }
        return col >= 0 && col <= dimension - 1;
    }

    private void fill(int row, int col) {
        if (!checkIndexNew(row, col)) {
            return;
        }
        if (row == 0) {
            grid[row][col] = 2;
            if (isOpen(row + 1, col)) {
                siteFull.union(row * dimension + col, (row + 1) * dimension + col);
                grid[row + 1][col] = 2;
            }
        } else if (row == dimension - 1) {
            if (isOpen(row - 1, col)) {
                siteFull.union((row - 1) * dimension + col, row * dimension + col);
                if (grid[row][col] == 2) {
                    grid[row - 1][col] = 2;
                } else if (grid[row - 1][col] == 2) {
                    grid[row][col] = 2;
                }
            }
        } else {
            if (isOpen(row + 1, col)) {
                siteFull.union(row * dimension + col, (row + 1) * dimension + col);
                if (grid[row][col] == 2) {
                    grid[row + 1][col] = 2;
                } else if (grid[row + 1][col] == 2) {
                    grid[row][col] = 2;
                }
            }
            if (isOpen(row - 1, col)) {
                siteFull.union((row - 1) * dimension + col, row * dimension + col);
                if (grid[row][col] == 2) {
                    grid[row - 1][col] = 2;
                } else if (grid[row - 1][col] == 2) {
                    grid[row][col] = 2;
                }
            }
        }
        if (col == 0) {
            if (isOpen(row, col + 1)) {
                siteFull.union(row * dimension + col, row * dimension + col + 1);
                if (grid[row][col] == 2) {
                    grid[row][col + 1] = 2;
                } else if (grid[row][col + 1] == 2) {
                    grid[row][col] = 2;
                }
            }
        } else if (col == dimension - 1) {
            if (isOpen(row, col - 1)) {
                siteFull.union(row * dimension + col - 1, row * dimension + col);
                if (grid[row][col] == 2) {
                    grid[row][col - 1] = 2;
                } else if (grid[row][col - 1] == 2) {
                    grid[row][col] = 2;
                }
            }
        } else {
            if (isOpen(row, col + 1)) {
                siteFull.union(row * dimension + col, row * dimension + col + 1);
                if (grid[row][col] == 2) {
                    grid[row][col + 1] = 2;
                } else if (grid[row][col + 1] == 2) {
                    grid[row][col] = 2;
                }
            }
            if (isOpen(row, col - 1)) {
                siteFull.union(row * dimension + col - 1, row * dimension + col);
                if (grid[row][col] == 2) {
                    grid[row][col - 1] = 2;
                } else if (grid[row][col - 1] == 2) {
                    grid[row][col] = 2;
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        //System.out.println(siteOpen.find(row * dimension + col + 1) + "  !");
        return grid[row][col] >= 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        if (dimension == 1) {
            return isOpen(0, 0);
        }
        if (isOpen(row, col)) {
            if (grid[row][col] == 2) {
                if (row == dimension - 1) {
                    per = true;
                }
                fill(row, col);
                if (row - 1 > 0 && isOpen(row - 1, col)) {
                    fill(row - 1, col);
                }
                if (row + 1 <= dimension - 1 && isOpen(row + 1, col)) {
                    fill(row + 1, col);
                }
                if (col - 1 > 0 && isOpen(row, col - 1)) {
                    fill(row, col - 1);
                }
                if (col + 1 <= dimension - 1 && isOpen(row, col + 1)) {
                    fill(row, col + 1);
                }
                return true;
            }
            for (int i = 0; i < dimension; i++) {
                if (isOpen(0, i)) {
                    if (siteFull.connected(row * dimension + col, i)) {
                        if (row == dimension - 1) {
                            per = true;
                        }
                        grid[row][col] = 2;
                        fill(row, col);
                        if (row - 1 > 0 && isOpen(row - 1, col)) {
                            fill(row - 1, col);
                        }
                        if (row + 1 <= dimension - 1 && isOpen(row + 1, col)) {
                            fill(row + 1, col);
                        }
                        if (col - 1 > 0 && isOpen(row, col - 1)) {
                            fill(row, col - 1);
                        }
                        if (col + 1 <= dimension - 1 && isOpen(row, col + 1)) {
                            fill(row, col + 1);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

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
        return per;
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
