package algorithm;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.stream.IntStream;

/**
 *
 * @author MITRA
 */
public class Percolation {
    
    private final int n;
    private final int size;
    private final int[] status;
    private final int TOP;
    private final int BOT;
    private final WeightedQuickUnionUF uf;
    //positions of a site  in the 2D grid
    private enum POSITION {LeftTopCorner, RightTopCorner, LeftBotCorner, RightBotCorner, Top, Bot, Left, Right, Middle};

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        verify(n);
        this.n = n;
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
        performUnion(row, col);
    }

    private void performUnion(int row, int col) {
        
        POSITION position = findPosition(row, col);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private POSITION findPosition(int row, int col) {
        POSITION x = POSITION.Middle;
        int index = calculateIndex(row, col);
        if(index == 1){ x = POSITION.LeftTopCorner;}
        else if( index == n) { x = POSITION.RightTopCorner;}
        else if( index == size - 2) { x = POSITION.RightBotCorner;}
        else if( index == size - 1 - n) {x = POSITION.LeftBotCorner;}
        else if((index % n) == 1 ) { x = POSITION.Left;}
        else if((index % n) == 0 ) { x = POSITION.Right;}
        else if((index - n ) < 0) { x = POSITION.Top;}
        else if((index + n) > size -2){x = POSITION.Bot;}
        return x;
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

    private void validate(int x) {
        if (x < 1 || x > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void verify(int n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
    }

    private int calculateIndex(int row, int col) {
        return (row * n) - (n - col);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        boolean isOpen = p.isOpen(1, 1);
//        System.out.println("isOpen: " + isOpen);
        int row = 1;
        int col = 3;
        System.out.println("Size: " + p.size + " n: " + p.n);
        System.out.println("Index: " + p.calculateIndex(row, col));
        System.out.println("Position: " + p.findPosition(row, col));
    }

}
