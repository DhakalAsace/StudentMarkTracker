<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="inft2201.dhakala.*" %>
<%
    Student student = (Student) session.getAttribute("student");
    request.setAttribute("pageTitle", "Dashboard");
%>
<%@ include file="header.jsp" %>
<div style="text-align:center;">
    <h2>Dashboard</h2>
    <% if(student != null) { %>
        <p>Welcome back, <%= student.getFirstName() %> <%= student.getLastName() %>!</p>
        <p>Your last access was on: <%= new SimpleDateFormat("yyyy-MM-dd").format(student.getLastAccess()) %></p>
    <% } else { %>
        <p>Please <a href="login.jsp">log in</a> to view your dashboard.</p>
    <% } %>
    
    <% if (session.getAttribute("updateMessage") != null) { %>
    <div style="color: green;"><%= session.getAttribute("updateMessage") %></div>
    <% session.removeAttribute("updateMessage"); // Remove the attribute after displaying %>
<% } %>
    
</div>
<%@ include file="footer.jsp" %>