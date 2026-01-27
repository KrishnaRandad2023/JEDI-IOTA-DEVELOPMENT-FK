package com.flipfit.dao;

import com.flipfit.bean.User;
import java.util.List;

public interface GymUserDAO {
    boolean authenticateUser(User user);

    boolean registerUser(User user);

    User getUserByEmail(String email);

    User getUserById(int userId);

    boolean updateUserProfile(User user);

    boolean deleteUser(int userId);

    boolean changePassword(int userId, String newPassword);

    List<User> getAllUsers();

    List<User> getUsersByRole(int roleId);
}
