package inft2201.dhakala;

import java.sql.*;

public class FacultyDA {
    static Connection aConnection;
    static Statement aStatement;

    public static void initialize(Connection c) {
        try {
            aConnection = c;
            aStatement = aConnection.createStatement();
        } catch (SQLException e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
        }
    }

    public static void terminate() {
        try {
            if (aStatement != null) aStatement.close();
            if (aConnection != null) aConnection.close();
        } catch (SQLException e) {
            System.err.println("Failed to terminate database connection: " + e.getMessage());
        }
    }

    public static boolean create(Faculty faculty) throws DuplicateException, InvalidUserDataException {
        try {
            if (retrieve(faculty.getId()) != null) { 
                throw new DuplicateException("Faculty with ID: " + faculty.getId() + " already exists.");
            }
        } catch (NotFoundException e) {
        }

        String sqlInsertFaculty = "INSERT INTO faculty (id, schoolcode, schooldescription, office, extension) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement psFaculty = aConnection.prepareStatement(sqlInsertFaculty)) {
            psFaculty.setLong(1, faculty.getId());
            psFaculty.setString(2, faculty.getSchoolCode());
            psFaculty.setString(3, faculty.getSchoolDescription());
            psFaculty.setString(4, faculty.getOffice());
            psFaculty.setInt(5, faculty.getExtension());

            int result = psFaculty.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error creating faculty: " + e.getMessage());
            return false;
        }
    }

    public static Faculty retrieve(long id) throws NotFoundException, InvalidUserDataException {
        String sqlRetrieve = "SELECT * FROM faculty WHERE id = ?";
        try (PreparedStatement psRetrieve = aConnection.prepareStatement(sqlRetrieve)) {
            psRetrieve.setLong(1, id);
            ResultSet rs = psRetrieve.executeQuery();
            
            if (!rs.next()) {
                throw new NotFoundException("Faculty with ID: " + id + " not found.");
            }
            
            return new Faculty(
                rs.getLong("id"),
                "", 
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("emailaddress"),
                rs.getDate("lastaccess"),
                rs.getDate("enroldate"),
                rs.getString("type").charAt(0),
                rs.getBoolean("enabled"),
                rs.getString("schoolcode"),
                rs.getString("schooldescription"),
                rs.getString("office"),
                rs.getInt("extension")
            );
        } catch (SQLException e) {
            System.err.println("Error retrieving faculty: " + e.getMessage());
            throw new InvalidUserDataException("Error retrieving faculty: " + e.getMessage());
        }
    }

    public static int update(Faculty faculty) throws NotFoundException, InvalidUserDataException {
        String sqlUpdateFaculty = "UPDATE faculty SET schoolcode = ?, schooldescription = ?, office = ?, extension = ? WHERE id = ?";
        try (PreparedStatement psUpdate = aConnection.prepareStatement(sqlUpdateFaculty)) {
            psUpdate.setString(1, faculty.getSchoolCode());
            psUpdate.setString(2, faculty.getSchoolDescription());
            psUpdate.setString(3, faculty.getOffice());
            psUpdate.setInt(4, faculty.getExtension());
            psUpdate.setLong(5, faculty.getId());

            return psUpdate.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating faculty: " + e.getMessage());
            throw new InvalidUserDataException("Error updating faculty: " + e.getMessage());
        }
    }

    public static int delete(long id) throws NotFoundException, InvalidUserDataException {
        String sqlDeleteFaculty = "DELETE FROM faculty WHERE id = ?";
        try (PreparedStatement psDelete = aConnection.prepareStatement(sqlDeleteFaculty)) {
            psDelete.setLong(1, id);
            int result = psDelete.executeUpdate();

            if (result == 0) {
                throw new NotFoundException("Faculty with ID: " + id + " not found.");
            }

            return result;
        } catch (SQLException e) {
            System.err.println("Error deleting faculty: " + e.getMessage());
            throw new InvalidUserDataException("Error deleting faculty: " + e.getMessage());
        }
    }
}
