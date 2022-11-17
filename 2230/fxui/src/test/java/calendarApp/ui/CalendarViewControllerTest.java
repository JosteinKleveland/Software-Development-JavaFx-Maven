package calendarApp.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.testfx.framework.junit5.ApplicationTest;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.core.DaysOfTheWeek;
import calendarApp.json.CalendarSaveHandler;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class CalendarViewControllerTest extends ApplicationTest {

    private CalendarViewController controller; // The CalendarViewController instance we perform the tests on
    private Calendar calendar; // The user's calendar
    private Appointment appointment1, appointment2, appointment3; // The appointments to compare with
    
    @Override
    public void start(final Stage stage) throws IOException {
      final FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
      final Parent root = loader.load();
      this.controller = loader.getController();
      stage.setScene(new Scene(root));
      stage.show();
    }

    /**
     * Sets up the CalendarViewController instance with a fresh calendar and appointments for new tests
     */
    @BeforeEach
    public void setupAppointments() {
        this.calendar = new Calendar("FreshCalendar");

        this.appointment1 = new Appointment("Fotball", "Husk å ta med fotball", DaysOfTheWeek.MONDAY, 1, 2, 15, 30);
        this.appointment2 = new Appointment("Yoga", "Ved blåbærvegen 8", DaysOfTheWeek.WEDNESDAY, 8, 9, 30, 30);
        this.appointment3 = new Appointment("Trumpet", "Husk munnstykke", DaysOfTheWeek.MONDAY, 8, 9, 30, 45);
        
        // Note that the CalenarViewController takes in a Calendar from WelcomeViewController
        // Testing of json reading will be tested in WelcomeViewControllerTest
        this.calendar.addAppointment(appointment1);
        this.calendar.addAppointment(appointment2);
        this.calendar.addAppointment(appointment3);
    
        Platform.runLater(() -> {
            controller.initialize(this.calendar); 
        });
    }


    @Test
    @DisplayName("Tests the calendar state of the controller")
    public void testControllerSetup() throws InterruptedException {

        Thread.sleep(100);

        // Checks if the setup works with the controller and calendar
        assertNotNull(this.controller);
        assertNotNull(this.calendar);

        // Tests if the Calendar actually contains the appointments in the setup
        checkCalendarAppointments(this.controller.getData(), appointment1, appointment2, appointment3);
    }
    

    @Test
    @DisplayName("Tests the display of selected appointments")
    public void testSetChosenAppointment() throws InterruptedException {

        Thread.sleep(100);


        // Tests if the first appointment automatically shows up in the "chosen appointment section"
        Label lblChosenAppointmentName = (Label) lookup("#lblChosenAppointmentName").query();
        Label lblChosenAppointmentDescription = (Label) lookup("#lblChosenAppointmentDescription").query();
        Label lblChosenAppointmentTime = (Label) lookup("#lblChosenAppointmentTime").query();

        String timeStringToCompare = "0" +appointment1.getStartHour()+":"+appointment1.getStartMinute()+" - "+ "0"+appointment1.getStopHour()+":"+ appointment1.getStopMinute();

        assertEquals(appointment1.getAppointmentName(), lblChosenAppointmentName.getText());
        assertEquals(appointment1.getAppointmentDescription(), lblChosenAppointmentDescription.getText());
        assertEquals(timeStringToCompare, lblChosenAppointmentTime.getText());
        

        // Tests if the second appointment shows up in the "chosen-appointment section" once it is clicked on
        Pane appointment2Pane = (Pane) lookup("#A1").query();
        clickOn(appointment2Pane);

        lblChosenAppointmentName = (Label) lookup("#lblChosenAppointmentName").query();
        lblChosenAppointmentDescription = (Label) lookup("#lblChosenAppointmentDescription").query();
        lblChosenAppointmentTime = (Label) lookup("#lblChosenAppointmentTime").query();

        timeStringToCompare = "0"+appointment2.getStartHour()+":"+appointment2.getStartMinute()+" - "+"0"+ appointment2.getStopHour()+":"+ appointment2.getStopMinute();

        assertEquals(appointment2.getAppointmentName(), lblChosenAppointmentName.getText());
        assertEquals(appointment2.getAppointmentDescription(), lblChosenAppointmentDescription.getText());
        assertEquals(timeStringToCompare, lblChosenAppointmentTime.getText());
    }

    @Test
    @DisplayName("Test change scene to MakeAppointment.fxml")
    public void testCreateAppointmentButton() throws InterruptedException {

        Thread.sleep(100);

        // Checks if an MakeAppointment.fxml element exists after pressing the create button
        clickOn("#btnNewAppointment");
        assertNotNull((ChoiceBox) lookup("#drdSetAppointmentDay").query());
    }

    @Test
    @DisplayName("Test exit")
    public void testExit() throws InterruptedException {

        Thread.sleep(100);

        // Checks if a WelcomeWindow.fxml element exists in the scene after pressing the exit button
        clickOn("#btnExitCalendar");
        assertNotNull((Button) lookup("#btnNewCalendar").query());
    }  

    @Test
    @DisplayName("Test deletion of the calendar")
    public void testDeleteCalendar() throws InterruptedException {

        //Creates CalendarLogicAccess and sets this so delete-test can be run
        CalendarLogic calendarLogic = new CalendarLogic(this.calendar);
        CalendarLogicAccess calendarLogicAccess = new DirectCalendarLogicAccess(calendarLogic);
        this.controller.setCalendarLogicAccess(calendarLogicAccess);

        Thread.sleep(100);
        CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();

        // Checks if "FreshCalendar.json" exists after pressing the delete button
        clickOn("#btnDeleteCalendar");
        clickOn("OK");
        assertFalse(calendarSaveHandler.checkIfFileExists("FreshCalendar"));
    }  


    @Test
    @DisplayName("Test deletion of selected appointment")
    public void testDeleteAppointment() throws InterruptedException {

        Thread.sleep(100);
        // Clicks on the pane belonging to the second appointment
        // then on the "delete appointment" button
        // then on "Ok" once the Alert shows up
        Pane appointment2Pane = (Pane) lookup("#A1").query();
        clickOn(appointment2Pane);
        clickOn("#btnDeleteAppointment");
        clickOn("OK");

        // The updated calendar's appointments
        List<Appointment> appointments = this.controller.getData();
        // Checks if the remaining appointments are equal to exactly appointment1 and appointment3 
        checkCalendarAppointments(appointments, appointment1, appointment3);
    }

    @AfterAll
    public static void cleanUp() {
        CalendarSaveHandler.delete("FreshCalendar");
    } 
    

    // Utility methods

    /**
     * Method that checks if an Appointment object has state variables equal to the respective arguments
     * The parameters represent all state variables of an appointment
     * @param appointment
     * @param appointmentName
     * @param appointmentDescription
     * @param dayOfTheWeek
     * @param startHour
     * @param stopHour
     * @param startMinute
     * @param stopMinute
     */
    private void checkCalendarAppointment(Appointment appointment, String appointmentName, String appointmentDescription, DaysOfTheWeek dayOfTheWeek, int startHour , int stopHour, int startMinute, int stopMinute) {
        if (appointmentName != null) {
            assertEquals(appointmentName, appointment.getAppointmentName());
        }
        if (appointmentDescription != null) {
            assertEquals(appointmentDescription, appointment.getAppointmentDescription());
        }
        if (dayOfTheWeek != null) {
            assertEquals(dayOfTheWeek, appointment.getDayOfTheWeek());
        }

        assertEquals(startHour, appointment.getStartHour());
        assertEquals(stopHour, appointment.getStopHour());
        assertEquals(startMinute, appointment.getStartMinute());
        assertEquals(stopMinute, appointment.getStopMinute());

    }


    /**
     * Method that takes a list of Appointment objects and compares them to a given set of specific Appointment objects
     * Note that the sequence they are compared in must be respected when using the method
     * @param list of an Iterable object containing Appointments
     * @param appointments a set of specific Appointment objects to compare with
     */
    private void checkCalendarAppointments(Iterable<Appointment> list, Appointment... appointments) {
        int i = 0;
        for (Appointment appointment : list) {
            assertTrue(i < appointments.length);
            checkCalendarAppointment(appointment, appointments[i].getAppointmentName(), appointments[i].getAppointmentDescription(), appointments[i].getDayOfTheWeek(), appointments[i].getStartHour(), appointments[i].getStopHour(), appointments[i].getStartMinute(), appointments[i].getStopMinute());
            i++;
        }
        assertTrue(i == appointments.length);
    }
}
