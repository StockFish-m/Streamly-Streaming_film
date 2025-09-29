
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.user.Viewer" %>  <%-- chỉ cần nếu bạn dùng Viewer --%>
<%@page import="model.user.User"%>

<%
    User user = (User) request.getAttribute("user");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= (user != null && user.getUser_id() > 0 ? "Edit User" : "Add User") %></title>

    <style>
        form { max-width: 500px; margin: auto; }
        input, button { width: 100%; padding: 10px; margin: 10px 0; }
        .error { color: red; font-weight: bold; text-align: center; }
        label { display: block; font-weight: bold; }
    </style>
</head>
<body>

<h2 style="text-align: center;"><%= (user != null && user.getUser_id() != 0 ? "Edit User" : "Add New User") %></h2>

<% if (error != null) { %>
    <p class="error"><%= error %></p>
<% } %>

<form action="<%= request.getContextPath() %>/function/listUser.jsp" method="post">


    <% if (user != null && user.getUser_id() != 0) { %>
        <input type="hidden" name="id" value="<%= user.getUser_id() %>">
    <% } %>

    <label>Username:</label>
    <input type="text" name="username" value="<%= (user != null ? user.getUsername() : "") %>" required>

    <label>Full Name:</label>
    <input type="text" name="fullname" value="<%= (user != null ? user.getFullName() : "") %>" required>

    <label>Email:</label>
    <input type="email" name="email" value="<%= (user != null ? user.getEmail() : "") %>" required>

    <label>Phone Number:</label>
    <input type="text" name="phone" value="<%= (user != null ? user.getPhoneNumber() : "") %>" required>

    <label>Password:</label>
    <input type="password" name="password" placeholder="<%= (user != null ? "Leave blank to keep current" : "") %>">

    <button type="submit"><%= (user != null && user.getUser_id() != 0 ? "Update" : "Edit") %></button>
    <a href="userlist">Cancel</a>
</form>

</body>
</html>
    