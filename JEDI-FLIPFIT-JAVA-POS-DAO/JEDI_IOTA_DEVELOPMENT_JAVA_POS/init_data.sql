-- Initialize Role Table with required roles
-- Run this script ONCE after creating the database schema

USE flipfit_schema;

-- Insert the three required roles
INSERT INTO Role (roleId, roleName, roleDescription) VALUES 
(1, 'ADMIN', 'System Administrator'),
(2, 'GYM_OWNER', 'Gym owner who manages centers'),
(3, 'CUSTOMER', 'Customer who books slots')
ON DUPLICATE KEY UPDATE roleName = VALUES(roleName);

-- Verify roles were inserted
SELECT * FROM Role;
