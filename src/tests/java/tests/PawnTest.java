package tests;

import main.Board;
import Move.Move;
import piece.Pawn;
import org.junit.jupiter.api.Test;
import tests.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {

    @Test
    void testWhitePawnInitialMoves() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Pawn pawn = new Pawn(true, 6, 4);
        board.setPiece(6, 4, pawn);

        List<Move> moves = pawn.getPossibleMoves(board);

        assertEquals(2, moves.size(),
                "White pawn initial move should allow 2 moves");
    }

    @Test
    void testBlackPawnInitialMoves() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Pawn pawn = new Pawn(false, 1, 4);
        board.setPiece(1, 4, pawn);

        List<Move> moves = pawn.getPossibleMoves(board);

        assertEquals(2, moves.size(),
                "Black pawn initial move should allow 2 moves");
    }

    @Test
    void testPawnCaptures() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Pawn whitePawn = new Pawn(true, 4, 4);
        board.setPiece(4, 4, whitePawn);

        board.setPiece(3, 3, new Pawn(false, 3, 3));
        board.setPiece(3, 5, new Pawn(false, 3, 5));

        List<Move> moves = whitePawn.getPossibleMoves(board);

        assertEquals(3, moves.size(),
                "Pawn should allow forward move plus captures");
    }

    @Test
    void testPawnBlocked() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Pawn pawn = new Pawn(true, 6, 4);
        board.setPiece(6, 4, pawn);

        board.setPiece(5, 4, new Pawn(false, 5, 4));

        List<Move> moves = pawn.getPossibleMoves(board);

        assertEquals(0, moves.size(),
                "Pawn blocked by piece in front should have no moves");
    }

    @Test
    void testPawnEdgeCases() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Pawn pawn = new Pawn(true, 6, 0);
        board.setPiece(6, 0, pawn);

        List<Move> moves = pawn.getPossibleMoves(board);

        assertEquals(2, moves.size(),
                "Pawn on edge should only have forward moves");
    }
}