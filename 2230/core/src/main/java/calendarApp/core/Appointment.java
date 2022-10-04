package calendarApp.core;

public class Appointment {

    private String appointmentName;
    private DaysOfTheWeek dayOfTheWeek;
    private int startHour;
    private int startMinute;
    private int stopHour;
    private int stopMinute;
    
    public Appointment(String appointmentName, DaysOfTheWeek dayOfTheWeek, int startHour , int stopHour, int startMinute, int stopMinute) {
        this.appointmentName = appointmentName;
        this.dayOfTheWeek = dayOfTheWeek;
        setTime(startHour, stopHour, startMinute, stopMinute);
    }
    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }
    public void setDayOfTheWeek(DaysOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public void setTime(int startHour, int stopHour, int startMinute, int stopMinute){
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

}