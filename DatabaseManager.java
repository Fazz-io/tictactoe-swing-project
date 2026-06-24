import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseManager class - Handles JDBC database connection.
 * TODO: Change URL, USER, and PASSWORD to match your PostgreSQL configuration.
 */
public class DatabaseManager {

    // TODO: Change these values based on your PostgreSQL configuration
    private static final String URL      = "jdbc:postgresql://localhost:5433/game_project";
    private static final String USER     = "postgres";   // Change to your PostgreSQL username
    private static final String PASSWORD = "5026251130";   // Change to your PostgreSQL password

    /**
     * Returns a new database connection.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
