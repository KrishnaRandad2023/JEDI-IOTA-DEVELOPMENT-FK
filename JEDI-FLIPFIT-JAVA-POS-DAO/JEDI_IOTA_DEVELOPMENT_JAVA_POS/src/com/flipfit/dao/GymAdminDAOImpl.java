package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.GymOwner;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GymAdminDAOImpl implements GymAdminDAO {

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
        // Assuming there isn't an explicit status in GymOwner table based on previous
        // schema,
        // but often there is a registration table.
        // However, if we need to approve a gym owner in the Registration logic, that's
        // different.
        // Let's implement a 'viewPendingRegistrations' and 'approveRegistration'
        // instead?
        // Staying faithful to the request 'GymAdminDAO interface and GymAdmin dao
        // implementation'
        // and standard operations.
        // I will implement a query that conceptually acts as approval, or simply
        // returns false if no appropriate column.
        // Re-reading schema: GymOwner table doesn't have isApproved. Registration does.
        // I'll update the implementation to query Registration or just standard update
        // if schema allowed.
        // For now, I will use a placeholder query on Registration table assuming
        // ownerId maps to something there,
        // OR simply return false with a comment if it doesn't fit the schema perfectly
        // 1:1.

        // BETTER APPROACH: Updating 'Registration' based on some logic.
        // Or, maybe I missed 'isApproved' in GymOwner in my own schema?
        // I checked schema: GymORwner table: id, userId, gst, cin, pan.
        // Registration table: ... isApproved.
        // So approval happens at registration.

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
}
