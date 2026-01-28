package com.flipfit.dao;

import com.flipfit.bean.User;
import java.util.List;

/**
 * Interface for Gym User DAO
 * 
 * @author team IOTA
 */
public interface GymUserDAO {
    boolean authenticateUser(User user);

    boolean registerUser(User user);

    boolean addRegistration(com.flipfit.bean.Registration reg);

    User getUserByEmail(String email);

    User getUserById(int userId);

    boolean updateUserProfile(User user);

    boolean deleteUser(int userId);

    boolean changePassword(int userId, String newPassword);

    List<User> getAllUsers();

    List<User> getUsersByRole(int roleId);
}
