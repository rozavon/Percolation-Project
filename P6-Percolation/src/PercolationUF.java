public class PercolationUF implements IPercolate {

    private IUnionFind myFinder;
    private boolean[][] myGrid;
    private final int VTOP;
    private final int VBOTTOM;
    private int myOpenCount;

    public PercolationUF(IUnionFind finder, int size){
        finder.initialize((size*size) + 2);
        myFinder = finder;
        myOpenCount = 0;
        myGrid = new boolean[size][size];
        VTOP = size*size;
        VBOTTOM = size*size+1;
    }

    private boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }

    public boolean isOpen(int row, int col) {
        if(!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myGrid[row][col];
    }


    public boolean isFull(int row, int col) {

        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        int site = (row * myGrid.length) + col;
        return myFinder.connected(site, VTOP);
    }
    public boolean percolates() {
        if (myFinder.connected(VTOP, VBOTTOM)) {
            return true;
        }
        return false;
    }
    public int numberOfOpenSites() {
        return myOpenCount;
    }

    public void open(int row, int col) {

        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        if (myGrid[row][col]) {
            return;
        }
        int site = (row * myGrid.length) + col;
        myGrid[row][col] = true;
        myOpenCount += 1;

        if (row == 0) {
            myFinder.union(site, VTOP);
        }
        if (row == myGrid.length-1) {
            myFinder.union(site, VBOTTOM);
        }

        if (inBounds(row, col + 1) && isOpen(row, col + 1)) {
            myFinder.union(site, (row * myGrid.length) + (col+1));
        }
        if (inBounds(row, col - 1) && isOpen(row, col - 1)) {
            myFinder.union(site, (row * myGrid.length) + (col-1));
        }
        if (inBounds(row + 1, col) && isOpen(row + 1, col)) {
            myFinder.union(site, ((row+1) * myGrid.length) + col);
        }
        if (inBounds(row - 1, col) && isOpen(row - 1, col)) {
            myFinder.union(site, ((row-1) * myGrid.length) + col);
        }
    }

}
