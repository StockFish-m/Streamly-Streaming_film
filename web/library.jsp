<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Streamly | Library</title>

    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">

    <style>
        :root {
            --bg-page: #0b0b0b;
            --bg-card: #1a1a1a;
            --bg-card-hover: #232323;
            --accent: #4dbf00;
            --text-primary: #f5f5f5;
            --text-muted: #9ca3af;
            --border-soft: rgba(255, 255, 255, 0.08);
        }

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: linear-gradient(180deg, #070707 0%, #0f0f0f 35%, #101010 100%);
            color: var(--text-primary);
            font-family: 'Roboto', sans-serif;
        }

        main {
            max-width: 1200px;
            margin: 0 auto;
            padding: 96px 24px 80px;
        }

        .page-header {
            text-align: center;
            margin-bottom: 56px;
        }

        .page-eyebrow {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 6px 16px;
            border-radius: 999px;
            font-size: 13px;
            letter-spacing: 0.32em;
            text-transform: uppercase;
            background: rgba(77, 191, 0, 0.12);
            color: var(--accent);
        }

        .page-title {
            margin: 20px 0 12px;
            font-size: clamp(2rem, 3vw, 2.6rem);
        }

        .page-subtitle {
            margin: 0 auto;
            max-width: 620px;
            font-size: 15px;
            color: var(--text-muted);
            line-height: 1.6;
        }

        .section-block {
            margin-bottom: 64px;
        }

        .section-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 16px;
            flex-wrap: wrap;
            margin-bottom: 28px;
        }

        .section-title {
            display: flex;
            align-items: center;
            gap: 14px;
            font-size: 22px;
            margin: 0;
        }

        .section-title::before {
            content: '';
            width: 6px;
            height: 28px;
            border-radius: 20px;
            background: var(--accent);
        }

        .section-count {
            font-size: 14px;
            color: var(--text-muted);
            letter-spacing: 0.08em;
            text-transform: uppercase;
        }

        .empty-state {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            gap: 12px;
            padding: 48px 24px;
            border-radius: 16px;
            border: 1px solid var(--border-soft);
            background: rgba(26, 26, 26, 0.6);
            text-align: center;
            color: var(--text-muted);
        }

        .empty-state i {
            font-size: 46px;
            color: var(--accent);
        }

        .movie-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
            gap: 24px;
        }

        .movie-card {
            position: relative;
            border-radius: 16px;
            background: var(--bg-card);
            border: 1px solid var(--border-soft);
            overflow: hidden;
            transition: transform 0.3s ease, box-shadow 0.3s ease, border-color 0.3s ease;
        }

        .movie-card:hover {
            transform: translateY(-6px);
            border-color: rgba(77, 191, 0, 0.6);
            box-shadow: 0 18px 40px rgba(0, 0, 0, 0.35);
        }

        .movie-link {
            display: block;
            text-decoration: none;
            color: inherit;
        }

        .movie-thumbnail img {
            width: 100%;
            height: 260px;
            object-fit: cover;
            display: block;
        }

        .movie-info {
            padding: 18px 18px 20px;
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        .movie-title {
            margin: 0;
            font-size: 16px;
            font-weight: 600;
            letter-spacing: 0.01em;
        }

        .movie-meta {
            margin: 0;
            font-size: 13px;
            color: var(--text-muted);
            letter-spacing: 0.08em;
            text-transform: uppercase;
        }

        .remove-form {
            position: absolute;
            top: 14px;
            right: 14px;
            z-index: 2;
        }

        .remove-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 38px;
            height: 38px;
            border-radius: 999px;
            border: 1px solid rgba(255, 255, 255, 0.12);
            background: rgba(0, 0, 0, 0.58);
            color: var(--text-primary);
            cursor: pointer;
            transition: background 0.3s ease, transform 0.3s ease, border-color 0.3s ease;
            font-size: 20px;
        }

        .remove-btn:hover {
            background: var(--accent);
            color: #061006;
            border-color: transparent;
            transform: scale(1.05);
        }

        @media (max-width: 768px) {
            main {
                padding: 88px 18px 64px;
            }

            .section-header {
                align-items: flex-start;
            }
        }

        @media (max-width: 480px) {
            .movie-grid {
                grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
                gap: 18px;
            }

            .movie-thumbnail img {
                height: 220px;
            }
        }
    </style>
</head>

<body>
<%@include file="/includes/header.jsp" %>

<main>
    <header class="page-header">
        <span class="page-eyebrow">Library</span>
        <h1 class="page-title">Your Personal Collection</h1>
        <p class="page-subtitle">All the stories you love in one place. Keep track of what you want to watch next and pick up right where you left off.</p>
    </header>

    <section class="section-block">
        <div class="section-header">
            <h2 class="section-title">Watch List</h2>
            <c:if test="${not empty watch_movies}">
                <span class="section-count">${fn:length(watch_movies)} titles saved</span>
            </c:if>
        </div>

        <c:choose>
            <c:when test="${empty watch_movies}">
                <div class="empty-state">
                    <i class='bx bx-bookmark'></i>
                    <p>Your watch list is waiting. Start exploring and bookmark titles to build your queue.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="movie-grid">
                    <c:forEach var="movie" items="${watch_movies}">
                        <article class="movie-card">
                            <form method="post" action="library" class="remove-form">
                                <input type="hidden" name="contentId" value="${movie.contentId}" />
                                <button type="submit" class="remove-btn" aria-label="Remove ${movie.title} from watch list">
                                    <i class='bx bx-x'></i>
                                </button>
                            </form>
                            <a class="movie-link" href="${pageContext.request.contextPath}/detail?id=${movie.contentId}">
                                <div class="movie-thumbnail">
                                    <img src="${movie.thumbnailUrl}" alt="${movie.title}">
                                </div>
                                <div class="movie-info">
                                    <h3 class="movie-title">${movie.title}</h3>
                                    <p class="movie-meta">${movie.releaseYear}</p>
                                </div>
                            </a>
                        </article>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <section class="section-block">
        <div class="section-header">
            <h2 class="section-title">Recently Watched</h2>
            <c:if test="${not empty recently_movies}">
                <span class="section-count">${fn:length(recently_movies)} titles</span>
            </c:if>
        </div>

        <c:choose>
            <c:when test="${empty recently_movies}">
                <div class="empty-state">
                    <i class='bx bx-history'></i>
                    <p>No watch history yet. Hit play on something and it will show up here for quick access.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="movie-grid">
                    <c:forEach var="movie" items="${recently_movies}">
                        <article class="movie-card">
                            <a class="movie-link" href="${pageContext.request.contextPath}/detail?id=${movie.contentId}">
                                <div class="movie-thumbnail">
                                    <img src="${movie.thumbnailUrl}" alt="${movie.title}">
                                </div>
                                <div class="movie-info">
                                    <h3 class="movie-title">${movie.title}</h3>
                                    <p class="movie-meta">${movie.releaseYear}</p>
                                </div>
                            </a>
                        </article>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</main>

<%@include file="/includes/footer.jsp" %>
</body>
</html>
