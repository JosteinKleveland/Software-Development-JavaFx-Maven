package calendarApp.core;

public class Appointment {

    private String appointmentName;
    private DaysOfTheWeek dayOfTheWeek;
    private int startHour;
    private int startMinute;
    private int stopHour;
    private int stopMinute;
    
    public Appointment(String appointmentName, DaysOfTheWeek dayOfTheWeek, int startHour , int stopHour, int startMinute, int stopMinute) {
        setAppointmentName(appointmentName);
        setDayOfTheWeek(dayOfTheWeek);;
        setTime(startHour, stopHour, startMinute, stopMinute);
    }

    private void setAppointmentName(String appointmentName) {
        if (!checkAppointmentName(appointmentName)){
            throw new IllegalArgumentException("Given appintment name is blanc or have an illegal format. Only A-Y, a-y and 0-9 is allowed. The name must be at least 2 characters");
        }
        this.appointmentName = appointmentName;
    }
    private void setDayOfTheWeek(DaysOfTheWeek dayOfTheWeek) {
        if(!checkDayOfTheWeek(dayOfTheWeek)){
            throw new IllegalArgumentException("Input for day is not valid");
        }

        this.dayOfTheWeek = dayOfTheWeek;
    }

    private void setTime(int startHour, int stopHour, int startMinute, int stopMinute){
        if(!checkTimeFormat(startHour, startMinute, stopHour, stopMinute)){
            throw new IllegalArgumentException("Time does not have the correct format");
        }
        this.startHour = startHour;
        this.stopHour = stopHour;
        this.startMinute = startMinute;
        this.stopMinute = stopMinute;
    }

    public String getAppointmentName() {
        return appointmentName;
    }
    public DaysOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }
    public int getStartHour() {
        return startHour;
    }
    public int getStartMinute() {
        return startMinute;
    }
    public int getStopHour() {
        return stopHour;
    }
    public int getStopMinute() {
        return stopMinute;
    }

    private boolean checkTimeFormat(int startHour,int stopHour,int startMinute,int stopMinute){
        //
        if (startHour < 0 || startHour > 23 || stopHour < 0 || stopHour > 23){
            return false;
        }

        else if (startMinute > 59 || startMinute < 0 || stopMinute > 59 || stopMinute < 0){
            return false;
        }
        // Check that an event is set to end after the start time
        else if (startHour > stopHour){
            return false;
        }

        else if (startHour == stopHour && startMinute >= stopMinute){
            return false;
        }

        return true;
    }

    private boolean checkAppointmentName(String appointmentName){
        if (appointmentName.isBlank() || appointmentName.matches("^[a-zA-Z0-9]*$") || appointmentName.length() < 2){
            return false;
        }
            return true;
    }

    private boolean checkDayOfTheWeek(DaysOfTheWeek day){
        if (!day.getClass().isEnum()){
            return false;
        }
        return true;
    }


}