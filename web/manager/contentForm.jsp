<%-- 
    Document   : contentForm
    Created on : Jul 16, 2025, 8:53:06 AM
    Author     : DELL
--%>

<%@ page language="java" pageEncoding="UTF-8" import="java.util.*, model.content.Content" %>

<%
    Content content = (Content) request.getAttribute("content");
    String formAction = (content == null) ? "create" : "edit";
%>

<h2 style="text-align:center;"><%= (content == null) ? "Thêm nội dung mới" : "Chỉnh sửa nội dung" %></h2>

<div style="width:70%; margin:auto;">
    <form method="post" action="contentManager?action=<%= (content == null) ? "insert" : "update" %>">

        <% if (content != null) { %>
            <input type="hidden" name="id" value="<%= content.getContentId() %>"/>
        <% } %>

        <label>Tiêu đề:</label><br>
        <input type="text" name="title" required style="width:100%;" value="<%= (content != null) ? content.getTitle() : "" %>"/><br><br>

        <label>Mô tả:</label><br>
        <textarea name="description" rows="4" style="width:100%;"><%= (content != null) ? content.getDescription() : "" %></textarea><br><br>

        <label>Ngày phát hành:</label><br>
        <input type="date" name="releaseDate" required value="<%= (content != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(content.getReleaseDate()) : "" %>"/><br><br>

        <label>Loại:</label><br>
        <select name="type">
            <option value="Movie" <%= (content != null && content.getType().name().equals("Movie")) ? "selected" : "" %>>Movie</option>
            <option value="Series" <%= (content != null && content.getType().name().equals("Series")) ? "selected" : "" %>>Series</option>
        </select><br><br>

        <label>URL Video:</label><br>
        <input type="text" name="videoUrl" style="width:100%;" value="<%= (content != null) ? content.getVideoUrl() : "" %>"/><br><br>

        <label>URL Thumbnail:</label><br>
        <input type="text" name="thumbnailUrl" style="width:100%;" value="<%= (content != null) ? content.getThumbnailUrl() : "" %>"/><br><br>

        <label>Trạng thái:</label><br>
        <select name="isActive">
            <option value="true" <%= (content != null && content.isIsActive()) ? "selected" : "" %>>Hiển thị</option>
            <option value="false" <%= (content != null && !content.isIsActive()) ? "selected" : "" %>>Đã ẩn</option>
        </select><br><br>

        <button type="submit">💾 Lưu</button>
        <a href="contentManager" style="margin-left: 10px;">❌ Hủy</a>

    </form>
</div>
        

