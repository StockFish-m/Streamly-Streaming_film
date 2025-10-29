<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Streamly | Profile</title>

    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">

    <style>
        :root {
            --bg-page: #0b0b0b;
            --bg-card: #161616;
            --accent: #4dbf00;
            --accent-soft: rgba(77, 191, 0, 0.18);
            --text-primary: #f5f5f5;
            --text-muted: #9ca3af;
            --border-soft: rgba(255, 255, 255, 0.08);
            --border-strong: rgba(255, 255, 255, 0.18);
        }

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: linear-gradient(180deg, #070707 0%, #0f0f0f 45%, #101010 100%);
            color: var(--text-primary);
            font-family: 'Roboto', sans-serif;
        }

        main {
            max-width: 1100px;
            margin: 0 auto;
            padding: 96px 24px 80px;
            display: flex;
            flex-direction: column;
            gap: 40px;
        }

        .page-header {
            text-align: center;
        }

        .page-eyebrow {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 6px 18px;
            border-radius: 999px;
            font-size: 13px;
            letter-spacing: 0.32em;
            text-transform: uppercase;
            background: var(--accent-soft);
            color: var(--accent);
        }

        .page-title {
            margin: 20px 0 14px;
            font-size: clamp(2.2rem, 3vw, 2.6rem);
        }

        .page-subtitle {
            margin: 0 auto;
            max-width: 620px;
            font-size: 15px;
            color: var(--text-muted);
            line-height: 1.6;
        }

        .alert {
            border-radius: 14px;
            padding: 16px 20px;
            border: 1px solid rgba(77, 191, 0, 0.28);
            background: rgba(77, 191, 0, 0.12);
            color: #d1fae5;
            display: flex;
            align-items: center;
            gap: 12px;
            font-size: 14px;
        }

        .profile-card {
            border-radius: 22px;
            border: 1px solid var(--border-soft);
            background: rgba(20, 20, 20, 0.92);
            padding: 32px;
            display: flex;
            flex-wrap: wrap;
            gap: 32px;
            align-items: center;
            box-shadow: 0 20px 48px rgba(0, 0, 0, 0.35);
        }

        .profile-card__avatar {
            width: 132px;
            height: 132px;
            border-radius: 50%;
            overflow: hidden;
            border: 3px solid rgba(77, 191, 0, 0.4);
            box-shadow: 0 12px 24px rgba(77, 191, 0, 0.18);
            flex-shrink: 0;
        }

        .profile-card__avatar img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .profile-card__body {
            flex: 1 1 280px;
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .profile-card__body h2 {
            margin: 0;
            font-size: 24px;
            font-weight: 600;
        }

        .profile-card__body p {
            margin: 0;
            font-size: 14px;
            color: var(--text-muted);
        }

        .badge-row {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
            margin-top: 8px;
        }

        .badge {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 6px 16px;
            border-radius: 999px;
            font-size: 13px;
            letter-spacing: 0.08em;
            text-transform: uppercase;
            background: rgba(255, 255, 255, 0.08);
        }

        .badge--accent {
            background: rgba(77, 191, 0, 0.16);
            color: var(--accent);
        }

        .badge--muted {
            color: var(--text-muted);
        }

        .details-panel {
            border-radius: 20px;
            border: 1px solid var(--border-soft);
            background: rgba(18, 18, 18, 0.86);
            padding: 28px 32px;
            display: flex;
            flex-direction: column;
            gap: 18px;
        }

        .panel-title {
            margin: 0;
            font-size: 18px;
            letter-spacing: 0.08em;
            text-transform: uppercase;
        }

        .details-list {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 20px 32px;
        }

        .details-item {
            display: flex;
            flex-direction: column;
            gap: 8px;
            border-bottom: 1px solid var(--border-soft);
            padding-bottom: 16px;
        }

        .details-item:last-child {
            border-bottom: none;
            padding-bottom: 0;
        }

        .details-item span.label {
            font-size: 13px;
            letter-spacing: 0.12em;
            text-transform: uppercase;
            color: var(--text-muted);
        }

        .details-item span.value {
            font-size: 15px;
            font-weight: 500;
            color: var(--text-primary);
        }

        .actions {
            display: flex;
            flex-wrap: wrap;
            gap: 16px;
        }

        .action-btn {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 12px 20px;
            border-radius: 999px;
            font-size: 14px;
            font-weight: 600;
            letter-spacing: 0.06em;
            text-transform: uppercase;
            border: 1px solid transparent;
            text-decoration: none;
            color: var(--text-primary);
            transition: transform 0.3s ease, box-shadow 0.3s ease, border-color 0.3s ease;
        }

        .action-btn--primary {
            background: linear-gradient(120deg, #4dbf00 0%, #68f038 100%);
            color: #031201;
        }

        .action-btn--secondary {
            border-color: var(--border-strong);
            background: rgba(18, 18, 18, 0.74);
        }

        .action-btn--outline {
            border-color: rgba(77, 191, 0, 0.35);
            color: var(--accent);
            background: rgba(77, 191, 0, 0.08);
        }

        .action-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 20px 34px rgba(77, 191, 0, 0.18);
        }

        .action-btn--secondary:hover {
            box-shadow: 0 18px 28px rgba(0, 0, 0, 0.35);
            border-color: rgba(255, 255, 255, 0.2);
        }

        .action-btn--outline:hover {
            box-shadow: 0 18px 32px rgba(77, 191, 0, 0.16);
        }

        @media (max-width: 768px) {
            main {
                padding: 88px 18px 64px;
            }

            .profile-card {
                padding: 26px;
            }

            .details-list {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<%@include file="/includes/header.jsp" %>

<main>
    <header class="page-header">
        <span class="page-eyebrow">Profile</span>
        <h1 class="page-title">Your Streamly Identity</h1>
        <p class="page-subtitle">Manage personal details, keep contact information current, and stay on top of your subscription status in a single glance.</p>
    </header>

    <c:if test="${not empty message}">
        <div class="alert">
            <i class='bx bx-info-circle'></i>
            <span>${message}</span>
        </div>
    </c:if>

    <section class="profile-card">
        <div class="profile-card__avatar">
            <img src="https://ui-avatars.com/api/?name=${user.username}&size=220&background=0D8ABC&color=fff" alt="Avatar">
        </div>
        <div class="profile-card__body">
            <h2>${user.username}</h2>
            <p>Member since <fmt:formatDate value="${user.created_at}" pattern="dd/MM/yyyy"/></p>

            <div class="badge-row">
                <span class="badge badge--accent"><i class='bx bx-user'></i> Streamly Member</span>
                <c:choose>
                    <c:when test="${not empty user.activeSubscription}">
                        <span class="badge badge--accent">
                            <i class='bx bx-badge-check'></i>
                            ${user.activeSubscription.planName}
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge badge--muted"><i class='bx bx-pause-circle'></i> No active plan</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </section>

    <section class="details-panel">
        <h2 class="panel-title">Account Details</h2>
        <div class="details-list">
            <div class="details-item">
                <span class="label">Full name</span>
                <span class="value">${user.fullName}</span>
            </div>
            <div class="details-item">
                <span class="label">Email</span>
                <span class="value">${user.email}</span>
            </div>
            <div class="details-item">
                <span class="label">Phone number</span>
                <span class="value">
                    <c:choose>
                        <c:when test="${not empty user.phoneNumber}">${user.phoneNumber}</c:when>
                        <c:otherwise>Not provided</c:otherwise>
                    </c:choose>
                </span>
            </div>
            <div class="details-item">
                <span class="label">Subscription</span>
                <span class="value">
                    <c:choose>
                        <c:when test="${not empty user.activeSubscription}">
                            ${user.activeSubscription.planName}
                            -
                            <fmt:formatNumber value="${user.activeSubscription.cost}" type="currency"/>
                            <c:choose>
                                <c:when test="${user.activeSubscription.duration == -1}">
                                    (lifetime access)
                                </c:when>
                                <c:otherwise>
                                    (expires <fmt:formatDate value="${user.activeSubscription.expiryDateAsDate}" pattern="dd/MM/yyyy"/>)
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>None</c:otherwise>
                    </c:choose>
                </span>
            </div>
        </div>
    </section>

    <div class="actions">
        <a href="profile?action=edit" class="action-btn action-btn--primary"><i class='bx bx-edit-alt'></i> Update Profile</a>
        <a href="changepassword.jsp" class="action-btn action-btn--secondary"><i class='bx bx-lock-alt'></i> Change Password</a>
        <a href="watchhistory" class="action-btn action-btn--outline"><i class='bx bx-history'></i> Watch History</a>
    </div>
</main>

<%@include file="/includes/footer.jsp" %>
</body>
</html>
