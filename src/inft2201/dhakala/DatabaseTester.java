package inft2201.dhakala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseTester class manages database connections.
 */
public class DatabaseTester {
    /** Database URL. */
    static String url = "jdbc:postgresql://127.0.0.1:5432/dhakala_db";

    /** Connection to the database. */
    static Connection aConnection;

    /** Database user ID. */
    static String user = "USER_ID";

    /** Database user password. */
    static String password = "USER_PASSWORD";

    /**
     * Initializes a connection to the database.
     * 
     * @return A connection to the database if successful, or null if an error occurs.
     */
    public static Connection initialize() {
        try {
            // Load the JDBC Driver for PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Create connection instance
            aConnection = DriverManager.getConnection(url, user, password);
        } 
        catch (ClassNotFoundException e) { // Occurs if the driver is not found
            System.out.println(e);
        } 
        catch (SQLException e) { // Occurs for database access errors
            System.out.println(e);
        }
        return aConnection;
    }

}
