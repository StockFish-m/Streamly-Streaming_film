<%-- 
    Document   : adminlogin
    Created on : Jul 15, 2025, 4:48:37 PM
    Author     : MinhooMinh
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
</head>
<body>
    <h2>Đăng nhập quản trị</h2>

    <%
        String error = (String) request.getAttribute("error");
        String enteredUsername = (String) request.getAttribute("enteredUsername");
        if (enteredUsername == null) enteredUsername = "";
        if (error != null) {
    %>
        <div style="color: red;"><%= error %></div>
    <%
        }
    %>

    <form action="adminlogin" method="post">
        Username: <input type="text" name="username" value="<%= enteredUsername %>" required /><br/>
        Password: <input type="password" name="password" required /><br/>
        <input type="submit" value="Login" />
    </form>
</body>
</html>
