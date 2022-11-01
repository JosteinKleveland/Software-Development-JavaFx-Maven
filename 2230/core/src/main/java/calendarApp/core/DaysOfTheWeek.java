package calendarApp.core;

/**
 * Enum of the weekdays to ensure consistensy in the application
 */
public enum DaysOfTheWeek {
    //Defining the 7 days of the week.
    MONDAY("Monday"),
    TUESDAY("Tuesday"), 
    WEDNESDAY("Wednesday"), 
    THURSDAY("Thursday"), 
    FRIDAY("Friday"), 
    SATURDAY("Saturday"), 
    SUNDAY("Sunday");


    protected final String nameOfDay;

    private DaysOfTheWeek (String nameOfDay){
        this.nameOfDay = nameOfDay;
    }

    public static void main(String[] args) {
        System.out.println(MONDAY.toString());
    }
}