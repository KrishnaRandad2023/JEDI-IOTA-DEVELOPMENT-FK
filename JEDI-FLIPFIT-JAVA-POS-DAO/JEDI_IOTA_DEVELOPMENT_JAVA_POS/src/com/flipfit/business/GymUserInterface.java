package com.flipfit.business;

import java.util.List;

import com.flipfit.bean.User;

/**
 * Interface for Gym User Operations
 * 
 * @author team IOTA
 */
public interface GymUserInterface {
    /**
     * Registers a new user
     * 
     * @param user the user details
     * @return true if registration is successful
     */
    boolean registerUser(User user);

    /**
     * Authenticates a user
     * 
     * @param email    user email
     * @param password user password
     * @return the user details if authentication is successful, null otherwise
     */
    User login(String email, String password);

    /**
     * Logs out a user
     * 
     * @param userId user ID
     */
    void logout(int userId);

    /**
     * Gets user by ID
     * 
     * @param userId user ID
     * @return the user details
     */
    User getUserById(int userId);

    /**
     * Gets user by email
     * 
     * @param email user email
     * @return the user details
     */
    User getUserByEmail(String email);

    /**
     * Updates user profile
     * 
     * @param user updated user details
     * @return true if update is successful
     */
    boolean updateUserProfile(User user);

    /**
     * Deletes a user
     * 
     * @param userId user ID
     * @return true if deletion is successful
     */
    boolean deleteUser(int userId);

    /**
     * Changes user password
     * 
     * @param userId      user ID
     * @param oldPassword current password
     * @param newPassword new password
     * @return true if password change is successful
     */
    boolean changePassword(int userId, String oldPassword, String newPassword);

    /**
     * Resets user password
     * 
     * @param email user email
     * @return true if reset is successful
     */
    boolean resetPassword(String email);

    /**
     * Gets all registered users
     * 
     * @return list of all users
     */
    List<User> getAllUsers();

    /**
     * Gets users by their role
     * 
     * @param roleId role ID
     * @return list of users with the specified role
     */
    List<User> getUsersByRole(int roleId);

    /**
     * Activates a user account
     * 
     * @param userId user ID
     * @return true if activation is successful
     */
    boolean activateUser(int userId);

    /**
     * Deactivates a user account
     * 
     * @param userId user ID
     * @return true if deactivation is successful
     */
    boolean deactivateUser(int userId);
}
