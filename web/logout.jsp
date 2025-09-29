<%-- 
    Document   : logout
    Created on : Jun 27, 2025, 2:49:02 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName()) || "password".equals(cookie.getName())) {
                cookie.setMaxAge(0); // Xóa cookie
                cookie.setPath("/"); // Đảm bảo xóa đúng domain
                response.addCookie(cookie);
            }
        }
    }

    // Chuyển về trang đăng nhập
    response.sendRedirect("userlogin.jsp");
%>

