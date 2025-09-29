<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.content.Content" %>

<%@ include file="/includes/header.jsp" %>

<%
    Content content = (Content) request.getAttribute("content");
    if (content == null) {
%>
<div class="text-center text-danger py-5">
    <h2>Không tìm thấy nội dung.</h2>
</div>
<%
        return;
    }
    List<String> genres = (List<String>) request.getAttribute("genres");
    List<String> casts = (List<String>) request.getAttribute("casts");
    String message = (String) request.getAttribute("feedbackMessage");
    model.user.Viewer user = (model.user.Viewer) session.getAttribute("user");
    model.user.UserReview userReview = (model.user.UserReview) request.getAttribute("userReview");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><%= content.getTitle() %> - Xem phim | Streamly</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f8f9fa;
        }
        .banner-container { position: relative; height: 500px; overflow: hidden; }
        .banner-img { width: 100%; height: 100%; object-fit: cover; }
        .banner-overlay {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            padding: 50px 40px;
            background: linear-gradient(to top, rgba(0,0,0,0.85), rgba(0,0,0,0.3), transparent);
            color: #fff;
        }
        .btn-action {
            background-color: #fff;
            color: #000;
            padding: 10px 24px;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            transition: background-color 0.3s;
        }
        .btn-action:hover { background-color: #e9ecef; }
        .info-card {
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        }
        .info-label {
            font-weight: bold;
            margin-bottom: 6px;
            display: block;
        }
        .tag-list { display: flex; flex-wrap: wrap; gap: 10px; }
        .tag {
            background-color: #e2e6ea;
            border-radius: 20px;
            padding: 6px 14px;
            font-size: 14px;
        }
    </style>
</head>
<body>

<!-- Banner -->
<div class="banner-container">
    <img class="banner-img" src="<%= content.getThumbnailUrl() %>" alt="Thumbnail">
    <div class="banner-overlay">
        <h1 class="banner-title fw-bold"><%= content.getTitle() %></h1>
        <p class="banner-description"><%= content.getDescription() %></p>
        <div class="d-flex flex-wrap gap-2">
            <a class="btn-action" href="watch?id=<%= content.getContentId() %>">▶ Xem phim ngay</a>
            <form action="detail" method="post">
                <input type="hidden" name="action" value="addToWatchlist" />
                <input type="hidden" name="contentId" value="<%= content.getContentId() %>" />
                <button type="submit" class="btn-action">+ Danh sách của tôi</button>
            </form>
        </div>
    </div>
</div>

<!-- Feedback Message -->
<% if (message != null) { %>
<div class="text-center py-3" style="color: <%= message.startsWith("✅") ? "green" : "red" %>; font-weight: 500;">
    <%= message %>
</div>
<% } %>

<!-- Detail Section -->
<div class="container my-5">
    <h2 class="mb-4 border-start border-primary ps-3">📋 Thông tin chi tiết</h2>
    <div class="info-card">
        <div class="mb-3">
            <span class="info-label">📅 Năm phát hành:</span>
            <%= content.getReleaseDate() %>
        </div>
        <div class="mb-3">
            <span class="info-label">🎬 Thể loại:</span>
            <div class="tag-list">
                <% for (String genre : genres) { %>
                <span class="tag"><%= genre %></span>
                <% } %>
            </div>
        </div>
        <div class="mb-3">
            <span class="info-label">🧑‍🎤 Diễn viên:</span>
            <div class="tag-list">
                <% for (String cast : casts) { %>
                <span class="tag"><%= cast %></span>
                <% } %>
            </div>
        </div>
    </div>
</div>



<% if (user != null) { %>
<div class="container my-5">
    <h3>Comment</h3>
    <form method="post" action="detail">
        <input type="hidden" name="contentId" value="<%= content.getContentId() %>" />
        <input type="hidden" name="action" value="submitReview" />

        <label>Rating (1-10):</label>
        <input type="number" name="rating" min="1" max="10" value="<%= userReview != null ? userReview.getRating() : "" %>" required />

        <label>comments:</label>
        <textarea name="comment" required><%= userReview != null ? userReview.getComment() : "" %></textarea>

        <button type="submit">Send</button>
    </form>
</div>
<% } %>

<!-- Review Section -->
<jsp:include page="/includes/review-section.jsp" />

<%@ include file="/includes/footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
