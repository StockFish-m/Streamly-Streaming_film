/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = {"/manager/*"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();
        String ctx = req.getContextPath();

        // ✅ Allow access to admin login page
        if (path.equals(ctx + "/manager/adminlogin.jsp") || path.equals(ctx + "/manager/adminlogin")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Object userObj = (session != null) ? session.getAttribute("employee") : null;

        if (userObj instanceof model.user.Employee) {
            chain.doFilter(request, response); // ✅ Admin logged in → allow
        } else {
            resp.sendRedirect(ctx + "/manager/adminlogin.jsp"); // ❌ Not admin → deny
        }
    }
}
