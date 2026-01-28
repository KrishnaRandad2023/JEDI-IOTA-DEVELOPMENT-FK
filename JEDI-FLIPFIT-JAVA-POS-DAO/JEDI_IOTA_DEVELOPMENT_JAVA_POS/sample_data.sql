-- ================================================
-- SAMPLE DATA FOR FLIPFIT GYM APPLICATION
-- ================================================
-- This script populates the database with sample users, 
-- gym centers, and slots for testing purposes.
-- Password for ALL sample users is: admin123

USE flipfit_schema;

-- 1. RESET TABLES (Optional: Uncomment to clear existing data)
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE Waitlist;
-- TRUNCATE TABLE Registration;
-- TRUNCATE TABLE Notification;
-- TRUNCATE TABLE Payment;
-- TRUNCATE TABLE Booking;
-- TRUNCATE TABLE GymCustomer;
-- TRUNCATE TABLE Slot;
-- TRUNCATE TABLE GymCenter;
-- TRUNCATE TABLE GymOwner;
-- TRUNCATE TABLE User;
-- TRUNCATE TABLE Role;
-- SET FOREIGN_KEY_CHECKS = 1;

-- 2. INITIALIZE ROLES
INSERT IGNORE INTO Role (roleId, roleName, roleDescription) VALUES
(1, 'ADMIN', 'System Administrator'),
(2, 'GYM_OWNER', 'Gym Owner - can add centers and slots'),
(3, 'CUSTOMER', 'Gym Customer - can book slots');

-- 3. INITIALIZE USERS (Password for all is 'admin123')
-- SHA-256 Hash for 'admin123': 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
INSERT IGNORE INTO User (userId, name, email, password, mobileNumber, roleId) VALUES
(1, 'System Admin', 'admin@flipfit.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '9999999999', 1),
(2, 'Gym Owner One', 'owner1@flipfit.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '8888888888', 2),
(3, 'Gym Owner Two', 'owner2@flipfit.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '7777777777', 2),
(4, 'Customer One', 'customer1@flipfit.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '6666666666', 3),
(5, 'Customer Two', 'customer2@flipfit.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '5555555555', 3);

-- 4. INITIALIZE GYM OWNERS
INSERT IGNORE INTO GymOwner (userId, gstNumber, cin, panNumber, aadhaarNumber) VALUES
(2, 'GST123456789', 'CIN987654321', 'PAN1234567', '123456789012'),
(3, 'GST987654321', 'CIN123456789', 'PAN7654321', '098765432109');

-- 5. INITIALIZE GYM CUSTOMERS
INSERT IGNORE INTO GymCustomer (userId, aadhaarNumber) VALUES
(4, '111122223333'),
(5, '444455556666');

-- 6. INITIALIZE GYM CENTERS
INSERT IGNORE INTO GymCenter (centerId, ownerId, centerName, address, city, capacity, isApproved) VALUES
(1, 2, 'Power Gym Bangalore', 'HSR Layout, Sector 7', 'Bangalore', 50, TRUE),
(2, 2, 'Elite Fitness Whitefield', 'Whitefield Main Road', 'Bangalore', 40, TRUE),
(3, 3, 'Fit Life Mumbai', 'Andheri West', 'Mumbai', 30, FALSE);

-- 7. INITIALIZE SLOTS
-- Slots for Power Gym (Center 1)
INSERT IGNORE INTO Slot (centerId, startTime, endTime, totalSeats, availableSeats) VALUES
(1, '06:00:00', '07:00:00', 10, 10),
(1, '07:00:00', '08:00:00', 10, 10),
(1, '18:00:00', '19:00:00', 10, 10),
(1, '19:00:00', '20:00:00', 10, 10);

-- Slots for Elite Fitness (Center 2)
INSERT IGNORE INTO Slot (centerId, startTime, endTime, totalSeats, availableSeats) VALUES
(2, '07:00:00', '08:00:00', 10, 10),
(2, '08:00:00', '09:00:00', 10, 10),
(2, '17:00:00', '18:00:00', 10, 10);

-- 8. INITIALIZE PENDING REGISTRATIONS (For Admin to Approve)
INSERT IGNORE INTO Registration (name, email, password, mobileNumber, roleType, city, isApproved) VALUES
('New Gym Owner', 'new_owner@flipfit.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '1231231234', 'GYM_OWNER', 'Chennai', FALSE),
('New Customer', 'new_customer@flipfit.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '4321432143', 'CUSTOMER', 'Delhi', FALSE);

-- VERIFY DATA
SELECT 'Users' as TableName, COUNT(*) as Count FROM User
UNION SELECT 'Centers', COUNT(*) FROM GymCenter
UNION SELECT 'Slots', COUNT(*) FROM Slot
UNION SELECT 'Pending Reg', COUNT(*) FROM Registration WHERE isApproved = FALSE;
