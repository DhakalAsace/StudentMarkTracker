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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	
	 /**
     * Handles POST requests for user registration.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException      if an I/O error occurs while handling the request
     */
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        Map<String, String> debugInfo = new HashMap<>();

        // Retrieve form parameters
        String idStr = request.getParameter("id");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String emailAddress = request.getParameter("emailAddress");
        String programCode = request.getParameter("programCode");
        String programDescription = request.getParameter("programDescription");
        String yearStr = request.getParameter("year");

        long id=0;
        int year=0;

        try {
            id = Long.parseLong(idStr);
            year = Integer.parseInt(yearStr);
            password = hashPassword(password);
            debugInfo.put("PasswordHashed", "Password was successfully hashed.");
        } catch (NumberFormatException e) {
            errors.put("numberFormat", "Student ID and Year must be valid numbers.");
            returnWithError(request, response, errors);
        } catch (NoSuchAlgorithmException e) {
            errors.put("hashingError", "Error hashing the password.");
            returnWithError(request, response, errors);
        }

        if (password.isEmpty()) {
            errors.put("password", "Password is required.");
        }

        if (!errors.isEmpty()) {
            returnWithError(request, response, errors);
            return;
        }

        Date today = new Date(new java.util.Date().getTime());
        char type = 's';
        boolean enabled = true;

        try (Connection connection = DatabaseConnect.initialize()) {
            Student.initialize(connection);

            Student newStudent = new Student(id, password, firstName, lastName, emailAddress, today, today, type, enabled, programCode, programDescription, year);
            
            boolean inserted = newStudent.create();

            if (inserted) {
                HttpSession session = request.getSession();
                session.setAttribute("student", newStudent);
                response.sendRedirect("dashboard.jsp");
            } else {
                errors.put("insertFailed", "Failed to insert student record.");
                returnWithError(request, response, errors);
            }
        } catch (Exception e) {
            debugInfo.put("Exception", e.getMessage());
            errors.put("databaseError", "An error occurred accessing the database.");
            returnWithError(request, response, errors);
            e.printStackTrace(); 
        } finally {
            Student.terminate(); 
            DatabaseConnect.terminate();
            debugInfo.put("DBTermination", "Database connection terminated.");
            if (!response.isCommitted()) {
                request.setAttribute("debugInfo", debugInfo);
                returnWithError(request, response, errors);
            }
        }

    }
    /**
     * Redirects to the registration page with error messages.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @param errors   Map containing error messages
     * @throws ServletException if an error occurs during servlet processing
     * @throws IOException      if an I/O error occurs while handling the request
     */
    
    private void returnWithError(HttpServletRequest request, HttpServletResponse response, Map<String, String> errors) throws ServletException, IOException {
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    /**
     * Hashes the provided password using SHA-1 algorithm.
     *
     * @param password the password to hash
     * @return the hashed password
     * @throws NoSuchAlgorithmException if the SHA-1 algorithm is not available
     */
    
    static String hashPassword(String password) throws NoSuchAlgorithmException {
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