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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a generic user in the college system. This class serves as a base
 * for specific types of users like students or faculty.
 */
public class User implements CollegeInterface {
	/** Default user ID. */
	public static final long DEFAULT_ID = 100123456L;

	/** Default user password. */
	public static final String DEFAULT_PASSWORD = "password";

	/** Minimum length for user passwords. */
	public static final byte MINIMUM_PASSWORD_LENGTH = 8;

	/** Maximum length for user passwords. */
	public static final byte MAXIMUM_PASSWORD_LENGTH = 40;

	/** Default first name for a user. */
	public static final String DEFAULT_FIRST_NAME = "John";

	/** Default last name for a user. */
	public static final String DEFAULT_LAST_NAME = "Doe";

	/** Default email address for a user. */
	public static final String DEFAULT_EMAIL_ADDRESS = "john.doe@dcmail.com";

	/** Default status indicating whether the user account is enabled. */
	public static final boolean DEFAULT_ENABLED_STATUS = true;

	/** Default type of user (e.g., 's' for student, 'f' for faculty). */
	public static final char DEFAULT_TYPE = 's';

	/** Required length for a user ID. */
	public static final byte ID_NUMBER_LENGTH = 9;

	/** Date format for displaying dates. */
	public static final DateFormat DF = new SimpleDateFormat("dd-MMM-yyyy", Locale.CANADA);

	/** Unique identifier for the user. */
	private long id;

	/** User's account password. */
	private String password;

	/** User's first name. */
	private String firstName;

	/** User's last name. */
	private String lastName;

	/** User's email address. */
	private String emailAddress;

	/** Date of user's last access to the system. */
	private Date lastAccess;

	/** Date of user's enrolment in the college system. */
	private Date enrolDate;

	/** Flag indicating whether the user is active. */
	private boolean enabled;

	/** Character representing the type of user. */
	private char type;

	 /**
     * Default constructor initializing a user with default values.
     *
     * @throws InvalidUserDataException If the default data is not valid.
     */
    public User() throws InvalidUserDataException {
        this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS,
                new Date(), new Date(), DEFAULT_TYPE, DEFAULT_ENABLED_STATUS);
    }


    /**
     * Constructs a user with specified details.
     *
     * @param id           The user's ID.
     * @param password     The user's password.
     * @param firstName    The user's first name.
     * @param lastName     The user's last name.
     * @param emailAddress The user's email address.
     * @param lastAccess   The last access date of the user.
     * @param enrolDate    The enrolment date of the user.
     * @param type         The type character of the user.
     * @param enabled      The enabled status of the user.
     * @throws InvalidUserDataException If any of the data is not valid.
     */
    public User(long id, String password, String firstName, String lastName, String emailAddress, Date lastAccess,
                Date enrolDate, char type, boolean enabled) throws InvalidUserDataException {
        try {
            setId(id);
            setPassword(password);
            setFirstName(firstName);
            setLastName(lastName);
            setEmailAddress(emailAddress);
            setLastAccess(lastAccess);
            setEnrolDate(enrolDate);
            setType(type);
            setEnabled(enabled);
        } catch (InvalidIdException | InvalidPasswordException | InvalidNameException e) {
            throw new InvalidUserDataException("Invalid user data: " + e.getMessage());
        }
    }
    /**
     * Initializes the User data access layer.
     * 
     * @param c Connection object for database connectivity
     */
    public static void initialize(Connection c) {
        UserDA.initialize(c);
    }

    /**
     * Terminates the User data access layer.
     */
    public static void terminate() {
        UserDA.terminate();
    }

    /**
     * Creates a new user.
     * 
     * @return true if user creation is successful
     * @throws InvalidUserDataException if user data is invalid
     * @throws DuplicateException if user already exists
     */
    public boolean create() throws InvalidUserDataException, DuplicateException {
        boolean success = UserDA.create(this);
        if (!success) {
            throw new InvalidUserDataException("Failed to create user.");
        }
        return true;
    }

    /**
     * Updates user information.
     * 
     * @return true if user update is successful
     * @throws SQLException if a database access error occurs
     * @throws InvalidUserDataException if user data is invalid
     */
    public boolean update() throws SQLException, InvalidUserDataException {
        return UserDA.update(this);
    }

    /**
     * Deletes a user.
     * 
     * @return true if user deletion is successful
     * @throws NotFoundException if user is not found
     */
    public boolean delete() throws NotFoundException {
        boolean success = UserDA.delete(this);
        if (!success) {
            throw new NotFoundException("Failed to delete user. User not found.");
        }
        return true;
    }

    /**
     * Retrieves a user by ID.
     * 
     * @param id ID of the user to retrieve
     * @return User object corresponding to the provided ID
     * @throws NotFoundException if user is not found
     */
    public static User retrieve(long id) throws NotFoundException {
        return UserDA.retrieve(id);
    }

    
	/**
	 * Sets the ID for the user.
	 * 
	 * @param id The ID to set for the user.
	 * @throws InvalidIdException If the ID is not valid.
	 */
	public void setId(long id) throws InvalidIdException {
		if (id < Math.pow(10, ID_NUMBER_LENGTH - 1) || id >= Math.pow(10, ID_NUMBER_LENGTH)) {
			throw new InvalidIdException("Your Banner ID must be 9 numbers long.");
		}
		this.id = id;
	}

	/**
	 * Sets the password for the user.
	 * 
	 * @param password The password to set for the user.
	 * @throws InvalidPasswordException If the password is not valid.
	 */
	public void setPassword(String password) throws InvalidPasswordException {
		if (password.length() < MINIMUM_PASSWORD_LENGTH || password.length() > MAXIMUM_PASSWORD_LENGTH) {
			throw new InvalidPasswordException(
					"Your password must be at least 8 characters long and no more than 40 characters long.");
		}
		this.password = password;
	}

	/**
	 * Sets the first name for the user.
	 * 
	 * @param firstName The first name to set for the user.
	 * @throws InvalidNameException If the first name is not valid.
	 */
	public void setFirstName(String firstName) throws InvalidNameException {
		if (firstName == null || firstName.trim().isEmpty() || firstName.matches(".*\\d.*")) {
			throw new InvalidNameException("First name cannot be empty or contain numbers.");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the last name for the user.
	 * 
	 * @param lastName The last name to set for the user.
	 * @throws InvalidNameException If the last name is not valid.
	 */
	public void setLastName(String lastName) throws InvalidNameException {
		if (lastName == null || lastName.trim().isEmpty() || lastName.matches(".*\\d.*")) {
			throw new InvalidNameException("Last name cannot be empty or contain numbers.");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the email address for the user.
	 * 
	 * @param emailAddress The email address to set for the user.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Sets the last access date for the user.
	 * 
	 * @param lastAccess The date of last access to set for the user.
	 */
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	/**
	 * Sets the enrolment date for the user.
	 * 
	 * @param enrolDate The enrolment date to set for the user.
	 */
	public void setEnrolDate(Date enrolDate) {
		this.enrolDate = enrolDate;
	}

	/**
	 * Sets the enabled status for the user.
	 * 
	 * @param enabled The enabled status to set for the user.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Sets the type of the user.
	 * 
	 * @param type The type to set for the user.
	 */
	public void setType(char type) {
		this.type = type;
	}

	// Getters

	/**
	 * Gets the user's ID.
	 * 
	 * @return The ID of the user.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the user's password.
	 * 
	 * @return The password of the user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the user's first name.
	 * 
	 * @return The first name of the user.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the user's last name.
	 * 
	 * @return The last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the user's email address.
	 * 
	 * @return The email address of the user.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Gets the user's last access date.
	 * 
	 * @return The date of last access of the user.
	 */
	public Date getLastAccess() {
		return lastAccess;
	}

	/**
	 * Gets the user's enrolment date.
	 * 
	 * @return The enrolment date of the user.
	 */
	public Date getEnrolDate() {
		return enrolDate;
	}

	/**
	 * Checks if the user is enabled.
	 * 
	 * @return True if the user is enabled, false otherwise.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Gets the user's type.
	 * 
	 * @return The type of the user.
	 */
	public char getType() {
		return type;
	}

	/**
	 * Returns a string representation of the user with all details.
	 * 
	 * @return A string representation of the user.
	 */
	 @Override
	    public String toString() {
	        return "User Info for: " + id + "\n" + "Name: " + firstName + " " + lastName + " (" + emailAddress + ")\n"
	                + "Created on: " + DF.format(enrolDate) + "\n" + "Last access: " + DF.format(lastAccess);
	    }

	/**
	 * Prints the user's details to the console.
	 */
	public void dump() {
		System.out.println(this.toString());
	}

	/**
	 * Verifies if the given ID is valid based on length.
	 * 
	 * @param id The ID to verify.
	 * @return True if the ID is valid, false otherwise.
	 */
	public static boolean verifyId(long id) {
		return (id >= Math.pow(10, ID_NUMBER_LENGTH - 1) && id < Math.pow(10, ID_NUMBER_LENGTH));
	}

	/**
	 * Returns the type of user for display purposes.
	 * 
	 * @return The type of user as a string.
	 */
	@Override
	public String getTypeForDisplay() {
		return "User";
	}
}
