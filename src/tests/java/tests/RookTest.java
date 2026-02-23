package tests;

import main.Board;
import Move.Move;
import piece.Rook;
import piece.Pawn;
import tests.TestUtils;
import java.util.List;

public class RookTest {

    public static void main(String[] args) {
        System.out.println("=== Testing Rook Movements ===");
        testRookEmptyBoard();
        testRookBlockedByFriendly();
        testRookCaptures();
        testRookEdgeCases();
    }

    private static void testRookEmptyBoard() {
        System.out.println("\n--- Rook on Empty Board ---");
        Board board = new Board();
        TestUtils.clearBoard(board);

        // Place rook at e4 (row 4, col 4)
        Rook rook = new Rook(true, 4, 4);
        board.setPiece(4, 4, rook);

        List<Move> moves = rook.getPossibleMoves(board);
        System.out.println("Rook at e4 on empty board - Total moves: " + moves.size());

        // Rook in center should have 14 moves (7 in each of 4 directions)
        assert moves.size() == 14 : "Expected 14 moves, got " + moves.size();
        System.out.println("✓ Rook empty board test passed");
    }

    private static void testRookBlockedByFriendly() {
        System.out.println("\n--- Rook Blocked by Friendly Pieces ---");
        Board board = new Board();
        TestUtils.clearBoard(board);

        // Place rook at e4
        Rook rook = new Rook(true, 4, 4);
        board.setPiece(4, 4, rook);

        // Place friendly pieces blocking
        board.setPiece(2, 4, new Pawn(true, 2, 4)); // Up
        board.setPiece(6, 4, new Pawn(true, 6, 4)); // Down
        board.setPiece(4, 2, new Pawn(true, 4, 2)); // Left
        board.setPiece(4, 6, new Pawn(true, 4, 6)); // Right

        List<Move> moves = rook.getPossibleMoves(board);
        System.out.println("Rook blocked by friendly pieces:");
        for (Move m : moves) {
            System.out.println("  -> " + m.endR + "," + m.endC);
        }

        // Should have 4 moves (one in each direction before block)
        assert moves.size() == 4 : "Expected 4 moves, got " + moves.size();
        System.out.println("✓ Rook blocked test passed");
    }

    private static void testRookCaptures() {
        System.out.println("\n--- Rook Captures ---");
        Board board = new Board();
        TestUtils.clearBoard(board);

        // Place rook at e4
        Rook rook = new Rook(true, 4, 4);
        board.setPiece(4, 4, rook);

        // Place enemy pieces
        board.setPiece(2, 4, new Pawn(false, 2, 4)); // Up
        board.setPiece(4, 6, new Pawn(false, 4, 6)); // Right

        List<Move> moves = rook.getPossibleMoves(board);
        System.out.println("Rook with capture opportunities - Total moves: " + moves.size());

        // Should be able to move up to and including captures
        boolean hasCapture = false;
        for (Move m : moves) {
            if (m.endR == 2 && m.endC == 4) {
                hasCapture = true;
                System.out.println("Found capture at (2,4)");
            }
        }
        assert hasCapture : "Rook should be able to capture at (2,4)";
        assert moves.size() == 8 : "Expected 8 moves, got " + moves.size();
        System.out.println("✓ Rook capture test passed");
    }

    private static void testRookEdgeCases() {
        System.out.println("\n--- Rook at Corner ---");
        Board board = new Board();
        TestUtils.clearBoard(board);

        // Place rook at a1 (row 7, col 0)
        Rook rook = new Rook(true, 7, 0);
        board.setPiece(7, 0, rook);

        List<Move> moves = rook.getPossibleMoves(board);
        System.out.println("Rook at a1 (corner) - Total moves: " + moves.size());

        // Rook in corner should have 14 moves (7 right + 7 up)
        assert moves.size() == 14 : "Expected 14 moves, got " + moves.size();
        System.out.println("✓ Rook corner test passed");
    }
}