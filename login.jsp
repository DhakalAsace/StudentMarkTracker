<%@ page import="java.util.*" %>
<%
    request.setAttribute("pageTitle", "Login");
%>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Log In</title>
    <style>
    body{
        font-family: Arial;
    }
    </style>
</head>
<body>
    <% 
        String errorMessage = (String)session.getAttribute("errorMessage");
        if(errorMessage == null) errorMessage = "";
        String studentNumber = (String)session.getAttribute("studentNumber");
        if(studentNumber == null) studentNumber = "";
    %>
    <h1 style="text-align:center;"><span style="color: red;">Student Portal Login</span></h1>
    <hr/>
    <div style="text-align: center;">
        <h2>Please log in</h2>
        <p>Enter your login information below.<br>
           If you are not yet registered, please <a href="register.jsp">register here</a>.</p>
           <form method="post" action="/inft2201-tomcat/LoginServlet">
            <table border="0" style="background-color: lightgoldenrodyellow;margin-left:auto; margin-right:auto;" cellpadding="10" >
                <tr>
                    <td colspan="2" align="center" style="color: red;"><%= errorMessage %></td>
                </tr>
                <tr>
                    <td><strong>Student Number</strong></td>
                    <td><input type="text" name="studentNumber" value="<%= studentNumber %>" size="20"></td>
                </tr>
                <tr>
                    <td><strong>Password</strong></td>
                    <td><input type="password" name="password" value="" size="20"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align:center;">
                        <input type="submit" value="Log In">
                        <input type="reset" value="Clear">
                    </td>
                </tr>
            </table>
        </form>
        Please wait after pressing <strong>Log in</strong> while we retrieve your records from our database.<br>
        <em>(This may take a few moments)</em>
    </div>
</body>
</html>
<%@ include file="footer.jsp" %>
