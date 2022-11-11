package calendarApp.springboot.restserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import calendarApp.core.Calendar;
import calendarApp.core.Appointment;
import calendarApp.core.CalendarLogic;
import calendarApp.json.CalendarSaveHandler;
import calendarApp.core.DaysOfTheWeek;

/**
 * Configures the calendar service,
 * including autowired objects.
 */
@Service
public class CalendarLogicService {

  private CalendarLogic calendarLogic;
  private CalendarSaveHandler calendarSaveHandler;

  /**
   * Initializes the service with a specific CalendarLogic.
   *
   * @param calendarLogic the CalendarLogic
   */
  public CalendarLogicService(CalendarLogic calendarLogic) {
    this.calendarLogic = calendarLogic;
    this.calendarSaveHandler = new CalendarSaveHandler();
    calendarSaveHandler.setSaveFile("springbootserver-calendar.json");
  }

  public CalendarLogicService() {
    this(createDefaultCalendarLogic());
  }

  public CalendarLogic getCalendarLogic() {
    return calendarLogic;
  }

  public void setCalendarLogic(CalendarLogic calendarLogic) {
    this.calendarLogic = calendarLogic;
  }

  private static CalendarLogic createDefaultCalendarLogic() {
    CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();
    URL url = CalendarLogicService.class.getResource("default-calendarlogic.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        return calendarSaveHandler.readCalendarLogic(reader);
      } catch (IOException e) {
        System.out.println("Couldn't read default-calendarlogic.json, so rigging CalendarLogic manually ("
            + e + ")");
      }
    }
    CalendarLogic calendarLogic = new CalendarLogic();
    Calendar calendar1 = new Calendar("calendar1");
    calendar1.addAppointment(new Appointment("Appointment 1", "First appointment", DaysOfTheWeek.MONDAY, 0, 0, 1, 0));
    calendarLogic.setCurrentCalendar(calendar1);
    //todoModel.addTodoList(new TodoList("todo2"));
    return calendarLogic;
  } 

  /**
   * Saves the CalendarLogic to disk.
   * Should be called after each update.
   */
  /* public void autoSaveCalendarLogic() {
    if (calendarSaveHandler != null) {
      try {
        calendarSaveHandler.autoSaveCalendarLogic(this.calendarLogic);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Couldn't auto-save CalendarLogic: " + e);
      }
    }
  }*/
  //ser ut til å ønske å implementere en "autoSaveCalendarLogic" i CalendarSaveHandler
}
