package inft2201.dhakala;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A servlet that handles user login requests.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    /**
     * Handles the HTTP POST request for user login.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentNumber = request.getParameter("studentNumber");
        String password = request.getParameter("password");

        // Check if both student number and password are provided
        if (studentNumber == null || studentNumber.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Please enter both student number and password.");
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Initialize the database connection and Student class
            Connection connection = DatabaseConnect.initialize();
            Student.initialize(connection);

            // Parse the student number and authenticate the user
            long studentNum = Long.parseLong(studentNumber);
            Student student = Student.authenticate(studentNum, password);

            try {
                // Update the last access time for the authenticated student
                StudentDA.updateLastAccess(student.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Store the authenticated student in the session
            HttpSession session = request.getSession();
            session.setAttribute("student", student);
            response.sendRedirect("dashboard.jsp");

        } catch (NumberFormatException e) {
            // Handle invalid student number format
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Invalid student number format.");
            response.sendRedirect("login.jsp");
        } catch (NotFoundException e) {
            // Handle incorrect login information
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Your login information is incorrect. Please try again.");
            response.sendRedirect("login.jsp");
        } finally {
            // Terminate the database connection
            DatabaseConnect.terminate();
        }
    }

    /**
     * Handles the HTTP GET request for user login by forwarding it to the doPost method.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}