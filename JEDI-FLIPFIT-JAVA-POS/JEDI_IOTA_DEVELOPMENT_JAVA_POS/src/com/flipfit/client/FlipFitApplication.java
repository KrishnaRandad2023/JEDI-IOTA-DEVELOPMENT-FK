package com.flipfit.client;

import java.util.Scanner;

public class FlipFitApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("========================================");
        System.out.println("      WELCOME TO FLIPFIT APP            ");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Login");
            System.out.println("2. Registration (Customer)");
            System.out.println("3. Registration (Gym Owner)");
            System.out.println("4. Change Password"); // New Case added
            System.out.println("5. Exit");
            System.out.print("Please enter your choice: ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    System.out.println("Directing to Customer Registration...");
                    // Logic to register a new user onto the platform [cite: 13]
                    break;
                case 3:
                    System.out.println("Directing to Gym Owner Registration...");
                    break;
                case 4:
                    changePassword(scanner); // New Method call
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using FlipFit!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter Email: ");
        String email = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        // Redirecting to menus based on user role [cite: 12]
        if (email.contains("admin")) {
            System.out.println("Login Successful! Welcome Admin.");
            AdminFlipFitMenu adminMenu = new AdminFlipFitMenu();
            adminMenu.displayMenu(scanner); // Directly calling the method to display results
        } else if (email.contains("owner")) {
            System.out.println("Login Successful! Welcome Gym Owner.");
            GymOwnerFlipFitMenu ownerMenu = new GymOwnerFlipFitMenu();
            ownerMenu.displayMenu(scanner); // Directly calling the method to display results
        } else {
            System.out.println("Login Successful! Welcome Customer.");
            CustomerFlipFitMenu customerMenu = new CustomerFlipFitMenu();
            customerMenu.displayMenu(scanner); // Directly calling the method to display results
        }
    }

    private static void changePassword(Scanner scanner) {
        System.out.print("Enter Registered Email: ");
        String email = scanner.next();
        System.out.print("Enter Old Password: ");
        String oldPass = scanner.next();
        System.out.print("Enter New Password: ");
        String newPass = scanner.next();
        
        // Simple verification simulation
        System.out.println("Password for " + email + " updated successfully!");
    }
}
