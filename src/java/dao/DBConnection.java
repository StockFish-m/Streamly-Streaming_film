
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    public static final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private static String env(String key, String defVal) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) v = System.getProperty(key); 
        return (v == null || v.isBlank()) ? defVal : v;
    }
    private static boolean envBool(String key, boolean defVal) {
        String v = env(key, Boolean.toString(defVal));
        return v.equalsIgnoreCase("true") || v.equalsIgnoreCase("1") || v.equalsIgnoreCase("yes");
    }

    public static final String DB_HOST = env("DB_HOST", "localhost");
    public static final String DB_PORT = env("DB_PORT", "1433");
    public static final String DB_NAME = env("DB_NAME", "Streamly");
    public static final String DB_USER = env("DB_USER", "sa");
    public static final String DB_PASS = env("DB_PASS", "yourStrong(!)Password");
    public static final boolean DB_ENCRYPT = envBool("DB_ENCRYPT", true);
    public static final boolean DB_TRUST_SERVER_CERT = envBool("DB_TRUST_SERVER_CERT", true);

    public static final String dbURL = String.format(
        "jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=%s;trustServerCertificate=%s;loginTimeout=30;",
        DB_HOST, DB_PORT, DB_NAME, DB_ENCRYPT, DB_TRUST_SERVER_CERT
    );
    
    public static Connection getConnection() {
          Connection con = null;
          try {
              Class.forName(driverName);
              con = DriverManager.getConnection(dbURL, DB_USER, DB_PASS);
              return con;
          } catch (Exception ex) {
              System.out.println(">>> DB connection failed: " + ex.getMessage());
              ex.printStackTrace(); // VERY important
          }
          return null;
      }


    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            if (con != null) {
                System.out.println("Connect to Streamly Success");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
