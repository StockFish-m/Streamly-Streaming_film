package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

public class UserRegisterListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // app start
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // app stop
    }

    // Lắng nghe ghi log đăng ký
    public static void logNewUser(String username) {
        System.out.println("New user registered: " + username);
    }
}
