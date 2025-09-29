<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.user.EmployeeRole" %>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f2f5;
        }
        .login-container {
            width: 400px;
            margin: 100px auto;
            background: white;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
            border-radius: 10px;
        }
        h2 {
            text-align: center;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label, input, select {
            display: block;
            width: 100%;
        }
        input, select {
            padding: 10px;
            font-size: 16px;
        }
        .error {
            color: red;
            margin-bottom: 10px;
            text-align: center;
        }
        button {
            width: 100%;
            padding: 10px;
            background: #007bff;
            color: white;
            font-size: 16px;
            border: none;
            border-radius: 5px;
        }
        button:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Employee Login</h2>

        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>

        <form action="employeelogin" method="post">
            <div class="form-group">
                <label for="username">Username or Email</label>
                <input type="text" id="username" name="username" required />
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required />
            </div>

            <div class="form-group">
                <label for="role">Select Role</label>
                <select id="role" name="roleId" required>
                    <% for (EmployeeRole role : EmployeeRole.values()) { %>
                        <option value="<%= role.getId() %>"><%= role.getDescription() %></option>
                    <% } %>
                </select>
            </div>

            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html>
