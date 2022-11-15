package calendarApp.ui;

import calendarApp.core.Calendar;

/**
 * Class that centralizes access to a CalendarLogic
 * Makes it easier to support transparent use of a REST api
 */
public interface CalendarLogicAccess {
    
    public Calendar getCurrentCalendar(String... calendarName);

    public void setCurrentCalendar(Calendar currentCalendar);

    public void deleteCalendar(String name);

    public void renameCalendar(String oldName, String newName);

    public boolean isValidCalendarName(String name);
}