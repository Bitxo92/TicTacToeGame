# Tic-Tac-Toe Game

This is a **Tic-Tac-Toe** game developed in Java using **Swing** for the graphical user interface (GUI). The game allows two players (X and O) to play against each other, keeping track of the score and displaying the winner after each round. Players can reset the game using the "Play Again?" button.

## Features

- **Two-player gameplay:** Alternates between Player X and Player O.
- **Graphical board:** Display the game state in a visually engaging format.
- **Win detection:** Automatically detects and announces the winner or a draw.
- **Scoreboard:** Keeps track of how many games each player has won.
- **Restart option:** A "Play Again?" button allows players to reset the game and start a new match.

## Screenshots

![image](https://github.com/user-attachments/assets/f19bb645-b69c-4b28-8b0f-712726253e85)

![image](https://github.com/user-attachments/assets/aaa1dd22-e417-4597-963b-5996ea7430d7)

## How to Run

 **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/TicTacToeGame.git
```
## Play the Game

1. The game window will open.
2. Players take turns by clicking on the board cells.
3. Once a player wins or the game ends in a draw, the result will be displayed.
4. Press the "Play Again?" button to restart the game.

## Game Mechanics

- **Game Board**: The board is a 3x3 grid where each cell can be clicked to mark it with `X` or `O` based on the current player.
- **Winning**: The first player to get three of their marks in a row (horizontally, vertically, or diagonally) wins.
- **Draw**: If all cells are filled without any player getting three in a row, the game ends in a draw.
- **Score Tracking**: The game keeps track of how many times each player has won.

## Dependencies

- Java JDK 8 or higher
- Swing and AWT libraries (included in standard Java distribution)

## Future Improvements

- Implement an AI player so that the game can be played in single-player mode.
- Add a timer for each player's turn.
- Implement difficulty levels for the AI.
- Allow users to customize the board size (e.g., 4x4 or 5x5 grids).

## License

This project is licensed under the GNU General Public License (GPL) v3.0. See the [LICENSE](LICENSE) file for details.

