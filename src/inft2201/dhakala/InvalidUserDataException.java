/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: January 23, 2024
 */

package inft2201.dhakala;

/**
 * Exception for handling general invalid user data scenarios.
 * This exception is a general-purpose exception used when specific user data
 * does not meet the required criteria.
 */
public class InvalidUserDataException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new InvalidUserDataException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public InvalidUserDataException(String message) {
        super(message);
    }
}
