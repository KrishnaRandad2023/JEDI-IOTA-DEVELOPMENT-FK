package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import java.util.List;

/**
 * Interface for Booking DAO
 * 
 * @author team IOTA
 */
public interface BookingDAO {
    boolean addBooking(Booking booking);

    Booking getBookingById(int bookingId);

    List<Booking> getBookingsByUser(int userId);

    List<Booking> getBookingsBySlot(int slotId);

    boolean updateBookingStatus(int bookingId, BookingStatus status);

    boolean deleteBooking(int bookingId);

    int getBookingCountForSlot(int slotId);
}
