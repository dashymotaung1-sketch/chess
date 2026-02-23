package main;

import GUI.ChessBoardGUI;

public class Main {
    public static void main(String[] args) {
        // 1. Create the shared board
        Board board = new Board();

        // 2. Load pieces (standard start)
        board.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        // 3. Start the GUI using that SAME board
        new ChessBoardGUI(board);
    }
}