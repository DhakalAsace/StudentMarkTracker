/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: January 23, 2024
 */
package inft2201.dhakala;

/**
 * Exception for handling invalid ID scenarios.
 * This exception is thrown when an ID does not meet the required criteria,
 * such as being the incorrect length or format.
 */
public class InvalidIdException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new InvalidIdException with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidIdException(String message) {
        super("Your Banner ID must be 9 numbers long.");
    }
}
