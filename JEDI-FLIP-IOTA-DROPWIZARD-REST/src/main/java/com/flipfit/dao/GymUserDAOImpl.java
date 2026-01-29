package com.flipfit.dao;

import com.flipfit.bean.Role;
import com.flipfit.bean.User;
import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymCustomer;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.flipfit.bean.Registration;

/**
 * MySQL-based implementation of the {@link GymUserDAO} interface.
 * Uses JDBC and {@link SQLConstants} to perform CRUD operations on user data.
 * 
 * @author team IOTA
 */
public class GymUserDAOImpl implements GymUserDAO {

    /**
     * Default constructor for GymUserDAOImpl.
     */
    public GymUserDAOImpl() {
    }

    @Override
    public boolean authenticateUser(User user) {
        String query = SQLConstants.AUTHENTICATE_USER;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean registerUser(User user) {
        String query = SQLConstants.REGISTER_USER;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getMobileNumber());
            stmt.setInt(5, user.getRole().getRoleId());
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    user.setUserId(userId);

                    if (user instanceof GymOwner) {
                        GymOwner owner = (GymOwner) user;
                        try (PreparedStatement ownerStmt = conn.prepareStatement(SQLConstants.ADD_GYM_OWNER_DETAILS)) {
                            ownerStmt.setInt(1, userId);
                            ownerStmt.setString(2, owner.getGstNumber());
                            ownerStmt.setString(3, owner.getCin());
                            ownerStmt.setString(4, owner.getPanNumber());
                            ownerStmt.setString(5, owner.getAadhaarNumber());
                            ownerStmt.executeUpdate();
                        }
                    } else if (user instanceof GymCustomer) {
                        GymCustomer customer = (GymCustomer) user;
                        try (PreparedStatement customerStmt = conn
                                .prepareStatement(SQLConstants.ADD_GYM_CUSTOMER_DETAILS)) {
                            customerStmt.setInt(1, userId);
                            customerStmt.setString(2, customer.getAadhaarNumber());
                            customerStmt.executeUpdate();
                        }
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addRegistration(Registration reg) {
        String query = SQLConstants.REGISTER_REGISTRATION;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, reg.getName());
            stmt.setString(2, reg.getEmail());
            stmt.setString(3, reg.getPassword());
            stmt.setString(4, reg.getMobileNumber());
            stmt.setString(5, reg.getRoleType());
            stmt.setString(6, reg.getCity());
            stmt.setString(7, reg.getPanNumber());
            stmt.setString(8, reg.getGstNumber());
            stmt.setString(9, reg.getCin());
            stmt.setString(10, reg.getAadhaarNumber());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        String query = SQLConstants.GET_USER_BY_EMAIL;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int roleId = rs.getInt("roleId");
                String roleName = rs.getString("roleName");

                // Create the appropriate user subclass based on role
                User user = createUserByRole(roleId, roleName);

                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setMobileNumber(rs.getString("mobileNumber"));

                Role role = new Role();
                role.setRoleId(roleId);
                role.setRoleName(roleName);
                user.setRole(role);

                // Fetch extra details for specific roles
                if (user instanceof GymOwner) {
                    GymOwner owner = (GymOwner) user;
                    owner.setGstNumber(rs.getString("gstNumber"));
                    owner.setCin(rs.getString("cin"));
                    owner.setPanNumber(rs.getString("panNumber"));
                    owner.setAadhaarNumber(rs.getString("ownerAadhaar"));
                } else if (user instanceof GymCustomer) {
                    GymCustomer customer = (GymCustomer) user;
                    customer.setAadhaarNumber(rs.getString("customerAadhaar"));
                }

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        String query = SQLConstants.GET_USER_BY_ID;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int roleId = rs.getInt("roleId");
                String roleName = rs.getString("roleName");

                // Create the appropriate user subclass based on role
                User user = createUserByRole(roleId, roleName);

                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setMobileNumber(rs.getString("mobileNumber"));

                Role role = new Role();
                role.setRoleId(roleId);
                role.setRoleName(roleName);
                user.setRole(role);

                // Fetch extra details for specific roles
                if (user instanceof GymOwner) {
                    GymOwner owner = (GymOwner) user;
                    owner.setGstNumber(rs.getString("gstNumber"));
                    owner.setCin(rs.getString("cin"));
                    owner.setPanNumber(rs.getString("panNumber"));
                    owner.setAadhaarNumber(rs.getString("ownerAadhaar"));
                } else if (user instanceof GymCustomer) {
                    GymCustomer customer = (GymCustomer) user;
                    customer.setAadhaarNumber(rs.getString("customerAadhaar"));
                }

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Helper method to create correct User subclass based on roleId and roleName
     */
    private User createUserByRole(int roleId, String roleName) {
        if (roleId == 1 || (roleName != null && roleName.equalsIgnoreCase("ADMIN"))) {
            return new GymAdmin();
        } else if (roleId == 2 || (roleName != null && roleName.equalsIgnoreCase("GYM_OWNER"))) {
            return new GymOwner();
        } else if (roleId == 3 || (roleName != null && roleName.equalsIgnoreCase("CUSTOMER"))) {
            return new GymCustomer();
        } else {
            return new User();
        }
    }

    @Override
    public boolean updateUserProfile(User user) {
        String query = SQLConstants.UPDATE_USER_PROFILE;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getMobileNumber());
            stmt.setInt(4, user.getUserId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        String query = SQLConstants.DELETE_USER;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean changePassword(int userId, String newPassword) {
        String query = SQLConstants.CHANGE_PASSWORD;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = SQLConstants.GET_ALL_USERS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int roleId = rs.getInt("roleId");
                String roleName = rs.getString("roleName");
                User user = createUserByRole(roleId, roleName);

                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setMobileNumber(rs.getString("mobileNumber"));

                Role role = new Role();
                role.setRoleId(roleId);
                role.setRoleName(roleName);
                user.setRole(role);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getUsersByRole(int roleId) {
        List<User> users = new ArrayList<>();
        String query = SQLConstants.GET_USERS_BY_ROLE;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String roleName = rs.getString("roleName");
                User user = createUserByRole(roleId, roleName);

                user.setUserId(rs.getInt("userId"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setMobileNumber(rs.getString("mobileNumber"));

                Role role = new Role();
                role.setRoleId(roleId);
                role.setRoleName(roleName);
                user.setRole(role);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
