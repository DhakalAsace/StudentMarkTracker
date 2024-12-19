/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: Feb 16, 2024
 */
package inft2201.dhakala;

/**
 * Exception thrown when an attempt is made to create a duplicate record in the database.
 */
@SuppressWarnings("serial")
public class DuplicateException extends Exception {
    /**
     * Default constructor without parameters.
     */
    public DuplicateException() {
        super("An attempt was made to create a duplicate record.");
    }

    /**
     * Constructor that accepts a message for the exception.
     * @param message A string message detailing the cause of the exception.
     */
    public DuplicateException(String message) {
        super(message);
    }
}
