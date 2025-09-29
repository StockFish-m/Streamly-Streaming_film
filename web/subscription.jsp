<%@ page import="model.subscription.SubscriptionPlans" %>
<%@ page import="model.subscription.SubscriptionPlan" %>
<%@ page import="model.subscription.ViewerSubscription" %>
<%@ page import="model.user.Viewer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Viewer viewer = (Viewer) session.getAttribute("user");
    ViewerSubscription sub = (viewer != null) ? viewer.getActiveSubscription() : null;
    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Subscription Plans</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
            background-color: #f9f9f9;
        }

        h2 {
            color: #333;
        }

        .status {
            margin-bottom: 20px;
            padding: 10px;
            background: #e6f7ff;
            border: 1px solid #91d5ff;
            border-radius: 4px;
        }

        table {
            width: 70%;
            margin-top: 20px;
            border-collapse: collapse;
            background-color: #fff;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #fafafa;
        }

        .btn {
            margin-top: 20px;
            padding: 10px 25px;
            font-size: 16px;
            background-color: #1890ff;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }

        .btn:hover {
            background-color: #40a9ff;
        }

        .error {
            color: red;
            margin-top: 10px;
        }

        .success {
            color: green;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<%@include file="/includes/header.jsp" %>
<h2>🔔 Your Current Subscription</h2>

<%
    if (sub != null) {
        String planName = (sub.getPlanName() != null) ? sub.getPlanName().toUpperCase() : "UNKNOWN";
%>
    <div class="status">
        <p><strong>Plan:</strong> <%= planName %></p>
        <p><strong>Expires on:</strong> <%= sub.getExpiryDate() %></p>
        <p><strong>Status:</strong> <%= sub.isActive() ? "✅ Active" : "⚠️ Expired" %></p>
    </div>
<%
    } else {
%>
    <div class="status">
        <p>You have not subscribed to any plan yet.</p>
    </div>
<%
    }
%>

<% if (error != null) { %>
    <div class="error">❌ Error: <%= error %></div>
<% } else if (success != null) { %>
    <div class="success">✅ Subscription successful!</div>
<% } %>

<h2>📦 Choose a Plan</h2>

<form action="subscribe" method="post">
    <table>
        <thead>
            <tr>
                <th>Select</th>
                <th>Plan</th>
                <th>Price (USD)</th>
                <th>Duration (Days)</th>
            </tr>
        </thead>
        <tbody>
        <%
            for (int id = 1; id <= 5; id++) {
                SubscriptionPlan plan = SubscriptionPlans.getById(id);
                if (plan != null) {
        %>
            <tr>
                <td><input type="radio" name="plan_id" value="<%= plan.getId() %>" required></td>
                <td><%= plan.getName().toUpperCase() %></td>
                <td><%= String.format("$%.2f", plan.getCost()) %></td>
                <td><%= plan.getBaseDuration() %></td>
            </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

    <button class="btn" type="submit">Subscribe</button>
</form>
<%@include file="/includes/footer.jsp" %>  
</body>
</html>
