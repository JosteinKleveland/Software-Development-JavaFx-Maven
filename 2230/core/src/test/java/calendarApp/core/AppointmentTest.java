package calendarApp.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


public class AppointmentTest {

    //To test our different methods for error-checking which are private, we will run input throught the public constructor
    //To do this we will run invalid input for the parameter we are checking
    //We have checks for enum, timeformat and name-setting.
    @Test
    @DisplayName("Check that appointmentName-requirements are followed")
    public void checkAppointmentNameTest() throws IllegalArgumentException {
        //Shall fail if name is shorter than 2 characters, empty, or contains invalid characters

        assertThrows(IllegalArgumentException.class, () -> new Appointment("", DaysOfTheWeek.FRIDAY, 10, 11, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Appointment("e", DaysOfTheWeek.FRIDAY, 10, 11, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Appointment("/*//", DaysOfTheWeek.FRIDAY, 10, 11, 0, 0));
    }

    @Test
    @DisplayName("Check that DayOfTheWeek-input is ENUM and valid")
    public void checkDayOfTheWeekTest() throws IllegalArgumentException {
        //Shall fail if input is not enum, or not defined in class "DaysOfTheWeek"
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.valueOf("Monday"), 10, 11, 0, 0));
    }

    @Test
    @DisplayName("Check that input-hours- and minutes is valid")
    public void checkTimeFormatTest() throws IllegalArgumentException {
        //Shall throw exception if:
        //1. Start- and/or stop-hour is less than 0:
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, -5, 11, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 5, -11, 0, 0));
        //2. Start- and/or stop-hour is larger than 23:
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 40, 11, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 5, 35, 0, 0));
        //3. Start- and/or stop-minute is less than 0:
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 5, 11, -5, 0));
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 5, 11, 0, -5));
        //4. Start- and/or stop-minute is larger than 59:
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 5, 11, 60, 0));
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 5, 11, 0, 60));
        //5. Start-time is later than stop-time for appointment:
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 10, 9, 15, 0));
        assertThrows(IllegalArgumentException.class, () -> new Appointment("calendarName", DaysOfTheWeek.FRIDAY, 4, 4, 45, 30));

    }

}
