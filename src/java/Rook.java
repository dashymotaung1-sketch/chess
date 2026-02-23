package piece; // Ensure the package declaration is correct

import java.util.ArrayList;
import java.util.List;
import Move.Move;
import main.Board; // Import the standalone Board class from the main package

public class Rook extends Piece {
    public Rook(boolean isWhite, int r, int c) {
        super(isWhite, r, c);
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        // Up, Down, Left, Right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

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