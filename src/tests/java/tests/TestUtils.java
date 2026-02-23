package tests;

import main.Board;
import piece.Piece;

public class TestUtils {

    public static void printBoard(Board board) {
        System.out.println("  a b c d e f g h");
        for (int r = 0; r < 8; r++) {
            System.out.print((8 - r) + " ");
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(r, c);
                if (p == null) {
                    System.out.print(". ");
                } else {
                    String symbol = p.getClass().getSimpleName().substring(0, 1);
                    if (symbol.equals("K") && p.getClass().getSimpleName().equals("Knight")) {
                        symbol = "N";
                    }
                    System.out.print((p.isWhite ? "w" : "b") + symbol + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void clearBoard(Board board) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board.setPiece(r, c, null);
            }
        }
    }
}