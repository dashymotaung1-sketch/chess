package players;

import Move.Move;
import main.Board;
import piece.Piece;
import java.util.*;

public class ComputerPlayer {
    private boolean isWhite;
    private String level;

    public ComputerPlayer(boolean isWhite, String level) {
        this.isWhite = isWhite;
        this.level = level;
    }

    public Move getDecision(Board board) {
        if (level.equals("Easy")) return getRandomMove(board);
        int depth = level.equals("Hard") ? 3 : 2; // Depth 3 is safer for performance
        return getBestMove(board, depth);
    }

    private Move getBestMove(Board board, int depth) {
        List<Move> moves = getAllLegalMoves(board, isWhite);
        Move bestMove = null;
        int bestVal = Integer.MIN_VALUE;

        for (Move m : moves) {
            Board temp = board.copyBoard();
            temp.makeMove(m);
            int val = minimax(temp, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if (val > bestVal) { bestVal = val; bestMove = m; }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, int alpha, int beta, boolean maxing) {
        if (depth == 0) return evaluate(board);
        List<Move> moves = getAllLegalMoves(board, maxing ? isWhite : !isWhite);
        if (moves.isEmpty()) return 0;

        if (maxing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move m : moves) {
                Board temp = board.copyBoard();
                temp.makeMove(m);
                maxEval = Math.max(maxEval, minimax(temp, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, maxEval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move m : moves) {
                Board temp = board.copyBoard();
                temp.makeMove(m);
                minEval = Math.min(minEval, minimax(temp, depth - 1, alpha, beta, true));
                beta = Math.min(beta, minEval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    private int evaluate(Board b) {
        int score = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = b.getPiece(r, c);
                if (p != null) {
                    int v = switch(p.getClass().getSimpleName()) {
                        case "Pawn" -> 100; case "Knight", "Bishop" -> 300;
                        case "Rook" -> 500; case "Queen" -> 900; case "King" -> 10000;
                        default -> 0;
                    };
                    score += (p.isWhite == isWhite) ? v : -v;
                }
            }
        }
        return score;
    }

    private List<Move> getAllLegalMoves(Board b, boolean white) {
        List<Move> moves = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = b.getPiece(r, c);
                if (p != null && p.isWhite == white) {
                    for (Move m : p.getPossibleMoves(b)) {
                        if (b.isValidMove(m)) moves.add(m);
                    }
                }
            }
        }
        return moves;
    }

    private Move getRandomMove(Board b) {
        List<Move> m = getAllLegalMoves(b, isWhite);
        return m.isEmpty() ? null : m.get(new Random().nextInt(m.size()));
    }
}