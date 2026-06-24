import javax.swing.*;
import java.awt.*;

/**
 * GameFrame - Swing window for playing Tic-Tac-Toe.
 * Board is represented by 9 JButtons.
 * Player uses 'X', Computer uses 'O'.
 */
public class GameFrame extends JFrame {

    private Player        currentPlayer;
    private PlayerService playerService;
    private GameLogic     gameLogic;
    private JButton[]     buttons;
    private JLabel        lblStatus;
    private boolean       gameOver;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic     = new GameLogic();
        this.gameOver      = false;
        setupUI();
    }

    private void setupUI() {
        setTitle("Tic-Tac-Toe - Game");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 50));

        // Top: player name and status
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(30, 30, 50));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        JLabel lblPlayer = new JLabel("Player: " + currentPlayer.getUsername() + "  (X)", SwingConstants.CENTER);
        lblPlayer.setFont(new Font("Arial", Font.BOLD, 16));
        lblPlayer.setForeground(new Color(100, 200, 255));

        lblStatus = new JLabel("Your turn! Click a cell to place X", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        lblStatus.setForeground(Color.WHITE);

        topPanel.add(lblPlayer);
        topPanel.add(lblStatus);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center: 3x3 board
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(new Color(50, 50, 80));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].setBackground(new Color(60, 60, 90));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorderPainted(false);
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            final int index = i;
            buttons[i].addActionListener(e -> handlePlayerMove(index));
            boardPanel.add(buttons[i]);
        }
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Bottom: Back to Menu button
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 50));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        JButton btnBack = new JButton("← Back to Menu");
        btnBack.setFont(new Font("Arial", Font.BOLD, 13));
        btnBack.setBackground(new Color(100, 100, 130));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Go back to menu? Current game will be lost.",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
                menuFrame.setVisible(true);
                this.dispose();
            }
        });
        btnPanel.add(btnBack);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Handles player's move when they click a board button.
     */
    private void handlePlayerMove(int index) {
        if (gameOver) return;

        // Try to make player move (X)
        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) {
            lblStatus.setText("That cell is already taken! Choose another.");
            return;
        }

        // Update button appearance
        buttons[index].setText("X");
        buttons[index].setForeground(new Color(100, 200, 255));
        buttons[index].setEnabled(false);

        // Check if player wins
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }

        // Check draw
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // Computer's turn
        lblStatus.setText("Computer is thinking...");

        // Small delay so it feels natural
        Timer timer = new Timer(400, e -> {
            int compIndex = gameLogic.computerMove();
            if (compIndex != -1) {
                buttons[compIndex].setText("O");
                buttons[compIndex].setForeground(new Color(255, 150, 100));
                buttons[compIndex].setEnabled(false);
            }

            // Check if computer wins
            if (gameLogic.checkWinner('O')) {
                finishGame("LOSE");
                return;
            }

            // Check draw again after computer move
            if (gameLogic.isDraw()) {
                finishGame("DRAW");
                return;
            }

            lblStatus.setText("Your turn! Click a cell to place X");
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Called when the game ends (WIN, LOSE, or DRAW).
     * Updates database statistics, shows result, returns to main menu.
     */
    private void finishGame(String result) {
        gameOver = true;

        // Disable all buttons
        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }

        // Update statistics in database
        playerService.updateStatistics(currentPlayer, result);

        // Refresh player data from database
        Player updatedPlayer = playerService.getPlayerById(currentPlayer.getId());
        if (updatedPlayer != null) {
            currentPlayer = updatedPlayer;
        }

        // Show result message
        String message;
        String title;
        if (result.equals("WIN")) {
            message = "🎉 You Win! +10 points\nNew Score: " + currentPlayer.getScore();
            title   = "You Win!";
            lblStatus.setText("You Win! 🎉");
        } else if (result.equals("LOSE")) {
            message = "😞 You Lose! Computer wins.\nScore: " + currentPlayer.getScore();
            title   = "You Lose!";
            lblStatus.setText("Computer wins! 😞");
        } else {
            message = "🤝 It's a Draw! +3 points\nNew Score: " + currentPlayer.getScore();
            title   = "Draw!";
            lblStatus.setText("It's a Draw! 🤝");
        }

        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);

        // Return to main menu
        MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
        menuFrame.setVisible(true);
        this.dispose();
    }
}
