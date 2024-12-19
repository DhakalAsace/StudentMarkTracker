/**
 * 
 * @author Ashesh Dhakal
 * @version 1.0
 * @since 1.0
 * date: January 23, 2024
 */
package inft2201.dhakala;

import java.text.DecimalFormat;

/**
 * Represents a mark record for a course, including course code, name, result, and GPA weighting.
 */
public class Mark {
    /** Course code for which the mark is recorded. */
    private String courseCode;

    /** Name of the course. */
    private String courseName;

    /** Numeric result (score) for the course. */
    private int result;

    /** GPA weighting for the course. */
    private float gpaWeighting;

    /** Minimum GPA value. */
    public static final float MINIMUM_GPA = 0.0f;

    /** Maximum GPA value. */
    public static final float MAXIMUM_GPA = 5.0f;

    /** Formatter for displaying GPA values. */
    public static final DecimalFormat GPA = new DecimalFormat("#.0");


    /**
     * Constructs a new Mark object with specified course details.
     * @param courseCode The code of the course.
     * @param courseName The name of the course.
     * @param result The result/mark obtained in the course.
     * @param gpaWeighting The GPA weighting of the course.
     */
    public Mark(String courseCode, String courseName, int result, float gpaWeighting) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.result = result;
        this.gpaWeighting = gpaWeighting;
    }

    /**
     * Retrieves the course code.
     * @return The course code.
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the course code.
     * @param courseCode The new course code.
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Retrieves the course name.
     * @return The course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name.
     * @param courseName The new course name.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Retrieves the result/mark for the course.
     * @return The result/mark.
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets the result/mark for the course.
     * @param result The new result/mark.
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * Retrieves the GPA weighting for the course.
     * @return The GPA weighting.
     */
    public float getGpaWeighting() {
        return gpaWeighting;
    }

    /**
     * Sets the GPA weighting for the course.
     * @param gpaWeighting The new GPA weighting.
     */
    public void setGpaWeighting(float gpaWeighting) {
        this.gpaWeighting = gpaWeighting;
    }

    /**
     * Converts the Mark object details into a formatted string.
     * @return A string representation of the Mark object.
     */
    @Override
    public String toString() {
        return String.format("%-10s %-35s %3d %6s", 
        		courseCode, 
        		courseName, 
        		result, 
        		GPA.format(gpaWeighting));
    }

}
