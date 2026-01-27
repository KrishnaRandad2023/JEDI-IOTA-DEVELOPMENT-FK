package com.flipfit.client;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.business.*;

public class GymOwnerFlipFitMenu implements FlipFitMenuInterface {
    
    private GymOwnerService gymOwnerService;
    private User loggedInOwner;
    
    public GymOwnerFlipFitMenu(User owner) {
        // Get services from factory
        ServiceFactory factory = ServiceFactory.getInstance();
        this.gymOwnerService = factory.getGymOwnerService();
        this.loggedInOwner = owner;
    }
    
    @Override
    public void displayMenu(Scanner scanner) {
        int choice = 0;
        
        System.out.println("\n👋 Welcome, " + loggedInOwner.getName() + "!");
        
        while (choice != 10) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║       GYM OWNER MENU               ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. Add Gym Center                  ║");
            System.out.println("║ 2. Update Gym Center               ║");
            System.out.println("║ 3. Delete Gym Center               ║");
            System.out.println("║ 4. View My Centers                 ║");
            System.out.println("║ 5. Add Slot to Center              ║");
            System.out.println("║ 6. View Slots for My Centers       ║");
            System.out.println("║ 7. Delete Slot                     ║");
            System.out.println("║ 8. View Bookings for My Centers    ║");
            System.out.println("║ 9. View My Statistics              ║");
            System.out.println("║ 10. Logout                         ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Enter choice: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        addGymCenter(scanner);
                        break;
                    case 2:
                        updateGymCenter(scanner);
                        break;
                    case 3:
                        deleteGymCenter(scanner);
                        break;
                    case 4:
                        viewMyCenters();
                        break;
                    case 5:
                        addSlotToCenter(scanner);
                        break;
                    case 6:
                        viewMySlots();
                        break;
                    case 7:
                        deleteSlot(scanner);
                        break;
                    case 8:
                        viewMyBookings(scanner);
                        break;
                    case 9:
                        viewMyStatistics();
                        break;
                    case 10:
                        System.out.println("👋 Logging out from Gym Owner...");
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
    
    // 1. Add gym center
    private void addGymCenter(Scanner scanner) {
        System.out.println("\n═══ ADD NEW GYM CENTER ═══");
        
        System.out.print("Enter Center Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        
        System.out.print("Enter Capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        
        boolean success = gymOwnerService.addGymCenter(
            loggedInOwner.getUserId(), 
            name, 
            address, 
            city, 
            capacity
        );
        
        if (success) {
            System.out.println("✅ Center added successfully!");
            System.out.println("⏳ Pending admin approval...");
        }
    }
    
    // 2. Update gym center
    private void updateGymCenter(Scanner scanner) {
        System.out.println("\n═══ UPDATE GYM CENTER ═══");
        
        // Show owner's centers
        gymOwnerService.displayMyCenters(loggedInOwner.getUserId());
        
        List<GymCenter> myCenters = gymOwnerService.viewMyCenters(loggedInOwner.getUserId());
        if (myCenters.isEmpty()) {
            return;
        }
        
        System.out.print("\nEnter Center ID to update (0 to cancel): ");
        int centerId = scanner.nextInt();
        scanner.nextLine();
        
        if (centerId == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        System.out.print("Enter New Center Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter New Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter New City: ");
        String city = scanner.nextLine();
        
        System.out.print("Enter New Capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        
        gymOwnerService.updateGymCenter(
            loggedInOwner.getUserId(), 
            centerId, 
            name, 
            address, 
            city, 
            capacity
        );
    }
    
    // 3. Delete gym center
    private void deleteGymCenter(Scanner scanner) {
        System.out.println("\n═══ DELETE GYM CENTER ═══");
        
        // Show owner's centers
        gymOwnerService.displayMyCenters(loggedInOwner.getUserId());
        
        List<GymCenter> myCenters = gymOwnerService.viewMyCenters(loggedInOwner.getUserId());
        if (myCenters.isEmpty()) {
            return;
        }
        
        System.out.print("\nEnter Center ID to delete (0 to cancel): ");
        int centerId = scanner.nextInt();
        scanner.nextLine();
        
        if (centerId == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        System.out.print("⚠️ Are you sure you want to delete this center? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            gymOwnerService.deleteGymCenter(loggedInOwner.getUserId(), centerId);
        } else {
            System.out.println("Cancelled.");
        }
    }
    
    // 4. View my centers
    private void viewMyCenters() {
        System.out.println("\n═══ MY GYM CENTERS ═══");
        gymOwnerService.displayMyCenters(loggedInOwner.getUserId());
        
        // Show pending vs approved
        List<GymCenter> approved = gymOwnerService.viewMyApprovedCenters(loggedInOwner.getUserId());
        List<GymCenter> pending = gymOwnerService.viewMyPendingCenters(loggedInOwner.getUserId());
        
        System.out.println("\n📊 Summary:");
        System.out.println("   ✅ Approved: " + approved.size());
        System.out.println("   ⏳ Pending: " + pending.size());
    }
    
 // 5. Add slot to center
    private void addSlotToCenter(Scanner scanner) {
        System.out.println("\n═══ ADD SLOT TO CENTER ═══");
        
        // Show only approved centers
        List<GymCenter> approvedCenters = gymOwnerService.viewMyApprovedCenters(loggedInOwner.getUserId());
        
        if (approvedCenters.isEmpty()) {
            System.out.println("❌ You have no approved centers yet!");
            System.out.println("   Slots can only be added to approved centers.");
            return;
        }
        
        System.out.println("\nYour Approved Centers:");
        for (GymCenter center : approvedCenters) {
            System.out.println("  ID: " + center.getCenterId() + " - " + center.getCenterName());
        }
        
        System.out.print("\nEnter Center ID: ");
        int centerId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Start Time (HH:mm format, e.g., 06:00): ");
        String startTimeStr = scanner.nextLine();
        
        System.out.print("Enter End Time (HH:mm format, e.g., 07:00): ");
        String endTimeStr = scanner.nextLine();
        
        System.out.print("Enter Total Seats: ");
        int totalSeats = scanner.nextInt();
        scanner.nextLine();
        
        // FIXED: Add validation for totalSeats > 0
        if (totalSeats <= 0) {
            System.out.println("❌ Total seats must be greater than 0!");
            return;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
            LocalTime endTime = LocalTime.parse(endTimeStr, formatter);
            
            // FIXED: Validate that end time is after start time
            if (!endTime.isAfter(startTime)) {
                System.out.println("❌ End time must be after start time!");
                return;
            }
            
            boolean success = gymOwnerService.addSlotToCenter(
                loggedInOwner.getUserId(), 
                centerId, 
                startTime, 
                endTime, 
                totalSeats
            );
            
            if (success) {
                System.out.println("✅ Slot added successfully!");
            }
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid time format! Please use HH:mm (e.g., 06:00)");
        }
    }
    
    // 6. View my slots
    private void viewMySlots() {
        System.out.println("\n═══ MY SLOTS ═══");
        gymOwnerService.displayMySlots(loggedInOwner.getUserId());
    }
    
    // 7. Delete slot
    private void deleteSlot(Scanner scanner) {
        System.out.println("\n═══ DELETE SLOT ═══");
        
        // Show owner's slots
        gymOwnerService.displayMySlots(loggedInOwner.getUserId());
        
        List<Slot> mySlots = gymOwnerService.viewMySlots(loggedInOwner.getUserId());
        if (mySlots.isEmpty()) {
            return;
        }
        
        System.out.print("\nEnter Slot ID to delete (0 to cancel): ");
        int slotId = scanner.nextInt();
        scanner.nextLine();
        
        if (slotId == 0) {
            System.out.println("Cancelled.");
            return;
        }
        
        System.out.print("⚠️ Are you sure you want to delete this slot? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            gymOwnerService.deleteSlot(loggedInOwner.getUserId(), slotId);
        } else {
            System.out.println("Cancelled.");
        }
    }
    
    // 8. View bookings for my centers
    private void viewMyBookings(Scanner scanner) {
        System.out.println("\n═══ BOOKINGS FOR MY CENTERS ═══");
        System.out.println("1. View All Bookings");
        System.out.println("2. View Bookings for Specific Center");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            List<Booking> myBookings = gymOwnerService.viewBookingsForMyCenters(loggedInOwner.getUserId());
            
            if (myBookings.isEmpty()) {
                System.out.println("No bookings for your centers yet.");
            } else {
                System.out.println("\nTotal Bookings: " + myBookings.size());
                for (Booking booking : myBookings) {
                    System.out.println("Booking ID: " + booking.getBookingId() + 
                                     " | User: " + booking.getUserId() + 
                                     " | Slot: " + booking.getSlotId() + 
                                     " | Status: " + booking.getStatus());
                }
            }
        } else if (choice == 2) {
            gymOwnerService.displayMyCenters(loggedInOwner.getUserId());
            
            System.out.print("\nEnter Center ID: ");
            int centerId = scanner.nextInt();
            scanner.nextLine();
            
            gymOwnerService.displayBookingsForCenter(loggedInOwner.getUserId(), centerId);
        }
    }
    
    // 9. View statistics
    private void viewMyStatistics() {
        System.out.println("\n═══ MY STATISTICS ═══");
        gymOwnerService.displayMyStatistics(loggedInOwner.getUserId());
    }
}