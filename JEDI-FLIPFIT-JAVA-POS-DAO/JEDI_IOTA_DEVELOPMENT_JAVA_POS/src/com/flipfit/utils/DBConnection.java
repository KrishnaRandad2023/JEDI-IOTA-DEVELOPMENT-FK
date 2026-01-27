package com.flipfit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/flipfit_schema";
    private static final String USER = "root"; // Replace with your DB username
    private static final String PASSWORD = "Engg@sql2026"; // Replace with your DB password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
