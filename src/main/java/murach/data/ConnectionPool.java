package murach.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool pool = null;
    
    // Thông tin kết nối MySQL
    private static final String URL = "jdbc:postgresql://dpg-d3h6s42li9vc73dsuck0-a.oregon-postgres.render.com:5432/werp";
    private static final String USER = "werp_user";   // username Render cấp
    private static final String PASSWORD = "fCkKHCr6Sk9ZhXS3FKKjlzcQ0NBemfVD"; // thay bằng password trong Render dashboard


    private ConnectionPool() {
        try {
            // Load PostgreSQL Driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    // Lấy kết nối
     public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Đóng kết nối
    public void freeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
