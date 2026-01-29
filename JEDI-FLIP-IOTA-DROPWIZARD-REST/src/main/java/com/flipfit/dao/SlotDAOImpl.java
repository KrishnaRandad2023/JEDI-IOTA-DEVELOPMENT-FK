package com.flipfit.dao;

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

/**
 * MySQL-based implementation of the {@link SlotDAO} interface.
 * Uses JDBC to perform CRUD operations on gym slot data.
 * 
 * @author team IOTA
 */
public class SlotDAOImpl implements SlotDAO {

    /**
     * Default constructor for SlotDAOImpl.
     */
    public SlotDAOImpl() {
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
    public Slot getSlotById(int slotId) {
        String query = SQLConstants.GET_SLOT_BY_ID;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, slotId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Slot slot = new Slot();
                slot.setSlotId(rs.getInt("slotId"));
                slot.setCenterId(rs.getInt("centerId"));
                slot.setStartTime(rs.getTime("startTime").toLocalTime());
                slot.setEndTime(rs.getTime("endTime").toLocalTime());
                slot.setTotalSeats(rs.getInt("totalSeats"));
                slot.setAvailableSeats(rs.getInt("availableSeats"));
                return slot;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Slot> getSlotsByCenter(int centerId) {
        List<Slot> slots = new ArrayList<>();
        String query = SQLConstants.GET_SLOTS_BY_CENTER;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, centerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Slot slot = new Slot();
                slot.setSlotId(rs.getInt("slotId"));
                slot.setCenterId(rs.getInt("centerId"));
                slot.setStartTime(rs.getTime("startTime").toLocalTime());
                slot.setEndTime(rs.getTime("endTime").toLocalTime());
                slot.setTotalSeats(rs.getInt("totalSeats"));
                slot.setAvailableSeats(rs.getInt("availableSeats"));
                slots.add(slot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

    @Override
    public boolean updateSlot(Slot slot) {
        String query = SQLConstants.UPDATE_SLOT_SEATS;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, slot.getAvailableSeats());
            stmt.setInt(2, slot.getSlotId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSlot(int slotId) {
        // Implementation might not be needed if not used, but good to have
        String query = "DELETE FROM Slot WHERE slotId = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, slotId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
