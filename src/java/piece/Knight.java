package piece;

import Move.Move;
import main.Board;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(boolean isWhite, int r, int c) {
        super(isWhite, r, c);
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        // The 8 possible "L" shapes a knight can take
        int[][] offsets = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] o : offsets) {
            int targetR = this.row + o[0];
            int targetC = this.col + o[1];

            if (board.isValidSquare(targetR, targetC)) {
                Piece targetPiece = board.getPiece(targetR, targetC);

                // Move is valid if square is empty OR contains an enemy piece
                if (targetPiece == null || targetPiece.isWhite != this.isWhite) {
                    moves.add(new Move(this.row, this.col, targetR, targetC));
                }
            }
        }
        return moves;
    }
}