import javax.swing.*;
import java.awt.*;

/**
 * MainMenuFrame - Swing window for the main menu after login.
 * Contains navigation buttons: Start Game, My Statistics, Top 5 Scorers, Exit.
 */
public class MainMenuFrame extends JFrame {

    private Player  currentPlayer;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;
        setupUI();
    }

    private void setupUI() {
        setTitle("Tic-Tac-Toe - Main Menu");
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 50));

        // Welcome label
        JLabel lblWelcome = new JLabel("Welcome, " + currentPlayer.getUsername() + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        lblWelcome.setForeground(new Color(100, 200, 255));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(25, 0, 5, 0));
        mainPanel.add(lblWelcome, BorderLayout.NORTH);

        // Score label
        JLabel lblScore = new JLabel("Score: " + currentPlayer.getScore() +
                "  |  W: " + currentPlayer.getWins() +
                "  L: " + currentPlayer.getLosses() +
                "  D: " + currentPlayer.getDraws(), SwingConstants.CENTER);
        lblScore.setFont(new Font("Arial", Font.PLAIN, 13));
        lblScore.setForeground(new Color(180, 180, 180));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 50));
        topPanel.add(lblWelcome, BorderLayout.NORTH);
        topPanel.add(lblScore, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Buttons panel
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        btnPanel.setBackground(new Color(30, 30, 50));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        btnStartGame   = createMenuButton("▶  Start Game",    new Color(80, 180, 100));
        btnStatistics  = createMenuButton("📊  My Statistics", new Color(100, 150, 220));
        btnTopScorers  = createMenuButton("🏆  Top 5 Scorers", new Color(220, 180, 50));
        btnExit        = createMenuButton("✖  Exit",           new Color(200, 80, 80));

        btnPanel.add(btnStartGame);
        btnPanel.add(btnStatistics);
        btnPanel.add(btnTopScorers);
        btnPanel.add(btnExit);

        mainPanel.add(btnPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Button actions
        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            this.dispose();
        });

        btnStatistics.addActionListener(e -> {
            StatisticsFrame statisticsFrame = new StatisticsFrame(currentPlayer);
            statisticsFrame.setVisible(true);
        });

        btnTopScorers.addActionListener(e -> {
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private JButton createMenuButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
