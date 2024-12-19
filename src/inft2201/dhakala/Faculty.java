package inft2201.dhakala;

import java.sql.Connection;
import java.util.Date;

public class Faculty extends User {
    private String schoolCode;
    private String schoolDescription;
    private String office;
    private int extension;

    public static final String DEFAULT_SCHOOL_CODE = "SET";
    public static final String DEFAULT_SCHOOL_DESCRIPTION = "School of Engineering & Technology";
    public static final String DEFAULT_OFFICE = "H-140";
    public static final int DEFAULT_PHONE_EXTENSION = 1234;

    public Faculty(long id, String password, String firstName, String lastName, String emailAddress, Date lastAccess,
                   Date enrolDate, char type, boolean enabled, String schoolCode, String schoolDescription, String office,
                   int extension) throws InvalidUserDataException {
        super(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, type, enabled);
        this.schoolCode = schoolCode;
        this.schoolDescription = schoolDescription;
        this.office = office;
        this.extension = extension;
    }

    public Faculty() throws InvalidUserDataException {
        this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, new Date(),
                new Date(), DEFAULT_TYPE, DEFAULT_ENABLED_STATUS, DEFAULT_SCHOOL_CODE, DEFAULT_SCHOOL_DESCRIPTION,
                DEFAULT_OFFICE, DEFAULT_PHONE_EXTENSION);
    }

    // Accessor and mutator methods

    // Overriding User class methods as needed to handle exceptions
    // and interact with the FacultyDA for database operations.

    @Override
    public boolean create() throws DuplicateException {
        try {
            return FacultyDA.create(this);
        } catch (InvalidUserDataException e) {
            throw new RuntimeException("Creating faculty failed: " + e.getMessage());
        }
    }

    @Override
    public boolean update() throws NotFoundException {
        try {
            int rowsAffected = FacultyDA.update(this);
            return rowsAffected > 0;
        } catch (InvalidUserDataException e) {
            throw new RuntimeException("Updating faculty failed: " + e.getMessage());
        }
    }

    @Override
    public boolean delete() throws NotFoundException {
        try {
            int rowsAffected = FacultyDA.delete(this.getId());
            return rowsAffected > 0;
        } catch (InvalidUserDataException e) {
            throw new RuntimeException("Deleting faculty failed: " + e.getMessage());
        }
    }

    public static Faculty retrieve(long id) throws NotFoundException {
        try {
            return FacultyDA.retrieve(id);
        } catch (InvalidUserDataException e) {
            throw new RuntimeException("Retrieving faculty failed: " + e.getMessage());
        }
    }

    // Getters and Setters for Faculty-specific fields
    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolDescription() {
        return schoolDescription;
    }

    public void setSchoolDescription(String schoolDescription) {
        this.schoolDescription = schoolDescription;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }

    @Override
    public String getTypeForDisplay() {
        return "Faculty";
    }

    @Override
    public String toString() {
        return super.toString() + "\nSchool Code: " + schoolCode + "\nSchool Description: " + schoolDescription + "\nOffice: " + office + "\nExtension: " + extension;
    }
}
