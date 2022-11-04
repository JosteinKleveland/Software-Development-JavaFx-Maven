package calendarApp.ui;

import java.io.IOException;

import calendarApp.core.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WelcomeWindowController {
    
    @FXML private Button btnNewCalendar;
    @FXML private Button btnLoadCalendar;
    @FXML private TextField txtCalendarNameInput;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void newCalendar(ActionEvent event) throws IOException {
        changeToCalendarViewWindow(event);
    }

  
    public void loadCalendar(ActionEvent event) throws IOException {
        
        changeToCalendarViewWindow(event);

    }

    // Method to swtch stage to CalendarView
    private void changeToCalendarViewWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
        stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
