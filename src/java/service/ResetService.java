/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


public class ResetService {
    private final int LIMIT_MINUS = 10;
  

    private static String env(String key, String defVal) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) v = System.getProperty(key);
        return (v == null || v.isBlank()) ? defVal : v;
    }

    static final String MAIL_HOST = env("MAIL_HOST", "smtp.gmail.com");
    static final String MAIL_PORT = env("MAIL_PORT", "587");
    static final String MAIL_USER = env("MAIL_USERNAME", "");
    static final String MAIL_PASS = env("MAIL_PASSWORD", "");
    static final String MAIL_FROM = env("MAIL_FROM", "Streamly <no-reply@local>");

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
    
    public LocalDateTime expireDateTime() {
        return LocalDateTime.now().plusMinutes(LIMIT_MINUS);
    }
    
    public boolean isExpireTime(LocalDateTime time) {
        return LocalDateTime.now().isAfter(time);
    }
    
    
    public boolean sendEmail(String to, String link, String name) {
        Properties props = new Properties();
        props.put("mail.smtp.host",  MAIL_HOST);
        props.put("mail.smtp.port", MAIL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_USER, MAIL_PASS);
            }
        };
        
        Session session = Session.getInstance(props, auth);
        
        MimeMessage msg = new MimeMessage(session);
        
        try {
            msg.addHeader("Content-type", "text/html; charset=UTF-8");
            
              InternetAddress fromAddr;
            if (MAIL_FROM.contains("<") && MAIL_FROM.contains(">")) {
                String personal = MAIL_FROM.substring(0, MAIL_FROM.indexOf('<')).trim();
                String email = MAIL_FROM.substring(MAIL_FROM.indexOf('<') + 1, MAIL_FROM.indexOf('>')).trim();
                fromAddr = new InternetAddress(email, personal);
            } else {
                fromAddr = new InternetAddress(MAIL_FROM);
            }
            
            msg.setFrom(fromAddr);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject("Reset Password", "UTF-8");
//            String content = "<h1>Hello "+name+"</h1>"+"<p>Click the link to reset password: "
//                    + "<a href="+link+">Click here</a></p>";
    String content = ""
        + "<p>Hi <strong>" + name + "</strong>,</p>"
        + "<p>We received a request to reset the password for your account on <strong>Streamly</strong>. "
        + "If you made this request, please click the link below to reset your password:</p>"
        + "<p><a href=\"" + link + "\" style=\"color: #1a73e8; text-decoration: none; font-weight: bold;\">Reset your password</a></p>"
        + "<h4>Note:</h4>"
        + "<ul>"
        + "<li>The above link will expire in <strong>10 minutes</strong> from the time of request.</li>"
        + "<li>If you did not request a password reset, please ignore this email. Your account remains secure and no changes have been made.</li>"
        + "</ul>"
        + "<p>If you need further assistance, feel free to contact us via <a href=\"https://www.facebook.com/k.en.ai.6789" +"\">Facebook</a>.</p>"
        + "<p>Thank you for using <strong>Streamly</strong>!</p>";
            msg.setContent(content, "text/html; charset=UTF-8");
            Transport.send(msg);
            System.out.println("Send successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Send error");
            System.out.println(e);
            return false;
        }
    }
}
