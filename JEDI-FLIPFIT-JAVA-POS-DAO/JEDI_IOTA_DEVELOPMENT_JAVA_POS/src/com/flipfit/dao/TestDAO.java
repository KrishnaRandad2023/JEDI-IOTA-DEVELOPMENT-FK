// package com.flipfit.dao;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// public class TestDAO {

// private String jdbcURL = "jdbc:mysql://localhost:3306/Flipfit_Schema";
// private String jdbcUsername = "root";
// private String jdbcPassword = "Engg@sql2026";

// private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customer (id,
// name, email, contact) VALUES (?, ?, ?, ?);";

// protected Connection getConnection() {
// Connection connection = null;
// try {
// Class.forName("com.mysql.cj.jdbc.Driver");
// connection = DriverManager.getConnection(jdbcURL, jdbcUsername,
// jdbcPassword);
// } catch (SQLException | ClassNotFoundException e) {
// e.printStackTrace();
// }
// return connection;
// }

// public void insertCustomer(int id, String name, String email, String contact)
// {
// try (Connection connection = getConnection();
// PreparedStatement preparedStatement =
// connection.prepareStatement(INSERT_CUSTOMER_SQL)) {

// preparedStatement.setInt(1, id);
// preparedStatement.setString(2, name);
// preparedStatement.setString(3, email);
// preparedStatement.setString(4, contact);

// preparedStatement.executeUpdate();
// System.out.println("Customer " + name + " inserted successfully!");

// } catch (SQLException e) {
// e.printStackTrace();
// }
// }
// }