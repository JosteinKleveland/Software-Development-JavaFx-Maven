package calendarApp.restserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import calendarApp.json.CalendarSaveHandler;
import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.restapi.CalendarLogicService;
import calendarApp.core.DaysOfTheWeek;

/**
 * Configures the rest service,
 * e.g. JSON support with Jackson and
 * injectable CalendarLogic and CalendarSaveHandler
 */
public class CalendarConfig extends ResourceConfig {

  private CalendarLogic calendarLogic;
  private CalendarSaveHandler calendarSaveHandler;

  /**
   * Initialize this CalendarConfig.
   *
   * @param calendarLogic calendarLogic instance to serve
   */
  public CalendarConfig(CalendarLogic calendarLogic) {
    setCalendarLogic(calendarLogic);
    calendarSaveHandler = new CalendarSaveHandler();
    register(CalendarLogicService.class);
    register(CalendarModuleObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(CalendarConfig.this.calendarLogic);
        bind(CalendarConfig.this.calendarSaveHandler);
      }
    });
  }

  /**
   * Initialize this CalendarConfig with a default CalendarLogic.
   */
  public CalendarConfig() {
    this(createDefaultCalendarLogic());
  }

  /**
   * Getter for the calendar logic with the calendar
   * 
   * @return the calendarLogic object
   */
  public CalendarLogic getCalendarLogic() {
    return new CalendarLogic(calendarLogic.getCurrentCalendar());
  }

  /**
   * sets the internal calendarLogic
   * 
   * @param calendarLogic to set
   */
  public void setCalendarLogic(CalendarLogic calendarLogic) {
    this.calendarLogic = new CalendarLogic(calendarLogic.getCurrentCalendar());
  }

  /**
   * creates a default calendarLogic with a Calendar
   * 
   * @return the calendarLogic
   */
  private static CalendarLogic createDefaultCalendarLogic() {
    CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();
    URL url = CalendarConfig.class.getResource("default-calendarlogic.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        return calendarSaveHandler.readCalendarLogic(reader);
      } catch (IOException e) {
        System.out.println("Couldn't read default-calendarlogic.json, so rigging CalendarLogic manually ("
            + e + ")");
      }
    }
    Calendar calendar1 = new Calendar("calendar1");
    CalendarLogic calendarLogic = new CalendarLogic(calendar1);
    calendar1.addAppointment(new Appointment("Appointment", "First appointment", DaysOfTheWeek.MONDAY, 0, 1, 0, 0));
    //calendarLogic.setCurrentCalendar(calendar1);
    //todoModel.addTodoList(new TodoList("todo2"));
    return calendarLogic;
  }
}

