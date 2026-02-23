package tests;

import main.Board;
import Move.Move;
import piece.Queen;
import piece.Pawn;
import org.junit.jupiter.api.Test;
import tests.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    @Test
    void testQueenEmptyBoard() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Queen queen = new Queen(true, 4, 4);
        board.setPiece(4, 4, queen);

        List<Move> moves = queen.getPossibleMoves(board);

        assertEquals(27, moves.size(),
                "Queen on empty board should have 27 moves");
    }

    @Test
    void testQueenBlocked() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Queen queen = new Queen(true, 4, 4);
        board.setPiece(4, 4, queen);

        board.setPiece(2, 4, new Pawn(true, 2, 4));
        board.setPiece(6, 4, new Pawn(true, 6, 4));
        board.setPiece(4, 2, new Pawn(true, 4, 2));
        board.setPiece(4, 6, new Pawn(true, 4, 6));
        board.setPiece(2, 2, new Pawn(true, 2, 2));
        board.setPiece(2, 6, new Pawn(true, 2, 6));
        board.setPiece(6, 2, new Pawn(true, 6, 2));
        board.setPiece(6, 6, new Pawn(true, 6, 6));

        List<Move> moves = queen.getPossibleMoves(board);

        assertEquals(8, moves.size(),
                "Queen should have 8 moves when blocked by friendly pieces");
    }

    @Test
    void testQueenCaptures() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Queen queen = new Queen(true, 4, 4);
        board.setPiece(4, 4, queen);

        Pawn enemy1 = new Pawn(false, 2, 4);
        Pawn enemy2 = new Pawn(false, 6, 6);

        board.setPiece(2, 4, enemy1);
        board.setPiece(6, 6, enemy2);

        List<Move> moves = queen.getPossibleMoves(board);

        boolean foundCapture1 = false;
        boolean foundCapture2 = false;

        for (Move m : moves) {
            if (m.endR == 2 && m.endC == 4) foundCapture1 = true;
            if (m.endR == 6 && m.endC == 6) foundCapture2 = true;
        }

        assertTrue(foundCapture1 && foundCapture2,
                "Queen should be able to capture enemy pieces");
    }
}