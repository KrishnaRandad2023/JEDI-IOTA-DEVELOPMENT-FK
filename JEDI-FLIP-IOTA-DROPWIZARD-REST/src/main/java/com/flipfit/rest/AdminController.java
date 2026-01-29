package com.flipfit.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.client.ServiceFactory;
import com.flipfit.exception.*;

/**
 * Controller for administrative operations.
 * Handles tasks such as approving owners, centers, and managing users.
 * 
 * @author team IOTA
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {

    /**
     * Default constructor for AdminController.
     */
    public AdminController() {
    }

    private final AdminService adminService = ServiceFactory.getInstance().getAdminService();

    /**
     * Retrieves all pending owner registration requests.
     * 
     * @return Response containing a list of pending registrations
     */
    @GET
    @Path("/pending-owners")
    public Response getPendingOwners() {
        List<Registration> registrations = adminService.getPendingOwnerRegistrations();
        return Response.ok(registrations).build();
    }

    /**
     * Retrieves all pending gym center approval requests.
     * 
     * @return Response containing a list of pending gym centers
     */
    @GET
    @Path("/pending-centers")
    public Response getPendingCenters() {
        List<GymCenter> centers = adminService.getPendingCenterApprovals();
        return Response.ok(centers).build();
    }

    /**
     * Approves a pending owner registration.
     * 
     * @param regId the registration request ID
     * @return Response indicating success or failure of approval
     */
    @PUT
    @Path("/approve-owner/{regId}")
    public Response approveOwner(@PathParam("regId") int regId) {
        try {
            adminService.approveOwnerRegistration(regId);
            return Response.ok("Owner approved successfully").build();
        } catch (RegistrationNotDoneException | IssueWithApprovalException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Rejects a pending owner registration request.
     * 
     * @param regId the registration request ID
     * @return Response indicating success or failure of rejection
     */
    @PUT
    @Path("/reject-owner/{regId}")
    public Response rejectOwner(@PathParam("regId") int regId) {
        try {
            adminService.rejectRegistration(regId);
            return Response.ok("Owner registration rejected").build();
        } catch (RegistrationNotDoneException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Approves a new gym center registration.
     * 
     * @param centerId the unique ID of the gym center
     * @return Response indicating success or failure of approval
     */
    @PUT
    @Path("/approve-center/{centerId}")
    public Response approveCenter(@PathParam("centerId") int centerId) {
        try {
            adminService.approveGymCenter(centerId);
            return Response.ok("Center approved successfully").build();
        } catch (IssueWithApprovalException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Rejects a new gym center registration.
     * 
     * @param centerId the unique ID of the gym center
     * @return Response indicating success or failure of rejection
     */
    @PUT
    @Path("/reject-center/{centerId}")
    public Response rejectCenter(@PathParam("centerId") int centerId) {
        if (adminService.rejectGymCenter(centerId)) {
            return Response.ok("Center rejected").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to reject center").build();
    }

    /**
     * Retrieves all bookings across the system.
     * 
     * @return Response containing a list of all bookings
     */
    @GET
    @Path("/bookings")
    public Response getAllBookings() {
        List<Booking> bookings = adminService.getAllBookings();
        return Response.ok(bookings).build();
    }

    /**
     * Retrieves all users in the system.
     * 
     * @return Response containing a list of all users
     */
    @GET
    @Path("/users")
    public Response getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return Response.ok(users).build();
    }

    /**
     * Retrieves system-wide usage statistics.
     * 
     * @return Response containing a map of various statistics
     */
    @GET
    @Path("/statistics")
    public Response getStatistics() {
        Map<String, Integer> stats = adminService.getSystemStatistics();
        return Response.ok(stats).build();
    }

    /**
     * Activates a deactivated user.
     * 
     * @param userId the unique ID of the user
     * @return Response indicating success or failure of activation
     */
    @PUT
    @Path("/users/{userId}/activate")
    public Response activateUser(@PathParam("userId") int userId) {
        try {
            adminService.activateUser(userId);
            return Response.ok("User activated").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Deactivates an active user.
     * 
     * @param userId the unique ID of the user
     * @return Response indicating success or failure of deactivation
     */
    @PUT
    @Path("/users/{userId}/deactivate")
    public Response deactivateUser(@PathParam("userId") int userId) {
        try {
            adminService.deactivateUser(userId);
            return Response.ok("User deactivated").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Deletes a user from the system.
     * 
     * @param userId the unique ID of the user to delete
     * @return Response indicating success or failure of deletion
     */
    @DELETE
    @Path("/users/{userId}")
    public Response deleteUser(@PathParam("userId") int userId) {
        try {
            adminService.deleteUser(userId);
            return Response.ok("User deleted").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves all users belonging to a specific role.
     * 
     * @param role the role type (e.g., "CUSTOMER", "GYM_OWNER")
     * @return Response containing a list of users with the specified role
     */
    @GET
    @Path("/users/role/{role}")
    public Response getUsersByRole(@PathParam("role") String role) {
        List<User> users = adminService.getUsersByRole(role);
        return Response.ok(users).build();
    }
}
