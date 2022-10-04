package calendarApp.core;

import java.util.ArrayList;

public class Calendar {

    private String calendarName;
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();


    public Calendar(String calendarName) {
        setCalendarName(calendarName);
    }

    // For loading of existing calendars
    public Calendar(String calendarName, Appointment[] appointment) {
        setCalendarName(calendarName);
    }
    
    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }



}
