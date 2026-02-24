package main;

import GUI.ChessBoardGUI;
import players.GameModeDialog;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModeDialog dialog = new GameModeDialog(null);
            dialog.setVisible(true);

            Board board = new Board();
            board.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

            new ChessBoardGUI(
                    board,
                    dialog.isTwoPlayerMode(),
                    dialog.isPlayerWhite(),
                    dialog.getDifficulty()
            );
        });
    }
}