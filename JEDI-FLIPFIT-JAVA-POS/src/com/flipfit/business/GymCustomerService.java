package com.flipfit.business;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Booking;
import com.flipfit.bean.Slot;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IGymCustomerService interface
 */
public class GymCustomerService implements IGymCustomerService {
    
    private List<GymCenter> gymCenters;
    private List<Booking> bookings;
    
    public GymCustomerService() {
        this.gymCenters = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }
    
    @Override
    public List<GymCenter> viewCenterByArea(String area) {
        // TODO: Implement method to view gym centers by area
        List<GymCenter> centersByArea = new ArrayList<>();
        for (GymCenter center : gymCenters) {
            if (center.getCity().equalsIgnoreCase(area)) {
                centersByArea.add(center);
            }
        }
        return centersByArea;
    }
    
    @Override
    public List<Slot> checkAvailability(int centerId, LocalDate date) {
        // TODO: Implement method to check availability at a gym center
        System.out.println("Checking availability for center ID: " + centerId + " on date: " + date);
        List<Slot> availableSlots = new ArrayList<>();
        // Logic to check and return available slots
        return availableSlots;
    }
    
    @Override
    public boolean makeBooking(Booking booking) {
        // TODO: Implement method to make a booking
        System.out.println("Making booking for user ID: " + booking.getUserId());
        bookings.add(booking);
        return true;
    }
    
    @Override
    public boolean cancelBooking(int bookingId) {
        // TODO: Implement method to cancel a booking
        System.out.println("Canceling booking with ID: " + bookingId);
        return bookings.removeIf(booking -> booking.getBookingId() == bookingId);
    }
    
    @Override
    public List<Booking> viewMyBookings(int userId) {
        // TODO: Implement method to view all bookings for a customer
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUserId() == userId) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }
}
