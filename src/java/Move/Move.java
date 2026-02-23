package Move;

public class Move {
    public int startR, startC, endR, endC;
    private int row;
    private int col;

    public Move(int startR, int startC, int endR, int endC) {
        this.startR = startR;
        this.startC = startC;
        this.endR = endR;
        this.endC = endC;
    }
    // Inside piece/Piece.java
    public void updatePosition(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
}