<%@ page import="java.util.List" %>
<%@ page import="model.user.UserReview" %>

<%
    Double avgRating = (Double) request.getAttribute("averageRating");
    List<UserReview> reviews = (List<UserReview>) request.getAttribute("latestReviews");
%>

<style>
    .reviews-wrap { margin-top: 26px; }
    .reviews-card { background: #1e1e1e; border-radius: 12px; padding: 18px 20px; box-shadow: 0 8px 24px rgba(0,0,0,.35); }
    .reviews-title { font-size: 20px; font-weight: 800; margin: 0 0 10px; }
    .reviews-sub { color: #bbb; margin-bottom: 12px; }
    .reviews-list { list-style: none; padding: 0; margin: 0; display: grid; gap: 14px; }
    .review-item { background: #161616; border: 1px solid #2a2a2a; border-radius: 10px; padding: 12px 14px; }
    .review-head { display: flex; justify-content: space-between; align-items: center; margin-bottom: 6px; }
    .review-user { font-weight: 700; color: #fff; }
    .review-rating { color: #4dbf00; font-weight: 800; }
    .review-body { color: #ddd; white-space: pre-wrap; }
    .empty-reviews { color: #bbb; padding: 8px 0; }
</style>

<div class="reviews-wrap">
    <div class="reviews-card">
        <div class="reviews-title">Reviews</div>
        <div class="reviews-sub">Average Rating: <strong><%= avgRating != null ? String.format("%.1f", avgRating) : "None" %> / 10</strong></div>

        <ul class="reviews-list">
            <% if (reviews != null && !reviews.isEmpty()) {
                   for (UserReview r : reviews) { %>
                <li class="review-item">
                    <div class="review-head">
                        <div class="review-user"><%= r.getFullname() %></div>
                        <div class="review-rating">★ <%= r.getRating() %>/10</div>
                    </div>
                    <div class="review-body"><%= r.getComment() %></div>
                </li>
            <%   }
               } else { %>
                <li class="empty-reviews">No reviews yet. Be the first to comment!</li>
            <% } %>
        </ul>
    </div>
</div>
