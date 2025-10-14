<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.content.Content" %>

<%
    Content content = (Content) request.getAttribute("content");
    if (content == null) {
%>
<div style="text-align:center;color:#e74c3c;padding:80px 0;">
    <h2>Content not found.</h2>
    <a href="${pageContext.request.contextPath}/main" style="color:#4dbf00;text-decoration:none;">Back to Home</a>
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

<%@ include file="/includes/header.jsp" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><%= content.getTitle() %> | Streamly</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body { background:#111; color:#fff; font-family:'Roboto',sans-serif; }
        a { text-decoration:none; }

        /* Hero */
        .hero { position:relative; height:65vh; min-height:460px; overflow:hidden; }
        .hero .bg { position:absolute; inset:0; width:100%; height:100%; object-fit:cover; filter:brightness(.55); }
        .hero .overlay { position:absolute; inset:0; background:linear-gradient(180deg, rgba(0,0,0,.25) 0%, rgba(0,0,0,.75) 60%, rgba(0,0,0,.95) 100%); }
        .hero .content { position:relative; z-index:2; max-width:1200px; margin:0 auto; padding:80px 24px 24px; display:flex; flex-direction:column; gap:14px; }
        .hero .title { font-size:42px; font-weight:800; line-height:1.1; }
        .hero .meta { display:flex; gap:10px; flex-wrap:wrap; }
        .chip { background:rgba(255,255,255,.14); color:#fff; border:1px solid rgba(255,255,255,.18); padding:6px 12px; border-radius:999px; font-size:12px; }
        .hero .desc { max-width:900px; color:#ddd; }
        .hero .actions { display:flex; gap:12px; flex-wrap:wrap; margin-top:6px; }
        .btn-play { background:#4dbf00; color:#111; font-weight:800; padding:12px 22px; border-radius:8px; border:none; cursor:pointer; }
        .btn-play:hover { filter:brightness(.95); }
        .btn-add { background:rgba(255,255,255,.08); color:#fff; padding:12px 18px; border-radius:8px; border:1px solid rgba(255,255,255,.18); cursor:pointer; }
        .btn-add:hover { background:rgba(255,255,255,.14); }

        /* Body */
        .wrap { max-width:1200px; margin:24px auto 40px; padding:0 16px; }
        .grid { display:grid; grid-template-columns:2fr 1fr; gap:28px; }
        .card { background:#1e1e1e; border-radius:12px; padding:20px; box-shadow:0 8px 24px rgba(0,0,0,.35); }
        .section-title { font-size:20px; font-weight:800; margin:0 0 12px; }
        .muted { color:#bbb; }
        .tags { display:flex; flex-wrap:wrap; gap:10px; }
        .tag { background:#222; color:#ddd; border:1px solid #333; border-radius:999px; padding:6px 12px; font-size:12px; }

        /* Review form */
        .review-card label { display:block; font-weight:700; margin:8px 0 6px; }
        .review-card input[type=number], .review-card textarea { width:100%; background:#141414; color:#fff; border:1px solid #2a2a2a; border-radius:8px; padding:10px 12px; }
        .review-card textarea { min-height:110px; resize:vertical; }
        .btn-submit { display:inline-block; background:#4dbf00; color:#111; border:none; border-radius:8px; padding:10px 18px; font-weight:800; cursor:pointer; margin-top:10px; }
        .btn-submit:hover { filter:brightness(.95); }

        /* Feedback */
        .feedback { max-width:1200px; margin:10px auto; padding:10px 16px; border-radius:10px; background:rgba(77,191,0,.12); color:#c6ffc6; border:1px solid rgba(77,191,0,.35); }

        @media (max-width: 900px){ .grid { grid-template-columns:1fr; } .hero { height:56vh; } }
    </style>
</head>
<body>

<!-- Hero Banner -->
<section class="hero">
    <img class="bg" src="<%= content.getThumbnailUrl() %>" alt="Background">
    <div class="overlay"></div>
    <div class="content">
        <div class="title"><%= content.getTitle() %></div>
        <div class="meta">
            <span class="chip"><%= content.getReleaseDate() != null ? content.getReleaseDate() : "Unknown year" %></span>
            <span class="chip"><%= content.getType() %></span>
            <span class="chip">Rating: <%= request.getAttribute("averageRating") != null ? request.getAttribute("averageRating") : "N/A" %></span>
        </div>
        <div class="desc muted"><%= content.getDescription() %></div>
        <div class="actions">
            <a class="btn-play" href="watch?id=<%= content.getContentId() %>">▶ Play</a>
            <form action="detail" method="post">
                <input type="hidden" name="action" value="addToWatchlist" />
                <input type="hidden" name="contentId" value="<%= content.getContentId() %>" />
                <button type="submit" class="btn-add">＋ My List</button>
            </form>
        </div>
    </div>
 </section>

<!-- Feedback Message -->
<% if (message != null) { %>
<div class="feedback">
    <%= message %>
</div>
<% } %>

<!-- Details + Review -->
<div class="wrap">
    <div class="grid">
        <!-- Left: About -->
        <div class="card">
            <div class="section-title">Overview</div>
            <p class="muted" style="margin-bottom:18px;"><%= content.getDescription() %></p>

            <div class="section-title" style="margin-top:8px;">Genres</div>
            <div class="tags" style="margin-bottom:18px;">
                <% if (genres != null) { for (String genre : genres) { %>
                <span class="tag"><%= genre %></span>
                <% } } %>
            </div>

            <div class="section-title">Cast</div>
            <div class="tags">
                <% if (casts != null) { for (String cast : casts) { %>
                <span class="tag"><%= cast %></span>
                <% } } %>
            </div>
        </div>

        <!-- Right: Review -->
        <div class="card review-card">
            <div class="section-title">Your Review</div>
            <% if (user != null) { %>
            <form method="post" action="detail">
                <input type="hidden" name="contentId" value="<%= content.getContentId() %>" />
                <input type="hidden" name="action" value="submitReview" />

                <label>Rating (1-10)</label>
                <input type="number" name="rating" min="1" max="10" value="<%= userReview != null ? userReview.getRating() : "" %>" required />

                <label>Comments</label>
                <textarea name="comment" placeholder="Share your thoughts..." required><%= userReview != null ? userReview.getComment() : "" %></textarea>

                <button type="submit" class="btn-submit">Submit</button>
            </form>
            <% } else { %>
                <p class="muted">Please log in to leave a review.</p>
            <% } %>
        </div>
    </div>
</div>

<!-- Review Section -->
<jsp:include page="/includes/review-section.jsp" />

<%@ include file="/includes/footer.jsp" %>
</body>
</html>
