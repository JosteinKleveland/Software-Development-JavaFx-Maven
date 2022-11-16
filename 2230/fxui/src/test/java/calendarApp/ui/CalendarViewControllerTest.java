package calendarApp.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

        this.appointment1 = new Appointment("Fotball", "Husk å ta med fotball", DaysOfTheWeek.MONDAY, 18, 19, 0, 30);
        this.appointment2 = new Appointment("Yoga", "Ved blåbærvegen 8", DaysOfTheWeek.WEDNESDAY, 17, 18, 0, 30);
        this.appointment3 = new Appointment("Trumpet", "Husk munnstykke", DaysOfTheWeek.MONDAY, 13, 14, 0, 0);
        
        // Note that the CalenarViewController takes in a Calendar from WelcomeViewController
        // Testing of json reading will be tested in WelcomeViewControllerTest
        calendar.addAppointment(appointment1);
        calendar.addAppointment(appointment2);
        calendar.addAppointment(appointment3);
        
        controller.initialize(calendar);
    }


    /**
     *  Tests if the Calendar actually contains the appointments in the setup
     */
    @Test
    public void testControllerSetup() {
        // Checks if the setup works with the controller and calendar
        assertNotNull(this.controller);
        assertNotNull(this.calendar);

        checkCalendarAppointments(this.controller.getData(), appointment1, appointment2, appointment3);
    }
    
    /**
     *  Test if the correct appointments are shown in the "chosen appointment section"
     */
    @Test
    public void testSetChosenAppointment() {

        // Tests if the first appointment automatically shows up in the "chosen appointment section"
        Label lblChosenAppointmentName = (Label) lookup("#lblChosenAppointmentName");
        Label lblChosenAppointmentDescription = (Label) lookup("#lblChosenAppointmentDescription");
        Label lblChosenAppointmentTime = (Label) lookup("#lblChosenAppointmentTime");

        String timeStringToCompare = appointment1.getStartHour()+":"+appointment1.getStartMinute()+" - "+ appointment1.getStopHour()+":"+ appointment1.getStopMinute();

        assertEquals(appointment1.getAppointmentName(), lblChosenAppointmentName.getText());
        assertEquals(appointment1.getAppointmentDescription(), lblChosenAppointmentDescription.getText());
        assertEquals(timeStringToCompare, lblChosenAppointmentTime.getText());

        // Tests if the second appointment shows up in the "chose appointment section" once it is clicked on
        clickOn("#1");
        lblChosenAppointmentName = (Label) lookup("#lblChosenAppointmentName");
        lblChosenAppointmentDescription = (Label) lookup("#lblChosenAppointmentDescription");
        lblChosenAppointmentTime = (Label) lookup("#lblChosenAppointmentTime");

        timeStringToCompare = appointment2.getStartHour()+":"+appointment2.getStartMinute()+" - "+ appointment2.getStopHour()+":"+ appointment2.getStopMinute();

        assertEquals(appointment2.getAppointmentName(), lblChosenAppointmentName.getText());
        assertEquals(appointment2.getAppointmentDescription(), lblChosenAppointmentDescription.getText());
        assertEquals(timeStringToCompare, lblChosenAppointmentTime.getText());
    }

    /**
     * Tests if the "delete appointment button" actually deletes the chosen appointment
     */
    @Test
    public void testDeleteAppointment() {
        // Clicks on the pane belonging to the second appointment
        // then on the "delete appointment" button
        // then on "Ok" once the Alert shows up
        clickOn("#1");
        clickOn("#deleteAppointment");
        clickOn("OK");

        // The updated calendar's appointments
        List<Appointment> appointments = this.controller.getData();
        // Checks if the remaining appointments are equal to exactly appointment1 and appointment3 
        checkCalendarAppointments(appointments, appointment1, appointment3);
    }




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
