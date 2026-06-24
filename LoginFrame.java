import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginFrame - Swing window for username and password input.
 * Calls PlayerService.login() to check credentials from database.
 */
public class LoginFrame extends JFrame {

    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private PlayerService  playerService;

    public LoginFrame() {
        playerService = new PlayerService();
        setupUI();
    }

    private void setupUI() {
        setTitle("Tic-Tac-Toe - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 50));

        // Title label
        JLabel lblTitle = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(100, 200, 255));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 30, 50));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsername, gbc);

        // Username field
        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(txtUsername, gbc);

        // Password label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblPassword, gbc);

        // Password field
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(txtPassword, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 50));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(100, 200, 255));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.setFocusPainted(false);
        btnPanel.add(btnLogin);

        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Allow pressing Enter to login
        txtPassword.addActionListener(e -> doLogin());

        // Login button action
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });
    }

    /**
     * Handles login logic:
     * 1. Gets username and password from fields
     * 2. Calls playerService.login()
     * 3. Opens MainMenuFrame if successful, shows error if not
     */
    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username and password cannot be empty!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Player player = playerService.login(username, password);

        if (player != null) {
            JOptionPane.showMessageDialog(this,
                    "Login successful! Welcome, " + player.getUsername() + "!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            MainMenuFrame menuFrame = new MainMenuFrame(player);
            menuFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }
}
