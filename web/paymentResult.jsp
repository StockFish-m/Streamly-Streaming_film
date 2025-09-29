<%-- 
    Document   : paymentResult
    Created on : Jul 16, 2025, 12:25:45 AM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
            background-color: #f4f4f4;
            text-align: center;
        }
        .result {
            font-size: 22px;
            padding: 20px;
            background: white;
            display: inline-block;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
        }
        .success {
            color: green;
        }
        .fail {
            color: red;
        }
        a {
            display: block;
            margin-top: 20px;
            color: #1890ff;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="result">
        <% Boolean result = (Boolean) request.getAttribute("transResult"); %>
        <% if (result != null && result) { %>
            <p class="success">Payment successful! Thank you for subscribing to the service package.</p>
        <% } else { %>
            <p class="fail">Payment failed. Please try again or choose another method.</p>
        <% } %>
        <a href="subscription">Back to subscription page </a>
    </div>
</body>
</html>
