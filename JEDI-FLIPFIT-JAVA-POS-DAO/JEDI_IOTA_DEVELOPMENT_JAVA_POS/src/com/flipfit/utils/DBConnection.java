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

    /**
     * Test database connection and print status
     * 
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   TESTING DATABASE CONNECTION          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("📍 Database URL: " + URL);
        System.out.println("👤 User: " + USER);

        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connection successful!");
                System.out.println("🔗 Connected to: " + conn.getCatalog());
                return true;
            } else {
                System.out.println("❌ Database connection failed!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            System.out.println("⚠️  Error: " + e.getMessage());
            System.out.println("\n💡 Troubleshooting:");
            System.out.println("   1. Check if MySQL server is running");
            System.out.println("   2. Verify database 'flipfit_schema' exists");
            System.out.println("   3. Confirm username/password are correct");
            System.out.println("   4. Run init_data.sql to populate Role table");
            return false;
        }
    }
}
