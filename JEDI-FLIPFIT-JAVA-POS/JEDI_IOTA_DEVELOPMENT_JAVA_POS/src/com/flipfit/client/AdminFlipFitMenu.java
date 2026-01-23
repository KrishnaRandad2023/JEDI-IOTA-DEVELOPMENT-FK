package com.flipfit.client;

import java.util.Scanner;

public class AdminFlipFitMenu implements FlipFitMenuInterface{
	@Override
    public void displayMenu(Scanner scanner) {
        int choice = 0;
        while (choice != 4) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View Pending Gym Owner Approvals");
            System.out.println("2. View Pending Gym Center Approvals");
            System.out.println("3. View All Bookings");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Displaying pending owners...");
                    System.out.println("Owner ID 101 - Status: PENDING. Approve? (y/n)");
                    break;
                case 2:
                    System.out.println("Displaying pending centers...");
                    System.out.println("Center: Bellandur Fitness - Status: PENDING. Approve? (y/n)");
                    break;
                case 3:
                    System.out.println("Fetching all system bookings...");
                    break;
                case 4:
                    System.out.println("Logging out from Admin...");
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }
}

