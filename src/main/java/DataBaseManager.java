import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DataBaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    private static Connnection connnection;

    private DataBaseManager() {
    }

    public static Connection getConnection() {
        if (connnection == null) {
            try {
                connnection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return connnection;
    }

    public static void closeConnection() throws SQLException {
        if (connnection != null) {
            connnection.close();
        }
    }

}

