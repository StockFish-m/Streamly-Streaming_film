
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.content.Content" %>
<%@include file="/includes/header.jsp" %>

<%
    Content content = (Content) request.getAttribute("content");
    if (content == null) {
%>
    <h2 style="color: red; text-align: center;">Không tìm thấy nội dung cần xem.</h2>
<%
        return;
    }
%>

<style>
    body {
        background-color: #151515;
        color: #fff;
        font-family: 'Roboto', sans-serif;
    }

    .container {
        max-width: 1000px;
        margin: 30px auto;
        padding: 20px;
        background-color: #1e1e1e;
        border-radius: 10px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.5);
    }

    .thumbnail {
        width: 100%;
        max-height: 400px;
        object-fit: cover;
        border-radius: 10px;
        margin-bottom: 20px;
    }

    video {
        width: 100%;
        height: auto;
        border-radius: 10px;
        margin-bottom: 20px;
    }

    h1 {
        font-size: 28px;
        color: #4dbf00;
        margin-bottom: 10px;
    }

    .meta {
        font-size: 14px;
        color: #ccc;
        margin-bottom: 20px;
    }

    .description {
        font-size: 16px;
        line-height: 1.6;
        color: #e0e0e0;
        margin-bottom: 30px;
    }

    .back-btn {
        display: inline-block;
        padding: 10px 20px;
        background-color: #4dbf00;
        color: #fff;
        text-decoration: none;
        border-radius: 5px;
        transition: background 0.3s ease;
    }

    .back-btn:hover {
        background-color: #3aa500;
    }
</style>

<script>
    window.onload = function () {
        const videoEl = document.getElementById("videoPlayer");
        if (videoEl) {
            videoEl.scrollIntoView({ behavior: "smooth", block: "start" });
            setTimeout(() => videoEl.play().catch(() => {}), 500);
        }
    };
</script>

<div class="container">
    <img class="thumbnail" src="<%= content.getThumbnailUrl() %>" alt="Thumbnail">

    <%
        String videoUrl = content.getVideoUrl();
        if (videoUrl != null && !videoUrl.isEmpty()) {
    %>
        <video id="videoPlayer" controls autoplay muted>
            <source src="<%= videoUrl %>" type="video/mp4">
            Trình duyệt của bạn không hỗ trợ thẻ video.
        </video>
    <%
        } else {
    %>
        <p style="color: orange; font-size: 16px;">⚠ Không có video để phát.</p>
    <%
        }
    %>

    <h1><%= content.getTitle() %></h1>
    <div class="meta">📅 <%= content.getReleaseDate() %></div>

    <div class="description"><%= content.getDescription() %></div>

    <div style="text-align: center;">
        <a class="back-btn" href="${pageContext.request.contextPath}/main">← Quay lại trang chủ</a>
    </div>
</div>

<%@include file="/includes/footer.jsp" %>

<script>
    const userId = '${sessionScope.user.user_id}';
    const videoId = '${content.contentId}';
    const watchedSeconds = <%= request.getAttribute("watchedSeconds") != null ? request.getAttribute("watchedSeconds") : 0 %>;

    const video = document.getElementById("videoPlayer");

    video.addEventListener("loadedmetadata", () => {
        if (watchedSeconds && watchedSeconds < video.duration - 5) {
            video.currentTime = watchedSeconds;
        }
    });

    setInterval(() => {
        if (!video.paused && !video.ended) {
            const currentTime = video.currentTime;
            fetch('update_progress', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    userId: userId,
                    videoId: videoId,
                    watchedSeconds: currentTime
                })
            });
        }
    }, 5000);
</script>
