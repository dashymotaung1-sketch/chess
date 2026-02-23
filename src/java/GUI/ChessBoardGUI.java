package GUI;

import main.Board;
import Move.Move;
import piece.Piece;
import javax.swing.*;
import java.awt.*;

public class ChessBoardGUI extends JFrame {
    private JButton[][] squares = new JButton[8][8];
    private Board board;
    private Piece selectedPiece = null;

    public ChessBoardGUI(Board board) {
        this.board = board;
        setTitle("Java Chess Project");
        setSize(600, 600);
        setLayout(new GridLayout(8, 8));

        initializeBoard();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window
        setVisible(true);
    }

    private void initializeBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c] = new JButton();

                // Color the checkerboard
                if ((r + c) % 2 == 0) {
                    squares[r][c].setBackground(Color.WHITE);
                } else {
                    squares[r][c].setBackground(Color.LIGHT_GRAY);
                }

                squares[r][c].setFocusPainted(false);

                // Use final variables for the lambda listener
                int row = r;
                int col = c;
                squares[r][c].addActionListener(e -> handleSquareClick(row, col));

                add(squares[r][c]);
            }
        }
        updateIcons();
    }

    private void handleSquareClick(int row, int col) {
        if (selectedPiece == null) {
            // Step 1: Try to select a piece
            Piece p = board.getPiece(row, col);
            if (p != null && p.isWhite == board.whiteToMove) {
                selectedPiece = p;
                highlightSquare(row, col);
            }
        } else {
            // Step 2: Try to move the selected piece
            Move move = new Move(selectedPiece.row, selectedPiece.col, row, col);

            if (board.isValidMove(move)) {
                board.makeMove(move);
                updateIcons();
            }

            // Step 3: Cleanup
            selectedPiece = null;
            clearHighlights();
        }
    }

    private void updateIcons() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(r, c);
                if (p != null) {
                    String color = p.isWhite ? "w" : "b";
                    String type = p.getClass().getSimpleName();
                    // Using 'N' for Knight to distinguish from King
                    String label = type.equals("Knight") ? "N" : type.substring(0, 1);
                    squares[r][c].setText(color + label);
                } else {
                    squares[r][c].setText("");
                }
            }
        }
    }

    private void highlightSquare(int row, int col) {
        squares[row][col].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
    }

    private void clearHighlights() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c].setBorder(UIManager.getBorder("Button.border"));
            }
        }
    }
}