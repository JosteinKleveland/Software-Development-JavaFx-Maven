package calendarApp.core;

public class CalendarLogic {
    
    private Calendar currentCalendar;

    //Dummy-constructor to be able to test from CalendarLogicTest
    public CalendarLogic() {
    }

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


    public Calendar getCurrentCalendar() {
        //to prevent direct access from outside, 
        //only a copy of the calendar object will be returned
        if (currentCalendar == null) {
            return null;
        }
        Calendar calendar = new Calendar(currentCalendar.getCalendarName());
        for ( Appointment appointment : currentCalendar.getAppointments() ) {
            currentCalendar.addAppointment(appointment);
        }
        return calendar;
    }

    public void setCurrentCalendar(Calendar currentCalendar) {
        //to prevent direct access from outside, 
        //and to have better controll over what comes in,
        //the calendar object will be set to a copy of the argument
        this.currentCalendar = currentCalendar != null ? new Calendar(currentCalendar.getCalendarName()) : null;
        if (currentCalendar != null) {
            for ( Appointment appointment : currentCalendar.getAppointments() ) {
                this.currentCalendar.addAppointment(appointment);
            }
        }
    }

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
