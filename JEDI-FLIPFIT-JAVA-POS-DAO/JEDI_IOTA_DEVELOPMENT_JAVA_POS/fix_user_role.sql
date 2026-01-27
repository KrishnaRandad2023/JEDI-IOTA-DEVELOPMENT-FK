-- ================================================
-- FIX USER ROLES AND VERIFY DATA
-- ================================================
-- Run this script in MySQL Workbench to fix the 
-- missing role mapping for your user.

USE flipfit_schema;

-- 1. Ensure the Role table has the standard IDs
INSERT IGNORE INTO Role (roleId, roleName, roleDescription) VALUES
(1, 'ADMIN', 'System Administrator'),
(2, 'GYM_OWNER', 'Gym Owner - can add centers and slots'),
(3, 'CUSTOMER', 'Gym Customer - can book slots');

-- 2. FIX: Manually assign the CUSTOMER role (Id=3) to your user
-- Change 'sree@flipfit.com' if you used a different email
UPDATE User 
SET roleId = 3 
WHERE email = 'sree@flipfit.com';

-- 3. VERIFY: Check if the user now has a valid role linking
SELECT 
    u.userId,
    u.name,
    u.email,
    u.roleId as DB_RoleId,
    r.roleName as Linked_RoleName
FROM User u
LEFT JOIN Role r ON u.roleId = r.roleId
WHERE u.email = 'sree@flipfit.com';

-- 4. Check all users to see if any others have NULL roleId
SELECT userId, name, email, roleId FROM User WHERE roleId IS NULL;
