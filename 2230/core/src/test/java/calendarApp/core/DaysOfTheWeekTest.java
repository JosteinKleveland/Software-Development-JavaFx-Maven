package calendarApp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DaysOfTheWeekTest {
   
    //Testing the ENUM-class may be a bit unnecessary, but it could for example be done by checking whether the values-input match the ENUMS.
    @Test
    @DisplayName("Check ENUMs value")
    public void checkValueOfEnumsTest() {      
        assertEquals("MONDAY", DaysOfTheWeek.MONDAY.name());
        assertNotEquals("Monday", DaysOfTheWeek.MONDAY.name());
    }
   

}
