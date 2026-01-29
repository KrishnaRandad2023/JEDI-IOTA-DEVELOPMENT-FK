package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL-based implementation of the {@link BookingDAO} interface.
 * Uses JDBC and {@link SQLConstants} to perform CRUD operations on booking
 * data.
 * 
 * @author team IOTA
 */
public class BookingDAOImpl implements BookingDAO {

    /**
     * Default constructor for BookingDAOImpl.
     */
    public BookingDAOImpl() {
    }

    @Override
    public int addBooking(Booking booking) {
        String query = SQLConstants.BOOK_SLOT;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getSlotId());
            stmt.setDate(3, new java.sql.Date(booking.getBookingDate().getTime()));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Booking getBookingById(int bookingId) {
        String query = SQLConstants.GET_BOOKING_BY_ID;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("bookingId"));
                booking.setUserId(rs.getInt("userId"));
                booking.setSlotId(rs.getInt("slotId"));
                booking.setBookingDate(rs.getDate("bookingDate"));
                booking.setStatus(BookingStatus.valueOf(rs.getString("status")));
                return booking;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> getBookingsByUser(int userId) {
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
    public List<Booking> getBookingsBySlot(int slotId) {
        List<Booking> bookings = new ArrayList<>();
        String query = SQLConstants.GET_BOOKINGS_BY_SLOT;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, slotId);
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
    public boolean updateBookingStatus(int bookingId, BookingStatus status) {
        String query = SQLConstants.UPDATE_BOOKING_STATUS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, bookingId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        String query = SQLConstants.DELETE_BOOKING;
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

    @Override
    public int getBookingCountForSlot(int slotId) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQLConstants.GET_BOOKING_COUNT_FOR_SLOT)) {
            stmt.setInt(1, slotId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
