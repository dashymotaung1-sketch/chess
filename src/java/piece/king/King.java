package piece;

import java.util.ArrayList;
import java.util.List;
import Move.Move;
import main.Board;

public class King extends Piece {
    public King(boolean isWhite, int r, int c) {
        super(isWhite, r, c);
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        // All 8 surrounding squares
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1},  {1, 0},  {1, 1}
        };

        for (int[] d : directions) {
            int targetR = row + d[0];
            int targetC = col + d[1];

            if (board.isValidSquare(targetR, targetC)) {
                Piece targetPiece = board.getPiece(targetR, targetC);

                // Can move to empty square or capture enemy piece
                if (targetPiece == null || targetPiece.isWhite != this.isWhite) {
                    moves.add(new Move(row, col, targetR, targetC));
                }
            }
        }

        // TODO: Add castling moves later
        // if (!hasMoved && !isInCheck(board)) {
        //     // Kingside castling
        //     // Queenside castling
        // }

        return moves;
    }
}