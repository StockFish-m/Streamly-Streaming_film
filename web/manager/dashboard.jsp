<%-- 
    Document   : dashboard
    Created on : Jul 16, 2025, 4:37:04 PM
    Author     : DELL
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 50px;
            text-align: center;
        }

        .btn {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 12px 24px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 8px;
            margin: 20px;
        }

        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <h1>📊 Admin Dashboard</h1>

    <form action="${pageContext.request.contextPath}/manager/contentManager" method="get">
        <button type="submit" class="btn">Manage Content</button>
    </form>
    <form action="${pageContext.request.contextPath}/manager/userlist.jsp" method="get">
        <button type="submit" class="btn">View Users</button>
    </form>

</body>
</html>

