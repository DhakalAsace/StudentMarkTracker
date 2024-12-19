package inft2201.dhakala;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	/**
     * Handles POST requests for updating student profile.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException      if an I/O error occurs while handling the request
     */
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Student student = (Student) session.getAttribute("student");
        Map<String, String> debugInfo = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        // Extract parameters from the form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailAddress = request.getParameter("emailAddress");
        String programCode = request.getParameter("programCode");
        String programDescription = request.getParameter("programDescription");
        String yearStr = request.getParameter("year");
        String password = request.getParameter("password");

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            errors.put("yearFormat", "Year must be a number.");
            debugInfo.put("YearError", "Invalid year format provided.");
            returnWithError(request, response, errors, debugInfo);
            return;
        }

        // Conditionally hash new password if provided
        if (password != null && !password.isEmpty()) {
            try {
                password = hashPassword(password); 
                debugInfo.put("PasswordHash", "Password hashed successfully.");
            } catch (NoSuchAlgorithmException e) {
                errors.put("hashError", "Error hashing password.");
                debugInfo.put("HashException", e.getMessage());
                returnWithError(request, response, errors, debugInfo);
                return;
            }
        } else {
            password = student.getPassword(); 
        }

        // Set new values to the student object
        try {
			student.setFirstName(firstName);
		} catch (InvalidNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			student.setLastName(lastName);
		} catch (InvalidNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        student.setEmailAddress(emailAddress);
        student.setProgramCode(programCode);
        student.setProgramDescription(programDescription);
        student.setYear(year);
        try {
			student.setPassword(password);
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Assuming password is directly settable

        try {
            if (student.update()) {
                debugInfo.put("UpdateSuccess", "Student information updated successfully.");
                session.setAttribute("student", student); // Refresh session's student object
                session.setAttribute("notification", "Profile updated successfully.");
                response.sendRedirect("dashboard.jsp");
            } else {
                errors.put("updateError", "Failed to update profile.");
                debugInfo.put("UpdateFailure", "Update operation failed without exception.");
                returnWithError(request, response, errors, debugInfo);
            }
        } catch (Exception e) {
            errors.put("exception", "Unexpected error occurred: " + e.getMessage());
            debugInfo.put("ExceptionDetails", e.toString());
            returnWithError(request, response, errors, debugInfo);
        }
    }
    
    /**
     * Redirects to the update page with error messages and debug information.
     *
     * @param request   HttpServletRequest object
     * @param response  HttpServletResponse object
     * @param errors    Map containing error messages
     * @param debugInfo Map containing debug information
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException      if an I/O error occurs while handling the request
     */
    
    private void returnWithError(HttpServletRequest request, HttpServletResponse response, Map<String, String> errors, Map<String, String> debugInfo) throws ServletException, IOException {
        request.setAttribute("errors", errors);
        request.setAttribute("debugInfo", debugInfo);
        request.getRequestDispatcher("/update.jsp").forward(request, response);
    }
    /**
     * Hashes the provided password using SHA-1 algorithm.
     *
     * @param password the password to hash
     * @return the hashed password
     * @throws NoSuchAlgorithmException if the SHA-1 algorithm is not available
     */
    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
    	MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Handles GET requests by redirecting them to doPost.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException      if an I/O error occurs while handling the request
     */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
