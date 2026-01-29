package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.business.PaymentService;

/**
 * The Class PaymentFlipFitMenu.
 * 
 * @author team IOTA
 */
public class PaymentFlipFitMenu {

    private PaymentService paymentService;

    /**
     * Default constructor for PaymentFlipFitMenu.
     * Initializes the payment service from the ServiceFactory.
     */
    public PaymentFlipFitMenu() {
        this.paymentService = ServiceFactory.getInstance().getPaymentService();
    }

    /**
     * Display the payment menu and process the payment.
     * 
     * @param scanner   Scanner for input
     * @param amount    The amount to be paid
     * @param bookingId the specific booking ID for which payment is being made
     * @return true if payment is successful, false otherwise
     */
    public boolean showPaymentMenu(Scanner scanner, double amount, int bookingId) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║          PAYMENT GATEWAY           ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Amount Due: %-22.2f ║%n", amount);
        System.out.println("╚════════════════════════════════════╝");

        /**
         * [DEVELOPMENT MODE]
         * The following section automatically approves the payment to streamline the
         * testing process.
         * It bypasses the manual selection and simulated processing.
         */
        System.out.println("\n⚡ [DEVELOPMENT MODE] Auto-approving payment...");
        System.out.println("🔗 Processing UPI transaction for booking ID: " + bookingId);

        try {
            // Simulated delay for UI feedback
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Proceed directly to processing payment with a default development method
        return paymentService.processPayment(bookingId, amount, "UPI (Auto-Approved)");

        /**
         * ORIGINAL MANUAL LOGIC (Commented out for future reference or switch back)
         * 
         * System.out.println("║ 1. Pay via UPI ║");
         * System.out.println("║ 2. Pay via Card ║");
         * System.out.println("║ 3. Cancel Payment ║");
         * System.out.println("╚════════════════════════════════════╝");
         * System.out.print("Select Payment Method: ");
         *
         * int choice = -1;
         * try {
         * choice = scanner.nextInt();
         * scanner.nextLine(); // Consume newline
         * } catch (Exception e) {
         * scanner.nextLine(); // Clear invalid input
         * System.out.println("❌ Invalid input!");
         * return false;
         * }
         *
         * String paymentMethod = "Unknown";
         *
         * switch (choice) {
         * case 1:
         * paymentMethod = "UPI";
         * System.out.print("Enter UPI ID: ");
         * scanner.nextLine();
         * break;
         * case 2:
         * paymentMethod = "Card";
         * System.out.print("Enter Card Number: ");
         * scanner.nextLine();
         * break;
         * case 3:
         * System.out.println("Payment Cancelled.");
         * return false;
         * default:
         * System.out.println("❌ Invalid choice!");
         * return false;
         * }
         *
         * System.out.println("\nProcessing Payment...");
         * return paymentService.processPayment(bookingId, amount, paymentMethod);
         */
    }
}
