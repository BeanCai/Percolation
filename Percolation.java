import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean[] matrix;
    private int row, col;
    private WeightedQuickUnionUF wquUF;
    private WeightedQuickUnionUF wquUFTop;
    private boolean alreadyPercolates;
    public Percolation(int n)  {              // create n-by-n grid, with all sites blocked
        if (n < 1) throw new IllegalArgumentException("Illeagal Argument");
        wquUF = new WeightedQuickUnionUF(n*n+2);
        wquUFTop = new WeightedQuickUnionUF(n*n+1);
        alreadyPercolates = false;
        row = n;
        col = n;
        matrix = new boolean[n*n+1];
    }
    private void validate(int i, int j) {
        if (i < 1 || i > row) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j < 1 || j > col) 
            throw new IndexOutOfBoundsException("col index j out of bounds");        
    }

    public void open(int i, int j) {   // open site (row, col) if it is not open already
        validate(i, j);
        int curIdx = (i-1)*col + j; 
        matrix[curIdx] = true;
        if (i == 1) {
            wquUF.union(curIdx, 0);
            wquUFTop.union(curIdx, 0);
        }
        if (i == row) {
            wquUF.union(curIdx, row*col+1);
        }
        
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        for (int dir = 0; dir < 4; dir++) {
            int posX = i + dx[dir];
            int posY = j + dy[dir];
            if (posX <= row && posX >= 1 
                    && posY <= row && posY >= 1 
                    && isOpen(posX, posY)) {
                wquUF.union(curIdx, (posX-1)*col+posY);
                wquUFTop.union(curIdx, (posX-1)*col+posY);
            }
        }
    }

    public boolean isOpen(int i, int j) { // is site (row, col) open?
        validate(i, j);
        return matrix[(i-1)*col + j];
    }

    public boolean isFull(int i, int j)  {
        validate(i, j);
        int curIdx = (i-1)*col+j;
        if (wquUFTop.find(curIdx) == wquUFTop.find(0)) return true;
        return false;
    }
    
    public     int numberOfOpenSites()   {    // number of open sites
        int count = 0;
        for (boolean a:matrix) {
             if (a)
             count++;
  }
        return count;
    }        
    public boolean percolates()              {
        if (alreadyPercolates) return true;
        if (wquUF.find(0) == wquUF.find(row*col+1)) {
            alreadyPercolates = true;
            return true;
        } 
        return false;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(2);
        perc.open(1, 1);
        perc.open(1, 2);
        perc.open(2, 1);
        System.out.println(perc.percolates());
    }

}