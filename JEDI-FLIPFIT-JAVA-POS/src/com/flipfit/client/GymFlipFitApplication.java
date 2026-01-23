package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * Main Application class for FlipFit Gym Management System
 */
public class GymFlipFitApplication {
    
    private static Scanner scanner = new Scanner(System.in);
    private static GymService gymService = new GymService();
    private static GymAdminService gymAdminService = new GymAdminService();
    private static GymOwnerService gymOwnerService = new GymOwnerService();
    private static GymCustomerService gymCustomerService = new GymCustomerService();
    private static BookingService bookingService = new BookingService();
    private static NotificationService notificationService = new NotificationService();
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("Welcome to FlipFit Gym Management System");
        System.out.println("===========================================");
        
        boolean exit = false;
        
        while (!exit) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    handleCustomerMenu();
                    break;
                case 2:
                    handleGymOwnerMenu();
                    break;
                case 3:
                    handleAdminMenu();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for using FlipFit!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n===========================================");
        System.out.println("Main Menu");
        System.out.println("===========================================");
        System.out.println("1. Customer");
        System.out.println("2. Gym Owner");
        System.out.println("3. Admin");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void handleCustomerMenu() {
        System.out.println("\n=== Customer Menu ===");
        System.out.println("1. Register");
        System.out.println("2. View Centers by Area");
        System.out.println("3. Check Availability");
        System.out.println("4. Make Booking");
        System.out.println("5. Cancel Booking");
        System.out.println("6. View My Bookings");
        System.out.println("7. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                registerCustomer();
                break;
            case 2:
                viewCentersByArea();
                break;
            case 3:
                checkAvailability();
                break;
            case 4:
                makeBooking();
                break;
            case 5:
                cancelBooking();
                break;
            case 6:
                viewMyBookings();
                break;
            case 7:
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void handleGymOwnerMenu() {
        System.out.println("\n=== Gym Owner Menu ===");
        System.out.println("1. Register");
        System.out.println("2. Add Gym Center");
        System.out.println("3. Remove Gym Center");
        System.out.println("4. Update Gym Center");
        System.out.println("5. Add Slot");
        System.out.println("6. View My Centers");
        System.out.println("7. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                registerGymOwner();
                break;
            case 2:
                addGymCenter();
                break;
            case 3:
                removeGymCenter();
                break;
            case 4:
                updateGymCenter();
                break;
            case 5:
                addSlot();
                break;
            case 6:
                viewMyCenters();
                break;
            case 7:
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void handleAdminMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Verify Gym Owner");
        System.out.println("2. Approve Gym Center");
        System.out.println("3. View Pending Owner Requests");
        System.out.println("4. View Pending Center Requests");
        System.out.println("5. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                verifyGymOwner();
                break;
            case 2:
                approveGymCenter();
                break;
            case 3:
                gymAdminService.viewPendingOwnerRequests();
                break;
            case 4:
                gymAdminService.viewPendingCenterRequests();
                break;
            case 5:
                // Return to main menu
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    // Customer related methods
    private static void registerCustomer() {
        System.out.println("\n=== Register Customer ===");
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter Pincode: ");
        String pincode = scanner.nextLine();
        
        GymCustomer customer = new GymCustomer(userId, name, email, password, phone, address, pincode);
        System.out.println("Customer registered successfully!");
    }
    
    private static void viewCentersByArea() {
        System.out.print("Enter area/city: ");
        String area = scanner.nextLine();
        gymCustomerService.viewCenterByArea(area);
    }
    
    private static void checkAvailability() {
        System.out.print("Enter Center ID: ");
        int centerId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);
        
        gymCustomerService.checkAvailability(centerId, date);
    }
    
    private static void makeBooking() {
        System.out.println("\n=== Make Booking ===");
        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        
        System.out.print("Enter Center ID: ");
        int centerId = scanner.nextInt();
        
        System.out.print("Enter Slot ID: ");
        int slotId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);
        
        Booking booking = new Booking(bookingId, userId, centerId, slotId, 
                                     LocalDateTime.now(), BookingStatus.PENDING, 
                                     date, LocalTime.of(6, 0), LocalTime.of(7, 0));
        
        if (bookingService.createBooking(booking)) {
            System.out.println("Booking created successfully!");
        }
    }
    
    private static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        
        if (bookingService.cancelBooking(bookingId)) {
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Failed to cancel booking.");
        }
    }
    
    private static void viewMyBookings() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        gymCustomerService.viewMyBookings(userId);
    }
    
    // Gym Owner related methods
    private static void registerGymOwner() {
        System.out.println("\n=== Register Gym Owner ===");
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter PAN Card: ");
        String panCard = scanner.nextLine();
        
        System.out.print("Enter Aadhar: ");
        String aadhar = scanner.nextLine();
        
        System.out.print("Enter GST Number: ");
        String gstNumber = scanner.nextLine();
        
        GymOwner owner = new GymOwner(userId, name, email, password, phone, panCard, aadhar, gstNumber, false);
        System.out.println("Gym Owner registered successfully! Awaiting verification.");
    }
    
    private static void addGymCenter() {
        System.out.println("\n=== Add Gym Center ===");
        System.out.print("Enter Center ID: ");
        int centerId = scanner.nextInt();
        
        System.out.print("Enter Owner ID: ");
        int ownerId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Center Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter GST Number: ");
        String gst = scanner.nextLine();
        
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        
        System.out.print("Enter Capacity: ");
        int capacity = scanner.nextInt();
        
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        
        GymCenter center = new GymCenter(centerId, ownerId, name, gst, address, city, capacity, price, false);
        gymOwnerService.addGymCenter(center);
        System.out.println("Gym Center added successfully! Awaiting approval.");
    }
    
    private static void removeGymCenter() {
        System.out.print("Enter Center ID to remove: ");
        int centerId = scanner.nextInt();
        
        if (gymOwnerService.removeGymCenter(centerId)) {
            System.out.println("Center removed successfully!");
        }
    }
    
    private static void updateGymCenter() {
        System.out.println("Update Gym Center functionality - To be implemented");
    }
    
    private static void addSlot() {
        System.out.println("\n=== Add Slot ===");
        System.out.print("Enter Slot ID: ");
        int slotId = scanner.nextInt();
        
        System.out.print("Enter Center ID: ");
        int centerId = scanner.nextInt();
        
        System.out.print("Enter Start Hour (0-23): ");
        int startHour = scanner.nextInt();
        
        System.out.print("Enter End Hour (0-23): ");
        int endHour = scanner.nextInt();
        
        System.out.print("Enter Capacity: ");
        int capacity = scanner.nextInt();
        
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        
        Slot slot = new Slot(slotId, centerId, LocalTime.of(startHour, 0), 
                            LocalTime.of(endHour, 0), capacity, "AVAILABLE", price);
        
        if (gymOwnerService.addSlot(slot)) {
            System.out.println("Slot added successfully!");
        }
    }
    
    private static void viewMyCenters() {
        System.out.print("Enter Owner ID: ");
        int ownerId = scanner.nextInt();
        gymOwnerService.viewMyCenter(ownerId);
    }
    
    // Admin related methods
    private static void verifyGymOwner() {
        System.out.print("Enter Owner ID to verify: ");
        int ownerId = scanner.nextInt();
        
        if (gymAdminService.verifyGymOwner(ownerId)) {
            System.out.println("Gym Owner verified successfully!");
        }
    }
    
    private static void approveGymCenter() {
        System.out.print("Enter Center ID to approve: ");
        int centerId = scanner.nextInt();
        
        if (gymAdminService.approveGymCenter(centerId)) {
            System.out.println("Gym Center approved successfully!");
        }
    }
}
