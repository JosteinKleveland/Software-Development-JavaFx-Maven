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

/**
 * Configures the calendar service,
 * including autowired objects.
 */

@Service
public class TodoModelService {

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
    //Må opprette en metode for alternativ fillagringssti i CalendarSaveHandler
    //Så vi faktisk får lagret kalenderne på server
    //calendarSaveHandler.setSaveFile("springbootserver-todolist.json");
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

  /*
  //Må også opprette en metode for "readCalendarLogic", det finnes en tilsvarende i
  //TodoPersistence som heter "readTodoModel(reader)"
  private static CalendarLogic createDefaultCalendarLogic() {
    CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();
    URL url = CalendarLogicService.class.getResource("default-calendarlogic.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        return calendarLogic.readTodoModel(reader);
      } catch (IOException e) {
        System.out.println("Couldn't read default-calendarlogic.json, so rigging CalendarLogic manually ("
            + e + ")");
      }
    }
    CalendarLogic calendarLogic = new CalendarLogic();
    Calendar calendar1 = new Calendar("calendar1");
    calendar1.addAppointment(new Appointment());
    calendarLogic.setCurrentCalendar(calendar1);
    //todoModel.addTodoList(new TodoList("todo2"));
    return calendarLogic;
  } */

  /**
   * Saves the CalendarLogic to disk.
   * Should be called after each update.
   */
  /*public void autoSaveCalendarLogic() {
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
