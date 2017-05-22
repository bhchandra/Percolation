
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.stream.IntStream;

/**
 *
 * @author MITRA
 */
public class Percolation {

    private final int m;
    private final int size;
    private final int[] status;
    private final int top;
    private final int bot;
    private final WeightedQuickUnionUF uf;
    private int numberOfOpenSites;

    //positions of a site  in the 2D grid
    private enum POSITION {
        LeftTopCorner, RightTopCorner, LeftBotCorner, RightBotCorner, Top, Bot, Left, Right, Middle
    };

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        verify(n);
        this.m = n;
        this.size = n * n + 2;
        this.top = size - 1;
        this.bot = size;
        numberOfOpenSites = 0;
        status = new int[size + 1];
        uf = new WeightedQuickUnionUF(size + 1);
        initialize(status, top, bot);

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
        if (status[index] != 1) {
            ++numberOfOpenSites;
            status[index] = 1;
        }

        performUnion(row, col);
    }

    private void performUnion(int row, int col) {
        final POSITION position = findPosition(row, col);
        performUnion(row, col, position);
    }

    private POSITION findPosition(int row, int col) {
        POSITION x = POSITION.Middle;
        int index = calculateIndex(row, col);
        
        if (index == 1) { x = POSITION.LeftTopCorner; } 
        else if (index == m) { x = POSITION.RightTopCorner; } 
        else if (index == size - 2) { x = POSITION.RightBotCorner; } 
        else if (index == size - 1 - m) {x = POSITION.LeftBotCorner; } 
        else if ((index % m) == 1) { x = POSITION.Left; } 
        else if ((index % m) == 0) { x = POSITION.Right; } 
        else if ((index - m) < 0) { x = POSITION.Top; } 
        else if ((index + m) > size - 2) { x = POSITION.Bot; }
        
        return x;
    }

    private void performUnion(int row, int col, POSITION position) {
        final int index = calculateIndex(row, col);
        final int rightNeighbour = index + 1;
        final int belowNeighbour = index + m;
        final int leftNeighbour = index - 1;
        final int aboveNeighbour = index - m;
        switch (position) {
            case LeftTopCorner:
                unionIfOpen(rightNeighbour, index);
                unionIfOpen(belowNeighbour, index);
                unionTOP(index);
                break;
            case RightTopCorner:
                unionIfOpen(leftNeighbour, index);
                unionIfOpen(belowNeighbour, index);
                unionTOP(index);
                break;
            case LeftBotCorner:
                unionIfOpen(rightNeighbour, index);
                unionIfOpen(aboveNeighbour, index);
                unionBOT(index);
                break;
            case RightBotCorner:
                unionIfOpen(leftNeighbour, index);
                unionIfOpen(aboveNeighbour, index);
                unionBOT(index);
                break;
            case Left:
                unionIfOpen(aboveNeighbour, index);
                unionIfOpen(belowNeighbour, index);
                unionIfOpen(rightNeighbour, index);
                break;
            case Right:
                unionIfOpen(aboveNeighbour, index);
                unionIfOpen(leftNeighbour, index);
                unionIfOpen(belowNeighbour, index);
                break;
            case Top:
                unionIfOpen(belowNeighbour, index);
                unionIfOpen(leftNeighbour, index);
                unionIfOpen(rightNeighbour, index);
                unionTOP(index);
                break;
            case Bot:
                unionIfOpen(aboveNeighbour, index);
                unionIfOpen(leftNeighbour, index);
                unionIfOpen(rightNeighbour, index);
                unionBOT(index);
                break;
            case Middle:
                unionIfOpen(aboveNeighbour, index);
                unionIfOpen(belowNeighbour, index);
                unionIfOpen(leftNeighbour, index);
                unionIfOpen(rightNeighbour, index);
                break;
            default:
                break;
        }
    }

    private void unionIfOpen(final int neighbour, final int index) {
        if (status[neighbour] == 1) {
            uf.union(neighbour, index);
        }
    }

    private void unionTOP(int index) {
        uf.union(top, index);
    }

    private void unionBOT(int index) {
        uf.union(bot, index);
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
        return uf.connected(index, top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(bot, top);
    }

    private void validate(int x) {
        if (x < 1 || x > m) {
            throw new IndexOutOfBoundsException("index: " + x + " is not < 1 and > " + m + " ");
        }
    }

    private void verify(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
    }

    private int calculateIndex(int row, int col) {
        return (row * m) - (m - col);
    }

}
