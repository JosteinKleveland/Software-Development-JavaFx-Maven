package calendarApp.ui;

import java.io.IOException;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.core.DaysOfTheWeek;
import calendarApp.json.CalendarSaveHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

// Handles the making of new appointments, as well as editing of existing ones
public class MakeAppointmentController {
    
    // FXML elements
    @FXML TextField txtAppointmentName;
    @FXML TextField txtSetStartTime;
    @FXML TextField txtSetStopTime;
    @FXML TextArea txtSetAppointmentDescription;

    @FXML ChoiceBox drdSetAppointmentDay;

    @FXML Button btnSaveAppointment;
    @FXML Button btnCancelEdit;

    @FXML Label txtFeedbackEdit;

    // State variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    private CalendarViewController calendarViewController; // the controller which belongs to the fxml
    // that the app will change scene to when this scene is finish
    private CalendarLogic calendarLogic; // the CalendarLogic object which contains the respective calendar of the user

    private boolean inEditMode; // true if the user is editing an appointment, and false otherwise
    private Appointment editAppointment; // the appointment being edited, can be null


    /**
     * Sets up the state of the class
     * @param calendarViewController a reference to the CalendarViewController used in the application
     * @param calendarLogic the CalendarLogic used in the application
     * @param inEditMode flags if the user is in edit mode, or simply creates a new appointment
     * @param editAppointment the potential Appointment in edit mode
     */
    protected void intialize(CalendarViewController calendarViewController, CalendarLogic calendarLogic, boolean inEditMode, Appointment editAppointment) {
        this.calendarViewController = calendarViewController;
        this.calendarLogic = calendarLogic;
        this.inEditMode = inEditMode;
        if (inEditMode)
            this.editAppointment = editAppointment;
    }
    
    
    /**
     * Makes a new Appointment in the Calendar and if in edit mode - deletes the previous one
     * @param event
     * @throws IOException
     */
    public void makeAppointment(ActionEvent event) throws IOException {
    
        String appointmentName = txtAppointmentName.getText();
        String appointmentDescription = "";
        String weekDay = (String) drdSetAppointmentDay.getValue();
        int[] startTime = decodeClock(txtSetStartTime.getText());
        int[] stopTime = decodeClock(txtSetStopTime.getText());
        int startHour = startTime[0];
        int startMinute = startTime[1];
        int stopHour = stopTime[0];
        int stopMinute = stopTime[1];
        
        Calendar currentCalendar = this.calendarLogic.getCurrentCalendar();

        // If we are in editing mode (in practice came from "ViewAppointment.fxml"), then remove the current appointment
        // before creating a new one with the desired attributes
        if (inEditMode) {
            currentCalendar.removeAppointment(editAppointment);
        }
        currentCalendar.addAppointment(new Appointment(appointmentName, appointmentDescription, DaysOfTheWeek.valueOf(weekDay), startHour, stopHour, startMinute, stopMinute));

        // Saves the updated version of the calendar in the respective JSON file
        CalendarSaveHandler.save(currentCalendar);

        // Changes back to the calendar view with the updated view
        String nextScene = "CalendarView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        this.root = loader.load();
        this.calendarViewController.viewCalendar(currentCalendar);
        changeScene(event, this.root, nextScene);
    }

    /**
     * Switches scene to the calendar view 
     * @param event
     * @throws IOException
     */
    public void exitView(ActionEvent event) throws IOException{
        String nextScene = "CalendarView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        this.root = loader.load();
        changeScene(event, this.root, nextScene);
    }

    //Helper method that decodes clock of the format 00:00
    //@returns an int array with 2 memory places [0] -> int hour, [1] -> int minute
    public int[] decodeClock(String time) {
        int[] timeIntArray = new int[2];
        String[] timeStringArray = time.split(":",2);
        String hourString = timeStringArray[0];
        String minuteString = timeStringArray[1];

        if (hourString.charAt(0) == '0') {
            hourString = Character.toString(hourString.charAt(1));
        }
        if (minuteString.charAt(0) == '0') {
            minuteString = Character.toString(minuteString.charAt(1));
        }

        timeIntArray[0] = Integer.parseInt(hourString);
        timeIntArray[1] = Integer.parseInt(minuteString);

        return timeIntArray;
    }


     //Method for switching between scenes
     private void changeScene(ActionEvent event, Parent root, String sceneName) throws IOException{
        stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
