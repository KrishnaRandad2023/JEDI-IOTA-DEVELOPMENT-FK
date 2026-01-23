package com.flipfit.business;

import java.util.List;

import com.flipfit.bean.User;

public class GymUserService {
    public boolean registerUser(User user) {
        // TODO: Add user to database
        return false;
    }
    
    public User login(String email, String password) {
        // TODO: Validate credentials and return user
        return null;
    }
    
    public void logout(int userId) {
        // TODO: Handle logout logic
    }
    
    public User getUserById(int userId) {
        // TODO: Fetch user from database
        return null;
    }
    
    public User getUserByEmail(String email) {
        // TODO: Fetch user by email
        return null;
    }
    
    public boolean updateUserProfile(User user) {
        // TODO: Update user in database
        return false;
    }
    
    public boolean deleteUser(int userId) {
        // TODO: Delete user from database
        return false;
    }
    
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        // TODO: Verify old password and update to new one
        return false;
    }
    
   
    public boolean resetPassword(String email) {
        // TODO: Generate reset token and send email
        return false;
    }
    
    public List<User> getAllUsers() {
        // TODO: Fetch all users from database
        return null;
    }
    
    public List<User> getUsersByRole(int roleId) {
        // TODO: Fetch users by role
        return null;
    }
    
    public boolean activateUser(int userId) {
        // TODO: Set user status to active
        return false;
    }
    
    public boolean deactivateUser(int userId) {
        // TODO: Set user status to inactive
        return false;
    }
}
