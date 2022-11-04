package calendarApp.ui;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import calendarApp.core.Calendar;
import calendarApp.json.CalendarSaveHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class WelcomeWindowController {
    
    @FXML private Button btnNewCalendar;
    @FXML private Button btnLoadCalendar;
    @FXML private TextField txtCalendarNameInput;
    @FXML private Label lblFeedback;

    private Stage stage;
    private Scene scene;

    /**
     * Creates a new calendar and activates the calendar view
     * @param event
     * @throws IllegalArgumentException if the calendar name input field is empty or if the calendar name already exists
     */
    public void newCalendar(ActionEvent event) throws IllegalArgumentException {

        //Getting the text input from the text field
        String calendarName = txtCalendarNameInput.getText();
        lblFeedback.setText("");

        //Checking whether the text field is empty or not
        if(calendarName.length() == 0){
            lblFeedback.setText("Calendar name cannot be empty");
            throw new IllegalArgumentException("Calendar name cannot be empty");
        }

        //Checks whether the calendar name exists from before.
        if(CalendarSaveHandler.checkIfFileExists(calendarName)) {
            lblFeedback.setText("Calendar name already exists, choose another");
            throw new IllegalArgumentException("This calendar name already exists");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
        CalendarViewController calendarViewController = loader.getController();

        Calendar calendar = new Calendar(calendarName);
         
        try {
            // Creates and saves the new calendar and 
            CalendarSaveHandler.save(calendar);

            // Changes window to CalenderView and sets up the respective controller with the calendar   
            calendarViewController.initialize(null);
            changeToCalendarViewWindow(event, loader.load());
            }
            catch (IOException e) {
                lblFeedback.setText("An error occured. Could not create new calendar.");
                e.printStackTrace();
            }
    }

  
    /**
     * Loads the requested calendar and activates calendar view
     * @param event
     * @throws IllegalArgumentException if requested calendar does not exist
     */
    public void loadCalendar(ActionEvent event) throws IllegalArgumentException {

        //Getting the text input from the text field
        String calendarName = txtCalendarNameInput.getText();
        lblFeedback.setText("");

        //Checks whether the calendar name exists from before.
        if(!CalendarSaveHandler.checkIfFileExists(calendarName)) {
            lblFeedback.setText("Calendar name does not exists");
            throw new IllegalArgumentException("Calendar name does not exist");
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
        CalendarViewController calendarViewController = loader.getController();


        try {
            // Loads the Calendar object with name calendarName
            Calendar calendar = CalendarSaveHandler.load(calendarName);
            // Changes window to CalenderView and sets up the respective controller with the calendar   
            calendarViewController.initialize(calendar);
            changeToCalendarViewWindow(event, loader.load());

        } catch (JsonParseException e) {
            e.printStackTrace();
            lblFeedback.setText("An error occured");
        } catch (JsonMappingException e) {
            e.printStackTrace();
            lblFeedback.setText("An error occured");
        } catch (IOException e) {
            e.printStackTrace();
            lblFeedback.setText("An error occured");
        }
    }

    /**
     * Helper method to switch stage to CalendarView
     * @param event
     * @param root
     * @throws IOException
     */
    private void changeToCalendarViewWindow(ActionEvent event, Parent root) throws IOException {
        stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
