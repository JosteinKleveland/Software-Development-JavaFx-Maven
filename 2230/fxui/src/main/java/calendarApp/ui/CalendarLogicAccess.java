package calendarApp.ui;

import calendarApp.core.Calendar;
import calendarApp.core.Appointment;

/**
 * Class that centralizes access to a CalendarModel
 * Makes it easier to support transparent use of a REST api
 */
public interface CalendarLogicAccess {
    
    public void addAppointmentToCalendar(Calendar c1, Appointment newAppointment);

    public Calendar getCurrentCalendar();

    public void setCurrentCalendar(Calendar currentCalendar);
}