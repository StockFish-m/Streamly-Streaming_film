<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.subscription.SubscriptionPlan" %>

<%
    List<SubscriptionPlan> plans = (List<SubscriptionPlan>) request.getAttribute("plans");
    if (plans == null) {
        response.sendRedirect("subscription");
        return;
    }
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Streamly | Plans</title>

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
            margin: 18px 0 14px;
            font-size: clamp(2rem, 3vw, 2.5rem);
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
            border: 1px solid rgba(239, 68, 68, 0.32);
            background: rgba(239, 68, 68, 0.14);
            color: #fecaca;
            display: flex;
            align-items: center;
            gap: 12px;
            font-size: 14px;
        }

        .plan-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
            gap: 24px;
        }

        .plan-card {
            border-radius: 20px;
            border: 1px solid var(--border-soft);
            background: rgba(20, 20, 20, 0.88);
            padding: 26px;
            display: flex;
            flex-direction: column;
            gap: 22px;
            box-shadow: 0 18px 32px rgba(0, 0, 0, 0.28);
            transition: transform 0.3s ease, border-color 0.3s ease, box-shadow 0.3s ease;
        }

        .plan-card:hover {
            transform: translateY(-6px);
            border-color: rgba(77, 191, 0, 0.45);
            box-shadow: 0 22px 36px rgba(77, 191, 0, 0.18);
        }

        .plan-name {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            font-size: 18px;
            font-weight: 600;
            letter-spacing: 0.04em;
            text-transform: uppercase;
        }

        .plan-name i {
            font-size: 22px;
            color: var(--accent);
        }

        .plan-amount {
            font-size: 32px;
            font-weight: 700;
        }

        .plan-amount small {
            display: block;
            margin-top: 4px;
            font-size: 13px;
            font-weight: 400;
            color: var(--text-muted);
            letter-spacing: 0.1em;
            text-transform: uppercase;
        }

        .plan-features {
            display: flex;
            flex-direction: column;
            gap: 8px;
            font-size: 14px;
            color: var(--text-muted);
        }

        .plan-features span {
            display: inline-flex;
            align-items: center;
            gap: 10px;
        }

        .plan-features i {
            color: var(--accent);
        }

        .primary-btn {
            margin-top: auto;
            align-self: flex-start;
            padding: 12px 26px;
            border-radius: 999px;
            border: none;
            cursor: pointer;
            background: linear-gradient(120deg, #4dbf00 0%, #68f038 100%);
            color: #031201;
            font-size: 14px;
            font-weight: 600;
            letter-spacing: 0.08em;
            text-transform: uppercase;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .primary-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 20px 34px rgba(104, 240, 56, 0.25);
        }

        @media (max-width: 768px) {
            main {
                padding: 88px 18px 64px;
            }
        }
    </style>
</head>
<body>
<%@ include file="/includes/header.jsp" %>

<main>
    <header class="page-header">
        <span class="page-eyebrow">Plans</span>
        <h1 class="page-title">Pick The Perfect Fit</h1>
        <p class="page-subtitle">Flexible options priced for every kind of viewer. Upgrade or downgrade whenever you like-streaming stays seamless.</p>
    </header>

    <% if (error != null) { %>
        <div class="alert">
            <i class='bx bx-error-circle'></i>
            <span><strong>Error:</strong> <%= error %></span>
        </div>
    <% } %>

    <section class="plan-grid">
        <% for (SubscriptionPlan plan : plans) { %>
            <form action="pay" method="post" class="plan-card">
                <input type="hidden" name="planId" value="<%= plan.getId() %>">

                <div>
                    <span class="plan-name"><i class='bx bx-bolt-circle'></i><%= plan.getName() %></span>
                </div>

                <div class="plan-amount">
                    <%= String.format("%,.0f VND", plan.getCost()) %>
                    <small>For <%= plan.getBaseDuration() %> days</small>
                </div>

                <div class="plan-features">
                    <span><i class='bx bx-tv'></i> Unlimited streaming in HD</span>
                    <span><i class='bx bx-user-voice'></i> Multiple profiles supported</span>
                    <span><i class='bx bx-refresh'></i> Auto-renew with easy cancellation</span>
                </div>

                <button type="submit" class="primary-btn">Purchase</button>
            </form>
        <% } %>
    </section>
</main>

<%@ include file="/includes/footer.jsp" %>
</body>
</html>
