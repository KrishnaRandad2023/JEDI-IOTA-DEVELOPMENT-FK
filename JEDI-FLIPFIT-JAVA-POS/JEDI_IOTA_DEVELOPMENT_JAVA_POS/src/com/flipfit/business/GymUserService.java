package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.*;

public class GymUserService {
    // Collections
    private Map<Integer, User> users;           // userId -> User
    private Map<String, User> usersByEmail;     // email -> User
    private Map<Integer, Boolean> userStatus;   // userId -> active status
    private int userIdCounter;
    
    // Constructor
    public GymUserService() {
        this.users = new HashMap<>();
        this.usersByEmail = new HashMap<>();
        this.userStatus = new HashMap<>();
        this.userIdCounter = 1;
        
        // Initialize with hard-coded users
        initializeHardcodedUsers();
        
        System.out.println("✅ GymUserService initialized with " + users.size() + " users");
    }
    
    // Initialize hard-coded users
    private void initializeHardcodedUsers() {
        // Admin user
        GymAdmin admin = new GymAdmin();
        admin.setUserId(userIdCounter++);
        admin.setName("Admin User");
        admin.setEmail("admin@flipfit.com");
        admin.setPassword("admin123");
        admin.setMobileNumber("9999999999");
        Role adminRole = new Role(1, "ADMIN", "System Administrator");
        admin.setRole(adminRole);
        
        users.put(admin.getUserId(), admin);
        usersByEmail.put(admin.getEmail(), admin);
        userStatus.put(admin.getUserId(), true);
        
        // Gym Owner 1
        GymOwner owner1 = new GymOwner();
        owner1.setUserId(userIdCounter++);
        owner1.setName("Rajesh Kumar");
        owner1.setEmail("rajesh@flipfit.com");
        owner1.setPassword("rajesh123");
        owner1.setMobileNumber("9876543210");
        Role ownerRole = new Role(2, "GYM_OWNER", "Gym owner who manages centers");
        owner1.setRole(ownerRole);
        
        users.put(owner1.getUserId(), owner1);
        usersByEmail.put(owner1.getEmail(), owner1);
        userStatus.put(owner1.getUserId(), true);
        
        // Gym Owner 2
        GymOwner owner2 = new GymOwner();
        owner2.setUserId(userIdCounter++);
        owner2.setName("Priya Sharma");
        owner2.setEmail("priya@flipfit.com");
        owner2.setPassword("priya123");
        owner2.setMobileNumber("9876543211");
        owner2.setRole(ownerRole);
        
        users.put(owner2.getUserId(), owner2);
        usersByEmail.put(owner2.getEmail(), owner2);
        userStatus.put(owner2.getUserId(), true);
        
        // Customer 1
        GymCustomer customer1 = new GymCustomer();
        customer1.setUserId(userIdCounter++);
        customer1.setName("Amit Verma");
        customer1.setEmail("amit@gmail.com");
        customer1.setPassword("amit123");
        customer1.setMobileNumber("9123456780");
        Role customerRole = new Role(3, "CUSTOMER", "Customer who books slots");
        customer1.setRole(customerRole);
        
        users.put(customer1.getUserId(), customer1);
        usersByEmail.put(customer1.getEmail(), customer1);
        userStatus.put(customer1.getUserId(), true);
        
        // Customer 2
        GymCustomer customer2 = new GymCustomer();
        customer2.setUserId(userIdCounter++);
        customer2.setName("Sneha Reddy");
        customer2.setEmail("sneha@gmail.com");
        customer2.setPassword("sneha123");
        customer2.setMobileNumber("9123456781");
        customer2.setRole(customerRole);
        
        users.put(customer2.getUserId(), customer2);
        usersByEmail.put(customer2.getEmail(), customer2);
        userStatus.put(customer2.getUserId(), true);
        
        // Customer 3
        GymCustomer customer3 = new GymCustomer();
        customer3.setUserId(userIdCounter++);
        customer3.setName("Vikram Singh");
        customer3.setEmail("vikram@gmail.com");
        customer3.setPassword("vikram123");
        customer3.setMobileNumber("9123456782");
        customer3.setRole(customerRole);
        
        users.put(customer3.getUserId(), customer3);
        usersByEmail.put(customer3.getEmail(), customer3);
        userStatus.put(customer3.getUserId(), true);
    }
    
    public boolean registerUser(User user) {
        // Check if email already exists
        if (usersByEmail.containsKey(user.getEmail())) {
            System.out.println("❌ Email already registered!");
            return false;
        }
        
        // Assign user ID
        user.setUserId(userIdCounter++);
        
        // Add to collections
        users.put(user.getUserId(), user);
        usersByEmail.put(user.getEmail(), user);
        userStatus.put(user.getUserId(), true);
        
        System.out.println("✅ User registered successfully!");
        System.out.println("   User ID: " + user.getUserId());
        return true;
    }
    
    public User login(String email, String password) {
        User user = usersByEmail.get(email);
        
        if (user == null) {
            return null;
        }
        
        // Check if user is active
        if (!userStatus.getOrDefault(user.getUserId(), false)) {
            System.out.println("❌ Account is deactivated!");
            return null;
        }
        
        // Verify password
        if (user.getPassword().equals(password)) {
            return user;
        }
        
        return null;
    }
    
    public void logout(int userId) {
        // In-memory system, no logout logic needed
        System.out.println("✅ Logged out successfully!");
    }
    
    public User getUserById(int userId) {
        return users.get(userId);
    }
    
    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }
    
    public boolean updateUserProfile(User user) {
        if (!users.containsKey(user.getUserId())) {
            System.out.println("❌ User not found!");
            return false;
        }
        
        // Update in both maps
        User oldUser = users.get(user.getUserId());
        String oldEmail = oldUser.getEmail();
        
        users.put(user.getUserId(), user);
        
        // If email changed, update email map
        if (!oldEmail.equals(user.getEmail())) {
            usersByEmail.remove(oldEmail);
            usersByEmail.put(user.getEmail(), user);
        }
        
        System.out.println("✅ Profile updated successfully!");
        return true;
    }
    
    public boolean deleteUser(int userId) {
        User user = users.remove(userId);
        
        if (user != null) {
            usersByEmail.remove(user.getEmail());
            userStatus.remove(userId);
            System.out.println("✅ User deleted successfully!");
            return true;
        }
        
        System.out.println("❌ User not found!");
        return false;
    }
    
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        User user = users.get(userId);
        
        if (user == null) {
            System.out.println("❌ User not found!");
            return false;
        }
        
        if (!user.getPassword().equals(oldPassword)) {
            System.out.println("❌ Old password is incorrect!");
            return false;
        }
        
        user.setPassword(newPassword);
        System.out.println("✅ Password changed successfully!");
        return true;
    }
    
    public boolean resetPassword(String email) {
        User user = usersByEmail.get(email);
        
        if (user == null) {
            System.out.println("❌ Email not found!");
            return false;
        }
        
        // Generate random password (simplified)
        String newPassword = "reset" + System.currentTimeMillis() % 10000;
        user.setPassword(newPassword);
        
        System.out.println("✅ Password reset!");
        System.out.println("   New password: " + newPassword);
        return true;
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public List<User> getUsersByRole(int roleId) {
        List<User> roleUsers = new ArrayList<>();
        
        for (User user : users.values()) {
            if (user.getRole() != null && user.getRole().getRoleId() == roleId) {
                roleUsers.add(user);
            }
        }
        
        return roleUsers;
    }
    
    public boolean activateUser(int userId) {
        if (!users.containsKey(userId)) {
            System.out.println("❌ User not found!");
            return false;
        }
        
        userStatus.put(userId, true);
        System.out.println("✅ User activated!");
        return true;
    }
    
    public boolean deactivateUser(int userId) {
        if (!users.containsKey(userId)) {
            System.out.println("❌ User not found!");
            return false;
        }
        
        userStatus.put(userId, false);
        System.out.println("✅ User deactivated!");
        return true;
    }
}