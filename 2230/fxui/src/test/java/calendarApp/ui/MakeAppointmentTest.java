package calendarApp.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.application.Platform;


public class MakeAppointmentTest extends ApplicationTest{


    private MakeAppointmentController controller; // The MakeAppointmentController instance we perform the tests on
    private Calendar calendar; // The user's calendar
    private Appointment appointment; // The appointment to edit

    @Override
    public void start(final Stage stage) throws Exception {
      final FXMLLoader loader = new FXMLLoader(getClass().getResource("MakeAppointment.fxml"));
      final Parent root = loader.load();
      this.controller = loader.getController();
      stage.setScene(new Scene(root));
      stage.show();
    }

    /**
     * Sets up the MakeAppointmentController instance with a fresh calendar and appointment for new tests
     */
    @BeforeEach
    public void setup() {
        this.calendar = new Calendar("TestCalendar");
        this.appointment = new Appointment("Fotball", "Husk å ta med fotball", DaysOfTheWeek.SATURDAY, 18, 19, 0, 30);
        this.calendar.addAppointment(appointment);
      }
    
}
