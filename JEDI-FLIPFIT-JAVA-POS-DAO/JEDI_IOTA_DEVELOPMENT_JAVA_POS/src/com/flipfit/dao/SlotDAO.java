package com.flipfit.dao;

import com.flipfit.bean.Slot;
import java.util.List;

/**
 * Interface for Slot DAO
 * 
 * @author team IOTA
 */
public interface SlotDAO {
    boolean addSlot(Slot slot);

    Slot getSlotById(int slotId);

    List<Slot> getSlotsByCenter(int centerId);

    boolean updateSlot(Slot slot);

    boolean deleteSlot(int slotId);
}
