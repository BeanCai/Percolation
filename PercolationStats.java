import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] x;
    private int expTimes;
    public PercolationStats(int N, int T) {   // perform trials independent experiments on an n-by-n grid
        if (N <= 0 || T <= 0) 
            throw new IllegalArgumentException("Illeagal Argument");
        x = new double[T];
        x[0] = 0.0;
        expTimes = T;
        for (int i = 1; i <= T; ++i) {
            Percolation perc = new Percolation(N);
            boolean[] isEmptySiteLine = new boolean[N+1];
            int numOfLine = 0;
            while (true) {    
                int posX, posY;
                do {
                    posX = StdRandom.uniform(N)+1;
                    posY = StdRandom.uniform(N)+1;
                } while (perc.isOpen(posX, posY));
                perc.open(posX, posY);
                x[i] += 1.00;
                if (!isEmptySiteLine[posX]) {
                    isEmptySiteLine[posX] = true;
                    numOfLine++;
                }
                if (numOfLine == N) {
                    if (perc.percolates())
                        break;
                }
            }
            x[i] = x[i] / (double) (N*N);
        }
    }
    

    public double mean()            {              // sample mean of percolation threshold 
        return StdStats.mean(x);
    }
    public double stddev() {                       // sample standard deviation of percolation threshold
        return StdStats.stddev(x);
    }
        
    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        double mu = mean();
        double sigma = stddev();
        return mu - 1.96*sigma / Math.sqrt(expTimes);
    }

    public double confidenceHi()    {              // high endpoint of 95% confidence interval
         double mu = mean();
        double sigma = stddev();
        return mu + 1.96*sigma / Math.sqrt(expTimes);
    }


    public static void main(String[] args)  {      // test client (described below)
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(N, T);
        System.out.println("mean = " + percStats.mean());
        System.out.println("stddev = " + percStats.stddev());
        System.out.println("95% confidence interval = [" + percStats.confidenceLo() + "," + percStats.confidenceHi() + "]");
        
    }
    
    

       
}