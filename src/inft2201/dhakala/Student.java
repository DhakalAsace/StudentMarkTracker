
/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: January 23, 2024
 */
package inft2201.dhakala;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import java.util.Vector;

/**
 * Represents a student in the college system, extending the User class.
 * Includes specific attributes related to a student's academic program.
 */
public class Student extends User {
	/** The code of the academic program the student is enrolled in. */
	private String programCode;

	/** A description of the academic program the student is enrolled in. */
	private String programDescription;

	/** The current academic year of the student. */
	private int year;

	/** A list of marks the student has received in their courses. */
	private Vector<Mark> marks;

	/** Default program code assigned to a student. */
	public static final String DEFAULT_PROGRAM_CODE = "UNDC";

	/** Default description for a student's program. */
	public static final String DEFAULT_PROGRAM_DESCRIPTION = "Undeclared";

	/** Default academic year assigned to a student. */
	public static final int DEFAULT_YEAR = 1;
	
	/**
     * Initializes the database connection for Student data access.
     * @param c The database connection object.
     */
    public static void initialize(Connection c) {
        StudentDA.initialize(c);
    }
    
    /**
     * Terminates the database connection.
     */
    public static void terminate() {
        StudentDA.terminate();
    }

    /**
     * Retrieves a Student object from the database based on the student ID.
     * @param id The ID of the student to retrieve.
     * @return The Student object.
     * @throws NotFoundException If the student cannot be found in the database.
     */
    public static Student retrieve(long id) throws NotFoundException {
        return StudentDA.retrieve(id);
    }

    /**
     * Constructs a new Student with specified details.
     * 
     * @param id                 The student's ID.
     * @param password           The student's password.
     * @param firstName          The student's first name.
     * @param lastName           The student's last name.
     * @param emailAddress       The student's email address.
     * @param date         The last access timestamp of the student.
     * @param enrolDate          The enrolment date of the student.
     * @param type               The type character of the user.
     * @param enabled            The enabled status of the student.
     * @param programCode        The code of the program the student is enrolled in.
     * @param programDescription The description of the program.
     * @param year               The current year of study of the student.
     * @throws InvalidUserDataException If any of the data is not valid.
     */
    public Student(long id, String password, String firstName, String lastName, String emailAddress, Date date,
            Date enrolDate, char type, boolean enabled, String programCode, String programDescription, int year)
            throws InvalidUserDataException {
        super(id, password, firstName, lastName, emailAddress, date, enrolDate, type, enabled);
        
        setProgramCode(programCode);
        setProgramDescription(programDescription);
        setYear(year);
        marks = new Vector<>();
    }

    /**
     * Constructs a default Student with predefined constants.
     * 
     * @throws InvalidUserDataException If any of the default data is not valid.
     */
    public Student() throws InvalidUserDataException {
        this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, new Date(),
                new Date(), DEFAULT_TYPE, DEFAULT_ENABLED_STATUS, DEFAULT_PROGRAM_CODE, DEFAULT_PROGRAM_DESCRIPTION,
                DEFAULT_YEAR);
    }

    
    
	// Accessor and mutator methods for each private attribute

	/**
	 * Retrieves the program code.
	 * 
	 * @return The program code.
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * Sets the program code.
	 * 
	 * @param programCode The new program code.
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * Retrieves the program description.
	 * 
	 * @return The program description.
	 */
	public String getProgramDescription() {
		return programDescription;
	}

	/**
	 * Sets the program description.
	 * 
	 * @param programDescription The new program description.
	 */
	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}

	/**
	 * Retrieves the current year of study.
	 * 
	 * @return The current year of study.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the current year of study.
	 * 
	 * @param year The new year of study.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Retrieves the marks vector.
	 * 
	 * @return The vector of marks.
	 */
	public Vector<Mark> getMarks() {
		return marks;
	}

	/**
	 * Sets the marks vector.
	 * 
	 * @param marks The new vector of marks.
	 */
	public void setMarks(Vector<Mark> marks) {
		this.marks = marks;
	}


	/**
	 * Returns the type of user for display.
	 * 
	 * @return The type of user as "Student".
	 */
	@Override
	public String getTypeForDisplay() {
		return "Student";
	}

	/**
	 * Converts the student details to a string format for display.
	 * 
	 * @return A string representation of the student details.
	 */
	@Override
	public String toString() {
		String yearSuffix = (year == 1 ? "st" : (year == 2 ? "nd" : (year == 3 ? "rd" : "th")));
		return "Student Info for:\n" + getFirstName() + " " + getLastName() + " (" + getId() + ")\n" + "Currently in "
				+ year + yearSuffix + " year of \"" + programDescription + "\" (" + programCode + ")\n" + "Enrolled: "
				+ DF.format(getEnrolDate());
	}
	/**
	 * Creates a new student record in the database.
	 *
	 * @return true if creation is successful, false otherwise.
	 * @throws DuplicateException if a student with the same ID already exists.
	 * @throws InvalidUserDataException if the data provided for the student is invalid.
	 */
	@Override
	public boolean create() throws DuplicateException, InvalidUserDataException { 
	    try {
	        boolean success = StudentDA.create(this);
	        if (!success) {
	            throw new DuplicateException("Failed to create student.");
	        }
	        return true;
	    } catch (SQLException e) {
	        System.err.println("Error creating student: " + e.getMessage());
	        return false; 
	    }
	}

	/**
	 * Updates an existing student record in the database.
	 *
	 * @return true if the update is successful, false otherwise.
	 * @throws SQLException if a database error occurs.
	 * @throws InvalidUserDataException if the updated data is invalid.
	 */
	public boolean update() throws SQLException, InvalidUserDataException {
	    return StudentDA.update(this);
	}


	/**
	 * Deletes a student record from the database.
	 *
	 * @return true if the deletion is successful, false otherwise.
	 * @throws NotFoundException if the student to be deleted cannot be found.
	 */
	@Override
	public boolean delete() throws NotFoundException {
	    int deletedRows = StudentDA.delete(this);
		if (deletedRows == 0) {
		    throw new NotFoundException("Failed to delete student");
		}
		return true;
	}




    /**
     * Authenticates a student based on their student number and password.
     *
     * @param studentNumber The student's number.
     * @param password The student's password.
     * @return A Student object if authentication is successful.
     * @throws NotFoundException if no matching student is found or the password is incorrect.
     */
    public static Student authenticate(long studentNumber, String password) throws NotFoundException {
        return StudentDA.authenticate(studentNumber, password);
    }

}
