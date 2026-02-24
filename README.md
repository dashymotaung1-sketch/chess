

# Java Chess Engine ♟️

A fully functional, 2D Chess application built from scratch using Java and Swing. Features include local PvP, a Minimax-based AI, and full chess rules logic.

## 🚀 Features
* **Full Chess Rules**: Implements movement for all pieces, including:
    * **Castling**: King and Rook double-move.
    * **Pawn Promotion**: Choose between Queen, Rook, Bishop, or Knight when reaching the 8th rank.
    * **Check & Checkmate Detection**: Automatic safety verification for every move.
* **AI Opponent**: Play against a computer with three difficulty levels:
    * **Easy**: Makes random moves.
    * **Medium**: Looks 2 moves ahead.
    * **Hard**: Uses Minimax with Alpha-Beta pruning to look 3+ moves ahead.
* **Move Validation**: Prevents illegal moves and alerts the player if their King is in danger.
* **FEN Loading**: Supports loading specific board states via FEN strings.

## 🛠️ Technical Overview
* **Architecture**: Follows an Object-Oriented approach with a base `Piece` class and specific subclasses for each piece type.
* **Recursion Safety**: Implements a "Recursion Guard" in the `Board` logic to prevent `StackOverflowError` during deep move simulations.
* **Deep Copying**: The AI simulates moves on virtual board clones to avoid affecting the live game state.

## 🕹️ How to Run
1.  **Prerequisites**: Ensure you have [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/) installed.
2.  **Clone the project**:
    ```bash
    git clone [https://github.com/yourusername/java-chess.git](https://github.com/yourusername/java-chess.git)
    ```
3.  **Compile**:
    Navigate to the `src` folder and compile the main class.
4.  **Launch**:
    Run `main.Main` to open the game menu.

## 📸 Gameplay


## 📝 Rules Implemented
| Feature | Status |
| :--- | :--- |
| Piece Movement | ✅ Complete |
| Check/Checkmate | ✅ Complete |
| Castling | ✅ Complete |
| Pawn Promotion | ✅ Complete |
| AI Difficulty | ✅ Complete |
| En Passant | ⏳ Planned |

## 🤝 Contributing
Feel free to fork this project and submit pull requests for features like move history, clock timers, or improved AI heuristics!

## 📜 Rules of the Game

This engine follows official FIDE chess rules. Here is how to play:

### ♟️ Piece Movements
* **King**: Moves one square in any direction.
* **Queen**: Moves any number of squares diagonally, horizontally, or vertically.
* **Rook**: Moves any number of squares horizontally or vertically.
* **Bishop**: Moves any number of squares diagonally.
* **Knight**: Moves in an "L-shape" (two squares in one direction, then one square perpendicular). It is the only piece that can "jump" over others.
* **Pawn**: Moves forward one square (or two on its first move). Captures diagonally.

### 🚩 Special Moves
* **Castling**: If your King and Rook haven't moved and the path is clear, move the King two squares toward the Rook. The Rook will automatically hop over the King. You cannot castle if you are in check or moving through check.
* **Promotion**: When a Pawn reaches the opposite end of the board, a menu will appear allowing you to promote it to a stronger piece.

### ⚔️ Winning and Losing
* **Check**: Your King is under attack. You **must** move to safety, block the attack, or capture the attacking piece.
* **Checkmate**: Your King is in check and there are no legal moves to escape. The game ends immediately.
* **Invalid Moves**: The game will prevent you from making any move that leaves your King in check. A warning pop-up will appear if you try.

## 🎮 Game Controls
1.  **Select Piece**: Click on any of your pieces (White always moves first). A **cyan border** will highlight your selection.
2.  **Move**: Click on a destination square. If the move is legal, the piece will move and the turn will switch.
3.  **Deselect**: To change your selection, click the piece again or click an empty square (if it's not a valid move).