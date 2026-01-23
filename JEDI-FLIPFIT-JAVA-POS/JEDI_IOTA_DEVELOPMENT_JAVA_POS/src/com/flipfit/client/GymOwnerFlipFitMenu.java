package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.bean.User;
import com.flipfit.business.GymService;

public class GymOwnerFlipFitMenu implements FlipFitMenuInterface {
    
	@Override
    public void displayMenu(Scanner scanner) {
        int choice = 0;
        while (choice != 4) {
            System.out.println("\n--- Gym Owner Menu ---");
            System.out.println("1. Add Gym Center");
            System.out.println("2. Add Slot to Center");
            System.out.println("3. View My Centers");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Center Name: ");
                    String name = scanner.next();
                    System.out.print("Enter City: ");
                    String city = scanner.next();
                    System.out.println("Center added and sent for Admin approval.");
                    break;
                case 2:
                    System.out.print("Enter Center ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter Time (e.g., 18:00): ");
                    String time = scanner.next();
                    System.out.println("Slot added successfully."); 
                    break;
                case 3:
                    System.out.println("Listing your registered centers...");
                    break;
                case 4:
                    System.out.println("Logging out from Owner...");
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }
}