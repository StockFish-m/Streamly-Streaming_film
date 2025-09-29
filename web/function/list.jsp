<%@ page import="java.util.*, model.content.Content" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Content> contents = (List<Content>) request.getAttribute("contents");
    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Content List</title>
    <style>
        table { width: 90%; margin: auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }
        h2, .message { text-align: center; }
        .actions a { margin: 0 5px; text-decoration: none; }
    </style>
</head>
<body>

<h2>Content List</h2>

<div style="text-align: center; margin-bottom: 20px;">
    <a href="edit">➕ Add New Content</a>
</div>

<% if (message != null) { %>
    <p class="message"><%= message %></p>
<% } %>

<table>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Type</th>
        <th>Release Date</th>
        <th>Actions</th>
    </tr>

    <% if (contents != null && !contents.isEmpty()) {
        for (Content c : contents) { %>
            <tr>
                <td><%= c.getContentId() %></td>
                <td><%= c.getTitle() %></td>
                <td><%= c.getType() %></td>
                <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(c.getReleaseDate()) %></td>
                <td class="actions">
                    <a href="edit?id=<%= c.getContentId() %>">✏️ Edit</a>
                    <a href="delete?id=<%= c.getContentId() %>" onclick="return confirm('Are you sure you want to delete this content?')">🗑️ Delete</a>
                </td>
            </tr>
    <%  }
    } else { %>
        <tr><td colspan="5">No content available.</td></tr>
    <% } %>
</table>

</body>
</html>
