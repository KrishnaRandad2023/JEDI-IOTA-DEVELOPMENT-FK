package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import com.flipfit.bean.GymCenter;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of Gym Customer DAO
 * 
 * @author team IOTA
 */
public class GymCustomerDAOImpl implements GymCustomerDAO {

    @Override
    public List<GymCenter> viewGyms() {
        List<GymCenter> gyms = new ArrayList<>();
        String query = SQLConstants.GET_APPROVED_GYMS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GymCenter gym = new GymCenter();
                gym.setCenterId(rs.getInt("centerId"));
                gym.setOwnerId(rs.getInt("ownerId"));
                gym.setCenterName(rs.getString("centerName"));
                gym.setAddress(rs.getString("address"));
                gym.setCity(rs.getString("city"));
                gym.setCapacity(rs.getInt("capacity"));
                gym.setApproved(rs.getBoolean("isApproved"));
                gyms.add(gym);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gyms;
    }

    @Override
    public boolean bookSlot(int bookingId, int slotId, int userId, Date date) {
        String query = SQLConstants.BOOK_SLOT;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, slotId);
            stmt.setDate(3, new java.sql.Date(date.getTime()));
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> viewBookings(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String query = SQLConstants.GET_MY_BOOKINGS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("bookingId"));
                booking.setUserId(rs.getInt("userId"));
                booking.setSlotId(rs.getInt("slotId"));
                booking.setBookingDate(rs.getDate("bookingDate"));
                booking.setStatus(BookingStatus.valueOf(rs.getString("status")));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        String query = SQLConstants.CANCEL_BOOKING;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
