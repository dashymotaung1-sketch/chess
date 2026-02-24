package piece;

import Move.Move;
import main.Board; // Import the main Board class
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(boolean isWhite, int r, int c) {
        super(isWhite, r, c);
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int dir = isWhite ? -1 : 1; // White moves up (-1), Black moves down (+1)

        // 1. Forward Move (1 square)
        if (board.isValidSquare(row + dir, col) && board.getPiece(row + dir, col) == null) {
            moves.add(new Move(row, col, row + dir, col));

            // 2. Double Move (only from starting position)
            int startRow = isWhite ? 6 : 1;
            if (row == startRow && board.getPiece(row + (2 * dir), col) == null) {
                moves.add(new Move(row, col, row + (2 * dir), col));
            }
        }

        // 3. Diagonal Captures
        int[] captureCols = {col - 1, col + 1};
        for (int c : captureCols) {
            if (board.isValidSquare(row + dir, c)) {
                Piece target = board.getPiece(row + dir, c);
                // Check if there is an enemy piece on the diagonal
                if (target != null && target.isWhite != this.isWhite) {
                    moves.add(new Move(row, col, row + dir, c));
                }
            }
        }
        return moves;
    }
    @Override
    public Piece copy() { return new Pawn(isWhite, row, col); }
}