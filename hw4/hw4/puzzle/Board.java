package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.Objects;

public class Board implements WorldState {

    /** Returns the string representation of the board.
      * Uncomment this method. */

    private int[] board;
    private int size;

    public Board(int[][] tiles) {
        size = tiles.length;
        board = new int[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[to1D(i, j)] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (j < 0 || j > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return board[to1D(i, j)];
    }

    public int size() {
        return size;
    }

    private int to1D(int i, int j) {
        return i * size + j;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int i = 0;
        int j = 0;
        for (int p = 0; p < size; p++) {
            for (int q = 0; q < size; q++) {
                if (board[to1D(p, q)] == 0) {
                    i = p;
                    j = q;
                    break;
                }
            }
        }
        int[][] a = new int[size][size];
        for (int p = 0; p < size; p++) {
            for (int q = 0; q < size; q++) {
                a[p][q] = board[to1D(p, q)];
            }
        }
        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {
                if (Math.abs(-i + k) + Math.abs(-j + l) - 1 == 0) {
                    a[i][j] = a[k][l];
                    a[k][l] = 0;
                    Board neighbor = new Board(a);
                    neighbors.enqueue(neighbor);
                    a[k][l] = a[i][j];
                    a[i][j] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int ham = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[to1D(i, j)] == 0) {
                    continue;
                }
                if (board[to1D(i, j)] != i * size + j + 1) {
                    ham += 1;
                }
            }
        }
        return ham;
    }

    public int manhattan() {
        int man = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[to1D(i, j)] == 0) {
                    continue;
                }
                if (board[to1D(i, j)] != i * size + j + 1) {
                    int disc = Math.abs((board[to1D(i, j)] - 1) / size - i)
                            + Math.abs((board[to1D(i, j)] - 1) % size - j);
                    man += disc;
                }
            }
        }
        return man;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || !y.getClass().equals(getClass())) {
            return false;
        }
        Board that = (Board) y;
        return size == that.size
                && Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
