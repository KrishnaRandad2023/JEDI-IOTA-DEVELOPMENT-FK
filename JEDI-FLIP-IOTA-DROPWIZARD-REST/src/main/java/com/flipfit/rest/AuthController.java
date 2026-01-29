package com.flipfit.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.client.ServiceFactory;
import com.flipfit.exception.*;

/**
 * Controller for authentication-related operations such as login and
 * registration.
 * 
 * @author team IOTA
 */
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    /**
     * Default constructor for AuthController.
     */
    public AuthController() {
    }

    private final GymUserService gymUserService = ServiceFactory.getInstance().getGymUserService();

    /**
     * Authenticates a user based on email and password.
     * 
     * @param email    user's email
     * @param password user's password
     * @return Response containing the User object if successful, or error status
     */
    @POST
    @Path("/login")
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        try {
            User user = gymUserService.login(email, password);
            if (user != null) {
                return Response.ok(user).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Registers a new customer.
     * 
     * @param customer GymCustomer object containing details
     * @return Response indicating success or failure of registration
     */
    @POST
    @Path("/register/customer")
    public Response registerCustomer(GymCustomer customer) {
        try {
            Role customerRole = new Role(3, "CUSTOMER", "Customer who books slots");
            customer.setRole(customerRole);
            if (gymUserService.registerUser(customer)) {
                return Response.status(Response.Status.CREATED).entity("Customer registered successfully").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Registration failed").build();
        } catch (RegistrationNotDoneException | InvalidEmailException | InvalidMobileException
                | InvalidAadhaarException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Submits a registration request for a new gym owner.
     * 
     * @param registration Registration object containing details
     * @return Response indicating acceptance of the registration request
     */
    @POST
    @Path("/register/owner")
    public Response registerOwner(Registration registration) {
        try {
            registration.setRoleType("GYM_OWNER");
            registration.setApproved(false);
            registration.setRegistrationDate(new java.util.Date());

            if (gymUserService.addRegistration(registration)) {
                return Response.status(Response.Status.ACCEPTED).entity("Owner registration request submitted").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Registration request failed").build();
        } catch (InvalidEmailException | InvalidMobileException | InvalidAadhaarException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Changes a user's password.
     * 
     * @param email       registered email
     * @param oldPassword current password
     * @param newPassword new password to set
     * @return Response indicating success or failure of password change
     */
    @POST
    @Path("/change-password")
    public Response changePassword(@QueryParam("email") String email,
            @QueryParam("oldPassword") String oldPassword,
            @QueryParam("newPassword") String newPassword) {
        try {
            User user = gymUserService.login(email, oldPassword);
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
            }
            if (gymUserService.changePassword(user.getUserId(), oldPassword, newPassword)) {
                return Response.ok("Password changed successfully").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to change password").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
