package calendarApp.restapi;

//import javax.annotation.processing.Generated;
import jakarta.ws.rs.core.MediaType;
import calendarApp.core.CalendarLogic;
import calendarApp.core.Calendar;
import calendarApp.json.CalendarSaveHandler;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

/**
 * Used for all requests referring to a Calendar by name
 */

@Produces(MediaType.APPLICATION_JSON) //Husk å importere
public class CalendarResource {
    
    //private Logger 
    private final CalendarLogic calendarLogic;
    private final String calendarName;
    private final Calendar calendar;

    @Context
    private CalendarSaveHandler calendarSaveHandler;

    public void setCalendarSaveHandler(CalendarSaveHandler calendarSaveHandler) {
        this.calendarSaveHandler = calendarSaveHandler;
    }

    /**
     * Initializes this CalendarResource with appropriate context information. Each method will check and use what it needs.
     * @param calendarLogic needed for delete and rename
     * @param calendarName needed for requesting the correct calender
     * @param calendar the calendar or null, needed for @PUT
     */
    public CalendarResource(CalendarLogic calendarLogic, String calendarName, Calendar calendar) {
        this.calendarLogic = calendarLogic;
        this.calendarName = calendarName;
        this.calendar = calendar;
    }

    //throw an exception in case of missing Calendar  
    private void checkCalendar() {
        if (this.calendar == null) {
            throw new NotFoundException("No calendar \"" + calendarName + "\"");

        }
    }

    /**
     * Gets the corresponding Calendar
     * @return the corresponding Calendar
     */
    @GET
    public Calendar getCalendar() {
        checkCalendar();
        return this.calendar;
    }

    private void autoSaveCalendarLogic() {
        if(calendarSaveHandler != null) {
            try {
                calendarSaveHandler.save(this.calendar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Replaces or adds a Calendar 
     * @param Calendar to add 
     * @param return true if it was added, false if it was replaced
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean putCalendar(Calendar calendar) {
        Calendar oldCalendar = this.calendarLogic.setCurrentCalendar(calendar);
        autoSaveCalendarLogic();
        return oldCalendar == null;
    }

     /**
     * Replaces or adds a Calendar with the given name, if it does not exist already
     * @param Calendar to add 
     * @param return true if it was added, false if it was replaced
     */
    @PUT
    public boolean putCalendar() {
       return putCalendar(new Calendar("name"));
    }

    /**
     * Rename Calendar
     * @param newName to rename the existing calendar
     * @return true if name was changed, else throws exception
     */
    @POST
    @Path("/rename")
    public boolean renameCalendar(@QueryParam("newName") String newName) {
        checkCalendar();
        if(this.calendarLogic.getCurrentCalendar() != null) {
            throw new IllegalArgumentException("A Calendar names \"" + newName + "\" already exist");
        }
        this.calendar.setCalendarName(newName); //setCalendarName er private, og kan ikke nås
        autoSaveCalendarLogic();
        return true;
    }

    /**
     * Removes the Calendar 
     * @return true if Calendar was removed, else exception is thrown
     */
    /*@DELETE
    public boolean removeCalendar() {
        checkCalendar();
        this.calendarLogic.removeCalendar(this.calendar);
        autoSaveCalendarLogic();
        return true;
    }*/
}

