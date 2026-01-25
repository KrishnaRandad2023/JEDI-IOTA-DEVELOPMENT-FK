package com.flipfit.client;

import com.flipfit.business.AdminService;
import com.flipfit.business.BookingService;
import com.flipfit.business.CustomerService;
import com.flipfit.business.GymOwnerService;
import com.flipfit.business.GymService;
import com.flipfit.business.GymUserService;
import com.flipfit.business.SlotService;
import com.flipfit.business.WaitlistService;

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
    private void displayInitializationSummary() {
        System.out.println("📊 SYSTEM SUMMARY:");
        System.out.println("   - Users: " + gymUserService.getAllUsers().size());
        System.out.println("   - Gym Centers: " + gymService.getAllCenters().size());
        System.out.println("   - Slots: " + slotService.getAllSlots().size());
        System.out.println("   - Bookings: " + bookingService.getAllBookings().size());
        System.out.println("   - Waitlist Entries: " + waitlistService.getAllWaitlistEntries().size());
        System.out.println("   - Pending Registrations: " + adminService.getAllPendingRegistrations().size());
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
}