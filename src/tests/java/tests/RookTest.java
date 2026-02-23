package tests;

import main.Board;
import Move.Move;
import piece.Rook;
import piece.Pawn;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    @Test
    void testRookEmptyBoard() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Rook rook = new Rook(true, 4, 4);
        board.setPiece(4, 4, rook);

        List<Move> moves = rook.getPossibleMoves(board);

        // Check key rook directions instead of move count
        assertTrue(moves.stream().anyMatch(m -> m.endR == 0 && m.endC == 4));
        assertTrue(moves.stream().anyMatch(m -> m.endR == 7 && m.endC == 4));
        assertTrue(moves.stream().anyMatch(m -> m.endR == 4 && m.endC == 0));
        assertTrue(moves.stream().anyMatch(m -> m.endR == 4 && m.endC == 7));
    }

    @Test
    void testRookBlockedByFriendly() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Rook rook = new Rook(true, 4, 4);
        board.setPiece(4, 4, rook);

        board.setPiece(2, 4, new Pawn(true, 2, 4));
        board.setPiece(6, 4, new Pawn(true, 6, 4));
        board.setPiece(4, 2, new Pawn(true, 4, 2));
        board.setPiece(4, 6, new Pawn(true, 4, 6));

        List<Move> moves = rook.getPossibleMoves(board);

        // Rook should not move onto friendly pieces
        assertFalse(moves.stream().anyMatch(m -> m.endR == 2 && m.endC == 4));
        assertFalse(moves.stream().anyMatch(m -> m.endR == 6 && m.endC == 4));
        assertFalse(moves.stream().anyMatch(m -> m.endR == 4 && m.endC == 2));
        assertFalse(moves.stream().anyMatch(m -> m.endR == 4 && m.endC == 6));
    }

    @Test
    void testRookCaptures() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Rook rook = new Rook(true, 4, 4);
        board.setPiece(4, 4, rook);

        Pawn enemy1 = new Pawn(false, 2, 4);
        Pawn enemy2 = new Pawn(false, 4, 6);

        board.setPiece(2, 4, enemy1);
        board.setPiece(4, 6, enemy2);

        List<Move> moves = rook.getPossibleMoves(board);

        assertTrue(moves.stream().anyMatch(m -> m.endR == 2 && m.endC == 4),
                "Rook should be able to capture piece at (2,4)");

        assertTrue(moves.stream().anyMatch(m -> m.endR == 4 && m.endC == 6),
                "Rook should be able to capture piece at (4,6)");
    }

    @Test
    void testRookCornerPosition() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Rook rook = new Rook(true, 7, 0);
        board.setPiece(7, 0, rook);

        List<Move> moves = rook.getPossibleMoves(board);

        assertTrue(moves.stream().anyMatch(m -> m.endR == 7 && m.endC == 7));
        assertTrue(moves.stream().anyMatch(m -> m.endR == 0 && m.endC == 0));
    }
}