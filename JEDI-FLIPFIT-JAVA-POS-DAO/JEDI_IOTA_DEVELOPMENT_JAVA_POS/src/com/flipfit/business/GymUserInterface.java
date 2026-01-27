package com.flipfit.business;

import java.util.List;

import com.flipfit.bean.User;

public interface GymUserInterface {
boolean registerUser(User user);
    
    // User Authentication
    User login(String email, String password);
    void logout(int userId);
    
    // User Profile Management
    User getUserById(int userId);
    User getUserByEmail(String email);
    boolean updateUserProfile(User user);
    boolean deleteUser(int userId);
    
    // Password Management
    boolean changePassword(int userId, String oldPassword, String newPassword);
    boolean resetPassword(String email);
    
    // User Listing (Admin functionality)
    List<User> getAllUsers();
    List<User> getUsersByRole(int roleId);
    
    // Account Status
    boolean activateUser(int userId);
    boolean deactivateUser(int userId);
}
