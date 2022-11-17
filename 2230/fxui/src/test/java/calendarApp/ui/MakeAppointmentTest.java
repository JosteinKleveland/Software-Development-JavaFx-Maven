package calendarApp.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;
import calendarApp.json.CalendarSaveHandler;
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

      @DisplayName("Check that all fields get correct value when opening in editmode")
    @Test
    public void testFieldContentTest() throws InterruptedException{

      Platform.runLater(() -> {
        controller.intialize(this.calendar, true, appointment); 
        });

      Thread.sleep(100);

      assertEquals(true,controller.getInEditmBoolean());

      TextField txtAppointmentName = (TextField) lookup("#txtAppointmentName").query();
      TextField txtSetStartTime = (TextField) lookup("#txtSetStartTime").query();
      TextField txtSetStopTime = (TextField) lookup("#txtSetStopTime").query();
      TextArea txtSetAppointmentDescription = (TextArea) lookup("#txtSetAppointmentDescription").query();
      ChoiceBox drdSetAppointmentDay = (ChoiceBox) lookup("#drdSetAppointmentDay").query();

      assertEquals(appointment.getAppointmentName(),txtAppointmentName.getText());
      assertEquals(Integer.toString(appointment.getStartHour()) +":"+"0"+ Integer.toString(appointment.getStartMinute()),txtSetStartTime.getText());
      assertEquals(Integer.toString(appointment.getStopHour()) +":"+ Integer.toString(appointment.getStopMinute()),txtSetStopTime.getText());
      assertEquals(appointment.getAppointmentDescription(), txtSetAppointmentDescription.getText());
      assertEquals(appointment.getDayOfTheWeek().toString(),drdSetAppointmentDay.getValue().toString().toUpperCase());
      }
      
      
    @DisplayName("Create new appointment")
    @Test
    public void makeAppointmentTest() throws IOException, InterruptedException {
      Platform.runLater(() -> {
      controller.intialize(this.calendar, false, null); 
      });

      Thread.sleep(100);

      assertEquals(false,controller.getInEditmBoolean());

      String appointmentName = "Yoga";
      String startTime = "10:30";
      String stopTime = "12:00";
      String appointmentDescription = "Relaxing morning yoga";
      DaysOfTheWeek day = DaysOfTheWeek.SATURDAY;
      
      clickOn("#drdSetAppointmentDay");
      type(KeyCode.DOWN);
      type(KeyCode.DOWN);
      type(KeyCode.DOWN);
      type(KeyCode.DOWN);
      type(KeyCode.DOWN);
      type(KeyCode.ENTER);
      
      clickOn("#txtAppointmentName").write(appointmentName);
      clickOn("#txtSetStartTime").write(startTime);
      clickOn("#txtSetStopTime").write(stopTime);
      clickOn("#txtSetAppointmentDescription").write(appointmentDescription);

      //Check that a new appointment is added to the calendar when save button is pressed
      assertEquals(calendar.getAppointments().size(), 1);
      clickOn("#btnSaveAppointment");
      assertEquals(calendar.getAppointments().size(), 2);

      //Check that new appointment corresponds to given data
      assertEquals(calendar.getAppointments().get(1).getAppointmentName(), "Yoga");
      assertEquals(calendar.getAppointments().get(1).getStartHour(), 10);
      assertEquals(calendar.getAppointments().get(1).getStartMinute(), 30);
      assertEquals(calendar.getAppointments().get(1).getStopHour(), 12);
      assertEquals(calendar.getAppointments().get(1).getStopMinute(), 0);
      assertEquals(appointment.getDayOfTheWeek().toString(),day.toString().toUpperCase());
        
    }

    @DisplayName("Not possible to create appointments with illegal format")
    @Test
    public void makeIllegalAppointmentTest() throws IOException {

      Platform.runLater(() -> {
        controller.intialize(this.calendar, false, null); 
        });
                  
        String appointmentName = "Yoga";
        String startTime = "12:55";
        String stopTime = "12:00";
        String appointmentDescription = "Relaxing morning yoga";
        
        clickOn("#drdSetAppointmentDay");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        
        clickOn("#txtAppointmentName").write(appointmentName);
        clickOn("#txtSetStartTime").write(startTime);
        clickOn("#txtSetStopTime").write(stopTime);
        clickOn("#txtSetAppointmentDescription").write(appointmentDescription);

        clickOn("#btnSaveAppointment");

        Label txtFeedbackEdit = (Label) lookup("#txtFeedbackEdit").query();

        assertFalse(txtFeedbackEdit.getText()=="");
    }

    @DisplayName("Edit existing appointment")
    @Test
    public void editAppointmentTest() throws IOException, InterruptedException {
      Platform.runLater(() -> {
        controller.intialize(this.calendar, true, appointment); 
        });
    
        Thread.sleep(100);

        assertEquals(true,controller.getInEditmBoolean());

        TextField txtAppointmentName = (TextField) lookup("#txtAppointmentName").query();
        TextField txtSetStartTime = (TextField) lookup("#txtSetStartTime").query();
        TextField txtSetStopTime = (TextField) lookup("#txtSetStopTime").query();
        ChoiceBox drdSetAppointmentDay = (ChoiceBox) lookup("#drdSetAppointmentDay").query();

      //Making changes to the appointment

        clickOn("#drdSetAppointmentDay");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        String newAppointmentName = "Fotballtrening";
        String newStartTime = "16:30";
        String newStopTime = "20:30";

        txtAppointmentName.setText("");
        txtSetStartTime.setText("");
        txtSetStopTime.setText("");

        clickOn("#txtAppointmentName").write(newAppointmentName);
        clickOn("#txtSetStartTime").write(newStartTime);
        clickOn("#txtSetStopTime").write(newStopTime);
        clickOn("#btnSaveAppointment");

        //Check that new changes are made

        Appointment newAppointment = calendar.getAppointments().get(0);
        assertEquals(newAppointmentName, newAppointment.getAppointmentName());
        assertEquals(newStartTime,Integer.toString(newAppointment.getStartHour()) +":"+ Integer.toString(newAppointment.getStartMinute()));
        assertEquals("TUESDAY",drdSetAppointmentDay.getValue().toString().toUpperCase());

    }

    @AfterAll
    public static void cleanUp() {
        CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();
        if (calendarSaveHandler.checkIfFileExists("TestCalendar")) {
            CalendarSaveHandler.delete("TestCalendar");
        }
    }
}
