<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Streamly | Edit Profile</title>

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
            --border-soft: rgba(255, 255, 255, 0.12);
        }

        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: radial-gradient(circle at top, rgba(77, 191, 0, 0.2) 0%, rgba(7, 7, 7, 1) 45%, rgba(9, 9, 9, 1) 100%);
            color: var(--text-primary);
            font-family: 'Roboto', sans-serif;
        }

        main {
            max-width: 620px;
            margin: 0 auto;
            padding: 96px 24px 80px;
            display: flex;
            flex-direction: column;
            gap: 32px;
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
            font-size: clamp(2rem, 3vw, 2.5rem);
        }

        .page-subtitle {
            margin: 0 auto;
            max-width: 480px;
            font-size: 15px;
            color: var(--text-muted);
            line-height: 1.6;
        }

        .edit-form {
            border-radius: 20px;
            border: 1px solid var(--border-soft);
            background: rgba(20, 20, 20, 0.9);
            padding: 32px;
            display: flex;
            flex-direction: column;
            gap: 20px;
            box-shadow: 0 20px 42px rgba(0, 0, 0, 0.32);
        }

        .field-group {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .field-group label {
            font-size: 13px;
            letter-spacing: 0.12em;
            text-transform: uppercase;
            color: var(--text-muted);
        }

        .field-group input {
            padding: 14px 16px;
            border-radius: 12px;
            border: 1px solid rgba(255, 255, 255, 0.12);
            background: rgba(12, 12, 12, 0.9);
            color: var(--text-primary);
            font-size: 15px;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        .field-group input:focus {
            outline: none;
            border-color: rgba(77, 191, 0, 0.6);
            box-shadow: 0 0 0 4px rgba(77, 191, 0, 0.16);
        }

        .form-actions {
            display: flex;
            flex-wrap: wrap;
            gap: 14px;
            justify-content: flex-end;
            margin-top: 12px;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 12px 22px;
            border-radius: 999px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
            letter-spacing: 0.06em;
            text-transform: uppercase;
            border: 1px solid transparent;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease, border-color 0.3s ease;
        }

        .btn-primary {
            background: linear-gradient(120deg, #4dbf00 0%, #68f038 100%);
            color: #031201;
        }

        .btn-secondary {
            border-color: rgba(255, 255, 255, 0.2);
            background: rgba(18, 18, 18, 0.7);
            color: var(--text-primary);
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 18px 32px rgba(77, 191, 0, 0.2);
        }

        .btn-secondary:hover {
            box-shadow: 0 16px 28px rgba(0, 0, 0, 0.4);
            border-color: rgba(255, 255, 255, 0.32);
        }

        @media (max-width: 600px) {
            main {
                padding: 88px 18px 64px;
            }

            .edit-form {
                padding: 28px;
            }

            .form-actions {
                justify-content: stretch;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
<%@include file="/includes/header.jsp" %>

<main>
    <header class="page-header">
        <span class="page-eyebrow">Profile</span>
        <h1 class="page-title">Edit Account Details</h1>
        <p class="page-subtitle">Keep your contact information current so Streamly can tailor recommendations and subscription updates for you.</p>
    </header>

    <form action="profile" method="post" class="edit-form">
        <input type="hidden" name="action" value="update">

        <div class="field-group">
            <label for="fullName">Full name</label>
            <input id="fullName" type="text" name="fullName" value="${user.fullName}" required>
        </div>

        <div class="field-group">
            <label for="email">Email</label>
            <input id="email" type="email" name="email" value="${user.email}" required>
        </div>

        <div class="field-group">
            <label for="phoneNumber">Phone number</label>
            <input id="phoneNumber" type="text" name="phoneNumber" value="${user.phoneNumber}">
        </div>

        <div class="form-actions">
            <a href="profile" class="btn btn-secondary"><i class='bx bx-arrow-back'></i> Back</a>
            <button type="submit" class="btn btn-primary"><i class='bx bx-save'></i> Save Changes</button>
        </div>
    </form>
</main>

<%@include file="/includes/footer.jsp" %>
</body>
</html>
