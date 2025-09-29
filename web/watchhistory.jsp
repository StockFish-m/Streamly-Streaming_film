<%@ page import="model.content.WatchHistory" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.WatchHistoryDAO" %>
<%@ page import="dao.WatchHistoryDAOImpl" %>
<%@ page import="model.user.Viewer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Viewer user = (Viewer) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("userlogin.jsp");
        return;
    }

    // Gọi DAO mới có join Content và Episodes
    WatchHistoryDAO dao = new WatchHistoryDAOImpl();
    List<WatchHistory> historyList = ((WatchHistoryDAOImpl) dao).getDetailedWatchHistoriesByUserId(user.getUser_id());
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Watch History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        h2 {
            text-align: center;
            margin-top: 30px;
        }
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px 15px;
            border-bottom: 1px solid #ccc;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        a {
            color: blue;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <h2>Your Watch History</h2>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Content Title</th>
                <th>Episode</th>
                <th>Watched At</th>
                <th>Watch</th>
            </tr>
        </thead>
        <tbody>
        <%
            if (historyList == null || historyList.isEmpty()) {
        %>
            <tr>
                <td colspan="5">No watch history found.</td>
            </tr>
        <%
            } else {
                int i = 1;
                for (WatchHistory wh : historyList) {
        %>
            <tr>
                <td><%= i++ %></td>
                <td><%= wh.getTitle() != null ? wh.getTitle() : "Unknown" %></td>
                <td><%= wh.getEpisode_id() > 0 ? ("Episode " + wh.getEpisode_id()) : "-" %></td>
                <td><%= wh.getWatched_at() %></td>
                <td>
                    <% if (wh.getVideo_url() != null && !wh.getVideo_url().isEmpty()) { %>
                        <a href="<%= wh.getVideo_url() %>" target="_blank">Play</a>
                    <% } else { %>
                        N/A
                    <% } %>
                </td>
            </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</body>
</html>
