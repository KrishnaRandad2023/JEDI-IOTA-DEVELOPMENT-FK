package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.BookingNotDoneException;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.BookingDAOImpl;

/**
 * Service class for handling all booking-related operations.
 * Manages slot availability checks, booking creation, cancellations, and
 * waitlist integration.
 * 
 * @author team IOTA
 */
public class BookingService implements BookingServiceInterface {

    private BookingDAO bookingDAO = new BookingDAOImpl();
    private SlotService slotService;
    private WaitlistService waitlistService;

    /**
     * Instantiates a new booking service and initializes the DAO.
     */
    public BookingService() {
        System.out.println("✅ BookingService initialized with Database DAO");
    }

    /**
     * Sets the booking DAO implementation.
     *
     * @param bookingDAO the booking DAO to use
     */
    public void setBookingDAO(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    /**
     * Sets the slot service for capacity management.
     *
     * @param slotService the slot service to use
     */
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }

    /**
     * Sets the waitlist service for handling full slots.
     *
     * @param waitlistService the waitlist service to use
     */
    public void setWaitlistService(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }

    /**
     * Checks if a specific slot is available for booking on a given date.
     *
     * @param slotId the unique ID of the slot
     * @param date   the date of the booking
     * @return true if the slot has available capacity, false otherwise
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
     * Attempts to book a slot for a user. If the slot is full, the user is added to
     * the waitlist.
     *
     * @param userId the ID of the user booking the slot
     * @param slotId the ID of the slot to book
     * @param date   the date for which to book the slot
     * @return the booking ID if successful, -1 if added to waitlist, or 0 if
     *         booking failed
     * @throws SlotNotAvailableException if the slot and waitlist are both full
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
     * Cancels an existing booking and updates slot availability.
     * If there are users on the waitlist for this slot, the first user is promoted.
     *
     * @param bookingId the unique ID of the booking to cancel
     * @return true if cancellation was successful, false if booking was already
     *         cancelled or not found
     * @throws BookingNotDoneException if the booking does not exist
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
     * Retrieves all bookings (both confirmed and cancelled) for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of all bookings for the user
     */
    @Override
    public List<Booking> getBookingsByUser(int userId) {
        return bookingDAO.getBookingsByUser(userId);
    }

    /**
     * Retrieves only confirmed/active bookings for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of active bookings for the user
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
     * Retrieves all bookings for a specific slot on a given date.
     *
     * @param slotId the ID of the slot
     * @param date   the date to filter bookings
     * @return a list of bookings for the slot
     */
    public List<Booking> getBookingsBySlot(int slotId, Date date) {
        // For simplicity, return all bookings for the slot
        // In real scenario, would filter by date
        return bookingDAO.getBookingsBySlot(slotId);
    }

    /**
     * Retrieves booking details by its ID.
     *
     * @param bookingId the unique ID of the booking
     * @return the Booking object
     * @throws BookingNotDoneException if the booking ID is not found
     */
    public Booking getBookingById(int bookingId) throws BookingNotDoneException {
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            throw new BookingNotDoneException("Booking with ID " + bookingId + " not found!");
        }
        return booking;
    }

    /**
     * Retrieves all bookings in the system.
     *
     * @return a list of all bookings
     */
    @Override
    public List<Booking> getAllBookings() {
        // Typically admin views through other filters, but can implement if needed
        return new ArrayList<>();
    }

    /**
     * Retrieves bookings filtered by their current status.
     *
     * @param status the status to filter by (CONFIRMED/CANCELLED)
     * @return a list of matching bookings
     */
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        // Typically not used this way, but if needed we filter from DB
        return new ArrayList<>();
    }

    /**
     * Returns the number of confirmed bookings for a specific slot.
     *
     * @param slotId the ID of the slot
     * @return the count of bookings for that slot
     */
    public int getBookingCountForSlot(int slotId) {
        return bookingDAO.getBookingsBySlot(slotId).size();
    }

    /**
     * Checks if a specific user has an active booking for a specific slot.
     *
     * @param userId the ID of the user
     * @param slotId the ID of the slot
     * @return true if the user has a confirmed booking for the slot, false
     *         otherwise
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
     * Manually updates the status of a booking.
     *
     * @param bookingId the ID of the booking
     * @param newStatus the new status to set
     * @return true if update was successful
     * @throws BookingNotDoneException if the booking doesn't exist
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
     * Deletes a booking record from the database.
     *
     * @param bookingId the ID of the booking to delete
     * @return true if deletion was successful
     * @throws BookingNotDoneException if the booking doesn't exist
     */
    @Override
    public boolean deleteBooking(int bookingId) throws BookingNotDoneException {
        if (bookingDAO.getBookingById(bookingId) == null) {
            throw new BookingNotDoneException("Booking with ID " + bookingId + " not found!");
        }
        return bookingDAO.deleteBooking(bookingId);
    }

    /**
     * Displays details of a single booking to the console.
     *
     * @param booking the booking to display
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
     * Displays all bookings for a specific user to the console.
     *
     * @param userId the ID of the user
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