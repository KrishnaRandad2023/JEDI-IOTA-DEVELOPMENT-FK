-- ========================================
-- VERIFY DATA IN FLIPFIT DATABASE
-- ========================================
-- Copy and paste these queries into MySQL Workbench
-- to verify your gym customer registration

USE flipfit_schema;

-- 1. Check if Role table has data (REQUIRED for User registration)
SELECT * FROM Role;
-- Expected: 3 rows (ADMIN, GYM_OWNER, CUSTOMER)

-- 2. Check all registered users
SELECT 
    u.userId,
    u.name,
    u.email,
    u.mobileNumber,
    r.roleName
FROM User u
LEFT JOIN Role r ON u.roleId = r.roleId
ORDER BY u.userId DESC;
-- Your newly registered customer should appear here!

-- 3. Check GymCustomer table (if applicable)
SELECT * FROM GymCustomer;

-- 4. Check all tables to see what has data
SELECT 'Role' as TableName, COUNT(*) as RowCount FROM Role
UNION ALL
SELECT 'User', COUNT(*) FROM User
UNION ALL
SELECT 'GymOwner', COUNT(*) FROM GymOwner
UNION ALL
SELECT 'GymCustomer', COUNT(*) FROM GymCustomer
UNION ALL
SELECT 'GymCenter', COUNT(*) FROM GymCenter
UNION ALL
SELECT 'Slot', COUNT(*) FROM Slot
UNION ALL
SELECT 'Booking', COUNT(*) FROM Booking;
