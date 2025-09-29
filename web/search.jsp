<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tìm kiếm phim</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <style>
        body {
            background-color: #151515;
            color: white;
            font-family: "Roboto", sans-serif;
            margin: 0;
            padding: 0;
        }

        h2, h3 {
            text-align: center;
            margin-top: 30px;
            color: #4dbf00;
        }

        .search-form {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
            background-color: #1e1e1e;
            padding: 20px;
            border-radius: 10px;
            max-width: 1000px;
            margin: 30px auto;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            color: white;
            font-weight: bold;
        }

        .form-group label {
            margin-bottom: 5px;
        }

        .form-group input,
        .form-group select {
            padding: 8px;
            border-radius: 5px;
            border: none;
            width: 200px;
        }

        .btn-search {
            padding: 10px 20px;
            background-color: #4dbf00;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            height: 42px;
            align-self: flex-end;
        }

        .btn-search:hover {
            background-color: #3aa900;
        }

        .movie-grid {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 25px;
            padding: 30px;
            max-width: 1200px;
            margin: auto;
        }

        .movie-card {
            background-color: #1e1e1e;
            width: 200px;
            border-radius: 8px;
            overflow: hidden;
            text-align: center;
            transition: transform 0.3s;
        }

        .movie-card:hover {
            transform: translateY(-5px);
        }

        .movie-card img {
            width: 100%;
            height: 280px;
            object-fit: contain;
            background-color: #000;
        }

        .movie-card h3 {
            color: #4dbf00;
            margin: 10px 0 5px 0;
        }

        .movie-card p {
            color: gray;
            margin-bottom: 10px;
        }
        
        .pagination {
                text-align: center;
                margin-top: 30px;
            }

            .pagination a, .pagination span {
                display: inline-block;
                margin: 0 5px;
                padding: 6px 12px;
                border: 1px solid #ccc;
                border-radius: 4px;
                text-decoration: none;
                color: #333;
            }

            .pagination .current {
                background-color: #007bff;
                color: white;
                font-weight: bold;
            }
    </style>
</head>
<body>

    <%@include file="/includes/header.jsp" %>

    <h2>Tìm kiếm phim</h2>

    <form action="search" method="post" class="search-form">
        <div class="form-group">
            <label for="searchTerm">Tìm tên:</label>
            <input type="text" id="searchTerm" name="searchTerm"
                   value="${param.searchTerm != null ? param.searchTerm : ''}" />
        </div>

        <div class="form-group">
            <label for="genreId">Thể loại:</label>
            <select name="genreId" id="genreId">
                <option value="0">-- Tất cả --</option>
                <c:forEach var="genre" items="${genreList}">
                    <option value="${genre.genreId}"
                        <c:if test="${param.genreId == genre.genreId}">selected</c:if>>
                        ${genre.genreName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="releaseYearId">Năm:</label>
            <select name="releaseYearId" id="releaseYearId">
                <option value="0">-- Tất cả --</option>
                <c:forEach var="releaseYear" items="${releaseYearList}">
                    <option value="${releaseYear}"
                        <c:if test="${param.releaseYearId == releaseYear}">selected</c:if>>
                        ${releaseYear}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="typeId">Định dạng:</label>
            <select name="typeId" id="typeId">
                <option value="">-- Tất cả --</option>
                <option value="movie" <c:if test="${param.typeId == 'movie'}">selected</c:if>>Phim</option>
                <option value="series" <c:if test="${param.typeId == 'series'}">selected</c:if>>Loạt truyện</option>
            </select>
        </div>

        <button type="submit" class="btn-search">Tìm kiếm</button>
    </form>

    <c:if test="${not empty searchResults}">
        <h3>Kết quả tìm kiếm</h3>
        <div class="movie-grid">
            <c:forEach var="movie" items="${searchResults}">
                <div class="movie-card">
                    <a href="${pageContext.request.contextPath}/detail?id=${movie.contentId}">
                        <img src="${movie.thumbnailUrl}" alt="${movie.title}">
                        <h3>${movie.title}</h3>
                    </a>
                    <p>${movie.releaseYear}</p>
                </div>
            </c:forEach>
        </div>
        
        <!-- Pagination -->
            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="search?searchTerm=${param.searchTerm}&genreId=${param.genreId}&releaseYearId=${param.releaseYearId}&typeId=${param.typeId}&page=1">
                            <i class='bx bx-chevrons-left'></i>
                        </a>
                        <a href="search?searchTerm=${param.searchTerm}&genreId=${param.genreId}&releaseYearId=${param.releaseYearId}&typeId=${param.typeId}&page=${currentPage - 1}">
                            <i class='bx bx-chevron-left'></i>
                        </a>
                    </c:if>

                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="current">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="search?searchTerm=${param.searchTerm}&genreId=${param.genreId}&releaseYearId=${param.releaseYearId}&typeId=${param.typeId}&page=${i}">
                                    ${i}
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="search?searchTerm=${param.searchTerm}&genreId=${param.genreId}&releaseYearId=${param.releaseYearId}&typeId=${param.typeId}&page=${currentPage + 1}">
                            <i class='bx bx-chevron-right'></i>
                        </a>
                        <a href="search?searchTerm=${param.searchTerm}&genreId=${param.genreId}&releaseYearId=${param.releaseYearId}&typeId=${param.typeId}&page=${totalPages}">
                            <i class='bx bx-chevrons-right'></i>
                        </a>
                    </c:if>
                </div>
            </c:if>
    </c:if>
        
        

    <%@include file="/includes/footer.jsp" %>
</body>
</html>
