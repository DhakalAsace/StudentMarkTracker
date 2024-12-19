/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: January 23, 2024
 */

package inft2201.dhakala;

/**
 * Custom exception class for handling invalid password scenarios.
 */
@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception {

    /**
     * Constructs a new InvalidPasswordException with the specified detail message.
     * 
     * @param message The detail message explaining the reason for the exception.
     */
    public InvalidPasswordException(String message) {
        super("There was a problem creating this user: " + message);
    }
}
