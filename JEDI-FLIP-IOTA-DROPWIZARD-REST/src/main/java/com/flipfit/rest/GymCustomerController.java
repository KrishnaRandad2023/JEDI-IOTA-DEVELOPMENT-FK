package com.flipfit.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.client.ServiceFactory;
import com.flipfit.exception.*;

/**
 * Controller for customer-related operations.
 * Handles tasks such as viewing gyms, booking slots, and managing profile.
 * 
 * @author team IOTA
 */
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymCustomerController {

    /**
     * Default constructor for GymCustomerController.
     */
    public GymCustomerController() {
    }

    private final CustomerService customerService = ServiceFactory.getInstance().getCustomerService();

    /**
     * Retrieves a list of gyms, optionally filtered by city.
     * 
     * @param city the city to filter gyms (optional)
     * @return Response containing a list of gyms
     */
    @GET
    @Path("/gyms")
    public Response getGyms(@QueryParam("city") String city) {
        if (city != null && !city.isEmpty()) {
            return Response.ok(customerService.viewGymsInCity(city)).build();
        }
        return Response.ok(customerService.viewAllGyms()).build();
    }

    /**
     * Searches for gyms by name.
     * 
     * @param name the name or part of the name of the gym
     * @return Response containing a list of matching gyms
     */
    @GET
    @Path("/gyms/search")
    public Response searchGyms(@QueryParam("name") String name) {
        return Response.ok(customerService.searchGymsByName(name)).build();
    }

    /**
     * Retrieves details of a specific gym center.
     * 
     * @param centerId the unique ID of the gym center
     * @return Response containing the gym center details
     */
    @GET
    @Path("/gyms/{centerId}")
    public Response getGymDetails(@PathParam("centerId") int centerId) {
        GymCenter center = customerService.getGymCenterDetails(centerId);
        if (center != null) {
            return Response.ok(center).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Retrieves available slots for a specific gym center.
     * 
     * @param centerId the unique ID of the gym center
     * @return Response containing a list of available slots
     */
    @GET
    @Path("/gyms/{centerId}/slots")
    public Response getSlots(@PathParam("centerId") int centerId) {
        return Response.ok(customerService.viewAvailableSlotsForGym(centerId)).build();
    }

    /**
     * Books a slot for the authenticated customer.
     * 
     * @param userId the ID of the customer (from header)
     * @param slotId the ID of the slot to book
     * @return Response indicating success, waitlist status, or failure
     */
    @POST
    @Path("/bookings")
    public Response bookSlot(@HeaderParam("userId") int userId, @QueryParam("slotId") int slotId) {
        try {
            int bookingId = customerService.bookSlot(userId, slotId);
            if (bookingId > 0) {
                return Response.status(Response.Status.CREATED).entity("Booking successful. ID: " + bookingId).build();
            } else if (bookingId == -1) {
                return Response.ok("Added to waitlist").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Booking failed").build();
        } catch (UserNotFoundException | SlotNotAvailableException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Cancels an existing booking.
     * 
     * @param userId    the ID of the customer (from header)
     * @param bookingId the ID of the booking to cancel
     * @return Response indicating success or failure of cancellation
     */
    @DELETE
    @Path("/bookings/{bookingId}")
    public Response cancelBooking(@HeaderParam("userId") int userId, @PathParam("bookingId") int bookingId) {
        try {
            customerService.cancelBooking(userId, bookingId);
            return Response.ok("Booking cancelled").build();
        } catch (BookingNotDoneException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves all bookings for the customer.
     * 
     * @param userId the ID of the customer (from header)
     * @return Response containing a list of all bookings
     */
    @GET
    @Path("/bookings")
    public Response getMyBookings(@HeaderParam("userId") int userId) {
        return Response.ok(customerService.viewMyBookings(userId)).build();
    }

    /**
     * Retrieves active bookings for the customer.
     * 
     * @param userId the ID of the customer (from header)
     * @return Response containing a list of active bookings
     */
    @GET
    @Path("/bookings/active")
    public Response getActiveBookings(@HeaderParam("userId") int userId) {
        return Response.ok(customerService.viewMyActiveBookings(userId)).build();
    }

    /**
     * Retrieves current waitlist status for the customer.
     * 
     * @param userId the ID of the customer (from header)
     * @return Response containing waitlist details
     */
    @GET
    @Path("/waitlist")
    public Response getWaitlist(@HeaderParam("userId") int userId) {
        return Response.ok(customerService.viewMyWaitlistStatus(userId)).build();
    }

    /**
     * Retrieves personal booking statistics for the customer.
     * 
     * @param userId the ID of the customer (from header)
     * @return Response containing customer statistics
     */
    @GET
    @Path("/statistics")
    public Response getStatistics(@HeaderParam("userId") int userId) {
        return Response.ok(customerService.getMyStatistics(userId)).build();
    }

    /**
     * Updates the customer's profile details.
     * 
     * @param userId   the ID of the customer (from header)
     * @param customer GymCustomer object with updated details
     * @return Response indicating success or failure of update
     */
    @PUT
    @Path("/profile")
    public Response updateProfile(@HeaderParam("userId") int userId, GymCustomer customer) {
        try {
            customer.setUserId(userId);
            if (customerService.updateMyProfile(customer)) {
                return Response.ok("Profile updated successfully").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update profile").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Changes the customer's password.
     * 
     * @param userId      the ID of the customer (from header)
     * @param oldPassword current password
     * @param newPassword new password to set
     * @return Response indicating success or failure of password change
     */
    @POST
    @Path("/change-password")
    public Response changePassword(@HeaderParam("userId") int userId,
            @QueryParam("oldPassword") String oldPassword,
            @QueryParam("newPassword") String newPassword) {
        try {
            if (customerService.changeMyPassword(userId, oldPassword, newPassword)) {
                return Response.ok("Password changed successfully").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to change password").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
