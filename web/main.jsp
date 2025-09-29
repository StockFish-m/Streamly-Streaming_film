<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Streamly</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/5/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Karma">
    <style>
    body {
        background-color: #111;
        color: white;
        font-family: Arial, sans-serif;
    }

    .movie-section {
        margin-bottom: 40px;
    }

    .movie-section h2 {
        margin-left: 10px;
        font-size: 24px;
        font-weight: bold;
        border-left: 4px solid #e50914;
        padding-left: 10px;
        margin-bottom: 15px;
    }

    .movie-grid {
        display: flex;
        gap: 16px;
        overflow-x: auto;
        padding: 10px;
        scroll-behavior: smooth;
    }

    .movie-card {
        min-width: 160px;
        max-width: 160px;
        background-color: #222;
        border-radius: 8px;
        overflow: hidden;
        transition: transform 0.3s;
        flex-shrink: 0;
    }

    .movie-card:hover {
        transform: scale(1.05);
    }

    .movie-card img {
        width: 100%;
        height: 240px;
        object-fit: cover;
        border-bottom: 1px solid #444;
    }

    .movie-card h3 {
        font-size: 14px;
        margin: 8px 10px 4px;
        color: #fff;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .movie-card p {
        font-size: 12px;
        color: #aaa;
        margin: 0 10px 10px;
    }

    .movie-grid::-webkit-scrollbar {
        height: 6px;
    }

    .movie-grid::-webkit-scrollbar-thumb {
        background-color: #444;
        border-radius: 3px;
    }

    .movie-grid::-webkit-scrollbar-track {
        background-color: transparent;
    }
    </style>
</head>
<body>

<%@include file="/includes/header.jsp" %>

<div class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:100px">

    <c:set var="sections" value="${['New Movies','Hot Movies','Trending Movies','Recommended','Action','Popular']}" />
    <c:set var="moviesList" value="${[new_movies, hot_movies, trending_movies, new_movies, hot_movies, trending_movies]}" />

    <c:forEach var="section" items="${sections}" varStatus="status">
        <div class="movie-section">
            <h2>${section}</h2>
            <div class="movie-grid">
                <c:forEach var="movie" items="${moviesList[status.index]}" begin="0" end="9">
                    <div class="movie-card">
                        <a href="${pageContext.request.contextPath}/detail?id=${movie.contentId}">
                            <img src="${movie.thumbnailUrl}" alt="${movie.title}">
                            <h3>${movie.title}</h3>
                            <p>${movie.releaseDate}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:forEach>

</div>
    
<%@include file="assistantChat.jsp" %>

<%@include file="/includes/footer.jsp" %>
</body>
</html>
