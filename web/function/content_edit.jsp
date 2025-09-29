<%@ page import="model.content.Content" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Content content = (Content) request.getAttribute("content");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= (content != null ? "Edit" : "Add") %> Content</title>
    <style>
        form { max-width: 600px; margin: auto; }
        input, textarea, select { width: 100%; padding: 8px; margin: 10px 0; }
        .error { color: red; font-weight: bold; }
    </style>
</head>
<body>

<h2 style="text-align: center;"><%= (content != null ? "Edit Content" : "Add New Content") %></h2>

<% if (error != null) { %>
    <p class="error"><%= error %></p>
<% } %>

<form action="edit" method="post">
    <% if (content != null) { %>
        <input type="hidden" name="contentId" value="<%= content.getContentId() %>">
    <% } %>

    <label>Title:</label>
    <input type="text" name="title" value="<%= (content != null ? content.getTitle() : "") %>" required>

    <label>Description:</label>
    <textarea name="description" rows="4" required><%= (content != null ? content.getDescription() : "") %></textarea>

    <label>Release Date:</label>
    <input type="date" name="releaseDate"
           value="<%= (content != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(content.getReleaseDate()) : "") %>" required>

    <label>Type:</label>
    <select name="type" required>
        <option value="Movie" <%= (content != null && content.getType() == Content.ContentType.Movie ? "selected" : "") %>>Movie</option>
        <option value="Series" <%= (content != null && content.getType() == Content.ContentType.Series ? "selected" : "") %>>Series</option>
    </select>

    <label>Video URL:</label>
    <input type="text" name="videoUrl" value="<%= (content != null ? content.getVideoUrl() : "") %>">

    <label>Thumbnail URL:</label>
    <input type="text" name="thumbnailUrl" value="<%= (content != null ? content.getThumbnailUrl() : "") %>">

    <button type="submit"><%= (content != null ? "Update" : "Add") %></button>
</form>

</body>
</html>
