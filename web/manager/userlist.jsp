<%@ page import="java.util.*, model.user.Viewer, dao.UserDAOImpl" %>
<%
    UserDAOImpl dao = new UserDAOImpl();
    List<Viewer> viewers = dao.getAllViewers();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Viewer List</title>
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
                <td><%= v.getUser_id()%></td>
                <td><%= v.getUsername()%></td>
                <td><%= v.getFullName()%></td>
                <td><%= v.getEmail()%></td>
                <td><%= v.getPhoneNumber()%></td>
                <td>
                    <a href="userdetail.jsp?id=<%= v.getUser_id()%>">Edit</a>
                </td>
            </tr>
            <% }%>
        </table>

        <form action="${pageContext.request.contextPath}/manager/dashboard.jsp" method="get" style="margin-bottom: 20px;">
            <button type="submit" style="
                    background-color: #2196F3;
                    color: white;
                    padding: 10px 20px;
                    font-size: 14px;
                    border: none;
                    border-radius: 5px;
                    cursor: pointer;
                    ">
                Back to Dashboard
            </button>
        </form>

    </body>
</html>
