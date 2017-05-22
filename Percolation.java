import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.HashSet;
import java.util.Set;

public class Percolation {
    
    private WeightedQuickUnionUF uf;
    // private int[][] openSites;
    // private int count;
    private Set<Integer> openSites;
    private int N;
    
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF(n*n);
        // openSites = new int[n][n];
        // count = 0;
        openSites = new HashSet<Integer>();
        N = n;
    }
    
    private int sub2ind(int row, int col) {
        return N*(row-1)+col-1;
    }
    
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > N || col > N)
            throw new IndexOutOfBoundsException();
        else {
            if (col > 1 && isOpen(row, col-1))
                uf.union(sub2ind(row, col), sub2ind(row, col-1));
            if (col < N && isOpen(row, col+1))
                uf.union(sub2ind(row, col), sub2ind(row, col+1));
            if (row > 1 && isOpen(row-1, col))
                uf.union(sub2ind(row, col), sub2ind(row-1, col));
            if (row < N && isOpen(row+1, col))
                uf.union(sub2ind(row, col), sub2ind(row+1, col));
            // openSites[row-1][col-1] = 1;
            // count++;
            openSites.add(sub2ind(row, col));
        }
    }
    
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > N || col > N)
            throw new IndexOutOfBoundsException();
        // return openSites[row-1][col-1] == 1;
        return openSites.contains(sub2ind(row, col));
    }
    
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > N || col > N)
            throw new IndexOutOfBoundsException();
        if (isOpen(row, col)) {
            if (row == 1)
                return true;
            else {
                for (int j = 1; j <= N; j++)
                    if (isOpen(1, j) && uf.connected(sub2ind(row, col), sub2ind(1, j)))
                    return true;
                return false;
            }
        } else
            return false;
    }
    
    public int numberOfOpenSites() {
        // return count;
        return openSites.size();
    }
    
    public boolean percolates() {
        for (int j = 1; j <= N; j++)
            if (isFull(N, j))
            return true;
        return false;
    }
}
