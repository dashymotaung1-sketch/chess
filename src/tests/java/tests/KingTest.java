package tests;

import main.Board;
import Move.Move;
import piece.King;
import piece.Pawn;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    @Test
    void testKingCenterMoves() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        King king = new King(true, 4, 4);
        board.setPiece(4, 4, king);

        List<Move> moves = king.getPossibleMoves(board);

        assertEquals(8, moves.size(),
                "King in center should have 8 possible moves");
    }

    @Test
    void testKingCornerMoves() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        King king = new King(true, 7, 0);
        board.setPiece(7, 0, king);

        List<Move> moves = king.getPossibleMoves(board);

        assertEquals(3, moves.size(),
                "King in corner should have 3 possible moves");
    }

    @Test
    void testKingCaptures() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        King king = new King(true, 4, 4);
        board.setPiece(4, 4, king);

        board.setPiece(3, 3, new Pawn(false, 3, 3));
        board.setPiece(4, 5, new Pawn(false, 4, 5));

        List<Move> moves = king.getPossibleMoves(board);

        assertEquals(8, moves.size(),
                "King should include captures in possible moves");
    }

    @Test
    void testKingBlockedByFriendly() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        King king = new King(true, 4, 4);
        board.setPiece(4, 4, king);

        board.setPiece(3, 3, new King(true, 3, 3));
        board.setPiece(4, 5, new Pawn(true, 4, 5));
        board.setPiece(5, 4, new Pawn(true, 5, 4));

        List<Move> moves = king.getPossibleMoves(board);

        assertEquals(5, moves.size(),
                "King should not move onto friendly pieces");
    }
}