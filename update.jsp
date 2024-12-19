<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ include file="header.jsp" %>

<%
    // Check if the user is logged in
    Student student = (Student) session.getAttribute("student");
    if (student == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<html>
<head>
    <title>Update Profile</title>
    <style>
        body {
            font-family: Arial;
        }
    </style>
    <script>
        function togglePasswordField() {
            var passwordField = document.getElementById("passwordField");
            var updatePasswordCheckbox = document.getElementById("updatePassword");

            if (updatePasswordCheckbox.checked) {
                passwordField.disabled = false;
            } else {
                passwordField.disabled = true;
                passwordField.value = "";
            }
        }
    </script>
</head>
<body>
<h1 style="text-align:center;">Update Profile</h1>
<div style="text-align: center;">
    <form name="updateForm" action="<%=request.getContextPath()%>/UpdateServlet" onsubmit="return validateForm()" method="post">
        <table border="0" style="background-color: lightgoldenrodyellow;margin-left:auto; margin-right:auto;" cellpadding="10">
            <tr>
                <td>Student ID:</td>
                <td><input type="number" name="id" value="<%= student.getId() %>" readonly></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><input type="text" name="firstName" value="<%= student.getFirstName() %>" required></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" name="lastName" value="<%= student.getLastName() %>" required></td>
            </tr>
            <tr>
                <td>Email Address:</td>
                <td><input type="email" name="emailAddress" value="<%= student.getEmailAddress() %>" required></td>
            </tr>
            <tr>
                <td>Program Code:</td>
                <td><input type="text" name="programCode" value="<%= student.getProgramCode() %>" required></td>
            </tr>
            <tr>
                <td>Program Description:</td>
                <td><input type="text" name="programDescription" value="<%= student.getProgramDescription() %>" required></td>
            </tr>
            <tr>
                <td>Year:</td>
                <td>
                    <select name="year" required>
                        <option value="1" <%= student.getYear() == 1 ? "selected" : "" %>>1st Year</option>
                        <option value="2" <%= student.getYear() == 2 ? "selected" : "" %>>2nd Year</option>
                        <option value="3" <%= student.getYear() == 3 ? "selected" : "" %>>3rd Year</option>
                        <option value="4" <%= student.getYear() == 4 ? "selected" : "" %>>4th Year</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Update Password:</td>
                <td>
                    <input type="checkbox" id="updatePassword" onclick="togglePasswordField()">
                    <input type="password" id="passwordField" name="password" disabled>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center;">
                    <input type="submit" value="Update">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
<%@ include file="footer.jsp" %>