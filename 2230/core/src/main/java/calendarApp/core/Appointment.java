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
            throw new IllegalArgumentException("Given appointment name is not valid");
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
        if(!checkTimeFormat(startHour, stopHour, startMinute, stopMinute)){
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
        if (appointmentName.isBlank() || appointmentName.length() < 2){
            return false;
        }
        else if(!appointmentName.matches("^[a-zA-Z0-9]*$")){
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

    @Override
    public String toString() {
        String startHour = String.valueOf(this.startHour);
        String startMinute = String.valueOf(this.startMinute);
        String stopHour = String.valueOf(this.stopHour);
        String stopMinute = String.valueOf(this.stopMinute);

        if (startHour.length()<2) {startHour = "0" + startHour;}  
        if (startMinute.length()<2) {startMinute = "0" + startMinute;}
        if (stopHour.length()<2) {stopHour = "0" + stopHour;}  
        if (stopMinute.length()<2) {stopMinute = "0" + stopMinute;}

        return appointmentName + ": " + dayOfTheWeek.nameOfDay +" "+ startHour +":"+ startMinute +" - "+ stopHour +":"+stopMinute;
    }

    /*public static void main(String[] args) {
        Appointment a1 = new Appointment("test",DaysOfTheWeek.FRIDAY , 0, 0, 0, 0);
    }*/

}