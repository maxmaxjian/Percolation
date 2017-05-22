import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] pthresh;
    
    public PercolationStats(int n, int trials) {
        if (trials <= 0 || n <= 0)
            throw new IllegalArgumentException();
        pthresh = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n)+1, col = StdRandom.uniform(n)+1;
                while (p.isOpen(row, col)) {
                    row = StdRandom.uniform(n)+1;
                    col = StdRandom.uniform(n)+1;
                }
                p.open(row, col);
            }
            pthresh[i] = p.numberOfOpenSites()/(double) (n*n);
        }
    }
    
    public double mean() {
        return StdStats.mean(pthresh);
    }
    
    public double stddev() {
        return StdStats.stddev(pthresh);
    }
    
    public double confidenceLo() {
        return mean()-1.96*stddev()/Math.sqrt(pthresh.length);
    }
    
    public double confidenceHi() {
        return mean()+1.96*stddev()/Math.sqrt(pthresh.length);
    }
    
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        double m = stats.mean();
        double s = stats.stddev();
        double loconf = stats.confidenceLo();
        double hiconf = stats.confidenceHi();
        StdOut.println("mean\t\t\t= "+m);
        StdOut.println("stddev\t\t\t= "+s);
        StdOut.println("95% confidence interval\t= "+"["+loconf+", "+hiconf+"]");
    }
}
