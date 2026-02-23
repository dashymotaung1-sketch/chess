package main;

import piece.*;
import Move.Move;
import piece.King;
import piece.Pawn;
import piece.Rook;

public class Board {
    private Piece[][] grid = new Piece[8][8];
    public boolean whiteToMove = true;

    public Piece getPiece(int r, int c) {
        if (isValidSquare(r, c)) return grid[r][c];
        return null;
    }

    public void setPiece(int r, int c, Piece piece) {
        if (isValidSquare(r, c)) grid[r][c] = piece;
    }

    public void makeMove(Move move) {
        Piece p = getPiece(move.startR, move.startC);
        if (p != null) {
            grid[move.endR][move.endC] = p;
            grid[move.startR][move.startC] = null;
            p.updatePosition(move.endR, move.endC); // Uses the method in Piece
            whiteToMove = !whiteToMove;
        }
    }

    public boolean isValidSquare(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    public boolean isValidMove(Move move) {
        Piece p = getPiece(move.startR, move.startC);
        if (p == null || p.isWhite != whiteToMove) return false;

        for (Move possible : p.getPossibleMoves(this)) {
            if (possible.endR == move.endR && possible.endC == move.endC) {
                return true;
            }
        }
        return false;
    }

    public void loadFEN(String fen) {
        this.grid = new Piece[8][8];
        String layout = fen.split(" ")[0];
        int row = 0, col = 0;
        for (char c : layout.toCharArray()) {
            if (c == '/') {
                row++;
                col = 0;
            } else if (Character.isDigit(c)) {
                col += Character.getNumericValue(c);
            } else {
                boolean isWhite = Character.isUpperCase(c);
                char type = Character.toLowerCase(c);

                switch (type) {
                    case 'p':
                        setPiece(row, col, new Pawn(isWhite, row, col));
                        break;
                    case 'n':
                        setPiece(row, col, new Knight(isWhite, row, col));
                        break;
                    case 'b':
                        setPiece(row, col, new Bishop(isWhite, row, col));
                        break;
                    case 'r':
                        setPiece(row, col, new Rook(isWhite, row, col));
                        break;
                    case 'q':
                        setPiece(row, col, new piece.Queen(isWhite, row, col));
                        break;
                    case 'k':
                        setPiece(row, col, new King(isWhite, row, col));
                        break;
                }
                col++;
            }
        }
    }
}