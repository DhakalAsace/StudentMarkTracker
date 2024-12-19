<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%
    request.setAttribute("pageTitle", "Register");
    // Directly use the implicit 'session' object without redeclaration.
    if(session != null && session.getAttribute("student") != null) {
        response.sendRedirect("dashboard.jsp");
        return;
    }
%>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Register</title>
    <style>
    body{
        font-family: Arial;
    }
    /* Add any specific styling you want here */
    </style>
    <script>
        function validateForm() {
            var id = document.forms["registrationForm"]["id"].value;
            var password = document.forms["registrationForm"]["password"].value;
            var firstName = document.forms["registrationForm"]["firstName"].value;
            var lastName = document.forms["registrationForm"]["lastName"].value;
            var emailAddress = document.forms["registrationForm"]["emailAddress"].value;
            var programCode = document.forms["registrationForm"]["programCode"].value;
            var programDescription = document.forms["registrationForm"]["programDescription"].value;
            var year = document.forms["registrationForm"]["year"].value;

            if (!id || !password || !firstName || !lastName || !emailAddress || !programCode || !programDescription || !year) {
                alert("All fields must be filled out");
                return false;
            }

            return true; // Form is valid
        }
    </script>
</head>
<body>
    <h1 style="text-align:center;">Register for the Student Portal</h1>
    <div style="text-align: center;">
        <form method="post" action="/student-mark-tracker/LoginServlet" onsubmit="return validateForm()">
            <table border="0" style="background-color: lightgoldenrodyellow;margin-left:auto; margin-right:auto;" cellpadding="10">
                <!-- Include fields for all user and student attributes except excluded ones -->
                <tr>
                    <td>Student ID:</td>
                    <td><input type="number" name="id" required></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password" required></td>
                </tr>
                <tr>
                    <td>First Name:</td>
                    <td><input type="text" name="firstName" required></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><input type="text" name="lastName" required></td>
                </tr>
                <tr>
                    <td>Email Address:</td>
                    <td><input type="email" name="emailAddress" required></td>
                </tr>
                <tr>
                    <td>Program Code:</td>
                    <td><input type="text" name="programCode" required></td>
                </tr>
                <tr>
                    <td>Program Description:</td>
                    <td><input type="text" name="programDescription" required></td>
                </tr>
                <tr>
                    <td>Year:</td>
                    <td>
                        <select name="year" required>
                            <option value="1">1st Year</option>
                            <option value="2">2nd Year</option>
                            <option value="3">3rd Year</option>
                            <option value="4">4th Year</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align:center;">
                        <input type="submit" value="Register">
                        <input type="reset" value="Clear">
                    </td>
                </tr>
            </table>
            <% if(request.getAttribute("debugInfo") != null) { %>
    <div style="background-color: #f2f2f2; padding: 10px; margin-top: 20px;">
        <h4>Debug Information:</h4>
        <ul>
            <% for(Map.Entry<String, String> entry : ((Map<String, String>)request.getAttribute("debugInfo")).entrySet()) { %>
                <li><strong><%= entry.getKey() %>:</strong> <%= entry.getValue() %></li>
            <% } %>
        </ul>
    </div>
<% } %>
            
        </form>
    </div>
</body>
</html>
<%@ include file="footer.jsp" %>
