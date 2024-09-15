package com.example.register.model.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseConnection {
    static String url = "jdbc:mysql://localhost:3306/SanChoi247";
    static String username = "root";
    static String password = "thithithi0305";
    static String nameClass = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        // Thông tin kết nối
        // String url = "jdbc:mysql://localhost:3306/SanChoi247";
        // String username = "root";
        // String password = "thithithi0305";
        // Kết nối tới cơ sở dữ liệu
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (connection != null) {
                System.out.println("Kết nối thành công!");  
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
        }
    }
}
