<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

    <%@include file="/includes/header.jsp" %>
    

<div class="container py-5" style="max-width: 650px;">
    <h1 class="mb-4 text-center"><i class="bi bi-pencil-square me-2"></i>Edit Profile</h1>

    <form action="profile" method="post" class="row g-3 bg-white p-4 shadow-sm rounded">
        <input type="hidden" name="action" value="update"/>

        <div class="col-md-6">
            <label class="form-label fw-semibold">Full name</label>
            <input type="text" name="fullName" class="form-control" value="${user.fullName}" required>
        </div>

        <div class="col-md-6">
            <label class="form-label fw-semibold">Email</label>
            <input type="email" name="email" class="form-control" value="${user.email}" required>
        </div>

        <div class="col-md-6">
            <label class="form-label fw-semibold">Phone number</label>
            <input type="text" name="phoneNumber" class="form-control" value="${user.phoneNumber}">
        </div>

        <div class="col-12 mt-3 d-flex justify-content-end gap-2">
            <a href="profile" class="btn btn-outline-secondary"><i class="bi bi-arrow-left"></i> Back</a>
            <button type="submit" class="btn btn-success"><i class="bi bi-save me-1"></i> Save Changes</button>
        </div>
    </form>
</div>


<%@include file="/includes/footer.jsp" %>     


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 