package com.flipfit.dao;

import com.flipfit.bean.User;
import java.util.List;

/**
 * Data Access Object interface for managing user-related persistent data.
 * Handles authentication, registration, and profile management for all user
 * roles.
 * 
 * @author team IOTA
 */
public interface GymUserDAO {

    /**
     * Authenticates a user based on their email and password.
     * 
     * @param user the User object containing credentials
     * @return true if authentication is successful, false otherwise
     */
    boolean authenticateUser(User user);

    /**
     * Registers a new user in the system.
     * 
     * @param user the User object to be registered
     * @return true if registration is successful, false otherwise
     */
    boolean registerUser(User user);

    /**
     * Adds a registration request for approval.
     * 
     * @param reg the Registration object containing pending details
     * @return true if the request was successfully added
     */
    boolean addRegistration(com.flipfit.bean.Registration reg);

    /**
     * Retrieves a user by their unique email address.
     * 
     * @param email the user's email
     * @return the User object, or null if not found
     */
    User getUserByEmail(String email);

    /**
     * Retrieves a user by their unique persistent ID.
     * 
     * @param userId the user's ID
     * @return the User object, or null if not found
     */
    User getUserById(int userId);

    /**
     * Updates the basic profile information of an existing user.
     * 
     * @param user the User object with updated details
     * @return true if the update was successful
     */
    boolean updateUserProfile(User user);

    /**
     * Deletes a user from the system based on their ID.
     * 
     * @param userId the ID of the user to delete
     * @return true if deletion was successful
     */
    boolean deleteUser(int userId);

    /**
     * Updates a user's password.
     * 
     * @param userId      the user's ID
     * @param newPassword the new hashed password
     * @return true if the password was successfully updated
     */
    boolean changePassword(int userId, String newPassword);

    /**
     * Retrieves all users registered in the system.
     * 
     * @return a list of all User objects
     */
    List<User> getAllUsers();

    /**
     * Retrieves a list of users filtered by their system role.
     * 
     * @param roleId the ID of the role to filter by
     * @return a list of User objects matching the role
     */
    List<User> getUsersByRole(int roleId);
}
