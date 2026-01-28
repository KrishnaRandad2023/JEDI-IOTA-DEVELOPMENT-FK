package com.flipfit.constants;

/**
 * Centralized class for all SQL queries used in the application.
 * 
 * @author team IOTA
 */
public class SQLConstants {

    // GymUser Queries
    public static final String AUTHENTICATE_USER = "SELECT * FROM User WHERE email = ? AND password = ?";
    public static final String REGISTER_USER = "INSERT INTO User (name, email, password, mobileNumber, roleId) VALUES (?, ?, ?, ?, ?)";
    public static final String GET_USER_BY_EMAIL = "SELECT u.*, r.roleName, o.gstNumber, o.cin, o.panNumber, o.aadhaarNumber AS ownerAadhaar, c.aadhaarNumber AS customerAadhaar FROM User u "
            +
            "LEFT JOIN Role r ON u.roleId = r.roleId " +
            "LEFT JOIN GymOwner o ON u.userId = o.userId " +
            "LEFT JOIN GymCustomer c ON u.userId = c.userId " +
            "WHERE u.email = ?";
    public static final String GET_USER_BY_ID = "SELECT u.*, r.roleName, o.gstNumber, o.cin, o.panNumber, o.aadhaarNumber AS ownerAadhaar, c.aadhaarNumber AS customerAadhaar FROM User u "
            +
            "LEFT JOIN Role r ON u.roleId = r.roleId " +
            "LEFT JOIN GymOwner o ON u.userId = o.userId " +
            "LEFT JOIN GymCustomer c ON u.userId = c.userId " +
            "WHERE u.userId = ?";
    public static final String UPDATE_USER_PROFILE = "UPDATE User SET name = ?, email = ?, mobileNumber = ? WHERE userId = ?";
    public static final String DELETE_USER = "DELETE FROM User WHERE userId = ?";
    public static final String CHANGE_PASSWORD = "UPDATE User SET password = ? WHERE userId = ?";
    public static final String GET_ALL_USERS = "SELECT u.*, r.roleName FROM User u " +
            "LEFT JOIN Role r ON u.roleId = r.roleId";
    public static final String GET_USERS_BY_ROLE = "SELECT u.*, r.roleName FROM User u " +
            "LEFT JOIN Role r ON u.roleId = r.roleId " +
            "WHERE u.roleId = ?";
    public static final String REGISTER_REGISTRATION = "INSERT INTO Registration (name, email, password, mobileNumber, roleType, city, panNumber, gstNumber, cin, aadhaarNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_GYM_OWNER_DETAILS = "INSERT INTO GymOwner (userId, gstNumber, cin, panNumber, aadhaarNumber) VALUES (?, ?, ?, ?, ?)";
    public static final String ADD_GYM_CUSTOMER_DETAILS = "INSERT INTO GymCustomer (userId, aadhaarNumber) VALUES (?, ?)";

    // GymOwner Queries
    public static final String ADD_GYM_CENTER = "INSERT INTO GymCenter (ownerId, centerName, address, city, capacity, isApproved) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String ADD_SLOT = "INSERT INTO Slot (centerId, startTime, endTime, totalSeats, availableSeats) VALUES (?, ?, ?, ?, ?)";
    public static final String GET_MY_GYM_CENTERS = "SELECT * FROM GymCenter WHERE ownerId = ?";
    public static final String UPDATE_GYM_CENTER = "UPDATE GymCenter SET centerName = ?, address = ?, city = ?, capacity = ? WHERE centerId = ?";
    public static final String DELETE_GYM_CENTER = "DELETE FROM GymCenter WHERE centerId = ?";
    public static final String GET_GYM_CENTER_BY_ID = "SELECT * FROM GymCenter WHERE centerId = ?";

    // GymAdmin Queries
    public static final String GET_ALL_GYM_OWNERS = "SELECT * FROM GymOwner JOIN User ON GymOwner.userId = User.userId";
    public static final String GET_PENDING_REGISTRATIONS = "SELECT * FROM Registration WHERE isApproved = false";
    public static final String GET_ALL_GYM_CENTERS = "SELECT * FROM GymCenter";
    public static final String APPROVE_GYM_OWNER = "UPDATE Registration SET isApproved = true WHERE registrationId = ? AND roleType = 'GYM_OWNER'";
    public static final String APPROVE_REGISTRATION = "UPDATE Registration SET isApproved = true WHERE registrationId = ?";
    public static final String APPROVE_GYM_CENTER = "UPDATE GymCenter SET isApproved = true WHERE centerId = ?";
    public static final String GET_PENDING_GYM_CENTERS = "SELECT * FROM GymCenter WHERE isApproved = false";

    // GymCustomer Queries
    public static final String GET_APPROVED_GYMS = "SELECT * FROM GymCenter WHERE isApproved = true";
    public static final String BOOK_SLOT = "INSERT INTO Booking (userId, slotId, bookingDate, status) VALUES (?, ?, ?, 'CONFIRMED')";
    public static final String GET_MY_BOOKINGS = "SELECT * FROM Booking WHERE userId = ?";
    public static final String CANCEL_BOOKING = "UPDATE Booking SET status = 'CANCELLED' WHERE bookingId = ?";
}
