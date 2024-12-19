package inft2201.dhakala;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A servlet that handles user logout requests.
 */
@SuppressWarnings("serial")
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request for user logout.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate the current session
        HttpSession session = request.getSession();
        session.invalidate();

        // Create a new session and set a logout message
        HttpSession newSession = request.getSession();
        newSession.setAttribute("logoutMessage", "You have successfully logged out.");

        // Redirect to the login page
        response.sendRedirect("login.jsp");
    }

    /**
     * Handles the HTTP POST request for user logout by forwarding it to the doGet method.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}