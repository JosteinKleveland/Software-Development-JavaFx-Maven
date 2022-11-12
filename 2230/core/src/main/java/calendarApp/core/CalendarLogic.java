package calendarApp.core;

/**
 * Contains logic framework for the application
 */
public class CalendarLogic {
    
    private Calendar currentCalendar; // Current calendar object

    //Dummy-constructor to be able to test from CalendarLogicTest
    public CalendarLogic(Calendar calendar) {
        setCurrentCalendar(calendar);
    }

    /**
     * Adds a new appointment to the respective calendar
     * @param c1
     * @param newAppointment
     * @throws IllegalArgumentException if the new appointment collides with an existing one in the calendar
     */
    public void addAppointmentToCalendar(Calendar c1, Appointment newAppointment){
        
        // Check if new appointment collide with existing 
        for (Appointment existingAppointment : c1.getAppointments()){
            if (existingAppointment.getDayOfTheWeek() == newAppointment.getDayOfTheWeek()){
                if(checkCollision(existingAppointment, newAppointment)){
                    throw new IllegalArgumentException("This appointment colides with existing appointment");
                }
            }
        }

        c1.addAppointment(newAppointment);
    }

    // Getter

    public Calendar getCurrentCalendar() {
        // to prevent direct access from outside, 
        // only a copy of the calendar object will be returned
        if (currentCalendar == null) {
            return null;
        }
        Calendar calendar = new Calendar(currentCalendar.getCalendarName());
        for ( Appointment appointment : currentCalendar.getAppointments() ) {
            currentCalendar.addAppointment(appointment);
        }
        return calendar;
    }

    // Setter

    private void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }

    /**
     * Helper function
     * Checks if the new appointment collides timewise with an existing one,
     * i.e. if the new appointment exists in the same time frame as the exsisting one
     * @param existingAppointment
     * @param newAppointment
     * @return true if it collides and false otherwise
     */
    private boolean checkCollision(Appointment existingAppointment, Appointment newAppointment){

        if (existingAppointment.getDayOfTheWeek() == newAppointment.getDayOfTheWeek()){
                if(newAppointment.getStartHour() > existingAppointment.getStartHour() && newAppointment.getStartHour() < existingAppointment.getStopHour()
                || newAppointment.getStopHour() > existingAppointment.getStartHour() && newAppointment.getStopHour() < existingAppointment.getStartHour()){
                    return true;
                }

                if ( newAppointment.getStartHour() == existingAppointment.getStartHour() && newAppointment.getStopHour() == existingAppointment.getStopHour() 
                && newAppointment.getStopMinute() < existingAppointment.getStartMinute()
                || newAppointment.getStartHour() == existingAppointment.getStartHour() && newAppointment.getStopHour() == existingAppointment.getStopHour() 
                && newAppointment.getStartMinute() < existingAppointment.getStopMinute()){
                    return true;
                }
            }
        return false;
        }
}
