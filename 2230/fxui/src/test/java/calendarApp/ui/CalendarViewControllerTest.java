package calendarApp.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

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

    private CalendarViewController controller;
    private Calendar calendar;
    private Appointment appointment1, appointment2, appointment3;
    
    @Override
    public void start(final Stage stage) throws Exception {
      final FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarViewController.fxml"));
      final Parent root = loader.load();
      this.controller = loader.getController();
      stage.setScene(new Scene(root));
      stage.show();
    }

    @BeforeEach
    public void setupAppointments() throws IOException {

        this.calendar = new Calendar("Fresh Calendar");

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

        checkCalendarAppointments(this.calendar.getAppointments(), appointment1, appointment2, appointment3);
    }
    
    /**
     *  
     */
    @Test
    public void testSetChosenAppointment() {
        // Clicks on the first appointment pane
        clickOn("#0");
        Label lblChosenAppointmentName = (Label) lookup("#lblChosenAppointmentName");
        Label lblChosenAppointmentDescription = (Label) lookup("#lblChosenAppointmentDescription");
        Label lblChosenAppointmentTime = (Label) lookup("#lblChosenAppointmentTime");

        String timeStringToCompare = appointment1.getStartHour()+":"+appointment1.getStartMinute()+" - "+ appointment1.getStopHour()+":"+ appointment1.getStopMinute();

        assertEquals(appointment1.getAppointmentName(), lblChosenAppointmentName.getText());
        assertEquals(appointment1.getAppointmentDescription(), lblChosenAppointmentDescription.getText());
        assertEquals(timeStringToCompare, lblChosenAppointmentTime.getText());
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
