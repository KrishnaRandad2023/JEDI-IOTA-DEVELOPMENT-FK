package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.*;

public class AdminService {
    // Collections - Using List for pending approvals, Set for tracking approved items
    private List<Registration> pendingRegistrations;         // Pending user registrations
    private Map<Integer, Registration> registrations;        // registrationId -> Registration
    private Set<Integer> approvedOwnerIds;                   // Set of approved gym owner IDs
    private Set<Integer> rejectedRegistrationIds;            // Set of rejected registration IDs
    private int registrationIdCounter;
    
    // References to other services
    private GymUserService gymUserService;
    private GymService gymService;
    private BookingService bookingService;
    private WaitlistService waitlistService;
    private SlotService slotService;
    
    // Constructor
    public AdminService() {
        this.pendingRegistrations = new ArrayList<>();
        this.registrations = new HashMap<>();
        this.approvedOwnerIds = new HashSet<>();
        this.rejectedRegistrationIds = new HashSet<>();
        this.registrationIdCounter = 1;
        
        System.out.println("✅ AdminService initialized");
    }
    
    // Set service dependencies
    public void setGymUserService(GymUserService gymUserService) {
        this.gymUserService = gymUserService;
    }
    
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }
    
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    public void setWaitlistService(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }
    
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }
    
    // Initialize with hard-coded pending registrations
    public void initializeHardcodedRegistrations() {
        // Pending Gym Owner Registration 1
        Registration ownerReg1 = new Registration();
        ownerReg1.setRegistrationId(registrationIdCounter++);
        ownerReg1.setName("Karthik Iyer");
        ownerReg1.setEmail("karthik.owner@flipfit.com");
        ownerReg1.setPassword("karthik123");
        ownerReg1.setMobileNumber("9876543212");
        ownerReg1.setRoleType("GYM_OWNER");
        ownerReg1.setCity("Bangalore");
        ownerReg1.setPanNumber("ABCDE1234F");
        ownerReg1.setApproved(false);
        addPendingRegistration(ownerReg1);
        
        // Pending Gym Owner Registration 2
        Registration ownerReg2 = new Registration();
        ownerReg2.setRegistrationId(registrationIdCounter++);
        ownerReg2.setName("Divya Menon");
        ownerReg2.setEmail("divya.owner@flipfit.com");
        ownerReg2.setPassword("divya123");
        ownerReg2.setMobileNumber("9876543213");
        ownerReg2.setRoleType("GYM_OWNER");
        ownerReg2.setCity("Bangalore");
        ownerReg2.setPanNumber("FGHIJ5678K");
        ownerReg2.setApproved(false);
        addPendingRegistration(ownerReg2);
        
        // Pending Customer Registration (auto-approved usually, but for demo)
        Registration customerReg = new Registration();
        customerReg.setRegistrationId(registrationIdCounter++);
        customerReg.setName("Rahul Kapoor");
        customerReg.setEmail("rahul@gmail.com");
        customerReg.setPassword("rahul123");
        customerReg.setMobileNumber("9123456783");
        customerReg.setRoleType("CUSTOMER");
        customerReg.setCity("Bangalore");
        customerReg.setApproved(false);
        addPendingRegistration(customerReg);
        
        System.out.println("✅ Initialized with " + pendingRegistrations.size() + " pending registrations");
    }
    
    // Helper to add pending registration
    private void addPendingRegistration(Registration reg) {
        registrations.put(reg.getRegistrationId(), reg);
        pendingRegistrations.add(reg);
    }
    
    // 1. View all pending gym owner registrations
    public List<Registration> getPendingOwnerRegistrations() {
        List<Registration> pendingOwners = new ArrayList<>();
        
        for (Registration reg : pendingRegistrations) {
            if ("GYM_OWNER".equals(reg.getRoleType()) && !reg.isApproved()) {
                pendingOwners.add(reg);
            }
        }
        
        return pendingOwners;
    }
    
    // 2. View all pending customer registrations
    public List<Registration> getPendingCustomerRegistrations() {
        List<Registration> pendingCustomers = new ArrayList<>();
        
        for (Registration reg : pendingRegistrations) {
            if ("CUSTOMER".equals(reg.getRoleType()) && !reg.isApproved()) {
                pendingCustomers.add(reg);
            }
        }
        
        return pendingCustomers;
    }
    
    // 3. View all pending registrations
    public List<Registration> getAllPendingRegistrations() {
        return new ArrayList<>(pendingRegistrations);
    }
    
    // 4. Approve gym owner registration
    public boolean approveOwnerRegistration(int registrationId) {
        Registration reg = registrations.get(registrationId);
        
        if (reg == null) {
            System.out.println("❌ Registration not found!");
            return false;
        }
        
        if (!"GYM_OWNER".equals(reg.getRoleType())) {
            System.out.println("❌ This is not a gym owner registration!");
            return false;
        }
        
        if (reg.isApproved()) {
            System.out.println("⚠️ Registration already approved!");
            return false;
        }
        
        // Create GymOwner user
        if (gymUserService != null) {
            GymOwner owner = new GymOwner();
            owner.setName(reg.getName());
            owner.setEmail(reg.getEmail());
            owner.setPassword(reg.getPassword());
            owner.setMobileNumber(reg.getMobileNumber());
            
            Role ownerRole = new Role(2, "GYM_OWNER", "Gym owner who manages centers");
            owner.setRole(ownerRole);
            
            if (gymUserService.registerUser(owner)) {
                // Mark registration as approved
                reg.setApproved(true);
                approvedOwnerIds.add(owner.getUserId());
                pendingRegistrations.remove(reg);
                
                System.out.println("✅ Gym Owner approved successfully!");
                System.out.println("   Name: " + owner.getName());
                System.out.println("   Email: " + owner.getEmail());
                System.out.println("   User ID: " + owner.getUserId());
                return true;
            }
        }
        
        return false;
    }
    
    // 5. Approve customer registration
    public boolean approveCustomerRegistration(int registrationId) {
        Registration reg = registrations.get(registrationId);
        
        if (reg == null) {
            System.out.println("❌ Registration not found!");
            return false;
        }
        
        if (!"CUSTOMER".equals(reg.getRoleType())) {
            System.out.println("❌ This is not a customer registration!");
            return false;
        }
        
        if (reg.isApproved()) {
            System.out.println("⚠️ Registration already approved!");
            return false;
        }
        
        // Create Customer user
        if (gymUserService != null) {
            GymCustomer customer = new GymCustomer();
            customer.setName(reg.getName());
            customer.setEmail(reg.getEmail());
            customer.setPassword(reg.getPassword());
            customer.setMobileNumber(reg.getMobileNumber());
            
            Role customerRole = new Role(3, "CUSTOMER", "Customer who books slots");
            customer.setRole(customerRole);
            
            if (gymUserService.registerUser(customer)) {
                // Mark registration as approved
                reg.setApproved(true);
                pendingRegistrations.remove(reg);
                
                System.out.println("✅ Customer approved successfully!");
                System.out.println("   Name: " + customer.getName());
                System.out.println("   Email: " + customer.getEmail());
                System.out.println("   User ID: " + customer.getUserId());
                return true;
            }
        }
        
        return false;
    }
    
    // 6. Reject registration
    public boolean rejectRegistration(int registrationId) {
        Registration reg = registrations.get(registrationId);
        
        if (reg == null) {
            System.out.println("❌ Registration not found!");
            return false;
        }
        
        // Remove from pending list
        pendingRegistrations.remove(reg);
        rejectedRegistrationIds.add(registrationId);
        
        System.out.println("✅ Registration rejected!");
        System.out.println("   Name: " + reg.getName());
        System.out.println("   Email: " + reg.getEmail());
        return true;
    }
    
    // 7. View all pending gym center approvals
    public List<GymCenter> getPendingCenterApprovals() {
        if (gymService != null) {
            return gymService.getPendingApprovals();
        }
        return new ArrayList<>();
    }
    
    // 8. Approve gym center
    public boolean approveGymCenter(int centerId) {
        if (gymService != null) {
            return gymService.approveCenterRegistration(centerId);
        }
        System.out.println("❌ GymService not available!");
        return false;
    }
    
    // 9. Reject gym center
    public boolean rejectGymCenter(int centerId) {
        if (gymService != null) {
            return gymService.rejectCenterRegistration(centerId);
        }
        System.out.println("❌ GymService not available!");
        return false;
    }
    
    // 10. View all bookings in the system
    public List<Booking> getAllBookings() {
        if (bookingService != null) {
            return bookingService.getAllBookings();
        }
        return new ArrayList<>();
    }
    
    // 11. View all users in the system
    public List<User> getAllUsers() {
        if (gymUserService != null) {
            return gymUserService.getAllUsers();
        }
        return new ArrayList<>();
    }
    
    // 12. View all gym centers
    public List<GymCenter> getAllGymCenters() {
        if (gymService != null) {
            return gymService.getAllCenters();
        }
        return new ArrayList<>();
    }
    
    // 13. View all slots
    public List<Slot> getAllSlots() {
        if (slotService != null) {
            return slotService.getAllSlots();
        }
        return new ArrayList<>();
    }
    
    // 14. View all waitlist entries
    public List<Waitlist> getAllWaitlistEntries() {
        if (waitlistService != null) {
            return waitlistService.getAllWaitlistEntries();
        }
        return new ArrayList<>();
    }
    
    // 15. Get system statistics
    public Map<String, Integer> getSystemStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        
        stats.put("Total Users", gymUserService != null ? gymUserService.getAllUsers().size() : 0);
        stats.put("Total Gym Centers", gymService != null ? gymService.getAllCenters().size() : 0);
        stats.put("Approved Centers", gymService != null ? gymService.getApprovedCenters().size() : 0);
        stats.put("Pending Centers", gymService != null ? gymService.getPendingApprovals().size() : 0);
        stats.put("Total Bookings", bookingService != null ? bookingService.getAllBookings().size() : 0);
        stats.put("Total Slots", slotService != null ? slotService.getAllSlots().size() : 0);
        stats.put("Waitlist Entries", waitlistService != null ? waitlistService.getAllWaitlistEntries().size() : 0);
        stats.put("Pending Registrations", pendingRegistrations.size());
        
        return stats;
    }
    
    // 16. Deactivate user (Admin privilege)
    public boolean deactivateUser(int userId) {
        if (gymUserService != null) {
            return gymUserService.deactivateUser(userId);
        }
        System.out.println("❌ GymUserService not available!");
        return false;
    }
    
    // 17. Activate user (Admin privilege)
    public boolean activateUser(int userId) {
        if (gymUserService != null) {
            return gymUserService.activateUser(userId);
        }
        System.out.println("❌ GymUserService not available!");
        return false;
    }
    
    // 18. Delete user (Admin privilege)
    public boolean deleteUser(int userId) {
        if (gymUserService != null) {
            return gymUserService.deleteUser(userId);
        }
        System.out.println("❌ GymUserService not available!");
        return false;
    }
    
    // 19. View users by role
    public List<User> getUsersByRole(String roleType) {
        if (gymUserService == null) {
            return new ArrayList<>();
        }
        
        int roleId = switch (roleType) {
            case "ADMIN" -> 1;
            case "GYM_OWNER" -> 2;
            case "CUSTOMER" -> 3;
            default -> -1;
        };
        
        if (roleId == -1) {
            return new ArrayList<>();
        }
        
        return gymUserService.getUsersByRole(roleId);
    }
    
    // 20. Display pending owner registrations
    public void displayPendingOwnerRegistrations() {
        List<Registration> pendingOwners = getPendingOwnerRegistrations();
        
        if (pendingOwners.isEmpty()) {
            System.out.println("No pending gym owner registrations!");
        } else {
            System.out.println("\n=== PENDING GYM OWNER REGISTRATIONS ===");
            for (Registration reg : pendingOwners) {
                displayRegistration(reg);
            }
        }
    }
    
    // 21. Display pending center approvals
    public void displayPendingCenterApprovals() {
        List<GymCenter> pendingCenters = getPendingCenterApprovals();
        
        if (pendingCenters.isEmpty()) {
            System.out.println("No pending gym center approvals!");
        } else {
            System.out.println("\n=== PENDING GYM CENTER APPROVALS ===");
            for (GymCenter center : pendingCenters) {
                if (gymService != null) {
                    gymService.displayCenter(center);
                }
            }
        }
    }
    
    // 22. Display system statistics
    public void displaySystemStatistics() {
        Map<String, Integer> stats = getSystemStatistics();
        
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║     SYSTEM STATISTICS             ║");
        System.out.println("╠═══════════════════════════════════╣");
        
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.printf("║ %-25s : %5d ║%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("╚═══════════════════════════════════╝");
    }
    
    // 23. Display registration details (helper method)
    private void displayRegistration(Registration reg) {
        System.out.println("Registration ID: " + reg.getRegistrationId());
        System.out.println("Name: " + reg.getName());
        System.out.println("Email: " + reg.getEmail());
        System.out.println("Mobile: " + reg.getMobileNumber());
        System.out.println("Role: " + reg.getRoleType());
        
        if ("GYM_OWNER".equals(reg.getRoleType())) {
            System.out.println("City: " + reg.getCity());
            System.out.println("PAN: " + reg.getPanNumber());
        }
        
        System.out.println("Registration Date: " + reg.getRegistrationDate());
        System.out.println("Status: " + (reg.isApproved() ? "✅ Approved" : "⏳ Pending"));
        System.out.println("---");
    }
}