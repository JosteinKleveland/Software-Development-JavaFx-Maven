package calendarApp.core;

import java.util.ArrayList;

public class Calendar {

    private String calendarName;
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    public Calendar(String calendarName) {
        setCalendarName(calendarName);
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        if(checkCalendarName(calendarName) == false){
            throw new IllegalArgumentException("Calendarname does not have a valid format");
        }
        this.calendarName = calendarName;
    }

    public ArrayList<Appointment> getAppointments() {
        return new ArrayList<Appointment>(appointments);
        //return appointments;
    }

    private boolean checkCalendarName(String calendarName){
        if (calendarName.isBlank() || calendarName.length() < 2){
            return false;
        }

        else if(!calendarName.matches("^[a-zA-Z0-9]*$")){
            return false;
        } 
        
        return true;
    }

    public void addAppointment(Appointment appointmentName){
        appointments.add(appointmentName);
    }

    public void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
    }
}
