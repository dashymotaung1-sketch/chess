package tests;

import main.Board;
import Move.Move;
import piece.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameIntegrationTest {

    @Test
    void testSimpleGame() {
        Board board = new Board();
        board.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        // 1. e4
        board.makeMove(new Move(6, 4, 4, 4));

        // 1... e5
        board.makeMove(new Move(1, 4, 3, 4));

        // 2. Nf3
        board.makeMove(new Move(7, 6, 5, 5));

        // 2... Nc6
        board.makeMove(new Move(0, 1, 2, 2));

        assertTrue(board.getPiece(4, 4) instanceof Pawn);
        assertTrue(board.getPiece(3, 4) instanceof Pawn);
        assertTrue(board.getPiece(5, 5) instanceof Knight);
        assertTrue(board.getPiece(2, 2) instanceof Knight);
    }

    @Test
    void testCaptureSequence() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Pawn whitePawn = new Pawn(true, 4, 4);
        board.setPiece(4, 4, whitePawn);

        Pawn blackPawn = new Pawn(false, 3, 3);
        board.setPiece(3, 3, blackPawn);

        board.makeMove(new Move(4, 4, 3, 3));

        assertTrue(board.getPiece(3, 3) instanceof Pawn);
        assertTrue(board.getPiece(3, 3).isWhite);
        assertNull(board.getPiece(4, 4));
    }

    @Test
    void testBlockingScenario() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Rook rook = new Rook(true, 7, 4);
        board.setPiece(7, 4, rook);

        Pawn pawn = new Pawn(true, 5, 4);
        board.setPiece(5, 4, pawn);

        List<Move> moves = rook.getPossibleMoves(board);

        boolean canMoveToE2 = false;
        boolean canMoveToE4 = false;

        for (Move m : moves) {
            if (m.endR == 6 && m.endC == 4) canMoveToE2 = true;
            if (m.endR == 4 && m.endC == 4) canMoveToE4 = true;
        }

        assertTrue(canMoveToE2);
        assertFalse(canMoveToE4);
    }
}