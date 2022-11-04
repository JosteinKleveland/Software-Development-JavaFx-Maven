package calendarApp.ui;

import java.io.IOException;

import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class CalendarViewController {
    
    @FXML private Button btnExitCalendar;
    @FXML private Button btnNewAppointment;
    @FXML private Button btnDeleteCalendar;

    @FXML private Label lblCalendarNamePreview;
    @FXML private Label lblFeedback;

    @FXML private GridPane gridShowCalendar;

    private CalendarLogic calendarLogic;
    private Calendar currentCalendar;

    private Stage stage;
    private Scene scene;
    private Parent root;



    public void initialize(Calendar calendar) {
        this.calendarLogic = new CalendarLogic();
        calendarLogic.setCurrentCalendar(calendar);
        viewCalendar();
    }

    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    public void viewCalendar() {
        
    }

    public void deleteCalendar() {

    }

    public void openAppointment(ActionEvent event) throws IOException {
        String nextScene = "ViewAppointment.fxml";

        changeScene(event, nextScene); 

    }

    public void exitCalendar(ActionEvent event) throws IOException {

        String nextScene = "WelcomeWindow.fxml";
        changeScene(event, nextScene);
    }
  
    
    
    public void createNewAppointment(ActionEvent event) throws IOException {
        String nextScene = "MakeAppointment.fxml";
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
