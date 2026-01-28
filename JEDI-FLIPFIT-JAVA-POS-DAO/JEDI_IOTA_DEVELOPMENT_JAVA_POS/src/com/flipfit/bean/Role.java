package com.flipfit.bean;

/**
 * The Class Role.
 * 
 * @author team IOTA
 * @ClassName "Role"
 */
public class Role {
    /** The role ID. */
    private int roleId;

    /** The role name. */
    private String roleName;

    /** The role description. */
    private String roleDescription;

    /**
     * Instantiates a new role.
     */
    public Role() {
    }

    /**
     * Instantiates a new role.
     *
     * @param roleId          the role ID
     * @param roleName        the role name
     * @param roleDescription the role description
     */
    public Role(int roleId, String roleName, String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    /**
     * Gets the role ID.
     * 
     * @return the role ID
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Sets the role ID.
     * 
     * @param roleId the new role ID
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets the role name.
     * 
     * @return the role name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the role name.
     * 
     * @param roleName the new role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets the role description.
     * 
     * @return the role description
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    /**
     * Sets the role description.
     * 
     * @param roleDescription the new role description
     */
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    /**
     * To string.
     * 
     * @return the string
     */
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                '}';
    }
}