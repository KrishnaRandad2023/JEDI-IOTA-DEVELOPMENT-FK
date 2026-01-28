package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.BookingNotDoneException;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.BookingDAOImpl;

// Class level Commenting

/**
 * The Class BookingService.
 *
 * @author team IOTA
 */
public class BookingService implements BookingServiceInterface {

    private BookingDAO bookingDAO = new BookingDAOImpl();
    private SlotService slotService;
    private WaitlistService waitlistService;

    /**
     * Instantiates a new booking service.
     */
    public BookingService() {
        System.out.println("✅ BookingService initialized with Database DAO");
    }

    /**
     * Sets the booking DAO.
     *
     * @param bookingDAO the new booking DAO
     */
    public void setBookingDAO(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    /**
     * Sets the slot service.
     *
     * @param slotService the new slot service
     */
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }

    /**
     * Sets the waitlist service.
     *
     * @param waitlistService the new waitlist service
     */
    public void setWaitlistService(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }

    /**
     * Check availability.
     *
     * @param slotId the slot ID
     * @param date   the date
     * @return true, if successful
     */
    @Override
    public boolean checkAvailability(int slotId, Date date) {
        // Check if slot has available seats
        if (slotService != null) {
            return slotService.hasAvailableSeats(slotId);
        }

        // Fallback: check if slot exists and has capacity (using DAO for count)
        return bookingDAO.getBookingCountForSlot(slotId) < 20;
    }

    /**
     * Book slot.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @param date   the date
     * @throws SlotNotAvailableException the slot not available exception
     */
    @Override
    public int bookSlot(int userId, int slotId, Date date) throws SlotNotAvailableException {
        // 1. Check if slot exists and has seats
        if (slotService != null && !slotService.hasAvailableSeats(slotId)) {
            // No seats available, add to waitlist instead?
            if (waitlistService != null) {
                System.out.println("⚠️ Slot full! Adding you to waitlist...");
                waitlistService.addToWaitlist(userId, slotId);
                return -1; // Return -1 to indicate waitlist
            }
            throw new SlotNotAvailableException("❌ Slot is fully booked and waitlist is full!");
        }

        // 2. Create booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setSlotId(slotId);
        booking.setBookingDate(date);
        booking.setStatus(BookingStatus.CONFIRMED);

        // 3. Persist booking
        int bookingId = bookingDAO.addBooking(booking);

        if (bookingId > 0) {
            // 4. Update slot seats
            if (slotService != null) {
                slotService.decreaseAvailableSeats(slotId);
            }
            System.out.println("✅ Booking successful for User ID: " + userId + " Slot ID: " + slotId);
            return bookingId;
        }

        return 0;
    }

    /**
     * Cancel booking.
     *
     * @param bookingId the booking ID
     * @throws BookingNotDoneException the booking not done exception
     */
    @Override
    public boolean cancelBooking(int bookingId) throws BookingNotDoneException {
        Booking booking = bookingDAO.getBookingById(bookingId);

        if (booking == null) {
            throw new BookingNotDoneException("❌ Booking ID " + bookingId + " not found!");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("⚠️ Booking is already cancelled.");
            return false;
        }

        // 1. Update status
        boolean success = bookingDAO.updateBookingStatus(bookingId, BookingStatus.CANCELLED);

        if (success) {
            // 2. Increase slot capacity
            if (slotService != null) {
                slotService.increaseAvailableSeats(booking.getSlotId());
            }

            // 3. Promote from waitlist if any
            if (waitlistService != null) {
                waitlistService.promoteFromWaitlist(booking.getSlotId());
            }

            System.out.println("✅ Booking " + bookingId + " cancelled successfully.");
            return true;
        }

        return false;
    }

    /**
     * Gets the bookings by user.
     *
     * @param userId the user ID
     * @return the bookings by user
     */
    @Override
    public List<Booking> getBookingsByUser(int userId) {
        return bookingDAO.getBookingsByUser(userId);
    }

    /**
     * Gets the active bookings by user.
     *
     * @param userId the user ID
     * @return the active bookings by user
     */
    public List<Booking> getActiveBookingsByUser(int userId) {
        List<Booking> userBookingList = bookingDAO.getBookingsByUser(userId);
        List<Booking> activeBookings = new ArrayList<>();

        for (Booking booking : userBookingList) {
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                activeBookings.add(booking);
            }
        }

        return activeBookings;
    }

    /**
     * Gets the bookings by slot.
     *
     * @param slotId the slot ID
     * @param date   the date
     * @return the bookings by slot
     */
    public List<Booking> getBookingsBySlot(int slotId, Date date) {
        // For simplicity, return all bookings for the slot
        // In real scenario, would filter by date
        return bookingDAO.getBookingsBySlot(slotId);
    }

    /**
     * Gets the booking by ID.
     *
     * @param bookingId the booking ID
     * @return the booking by ID
     * @throws BookingNotDoneException the booking not done exception
     */
    public Booking getBookingById(int bookingId) throws BookingNotDoneException {
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new BookingNotDoneException("Booking with ID " + bookingId + " not found!");
        }
        return booking;
    }

    /**
     * Gets the all bookings.
     *
     * @return the all bookings
     */
    @Override
    public List<Booking> getAllBookings() {
        // Typically admin views through other filters, but can implement if needed
        return new ArrayList<>();
    }

    /**
     * Gets the bookings by status.
     *
     * @param status the status
     * @return the bookings by status
     */
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        // Typically not used this way, but if needed we filter from DB
        return new ArrayList<>();
    }

    /**
     * Gets the booking count for slot.
     *
     * @param slotId the slot ID
     * @return the booking count for slot
     */
    public int getBookingCountForSlot(int slotId) {
        return bookingDAO.getBookingsBySlot(slotId).size();
    }

    /**
     * Checks for user booked slot.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return true, if successful
     */
    @Override
    public boolean hasUserBookedSlot(int userId, int slotId) {
        List<Booking> userBookingList = bookingDAO.getBookingsByUser(userId);

        for (Booking booking : userBookingList) {
            if (booking.getSlotId() == slotId && booking.getStatus() == BookingStatus.CONFIRMED) {
                return true;
            }
        }

        return false;
    }

    /**
     * Update booking status.
     *
     * @param bookingId the booking ID
     * @param newStatus the new status
     * @return true, if successful
     * @throws BookingNotDoneException the booking not done exception
     */
    @Override
    public boolean updateBookingStatus(int bookingId, BookingStatus newStatus) throws BookingNotDoneException {
        Booking booking = bookingDAO.getBookingById(bookingId);

        if (booking == null) {
            throw new BookingNotDoneException("❌ Booking ID " + bookingId + " not found!");
        }

        return bookingDAO.updateBookingStatus(bookingId, newStatus);
    }

    /**
     * Delete booking.
     *
     * @param bookingId the booking ID
     * @return true, if successful
     * @throws BookingNotDoneException the booking not done exception
     */
    @Override
    public boolean deleteBooking(int bookingId) throws BookingNotDoneException {
        if (bookingDAO.getBookingById(bookingId) == null) {
            throw new BookingNotDoneException("Booking with ID " + bookingId + " not found!");
        }
        return bookingDAO.deleteBooking(bookingId);
    }

    /**
     * Display booking.
     *
     * @param booking the booking
     */
    public void displayBooking(Booking booking) {
        if (booking != null) {
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("User ID: " + booking.getUserId());
            System.out.println("Slot ID: " + booking.getSlotId());
            System.out.println("Date: " + booking.getBookingDate());
            System.out.println("Status: " + booking.getStatus());
            System.out.println("---");
        }
    }

    /**
     * Display user bookings.
     *
     * @param userId the user ID
     */
    public void displayUserBookings(int userId) {
        List<Booking> userBookingList = bookingDAO.getBookingsByUser(userId);

        if (userBookingList.isEmpty()) {
            System.out.println("No bookings found for user ID: " + userId);
        } else {
            System.out.println("\n=== Bookings for User ID: " + userId + " ===");
            for (Booking booking : userBookingList) {
                displayBooking(booking);
            }
        }
    }

    /**
     * Display all bookings.
     */
    public void displayAllBookings() {
        // Typically admin views through other filters
        System.out.println("Functionality not implemented at search-all level.");
    }
}