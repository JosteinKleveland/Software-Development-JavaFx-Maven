package calendarApp.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.matchers.Each;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarViewControllerTest extends ApplicationTest {

    private CalendarViewController controller;
    private Calendar calendar;
    
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
        this.calendar = new Calendar("New Calendar");

        Appointment appointment1 = new Appointment("Fotball", "Husk å ta med fotball", DaysOfTheWeek.MONDAY, 18, 19, 0, 30);
        Appointment appointment2 = new Appointment("Yoga", "Ved blåbærvegen 8", DaysOfTheWeek.WEDNESDAY, 17, 18, 0, 30);
        Appointment appointment3 = new Appointment("Trumpet", "Husk munnstykke", DaysOfTheWeek.MONDAY, 13, 14, 0, 0);
        
        calendar.addAppointment(appointment1);
        calendar.addAppointment(appointment2);
        calendar.addAppointment(appointment3);

        controller.initialize(calendar);
    }

        /**
     *  Tests if the Calendar actually contains the appointments in the setup
     */
    @Test
    public void testController_appointmentsSetup() {
        // Checks if the setup works with the controller and calendar
        assertNotNull(this.controller);
        assertNotNull(this.calendar);


    }
    
    @Test
    public void testSetChosenAppointment() {


    }

    
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

    private void checkCalendarAppointments(Iterable<Appointment> list, Appointment... appointments) {
        int i = 0;
        for (Appointment appointment : appointments) {
            assertTrue(i < appointments.length);
            checkCalendarAppointment(appointment, appointments[i].getAppointmentName(), appointments[i].getAppointmentDescription(), appointments[i].getDayOfTheWeek(), appointments[i].getStartHour(), appointments[i].getStopHour(), appointments[i].getStartMinute(), appointments[i].getStopMinute());
            i++;
        }
        assertTrue(i == appointments.length);
    }
}
