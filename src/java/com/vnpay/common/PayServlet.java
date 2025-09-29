// ✅ Đây là servlet "pay" dùng để xử lý việc mua gói subscription qua VNPAY
// ✅ Đã chỉnh sửa từ code mẫu của VNPAY để phù hợp với project streaming film
// ✅ Đã thêm comment chi tiết để bạn dễ hiểu từng bước
package com.vnpay.common;

import com.vnpay.common.Config;
import dao.PaymentTransactionDAO;
import dao.SubscriptionPlanDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.subscription.PaymentTransaction;
import model.subscription.SubscriptionPlan;
import model.user.Viewer;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

//@WebServlet(name = "PayServlet", urlPatterns = "/pay")
//public class PayServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        Viewer viewer = (Viewer) session.getAttribute("user");
//        if (viewer == null) {
//            resp.sendRedirect("userlogin.jsp");
//            return;
//        }
//
//        String planIdStr = req.getParameter("planId");
//        if (planIdStr == null) {
//            resp.sendRedirect("subscriptionPlan.jsp?error=Missing+plan+id");
//            return;
//        }
//        int planId = Integer.parseInt(planIdStr);
//
//        //  Lấy gói đăng ký từ DB
//        SubscriptionPlanDAO planDAO = new SubscriptionPlanDAO();
//        SubscriptionPlan plan = planDAO.getPlanById(planId);
//
//        if (plan == null) {
//            List<SubscriptionPlan> plans = planDAO.getAllPlans();
//            req.setAttribute("plans", plans);  // Gửi lại danh sách gói
//            req.setAttribute("error", "Gói đăng ký không hợp lệ.");  // Gửi thông báo lỗi
//            req.getRequestDispatcher("subscriptionPlan.jsp").forward(req, resp);  // forward thay vì redirect
//            return;
//        }
//
//        double amountDouble = plan.getCost();
//
//        String vnp_TxnRef = UUID.randomUUID().toString(); // ma giao dich
//
//        PaymentTransaction tx = new PaymentTransaction(
//                viewer.getUser_id(),
//                planId,
//                vnp_TxnRef,
//                amountDouble,
//                "PENDING"
//        );
//
//        PaymentTransactionDAO txDAO = new PaymentTransactionDAO();
//        txDAO.insert(tx);
//
//        String vnp_Version = "2.1.0";
//        String vnp_Command = "pay";
//        String orderType = "billpayment";
//        long amount = (long) (amountDouble * 100);
//        String vnp_TmnCode = Config.vnp_TmnCode;
//        String vnp_IpAddr = Config.getIpAddress(req);
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", vnp_Version);
//        vnp_Params.put("vnp_Command", vnp_Command);
//        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(amount));
//        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.put("vnp_OrderInfo", "Thanh toan goi " + plan.getName());
//        vnp_Params.put("vnp_OrderType", orderType);
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
//            String fieldName = itr.next();
//            String fieldValue = vnp_Params.get(fieldName);
//            if (fieldValue != null && !fieldValue.isEmpty()) {
//                hashData.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
//                        .append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                if (itr.hasNext()) {
//                    hashData.append('&');
//                    query.append('&');
//                }
//            }
//        }
//        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
//        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
//
//        //  Gửi user đến trang thanh toán của VNPAY
//        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();
//        resp.sendRedirect(paymentUrl);
//    }
//}
@WebServlet(name = "PayServlet", urlPatterns = "/pay")
public class PayServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy người dùng từ session
        HttpSession session = req.getSession();
        Viewer viewer = (Viewer) session.getAttribute("user");
        if (viewer == null) {
            resp.sendRedirect("userlogin.jsp");
            return;
        }

        // Lấy planId từ request
        String planIdStr = req.getParameter("planId");
        if (planIdStr == null) {
            resp.sendRedirect("subscriptionPlan.jsp?error=Missing+plan+id");
            return;
        }

        int planId = Integer.parseInt(planIdStr);

        // Lấy thông tin gói từ DB
        SubscriptionPlanDAO planDAO = new SubscriptionPlanDAO();
        SubscriptionPlan plan = planDAO.getPlanById(planId);
        if (plan == null) {
            resp.sendRedirect("subscriptionPlan.jsp?error=Invalid+plan");
            return;
        }

        double amountDouble = plan.getCost();

        // Tạo mã giao dịch ngẫu nhiên
        String vnp_TxnRef = UUID.randomUUID().toString();

        // Tạo giao dịch và lưu vào DB
        PaymentTransaction tx = new PaymentTransaction(
                viewer.getUser_id(), // ID người dùng
                planId,
                vnp_TxnRef,
                amountDouble,
                "PENDING"
        );
        PaymentTransactionDAO txDAO = new PaymentTransactionDAO();
        txDAO.insert(tx);

        // Bắt đầu chuẩn bị gửi request tới VNPAY
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TmnCode = Config.vnp_TmnCode;
        String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_OrderInfo = "Payment subscription " + plan.getName();
        String vnp_OrderType = "billpayment";
        String vnp_Locale = "vn";
        String vnp_ReturnUrl = Config.vnp_ReturnUrl;
        long vnp_Amount = (long) (amountDouble * 100); // Nhân 100 theo yêu cầu của VNPAY

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Tạo ngày tạo và ngày hết hạn
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Tạo chuỗi hash để bảo mật
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (Iterator<String> it = fieldNames.iterator(); it.hasNext(); ) {
            String fieldName = it.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                     .append('=')
                     .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (it.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        // Sinh mã secure hash
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        // Tạo URL thanh toán và redirect sang VNPAY
        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();
        resp.sendRedirect(paymentUrl);
    }

}