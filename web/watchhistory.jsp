<%@ page import="model.content.WatchHistory" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.WatchHistoryDAO" %>
<%@ page import="dao.WatchHistoryDAOImpl" %>
<%@ page import="model.user.Viewer" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Viewer user = (Viewer) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/userlogin");
        return;
    }

    // Gọi DAO mới có join Content và Episodes
    WatchHistoryDAO dao = new WatchHistoryDAOImpl();
    List<WatchHistory> historyList = ((WatchHistoryDAOImpl) dao).getDetailedWatchHistoriesByUserId(user.getUser_id());
%>

<%@include file="/includes/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Watch History</title>
    <style>
        body { background-color: #111; color: #fff; font-family: 'Roboto', Arial, sans-serif; }
        .container { max-width: 1200px; margin: 90px auto 40px; padding: 0 16px; }
        h2 { margin: 0 0 16px; font-weight: 700; }
        .toolbar { display: flex; justify-content: flex-start; align-items: center; gap: 12px; margin-bottom: 16px; }
        .toolbar .back-btn { order: 1; }
        .toolbar h2 { order: 2; }
        .back-btn { display: inline-flex; align-items: center; gap: 8px; padding: 10px 16px; background: #4dbf00; color: #111; border: none; border-radius: 6px; text-decoration: none; font-weight: 700; cursor: pointer; }
        .back-btn:hover { filter: brightness(0.95); }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(180px, 1fr)); gap: 18px; }
        .card { background: #1e1e1e; border-radius: 10px; overflow: hidden; transition: transform .2s ease, box-shadow .2s ease; box-shadow: 0 6px 18px rgba(0,0,0,0.25); }
        .card:hover { transform: translateY(-4px); box-shadow: 0 10px 24px rgba(0,0,0,0.35); }
        .thumb { width: 100%; height: 260px; object-fit: cover; background: #222; display: block; }
        .meta { padding: 10px 12px 14px; }
        .title { font-size: 14px; font-weight: 700; color: #fff; margin: 0 0 6px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
        .watched { font-size: 12px; color: #bbb; }
        .link { text-decoration: none; color: inherit; display: block; }
        .empty { color:#bbb; text-align:center; padding: 28px 0; grid-column: 1/-1; }
        /* Hide inline heading in toolbar and place title below */
        .toolbar h2 { display: none; }
    </style>
</head>
<body>
    <div class="container">
        <div class="toolbar">
            <h2>Your Watch History</h2>
            <a class="back-btn" href="${pageContext.request.contextPath}/profile" onclick="event.preventDefault(); history.back();">
                19 Back
            </a>
        </div>
        <h2>Your Watch History</h2>

    <div class="grid container">
    <%
        if (historyList == null || historyList.isEmpty()) {
    %>
        <div class="empty">No watch history found.</div>
    <%
        } else {
            // Safety dedupe in view (DAO already returns latest per content)
            Set<Integer> seen = new HashSet<>();
            for (WatchHistory wh : historyList) {
                if (!seen.add(wh.getContent_id())) continue;
                String thumb = wh.getThumbnail_url() != null && !wh.getThumbnail_url().isEmpty() ? wh.getThumbnail_url() : "https://via.placeholder.com/300x450?text=No+Image";
    %>
        <a class="link card" href="<%= request.getContextPath() %>/watch?id=<%= wh.getContent_id() %>">
            <img class="thumb" src="<%= thumb %>" alt="Poster">
            <div class="meta">
                <div class="title"><%= wh.getTitle() != null ? wh.getTitle() : "Untitled" %></div>
                <div class="watched">Watched: <%= wh.getWatched_at() %></div>
            </div>
        </a>
    <%
            }
        }
    %>
    </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var backBtn = document.querySelector('.back-btn');
            if (backBtn) backBtn.textContent = '\u2190 Back';
        });
    </script>

<%@include file="/includes/footer.jsp" %>
</body>
</html>
