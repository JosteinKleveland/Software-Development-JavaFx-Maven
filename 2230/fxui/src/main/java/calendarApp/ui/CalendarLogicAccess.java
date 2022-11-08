package calendarApp.ui;

import calendarApp.core.Calendar;
import calendarApp.core.Appointment;

/**
 * Class that centralizes access to a CalendarLogic
 * Makes it easier to support transparent use of a REST api
 */
public interface CalendarLogicAccess {
    
    public void addAppointmentToCalendar(Calendar c1, Appointment newAppointment);

    public Calendar getCurrentCalendar(String... calendarName);

    public void setCurrentCalendar(Calendar currentCalendar);

    public void removeCalendar(String name); // Skal vi ha med navnet på kalenderen?

    public void renameCalendar(String oldName, String newName);
}