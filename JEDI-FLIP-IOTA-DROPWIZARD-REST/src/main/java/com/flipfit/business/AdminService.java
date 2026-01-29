package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.dao.GymAdminDAO;
import com.flipfit.dao.GymAdminDAOImpl;
import com.flipfit.exception.RegistrationNotDoneException;
import com.flipfit.exception.IssueWithApprovalException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.InvalidEmailException;
import com.flipfit.exception.InvalidMobileException;
import com.flipfit.exception.InvalidAadhaarException;

/**
 * Service class for Admin role functionality.
 * Handles administrative tasks such as approving gym owner/customer
 * registrations,
 * gym center approvals, and viewing system-wide statistics.
 * 
 * @author team IOTA
 */
public class AdminService {

    private GymAdminDAO gymAdminDAO = new GymAdminDAOImpl();

    private GymUserService gymUserService;
    private GymService gymService;
    private BookingService bookingService;
    private WaitlistService waitlistService;
    private SlotService slotService;

    /**
     * Default constructor for AdminService.
     * Initializes the DAO instance.
     */
    public AdminService() {
        System.out.println("✅ AdminService initialized with Database DAO");
    }

    /**
     * Sets the GymAdminDAO implementation.
     * 
     * @param gymAdminDAO the DAO to set
     */
    public void setGymAdminDAO(GymAdminDAO gymAdminDAO) {
        this.gymAdminDAO = gymAdminDAO;
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
     * Retrieves all pending gym owner registration requests.
     * 
     * @return a list of pending owner registrations
     */
    public List<Registration> getPendingOwnerRegistrations() {
        List<Registration> allPending = gymAdminDAO.viewPendingRegistrations();
        List<Registration> pendingOwners = new ArrayList<>();
        for (Registration reg : allPending) {
            if ("GYM_OWNER".equals(reg.getRoleType())) {
                pendingOwners.add(reg);
            }
        }
        return pendingOwners;
    }

    /**
     * Retrieves all pending registration requests (of any role type).
     * 
     * @return a list of all pending registrations
     */
    public List<Registration> getAllPendingRegistrations() {
        return gymAdminDAO.viewPendingRegistrations();
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
        // We need to fetch the registration details first.
        // GymAdminDAO currently only allows viewing pending.
        // Let's assume we can get it from the pending list for now or add
        // getRegistrationById.
        Registration reg = null;
        for (Registration r : gymAdminDAO.viewPendingRegistrations()) {
            if (r.getRegistrationId() == registrationId) {
                reg = r;
                break;
            }
        }

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
            GymOwner owner = createGymOwner(reg);

            try {
                if (gymUserService.registerUser(owner)) {
                    // Mark registration as approved
                    reg.setApproved(true);
                    // Update registration in DB
                    // Note: Ideally we want a DAO method to update registration approval status
                    // For now, removing from pending is implicitly done by viewed query filtering
                    // on isApproved=0

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
     * Reject registration.
     *
     * @param registrationId the registration ID
     * @return true, if successful
     * @throws RegistrationNotDoneException the registration not done exception
     */
    public boolean rejectRegistration(int registrationId) throws RegistrationNotDoneException {
        // For now, rejection just removes it from pending if we were tracking it,
        // but it should probably delete it from the Registration table.
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
        stats.put("Pending Registrations", gymAdminDAO.viewPendingRegistrations().size());

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

        int roleId;
        switch (roleType) {
            case "ADMIN":
                roleId = 1;
                break;
            case "GYM_OWNER":
                roleId = 2;
                break;
            case "CUSTOMER":
                roleId = 3;
                break;
            default:
                roleId = -1;
                break;
        }

        if (roleId == -1) {
            return new ArrayList<>();
        }

        return gymUserService.getUsersByRole(roleId);
    }

    /**
     * Helper method to create GymOwner from registration data.
     */
    private GymOwner createGymOwner(Registration reg) {
        GymOwner owner = new GymOwner();
        owner.setName(reg.getName());
        owner.setEmail(reg.getEmail());
        owner.setPassword(reg.getPassword());
        owner.setMobileNumber(reg.getMobileNumber());
        owner.setAadhaarNumber(reg.getAadhaarNumber());
        owner.setPanNumber(reg.getPanNumber());
        owner.setGstNumber(reg.getGstNumber());
        owner.setCin(reg.getCin());

        Role ownerRole = new Role(2, "GYM_OWNER", "Gym owner who manages centers");
        owner.setRole(ownerRole);
        return owner;
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