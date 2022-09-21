package CalendarApp.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CalendarSaveHandlerTest {


    public final static String SAVE_FOLDER = "/workspace/gr2230/2230/src/main/java/CalendarApp/core/savedCalendars/";

    @Test
    @DisplayName("Check that calendar is created and saved") 

    public static void SaveCalendarTest() throws FileNotFoundException{
        //Check if testCalendar exist, and delete it if it exists
        String filePath = SAVE_FOLDER + "testCalendar.json";
        File file = new File(filePath);
        if(file.isFile()) {
            file.delete();
        }
        assertFalse(file.exists());
  
        // Create and save a new calendar
        CalendarSaveHandler.save("testCalendar");

        // Check if new file was created
        File newFile = new File(filePath);
        assertTrue(newFile.exists());

    }



}
