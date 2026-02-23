package piece;

import Move.Move;
import main.Board;

import java.util.List;

public abstract class Piece {
    // Change these to protected
    public boolean isWhite;
    public int row;
    public int col;

    public Piece(boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
    }

    public abstract List<Move> getPossibleMoves(Board board);

    public void updatePosition(int endR, int endC) {
    }
}