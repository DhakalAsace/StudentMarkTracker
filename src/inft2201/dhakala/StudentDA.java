/**
* 
* @author Ashesh Dhakal
* @version 1.0
* @since 1.0
* date: Feb 16, 2024
*/
package inft2201.dhakala;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
* StudentDA - This file contains all of the data access methods that interact with the database
* to manage Student records.
* @author Ashesh Dhakal
* @version 1.0
* @since 1.0
*/
public class StudentDA {
   static Connection aConnection;
   static Statement aStatement;
   
   /**
    * Simple date format sql df
    * 
    * @param c The Connection object.
    */
   // SQL Date Formatter
   @SuppressWarnings("unused")
   private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");

   /**
    * Initializes the database connection.
    * 
    * @param c The Connection object.
    */
   public static void initialize(Connection c) {
       try {
           aConnection = c;
           aStatement = aConnection.createStatement();
       } catch (SQLException e) {
           System.out.println(e);
       }
   }

   /**
    * Creates a new student record in the database along with associated user data.
    * 
    * @param student The student to be created.
    * @return true if the student and user records are successfully inserted.
    * @throws SQLException if there is a problem accessing the database or the transaction fails.
    * @throws DuplicateException if a user with the same ID already exists.
    * @throws InvalidUserDataException if the user data provided is invalid.
    */

   public static boolean create(Student student) throws SQLException, DuplicateException, InvalidUserDataException {
	    boolean inserted = false;

	    if (aConnection == null) {
	        throw new SQLException("Database connection is not initialized.");
	    }

	    try {
	        aConnection.setAutoCommit(false);

	        User user = new User(student.getId(), student.getPassword(), student.getFirstName(), student.getLastName(),
	                student.getEmailAddress(), student.getLastAccess(), student.getEnrolDate(), student.getType(), student.isEnabled());
	        UserDA.initialize(aConnection);
	        boolean userInserted = UserDA.create(user);
	        if (userInserted) {
	            String sqlInsertStudent = "INSERT INTO students (id, programCode, programDescription, year) VALUES (?, ?, ?, ?)";
	            try (PreparedStatement psStudent = aConnection.prepareStatement(sqlInsertStudent)) {
	                psStudent.setLong(1, student.getId());
	                psStudent.setString(2, student.getProgramCode());
	                psStudent.setString(3, student.getProgramDescription());
	                psStudent.setInt(4, student.getYear());

	                int studentResult = psStudent.executeUpdate();
	                if (studentResult > 0) {
	                    inserted = true;
	                    aConnection.commit();
	                } else {
	                    aConnection.rollback();
	                    throw new SQLException("Failed to insert the student part.");
	                }
	            }
	        } else {
	            // Rollback if user creation fails
	            aConnection.rollback();
	            throw new SQLException("Failed to insert the user part of the student.");
	        }
	    } catch (SQLException | DuplicateException | InvalidUserDataException e) {
	        // Log the exception
	        System.err.println("Error creating student: " + e.getMessage());
	        e.printStackTrace();

	        // Attempt to rollback on error
	        try {
	            if (aConnection != null) {
	                aConnection.rollback();
	            }
	        } catch (SQLException e2) {
	            e.addSuppressed(e2);
	        }
	        throw e; 
	    } finally {
	        if (aConnection != null) {
	            aConnection.setAutoCommit(true);
	        }
	    }

	    return inserted;
	}



   /**
    * Retrieves a Student record from the database.
    * 
    * @param id The ID of the student to retrieve.
    * @return Student object populated with data.
    * @throws NotFoundException if the student cannot be found.
    */
   public static Student retrieve(long id) throws NotFoundException {
	    Student student = null;

	    String sqlRetrieve = "SELECT users.id, password, firstname, lastname, emailaddress, lastaccess, enroldate, type, enabled, programcode, programdescription, year FROM users INNER JOIN students ON users.id = students.id WHERE users.id = ?";

	    try {
	        PreparedStatement psRetrieve = aConnection.prepareStatement(sqlRetrieve);
	        psRetrieve.setLong(1, id);
	        ResultSet rs = psRetrieve.executeQuery();

	        if (rs.next()) {
	            long retrievedId = rs.getLong("id");
	            String password = rs.getString("password");
	            String firstName = rs.getString("firstname");
	            String lastName = rs.getString("lastname");
	            String emailAddress = rs.getString("emailaddress");
	            Timestamp lastAccess = rs.getTimestamp("lastaccess");
	            Date enrolDate = rs.getDate("enroldate");
	            char type = rs.getString("type").charAt(0);
	            boolean enabled = rs.getBoolean("enabled");
	            String programCode = rs.getString("programcode");
	            String programDescription = rs.getString("programdescription");
	            int year = rs.getInt("year");

	            try {
	                student = new Student(retrievedId, password, firstName, lastName, emailAddress, lastAccess, enrolDate, type, enabled, programCode, programDescription, year);
	            } catch (InvalidUserDataException e) {
	                System.err.println("Error creating student: " + e.getMessage());
	                throw new NotFoundException("Error creating student from retrieved data.");
	            }
	        } else {
	            throw new NotFoundException("Student with ID: " + id + " not found.");
	        }
	    } catch (SQLException sqlException) {
	        System.out.println(sqlException);
	    }
	    return student;
	}


   /**
    * Updates a Student record in the database.
    * 
    * @param student The Student object to update.
    * @return The number of records updated.
    * @throws NotFoundException if the student cannot be found.
    * @throws SQLException if a SQL error occurs.
    */
   public static boolean update(Student student) throws SQLException, InvalidUserDataException {
	    boolean updated = false;

	    try (Connection connection = DatabaseConnect.initialize()) {
	        boolean userUpdated = UserDA.update(new User(student.getId(), student.getPassword(), student.getFirstName(), student.getLastName(),
	                student.getEmailAddress(), student.getLastAccess(), student.getEnrolDate(), student.getType(), student.isEnabled()));

	        if (!userUpdated) {
	            throw new SQLException("Failed to update user part of the student.");
	        }

	        String sqlUpdateStudent = "UPDATE students SET programCode = ?, programDescription = ?, year = ? WHERE id = ?";
	        try (PreparedStatement psStudent = connection.prepareStatement(sqlUpdateStudent)) {
	            psStudent.setString(1, student.getProgramCode());
	            psStudent.setString(2, student.getProgramDescription());
	            psStudent.setInt(3, student.getYear());
	            psStudent.setLong(4, student.getId());

	            int studentResult = psStudent.executeUpdate();
	            if (studentResult > 0) {
	                updated = true;
	            } else {
	                throw new SQLException("Failed to update the student part.");
	            }
	        }
	    }
	    return updated;
	}



   /**
    * Deletes a Student record from the database.
    * 
    * @param student The Student object to delete.
    * @return The number of records deleted.
    * @throws NotFoundException if the student to delete does not exist.
    */
   public static int delete(Student student) throws NotFoundException {
       int recordsDeleted = 0;
       String sqlDeleteStudent = "DELETE FROM Students WHERE Id = ?";

       try (PreparedStatement psStudent = aConnection.prepareStatement(sqlDeleteStudent)) {
           psStudent.setLong(1, student.getId());
           recordsDeleted = psStudent.executeUpdate();
       } catch (SQLException e) {
           System.out.println(e);
       }
       return recordsDeleted;
   }

   
   
   /**
    * Authenticates a student.
    *
    * @param studentNumber Student's number.
    * @param password Student's password.
    * @return Authenticated Student object.
    * @throws NotFoundException if authentication fails.
    */
   public static Student authenticate(long studentNumber, String password) throws NotFoundException {
       String sqlAuthenticate = "SELECT * FROM Users INNER JOIN Students ON Users.Id = Students.Id WHERE Users.Id = ? AND Users.Password = ?";

       try {
           String hashedPassword = hashPassword(password);
           PreparedStatement psAuthenticate = aConnection.prepareStatement(sqlAuthenticate);
           psAuthenticate.setLong(1, studentNumber);
           psAuthenticate.setString(2, hashedPassword);
           ResultSet rs = psAuthenticate.executeQuery();

           if (rs.next()) {
               try {
                   Student student = new Student(
                       rs.getLong("Id"),
                       hashedPassword, 
                       rs.getString("FirstName"),
                       rs.getString("LastName"),
                       rs.getString("EmailAddress"),
                       rs.getDate("LastAccess"),
                       rs.getDate("EnrolDate"),
                       rs.getString("Type").charAt(0),
                       rs.getBoolean("Enabled"),
                       rs.getString("ProgramCode"),
                       rs.getString("ProgramDescription"),
                       rs.getInt("Year")
                   );
                   return student;
               } catch (InvalidUserDataException e) {
   
                   throw new NotFoundException("Failed to create student due to invalid data.");
               }
           } else {
               throw new NotFoundException("Authentication failed: Student not found or password incorrect.");
           }
       } catch (SQLException | NoSuchAlgorithmException e) {
          
           throw new RuntimeException("Database or hashing error during authentication.", e);
       }
   }
   
   public static void updateLastAccess(long id) throws SQLException {
       String sqlUpdateLastAccess = "UPDATE users SET lastaccess = CURRENT_TIMESTAMP WHERE id = ?";
       try (PreparedStatement ps = aConnection.prepareStatement(sqlUpdateLastAccess)) {
           ps.setLong(1, id);
           ps.executeUpdate();
       } catch (SQLException e) {
           System.out.println(e.getMessage());
           throw e;
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
   
   /**
    * Terminates the database connection.
    */
   public static void terminate() {
       try {
           if (aStatement != null) aStatement.close();
           if (aConnection != null) aConnection.close();
       } catch (SQLException e) {
           System.out.println(e);
       }
   }
}