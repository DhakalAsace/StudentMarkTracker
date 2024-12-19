/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: Feb 16, 2024
 */
package inft2201.dhakala;

/**
 * Exception thrown when a requested record cannot be found in the database.
 */
@SuppressWarnings("serial")
public class NotFoundException extends Exception {
    /**
     * Default constructor without parameters.
     */
    public NotFoundException() {
        super("The requested record was not found.");
    }

    /**
     * Constructor that accepts a message for the exception.
     * @param message A string message detailing the cause of the exception.
     */
    public NotFoundException(String message) {
        super(message);
    }
}