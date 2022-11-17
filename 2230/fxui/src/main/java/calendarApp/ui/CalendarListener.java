package calendarApp.ui;

import calendarApp.core.Appointment;

// Makes the pane of the respective appointment in the calendar grid 
public interface CalendarListener {
    public void onClickListener(Appointment appointment);
    
}
