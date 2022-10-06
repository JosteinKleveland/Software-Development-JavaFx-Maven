package calendarApp.core;

public enum DaysOfTheWeek {
    //Defining the 7 days of the week.
    MONDAY("Monday"),
    TUESDAY("Tuesday"), 
    WEDENSDAY("Wedensday"), 
    THURSDAY("Thursday"), 
    FRIDAY("Friday"), 
    SATURDAY("Saturday"), 
    SUNDAY("Sunday");


    final String nameOfDay;

    DaysOfTheWeek (String nameOfDay){
        this.nameOfDay = nameOfDay;
    }
}