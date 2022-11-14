package calendarApp.restapi;

import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.json.CalendarSaveHandler;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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

    //Logger?

    //@Inject?
    @Context
    private CalendarLogic calendarLogic;

    @Context
    private CalendarSaveHandler calendarSaveHandler;

    /**
     * The root resource, i.e /calendar
     * 
     * @return the CalendarLogic
     */
    @GET
    public CalendarLogic getCalendarLogic() {
        //LOG.debug ...
        return calendarLogic;
    }

    /**
     * The root resource, i.e. /calendar
     * 
     * @return the CalendarLogic
     */
    /*@Path("/settings")
    public TodoSettingsResource getTodoSettings() {
        LOG.debug("Sub-resource for TodoSettings");
        return new TodoSettingsResource(todoModel);
    }*/ 
    //Tror denne ikke er nødvndig for vår del? Henter ut settings elns.

    /**
   * Returns the CalendarResource containing the
   * currentCalendar in the CalendarLogic
   * (as a resource to support chaining path elements).
   * This supports all requests referring to TodoLists by name.
   * Note that the Calendar needn't exist, since it can be a PUT.
   *
   * @param name the name of the calendar
   */
  @Path("/list/{name}")
  public CalendarResource getCalendar(@PathParam("name") String name) {
    Calendar calendar = getCalendarLogic().getCurrentCalendar();
    CalendarResource calendarResource = new CalendarResource(calendarLogic, name, calendar);
    calendarResource.setCalendarSaveHandler(calendarSaveHandler);
    return calendarResource;
  }
}

