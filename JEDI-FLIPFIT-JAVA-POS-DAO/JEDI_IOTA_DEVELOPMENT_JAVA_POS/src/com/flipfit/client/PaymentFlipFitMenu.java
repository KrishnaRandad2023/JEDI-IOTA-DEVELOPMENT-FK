package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.business.PaymentService;

public class PaymentFlipFitMenu {

    private PaymentService paymentService;

    public PaymentFlipFitMenu() {
        this.paymentService = ServiceFactory.getInstance().getPaymentService();
    }

    /**
     * Display the payment menu and process the payment.
     * 
     * @param scanner Scanner for input
     * @param amount  The amount to be paid
     * @return true if payment is successful, false otherwise
     */
    public boolean showPaymentMenu(Scanner scanner, double amount) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║          PAYMENT GATEWAY           ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Amount Due: %-22.2f ║%n", amount);
        System.out.println("║                                    ║");
        System.out.println("║ 1. Pay via UPI                     ║");
        System.out.println("║ 2. Pay via Card                    ║");
        System.out.println("║ 3. Cancel Payment                  ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("Select Payment Method: ");

        int choice = -1;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            System.out.println("❌ Invalid input!");
            return false;
        }

        String paymentMethod = "Unknown";

        switch (choice) {
            case 1:
                paymentMethod = "UPI";
                System.out.print("Enter UPI ID: ");
                scanner.nextLine(); // Simulate reading UPI ID
                break;
            case 2:
                paymentMethod = "Card";
                System.out.print("Enter Card Number: ");
                scanner.nextLine(); // Simulate reading Card Number
                break;
            case 3:
                System.out.println("Payment Cancelled.");
                return false;
            default:
                System.out.println("❌ Invalid choice!");
                return false;
        }

        System.out.println("\nProcessing Payment...");
        // 0 is a placeholder for bookingId, which might be linked later often in a real
        // system
        return paymentService.processPayment(0, amount, paymentMethod);
    }
}
