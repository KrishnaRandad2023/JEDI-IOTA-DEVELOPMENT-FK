package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import java.util.Date;
import java.util.List;

public interface GymCustomerDAO {
    List<GymCenter> viewGyms();

    boolean bookSlot(int bookingId, int slotId, int userId, Date date);

    List<Booking> viewBookings(int userId);

    boolean cancelBooking(int bookingId);
}
