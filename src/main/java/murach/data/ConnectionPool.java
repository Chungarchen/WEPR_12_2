package murach.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool pool = null;
    
    // Thông tin kết nối MySQL
    private static final String URL = "jdbc:postgresql://dpg-d3gabe9r0fns73bepsa0-a.oregon-postgres.render.com:5432/sql2_db";
    private static final String USER = "sql2_db_user";   // username Render cấp
    private static final String PASSWORD = "tKphsIdvdegP8XNxXelJjzKOgAWVgaag"; // thay bằng password trong Render dashboard


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
