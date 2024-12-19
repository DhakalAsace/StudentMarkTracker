/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: January 23, 2024
 */

package inft2201.dhakala;

/**
 * Exception for handling invalid name scenarios.
 * This exception is thrown when a name does not meet the required criteria,
 * such as containing numbers or being empty.
 */
public class InvalidNameException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new InvalidNameException with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidNameException(String message) {
        super("You may not have a blank first name or a name containing numbers.");
    }
}

