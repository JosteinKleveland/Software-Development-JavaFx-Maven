package calendarApp.ui;

import java.io.IOException;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.json.CalendarSaveHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

// Manages the view of the appointment, going back to the calendar view, or editing the appointment
public class ViewAppointmentController {
    
    // FXML elements
    @FXML private Label lblAppointmentName;
    @FXML private Label lblPreviewStartTime;
    @FXML private Label lblPreviewStopTime;
    @FXML private Label lblAppointmentDescription;

    @FXML private Button btnEditAppointment;
    @FXML private Button btnDeleteAppointment;

    // State variables
    private CalendarViewController calendarViewController; // a reference to the controller that activated this one
    private Appointment appointmentInView; // the appointment the user clicked on
    private CalendarLogic calendarLogic; // the calendarLogic object that manages the user's calendar
    private Calendar calendar; // a reference to the user's calendar (extracted from calendarLogic)

    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Setter and enables the visual elements
     * @param calendarViewController a reference to the controller that activated this one
     * @param calendarLogic the calendarLogic object that manages the user's calendar
     * @param appointmentInView the appointment the user clicked on
     */
    protected void viewAppointment(CalendarViewController calendarViewController, CalendarLogic calendarLogic, Appointment appointmentInView) {
        // Sets the variables of the controller such that other methods can access them
        this.calendarViewController = calendarViewController;
        this.calendarLogic = calendarLogic;
        this.calendar = calendarLogic.getCurrentCalendar();
        this.appointmentInView = appointmentInView;

        // Sets the visual elements to represent the appointment's details
        lblAppointmentName.setText(appointmentInView.getAppointmentName());
        lblPreviewStartTime.setText(appointmentInView.getStartHour() + ":" + appointmentInView.getStartMinute());
        lblPreviewStopTime.setText(appointmentInView.getStopHour() + ":" + appointmentInView.getStopMinute());
        lblAppointmentDescription.setText(appointmentInView.getAppointmentDescription());
    }


    /**
     * Deletes the appointment in view from the user's calendar
     * @param event
     * @throws IOException
     */
    public void deleteAppointment(ActionEvent event) throws IOException {

        // Removes the appointment from the calendar and saves it in the json file
        calendar.removeAppointment(appointmentInView);
        CalendarSaveHandler.save(calendar);

        String nextScene = "CalendarView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        this.root = loader.load();

        // Updates the calendar view
        this.calendarViewController.viewCalendar(calendar);

        changeScene(event, this.root, nextScene);
    }

    /**
     * Opens the edit window (MakeAppointmend.fxml) and readies the respective controller
     * @param event
     * @throws IOException
     */
    public void editAppointment(ActionEvent event) throws IOException {

        String nextScene = "MakeAppointment.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        this.root = loader.load();
        
        // Gets the controller that is connected to the MakeAppointment.fxml and intializes the setup to edit an appointment
        // Since the same controller is also used to create new appointments from scratch,
        // the arguments "inEditMode" and "editAppointment" are set to true and to the appointmentInView
        MakeAppointmentController makeAppointmentController = loader.getController();
        makeAppointmentController.intialize(this.calendarViewController, this.calendarLogic, true, this.appointmentInView);

        changeScene(event, this.root, nextScene);
    }

    
    /**
     * Exits the current scene and goes "back to CalendarView.fxml and the respective controller"
     * @param event
     * @throws IOException
     */
    public void exitView(ActionEvent event) throws IOException {
        String nextScene = "CalendarView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        this.root = loader.load();
        changeScene(event, this.root, nextScene);
    }


    //Method for switching between scenes
    private void changeScene(ActionEvent event, Parent root, String sceneName) throws IOException{
        stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
