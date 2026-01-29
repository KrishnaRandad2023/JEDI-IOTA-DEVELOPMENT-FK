package com.flipfit.constants;

/**
 * Centralized class for all SQL queries used in the application.
 * 
 * @author team IOTA
 */
public class SQLConstants {

        /**
         * Private constructor to prevent instantiation of utility class.
         */
        private SQLConstants() {
        }

        // GymUser Queries
        /** Query to authenticate a user. */
        public static final String AUTHENTICATE_USER = "SELECT * FROM User WHERE email = ? AND password = ?";
        /** Query to register a new user. */
        public static final String REGISTER_USER = "INSERT INTO User (name, email, password, mobileNumber, roleId) VALUES (?, ?, ?, ?, ?)";
        /** Query to get user by email with details. */
        public static final String GET_USER_BY_EMAIL = "SELECT u.*, r.roleName, o.gstNumber, o.cin, o.panNumber, o.aadhaarNumber AS ownerAadhaar, c.aadhaarNumber AS customerAadhaar FROM User u "
                        +
                        "LEFT JOIN Role r ON u.roleId = r.roleId " +
                        "LEFT JOIN GymOwner o ON u.userId = o.userId " +
                        "LEFT JOIN GymCustomer c ON u.userId = c.userId " +
                        "WHERE u.email = ?";
        /** Query to get user by ID with details. */
        public static final String GET_USER_BY_ID = "SELECT u.*, r.roleName, o.gstNumber, o.cin, o.panNumber, o.aadhaarNumber AS ownerAadhaar, c.aadhaarNumber AS customerAadhaar FROM User u "
                        +
                        "LEFT JOIN Role r ON u.roleId = r.roleId " +
                        "LEFT JOIN GymOwner o ON u.userId = o.userId " +
                        "LEFT JOIN GymCustomer c ON u.userId = c.userId " +
                        "WHERE u.userId = ?";
        /** Query to update user profile. */
        public static final String UPDATE_USER_PROFILE = "UPDATE User SET name = ?, email = ?, mobileNumber = ? WHERE userId = ?";
        /** Query to delete a user. */
        public static final String DELETE_USER = "DELETE FROM User WHERE userId = ?";
        /** Query to change user password. */
        public static final String CHANGE_PASSWORD = "UPDATE User SET password = ? WHERE userId = ?";
        /** Query to get all users. */
        public static final String GET_ALL_USERS = "SELECT u.*, r.roleName FROM User u " +
                        "LEFT JOIN Role r ON u.roleId = r.roleId";
        /** Query to get users by role. */
        public static final String GET_USERS_BY_ROLE = "SELECT u.*, r.roleName FROM User u " +
                        "LEFT JOIN Role r ON u.roleId = r.roleId " +
                        "WHERE u.roleId = ?";
        /** Query to register a registration request. */
        public static final String REGISTER_REGISTRATION = "INSERT INTO Registration (name, email, password, mobileNumber, roleType, city, panNumber, gstNumber, cin, aadhaarNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        /** Query to add gym owner details. */
        public static final String ADD_GYM_OWNER_DETAILS = "INSERT INTO GymOwner (userId, gstNumber, cin, panNumber, aadhaarNumber) VALUES (?, ?, ?, ?, ?)";
        /** Query to add gym customer details. */
        public static final String ADD_GYM_CUSTOMER_DETAILS = "INSERT INTO GymCustomer (userId, aadhaarNumber) VALUES (?, ?)";

        // GymOwner Queries
        /** Query to add a gym center. */
        public static final String ADD_GYM_CENTER = "INSERT INTO GymCenter (ownerId, centerName, address, city, capacity, isApproved) VALUES (?, ?, ?, ?, ?, ?)";
        /** Query to add a slot. */
        public static final String ADD_SLOT = "INSERT INTO Slot (centerId, startTime, endTime, totalSeats, availableSeats) VALUES (?, ?, ?, ?, ?)";
        /** Query to get gym centers by owner. */
        public static final String GET_MY_GYM_CENTERS = "SELECT * FROM GymCenter WHERE ownerId = ?";
        /** Query to update gym center. */
        public static final String UPDATE_GYM_CENTER = "UPDATE GymCenter SET centerName = ?, address = ?, city = ?, capacity = ? WHERE centerId = ?";
        /** Query to delete a gym center. */
        public static final String DELETE_GYM_CENTER = "DELETE FROM GymCenter WHERE centerId = ?";
        /** Query to get gym center by ID. */
        public static final String GET_GYM_CENTER_BY_ID = "SELECT * FROM GymCenter WHERE centerId = ?";
        /** Query to get slots by center. */
        public static final String GET_SLOTS_BY_CENTER = "SELECT * FROM Slot WHERE centerId = ?";
        /** Query to get slot by ID. */
        public static final String GET_SLOT_BY_ID = "SELECT * FROM Slot WHERE slotId = ?";
        /** Query to update available seats in a slot. */
        public static final String UPDATE_SLOT_SEATS = "UPDATE Slot SET availableSeats = ? WHERE slotId = ?";

        // GymAdmin Queries
        /** Query to get all gym owners. */
        public static final String GET_ALL_GYM_OWNERS = "SELECT * FROM GymOwner JOIN User ON GymOwner.userId = User.userId";
        /** Query to get pending registrations. */
        public static final String GET_PENDING_REGISTRATIONS = "SELECT * FROM Registration WHERE isApproved = false";
        /** Query to get all gym centers. */
        public static final String GET_ALL_GYM_CENTERS = "SELECT * FROM GymCenter";
        /** Query to approve a gym owner registration. */
        public static final String APPROVE_GYM_OWNER = "UPDATE Registration SET isApproved = true WHERE registrationId = ? AND roleType = 'GYM_OWNER'";
        /** Query to approve any registration. */
        public static final String APPROVE_REGISTRATION = "UPDATE Registration SET isApproved = true WHERE registrationId = ?";
        /** Query to approve a gym center. */
        public static final String APPROVE_GYM_CENTER = "UPDATE GymCenter SET isApproved = true WHERE centerId = ?";
        /** Query to get pending gym centers. */
        public static final String GET_PENDING_GYM_CENTERS = "SELECT * FROM GymCenter WHERE isApproved = false";
        /** Query to get all pending registrations. */
        public static final String GET_ALL_PENDING_REGISTRATIONS = "SELECT * FROM Registration WHERE isApproved = false";

        // GymCustomer Queries
        /** Query to get all approved gym centers. */
        public static final String GET_APPROVED_GYMS = "SELECT * FROM GymCenter WHERE isApproved = true";
        /** Query to book a slot. */
        public static final String BOOK_SLOT = "INSERT INTO Booking (userId, slotId, bookingDate, status) VALUES (?, ?, ?, 'CONFIRMED')";
        /** Query to get customer's bookings. */
        public static final String GET_MY_BOOKINGS = "SELECT * FROM Booking WHERE userId = ?";
        /** Query to cancel a booking. */
        public static final String CANCEL_BOOKING = "UPDATE Booking SET status = 'CANCELLED' WHERE bookingId = ?";
        /** Query to get bookings by slot. */
        public static final String GET_BOOKINGS_BY_SLOT = "SELECT * FROM Booking WHERE slotId = ?";
        /** Query to get booking by ID. */
        public static final String GET_BOOKING_BY_ID = "SELECT * FROM Booking WHERE bookingId = ?";
        /** Query to update booking status. */
        public static final String UPDATE_BOOKING_STATUS = "UPDATE Booking SET status = ? WHERE bookingId = ?";
        /** Query to delete a booking. */
        public static final String DELETE_BOOKING = "DELETE FROM Booking WHERE bookingId = ?";
        /** Query to count confirmed bookings for a slot. */
        public static final String GET_BOOKING_COUNT_FOR_SLOT = "SELECT COUNT(*) FROM Booking WHERE slotId = ? AND status = 'CONFIRMED'";

        // Waitlist Queries
        /** Query to add user to waitlist. */
        public static final String ADD_TO_WAITLIST = "INSERT INTO Waitlist (userId, slotId, requestDate, priorityPosition) VALUES (?, ?, ?, ?)";
        /** Query to remove user from waitlist. */
        public static final String REMOVE_FROM_WAITLIST = "DELETE FROM Waitlist WHERE waitlistId = ?";
        /** Query to get waitlist by slot. */
        public static final String GET_WAITLIST_BY_SLOT = "SELECT * FROM Waitlist WHERE slotId = ? ORDER BY priorityPosition ASC";
        /** Query to get waitlist by user. */
        public static final String GET_WAITLIST_BY_USER = "SELECT * FROM Waitlist WHERE userId = ?";
        /** Query to get waitlist entry by ID. */
        public static final String GET_WAITLIST_BY_ID = "SELECT * FROM Waitlist WHERE waitlistId = ?";
        /** Query to update waitlist priority position. */
        public static final String UPDATE_WAITLIST_POSITION = "UPDATE Waitlist SET priorityPosition = ? WHERE waitlistId = ?";

        // Payment Queries
        /** Query to add a payment record. */
        public static final String ADD_PAYMENT = "INSERT INTO Payment (bookingId, amount, transactionId) VALUES (?, ?, ?)";
        /** Query to get payment by ID. */
        public static final String GET_PAYMENT_BY_ID = "SELECT * FROM Payment WHERE paymentId = ?";
}
