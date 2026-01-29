package com.flipfit.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.client.ServiceFactory;
import com.flipfit.exception.UserNotFoundException;

/**
 * Controller for gym owner operations.
 * Handles tasks such as managing centers, slots, and viewing bookings for their
 * gyms.
 * 
 * @author team IOTA
 */
@Path("/owner")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GymOwnerController {

    /**
     * Default constructor for GymOwnerController.
     */
    public GymOwnerController() {
    }

    private final GymOwnerService gymOwnerService = ServiceFactory.getInstance().getGymOwnerService();

    /**
     * Adds a new gym center for the owner.
     * 
     * @param userId the ID of the gym owner (from header)
     * @param center GymCenter object containing center details
     * @return Response indicating success (pending approval) or failure
     */
    @POST
    @Path("/centers")
    public Response addGymCenter(@HeaderParam("userId") int userId, GymCenter center) {
        try {
            boolean success = gymOwnerService.addGymCenter(
                    userId,
                    center.getCenterName(),
                    center.getAddress(),
                    center.getCity(),
                    center.getCapacity());
            if (success) {
                return Response.status(Response.Status.CREATED).entity("Center added successfully, pending approval")
                        .build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to add center").build();
        } catch (UserNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Updates an existing gym center's details.
     * 
     * @param userId   the ID of the gym owner (from header)
     * @param centerId the ID of the center to update
     * @param center   GymCenter object with updated details
     * @return Response indicating success or failure of update
     */
    @PUT
    @Path("/centers/{centerId}")
    public Response updateGymCenter(@HeaderParam("userId") int userId, @PathParam("centerId") int centerId,
            GymCenter center) {
        if (gymOwnerService.updateGymCenter(
                userId,
                centerId,
                center.getCenterName(),
                center.getAddress(),
                center.getCity(),
                center.getCapacity())) {
            return Response.ok("Center updated successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update center").build();
    }

    /**
     * Deletes a gym center.
     * 
     * @param userId   the ID of the gym owner (from header)
     * @param centerId the ID of the center to delete
     * @return Response indicating success or failure of deletion
     */
    @DELETE
    @Path("/centers/{centerId}")
    public Response deleteGymCenter(@HeaderParam("userId") int userId, @PathParam("centerId") int centerId) {
        if (gymOwnerService.deleteGymCenter(userId, centerId)) {
            return Response.ok("Center deleted").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete center").build();
    }

    /**
     * Retrieves all gym centers owned by the owner.
     * 
     * @param userId the ID of the gym owner (from header)
     * @return Response containing a list of all centers
     */
    @GET
    @Path("/centers")
    public Response getMyCenters(@HeaderParam("userId") int userId) {
        return Response.ok(gymOwnerService.viewMyCenters(userId)).build();
    }

    /**
     * Retrieves approved gym centers owned by the owner.
     * 
     * @param userId the ID of the gym owner (from header)
     * @return Response containing a list of approved centers
     */
    @GET
    @Path("/centers/approved")
    public Response getApprovedCenters(@HeaderParam("userId") int userId) {
        return Response.ok(gymOwnerService.viewMyApprovedCenters(userId)).build();
    }

    /**
     * Retrieves centers awaiting approval.
     * 
     * @param userId the ID of the gym owner (from header)
     * @return Response containing a list of pending centers
     */
    @GET
    @Path("/centers/pending")
    public Response getPendingCenters(@HeaderParam("userId") int userId) {
        return Response.ok(gymOwnerService.viewMyPendingCenters(userId)).build();
    }

    /**
     * Adds a new time slot to a gym center.
     * 
     * @param userId       the ID of the gym owner (from header)
     * @param centerId     the ID of the target gym center
     * @param startTimeStr start time in HH:mm format
     * @param endTimeStr   end time in HH:mm format
     * @param totalSeats   total capacity for the slot
     * @return Response indicating success or failure of slot addition
     */
    @POST
    @Path("/slots")
    public Response addSlot(@HeaderParam("userId") int userId,
            @QueryParam("centerId") int centerId,
            @QueryParam("startTime") String startTimeStr,
            @QueryParam("endTime") String endTimeStr,
            @QueryParam("totalSeats") int totalSeats) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(startTimeStr, formatter);
            LocalTime endTime = LocalTime.parse(endTimeStr, formatter);

            if (gymOwnerService.addSlotToCenter(userId, centerId, startTime, endTime, totalSeats)) {
                return Response.status(Response.Status.CREATED).entity("Slot added successfully").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to add slot").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves all slots across all centers owned by the owner.
     * 
     * @param userId the ID of the gym owner (from header)
     * @return Response containing a list of slots
     */
    @GET
    @Path("/slots")
    public Response getMySlots(@HeaderParam("userId") int userId) {
        return Response.ok(gymOwnerService.viewMySlots(userId)).build();
    }

    /**
     * Deletes a specific slot.
     * 
     * @param userId the ID of the gym owner (from header)
     * @param slotId the ID of the slot to delete
     * @return Response indicating success or failure of deletion
     */
    @DELETE
    @Path("/slots/{slotId}")
    public Response deleteSlot(@HeaderParam("userId") int userId, @PathParam("slotId") int slotId) {
        if (gymOwnerService.deleteSlot(userId, slotId)) {
            return Response.ok("Slot deleted").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete slot").build();
    }

    /**
     * Retrieves bookings for the owner's gyms, optionally filtered by center ID.
     * 
     * @param userId   the ID of the gym owner (from header)
     * @param centerId ID of a specific center (optional)
     * @return Response containing a list of bookings
     */
    @GET
    @Path("/bookings")
    public Response getBookings(@HeaderParam("userId") int userId, @QueryParam("centerId") Integer centerId) {
        if (centerId != null) {
            return Response.ok(gymOwnerService.viewBookingsForCenter(userId, centerId)).build();
        }
        return Response.ok(gymOwnerService.viewBookingsForMyCenters(userId)).build();
    }

    /**
     * Retrieves overall performance statistics for all gyms owned by the owner.
     * 
     * @param userId the ID of the gym owner (from header)
     * @return Response containing owner statistics
     */
    @GET
    @Path("/statistics")
    public Response getStatistics(@HeaderParam("userId") int userId) {
        return Response.ok(gymOwnerService.getMyStatistics(userId)).build();
    }
}
