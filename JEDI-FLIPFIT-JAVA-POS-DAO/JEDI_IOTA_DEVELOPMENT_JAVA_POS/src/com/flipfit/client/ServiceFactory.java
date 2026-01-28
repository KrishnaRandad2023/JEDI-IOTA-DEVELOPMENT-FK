package com.flipfit.client;

import com.flipfit.business.AdminService;
import com.flipfit.business.BookingService;
import com.flipfit.business.CustomerService;
import com.flipfit.business.GymOwnerService;
import com.flipfit.business.GymService;
import com.flipfit.business.GymUserService;
import com.flipfit.business.SlotService;
import com.flipfit.business.WaitlistService;
import com.flipfit.business.PaymentService;

/**
 * Factory class to initialize and provide singleton service instances.
 * 
 * @author team IOTA
 */
public class ServiceFactory {
    // All services as singleton instances
    private static ServiceFactory instance;

    // Service instances
    private GymUserService gymUserService;
    private GymService gymService;
    private SlotService slotService;
    private BookingService bookingService;
    private WaitlistService waitlistService;
    private AdminService adminService;
    private GymOwnerService gymOwnerService;
    private CustomerService customerService;
    private PaymentService paymentService;

    // Private constructor (Singleton pattern)
    private ServiceFactory() {
        initializeServices();
    }

    // Get singleton instance
    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    // Initialize all services in correct order
    private void initializeServices() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   INITIALIZING FLIPFIT SERVICES        ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // Step 1: Create basic services (no dependencies)
        System.out.println("📦 Step 1: Creating basic services...");
        gymUserService = new GymUserService();
        gymService = new GymService();
        slotService = new SlotService();
        paymentService = new PaymentService();

        // Step 2: Create services with dependencies
        System.out.println("\n📦 Step 2: Creating dependent services...");
        bookingService = new BookingService();
        waitlistService = new WaitlistService();

        // Step 3: Set cross-dependencies
        System.out.println("\n🔗 Step 3: Setting service dependencies...");
        bookingService.setSlotService(slotService);
        bookingService.setWaitlistService(waitlistService);
        waitlistService.setBookingService(bookingService);

        // Step 4: Create high-level services
        System.out.println("\n📦 Step 4: Creating high-level services...");
        adminService = new AdminService();
        adminService.setGymUserService(gymUserService);
        adminService.setGymService(gymService);
        adminService.setBookingService(bookingService);
        adminService.setWaitlistService(waitlistService);
        adminService.setSlotService(slotService);

        gymOwnerService = new GymOwnerService();
        gymOwnerService.setGymService(gymService);
        gymOwnerService.setSlotService(slotService);
        gymOwnerService.setBookingService(bookingService);
        gymOwnerService.setGymUserService(gymUserService);

        customerService = new CustomerService();
        customerService.setGymService(gymService);
        customerService.setSlotService(slotService);
        customerService.setBookingService(bookingService);
        customerService.setWaitlistService(waitlistService);
        customerService.setGymUserService(gymUserService);

        // Step 5: Initialize hard-coded data
        System.out.println("\n💾 Step 5: Loading hard-coded data...");
        bookingService.initializeHardcodedBookings();
        waitlistService.initializeHardcodedWaitlist();
        adminService.initializeHardcodedRegistrations();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SERVICES INITIALIZED!         ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // Display summary
        displayInitializationSummary();
    }

    // Display initialization summary
    // Display initialization summary
    private void displayInitializationSummary() {
        System.out.println("📊 SYSTEM SUMMARY:");

        // FIXED: Add null checks to prevent NullPointerException
        int userCount = (gymUserService != null && gymUserService.getAllUsers() != null)
                ? gymUserService.getAllUsers().size()
                : 0;
        int centerCount = (gymService != null && gymService.getAllCenters() != null)
                ? gymService.getAllCenters().size()
                : 0;
        int slotCount = (slotService != null && slotService.getAllSlots() != null)
                ? slotService.getAllSlots().size()
                : 0;
        int bookingCount = (bookingService != null && bookingService.getAllBookings() != null)
                ? bookingService.getAllBookings().size()
                : 0;
        int waitlistCount = (waitlistService != null && waitlistService.getAllWaitlistEntries() != null)
                ? waitlistService.getAllWaitlistEntries().size()
                : 0;
        int registrationCount = (adminService != null && adminService.getAllPendingRegistrations() != null)
                ? adminService.getAllPendingRegistrations().size()
                : 0;

        System.out.println("   - Users: " + userCount);
        System.out.println("   - Gym Centers: " + centerCount);
        System.out.println("   - Slots: " + slotCount);
        System.out.println("   - Bookings: " + bookingCount);
        System.out.println("   - Waitlist Entries: " + waitlistCount);
        System.out.println("   - Pending Registrations: " + registrationCount);
        System.out.println();
    }

    // Getters for all services
    public GymUserService getGymUserService() {
        return gymUserService;
    }

    public GymService getGymService() {
        return gymService;
    }

    public SlotService getSlotService() {
        return slotService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public WaitlistService getWaitlistService() {
        return waitlistService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public GymOwnerService getGymOwnerService() {
        return gymOwnerService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }
}