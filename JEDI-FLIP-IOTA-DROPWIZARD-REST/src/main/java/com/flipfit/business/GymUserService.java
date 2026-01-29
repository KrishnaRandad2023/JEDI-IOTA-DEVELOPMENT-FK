package com.flipfit.business;

import java.util.*;
import com.flipfit.bean.*;
import com.flipfit.dao.GymUserDAO;
import com.flipfit.dao.GymUserDAOImpl;
import com.flipfit.exception.RegistrationNotDoneException;
import com.flipfit.exception.UserNotFoundException;
import com.flipfit.exception.InvalidEmailException;
import com.flipfit.exception.InvalidMobileException;
import com.flipfit.exception.InvalidAadhaarException;
import com.flipfit.utils.PasswordUtils;
import java.util.regex.Pattern;

/**
 * The Class GymUserService.
 *
 * @author team IOTA
 * 
 */
public class GymUserService {

    /** The gym user DAO. */
    private GymUserDAO gymUserDAO = new GymUserDAOImpl();

    /**
     * Instantiates a new gym user service.
     */
    public GymUserService() {
        System.out.println("✅ GymUserService initialized with Database DAO");
    }

    /**
     * Registers a user who has already been validated and whose password is already
     * hashed.
     *
     * @param user the user
     * @return true, if successful
     * @throws RegistrationNotDoneException the registration not done exception
     */
    public boolean registerApprovedUser(User user) throws RegistrationNotDoneException {
        // Check if email already exists
        if (gymUserDAO.getUserByEmail(user.getEmail()) != null) {
            System.out.println("❌ Email already registered!");
            throw new RegistrationNotDoneException("Email already registered!");
        }

        GymUserDAOImpl daoImpl = (GymUserDAOImpl) gymUserDAO;
        String result = daoImpl.registerUserDetailed(user);
        if ("SUCCESS".equals(result)) {
            System.out.println("✅ User registered successfully!");
            return true;
        } else {
            System.out.println("❌ Registration failed: " + result);
            throw new RegistrationNotDoneException("Registration failed in Database: " + result);
        }
    }

    /**
     * Register user.
     *
     * @param user the user
     * @return true, if successful
     * @throws RegistrationNotDoneException the registration not done exception
     * @throws InvalidEmailException        the invalid email exception
     * @throws InvalidMobileException       the invalid mobile exception
     * @throws InvalidAadhaarException      the invalid aadhaar exception
     */
    public boolean registerUser(User user) throws RegistrationNotDoneException, InvalidEmailException,
            InvalidMobileException, InvalidAadhaarException {

        // Validate basic inputs
        validateEmail(user.getEmail());
        validateMobile(user.getMobileNumber());

        if (user instanceof GymOwner) {
            validateAadhaar(((GymOwner) user).getAadhaarNumber());
        } else if (user instanceof GymCustomer) {
            validateAadhaar(((GymCustomer) user).getAadhaarNumber());
        }

        // Hash the password before registration
        user.setPassword(PasswordUtils.hashPassword(user.getPassword()));

        // Check if email already exists
        if (gymUserDAO.getUserByEmail(user.getEmail()) != null) {
            System.out.println("❌ Email already registered!");
            throw new RegistrationNotDoneException("Email already registered!");
        }

        boolean success = gymUserDAO.registerUser(user);
        if (success) {
            System.out.println("✅ User registered successfully!");
        } else {
            System.out.println("❌ Registration failed!");
            throw new RegistrationNotDoneException("Registration failed in Database!");
        }
        return success;
    }

    /**
     * Add registration request.
     *
     * @param reg the registration
     * @return true, if successful
     * @throws InvalidEmailException   the invalid email exception
     * @throws InvalidMobileException  the invalid mobile exception
     * @throws InvalidAadhaarException the invalid aadhaar exception
     */
    public boolean addRegistration(Registration reg)
            throws InvalidEmailException, InvalidMobileException, InvalidAadhaarException {
        validateEmail(reg.getEmail());
        validateMobile(reg.getMobileNumber());
        validateAadhaar(reg.getAadhaarNumber());

        // Hash the password before storage in Registration table
        reg.setPassword(PasswordUtils.hashPassword(reg.getPassword()));

        return gymUserDAO.addRegistration(reg);
    }

    private void validateEmail(String email) throws InvalidEmailException {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (email == null || !Pattern.compile(emailRegex).matcher(email).matches()) {
            throw new InvalidEmailException("Invalid email format: " + email);
        }
    }

    private void validateMobile(String mobile) throws InvalidMobileException {
        if (mobile == null || !mobile.matches("\\d{10}")) {
            throw new InvalidMobileException("Invalid mobile number: " + mobile + ". Must be 10 digits.");
        }
    }

    private void validateAadhaar(String aadhaar) throws InvalidAadhaarException {
        if (aadhaar == null || !aadhaar.matches("\\d{12}")) {
            throw new InvalidAadhaarException("Invalid Aadhaar number: " + aadhaar + ". Must be 12 digits.");
        }
    }

    /**
     * Login.
     *
     * @param email    the email
     * @param password the password
     * @return the user
     * @throws UserNotFoundException the user not found exception
     */
    public User login(String email, String password) throws UserNotFoundException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(PasswordUtils.hashPassword(password));

        if (gymUserDAO.authenticateUser(user)) {
            return gymUserDAO.getUserByEmail(email);
        }
        throw new UserNotFoundException("Invalid email or password!");
    }

    /**
     * Logout.
     *
     * @param userId the user ID
     */
    public void logout(int userId) {
        System.out.println("✅ Logged out successfully!");
    }

    /**
     * Gets the user by ID.
     *
     * @param userId the user ID
     * @return the user by ID
     * @throws UserNotFoundException the user not found exception
     */
    public User getUserById(int userId) throws UserNotFoundException {
        User user = gymUserDAO.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found!");
        }
        return user;
    }

    /**
     * Gets the user by email.
     *
     * @param email the email
     * @return the user by email
     * @throws UserNotFoundException the user not found exception
     */
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = gymUserDAO.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found!");
        }
        return user;
    }

    /**
     * Update user profile.
     *
     * @param user the user
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean updateUserProfile(User user) throws UserNotFoundException {
        if (gymUserDAO.getUserById(user.getUserId()) == null) {
            System.out.println("❌ User not found!");
            throw new UserNotFoundException("User with ID " + user.getUserId() + " not found!");
        }

        boolean success = gymUserDAO.updateUserProfile(user);
        if (success) {
            System.out.println("✅ Profile updated successfully!");
        }
        return success;
    }

    /**
     * Delete user.
     *
     * @param userId the user ID
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean deleteUser(int userId) throws UserNotFoundException {
        if (gymUserDAO.getUserById(userId) == null) {
            System.out.println("❌ User not found!");
            throw new UserNotFoundException("User with ID " + userId + " not found!");
        }
        boolean success = gymUserDAO.deleteUser(userId);
        if (success) {
            System.out.println("✅ User deleted successfully!");
        }
        return success;
    }

    /**
     * Change password.
     *
     * @param userId      the user ID
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) throws UserNotFoundException {
        User user = gymUserDAO.getUserById(userId);

        if (user == null) {
            System.out.println("❌ User not found!");
            throw new UserNotFoundException("User with ID " + userId + " not found!");
        }

        if (!user.getPassword().equals(PasswordUtils.hashPassword(oldPassword))) {
            System.out.println("❌ Old password is incorrect!");
            return false;
        }

        boolean success = gymUserDAO.changePassword(userId, PasswordUtils.hashPassword(newPassword));
        if (success) {
            System.out.println("✅ Password changed successfully!");
        }
        return success;
    }

    /**
     * Reset password.
     *
     * @param email the email
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    public boolean resetPassword(String email) throws UserNotFoundException {
        User user = gymUserDAO.getUserByEmail(email);

        if (user == null) {
            System.out.println("❌ Email not found!");
            throw new UserNotFoundException("User with email " + email + " not found!");
        }

        String newPassword = "reset" + System.currentTimeMillis() % 10000;
        boolean success = gymUserDAO.changePassword(user.getUserId(), PasswordUtils.hashPassword(newPassword));

        if (success) {
            System.out.println("✅ Password reset!");
            System.out.println("   New password: " + newPassword);
            return true;
        }
        return false;
    }

    /**
     * Gets the all users.
     *
     * @return the all users
     */
    public List<User> getAllUsers() {
        return gymUserDAO.getAllUsers();
    }

    /**
     * Gets the users by role.
     *
     * @param roleId the role ID
     * @return the users by role
     */
    public List<User> getUsersByRole(int roleId) {
        return gymUserDAO.getUsersByRole(roleId);
    }

    /**
     * Activate user.
     *
     * @param userId the user ID
     * @return true, if successful
     */
    public boolean activateUser(int userId) {
        System.out.println("⚠️ Activate user not fully supported in DB schema yet.");
        return true;
    }

    /**
     * Deactivate user.
     *
     * @param userId the user ID
     * @return true, if successful
     */
    public boolean deactivateUser(int userId) {
        System.out.println("⚠️ Deactivate user not fully supported in DB schema yet.");
        return true;
    }
}