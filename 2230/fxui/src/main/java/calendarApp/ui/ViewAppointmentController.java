package calendarApp.ui;

import java.io.IOException;

import calendarApp.core.Appointment;
import calendarApp.core.CalendarLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ViewAppointmentController {
    
    @FXML Label lblAppointmentName;
    @FXML Label lblPreviewStartTime;
    @FXML Label lblPreviewStopTime;
    @FXML Label lblAppointmentDescription;
    
    @FXML Button btnEditAppointment;
    @FXML Button btnDeleteAppointment;

    private CalendarViewController calendarViewController;
    private Appointment appointmentInView;
    private CalendarLogic calendarLogic;

    
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void viewAppointment(CalendarViewController calendarViewController, CalendarLogic calendarLogic, Appointment appointmentInView) {
        this.calendarViewController = calendarViewController;
        this.calendarLogic = calendarLogic;
        this.appointmentInView = appointmentInView;
    }

    public void deleteAppointment(ActionEvent event) throws IOException {
        String nextScene = "MakeAppointment.fxml";
        changeScene(event, nextScene);

    }

    public void editAppointment(ActionEvent event) throws IOException {
        String nextScene = "MakeAppointment.fxml";
        changeScene(event, nextScene);

    }


    public void exitView(ActionEvent event) throws IOException {

        String nextScene = "CalendarView.fxml";
        changeScene(event, nextScene);

    }


    //Method for switching between scenes
    private void changeScene(ActionEvent event, String sceneName) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(sceneName));
        stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }






}
