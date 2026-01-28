package com.flipfit.dao;

import com.flipfit.bean.Waitlist;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Waitlist DAO
 * 
 * @author team IOTA
 */
public class WaitlistDAOImpl implements WaitlistDAO {

    @Override
    public boolean addToWaitlist(Waitlist waitlist) {
        String query = SQLConstants.ADD_TO_WAITLIST;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, waitlist.getUserId());
            stmt.setInt(2, waitlist.getSlotId());
            stmt.setDate(3, new java.sql.Date(waitlist.getRequestDate().getTime()));
            stmt.setInt(4, waitlist.getPriorityPosition());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeFromWaitlist(int waitlistId) {
        String query = SQLConstants.REMOVE_FROM_WAITLIST;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, waitlistId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Waitlist> getWaitlistBySlot(int slotId) {
        List<Waitlist> waitlistEntries = new ArrayList<>();
        String query = SQLConstants.GET_WAITLIST_BY_SLOT;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, slotId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Waitlist entry = new Waitlist();
                entry.setWaitlistId(rs.getInt("waitlistId"));
                entry.setUserId(rs.getInt("userId"));
                entry.setSlotId(rs.getInt("slotId"));
                entry.setRequestDate(rs.getDate("requestDate"));
                entry.setPriorityPosition(rs.getInt("priorityPosition"));
                waitlistEntries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waitlistEntries;
    }

    @Override
    public List<Waitlist> getWaitlistByUser(int userId) {
        List<Waitlist> waitlistEntries = new ArrayList<>();
        String query = SQLConstants.GET_WAITLIST_BY_USER;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Waitlist entry = new Waitlist();
                entry.setWaitlistId(rs.getInt("waitlistId"));
                entry.setUserId(rs.getInt("userId"));
                entry.setSlotId(rs.getInt("slotId"));
                entry.setRequestDate(rs.getDate("requestDate"));
                entry.setPriorityPosition(rs.getInt("priorityPosition"));
                waitlistEntries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waitlistEntries;
    }

    @Override
    public Waitlist getWaitlistById(int waitlistId) {
        String query = SQLConstants.GET_WAITLIST_BY_ID;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, waitlistId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Waitlist entry = new Waitlist();
                entry.setWaitlistId(rs.getInt("waitlistId"));
                entry.setUserId(rs.getInt("userId"));
                entry.setSlotId(rs.getInt("slotId"));
                entry.setRequestDate(rs.getDate("requestDate"));
                entry.setPriorityPosition(rs.getInt("priorityPosition"));
                return entry;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateWaitlistPosition(int waitlistId, int newPosition) {
        String query = SQLConstants.UPDATE_WAITLIST_POSITION;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newPosition);
            stmt.setInt(2, waitlistId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
