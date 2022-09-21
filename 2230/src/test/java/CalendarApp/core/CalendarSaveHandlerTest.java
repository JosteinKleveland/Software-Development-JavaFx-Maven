package CalendarApp.core;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CalendarSaveHandlerTest {


    @Test
    @DisplayName("Check that calendar is created and saved") 

    public static void SaveCalendarTest() throws FileNotFoundException{
        //Check if testCalendar exist, and delete it if it exists
            File fileOld = new File("/workspace/gr2230/2230/src/main/java/CalendarApp/core/savedCalendars/testCalendar.json");
        try{
            if (fileOld.exists()){
                fileOld.delete();
            }
        }
        finally{

        }   
  
        // Create and save a new calendar
        CalendarSaveHandler.save("testCalendar");
            File fileNew = new File("/workspace/gr2230/2230/src/main/java/CalendarApp/core/savedCalendars/testCalendar.json");

        // Check if new file was created
        assertTrue(fileNew.exists());

    }



}
