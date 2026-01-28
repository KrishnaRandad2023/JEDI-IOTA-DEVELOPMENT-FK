package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.Booking;
import com.flipfit.bean.BookingStatus;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.BookingNotDoneException;

/// Classs level Comminting

/**
 * The Class BookingService.
 *
 * @author team IOTA
 * @ClassName "BookingService"
 */
public class BookingService implements BookingServiceInterface {

    /** The bookings. */
    private Map<Integer, Booking> bookings; // bookingId -> Booking

    /** The user bookings. */
    private Map<Integer, List<Booking>> userBookings; // userId -> List of Bookings

    /** The slot bookings. */
    private Map<Integer, List<Booking>> slotBookings; // slotId -> List of Bookings

    /** The slot booking count. */
    private Map<Integer, Integer> slotBookingCount; // slotId -> count of confirmed bookings

    /** The booking id counter. */
    private int bookingIdCounter;

    /** The slot service. */
    private SlotService slotService;

    /** The waitlist service. */
    private WaitlistService waitlistService;

    /**
     * Instantiates a new booking service.
     */
    public BookingService() {
        this.bookings = new HashMap<>();
        this.userBookings = new HashMap<>();
        this.slotBookings = new HashMap<>();
        this.slotBookingCount = new HashMap<>();
        this.bookingIdCounter = 1;

        System.out.println("✅ BookingService initialized");
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
     * Initialize hardcoded bookings.
     */
    public void initializeHardcodedBookings() {
        // Customer 1 (Amit - userId=4) bookings
        createBookingInternal(4, 1, BookingStatus.CONFIRMED); // Slot 1 - Bellandur 6-7 AM
        createBookingInternal(4, 3, BookingStatus.CONFIRMED); // Slot 3 - Bellandur 6-7 PM

        // Customer 2 (Sneha - userId=5) bookings
        createBookingInternal(5, 2, BookingStatus.CONFIRMED); // Slot 2 - Bellandur 7-8 AM
        createBookingInternal(5, 6, BookingStatus.CONFIRMED); // Slot 6 - HSR 5-6 PM

        // Customer 3 (Vikram - userId=6) bookings
        createBookingInternal(6, 9, BookingStatus.CONFIRMED); // Slot 9 - Indiranagar 6-7 AM

        System.out.println("✅ Initialized with " + bookings.size() + " bookings");
    }

    /**
     * Creates the booking internal.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @param status the status
     */
    private void createBookingInternal(int userId, int slotId, BookingStatus status) {
        Booking booking = new Booking();
        booking.setBookingId(bookingIdCounter++);
        booking.setUserId(userId);
        booking.setSlotId(slotId);
        booking.setBookingDate(new Date());
        booking.setStatus(status);

        // Add to collections
        bookings.put(booking.getBookingId(), booking);
        userBookings.computeIfAbsent(userId, k -> new ArrayList<>()).add(booking);
        slotBookings.computeIfAbsent(slotId, k -> new ArrayList<>()).add(booking);

        // Update slot booking count
        if (status == BookingStatus.CONFIRMED) {
            slotBookingCount.put(slotId, slotBookingCount.getOrDefault(slotId, 0) + 1);

            // Decrease available seats if slotService is available
            if (slotService != null) {
                slotService.decreaseAvailableSeats(slotId);
            }
        }
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

        // Fallback: just check if slot exists
        return slotBookings.getOrDefault(slotId, new ArrayList<>()).size() < 20;
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
    public void bookSlot(int userId, int slotId, Date date) throws SlotNotAvailableException {
        // Check if slot has available seats
        if (slotService != null && !slotService.hasAvailableSeats(slotId)) {
            System.out.println("❌ Slot is full! Adding to waitlist...");

            // Add to waitlist
            if (waitlistService != null) {
                waitlistService.addToWaitlist(userId, slotId);
            }
            throw new SlotNotAvailableException("Slot with ID " + slotId + " is full!");
        }

        // Create new booking
        Booking booking = new Booking();
        booking.setBookingId(bookingIdCounter++);
        booking.setUserId(userId);
        booking.setSlotId(slotId);
        booking.setBookingDate(date != null ? date : new Date());
        booking.setStatus(BookingStatus.CONFIRMED);

        // Add to collections
        bookings.put(booking.getBookingId(), booking);
        userBookings.computeIfAbsent(userId, k -> new ArrayList<>()).add(booking);
        slotBookings.computeIfAbsent(slotId, k -> new ArrayList<>()).add(booking);

        // Update slot booking count
        slotBookingCount.put(slotId, slotBookingCount.getOrDefault(slotId, 0) + 1);

        // Decrease available seats
        if (slotService != null) {
            slotService.decreaseAvailableSeats(slotId);
        }

        System.out.println("✅ Booking successful!");
        System.out.println("   Booking ID: " + booking.getBookingId());
        System.out.println("   Slot ID: " + slotId);
        System.out.println("   Date: " + booking.getBookingDate());
    }

    /**
     * Cancel booking.
     *
     * @param bookingId the booking ID
     * @throws BookingNotDoneException the booking not done exception
     */
    @Override
    public void cancelBooking(int bookingId) throws BookingNotDoneException {
        Booking booking = bookings.get(bookingId);

        if (booking == null) {
            System.out.println("❌ Booking not found!");
            throw new BookingNotDoneException("Booking with ID " + bookingId + " not found!");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("⚠️ Booking already cancelled!");
            return;
        }

        // FIXED: Store old status BEFORE updating
        BookingStatus oldStatus = booking.getStatus();

        // Update booking status
        booking.setStatus(BookingStatus.CANCELLED);

        // FIXED: Only decrease count and increase seats if booking was CONFIRMED
        if (oldStatus == BookingStatus.CONFIRMED) {
            // Decrease slot booking count
            int slotId = booking.getSlotId();
            slotBookingCount.put(slotId, slotBookingCount.getOrDefault(slotId, 1) - 1);

            // Increase available seats
            if (slotService != null) {
                slotService.increaseAvailableSeats(slotId);
            }

            // Promote from waitlist if applicable
            if (waitlistService != null) {
                waitlistService.promoteFromWaitlist(slotId);
            }
        }

        System.out.println("✅ Booking cancelled successfully!");
        System.out.println("   Booking ID: " + bookingId);
    }

    /**
     * Gets the bookings by user.
     *
     * @param userId the user ID
     * @return the bookings by user
     */
    public List<Booking> getBookingsByUser(int userId) {
        return userBookings.getOrDefault(userId, new ArrayList<>());
    }

    /**
     * Gets the active bookings by user.
     *
     * @param userId the user ID
     * @return the active bookings by user
     */
    public List<Booking> getActiveBookingsByUser(int userId) {
        List<Booking> userBookingList = userBookings.getOrDefault(userId, new ArrayList<>());
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
        return slotBookings.getOrDefault(slotId, new ArrayList<>());
    }

    /**
     * Gets the booking by ID.
     *
     * @param bookingId the booking ID
     * @return the booking by ID
     * @throws BookingNotDoneException the booking not done exception
     */
    public Booking getBookingById(int bookingId) throws BookingNotDoneException {
        Booking booking = bookings.get(bookingId);
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
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }

    /**
     * Gets the bookings by status.
     *
     * @param status the status
     * @return the bookings by status
     */
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        List<Booking> filteredBookings = new ArrayList<>();

        for (Booking booking : bookings.values()) {
            if (booking.getStatus() == status) {
                filteredBookings.add(booking);
            }
        }

        return filteredBookings;
    }

    /**
     * Gets the booking count for slot.
     *
     * @param slotId the slot ID
     * @return the booking count for slot
     */
    public int getBookingCountForSlot(int slotId) {
        return slotBookingCount.getOrDefault(slotId, 0);
    }

    /**
     * Checks for user booked slot.
     *
     * @param userId the user ID
     * @param slotId the slot ID
     * @return true, if successful
     */
    public boolean hasUserBookedSlot(int userId, int slotId) {
        List<Booking> userBookingList = userBookings.getOrDefault(userId, new ArrayList<>());

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
    public boolean updateBookingStatus(int bookingId, BookingStatus newStatus) throws BookingNotDoneException {
        Booking booking = bookings.get(bookingId);

        if (booking == null) {
            System.out.println("❌ Booking not found!");
            throw new BookingNotDoneException("Booking with ID " + bookingId + " not found!");
        }

        BookingStatus oldStatus = booking.getStatus();
        booking.setStatus(newStatus);

        // Update slot counts if status changed from/to CONFIRMED
        if (oldStatus == BookingStatus.CONFIRMED && newStatus != BookingStatus.CONFIRMED) {
            slotBookingCount.put(booking.getSlotId(),
                    slotBookingCount.getOrDefault(booking.getSlotId(), 1) - 1);

            if (slotService != null) {
                slotService.increaseAvailableSeats(booking.getSlotId());
            }
        } else if (oldStatus != BookingStatus.CONFIRMED && newStatus == BookingStatus.CONFIRMED) {
            slotBookingCount.put(booking.getSlotId(),
                    slotBookingCount.getOrDefault(booking.getSlotId(), 0) + 1);

            if (slotService != null) {
                slotService.decreaseAvailableSeats(booking.getSlotId());
            }
        }

        System.out.println("✅ Booking status updated to: " + newStatus);
        return true;
    }

    /**
     * Delete booking.
     *
     * @param bookingId the booking ID
     * @return true, if successful
     * @throws BookingNotDoneException the booking not done exception
     */
    public boolean deleteBooking(int bookingId) throws BookingNotDoneException {
        Booking booking = bookings.remove(bookingId);

        if (booking != null) {
            // Remove from user's bookings
            List<Booking> userBookingList = userBookings.get(booking.getUserId());
            if (userBookingList != null) {
                userBookingList.removeIf(b -> b.getBookingId() == bookingId);
            }

            // Remove from slot's bookings
            List<Booking> slotBookingList = slotBookings.get(booking.getSlotId());
            if (slotBookingList != null) {
                slotBookingList.removeIf(b -> b.getBookingId() == bookingId);
            }

            // Update counts if booking was confirmed
            if (booking.getStatus() == BookingStatus.CONFIRMED) {
                slotBookingCount.put(booking.getSlotId(),
                        slotBookingCount.getOrDefault(booking.getSlotId(), 1) - 1);

                if (slotService != null) {
                    slotService.increaseAvailableSeats(booking.getSlotId());
                }
            }

            System.out.println("✅ Booking deleted!");
            return true;
        }

        System.out.println("❌ Booking not found!");
        throw new BookingNotDoneException("Booking with ID " + bookingId + " not found!");
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
        List<Booking> userBookingList = getBookingsByUser(userId);

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
        if (bookings.isEmpty()) {
            System.out.println("No bookings in the system!");
        } else {
            System.out.println("\n=== ALL BOOKINGS ===");
            for (Booking booking : bookings.values()) {
                displayBooking(booking);
            }
        }
    }
}