package piece;

import Move.Move;
import main.Board;
import java.util.List;

public abstract class Piece {
    public boolean isWhite;
    public int row, col;
    public boolean hasMoved = false;

    public Piece(boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
    }

    public abstract List<Move> getPossibleMoves(Board board);
    public abstract Piece copy();

    public void updatePosition(int r, int c) {
        this.row = r;
        this.col = c;
    }
}