import javax.swing.SwingUtilities;

/**
 * Main class - Entry point of the application.
 * Opens the Login Window when the program starts.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}
