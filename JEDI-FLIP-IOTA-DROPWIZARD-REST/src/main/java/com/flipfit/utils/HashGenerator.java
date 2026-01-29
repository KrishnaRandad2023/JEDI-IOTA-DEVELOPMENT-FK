package com.flipfit.utils;

/**
 * Utility tool to generate SHA-256 hashes for passwords and generate SQL update
 * statements.
 * Primarily used for administrative setup and debugging.
 * 
 * @author team IOTA
 */
public class HashGenerator {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private HashGenerator() {
    }

    /**
     * Main entry point to generate a hash for a hardcoded password.
     * Prints the plain password, hash, and a sample SQL UPDATE statement.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        String password = "admin123";
        String hash = PasswordUtils.hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("SHA-256 Hash: " + hash);

        System.out.println("\nSQL to update admin:");
        System.out.println("UPDATE User SET password = '" + hash + "' WHERE email = 'admin@flipfit.com';");
    }
}
