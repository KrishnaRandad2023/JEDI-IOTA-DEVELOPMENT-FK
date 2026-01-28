package com.flipfit.client;

import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.exception.BookingNotDoneException;
import com.flipfit.exception.SlotNotAvailableException;
import com.flipfit.exception.UserNotFoundException;

/**
 * The Class CustomerFlipFitMenu.
 *
 * @author team IOTA
 * @ClassName "CustomerFlipFitMenu"
 */
public class CustomerFlipFitMenu implements FlipFitMenuInterface {

    /** The customer service. */
    private CustomerService customerService;

    /** The logged in customer. */
    private User loggedInCustomer;

    /**
     * Instantiates a new customer flip fit menu.
     *
     * @param customer the customer
     */
    public CustomerFlipFitMenu(User customer) {
        // Get services from factory
        ServiceFactory factory = ServiceFactory.getInstance();
        this.customerService = factory.getCustomerService();
        this.loggedInCustomer = customer;
    }

    /**
     * Display menu.
     *
     * @param scanner the scanner
     */
    @Override
    public void displayMenu(Scanner scanner) {
        int choice = 0;

        System.out.println("\n👋 Welcome, " + loggedInCustomer.getName() + "!");

        while (choice != 11) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       GYM CUSTOMER MENU            ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. View Gyms in a City             ║");
            System.out.println("║ 2. Search Gyms by Name             ║");
            System.out.println("║ 3. View Gym Details & Slots        ║");
            System.out.println("║ 4. Book a Slot                     ║");
            System.out.println("║ 5. Cancel a Booking                ║");
            System.out.println("║ 6. View My Bookings                ║");
            System.out.println("║ 7. View My Active Bookings         ║");
            System.out.println("║ 8. View My Waitlist Status         ║");
            System.out.println("║ 9. Update My Profile               ║");
            System.out.println("║ 10. View My Statistics             ║");
            System.out.println("║ 11. Logout                         ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Enter choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        viewGymsInCity(scanner);
                        break;
                    case 2:
                        searchGymsByName(scanner);
                        break;
                    case 3:
                        viewGymDetails(scanner);
                        break;
                    case 4:
                        bookSlot(scanner);
                        break;
                    case 5:
                        cancelBooking(scanner);
                        break;
                    case 6:
                        viewMyBookings();
                        break;
                    case 7:
                        viewMyActiveBookings();
                        break;
                    case 8:
                        viewMyWaitlistStatus();
                        break;
                    case 9:
                        updateProfile(scanner);
                        break;
                    case 10:
                        viewMyStatistics();
                        break;
                    case 11:
                        System.out.println("👋 Logging out from Customer...");
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * View gyms in city.
     *
     * @param scanner the scanner
     */
    // 1. View gyms in a city
    private void viewGymsInCity(Scanner scanner) {
        System.out.println("\n═══ VIEW GYMS BY CITY ═══");
        System.out.print("Enter City Name: ");
        String city = scanner.nextLine();

        customerService.displayGymsInCity(city);
    }

    /**
     * Search gyms by name.
     *
     * @param scanner the scanner
     */
    // 2. Search gyms by name
    private void searchGymsByName(Scanner scanner) {
        System.out.println("\n═══ SEARCH GYMS ═══");
        System.out.print("Enter Gym Name (or part of it): ");
        String searchTerm = scanner.nextLine();

        List<GymCenter> matchingGyms = customerService.searchGymsByName(searchTerm);

        if (matchingGyms.isEmpty()) {
            System.out.println("❌ No gyms found matching '" + searchTerm + "'");
        } else {
            System.out.println("\n✅ Found " + matchingGyms.size() + " gym(s):");
            for (GymCenter center : matchingGyms) {
                System.out.println("\n  ID: " + center.getCenterId());
                System.out.println("  Name: " + center.getCenterName());
                System.out.println("  Address: " + center.getAddress());
                System.out.println("  City: " + center.getCity());
                System.out.println("  Capacity: " + center.getCapacity());
                System.out.println("  ---");
            }
        }
    }

    /**
     * View gym details.
     *
     * @param scanner the scanner
     */
    // 3. View gym details with slots
    private void viewGymDetails(Scanner scanner) {
        System.out.println("\n═══ VIEW GYM DETAILS ═══");
        System.out.print("Enter Gym Center ID: ");
        int centerId = scanner.nextInt();
        scanner.nextLine();

        customerService.displayGymWithSlots(centerId);
    }

    /**
     * Book slot.
     *
     * @param scanner the scanner
     */
    // 4. Book a slot
    private void bookSlot(Scanner scanner) {
        System.out.println("\n═══ BOOK A SLOT ═══");

        // First, show available gyms
        System.out.print("Enter City to view gyms: ");
        String city = scanner.nextLine();

        List<GymCenter> gyms = customerService.viewGymsInCity(city);

        if (gyms.isEmpty()) {
            System.out.println("❌ No gyms found in " + city);
            return;
        }

        System.out.println("\nAvailable Gyms:");
        for (GymCenter center : gyms) {
            System.out.println("  ID: " + center.getCenterId() + " - " + center.getCenterName());
        }

        System.out.print("\nEnter Gym Center ID to view slots: ");
        int centerId = scanner.nextInt();
        scanner.nextLine();

        // Show available slots
        List<Slot> availableSlots = customerService.viewAvailableSlotsForGym(centerId);

        if (availableSlots.isEmpty()) {
            System.out.println("❌ No available slots for this gym!");
            return;
        }

        System.out.println("\nAvailable Slots:");
        for (Slot slot : availableSlots) {
            System.out.println("  Slot ID: " + slot.getSlotId() +
                    " | Time: " + slot.getStartTime() + " - " + slot.getEndTime() +
                    " | Available: " + slot.getAvailableSeats() + "/" + slot.getTotalSeats());
        }

        System.out.print("\nEnter Slot ID to book (0 to cancel): ");
        int slotId = scanner.nextInt();
        scanner.nextLine();

        if (slotId == 0) {
            System.out.println("Cancelled.");
            return;
        }

        System.out.print("Confirm booking? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            // INTEGRATED PAYMENT STEP via Payment Menu
            PaymentFlipFitMenu paymentMenu = new PaymentFlipFitMenu();
            double amount = 500.00; // Mock amount

            boolean paymentSuccess = paymentMenu.showPaymentMenu(scanner, amount);

            if (paymentSuccess) {
                try {
                    boolean bookingSuccess = customerService.bookSlot(loggedInCustomer.getUserId(), slotId);
                    if (!bookingSuccess) {
                        System.out.println("⚠️ Added to waitlist as slot was full.");
                    } else {
                        System.out.println("\n✅ Booking Confirmed!");
                    }
                } catch (UserNotFoundException | SlotNotAvailableException e) {
                    System.out.println("❌ Booking failed: " + e.getMessage());
                }
            } else {
                System.out.println("❌ Payment failed or cancelled! Booking aborted.");
            }
        } else {
            System.out.println("Booking cancelled.");
        }
    }

    /**
     * Cancel booking.
     *
     * @param scanner the scanner
     */
    // 5. Cancel a booking
    private void cancelBooking(Scanner scanner) {
        System.out.println("\n═══ CANCEL BOOKING ═══");

        // Show user's active bookings
        List<Booking> activeBookings = customerService.viewMyActiveBookings(loggedInCustomer.getUserId());

        if (activeBookings.isEmpty()) {
            System.out.println("❌ You have no active bookings to cancel.");
            return;
        }

        System.out.println("\nYour Active Bookings:");
        for (Booking booking : activeBookings) {
            Slot slot = customerService.getSlotDetails(booking.getSlotId());
            System.out.println("  Booking ID: " + booking.getBookingId() +
                    " | Slot: " + (slot != null ? slot.getStartTime() + "-" + slot.getEndTime() : booking.getSlotId()) +
                    " | Date: " + booking.getBookingDate());
        }

        System.out.print("\nEnter Booking ID to cancel (0 to go back): ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        if (bookingId == 0) {
            System.out.println("Cancelled.");
            return;
        }

        System.out.print("⚠️ Are you sure you want to cancel this booking? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            try {
                customerService.cancelBooking(loggedInCustomer.getUserId(), bookingId);
            } catch (BookingNotDoneException e) {
                System.out.println("❌ Cancellation failed: " + e.getMessage());
            }
        } else {
            System.out.println("Cancellation aborted.");
        }
    }

    /**
     * View my bookings.
     */
    // 6. View all my bookings
    private void viewMyBookings() {
        System.out.println("\n═══ MY BOOKINGS ═══");
        customerService.displayMyBookings(loggedInCustomer.getUserId());
    }

    /**
     * View my active bookings.
     */
    // 7. View my active bookings
    private void viewMyActiveBookings() {
        System.out.println("\n═══ MY ACTIVE BOOKINGS ═══");
        customerService.displayMyActiveBookings(loggedInCustomer.getUserId());
    }

    /**
     * View my waitlist status.
     */
    // 8. View waitlist status
    private void viewMyWaitlistStatus() {
        System.out.println("\n═══ MY WAITLIST STATUS ═══");
        customerService.displayMyWaitlistStatus(loggedInCustomer.getUserId());
    }

    /**
     * Update profile.
     *
     * @param scanner the scanner
     */
    // 9. Update profile
    private void updateProfile(Scanner scanner) {
        System.out.println("\n═══ UPDATE PROFILE ═══");
        System.out.println("1. Update Name");
        System.out.println("2. Update Mobile Number");
        System.out.println("3. Change Password");
        System.out.println("4. Back");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (choice) {
                case 1:
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    loggedInCustomer.setName(newName);
                    if (customerService.updateMyProfile(loggedInCustomer)) {
                        System.out.println("✅ Name updated successfully!");
                    }
                    break;

                case 2:
                    System.out.print("Enter New Mobile Number: ");
                    String newMobile = scanner.nextLine();
                    loggedInCustomer.setMobileNumber(newMobile);
                    if (customerService.updateMyProfile(loggedInCustomer)) {
                        System.out.println("✅ Mobile number updated successfully!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Old Password: ");
                    String oldPassword = scanner.nextLine();
                    System.out.print("Enter New Password: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Confirm New Password: ");
                    String confirmPassword = scanner.nextLine();

                    if (!newPassword.equals(confirmPassword)) {
                        System.out.println("❌ Passwords don't match!");
                    } else {
                        customerService.changeMyPassword(
                                loggedInCustomer.getUserId(),
                                oldPassword,
                                newPassword);
                    }
                    break;

                case 4:
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        } catch (UserNotFoundException e) {
            System.out.println("❌ Profile update failed: " + e.getMessage());
        }
    }

    /**
     * View my statistics.
     */
    // 10. View statistics
    private void viewMyStatistics() {
        System.out.println("\n═══ MY STATISTICS ═══");
        customerService.displayMyStatistics(loggedInCustomer.getUserId());
    }
}