import java.math.BigDecimal;
import  java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class DataBaseReader {
    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";


    public static void main(String[] args) {
        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Perform INSERT operation
            insertTeacher(connection, 11, "Tom", "Hanks", "Maplewood School", "2021-08-20", 57000.00);

            // Perform SELECT operation
            selectTeachers(connection);

            // Perform UPDATE operation
            updateTeacherSalary(connection, 11, 59000.00);

            // Perform DELETE operation
            deleteTeacher(connection, 11);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertTeacher(Connection connection, int id, String firstName, String lastName, String school, String hireDate, double salary) throws SQLException {
        String insertSQL = "INSERT INTO teachers (id, first_name, last_name, school, hire_date, salary) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, school);
            pstmt.setDate(5, Date.valueOf(hireDate));
            pstmt.setBigDecimal(6, BigDecimal.valueOf(salary));
            int rowsInserted = pstmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        }
    }

    private static void selectTeachers(Connection connection) throws SQLException {
        String selectSQL = "SELECT * FROM teachers";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String school = rs.getString("school");
                Date hireDate = rs.getDate("hire_date");
                BigDecimal salary = rs.getBigDecimal("salary");

                System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", School: " + school + ", Hire Date: " + hireDate + ", Salary: " + salary);
            }
        }
    }

    private static void updateTeacherSalary(Connection connection, int id, double newSalary) throws SQLException {
        String updateSQL = "UPDATE teachers SET salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setBigDecimal(1, BigDecimal.valueOf(newSalary));
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        }
    }

    private static void deleteTeacher(Connection connection, int id) throws SQLException {
        String deleteSQL = "DELETE FROM teachers WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        }
    }


}
