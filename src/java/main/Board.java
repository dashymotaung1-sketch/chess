package main;

import Move.Move;
import piece.*;
import piece.King;
import piece.Pawn;
import piece.Queen;

import java.util.List;

public class Board {
    private Piece[][] grid = new Piece[8][8];
    public boolean whiteToMove = true;
    private boolean simulating = false; // The Recursion Guard

    public boolean isSimulating() { return simulating; }
    public Piece getPiece(int r, int c) { return isValidSquare(r, c) ? grid[r][c] : null; }
    public void setPiece(int r, int c, Piece p) { if (isValidSquare(r, c)) grid[r][c] = p; }
    public boolean isValidSquare(int r, int c) { return r >= 0 && r < 8 && c >= 0 && c < 8; }

    public void makeMove(Move move) {
        executeMoveLogic(move);
        whiteToMove = !whiteToMove;
    }

    private void executeMoveLogic(Move move) {
        Piece p = getPiece(move.startR, move.startC);
        if (p == null) return;

        // Castling Movement
        if (p instanceof King && Math.abs(move.startC - move.endC) == 2) {
            int rCol = (move.endC > move.startC) ? 7 : 0;
            int rDest = (move.endC > move.startC) ? move.endC - 1 : move.endC + 1;
            Piece rook = getPiece(move.startR, rCol);
            grid[move.startR][rCol] = null;
            grid[move.startR][rDest] = rook;
            if (rook != null) rook.updatePosition(move.startR, rDest);
        }

        grid[move.startR][move.startC] = null;
        grid[move.endR][move.endC] = p;
        p.updatePosition(move.endR, move.endC);
        p.hasMoved = true;
    }

    public boolean isInCheck(boolean white) {
        if (simulating) return false; // Break the cycle
        simulating = true;

        int kr = -1, kc = -1;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = getPiece(r, c);
                if (p instanceof King && p.isWhite == white) { kr = r; kc = c; break; }
            }
        }

        boolean foundCheck = false;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = getPiece(r, c);
                if (p != null && p.isWhite != white) {
                    for (Move m : p.getPossibleMoves(this)) {
                        if (m.endR == kr && m.endC == kc) { foundCheck = true; break; }
                    }
                }
                if (foundCheck) break;
            }
            if (foundCheck) break;
        }

        simulating = false;
        return foundCheck;
    }

    public boolean isValidMove(Move move) {
        Piece p = getPiece(move.startR, move.startC);
        if (p == null || p.isWhite != whiteToMove) return false;

        boolean geoValid = p.getPossibleMoves(this).stream()
                .anyMatch(m -> m.endR == move.endR && m.endC == move.endC);
        if (!geoValid) return false;

        Board temp = this.copyBoard();
        temp.executeMoveLogic(move);
        return !temp.isInCheck(p.isWhite);
    }

    public boolean isCheckmate(boolean white) {
        if (!isInCheck(white)) return false;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = getPiece(r, c);
                if (p != null && p.isWhite == white) {
                    for (Move m : p.getPossibleMoves(this)) {
                        if (isValidMove(m)) return false;
                    }
                }
            }
        }
        return true;
    }

    public Board copyBoard() {
        Board copy = new Board();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = getPiece(r, c);
                if (p != null) copy.setPiece(r, c, p.copy());
            }
        }
        copy.whiteToMove = this.whiteToMove;
        return copy;
    }

    public void loadFEN(String fen) {
        String layout = fen.split(" ")[0];
        int r = 0, c = 0;
        for (char ch : layout.toCharArray()) {
            if (ch == '/') { r++; c = 0; }
            else if (Character.isDigit(ch)) { c += Character.getNumericValue(ch); }
            else {
                boolean w = Character.isUpperCase(ch);
                char t = Character.toLowerCase(ch);
                switch (t) {
                    case 'p' -> setPiece(r, c, new Pawn(w, r, c));
                    case 'r' -> setPiece(r, c, new Rook(w, r, c));
                    case 'n' -> setPiece(r, c, new Knight(w, r, c));
                    case 'b' -> setPiece(r, c, new Bishop(w, r, c));
                    case 'q' -> setPiece(r, c, new Queen(w, r, c));
                    case 'k' -> setPiece(r, c, new King(w, r, c));
                }
                c++;
            }
        }
    }
}