package calendarApp.springboot.restserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;

/**
 * The service implementation.
 */

@RestController
@RequestMapping(CalendarLogicController.CALENDAR_LOGIC_SERVICE_PATH)
public class CalendarLogicController {  

  public static final String CALENDAR_LOGIC_SERVICE_PATH = "springboot/calendar";

  @Autowired
  private CalendarLogicService calendarLogicService;

  @GetMapping
  public CalendarLogic getCalendarLogic() {
    return calendarLogicService.getCalendarLogic();
  }

  /*private void autoSaveCalendarLogic() {
    todoModelService.autoSaveCalendarLogic();
  }*/

  private void checkCalendar(Calendar calendar, String name) {
    if (calendar == null) {
      throw new IllegalArgumentException("No Calendar named \"" + name + "\"");
    }
  }

  /**
   * Gets the corresponding Calendar.
   *
   * @param name the name of the Calendar
   * @return the corresponding Calendar
   */
  @GetMapping(path = "/{name}")
  public Calendar getCalendar(@PathVariable("name") String name) {
    Calendar calendar = getCalendarLogic().getCalendar(name);
    checkCalendar(calendar, name);
    return calendar;
  }

  /**
   * Replaces or adds a Calendar.
   *
   * @param name the name of the Calendar
   * @param todoList the Calendar to add
   * @return true if it was added, false if it replaced
   */
  @PutMapping(path = "/{name}")
  public boolean putCalendar(@PathVariable("name") String name,
      @RequestBody Calendar calendar) {
    boolean added = getCalendarLogic().setCurrentCalendar(calendar) == null;
    //autoSaveCalendarLogic();
    return added;
  }

  /**
   * Renames the Calendar.
   *
   * @param name the name of the Calendar
   * @param newName the new name
   */
  @PostMapping(path = "/{name}/rename")
  public boolean renameCalendar(@PathVariable("name") String name,
      @RequestParam("newName") String newName) {
    Calendar calendar = getCalendarLogic().getCurrentCalendar();
    checkCalendar(calendar, name);
    if (getCalendarLogic().getCurrentCalendar(newName) != null) {
      throw new IllegalArgumentException("A Calendar named \"" + newName + "\" already exists");
    }
    calendar.setCalendarName(newName);
    //autoSaveCalendarLogic();
    return true;
  }

  /**
   * Removes the Calendar.
   *
   * @param name the name of the Calendar
   */
  /*@DeleteMapping(path = "/{name}")
  public boolean removeCalendar(@PathVariable("name") String name) {
    Calendar calednar = getCalendarLogic().getCurrentCalendar();
    checkCalendar(calendar, name);
    getCalendarLogic().removeCurrentCalendar();
    //autoSaveCalendarLogic();
    return true;
  }*/
}
