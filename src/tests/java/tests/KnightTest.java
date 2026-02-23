package tests.piece;

import main.Board;
import Move.Move;
import piece.Knight;
import piece.Pawn;
import org.junit.jupiter.api.Test;
import tests.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    @Test
    void testKnightCenterMoves() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Knight knight = new Knight(true, 4, 4);
        board.setPiece(4, 4, knight);

        List<Move> moves = knight.getPossibleMoves(board);

        assertEquals(8, moves.size(),
                "Knight in center should have 8 possible moves");
    }

    @Test
    void testKnightCornerMoves() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Knight knight = new Knight(true, 7, 0);
        board.setPiece(7, 0, knight);

        List<Move> moves = knight.getPossibleMoves(board);

        assertEquals(2, moves.size(),
                "Knight in corner should have 2 possible moves");
    }

    @Test
    void testKnightCaptures() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Knight knight = new Knight(true, 4, 4);
        board.setPiece(4, 4, knight);

        board.setPiece(2, 3, new Pawn(false, 2, 3));
        board.setPiece(2, 5, new Pawn(false, 2, 5));

        List<Move> moves = knight.getPossibleMoves(board);

        assertEquals(8, moves.size(),
                "Knight moves should include capture squares");
    }

    @Test
    void testKnightBlockedByFriendly() {
        Board board = new Board();
        TestUtils.clearBoard(board);

        Knight knight = new Knight(true, 4, 4);
        board.setPiece(4, 4, knight);

        board.setPiece(2, 3, new Knight(true, 2, 3));
        board.setPiece(2, 5, new Knight(true, 2, 5));

        List<Move> moves = knight.getPossibleMoves(board);

        assertEquals(6, moves.size(),
                "Knight should not move onto friendly pieces");
    }
}