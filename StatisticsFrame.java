import javax.swing.*;
import java.awt.*;

/**
 * StatisticsFrame - Swing window for showing personal statistics.
 * Displays wins, losses, draws, and score of the current player.
 */
public class StatisticsFrame extends JFrame {

    private Player        currentPlayer;
    private PlayerService playerService;

    public StatisticsFrame(Player player) {
        this.playerService = new PlayerService();
        // Refresh from database to get latest data
        Player freshData = playerService.getPlayerById(player.getId());
        this.currentPlayer = (freshData != null) ? freshData : player;
        setupUI();
    }

    private void setupUI() {
        setTitle("Tic-Tac-Toe - My Statistics");
        setSize(380, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 50));

        // Title
        JLabel lblTitle = new JLabel("My Statistics", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(100, 200, 255));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        statsPanel.setBackground(new Color(40, 40, 65));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        addStatRow(statsPanel, "Username:",  currentPlayer.getUsername(),    Color.WHITE);
        addStatRow(statsPanel, "Wins:",      String.valueOf(currentPlayer.getWins()),   new Color(100, 220, 100));
        addStatRow(statsPanel, "Losses:",    String.valueOf(currentPlayer.getLosses()), new Color(220, 100, 100));
        addStatRow(statsPanel, "Draws:",     String.valueOf(currentPlayer.getDraws()),  new Color(220, 200, 100));
        addStatRow(statsPanel, "Total Score:", String.valueOf(currentPlayer.getScore()), new Color(100, 200, 255));

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Close button
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 50));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Arial", Font.BOLD, 13));
        btnClose.setBackground(new Color(100, 100, 130));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setPreferredSize(new Dimension(100, 32));
        btnClose.addActionListener(e -> this.dispose());
        btnPanel.add(btnClose);

        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void addStatRow(JPanel panel, String label, String value, Color valueColor) {
        JLabel lblKey = new JLabel(label);
        lblKey.setFont(new Font("Arial", Font.PLAIN, 15));
        lblKey.setForeground(new Color(180, 180, 180));

        JLabel lblVal = new JLabel(value);
        lblVal.setFont(new Font("Arial", Font.BOLD, 15));
        lblVal.setForeground(valueColor);

        panel.add(lblKey);
        panel.add(lblVal);
    }
}
