/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int TOP = 0;
    private int bottom;
    private int size;
    private int numOpenSites;
    private boolean[][] opened;
    private WeightedQuickUnionUF ufWithVirtualSite; // Union find structure to represent the grid model
    private WeightedQuickUnionUF ufTopVirtualSite; // Don't connect to bottom virtual site

    public Percolation(int n) {
        // n by n is size of the grid
        // If n to make grid is less than 0
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        bottom = n * n + 1; // Virtual bottom is n * n + 1 for last element of quick union structure (array)
        opened = new boolean[n][n]; // Create grid of N by N, element in array of boolean is false by default

        ufWithVirtualSite = new WeightedQuickUnionUF(n * n + 2); // Size of array needed to store all element in matrix
        ufTopVirtualSite = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        // Check if row and col is out of prescribed range
        if (outOfMatrixRange(row, col)) {
            throw new IllegalArgumentException();
        }

        // Open a site in matrix
        opened[row - 1][col - 1] = true; // -1 since the row and col given start from 1. But array in java start from 0
        numOpenSites++;

        if (row - 1 == 0) // If the site is in top row
        {
            ufWithVirtualSite.union(xyToArrayIndex(0, col), TOP);
            ufTopVirtualSite.union(xyToArrayIndex(0, col), TOP);
        }
        else if (row == this.size) // If the site in bottom row
        {
            ufWithVirtualSite.union(xyToArrayIndex(row - 1, col), this.bottom);
        }

        // Check if row in left right ... is open and make sure it is not out of range. then union it
        if (!outOfMatrixRange(row + 1, col) && isOpen(row + 1, col)) // Down
        {
            // Union or Connect the site
            ufWithVirtualSite.union(xyToArrayIndex(row - 1, col), xyToArrayIndex(row, col));
            ufTopVirtualSite.union(xyToArrayIndex(row - 1, col), xyToArrayIndex(row, col));
        }
        if (!outOfMatrixRange(row - 1, col) && isOpen(row - 1, col)) // Up
        {
            ufWithVirtualSite.union(xyToArrayIndex(row - 1, col), xyToArrayIndex(row - 2, col));
            ufTopVirtualSite.union(xyToArrayIndex(row - 1, col), xyToArrayIndex(row - 2, col));
        }
        if (!outOfMatrixRange(row, col + 1) && isOpen(row, col + 1)) // Right
        {
            ufWithVirtualSite.union(xyToArrayIndex(row - 1, col), xyToArrayIndex(row - 1, col + 1));
            ufTopVirtualSite.union(xyToArrayIndex(row - 1, col), xyToArrayIndex(row - 1, col + 1));
        }
        if (!outOfMatrixRange(row, col - 1) && isOpen(row, col - 1)) // Left
        {
            ufWithVirtualSite.union(xyToArrayIndex(row - 1, col), xyToArrayIndex(row - 1, col - 1));
            ufTopVirtualSite.union(xyToArrayIndex(row - 1, col), xyToArrayIndex(row - 1, col - 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (isOpen(row, col)) {
            // Get index of site in array
            int index = xyToArrayIndex(row - 1, col);
            // Check if site connect to virtual top site
            return ufTopVirtualSite.connected(index, TOP);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufWithVirtualSite.connected(TOP, this.bottom);
    }

    // Convert row and col of matrix to array index
    private int xyToArrayIndex(int row, int col) {
        // index = row * line length + col, 0 * 3 + 3 = index 3
        int index = row * this.size + col;
        return index;
    }

    // Check if the row and col is out of range
    private boolean outOfMatrixRange(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
