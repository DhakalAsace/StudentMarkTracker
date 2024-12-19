package inft2201.dhakala;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Handles database operations for user records.
 */
public class UserDA {
    private static Connection connection;
    /**
     * Initializes the database connection.
     * 
     * @param conn The connection object.
     */
    public static void initialize(Connection conn) {
        connection = conn;
    }
    /**
     * Closes the database connection.
     */
    public static void terminate() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Inserts a new user into the database.
     * 
     * @param user The user to be created.
     * @return true if the operation was successful.
     * @throws DuplicateException If a user with the same ID already exists.
     * @throws InvalidUserDataException If the provided user data is invalid.
     */
    public static boolean create(User user) throws DuplicateException, InvalidUserDataException {
        String sql = "INSERT INTO Users (Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getEmailAddress());
            statement.setDate(6, new java.sql.Date(user.getLastAccess().getTime()));
            statement.setDate(7, new java.sql.Date(user.getEnrolDate().getTime()));
            statement.setBoolean(8, user.isEnabled());
            statement.setString(9, String.valueOf(user.getType()));

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new InvalidUserDataException("Failed to create user due to database error.");
        }
    }
    /**
     * Updates an existing user in the database.
     * 
     * @param user The user with updated information.
     * @return true if the operation was successful.
     * @throws SQLException If there is a problem accessing the database.
     * @throws InvalidUserDataException If the provided user data is invalid.
     */
    public static boolean update(User user) throws SQLException, InvalidUserDataException {
        String sql = "UPDATE Users SET Password = ?, FirstName = ?, LastName = ?, EmailAddress = ?, LastAccess = NOW(), EnrolDate = ?, Enabled = ?, Type = ? WHERE Id = ?";
        try (Connection connection = DatabaseConnect.initialize();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getPassword()); // Assume password is already hashed
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmailAddress());
            statement.setDate(5, new java.sql.Date(user.getEnrolDate().getTime()));
            statement.setBoolean(6, user.isEnabled());
            statement.setString(7, String.valueOf(user.getType()));
            statement.setLong(8, user.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } finally {
            DatabaseConnect.terminate();
        }
    }

    /**
     * Deletes a user from the database.
     * 
     * @param user The user to be deleted.
     * @return true if the operation was successful.
     * @throws NotFoundException If the user cannot be found.
     */

    public static boolean delete(User user) throws NotFoundException {
        String sql = "DELETE FROM Users WHERE Id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, user.getId());

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new NotFoundException("Failed to delete user. User not found.");
        }
    }
    /**
     * Retrieves a user from the database.
     * 
     * @param id The ID of the user to retrieve.
     * @return The retrieved User object.
     * @throws NotFoundException If the user cannot be found.
     */
    public static User retrieve(long id) throws NotFoundException {
        String sql = "SELECT * FROM Users WHERE Id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("Id"),
                        resultSet.getString("Password"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("EmailAddress"),
                        resultSet.getDate("LastAccess"),
                        resultSet.getDate("EnrolDate"),
                        resultSet.getString("Type").charAt(0),
                        resultSet.getBoolean("Enabled")
                );
                return user;
            } else {
                throw new NotFoundException("User not found.");
            }
        } catch (SQLException | InvalidUserDataException e) {
            throw new NotFoundException("Failed to retrieve user due to database error.");
        }
    }
    
    /**
     * Hashes the provided password using the SHA-1 algorithm.
     * 
     * @param password The password to hash.
     * @return The hashed password.
     * @throws NoSuchAlgorithmException if the hashing algorithm is not available.
     */
    static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
