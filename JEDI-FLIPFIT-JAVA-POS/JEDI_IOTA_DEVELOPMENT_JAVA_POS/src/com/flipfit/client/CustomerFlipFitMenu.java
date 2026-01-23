package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.bean.User;
import com.flipfit.business.GymService;
import com.flipfit.business.BookingService;
import com.flipfit.business.WaitlistService;

public class CustomerFlipFitMenu implements FlipFitMenuInterface {
    
	@Override
    public void displayMenu(Scanner scanner) {
        int choice = 0;
        while (choice != 5) {
            System.out.println("\n--- Gym Customer Menu ---");
            System.out.println("1. View Gyms in Bangalore");
            System.out.println("2. Book a Workout Slot");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View My Bookings Plan");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Gyms in Bangalore: Bellandur, HSR, Indiranagar.");
                    break;
                case 2:
                    System.out.print("Enter Slot ID to book: ");
                    int slotId = scanner.nextInt();
                    System.out.println("Booking successful!");
                    break;
                case 3:
                    System.out.print("Enter Booking ID to cancel: ");
                    int bookId = scanner.nextInt();
                    System.out.println("Booking cancelled.");
                    break;
                case 4:
                    System.out.println("Your Plan: Slot 6PM at Bellandur on Friday.");
                    break;
                case 5:
                    System.out.println("Logging out from Customer...");
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }
}