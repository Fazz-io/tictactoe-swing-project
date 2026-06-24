import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * TopScorersFrame - Swing window for showing Top 5 scorers.
 * Data is retrieved from the database using PlayerService.
 * Displayed using JTable.
 */
public class TopScorersFrame extends JFrame {

    private JTable        table;
    private PlayerService playerService;

    public TopScorersFrame() {
        this.playerService = new PlayerService();
        setupUI();
    }

    private void setupUI() {
        setTitle("Tic-Tac-Toe - Top 5 Scorers");
        setSize(500, 340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 50));

        // Title
        JLabel lblTitle = new JLabel("🏆  Top 5 Scorers", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(220, 180, 50));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Table setup
        String[]           columns = {"Rank", "Username", "Wins", "Losses", "Draws", "Score"};
        DefaultTableModel  model   = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; } // read-only
        };

        // Get Top 5 data from database
        ArrayList<Player> topPlayers = playerService.getTopFiveScorers();
        for (int i = 0; i < topPlayers.size(); i++) {
            Player p = topPlayers.get(i);
            model.addRow(new Object[]{
                    i + 1,                // Rank
                    p.getUsername(),      // Username
                    p.getWins(),          // Wins
                    p.getLosses(),        // Losses
                    p.getDraws(),         // Draws
                    p.getScore()          // Score
            });
        }

        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setBackground(new Color(40, 40, 65));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(80, 80, 110));
        table.setSelectionBackground(new Color(80, 130, 200));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(50, 100, 180));
        table.getTableHeader().setForeground(Color.WHITE);

        // Center all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // Rank
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Username
        table.getColumnModel().getColumn(2).setPreferredWidth(60);  // Wins
        table.getColumnModel().getColumn(3).setPreferredWidth(60);  // Losses
        table.getColumnModel().getColumn(4).setPreferredWidth(60);  // Draws
        table.getColumnModel().getColumn(5).setPreferredWidth(80);  // Score

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        scrollPane.getViewport().setBackground(new Color(40, 40, 65));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Close button
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 50));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

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
}
