package com.flipfit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.flipfit.bean.Payment;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;

/**
 * MySQL-based implementation of the {@link PaymentDAO} interface.
 * Uses JDBC and {@link SQLConstants} to handle payment record persistence.
 * 
 * @author team IOTA
 */
public class PaymentDAOImpl implements PaymentDAO {

    /**
     * Default constructor for PaymentDAOImpl.
     */
    public PaymentDAOImpl() {
    }

    @Override
    public boolean addPayment(Payment payment) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQLConstants.ADD_PAYMENT,
                        Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payment.getBookingId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getTransactionId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment.setPaymentId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Payment getPaymentById(int paymentId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQLConstants.GET_PAYMENT_BY_ID)) {
            stmt.setInt(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("paymentId"));
                payment.setBookingId(rs.getInt("bookingId"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setTransactionId(rs.getString("transactionId"));
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
