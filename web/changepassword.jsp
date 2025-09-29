<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.user.Viewer" %>
<%
    Viewer currentUser = (Viewer) session.getAttribute("user");
    Viewer resetUser = (Viewer) session.getAttribute("reset_user");
    boolean isReset = (resetUser != null && currentUser == null);
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= isReset ? "Reset Password" : "Change Password" %></title>
    <meta charset="UTF-8">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            background: url('img/netflix-bg.jpg') no-repeat center center fixed;
            background-size: cover;
            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
            color: #000;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background-color: white;
            padding: 40px;
            width: 100%;
            max-width: 450px;
            border-radius: 4px;
            box-shadow: 0 0 15px rgba(0,0,0,0.5);
        }

        h2 {
            font-size: 24px;
            font-weight: 700;
            margin-bottom: 20px;
        }

        p {
            margin-bottom: 20px;
            font-size: 16px;
            color: #333;
        }

        label {
            display: block;
            font-weight: 500;
            margin-bottom: 8px;
        }

        input[type="password"],
        input[type="text"],
        input[type="email"] {
            width: 100%;
            padding: 12px 10px;
            margin-bottom: 20px;
            border: 1px solid #aaa;
            border-radius: 4px;
            font-size: 14px;
        }

        input[type="submit"] {
            background-color: #e50914;
            color: white;
            border: none;
            padding: 12px;
            width: 100%;
            font-size: 16px;
            font-weight: bold;
            border-radius: 4px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #f40612;
        }

        .error {
            color: #e50914;
            margin-bottom: 10px;
            text-align: center;
        }

        .success {
            color: #4caf50;
            margin-bottom: 10px;
            text-align: center;
        }

        .footer-link {
            font-size: 14px;
            margin-top: 10px;
            text-align: center;
        }

        .footer-link a {
            color: #333;
            text-decoration: underline;
        }

        @media (max-width: 600px) {
            .container {
                margin: 20px;
                padding: 25px;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <h2><%= isReset ? "Reset your password" : "Change your password" %></h2>

    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <% if (request.getAttribute("message") != null) { %>
        <p class="success"><%= request.getAttribute("message") %></p>
    <% } %>

    <form method="post" action="changepassword">
        <% if (!isReset) { %>
            <label for="old_password">Old Password</label>
            <input type="password" name="old_password" id="old_password" required>
        <% } %>

        <label for="new_password">New Password</label>
        <input type="password" name="new_password" id="new_password" required>

        <label for="confirm_password">Confirm New Password</label>
        <input type="password" name="confirm_password" id="confirm_password" required>

        <input type="submit" value="<%= isReset ? "Reset Password" : "Change Password" %>">
    </form>

    <% if (isReset) { %>
        <div class="footer-link">
            <a href="forgot.jsp">I can't remember my email address or phone number</a>
        </div>
    <% } %>
</div>

</body>
</html>
