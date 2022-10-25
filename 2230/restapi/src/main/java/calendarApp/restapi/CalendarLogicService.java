package calendarApp.restapi;

import calendarApp.core.CalendarLogic;

/**
 * The top-level rest service for CalendarLogic
 */
@Path(CalendarLogicService.CALENDAR_LOGIC_SERVICE_PATH)
public class CalendarLogicService {
    //prefix for all requests that are sent to instances of this particular class
    public static final String CALENDAR_LOGIC_SERVICE_PATH = "calendar";

    //Logger?

    //@Inject?
    @Context
    private CalendarLogic calendarLogic;

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public CalendarLogic getCalendarLogic() {
        //LOG.debug ...
        return calendarLogic;
    }
}
