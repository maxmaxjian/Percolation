import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF uf;
    private int[][] openSites;
    private int count;
    
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF(n*n);
        openSites = new int[n][n];
        count = 0;
    }
    
    private int sub2ind(int row, int col) {
        return openSites.length*(row-1)+col-1;
    }
    
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > openSites.length || col > openSites.length)
            throw new IndexOutOfBoundsException();
        else {
            if (col > 1 && isOpen(row, col-1))
                uf.union(sub2ind(row, col), sub2ind(row, col-1));
            if (col < openSites.length && isOpen(row, col+1))
                uf.union(sub2ind(row, col), sub2ind(row, col+1));
            if (row > 1 && isOpen(row-1, col))
                uf.union(sub2ind(row, col), sub2ind(row-1, col));
            if (row < openSites.length && isOpen(row+1, col))
                uf.union(sub2ind(row, col), sub2ind(row+1, col));
            openSites[row-1][col-1] = 1;
            count++;
        }
    }
    
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > openSites.length || col > openSites.length)
            throw new IndexOutOfBoundsException();
        return openSites[row-1][col-1] == 1;
    }
    
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > openSites.length || col > openSites.length)
            throw new IndexOutOfBoundsException();
        if (isOpen(row, col)) {
            if (row == 1)
                return true;
            else {
                for (int j = 1; j <= openSites[0].length; j++)
                    if (isOpen(1, j) && uf.connected(sub2ind(row, col), sub2ind(1, j)))
                    return true;
                return false;
            }
        } else
            return false;
    }
    
    public int numberOfOpenSites() {
        return count;
    }
    
    public boolean percolates() {
        for (int j = 1; j <= openSites.length; j++)
            if (isFull(openSites.length, j))
            return true;
        return false;
    }
}
