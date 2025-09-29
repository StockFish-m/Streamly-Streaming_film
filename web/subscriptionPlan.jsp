<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.subscription.SubscriptionPlan" %>

<%
    List<SubscriptionPlan> plans = (List<SubscriptionPlan>) request.getAttribute("plans");
    if (plans == null) {
        response.sendRedirect("subscription"); // Gọi lại servlet nếu không có danh sách
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chọn Gói Đăng Ký</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 30px;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }

        table {
            border-collapse: collapse;
            width: 70%;
            margin: 0 auto;
            background-color: #fff;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 14px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #f1f1f1;
        }

        .btn-buy {
            padding: 8px 16px;
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 4px;
        }

        .btn-buy:hover {
            background-color: #0056b3;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%@ include file="/includes/header.jsp" %>

<h2>📦 Danh Sách Gói Đăng Ký</h2>

<% if (request.getParameter("error") != null) { %>
    <p class="error">❌ <%= request.getParameter("error") %></p>
<% } %>

<table>
    <tr>
        <th>Tên Gói</th>
        <th>Giá</th>
        <th>Thời Gian (Ngày)</th>
        <th>Hành Động</th>
    </tr>
    <% for (SubscriptionPlan plan : plans) { %>
    <tr>
        <td><%= plan.getName() %></td>
        <td><%= String.format("%,.0f VND", plan.getCost()) %></td>
        <td><%= plan.getBaseDuration() %></td>
        <td>
            <form action="pay" method="post">
                <input type="hidden" name="planId" value="<%= plan.getId() %>">
                <button type="submit" class="btn-buy">Mua</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>

<%@ include file="/includes/footer.jsp" %>
</body>
</html>
