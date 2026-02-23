package piece;

import java.util.ArrayList;
import java.util.List;
import Move.Move;
import main.Board;

public class Bishop extends Piece {
    public Bishop(boolean isWhite, int r, int c) {
        super(isWhite, r, c);
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        // Diagonal directions: up-left, up-right, down-left, down-right
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] d : directions) {
            for (int i = 1; i < 8; i++) {
                int targetR = row + (d[0] * i);
                int targetC = col + (d[1] * i);

                if (board.isValidSquare(targetR, targetC)) {
                    Piece targetPiece = board.getPiece(targetR, targetC);

                    if (targetPiece == null) {
                        // Square is empty, keep sliding
                        moves.add(new Move(row, col, targetR, targetC));
                    } else {
                        // Blocked by a piece
                        if (targetPiece.isWhite != this.isWhite) {
                            // It's an enemy! We can capture, then stop.
                            moves.add(new Move(row, col, targetR, targetC));
                        }
                        break; // Stop sliding because we hit a piece
                    }
                } else {
                    break; // Stop sliding because we hit the edge of the board
                }
            }
        }
        return moves;
    }
}