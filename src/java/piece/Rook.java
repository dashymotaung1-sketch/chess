package piece;
import java.util.*;
import Move.Move;
import main.Board;

public class Rook extends Piece {
    public Rook(boolean isWhite, int r, int c) { super(isWhite, r, c); }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        for (int[] d : dirs) {
            for (int i = 1; i < 8; i++) {
                int tr = row + d[0]*i, tc = col + d[1]*i;
                if (!board.isValidSquare(tr, tc)) break;
                Piece target = board.getPiece(tr, tc);
                if (target == null) {
                    moves.add(new Move(row, col, tr, tc));
                } else {
                    if (target.isWhite != this.isWhite) moves.add(new Move(row, col, tr, tc));
                    break;
                }
            }
        }
        return moves;
    }

    @Override
    public Piece copy() { return new Rook(isWhite, row, col); }
}