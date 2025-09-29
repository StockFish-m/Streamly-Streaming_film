<%-- 
    Document   : contentList
    Created on : Jul 16, 2025, 8:52:17 AM
    Author     : DELL
--%>

<%@ page language="java" pageEncoding="UTF-8" import="java.util.*, model.content.Content" %>
<%@include file="/includes/header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách nội dung</title>
    </head>
    <body>
        <%
    List<Content> contents = (List<Content>) request.getAttribute("contents");
    String message = (String) request.getAttribute("message");
%>

<h2 style="text-align:center;">Danh sách nội dung</h2>

<div style="width:90%; margin:auto;">
    <a href="contentManager?action=new" style="float:right; margin-bottom: 10px;">➕ Thêm nội dung mới</a>
    
    <% if (message != null) { %>
        <p style="color:green;"><%= message %></p>
    <% } %>

    <table border="1" width="100%" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Tiêu đề</th>
            <th>Mô tả</th>
            <th>Ngày phát hành</th>
            <th>Loại</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        <% for (Content c : contents) { %>
        <tr>
            <td><%= c.getContentId() %></td>
            <td><%= c.getTitle() %></td>
            <td><%= c.getDescription() %></td>
            <td><%= c.getReleaseDate() %></td>
            <td><%= c.getType() %></td>
            <td><%= c.isIsActive() ? "Hiển thị" : "Đã ẩn" %></td>
            <td>
                <a href="contentManager?action=edit&id=<%= c.getContentId() %>">✏ Sửa</a> |
                <a href="contentManager?action=delete&id=<%= c.getContentId() %>" onclick="return confirm('Xác nhận xóa?');">❌ Xóa</a>
            </td>
        </tr>
        <% } %>
    </table>
</div>
    </body>
</html>
<%@include file="/includes/footer.jsp" %>
