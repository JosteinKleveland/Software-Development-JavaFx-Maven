package calendarApp.restapi;

import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.json.CalendarSaveHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;

import java.io.IOException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

/**
 * The top-level rest service for CalendarLogic
 */
@Path(CalendarLogicService.CALENDAR_LOGIC_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class CalendarLogicService {
    //prefix for all requests that are sent to instances of this particular class
    public static final String CALENDAR_LOGIC_SERVICE_PATH = "calendar";
    private String calendarName;

    private static final Logger LOG = LoggerFactory.getLogger(CalendarLogic.class);

    @Context
    private CalendarLogic calendarLogic;

    private Calendar calendar;

    @Context
    private CalendarSaveHandler calendarSaveHandler;

    /**
     * The root resource, i.e /calendar
     * 
     * @return the CalendarLogic
     */
    @GET
    public CalendarLogic getCalendarLogic() {
      LOG.debug("getCalendarLogic: " + calendarLogic);
      return new CalendarLogic(calendarLogic.getCurrentCalendar());
    }

    /**
     * Autosaves the calendar logic
     */
    private void autoSaveCalendarLogic() {
      if(calendarSaveHandler != null) {
        try {
          CalendarSaveHandler.save(this.calendar);
        } catch (IllegalStateException | IOException e) {
          LOG.error("Couldn't autosave Calender: ", e);
        }
      }
    }

    /**
     * Checks if the calendar is null
     * 
     * Throw an exception in case of missing Calendar
     */ 
    private void checkCalendar(Calendar calendar) {
      if (calendar == null) {
        throw new NotFoundException("No calendar with this name is found");  
      }
    }

  /**
   * Returns the CalendarResource containing the
   * currentCalendar in the CalendarLogic
   * (as a resource to support chaining path elements).
   * This supports all requests referring to Calendar by name.
   * Note that the Calendar needn't exist, since it can be a PUT.
   *
   * @param name the name of the calendar
   * @return Calendar the calendar
   */
  @Path("/{name}")
  public Calendar getCurrentCalendar(@PathParam("name") String name) {
    //Calendar calendar = getCalendarLogic().getCurrentCalendar();
    Calendar calendar = null;
    try {
      calendar = CalendarSaveHandler.load(name);
  } catch (JsonParseException e) {
      e.printStackTrace();
  } catch (JsonMappingException e) {
      e.printStackTrace();
  } catch (IOException e) {
      e.printStackTrace();
  }    
    LOG.debug("Sub-resource for Calendar " + name + ": " + calendar);
    checkCalendar(calendar);
    return calendar;
  }

    /**
     * Replaces or adds a Calendar 
     * 
     * @param Calendar to add 
     * @return true if it was added, false if it was replaced
     * @throws IOException
     * @throws JsonProcessingException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Calendar putCalendar(Calendar calendar) throws JsonProcessingException, IOException {
        LOG.debug("putCalendar({})", calendar);
        CalendarSaveHandler.save(calendar);
        if (CalendarSaveHandler.getFile(calendar.getCalendarName()).isFile()){
          return calendar;
        } else {
          return null;
        }
    }

     /**
     * Rename the Calendar
     * 
     * @param newName to rename the existing calendar
     * @return true if name was changed, else throws exception
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @POST
    @Path("/rename")
    public boolean renameCalendar(@QueryParam("newName") String newName) throws JsonParseException, JsonMappingException, IOException {
        if(CalendarSaveHandler.load(newName) != null) {
            throw new IllegalArgumentException("A Calendar name \"" + newName + "\" already exist");
        }
        this.calendar.setCalendarName(newName); //setCalendarName er private, og kan ikke nås
        autoSaveCalendarLogic();
        return true;
    }

    /**
     * Removes the Calendar 
     * 
     * @return true if Calendar was removed, else exception is thrown
     */
    @DELETE
    public boolean removeCalendar() {
        return CalendarSaveHandler.delete(this.calendarName);
    }

}
