package com.flipfit.dao;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class GymOwnerDAOImpl implements GymOwnerDAO {

    @Override
    public boolean addGymCenter(GymCenter gymCenter) {
        String query = SQLConstants.ADD_GYM_CENTER;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gymCenter.getOwnerId());
            stmt.setString(2, gymCenter.getCenterName());
            stmt.setString(3, gymCenter.getAddress());
            stmt.setString(4, gymCenter.getCity());
            stmt.setInt(5, gymCenter.getCapacity());
            stmt.setBoolean(6, gymCenter.isApproved());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addSlot(Slot slot) {
        String query = SQLConstants.ADD_SLOT;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, slot.getCenterId());
            stmt.setTime(2, Time.valueOf(slot.getStartTime()));
            stmt.setTime(3, Time.valueOf(slot.getEndTime()));
            stmt.setInt(4, slot.getTotalSeats());
            stmt.setInt(5, slot.getAvailableSeats());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<GymCenter> viewMyGymCenters(int ownerId) {
        List<GymCenter> gyms = new ArrayList<>();
        String query = SQLConstants.GET_MY_GYM_CENTERS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();
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
    public boolean updateGymCenter(GymCenter gymCenter) {
        String query = SQLConstants.UPDATE_GYM_CENTER;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, gymCenter.getCenterName());
            stmt.setString(2, gymCenter.getAddress());
            stmt.setString(3, gymCenter.getCity());
            stmt.setInt(4, gymCenter.getCapacity());
            stmt.setInt(5, gymCenter.getCenterId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteGymCenter(int centerId) {
        String query = SQLConstants.DELETE_GYM_CENTER;
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
    public GymCenter getGymCenterById(int centerId) {
        String query = SQLConstants.GET_GYM_CENTER_BY_ID;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, centerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                GymCenter gym = new GymCenter();
                gym.setCenterId(rs.getInt("centerId"));
                gym.setOwnerId(rs.getInt("ownerId"));
                gym.setCenterName(rs.getString("centerName"));
                gym.setAddress(rs.getString("address"));
                gym.setCity(rs.getString("city"));
                gym.setCapacity(rs.getInt("capacity"));
                gym.setApproved(rs.getBoolean("isApproved"));
                return gym;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
