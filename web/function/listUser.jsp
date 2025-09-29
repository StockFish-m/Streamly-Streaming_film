<%@ page import="java.util.*, model.user.Viewer, dao.UserDAOImpl" %>
<%
    UserDAOImpl dao = new UserDAOImpl();
    List<Viewer> viewers = dao.getAllViewers();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
</head>
<body>
    <h2>Viewer List</h2>
    <table border="1">
        <tr>
            <th>ID</th><th>Username</th><th>Fullname</th><th>Email</th><th>Phone</th><th>Actions</th>
        </tr>
        <%
            for (Viewer v : viewers) {
        %>
        <tr>
            <td><%= v.getUser_id() %></td>
            <td><%= v.getUsername() %></td>
            <td><%= v.getFullName() %></td>
            <td><%= v.getEmail() %></td>
            <td><%= v.getPhoneNumber() %></td>
            <td>
                <a href="editUser.jsp?id=<%= v.getUser_id() %>">Edit</a>
                
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>
