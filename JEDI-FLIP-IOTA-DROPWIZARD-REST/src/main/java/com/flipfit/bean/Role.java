package com.flipfit.bean;

/**
 * Represents a system role (e.g., Admin, Customer, Gym Owner).
 * Defines the permissions and type of user within the FlipFit platform.
 * 
 * @author team IOTA
 */
public class Role {

    /** Unique ID of the role. */
    private int roleId;

    /** Name of the role (e.g., "ADMIN", "CUSTOMER"). */
    private String roleName;

    /** Brief description of what this role entails. */
    private String roleDescription;

    /**
     * Default constructor for creating an empty Role object.
     */
    public Role() {
    }

    /**
     * Parameterized constructor for creating a Role with specific details.
     *
     * @param roleId          unique ID of the role
     * @param roleName        display name of the role
     * @param roleDescription details about role permissions
     */
    public Role(int roleId, String roleName, String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    /**
     * Gets the unique ID of the role.
     * 
     * @return the role ID
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Sets the unique ID of the role.
     * 
     * @param roleId the role ID
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets the name of the role.
     * 
     * @return the role name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the name of the role.
     * 
     * @param roleName the role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets the description of the role.
     * 
     * @return the role description
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    /**
     * Sets the description of the role.
     * 
     * @param roleDescription the role description
     */
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                '}';
    }
}