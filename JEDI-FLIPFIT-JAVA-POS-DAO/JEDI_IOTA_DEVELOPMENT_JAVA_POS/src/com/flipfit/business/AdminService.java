package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.exception.RegistrationNotDoneException;
import com.flipfit.exception.IssueWithApprovalException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.InvalidEmailException;
import com.flipfit.exception.InvalidMobileException;
import com.flipfit.exception.InvalidAadhaarException;

/**
 * The Class AdminService.
 *
 * @author team IOTA
 * @ClassName "AdminService"
 */
public class AdminService {

    /** The pending registrations. */
    private List<Registration> pendingRegistrations; // Pending user registrations

    /** The registrations. */
    private Map<Integer, Registration> registrations; // registrationId -> Registration

    /** The approved owner ids. */
    private Set<Integer> approvedOwnerIds; // Set of approved gym owner IDs

    /** The rejected registration ids. */
    private Set<Integer> rejectedRegistrationIds; // Set of rejected registration IDs

    /** The registration id counter. */
    private int registrationIdCounter;

    /** The gym user service. */
    private GymUserService gymUserService;

    /** The gym service. */
    private GymService gymService;

    /** The booking service. */
    private BookingService bookingService;

    /** The waitlist service. */
    private WaitlistService waitlistService;

    /** The slot service. */
    private SlotService slotService;

    /**
     * Instantiates a new admin service.
     */
    public AdminService() {
        this.pendingRegistrations = new ArrayList<>();
        this.registrations = new HashMap<>();
        this.approvedOwnerIds = new HashSet<>();
        this.rejectedRegistrationIds = new HashSet<>();
        this.registrationIdCounter = 1;

        System.out.println("✅ AdminService initialized");
    }

    /**
     * Sets the gym user service.
     *
     * @param gymUserService the new gym user service
     */
    public void setGymUserService(GymUserService gymUserService) {
        this.gymUserService = gymUserService;
    }

    /**
     * Sets the gym service.
     *
     * @param gymService the new gym service
     */
    public void setGymService(GymService gymService) {
        this.gymService = gymService;
    }

    /**
     * Sets the booking service.
     *
     * @param bookingService the new booking service
     */
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
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
     * Sets the slot service.
     *
     * @param slotService the new slot service
     */
    public void setSlotService(SlotService slotService) {
        this.slotService = slotService;
    }

    /**
     * Initialize hardcoded registrations.
     */
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
        ownerReg1.setAadhaarNumber("123456789012");
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
        ownerReg2.setAadhaarNumber("234567890123");
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
        customerReg.setAadhaarNumber("345678901234");
        customerReg.setApproved(false);
        addPendingRegistration(customerReg);

        System.out.println("✅ Initialized with " + pendingRegistrations.size() + " pending registrations");
    }

    /**
     * Adds the pending registration.
     *
     * @param reg the reg
     */
    private void addPendingRegistration(Registration reg) {
        registrations.put(reg.getRegistrationId(), reg);
        pendingRegistrations.add(reg);
    }

    /**
     * Gets the pending owner registrations.
     *
     * @return the pending owner registrations
     */
    public List<Registration> getPendingOwnerRegistrations() {
        List<Registration> pendingOwners = new ArrayList<>();

        for (Registration reg : pendingRegistrations) {
            if ("GYM_OWNER".equals(reg.getRoleType()) && !reg.isApproved()) {
                pendingOwners.add(reg);
            }
        }

        return pendingOwners;
    }

    /**
     * Gets the pending customer registrations.
     *
     * @return the pending customer registrations
     */
    public List<Registration> getPendingCustomerRegistrations() {
        List<Registration> pendingCustomers = new ArrayList<>();

        for (Registration reg : pendingRegistrations) {
            if ("CUSTOMER".equals(reg.getRoleType()) && !reg.isApproved()) {
                pendingCustomers.add(reg);
            }
        }

        return pendingCustomers;
    }

    /**
     * Gets the all pending registrations.
     *
     * @return the all pending registrations
     */
    public List<Registration> getAllPendingRegistrations() {
        return new ArrayList<>(pendingRegistrations);
    }

    /**
     * Approve owner registration.
     *
     * @param registrationId the registration ID
     * @return true, if successful
     * @throws RegistrationNotDoneException the registration not done exception
     * @throws IssueWithApprovalException   the issue with approval exception
     */
    public boolean approveOwnerRegistration(int registrationId)
            throws RegistrationNotDoneException, IssueWithApprovalException {
        Registration reg = registrations.get(registrationId);

        if (reg == null) {
            System.out.println("❌ Registration not found!");
            throw new RegistrationNotDoneException("Registration not found for ID: " + registrationId);
        }

        if (!"GYM_OWNER".equals(reg.getRoleType())) {
            System.out.println("❌ This is not a gym owner registration!");
            throw new IssueWithApprovalException("This is not a gym owner registration!");
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

            try {
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
            } catch (RegistrationNotDoneException | InvalidEmailException | InvalidMobileException
                    | InvalidAadhaarException e) {
                throw new IssueWithApprovalException("Failed to register owner during approval: " + e.getMessage());
            }
        }

        return false;
    }

    /**
     * Approve customer registration.
     *
     * @param registrationId the registration ID
     * @return true, if successful
     * @throws RegistrationNotDoneException the registration not done exception
     * @throws IssueWithApprovalException   the issue with approval exception
     */
    public boolean approveCustomerRegistration(int registrationId)
            throws RegistrationNotDoneException, IssueWithApprovalException {
        Registration reg = registrations.get(registrationId);

        if (reg == null) {
            System.out.println("❌ Registration not found!");
            throw new RegistrationNotDoneException("Registration not found for ID: " + registrationId);
        }

        if (!"CUSTOMER".equals(reg.getRoleType())) {
            System.out.println("❌ This is not a customer registration!");
            throw new IssueWithApprovalException("This is not a customer registration!");
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

            try {
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
            } catch (RegistrationNotDoneException | InvalidEmailException | InvalidMobileException
                    | InvalidAadhaarException e) {
                throw new IssueWithApprovalException("Failed to register customer during approval: " + e.getMessage());
            }
        }

        return false;
    }

    /**
     * Reject registration.
     *
     * @param registrationId the registration ID
     * @return true, if successful
     * @throws RegistrationNotDoneException the registration not done exception
     */
    public boolean rejectRegistration(int registrationId) throws RegistrationNotDoneException {
        Registration reg = registrations.get(registrationId);

        if (reg == null) {
            System.out.println("❌ Registration not found!");
            throw new RegistrationNotDoneException("Registration not found for ID: " + registrationId);
        }

        // Remove from pending list
        pendingRegistrations.remove(reg);
        rejectedRegistrationIds.add(registrationId);

        System.out.println("✅ Registration rejected!");
        System.out.println("   Name: " + reg.getName());
        System.out.println("   Email: " + reg.getEmail());
        return true;
    }

    /**
     * Gets the pending center approvals.
     *
     * @return the pending center approvals
     */
    public List<GymCenter> getPendingCenterApprovals() {
        if (gymService != null) {
            return gymService.getPendingApprovals();
        }
        return new ArrayList<>();
    }

    /**
     * Approve gym center.
     *
     * @param centerId the center ID
     * @return true, if successful
     * @throws IssueWithApprovalException the issue with approval exception
     */
    public boolean approveGymCenter(int centerId) throws IssueWithApprovalException {
        if (gymService != null) {
            boolean success = gymService.approveCenterRegistration(centerId);
            if (!success) {
                throw new IssueWithApprovalException("Failed to approve gym center with ID: " + centerId);
            }
            return true;
        }
        System.out.println("❌ GymService not available!");
        return false;
    }

    /**
     * Reject gym center.
     *
     * @param centerId the center ID
     * @return true, if successful
     */
    public boolean rejectGymCenter(int centerId) {
        if (gymService != null) {
            return gymService.rejectCenterRegistration(centerId);
        }
        System.out.println("❌ GymService not available!");
        return false;
    }

    /**
     * Gets the all bookings.
     *
     * @return the all bookings
     */
    public List<Booking> getAllBookings() {
        if (bookingService != null) {
            return bookingService.getAllBookings();
        }
        return new ArrayList<>();
    }

    /**
     * Gets the all users.
     *
     * @return the all users
     */
    public List<User> getAllUsers() {
        if (gymUserService != null) {
            return gymUserService.getAllUsers();
        }
        return new ArrayList<>();
    }

    /**
     * Gets the all gym centers.
     *
     * @return the all gym centers
     */
    public List<GymCenter> getAllGymCenters() {
        if (gymService != null) {
            return gymService.getAllCenters();
        }
        return new ArrayList<>();
    }

    /**
     * Gets the all slots.
     *
     * @return the all slots
     */
    public List<Slot> getAllSlots() {
        if (slotService != null) {
            return slotService.getAllSlots();
        }
        return new ArrayList<>();
    }

    /**
     * Gets the all waitlist entries.
     *
     * @return the all waitlist entries
     */
    public List<Waitlist> getAllWaitlistEntries() {
        if (waitlistService != null) {
            return waitlistService.getAllWaitlistEntries();
        }
        return new ArrayList<>();
    }

    /**
     * Gets the system statistics.
     *
     * @return the system statistics
     */
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

    /**
     * Deactivate user.
     *
     * @param userId the user ID
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean deactivateUser(int userId) throws UserNotFoundException {
        if (gymUserService != null) {
            return gymUserService.deactivateUser(userId);
        }
        System.out.println("❌ GymUserService not available!");
        return false;
    }

    /**
     * Activate user.
     *
     * @param userId the user ID
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean activateUser(int userId) throws UserNotFoundException {
        if (gymUserService != null) {
            return gymUserService.activateUser(userId);
        }
        System.out.println("❌ GymUserService not available!");
        return false;
    }

    /**
     * Delete user.
     *
     * @param userId the user ID
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean deleteUser(int userId) throws UserNotFoundException {
        if (gymUserService != null) {
            return gymUserService.deleteUser(userId);
        }
        System.out.println("❌ GymUserService not available!");
        return false;
    }

    /**
     * Gets the users by role.
     *
     * @param roleType the role type
     * @return the users by role
     */
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

    /**
     * Display pending owner registrations.
     */
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

    /**
     * Display pending center approvals.
     */
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

    /**
     * Display system statistics.
     */
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

    /**
     * Display registration.
     *
     * @param reg the reg
     */
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
        System.out.println("Aadhaar: " + reg.getAadhaarNumber());

        System.out.println("Registration Date: " + reg.getRegistrationDate());
        System.out.println("Status: " + (reg.isApproved() ? "✅ Approved" : "⏳ Pending"));
        System.out.println("---");
    }
}