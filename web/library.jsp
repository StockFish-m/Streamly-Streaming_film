<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Library</title>
    
    <!-- Fonts and Icons -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

    <!-- Basic Styling -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            background: #f5f5f5;
            color: #333;
        }

        h2 {
            margin: 40px 0 20px;
            text-align: center;
            color: #444;
        }

        .section {
            max-width: 1200px;
            margin: auto;
            padding: 20px;
        }

        .movie-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: flex-start;
        }

        .movie-card {
            flex: 0 0 calc(25% - 20px);
            max-width: calc(25% - 20px);
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            overflow: hidden;
            position: relative;
            transition: transform 0.2s;
        }

        .movie-card:hover {
            transform: translateY(-5px);
        }

        .movie-card img {
            width: 100%;
            display: block;
            border-radius: 10px 10px 0 0;
        }

        .movie-card h3 {
            margin: 10px;
            font-size: 18px;
        }

        .movie-card p {
            margin: 0 10px 10px;
            font-size: 14px;
            color: #777;
        }

        .movie-card a {
            text-decoration: none;
            color: inherit;
        }

        .remove-form {
            position: absolute;
            top: 8px;
            right: 8px;
        }

        .remove-form button {
            background-color: #ff4d4d;
            color: white;
            border: none;
            padding: 4px 8px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        @media (max-width: 992px) {
            .movie-card {
                flex: 0 0 calc(33.333% - 20px);
            }
        }

        @media (max-width: 768px) {
            .movie-card {
                flex: 0 0 calc(50% - 20px);
            }
        }

        @media (max-width: 500px) {
            .movie-card {
                flex: 0 0 100%;
            }
        }
    </style>
</head>

<body>
    <%@include file="/includes/header.jsp" %>

    <div class="section">
        <h2>Your Watch List</h2>
        <div class="movie-grid">
            <c:forEach var="movie" items="${watch_movies}">
                <div class="movie-card">
                    <form method="post" action="library" class="remove-form">
                        <input type="hidden" name="contentId" value="${movie.contentId}" />
                        <button type="submit">X</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/detail?id=${movie.contentId}">
                        <img src="${movie.thumbnailUrl}" alt="${movie.title}">
                        <h3>${movie.title}</h3>
                        <p>${movie.releaseYear}</p>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="section">
        <h2>Recently Watched</h2>
        <div class="movie-grid">
            <c:forEach var="movie" items="${recently_movies}">
                <div class="movie-card">
                    <a href="${pageContext.request.contextPath}/detail?id=${movie.contentId}">
                        <img src="${movie.thumbnailUrl}" alt="${movie.title}">
                        <h3>${movie.title}</h3>
                        <p>${movie.releaseYear}</p>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>

    <%@include file="/includes/footer.jsp" %>
</body>
</html>
