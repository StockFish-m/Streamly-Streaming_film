<%@ page import="java.util.List" %>
<%@ page import="model.user.UserReview" %>

<%
    Double avgRating = (Double) request.getAttribute("averageRating");
    List<UserReview> reviews = (List<UserReview>) request.getAttribute("latestReviews");
%>

<div class="review-section" style="margin-top: 30px; padding: 20px; border: 1px solid #ccc; border-radius: 8px;">
    <h2>Reviews</h2>

    <p>
        Average Rating: 
        <strong><%= avgRating != null ? String.format("%.1f", avgRating) : "None" %> / 10</strong>
    </p>

    <h3>Top comments</h3>
    <ul style="list-style: none; padding-left: 0;">
        <% if (reviews != null && !reviews.isEmpty()) {
               for (UserReview r : reviews) { %>
            <li style="margin-bottom: 15px; padding-bottom: 10px; border-bottom: 1px solid #eee;">
                <strong><%= r.getFullname() %></strong> ? 
                <%= r.getRating() %> Stars<br>
                "<%= r.getComment() %>"
            </li>
        <%   }
           } else { %>
            <li>none</li>
        <% } %>
    </ul>
</div>
