<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        .profile-card { max-width: 650px; margin: 0 auto; }
        #updateForm { border: 1px solid #dee2e6; border-radius: .5rem; padding: 1.5rem; box-shadow: 0 0.25rem 0.75rem rgba(0,0,0,.05); }
        .action-buttons > * { min-width: 160px; }
        @media (max-width: 575.98px) { .action-buttons > * { width: 100%; } }
    </style>
</head>
<body class="bg-light">
    
<%@include file="/includes/header.jsp" %>

<div class="container py-5">
    <h1 class="mb-4 text-center">User Profile</h1>

    <!-- Success / Error message -->
    <c:if test="${not empty message}">
        <div class="alert alert-info text-center" role="alert">${message}</div>
    </c:if>

    <!-- Profile Card -->
    <div class="card profile-card shadow-sm mb-5">
        <div class="card-body text-center">
            <img src="https://ui-avatars.com/api/?name=${user.username}&size=120&background=0D8ABC&color=fff" class="rounded-circle mb-3 shadow-sm" alt="Avatar">
            <h4 class="card-title mb-0">${user.username}</h4>
            <p class="text-muted small">
                Member since <fmt:formatDate value="${user.created_at}" pattern="dd/MM/yyyy"/>
            </p>
        </div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item"><strong>Full name:</strong> ${user.fullName}</li>
            <li class="list-group-item"><strong>Email:</strong> ${user.email}</li>
            <li class="list-group-item"><strong>Phone number:</strong> ${user.phoneNumber}</li>
            <li class="list-group-item"><strong>Subscription:</strong>
                <c:choose>
                    <c:when test="${not empty user.activeSubscription}">
                        ${user.activeSubscription.planName} -
                        <fmt:formatNumber value="${user.activeSubscription.cost}" type="currency"/>
                        (
                        <c:choose>
                            <c:when test="${user.activeSubscription.duration == -1}">
                                Forever
                            </c:when>
                            <c:otherwise>
                                expires on 
                                <fmt:formatDate value="${user.activeSubscription.expiryDateAsDate}" pattern="dd/MM/yyyy"/>
                            </c:otherwise>
                        </c:choose>
                        )
                    </c:when>
                    <c:otherwise>NONE</c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>

    <!-- Action Buttons -->
    <div class="d-flex justify-content-center flex-wrap gap-2 action-buttons mb-4">
        <a href="profile?action=edit" class="btn btn-primary"><i class="bi bi-pencil-square me-1"></i> Update Profile</a>
        <a href="changepassword.jsp" class="btn btn-secondary"><i class="bi bi-lock me-1"></i> Change Password</a>
        <a href="watchhistory" class="btn btn-outline-info"><i class="bi bi-clock-history me-1"></i> Watch History</a>
    </div>
</div>

<%@include file="/includes/footer.jsp" %>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 
