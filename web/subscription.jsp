<%@ page import="model.subscription.SubscriptionPlans" %>
<%@ page import="model.subscription.SubscriptionPlan" %>
<%@ page import="model.subscription.ViewerSubscription" %>
<%@ page import="model.user.Viewer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Viewer viewer = (Viewer) session.getAttribute("user");
    ViewerSubscription sub = (viewer != null) ? viewer.getActiveSubscription() : null;
    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Streamly | Subscription</title>

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
            --danger: #ef4444;
            --success: #22c55e;
        }

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: radial-gradient(circle at top, rgba(77, 191, 0, 0.18) 0%, rgba(11, 11, 11, 1) 45%, rgba(7, 7, 7, 1) 100%);
            color: var(--text-primary);
            font-family: 'Roboto', sans-serif;
        }

        main {
            max-width: 1100px;
            margin: 0 auto;
            padding: 96px 24px 80px;
            display: flex;
            flex-direction: column;
            gap: 48px;
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
            margin: 20px 0 16px;
            font-size: clamp(2rem, 3vw, 2.6rem);
        }

        .page-subtitle {
            margin: 0 auto;
            max-width: 620px;
            font-size: 15px;
            color: var(--text-muted);
            line-height: 1.6;
        }

        .status-card {
            border-radius: 18px;
            border: 1px solid var(--border-soft);
            background: rgba(18, 18, 18, 0.9);
            padding: 28px 32px;
            display: flex;
            flex-wrap: wrap;
            gap: 24px;
            align-items: center;
            box-shadow: 0 18px 40px rgba(0, 0, 0, 0.28);
        }

        .status-card__icon {
            width: 68px;
            height: 68px;
            border-radius: 18px;
            background: rgba(77, 191, 0, 0.14);
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-size: 34px;
            color: var(--accent);
        }

        .status-card__body {
            flex: 1 1 260px;
        }

        .status-card__title {
            margin: 0 0 6px;
            font-size: 20px;
            font-weight: 600;
        }

        .status-meta {
            margin: 0;
            font-size: 14px;
            color: var(--text-muted);
            display: flex;
            flex-direction: column;
            gap: 4px;
        }

        .chip {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 6px 14px;
            border-radius: 999px;
            background: rgba(255, 255, 255, 0.08);
            font-size: 13px;
            letter-spacing: 0.08em;
            text-transform: uppercase;
        }

        .chip--success {
            color: var(--accent);
            background: rgba(77, 191, 0, 0.16);
        }

        .chip--danger {
            color: #ff8181;
            background: rgba(239, 68, 68, 0.16);
        }

        .alert {
            border-radius: 14px;
            padding: 16px 20px;
            border: 1px solid;
            display: flex;
            align-items: center;
            gap: 12px;
            font-size: 14px;
        }

        .alert + .alert {
            margin-top: 16px;
        }

        .alert--error {
            background: rgba(239, 68, 68, 0.14);
            color: #fecaca;
            border-color: rgba(239, 68, 68, 0.32);
        }

        .alert--success {
            background: rgba(34, 197, 94, 0.14);
            color: #bbf7d0;
            border-color: rgba(34, 197, 94, 0.32);
        }

        .plans-section {
            display: flex;
            flex-direction: column;
            gap: 28px;
        }

        .plans-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            flex-wrap: wrap;
            gap: 16px;
        }

        .plans-title {
            margin: 0;
            font-size: 22px;
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .plans-title::before {
            content: '';
            width: 6px;
            height: 28px;
            border-radius: 14px;
            background: var(--accent);
        }

        .plans-caption {
            font-size: 13px;
            color: var(--text-muted);
            letter-spacing: 0.08em;
            text-transform: uppercase;
        }

        .plan-form {
            display: flex;
            flex-direction: column;
            gap: 24px;
        }

        .plan-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 24px;
        }

        .plan-card {
            position: relative;
            display: block;
            cursor: pointer;
        }

        .plan-card input[type="radio"] {
            position: absolute;
            opacity: 0;
            pointer-events: none;
        }

        .plan-content {
            position: relative;
            border-radius: 18px;
            border: 1px solid var(--border-soft);
            background: var(--bg-card);
            padding: 24px;
            display: flex;
            flex-direction: column;
            gap: 18px;
            transition: transform 0.3s ease, border-color 0.3s ease, box-shadow 0.3s ease;
        }

        .plan-card:hover .plan-content {
            transform: translateY(-6px);
            border-color: rgba(77, 191, 0, 0.45);
            box-shadow: 0 18px 32px rgba(0, 0, 0, 0.35);
        }

        .plan-content::after {
            content: "";
            position: absolute;
            top: 18px;
            right: 18px;
            width: 24px;
            height: 24px;
            border-radius: 50%;
            border: 2px solid rgba(255, 255, 255, 0.2);
            transition: all 0.3s ease;
        }

        .plan-card input[type="radio"]:checked + .plan-content {
            border-color: rgba(77, 191, 0, 0.8);
            box-shadow: 0 20px 38px rgba(77, 191, 0, 0.18);
        }

        .plan-card input[type="radio"]:checked + .plan-content::after {
            border-color: var(--accent);
            background: var(--accent);
            box-shadow: 0 0 0 6px rgba(77, 191, 0, 0.28);
        }

        .plan-offer {
            font-size: 13px;
            color: var(--accent);
            letter-spacing: 0.08em;
            text-transform: uppercase;
        }

        .plan-name {
            margin: 0;
            font-size: 18px;
            font-weight: 600;
            letter-spacing: 0.04em;
            text-transform: uppercase;
        }

        .plan-pricing {
            display: flex;
            align-items: baseline;
            gap: 8px;
        }

        .plan-amount {
            font-size: 28px;
            font-weight: 700;
        }

        .plan-duration {
            font-size: 13px;
            color: var(--text-muted);
            text-transform: uppercase;
            letter-spacing: 0.1em;
        }

        .plan-features {
            display: flex;
            flex-direction: column;
            gap: 6px;
            font-size: 13px;
            color: var(--text-muted);
        }

        .plan-features span {
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }

        .primary-btn {
            align-self: flex-end;
            padding: 14px 34px;
            border-radius: 999px;
            border: none;
            cursor: pointer;
            background: linear-gradient(120deg, #4dbf00 0%, #68f038 100%);
            color: #031201;
            font-size: 15px;
            font-weight: 600;
            letter-spacing: 0.08em;
            text-transform: uppercase;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .primary-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 20px 38px rgba(104, 240, 56, 0.25);
        }

        @media (max-width: 768px) {
            main {
                padding: 88px 18px 64px;
            }

            .status-card {
                padding: 24px;
            }
        }

        @media (max-width: 540px) {
            .plan-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<%@include file="/includes/header.jsp" %>

<main>
    <header class="page-header">
        <span class="page-eyebrow">Subscription</span>
        <h1 class="page-title">Streaming Without Limits</h1>
        <p class="page-subtitle">Choose the best plan for your watch habits and enjoy Streamly across your favorite devices. Upgrade, pause, or come back whenever you like.</p>
    </header>

    <section class="status-card">
        <div class="status-card__icon">
            <i class='bx bx-badge-check'></i>
        </div>
        <div class="status-card__body">
            <h2 class="status-card__title">Your Current Subscription</h2>
            <p class="status-meta">
                <% if (sub != null) { %>
                    <span><strong>Plan:</strong> <%= (sub.getPlanName() != null) ? sub.getPlanName() : "Unknown" %></span>
                    <span><strong>Expires:</strong> <%= sub.getExpiryDate() %></span>
                <% } else { %>
                    <span>You do not have an active subscription yet. Start one below to unlock the full catalog.</span>
                <% } %>
            </p>
        </div>
        <div>
            <% if (sub != null && sub.isActive()) { %>
                <span class="chip chip--success"><i class='bx bx-play-circle'></i> Active</span>
            <% } else if (sub != null) { %>
                <span class="chip chip--danger"><i class='bx bx-time-five'></i> Expired</span>
            <% } else { %>
                <span class="chip"><i class='bx bx-dots-horizontal-rounded'></i> Pending</span>
            <% } %>
        </div>
    </section>

    <% if (error != null || success != null) { %>
        <div>
            <% if (error != null) { %>
                <div class="alert alert--error">
                    <i class='bx bx-error-circle'></i>
                    <span><strong>Error:</strong> <%= error %></span>
                </div>
            <% } %>
            <% if (success != null) { %>
                <div class="alert alert--success">
                    <i class='bx bx-check-circle'></i>
                    <span>Subscription updated successfully.</span>
                </div>
            <% } %>
        </div>
    <% } %>

    <section class="plans-section">
        <div class="plans-header">
            <h2 class="plans-title">Choose Your Plan</h2>
            <span class="plans-caption">Cancel anytime | Plans renew automatically</span>
        </div>

        <form action="subscribe" method="post" class="plan-form">
            <div class="plan-grid">
                <%
                    for (int id = 1; id <= 5; id++) {
                        SubscriptionPlan plan = SubscriptionPlans.getById(id);
                        if (plan != null) {
                %>
                    <label class="plan-card">
                        <input type="radio" name="plan_id" value="<%= plan.getId() %>" required>
                        <div class="plan-content">
                            <div class="plan-offer"><i class='bx bx-bolt'></i> Stream without limits</div>
                            <h3 class="plan-name"><%= plan.getName() != null ? plan.getName().toUpperCase() : "PLAN" %></h3>
                            <div class="plan-pricing">
                                <span class="plan-amount"><%= String.format("$%.2f", plan.getCost()) %></span>
                                <span class="plan-duration">per <%= plan.getBaseDuration() %> days</span>
                            </div>
                            <div class="plan-features">
                                <span><i class='bx bx-tv'></i> Watch on any device</span>
                                <span><i class='bx bx-download'></i> Download for offline</span>
                            </div>
                        </div>
                    </label>
                <%
                        }
                    }
                %>
            </div>

            <button class="primary-btn" type="submit">Activate Plan</button>
        </form>
    </section>
</main>

<%@include file="/includes/footer.jsp" %>
</body>
</html>
