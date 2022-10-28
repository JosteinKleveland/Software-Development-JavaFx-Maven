package calendarApp.core;

import java.util.ArrayList;

/**
 * Calendars with a name and list of appointments
 */
public class Calendar {

    
    private String calendarName; // name of the calendar
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>(); // arraylist of all the calendar's appointments

    /**
     * Constructor that initializes an instance of Calendar with the name given by the argument
     * @param calendarName
     */
    public Calendar(String calendarName) {
        setCalendarName(calendarName);
    }

    //Getters

    public String getCalendarName() {
        return calendarName;
    }
    
    public ArrayList<Appointment> getAppointments() {
        return new ArrayList<Appointment>(appointments);
    }

    /**
     * Sets the calendar name from the argument
     * @param calendarName
     * @throws IllegalArgumentException if the argument was not in a valid format
     */
    private void setCalendarName(String calendarName) {
        if(checkCalendarName(calendarName) == false){
            throw new IllegalArgumentException("Calendarname does not have a valid format");
        }
        this.calendarName = calendarName;
    }


    /**
     * Finds and returns the appointment in the calendar that matches with the arguments
     * If it does not exist, return null
     * @param appointmentName name of the appointment
     * @param dayOfTheWeek day of the appointment
     * @param startHour hour of the day when the appointment starts
     * @param stopHour hour of the day when the appointment ends
     * @param startMinute minute of the day when the appointment starts
     * @param stopMinute minute of the day when the appointment ends
     * @return Appointment object or null
     */
    public Appointment getAppointement(String appointmentName, DaysOfTheWeek dayOfTheWeek, int startHour , int stopHour, int startMinute, int stopMinute) {
        ArrayList<Appointment> appointments = getAppointments();
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentName() == appointmentName 
                && appointment.getDayOfTheWeek() == dayOfTheWeek
                && appointment.getStartHour() == startHour
                && appointment.getStartMinute() == startMinute
                && appointment.getStopHour() == stopHour
                && appointment.getStopMinute() == stopMinute) {
                    return appointment;
                }
        }    
        return null;   
    }

   /**
    * Helper function
    * Checks if the calendarName argument is in a correct format
    * @param calendarName
    * @return true if the format is correct and false otherwise
    */
    private boolean checkCalendarName(String calendarName){
        //If the calendar name is empty or has a length less than 2, return false
        if (calendarName.isBlank() || calendarName.length() < 2){
            return false;
        }

        //If the calendar name has any special characters, return false
        else if(!calendarName.matches("^[a-zA-Z0-9]*$")){
            return false;
        } 
        
        return true;
    }

    
    /**
     * Adds an Appointment object to this calendar's array of appointments
     * @param appointment reference to the appointment object
     */
    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }


    /**
     * Removes an Appointment object from this calendar's array of appointments
     * @param appointment reference to the appointment object
     */
    public void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
    }
}
