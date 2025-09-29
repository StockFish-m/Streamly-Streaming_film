<%-- 
    Document   : signup
    Created on : Jun 27, 2025, 2:34:53 PM
    Author     : Acer
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .signup-container {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        h2 {
            margin-bottom: 24px;
            text-align: center;
            color: #333;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #555;
        }

        .form-group input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        .form-group input:focus {
            border-color: #007bff;
            outline: none;
        }

        .submit-btn {
            width: 100%;
            background: #007bff;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s ease;
        }

        .submit-btn:hover {
            background: #0056b3;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 16px;
            color: #007bff;
            text-decoration: none;
            font-size: 14px;
        }

        .back-link i {
            vertical-align: middle;
            margin-right: 4px;
        }

        .back-link:hover {
            text-decoration: underline;
        }

        .error-message {
            background-color: #ffe0e0;
            color: #c00;
            border: 1px solid #cc0000;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 6px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="signup-container">
        <h2>Create Account</h2>

        <!-- Error Display -->
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/signup">
            <div class="form-group">
                <label>Username:</label>
                <input type="text" name="username" value="${enteredUsername}" required>
            </div>
            <div class="form-group">
                <label>Full Name:</label>
                <input type="text" name="fullname" value="${enteredFullname}" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" value="${enteredEmail}" required>
            </div>
            <div class="form-group">
                <label>Phone:</label>
                <input type="tel" name="phone" value="${enteredPhone}" required>
            </div>
            <div class="form-group">
                <label>Password:</label>
                <input type="password" name="password" required>
            </div>
            <input class="submit-btn" type="submit" value="Sign Up">
        </form>
        <a class="back-link" href="userlogin.jsp"><i class='bx bx-arrow-back'></i>Back to Login</a>
    </div>
</body>
</html>
