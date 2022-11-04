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



    public void initialize(Calendar calendar) {
        this.currentCalendar = calendar;
        this.calendarLogic = new CalendarLogic();
        calendarLogic.setCurrentCalendar(calendar);
        viewCalendar(calendar);
    }



    @FXML
    public void viewCalendar(Calendar calendar) {
        
        this.calendarLogic.setCurrentCalendar(calendar);

        // Changes preview name in GUI
        lblCalendarNamePreview.setText(calendar.getCalendarName());

        // Adding the appointments in the gridpain

        // Variables to place the appointments in the correct cell of the gridpane
        int mo = 1;
        int tu = 1;
        int we = 1;
        int th = 1;
        int fr = 1;
        int sa = 1;
        int su = 1;

        for (Appointment a : calendar.getAppointments()){

            // Adding the apointment to the correct day

            if (a.getDayOfTheWeek() == DaysOfTheWeek.MONDAY){
                gridShowCalendar.add(new Label(a.toString()), mo, 1);
                mo ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.TUESDAY){
                gridShowCalendar.add(new Label(a.toString()), tu, 2);
                tu ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.WEDNESDAY){
                gridShowCalendar.add(new Label(a.toString()), we, 3);
                we ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.THURSDAY){
                gridShowCalendar.add(new Label(a.toString()), th, 4);
                th ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.FRIDAY){
                gridShowCalendar.add(new Label(a.toString()), fr, 5);
                fr ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.SATURDAY){
                gridShowCalendar.add(new Label(a.toString()), sa, 6);
                sa ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.SUNDAY){
                gridShowCalendar.add(new Label(a.toString()), su, 7);
                su ++;
            }

        }

    


        
    }


    // Method to delete current calendar
    public void deleteCalendar(ActionEvent event) throws IOException {    
        //Not yet implemented in save handeler
        //CalendarSaveHandler.delete(getCurrentCalendar().getCalendarName());
        changeScene(event, "welcomeWindow.fxml");
    }

    public void openAppointment(ActionEvent event) throws IOException {
        String nextScene = "ViewAppointment.fxml";

        changeScene(event, nextScene); 

    }

    // Exiting current calendar, and going back to welcome window
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
