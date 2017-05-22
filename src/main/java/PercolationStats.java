
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *
 * @author MITRA
 */
public class PercolationStats {

    private final int n;
    private final int trials;
    private final double percolationResults[];

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        percolationResults = new double[trials];
        performTrials(trials);
    }

    private void performTrials(int trials) {
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            double percolation = (p.numberOfOpenSites() * 1.0) / (n * n);
            percolationResults[i] = percolation;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationResults);

    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidence();
    }

    private double confidence() {
        double s = stddev();
        double sqrtTrials = Math.sqrt(trials);
        return 1.96 * s / sqrtTrials;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please enter arguments to run the program.");
            System.err.println("Exiting.");
        } else if (args.length > 0) {
            int n = 0;
            int trials = 0;
            try {
                n = Integer.parseInt(args[0]);
                trials = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Arguments must be valid integers.");
            }
            
            final PercolationStats ps = new PercolationStats(n, trials);

            StdOut.printf("mean                    = %s\n", ps.mean());
            StdOut.printf("stddev                  = %s\n", ps.stddev());
            StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        }

    }
}
