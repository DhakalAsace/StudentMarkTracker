/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: January 23, 2024
 */

package inft2201.dhakala;

/**
 * Interface representing basic college information.
 */
public interface CollegeInterface {

    /**
     * Name of the college.
     */
    String COLLEGE_NAME = "Durham College";

    
    /**
     * Contact phone number of the college.
     */
    String PHONE_NUMBER = "(905)721-2000";

    /**
     * Method to get the displayable type of a college.
     * @return A string representing the type.
     */
    String getTypeForDisplay();
}
