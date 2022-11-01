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
    }

    public void addAppointmentToCalendar(Calendar c1, Appointment newAppointment) {
        calendarLogic.addAppointmentToCalendar(c1, newAppointment);
    }

    public Calendar getCurrentCalendar(String calendarName) {
        return calendarLogic.getCurrentCalendar(calendarName);
    }

    public void setCurrentCalendar(Calendar currentCalendar) {
        calendarLogic.setCurrentCalendar(currentCalendar);
    }

}
