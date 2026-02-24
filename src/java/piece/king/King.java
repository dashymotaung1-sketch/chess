package piece;

import java.util.*;
import Move.Move;
import main.Board;

public class King extends Piece {
    public King(boolean isWhite, int r, int c) { super(isWhite, r, c); }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[] dr = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] dc = {1, 0, -1, 1, -1, 1, 0, -1};

        for (int i = 0; i < 8; i++) {
            int tr = row + dr[i], tc = col + dc[i];
            if (board.isValidSquare(tr, tc)) {
                Piece target = board.getPiece(tr, tc);
                if (target == null || target.isWhite != this.isWhite) {
                    moves.add(new Move(row, col, tr, tc));
                }
            }
        }

        // RECURSION GUARD: Don't check castling if the board is already
        // in the middle of a "Check" calculation.
        if (!this.hasMoved && !board.isSimulating()) {
            checkCastle(board, moves, 7, col + 2); // Kingside
            checkCastle(board, moves, 0, col - 2); // Queenside
        }
        return moves;
    }

    private void checkCastle(Board b, List<Move> moves, int rookCol, int targetCol) {
        Piece p = b.getPiece(row, rookCol);
        if (p instanceof Rook && !p.hasMoved) {
            int dir = (rookCol == 7) ? 1 : -1;
            for (int c = col + dir; c != rookCol; c += dir) {
                if (b.getPiece(row, c) != null) return;
            }
            // Ensure we don't castle out of check
            if (!b.isInCheck(this.isWhite)) moves.add(new Move(row, col, row, targetCol));
        }
    }

    @Override
    public Piece copy() {
        King k = new King(isWhite, row, col);
        k.hasMoved = this.hasMoved;
        return k;
    }
}