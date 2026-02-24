package GUI;

import main.Board;
import Move.Move;
import piece.*;
import piece.Pawn;
import piece.Queen;
import players.ComputerPlayer;
import javax.swing.*;
import java.awt.*;

public class ChessBoardGUI extends JFrame {
    private JButton[][] squares = new JButton[8][8];
    private Board board;
    private Piece selectedPiece = null;
    private boolean pvp, playerWhite, isThinking = false;
    private ComputerPlayer ai;

    public ChessBoardGUI(Board board, boolean pvp, boolean white, String diff) {
        this.board = board; this.pvp = pvp; this.playerWhite = white;
        if (!pvp) this.ai = new ComputerPlayer(!white, diff);

        setTitle("Java Chess - Stable Version");
        setSize(600, 600);
        setLayout(new GridLayout(8, 8));

        initBoard();
        updateIcons();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c] = new JButton();
                squares[r][c].setBackground((r+c)%2==0 ? new Color(245, 245, 220) : new Color(130, 160, 95));
                int row = r, col = c;
                squares[r][c].addActionListener(e -> handleClick(row, col));
                add(squares[r][c]);
            }
        }
    }

    private void handleClick(int r, int c) {
        if (isThinking || (!pvp && board.whiteToMove != playerWhite)) return;

        if (selectedPiece == null) {
            Piece p = board.getPiece(r, c);
            if (p != null && p.isWhite == board.whiteToMove) {
                selectedPiece = p;
                squares[r][c].setBorder(BorderFactory.createLineBorder(Color.CYAN, 4));
            }
        } else {
            Move move = new Move(selectedPiece.row, selectedPiece.col, r, c);
            if (board.isValidMove(move)) {
                board.makeMove(move);
                handlePromotion(r, c);
                updateIcons();
                checkState();
                if (!pvp && !board.isCheckmate(board.whiteToMove)) triggerAI();
            } else {
                JOptionPane.showMessageDialog(this, "Illegal Move!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            selectedPiece = null;
            resetBorders();
        }
    }

    private void handlePromotion(int r, int c) {
        Piece p = board.getPiece(r, c);
        if (p instanceof Pawn && (r == 0 || r == 7)) {
            String[] opts = {"Queen", "Rook", "Bishop", "Knight"};
            int choice = JOptionPane.showOptionDialog(this, "Promote to:", "Promotion",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);
            Piece newP = switch(choice) {
                case 1 -> new Rook(p.isWhite, r, c);
                case 2 -> new Bishop(p.isWhite, r, c);
                case 3 -> new Knight(p.isWhite, r, c);
                default -> new Queen(p.isWhite, r, c);
            };
            board.setPiece(r, c, newP);
        }
    }

    private void checkState() {
        if (board.isCheckmate(board.whiteToMove)) {
            JOptionPane.showMessageDialog(this, "CHECKMATE! Game Over.");
        } else if (board.isInCheck(board.whiteToMove)) {
            JOptionPane.showMessageDialog(this, "Check!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void triggerAI() {
        isThinking = true;
        new Timer(600, e -> {
            Move m = ai.getDecision(board);
            if (m != null) {
                board.makeMove(m);
                handlePromotion(m.endR, m.endC);
                updateIcons();
                checkState();
            }
            isThinking = false;
        }).start();
    }

    private void updateIcons() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(r, c);
                if (p != null) {
                    String t = p.getClass().getSimpleName();
                    squares[r][c].setText((p.isWhite ? "W" : "B") + (t.equals("Knight") ? "N" : t.charAt(0)));
                } else squares[r][c].setText("");
            }
        }
    }

    private void resetBorders() {
        for (int r = 0; r < 8; r++) for (int c = 0; c < 8; c++) squares[r][c].setBorder(null);
    }
}