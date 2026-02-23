import Move.Move;
import main.Board;
import piece.Piece;
import main.Board;

// These should be fields at the top of your ChessBoardGUI class
private Piece selectedPiece = null;

        private void handleSquareClick(int row, int col) {
            // 1. SELECTING A PIECE
            Board board = new Board();
            if (selectedPiece == null) {
                Piece p = board.getPiece(row, col);

                // Only select if there's a piece AND it's that piece's turn to move
                if (p != null && p.isWhite == board.whiteToMove) {
                    selectedPiece = p;
                    highlightSquare(row, col); // Visual feedback
                }
            }
            // 2. ATTEMPTING A MOVE
            else {
                Move move = new Move(selectedPiece.row, selectedPiece.col, row, col);

                if (board.isValidMove(move)) {
                    board.makeMove(move);
                    updateIcons();
                }

                // Always reset after the second click attempt
                selectedPiece = null;
                clearHighlights();
            }
        }

        private void highlightSquare(int row, int col) {
        }

        private void updateIcons() {
        }

        private void clearHighlights() {
        }

        void main() {
        }