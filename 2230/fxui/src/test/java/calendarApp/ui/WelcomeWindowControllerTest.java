package calendarApp.ui;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;
import calendarApp.json.CalendarSaveHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WelcomeWindowControllerTest extends ApplicationTest {

    private WelcomeWindowController controller; // The CalendarViewController instance we perform the tests on
    private Calendar calendarLoaded;
    private CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();
    
    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeWindow.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void cleanAndPrepareTests() throws JsonProcessingException, IOException {
        // Deletes a potential "TestNewCalendar.json", to prepare for testCreateCalendar & testCreateCalendarFeedback
        if (calendarSaveHandler.checkIfFileExists("TestNewCalendar"))
            CalendarSaveHandler.delete("TestNewCalendar");
        
        // Creates and saves a new "TestExistingCalendar.json" file
        String calendarName = "TestExistingCalendar";
        if (calendarSaveHandler.checkIfFileExists(calendarName))
            CalendarSaveHandler.delete(calendarName);
        calendarLoaded = new Calendar(calendarName);
        CalendarSaveHandler.save(calendarLoaded);  
    } 

    @Test
    public void testCreateCalendar() {
        clickOn("#txtCalendarNameInput").write("TestNewCalendar");
        clickOn("#btnNewCalendar");

        assertNotNull(controller.getCalendar()); // Checks if a new Calendar object was created
        assertEquals("TestNewCalendar", controller.getCalendar().getCalendarName()); // Checks if the new Calendar has the correct name
        assertEquals(0, controller.getCalendar().getAppointments().size()); // Checks if the new Calendar has 0 appointments
    }

    @Test
    public void testCreateCalendarFeedback() {

    }

    @Test
    public void testLoadCalendar() throws JsonProcessingException, IOException {

        String calendarName = "TestExistingCalendar";  
        clickOn("#txtCalendarNameInput").write(calendarName);
        clickOn("#btnLoadCalendar");
        
        assertEquals(calendarName, controller.getCalendar().getCalendarName());
    }

    @Test
    public void testLoadCalendarFeedback() {
        
        clickOn("#txtCalendarNameInput").write();
    }
}