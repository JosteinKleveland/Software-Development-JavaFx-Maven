package calendarApp.core;

/**
 * Core data of appointments
 */
public class Appointment {

    private String appointmentName; // name of the appointment
    private String appointmentDescription; // description of the appointment
    private DaysOfTheWeek dayOfTheWeek; // day of the assignment
    private int startHour; // hour of the day when the appointment starts
    private int startMinute; // minute of the day when the appointment starts
    private int stopHour; // hour of the day when the appointment ends
    private int stopMinute; // minute of the day when the appointment ends
    

    /**
     * Constructor that initializes an Appointment and sets the respected variabels from the respected arguments
     * @param appointmentName
     * @param appointmentDescription
     * @param dayOfTheWeek
     * @param startHour
     * @param stopHour
     * @param startMinute
     * @param stopMinute
     */
    public Appointment(String appointmentName, String appointmentDescription, DaysOfTheWeek dayOfTheWeek, int startHour , int stopHour, int startMinute, int stopMinute) {
        setAppointmentName(appointmentName);
        setAppointmentDescription(appointmentDescription);
        setDayOfTheWeek(dayOfTheWeek);;
        setTime(startHour, stopHour, startMinute, stopMinute);
    }

    /**
     * Sets the appointment name
     * @param appointmentName
     * @throws IllegalArgumentException if the argument is not in a correct format
     */
    private void setAppointmentName(String appointmentName) {
        if (!checkAppointmentName(appointmentName)){
            throw new IllegalArgumentException("Given appointment name is not valid");
        }
        this.appointmentName = appointmentName;
    }

    /**
     * Sets the appointment description
     * @param appointmentDescription
     * @throws IllegalArgumentException if the description exceeds the limit of 250 characters 
     */
    private void setAppointmentDescription(String appointmentDescription) {
        if(appointmentDescription.length() > 250) {
            throw new IllegalArgumentException("Given description exceeds the limit of 250 characters");
        }
        this.appointmentDescription = appointmentDescription;
    }
    
    /**
     * Sets the day of the appointment
     * @param dayOfTheWeek
     * @throws IllegalArgumentException if the day provided in the argument is not valid
     */
    private void setDayOfTheWeek(DaysOfTheWeek dayOfTheWeek) {
        if(!checkDayOfTheWeek(dayOfTheWeek)){
            throw new IllegalArgumentException("Input for day is not valid");
        }

        this.dayOfTheWeek = dayOfTheWeek;
    }

    /**
     * Sets the time of the appointment
     * @param startHour
     * @param stopHour
     * @param startMinute
     * @param stopMinute
     * @throws IllegalArgumentException if the times provded is not on the correct format
     */
    private void setTime(int startHour, int stopHour, int startMinute, int stopMinute){
        if(!checkTimeFormat(startHour, stopHour, startMinute, stopMinute)){
            throw new IllegalArgumentException("Time does not have the correct format");
        }
        this.startHour = startHour;
        this.stopHour = stopHour;
        this.startMinute = startMinute;
        this.stopMinute = stopMinute;
    }

    //Getters

    public String getAppointmentName() {
        return appointmentName;
    }
    

    public String getAppointmentDescription() {
        return appointmentDescription;
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

    /**
     * Helper function
     * Checks the validity of the provided time.
     * @param startHour
     * @param stopHour
     * @param startMinute
     * @param stopMinute
     * @return true if the time is valid and false if not
     */
    private boolean checkTimeFormat(int startHour,int stopHour,int startMinute,int stopMinute){
       
        // Check that an event follows digital clock norms
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

        else if (startMinute % 15 != 0 || stopMinute % 15 !=0) {
            return false;
        }

        return true;
    }


    /**
     * Helper function
     * Checks if an appointment name is on the correct format
     * @param appointmentName
     * @return true if the appointmentName is on a correct format and false otherwise
     */
    private boolean checkAppointmentName(String appointmentName){

        //If the calendar name is empty or has a length less than 2, return false
        if (appointmentName.isBlank() || appointmentName.length() < 2){
            return false;
        }

        //If the calendar name has any special characters, return false
        else if(!appointmentName.matches("^[a-zA-Z0-9]*$")){
            return false;
        }
        return true;
    }


    /**
     * Helper function
     * Checks if a day is part of the enum class
     * @param day
     * @return true if it is part of the enum class and false if not
     */
    private boolean checkDayOfTheWeek(DaysOfTheWeek day){
        if (!day.getClass().isEnum()){
            return false;
        }
        return true;
    }

    /**
     * @return a String consisting of the appointment's details in a readable format
     */
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