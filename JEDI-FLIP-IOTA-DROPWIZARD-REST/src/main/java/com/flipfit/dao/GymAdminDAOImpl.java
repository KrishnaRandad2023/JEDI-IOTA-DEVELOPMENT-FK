package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Registration;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL-based implementation of the {@link GymAdminDAO} interface.
 * Uses JDBC and {@link SQLConstants} to perform administrative CRUD operations.
 * 
 * @author team IOTA
 */
public class GymAdminDAOImpl implements GymAdminDAO {

    /**
     * Default constructor for GymAdminDAOImpl.
     */
    public GymAdminDAOImpl() {
    }

    @Override
    public List<GymOwner> viewGymOwners() {
        List<GymOwner> owners = new ArrayList<>();
        String query = SQLConstants.GET_ALL_GYM_OWNERS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GymOwner owner = new GymOwner();
                owner.setUserId(rs.getInt("userId"));
                owner.setName(rs.getString("name"));
                owner.setEmail(rs.getString("email"));
                owner.setGstNumber(rs.getString("gstNumber"));
                owner.setCin(rs.getString("cin"));
                owner.setPanNumber(rs.getString("panNumber"));
                owner.setAadhaarNumber(rs.getString("aadhaarNumber"));
                owners.add(owner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return owners;
    }

    @Override
    public List<GymCenter> viewGymCenters() {
        List<GymCenter> gyms = new ArrayList<>();
        String query = SQLConstants.GET_ALL_GYM_CENTERS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GymCenter gym = new GymCenter();
                gym.setCenterId(rs.getInt("centerId"));
                gym.setOwnerId(rs.getInt("ownerId"));
                gym.setCenterName(rs.getString("centerName"));
                gym.setAddress(rs.getString("address"));
                gym.setCity(rs.getString("city"));
                gym.setCapacity(rs.getInt("capacity"));
                gym.setApproved(rs.getBoolean("isApproved"));
                gyms.add(gym);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gyms;
    }

    @Override
    public boolean approveGymOwner(int ownerId) {
        /**
         * Approval happens at registration.
         */

        String regQuery = SQLConstants.APPROVE_REGISTRATION;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(regQuery)) {
            stmt.setInt(1, ownerId); // Identifying ID might vary
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean approveGymCenter(int centerId) {
        String query = SQLConstants.APPROVE_GYM_CENTER;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, centerId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<GymCenter> viewPendingGymCenters() {
        List<GymCenter> gyms = new ArrayList<>();
        String query = SQLConstants.GET_PENDING_GYM_CENTERS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GymCenter gym = new GymCenter();
                gym.setCenterId(rs.getInt("centerId"));
                gym.setOwnerId(rs.getInt("ownerId"));
                gym.setCenterName(rs.getString("centerName"));
                gym.setAddress(rs.getString("address"));
                gym.setCity(rs.getString("city"));
                gym.setCapacity(rs.getInt("capacity"));
                gym.setApproved(rs.getBoolean("isApproved"));
                gyms.add(gym);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gyms;
    }

    @Override
    public List<Registration> viewPendingRegistrations() {
        List<Registration> registrations = new ArrayList<>();
        String query = SQLConstants.GET_ALL_PENDING_REGISTRATIONS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Registration reg = new Registration();
                reg.setRegistrationId(rs.getInt("registrationId"));
                reg.setName(rs.getString("name"));
                reg.setEmail(rs.getString("email"));
                reg.setMobileNumber(rs.getString("mobileNumber"));
                reg.setRoleType(rs.getString("roleType"));
                reg.setRegistrationDate(rs.getTimestamp("registrationDate"));
                reg.setApproved(rs.getBoolean("isApproved"));
                reg.setCity(rs.getString("city"));
                reg.setPanNumber(rs.getString("panNumber"));
                reg.setGstNumber(rs.getString("gstNumber"));
                reg.setCin(rs.getString("cin"));
                reg.setAadhaarNumber(rs.getString("aadhaarNumber"));
                registrations.add(reg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }
}
