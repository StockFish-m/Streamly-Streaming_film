<%@ page import="dao.UserDAOImpl, model.user.Viewer" %>
<%
    String idStr = request.getParameter("id");
    Viewer viewer = null;

    if (idStr != null) {
    try{
        int userId = Integer.parseInt(idStr);
        viewer = new UserDAOImpl().getViewer(userId);
    }catch(NumberFormatException e){
        out.println("Invalid ID");
    }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
</head>
<body>
    <h2>Edit Viewer Info</h2>

    <form action="${pageContext.request.contextPath}/manager/userlist" method="post">
    <input type="hidden" name="user_id" value="<%= viewer.getUser_id() %>">
    <p>Username: <input type="text" name="username" value="<%= viewer.getUsername() %>" readonly></p
    <p>Full Name: <input type="text" name="fullname" value="<%= viewer.getFullName() %>" required></p>
    <p>Email: <input type="email" name="email" value="<%= viewer.getEmail() %>" required></p>
    <p>Phone: <input type="text" name="phone" value="<%= viewer.getPhoneNumber() %>" required></p>

    <p><button type="submit">Update</button></p>
    </form>


    <p><a href="userlist.jsp">Back to list</a></p>
</body>
</html>
