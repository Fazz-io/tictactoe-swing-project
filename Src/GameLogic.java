import java.util.ArrayList;
import java.util.Random;

/**
 * GameLogic class - Handles all game rules for Tic-Tac-Toe.
 * Includes: move validation, winner checking, draw checking, computer move.
 */
public class GameLogic {

    private char[] board;

    public GameLogic() {
        board = new char[9];
        resetBoard();
    }

    /**
     * Resets all cells to empty (space character).
     */
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    /**
     * Makes a move at the given index with the given symbol ('X' or 'O').
     * Returns true if the move is valid, false if the cell is already occupied.
     */
    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) {
            return false;
        }
        if (board[index] != ' ') {
            return false; // Cell already occupied - invalid move
        }
        board[index] = symbol;
        return true;
    }

    /**
     * Checks if the given symbol ('X' or 'O') has won.
     * Checks all 8 winning patterns: 3 rows, 3 columns, 2 diagonals.
     */
    public boolean checkWinner(char symbol) {
        int[][] patterns = {
                {0, 1, 2}, // Row 1
                {3, 4, 5}, // Row 2
                {6, 7, 8}, // Row 3
                {0, 3, 6}, // Column 1
                {1, 4, 7}, // Column 2
                {2, 5, 8}, // Column 3
                {0, 4, 8}, // Diagonal top-left to bottom-right
                {2, 4, 6}  // Diagonal top-right to bottom-left
        };

        for (int i = 0; i < patterns.length; i++) {
            int a = patterns[i][0];
            int b = patterns[i][1];
            int c = patterns[i][2];
            if (board[a] == symbol && board[b] == symbol && board[c] == symbol) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the board is full with no winner (draw).
     */
    public boolean isDraw() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                return false; // Still has empty cell - not a draw yet
            }
        }
        return true;
    }

    /**
     * Computer makes a move using simple strategy:
     * 1. Try to win if possible
     * 2. Block player from winning
     * 3. Otherwise pick a random empty cell
     * Returns the index of the cell chosen by computer, or -1 if no move possible.
     */
    public int computerMove() {
        // Step 1: Try to win
        int winMove = findBestMove('O');
        if (winMove != -1) {
            board[winMove] = 'O';
            return winMove;
        }

        // Step 2: Block player from winning
        int blockMove = findBestMove('X');
        if (blockMove != -1) {
            board[blockMove] = 'O';
            return blockMove;
        }

        // Step 3: Try center
        if (board[4] == ' ') {
            board[4] = 'O';
            return 4;
        }

        // Step 4: Pick random empty cell
        ArrayList<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                emptyCells.add(i);
            }
        }
        if (!emptyCells.isEmpty()) {
            Random random = new Random();
            int chosenIndex = emptyCells.get(random.nextInt(emptyCells.size()));
            board[chosenIndex] = 'O';
            return chosenIndex;
        }

        return -1; // No moves available
    }

    /**
     * Helper method: finds a winning move for the given symbol.
     * Returns the index if a winning move is found, -1 otherwise.
     */
    private int findBestMove(char symbol) {
        int[][] patterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] pattern : patterns) {
            int a = pattern[0], b = pattern[1], c = pattern[2];
            // Two of the same symbol + one empty = winning/blocking move
            if (board[a] == symbol && board[b] == symbol && board[c] == ' ') return c;
            if (board[a] == symbol && board[c] == symbol && board[b] == ' ') return b;
            if (board[b] == symbol && board[c] == symbol && board[a] == ' ') return a;
        }
        return -1;
    }

    public char[] getBoard() {
        return board;
    }
}
