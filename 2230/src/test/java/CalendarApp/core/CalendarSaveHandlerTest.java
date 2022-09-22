package CalendarApp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class CalendarSaveHandlerTest {


    private final static String SAVE_FOLDER = "/workspace/gr2230/2230/src/main/java/CalendarApp/core/savedCalendars/";
    private static String testCalendarName = "testCalendar";
    private static String filePath = SAVE_FOLDER + testCalendarName + ".json";
    
    //Check if testCalendar exsists, and delete it if so
    @BeforeEach
    public static void testSetup(){
        File file = new File(filePath);
        if(file.isFile()) {
            file.delete();
        }
        assertFalse(file.isFile());
    }

    @Test
    @DisplayName("Check that correct file path is returned")
    public static void getFilePathTest(){
        assertEquals("/workspace/gr2230/2230/src/main/java/CalendarApp/core/savedCalendars/testCalendar.json", CalendarSaveHandler.getFilePath(testCalendarName));
    }    

    @Test
    @DisplayName("Check that calendar is created and saved") 
    public static void SaveCalendarTest(){
        // Create and save a new calendar
        try {
            CalendarSaveHandler.save(testCalendarName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Check if new file was created
        File file = new File(filePath);
        assertTrue(file.exists());
    }


}
