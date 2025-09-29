package servlet;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.user.Employee;
import model.user.EmployeeRole;

import java.io.IOException;

@WebServlet("/EmployeeLogin")
public class EmployeeLogin extends HttpServlet {

    private EmployeeDAO employeeDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = new EmployeeDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usernameOrEmail = request.getParameter("username");
        String password = request.getParameter("password");
        String roleIdParam = request.getParameter("roleId");

        // ✅ Kiểm tra dữ liệu đầu vào
        if (usernameOrEmail == null || password == null || roleIdParam == null ||
                usernameOrEmail.trim().isEmpty() || password.trim().isEmpty() || roleIdParam.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin đăng nhập và chọn vai trò.");
            request.getRequestDispatcher("Employee.jsp").forward(request, response);
            return;
        }

        int roleId;
        try {
            roleId = Integer.parseInt(roleIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Vai trò phải là số hợp lệ.");
            request.getRequestDispatcher("Employee.jsp").forward(request, response);
            return;
        }

        EmployeeRole selectedRole;
        try {
            selectedRole = EmployeeRole.fromId(roleId);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Vai trò không tồn tại.");
            request.getRequestDispatcher("Employee.jsp").forward(request, response);
            return;
        }

        // ✅ Xác thực tài khoản
        Employee employee = employeeDAO.login(usernameOrEmail, password);

        if (employee == null) {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            request.getRequestDispatcher("Employee.jsp").forward(request, response);
            return;
        }

        // ✅ Kiểm tra quyền truy cập
        if (!employee.hasRole(selectedRole)) {
            request.setAttribute("error", "Bạn không có quyền: " + selectedRole.getDescription());
            request.getRequestDispatcher("Employee.jsp").forward(request, response);
            return;
        }

        // ✅ Lưu session và chuyển hướng
        HttpSession session = request.getSession();
        session.setAttribute("loggedInEmployee", employee);
        session.setAttribute("employeeRole", selectedRole);

        response.sendRedirect("main.jsp"); 
    }
}
