package algorithm;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.stream.IntStream;

/**
 *
 * @author MITRA
 */
public class Percolation {

    private final int size;
    private int[] status;
    private final int TOP;
    private final int BOT;
    private final WeightedQuickUnionUF uf;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        verify(n);
        this.size = n * n + 2;
        this.TOP = size - 2;
        this.BOT = size - 1;
        status = new int[size + 1];
        uf = new WeightedQuickUnionUF(size);
        initialize(status, TOP, BOT);

    }

    private void initialize(int[] status, int TOP, int BOT) {
        IntStream.rangeClosed(1, size).forEach(i -> status[i] = 0);
        status[TOP] = 1;
        status[BOT] = 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row);
        validate(col);
        int index = calculateIndex(row, col);
        status[index] = 1;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        int index = calculateIndex(row, col);
        return status[index] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        int index = calculateIndex(row, col);

        return true;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return 1;
    }

    // does the system percolate?
    public boolean percolates() {
        return true;
    }

    private void validate(int index) {
        if (index < 1 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void verify(int n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
    }

    private int calculateIndex(int row, int col) {
        return (row * size) - (size - col);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(100);
        boolean isOpen = p.isOpen(1, 1);
        System.out.println("isOpen: " + isOpen);
        System.out.println(p.percolates());
    }

}
