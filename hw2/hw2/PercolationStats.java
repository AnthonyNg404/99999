package hw2;

public class PercolationStats {
    private double total = 0;
    private int count;
    private int[] numberSet;
    private double mean;
    private double stdDev;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        count = T;
        numberSet = new int[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int x = (int) Math.round(Math.random() * N);
                int y = (int) Math.round(Math.random() * N);
                if (p.isOpen(x, y)) {
                    p.open(x, y);
                }
            }
            total += p.numberOfOpenSites();
            numberSet[i] = p.numberOfOpenSites();
        }
        mean = mean();
        stdDev = stddev();
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = total / count;
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sum = 0;
        for (int num : numberSet) {
            sum += Math.pow(num - mean, 2);
        }
        stdDev = Math.sqrt(sum / (count - 1));
        return stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean - 1.96 * stdDev / Math.sqrt(count);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean + 1.96 * stdDev / Math.sqrt(count);
    }
}
