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

// Controller to create a new calendar or load an existing one
// Each of these functions opens the calendar view scene
public class WelcomeWindowController {
    
    @FXML private Button btnNewCalendar;
    @FXML private Button btnLoadCalendar;
    @FXML private TextField txtCalendarNameInput;
    @FXML private Label lblFeedback;
    
    CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();

    private Stage stage;
    private Scene scene;
    private Parent root;

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
        if(calendarSaveHandler.checkIfFileExists(calendarName)) {
            lblFeedback.setText("Calendar name already exists, choose another");
            throw new IllegalArgumentException("This calendar name already exists");
        }

        // Prepares the root for the method that switches scenes
        // and to activate the respective fxml and controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
        try {
            this.root = loader.load();
        } catch (IOException e1) {
            lblFeedback.setText("An error occured loading calendar page");
            e1.printStackTrace();
        }
        
        // Gets the controller that is connected to the CalendarView.fxml
        // Used to pass the Calendar object from this controller to CalendarViewController
        // and to call on its initialize method
        CalendarViewController calendarViewController = loader.getController();
        
        try {
            // Creates and saves the new calendar and 
            Calendar calendar = new Calendar(calendarName);
            CalendarSaveHandler.save(calendar);

            // Changes window to CalenderView and sets up the respective controller with the calendar   
            changeToCalendarViewWindow(event, this.root);
            calendarViewController.initialize(calendar);
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

        //Checks whether the calendar name exists from before
        if(!calendarSaveHandler.checkIfFileExists(calendarName)) {
            lblFeedback.setText("Calendar name does not exists");
            throw new IllegalArgumentException("Calendar name does not exist");
        }
        
        // Prepares the root for the method that switches scenes
        // and to activate the respective fxml and controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
        try {
            this.root = loader.load();
        } catch (IOException e1) {
            lblFeedback.setText("An error occured. Could not create new calendar.");
            e1.printStackTrace();
        }

        // Gets the controller that is connected to the CalendarView.fxml
        // Used to pass the Calendar object from this controller to CalendarViewController
        // and to call on its initialize method
        CalendarViewController calendarViewController = loader.getController();

        try {
            // Loads the Calendar object with name calendarName
            Calendar calendar = CalendarSaveHandler.load(calendarName);
            
            // Changes window to CalenderView and sets up the respective controller with the calendar
            changeToCalendarViewWindow(event, this.root);
            calendarViewController.initialize(calendar);   

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
