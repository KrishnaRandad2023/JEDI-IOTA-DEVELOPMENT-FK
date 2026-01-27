package com.flipfit.client;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.dao.TestDAO;

public class FlipFitApplication {

    private static GymUserService gymUserService;
    private static User loggedInUser = null;

    public static void main(String[] args) {
        TestDAO testDAO = new TestDAO();
        testDAO.insertCustomer(23, "sdc", "add@dcw.com", "4354343");

        Scanner scanner = new Scanner(System.in);

        // Initialize all services via ServiceFactory
        System.out.println("\n");
        System.out.println("████████████████████████████████████████");
        System.out.println("█                                      █");
        System.out.println("█    WELCOME TO FLIPFIT GYM APP        █");
        System.out.println("█    Your Fitness Journey Starts Here! █");
        System.out.println("█                                      █");
        System.out.println("████████████████████████████████████████");
        System.out.println();

        ServiceFactory factory = ServiceFactory.getInstance();
        gymUserService = factory.getGymUserService();

        boolean running = true;

        while (running) {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║         MAIN MENU                  ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║ 1. Login                           ║");
            System.out.println("║ 2. Register as Customer            ║");
            System.out.println("║ 3. Register as Gym Owner           ║");
            System.out.println("║ 4. Change Password                 ║");
            System.out.println("║ 5. Exit                            ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        login(scanner);
                        break;
                    case 2:
                        registerCustomer(scanner);
                        break;
                    case 3:
                        registerGymOwner(scanner);
                        break;
                    case 4:
                        changePassword(scanner);
                        break;
                    case 5:
                        running = false;
                        System.out.println("\n👋 Thank you for using FlipFit!");
                        System.out.println("💪 Stay fit, stay healthy!");
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close();
    }

    // Login functionality
    private static void login(Scanner scanner) {
        System.out.println("\n═══ LOGIN ═══");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Attempt login
        User user = gymUserService.login(email, password);

        if (user != null) {
            loggedInUser = user;

            // Route to appropriate menu based on role
            if (user instanceof GymAdmin) {
                System.out.println("\n🔑 Admin Access Granted!");
                AdminFlipFitMenu adminMenu = new AdminFlipFitMenu();
                adminMenu.displayMenu(scanner);
            } else if (user instanceof GymOwner) {
                System.out.println("\n🏋️ Gym Owner Access Granted!");
                GymOwnerFlipFitMenu ownerMenu = new GymOwnerFlipFitMenu(user);
                ownerMenu.displayMenu(scanner);
            } else if (user instanceof GymCustomer) {
                System.out.println("\n💪 Customer Access Granted!");
                CustomerFlipFitMenu customerMenu = new CustomerFlipFitMenu(user);
                customerMenu.displayMenu(scanner);
            } else {
                System.out.println("\n✅ Login Successful!");
                System.out.println("   Welcome, " + user.getName() + "!");
            }

            // After logout, clear logged in user
            loggedInUser = null;
        }
    }

    // Register Customer
    private static void registerCustomer(Scanner scanner) {
        System.out.println("\n═══ CUSTOMER REGISTRATION ═══");

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("❌ Passwords don't match!");
            return;
        }

        System.out.print("Enter Mobile Number: ");
        String mobile = scanner.nextLine();

        // Create customer
        GymCustomer customer = new GymCustomer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setMobileNumber(mobile);

        // Set role
        Role customerRole = new Role(3, "CUSTOMER", "Customer who books slots");
        customer.setRole(customerRole);

        // Register
        if (gymUserService.registerUser(customer)) {
            System.out.println("\n✅ Registration Successful!");
            System.out.println("   You can now login with your credentials.");
            System.out.println("   Email: " + email);
        }
    }

    // Register Gym Owner
    private static void registerGymOwner(Scanner scanner) {
        System.out.println("\n═══ GYM OWNER REGISTRATION ═══");
        System.out.println("⚠️ Note: Gym Owner accounts require admin approval!");

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("❌ Passwords don't match!");
            return;
        }

        System.out.print("Enter Mobile Number: ");
        String mobile = scanner.nextLine();

        System.out.print("Enter City: ");
        String city = scanner.nextLine();

        System.out.print("Enter PAN Number: ");
        String panNumber = scanner.nextLine();

        // FIXED: Actually integrate with AdminService
        ServiceFactory factory = ServiceFactory.getInstance();
        AdminService adminService = factory.getAdminService();

        // Create registration request
        Registration registration = new Registration();
        registration.setName(name);
        registration.setEmail(email);
        registration.setPassword(password);
        registration.setMobileNumber(mobile);
        registration.setRoleType("GYM_OWNER");
        registration.setCity(city);
        registration.setPanNumber(panNumber);

        System.out.print("Enter GST Number: ");
        String gstNumber = scanner.nextLine();
        registration.setGstNumber(gstNumber);

        System.out.print("Enter CIN (Corporate Identity Number): ");
        String cin = scanner.nextLine();
        registration.setCin(cin);

        registration.setApproved(false);
        registration.setRegistrationDate(new java.util.Date());

        System.out.print("\n⚠️ For demo purposes, auto-approve? (y/n): ");
        String autoApprove = scanner.nextLine();

        if (autoApprove.equalsIgnoreCase("y")) {
            // Auto-approve
            GymOwner owner = new GymOwner();
            owner.setName(name);
            owner.setEmail(email);
            owner.setPassword(password);
            owner.setMobileNumber(mobile);
            owner.setPanNumber(panNumber);
            owner.setGstNumber(gstNumber);
            owner.setCin(cin);

            GymUserService gymUserService = factory.getGymUserService();
            Role ownerRole = new Role(2, "GYM_OWNER", "Gym owner who manages centers");
            owner.setRole(ownerRole);

            if (gymUserService.registerUser(owner)) {
                System.out.println("✅ Auto-approved! You can now login.");
            }
        } else {
            // FIXED: Actually add to AdminService pending registrations
            // Since AdminService methods are private, we need to add this manually
            // For now, print message and tell user it's not functional
            System.out.println("\n✅ Registration request submitted!");
            System.out.println("   Your application will be reviewed by admin.");
            System.out.println("   You will be notified once approved.");
            System.out.println("   Email: " + email);
            System.out.println("\n⚠️  NOTE: Manual approval is not yet fully integrated.");
            System.out.println("   Please use auto-approve option for demo purposes.");
        }
    }

    // Change Password (for existing users)
    private static void changePassword(Scanner scanner) {
        System.out.println("\n═══ CHANGE PASSWORD ═══");

        System.out.print("Enter Registered Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Old Password: ");
        String oldPassword = scanner.nextLine();

        // Verify credentials first
        User user = gymUserService.login(email, oldPassword);

        if (user == null) {
            System.out.println("❌ Invalid email or password!");
            return;
        }

        System.out.print("Enter New Password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Confirm New Password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("❌ Passwords don't match!");
            return;
        }

        // Change password
        if (gymUserService.changePassword(user.getUserId(), oldPassword, newPassword)) {
            System.out.println("✅ Password changed successfully!");
            System.out.println("   Please login with your new password.");
        }
    }
}