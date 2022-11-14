package calendarApp.ui;

import calendarApp.core.Calendar;
import calendarApp.core.Appointment;
import calendarApp.core.CalendarLogic;

/**
 * Class that centralizes access to a CalendarLogic
 * Makes it easier to support transparent use of a REST api
 */
public class DirectCalendarLogicAccess implements CalendarLogicAccess {
    
    private final CalendarLogic calendarLogic;

    public DirectCalendarLogicAccess(CalendarLogic calendarLogic) {
        this.calendarLogic = calendarLogic;
        this.calendarLogic.setCurrentCalendar(calendarLogic.getCurrentCalendar());
    }

    public void addAppointmentToCalendar(Calendar c1, Appointment newAppointment) {
        calendarLogic.addAppointmentToCalendar(c1, newAppointment);
    }

    public Calendar getCurrentCalendar(String... calendarName) {
        return calendarLogic.getCurrentCalendar(calendarName);
    }

    public void setCurrentCalendar(Calendar currentCalendar) {
        calendarLogic.setCurrentCalendar(currentCalendar);
    }

    public void removeCalendar(String name) {
        //calendarLogic.removeCalendar();
    }

    public void renameCalendar(String oldName, String newName) {
        Calendar calendar = calendarLogic.getCurrentCalendar();
        if (calendar == null) {
        throw new IllegalArgumentException("No Calendar named \"" + oldName + "\" found");
        }
        if (calendarLogic.getCurrentCalendar(newName) != null) {
        throw new IllegalArgumentException("A TodoList named \"" + newName + "\" already exists");
        }
        calendar.setCalendarName(newName);
    }
}
