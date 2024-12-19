<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, inft2201.dhakala.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><%= request.getAttribute("pageTitle") %></title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        header h1 { color: red; }
        nav ul { list-style-type: none; padding: 0; }
        nav ul li { display: inline; margin-right: 10px; }
        a { text-decoration: none; color: darkblue; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <header>
        <h1><%= request.getAttribute("pageTitle") %></h1>
        <nav>
            <ul>
                <% if (request.getSession().getAttribute("student") == null) { %>
                    <li><a href="login.jsp">Login</a></li>
                    <li><a href="register.jsp">Register</a></li>
                <% } else { %>
                    <li><a href="dashboard.jsp">Dashboard</a></li>
                    <li><a href="update.jsp">Update Profile</a></li>
                    <li><a href="LogoutServlet">Logout</a></li>
                <% } %>
            </ul>
        </nav>
    </header>
