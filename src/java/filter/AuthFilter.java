package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();
        String ctx = req.getContextPath(); // ví dụ: /testS

        // ✅ Đường dẫn không cần kiểm tra login
        boolean isPublicPath =
                path.equals(ctx + "/userlogin.jsp") ||
                path.equals(ctx + "/signup.jsp") ||
                path.equals(ctx + "/userlogin") ||
                path.equals(ctx + "/signup") ||
                path.equals(ctx + "/request_password") || 
                path.equals(ctx + "/reset_password") ||
                path.equals(ctx + "/home.jsp") ||
                path.startsWith(ctx + "/manager/") ||
                path.endsWith("home.jsp") || path.endsWith("home") ||
                path.endsWith(".css") || path.endsWith(".js") ||
                path.endsWith(".png") || path.endsWith(".jpg") ||
                path.endsWith(".jpeg") || path.endsWith(".gif") ||
                path.endsWith(".woff") || path.endsWith(".ttf") || path.endsWith(".svg");

        if (isPublicPath) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ Kiểm tra session
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(request, response); // Đã đăng nhập → tiếp tục
        } else {
            resp.sendRedirect(ctx + "/userlogin.jsp"); // Chưa đăng nhập → chuyển hướng
        }
    }
}


