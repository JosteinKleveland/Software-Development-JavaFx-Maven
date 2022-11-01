package calendarApp.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalendarLogicTest {
    
    @Test
    @DisplayName("Check that appointment that collide with existing appointment cannot be added to calendar")
    public void checkCollisionTest() {
        CalendarLogic calendarlogic = new CalendarLogic();
        Calendar calendar = new Calendar("calendar");
        
        Appointment a1 = new Appointment("Football", "test description", DaysOfTheWeek.WEDNESDAY, 10, 12, 30, 30);
        Appointment a2 = new Appointment("Tennis", "test description", DaysOfTheWeek.WEDNESDAY, 12, 14, 30, 30);
        Appointment a3 = new Appointment("Golf", "test description", DaysOfTheWeek.FRIDAY, 15, 17, 0, 0);
        Appointment a4 = new Appointment("Formula1", "test description", DaysOfTheWeek.FRIDAY, 16, 18, 30, 0);

        calendarlogic.addAppointmentToCalendar(calendar, a1);
        calendarlogic.addAppointmentToCalendar(calendar, a2);
        calendarlogic.addAppointmentToCalendar(calendar, a3);        
        assertThrows(IllegalArgumentException.class, () -> calendarlogic.addAppointmentToCalendar(calendar, a4));

    }
}
