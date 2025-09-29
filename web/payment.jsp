<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    model.user.Viewer viewer = (model.user.Viewer) session.getAttribute("user");
    model.subscription.ViewerSubscription sub = null;
    if (viewer != null) {
        sub = viewer.getActiveSubscription();
    }
%>

<html>
<head>
    <title>Payment Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background-color: #f9f9f9;
        }
        .payment-box {
            max-width: 500px;
            margin: auto;
            padding: 30px;
            border-radius: 10px;
            background: #fff;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .payment-box h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .payment-info {
            line-height: 1.8;
            font-size: 16px;
        }
        .success {
            color: green;
            font-weight: bold;
            margin-top: 20px;
        }
        a.button {
            display: inline-block;
            margin-top: 30px;
            padding: 10px 20px;
            background-color: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        a.button:hover {
            background-color: #0b7dda;
        }
    </style>
</head>
<body>

<div class="payment-box">
    <h2>Subscription Payment Successful</h2>

    <c:choose>
        <c:when test="${viewer != null && sub != null}">
            <div class="payment-info">
                <p><strong>Subscriber:</strong> <%= viewer.getFullName() %> (<%= viewer.getUsername() %>)</p>
                <p><strong>Plan Name:</strong> <%= sub.getPlanName() %></p>
                <p><strong>Price:</strong> $<%= sub.getCost() %></p>
                <p><strong>Duration:</strong> <%= sub.getDuration() %> days</p>
                <p><strong>Purchase Date:</strong> <%= sub.getPurchaseDate() %></p>
                <p><strong>Expiry Date:</strong> <%= sub.getExpiryDate() %></p>
            </div>
            <p class="success">🎉 Thank you for your subscription!</p>
        </c:when>
        <c:otherwise>
            <p style="color:red;">No subscription information available. Please try again.</p>
        </c:otherwise>
    </c:choose>

            <a href="subscription.jsp" class="button">Back to Home</a>
</div>

</body>
</html>
