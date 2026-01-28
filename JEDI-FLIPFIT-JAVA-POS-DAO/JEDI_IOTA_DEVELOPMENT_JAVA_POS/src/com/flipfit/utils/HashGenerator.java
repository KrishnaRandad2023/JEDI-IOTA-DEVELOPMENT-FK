package com.flipfit.utils;

public class HashGenerator {
    public static void main(String[] args) {
        String password = "admin123";
        String hash = PasswordUtils.hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("SHA-256 Hash: " + hash);

        System.out.println("\nSQL to update admin:");
        System.out.println("UPDATE User SET password = '" + hash + "' WHERE email = 'admin@flipfit.com';");
    }
}
