package calendarApp.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalendarTest {
    @BeforeEach
    public void testSetup() {
        Calendar calendar = new Calendar("testCalendar");
        assertTrue(calendar.getAppointments().size() == 0);
    }

    @Test
    @DisplayName("Check that invalid calendarNames throws exceptions")
    public void setCalendarNameTest() {
        assertThrows(IllegalArgumentException.class, () -> new Calendar(""));
        assertThrows(IllegalArgumentException.class, () -> new Calendar("e"));
        assertThrows(IllegalArgumentException.class, () -> new Calendar("/*/"));
    }

    @Test
    @DisplayName("Check that appointments are correctly added to, and removed from list")
    public void addAppointmentTest() {
        Appointment a1 = new Appointment("Football", DaysOfTheWeek.TUESDAY, 20, 22, 0, 0);
        Appointment a2 = new Appointment("Tennis", DaysOfTheWeek.FRIDAY, 14, 15, 0, 30);
        
        Calendar calendar = new Calendar("testCalendar");
        calendar.addAppointment(a1);
        assertTrue(calendar.getAppointments().size() == 1);
        calendar.addAppointment(a2);
        assertTrue(calendar.getAppointments().size() == 2);
        calendar.removeAppointment(a2);
        assertTrue(calendar.getAppointments().size() == 1);
        }

    
}
