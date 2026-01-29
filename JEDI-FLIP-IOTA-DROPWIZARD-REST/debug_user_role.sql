-- Check user data for debugging role-based login
USE flipfit_schema;

-- 1. Check if sree user exists and what role they have
SELECT 
    u.userId,
    u.name,
    u.email,
    u.roleId,
    r.roleName,
    r.roleDescription
FROM User u
LEFT JOIN Role r ON u.roleId = r.roleId
WHERE u.email = 'sree@flipfit.com';

-- 2. If user doesn't exist, show all users
SELECT 
    u.userId,
    u.name,
    u.email,
    u.roleId,
    r.roleName
FROM User u
LEFT JOIN Role r ON u.roleId = r.roleId
ORDER BY u.userId;

-- 3. Check if Role table has all roles
SELECT * FROM Role;
