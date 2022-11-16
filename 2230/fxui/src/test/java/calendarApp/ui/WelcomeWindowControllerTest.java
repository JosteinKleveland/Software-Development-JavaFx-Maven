package calendarApp.ui;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.testfx.framework.junit5.ApplicationTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;
import calendarApp.json.CalendarSaveHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class WelcomeWindowControllerTest extends ApplicationTest {

    private WelcomeWindowController controller; // The CalendarViewController instance we perform the tests on
    private CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();
    
    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeWindow.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }


    // Prepares the tests with the creation/deletion of respective json files
    @BeforeEach
    public void cleanAndPrepareTests() throws JsonProcessingException, IOException {

        // Deletes a potential "TestNewCalendar.json", to prepare for testCreateCalendar & testCreateCalendarFeedback
        if (calendarSaveHandler.checkIfFileExists("TestNewCalendar"))
            CalendarSaveHandler.delete("TestNewCalendar");
        
        // Creates and saves a new "TestExistingCalendar.json" file
        String calendarName = "TestExistingCalendar";
        if (calendarSaveHandler.checkIfFileExists(calendarName))
            CalendarSaveHandler.delete(calendarName);
        CalendarSaveHandler.save(new Calendar(calendarName));  
    } 


    @Test
    @DisplayName("Test creation of a calendar")
    public void testCreateCalendar() {

        clickOn("#txtCalendarNameInput").write("TestNewCalendar");
        clickOn("#btnNewCalendar");

        assertNotNull(controller.getCalendar()); // Checks if a new Calendar object was created
        assertEquals("TestNewCalendar", controller.getCalendar().getCalendarName()); // Checks if the new Calendar has the correct name
        assertEquals(0, controller.getCalendar().getAppointments().size()); // Checks if the new Calendar has 0 appointments
    }


    @Test
    @DisplayName("Test feedback on creating a calendar")
    public void testCreateCalendarFeedback() throws JsonProcessingException, IOException {
        
        CalendarSaveHandler.save(new Calendar("TestNewCalendar")); 

        clickOn("#txtCalendarNameInput").write("TestNewCalendar");
        clickOn("#btnNewCalendar");

        Label feedback = (Label) lookup("#lblFeedback").query();
        assertEquals("Calendar name already exists, choose another", feedback.getText());
    }



    @Test
    @DisplayName("Test loading of an existing calendar")
    public void testLoadCalendar() {

        String calendarName = "TestExistingCalendar"; 

        clickOn("#txtCalendarNameInput").write(calendarName);
        clickOn("#btnLoadCalendar");

        assertEquals(calendarName, controller.getCalendar().getCalendarName());
    }


    @Test
    @DisplayName("Test feedback on 'load calendar'")
    public void testLoadCalendarFeedback() {

        String calendarName = "DoesNotExist"; 

        // If DoesNotExist.json exists, deletes it, in preperation for the test
        if (calendarSaveHandler.checkIfFileExists(calendarName))
            CalendarSaveHandler.delete(calendarName);

        clickOn("#txtCalendarNameInput").write(calendarName);
        clickOn("#btnLoadCalendar");

        Label feedback = (Label) lookup("#lblFeedback").query();
        assertEquals("Calendar name does not exists", feedback.getText());
    }


    @AfterAll
    public static void cleanUp() {
        CalendarSaveHandler.delete("TestExistingCalendar");
    }
}