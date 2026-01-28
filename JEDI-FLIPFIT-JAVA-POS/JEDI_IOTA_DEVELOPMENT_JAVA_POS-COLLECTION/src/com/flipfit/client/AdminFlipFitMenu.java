package com.flipfit.client;

import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.business.*;

public class AdminFlipFitMenu implements FlipFitMenuInterface {
    
    private AdminService adminService;
    private GymService gymService;
    
    public AdminFlipFitMenu() {
        // Get services from factory
        ServiceFactory factory = ServiceFactory.getInstance();
        this.adminService = factory.getAdminService();
        this.gymService = factory.getGymService();
    }
    
    @Override
    public void displayMenu(Scanner scanner) {
        int choice = 0;
        
        while (choice != 9) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║         ADMIN MENU                 ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. View Pending Gym Owner Approvals║");
            System.out.println("║ 2. View Pending Gym Center Approvals");
            System.out.println("║ 3. Approve Gym Owner               ║");
            System.out.println("║ 4. Approve Gym Center              ║");
            System.out.println("║ 5. View All Bookings               ║");
            System.out.println("║ 6. View All Users                  ║");
            System.out.println("║ 7. View System Statistics          ║");
            System.out.println("║ 8. Manage Users                    ║");
            System.out.println("║ 9. Logout                          ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Enter choice: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        viewPendingOwnerApprovals();
                        break;
                    case 2:
                        viewPendingCenterApprovals();
                        break;
                    case 3:
                        approveGymOwner(scanner);
                        break;
                    case 4:
                        approveGymCenter(scanner);
                        break;
                    case 5:
                        viewAllBookings();
                        break;
                    case 6:
                        viewAllUsers();
                        break;
                    case 7:
                        viewSystemStatistics();
                        break;
                    case 8:
                        manageUsers(scanner);
                        break;
                    case 9:
                        System.out.println("👋 Logging out from Admin...");
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
    
    // 1. View pending gym owner registrations
    private void viewPendingOwnerApprovals() {
        System.out.println("\n═══ PENDING GYM OWNER REGISTRATIONS ═══");
        adminService.displayPendingOwnerRegistrations();
    }
    
    // 2. View pending gym center approvals
    private void viewPendingCenterApprovals() {
        System.out.println("\n═══ PENDING GYM CENTER APPROVALS ═══");
        adminService.displayPendingCenterApprovals();
    }
    
    // 3. Approve gym owner
    private void approveGymOwner(Scanner scanner) {
        adminService.displayPendingOwnerRegistrations();
        
        List<Registration> pendingOwners = adminService.getPendingOwnerRegistrations();
        if (pendingOwners.isEmpty()) {
            return;
        }
        
        System.out.print("\nEnter Registration ID to approve (0 to cancel): ");
        int regId = scanner.nextInt();
        scanner.nextLine();
        
        if (regId == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        System.out.print("Approve this registration? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            adminService.approveOwnerRegistration(regId);
        } else {
            System.out.print("Reject this registration? (y/n): ");
            String reject = scanner.nextLine();
            if (reject.equalsIgnoreCase("y")) {
                adminService.rejectRegistration(regId);
            }
        }
    }
    
    // 4. Approve gym center
    private void approveGymCenter(Scanner scanner) {
        adminService.displayPendingCenterApprovals();
        
        List<GymCenter> pendingCenters = adminService.getPendingCenterApprovals();
        if (pendingCenters.isEmpty()) {
            return;
        }
        
        System.out.print("\nEnter Center ID to approve (0 to cancel): ");
        int centerId = scanner.nextInt();
        scanner.nextLine();
        
        if (centerId == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        System.out.print("Approve this gym center? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            adminService.approveGymCenter(centerId);
        } else {
            System.out.print("Reject this gym center? (y/n): ");
            String reject = scanner.nextLine();
            if (reject.equalsIgnoreCase("y")) {
                adminService.rejectGymCenter(centerId);
            }
        }
    }
    
    // 5. View all bookings
    private void viewAllBookings() {
        System.out.println("\n═══ ALL SYSTEM BOOKINGS ═══");
        List<Booking> allBookings = adminService.getAllBookings();
        
        if (allBookings.isEmpty()) {
            System.out.println("No bookings in the system.");
        } else {
            System.out.println("Total Bookings: " + allBookings.size());
            for (Booking booking : allBookings) {
                System.out.println("Booking ID: " + booking.getBookingId() + 
                                 " | User: " + booking.getUserId() + 
                                 " | Slot: " + booking.getSlotId() + 
                                 " | Status: " + booking.getStatus());
            }
        }
    }
    
    // 6. View all users
    private void viewAllUsers() {
        System.out.println("\n═══ ALL SYSTEM USERS ═══");
        List<User> allUsers = adminService.getAllUsers();
        
        if (allUsers.isEmpty()) {
            System.out.println("No users in the system.");
        } else {
            System.out.println("Total Users: " + allUsers.size());
            for (User user : allUsers) {
                System.out.println("ID: " + user.getUserId() + 
                                 " | Name: " + user.getName() + 
                                 " | Email: " + user.getEmail() + 
                                 " | Role: " + (user.getRole() != null ? user.getRole().getRoleName() : "N/A"));
            }
        }
    }
    
    // 7. View system statistics
    private void viewSystemStatistics() {
        adminService.displaySystemStatistics();
    }
    
    // 8. Manage users
    private void manageUsers(Scanner scanner) {
        System.out.println("\n═══ USER MANAGEMENT ═══");
        System.out.println("1. Activate User");
        System.out.println("2. Deactivate User");
        System.out.println("3. Delete User");
        System.out.println("4. Back");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice >= 1 && choice <= 3) {
            System.out.print("Enter User ID: ");
            int userId = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> adminService.activateUser(userId);
                case 2 -> adminService.deactivateUser(userId);
                case 3 -> {
                    System.out.print("Are you sure? (y/n): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("y")) {
                        adminService.deleteUser(userId);
                    }
                }
            }
        }
    }
}