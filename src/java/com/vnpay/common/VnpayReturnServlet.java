
package com.vnpay.common ;

import com.vnpay.common.Config;
import dao.PaymentTransactionDAO;
import dao.ViewerSubscriptionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import model.subscription.PaymentTransaction;

@WebServlet(name = "VnpayReturnServlet", urlPatterns = "/vnpay-return")
public class VnpayReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

           response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            
        

        // ✅ Xác thực chữ ký của VNPAY
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        String signValue = Config.hashAllFields(fields);

        if (signValue.equals(vnp_SecureHash)) {
            // ✅ Hợp lệ
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");
            String statusCode = request.getParameter("vnp_TransactionStatus");
            String responeCode = request.getParameter("vnp_TransactionNo");
            
            PaymentTransactionDAO txDAO = new PaymentTransactionDAO();
            PaymentTransaction tx = txDAO.getByTxnRef(vnp_TxnRef);

            boolean success = false;
            if (tx != null) {
                if ("00".equals(statusCode)) {
                  
                    txDAO.updateStatus(vnp_TxnRef, "SUCCESS", responeCode);
                    ViewerSubscriptionDAO subDAO = new ViewerSubscriptionDAO();
                    subDAO.createSubscriptionFromTransaction(tx);
                    success = true;
                } else {
                    //  Giao dịch thất bại
                    txDAO.updateStatus(vnp_TxnRef, "FAIL", responeCode);
                }
            }

            request.setAttribute("transResult", success);
            request.getRequestDispatcher("paymentResult.jsp").forward(request, response);

        } else {
            response.getWriter().println("invalid signature");
        }
    }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // chuyển POST → GET
    }
}
         