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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Handles the making of new appointments, as well as editing of existing ones
public class MakeAppointmentController {
    
    ObservableList list = FXCollections.observableArrayList();

    // FXML elements
    @FXML TextField txtAppointmentName;
    @FXML TextField txtSetStartTime;
    @FXML TextField txtSetStopTime;
    @FXML TextArea txtSetAppointmentDescription;

    @FXML ChoiceBox<String> drdSetAppointmentDay;

    @FXML Button btnSaveAppointment;
    @FXML Button btnCancelEdit;

    @FXML Label txtFeedbackEdit;

    // State variables
    private Stage stage;
    private Scene scene;

    private Calendar currentCalendar; // the current calendar of the user
    private CalendarLogic calendarLogic;

    private boolean inEditMode; // true if the user is editing an appointment, and false otherwise
    private Appointment editAppointment; // the appointment being edited, can be null

    private String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

    /**
     * Sets up the state of the class
     * @param currentCalendar the users current calendar
     * @param inEditMode flags if the user is in edit mode, or simply creates a new appointment
     * @param editAppointment the potential Appointment in edit mode
     */
    protected void intialize(Calendar currentCalendar, boolean inEditMode, Appointment editAppointment) {
        drdSetAppointmentDay.getItems().addAll(days);
        this.currentCalendar = currentCalendar;
        this.calendarLogic = new CalendarLogic(currentCalendar);
        this.inEditMode = inEditMode;

        if (inEditMode){

            this.editAppointment = editAppointment;
            txtAppointmentName.setText(editAppointment.getAppointmentName());

            txtAppointmentName.setText(editAppointment.getAppointmentName());
            txtSetStartTime.setText(convertToTwoDidgets(editAppointment.getStartHour())+":"+convertToTwoDidgets(editAppointment.getStartMinute()));
            txtSetStopTime.setText(convertToTwoDidgets(editAppointment.getStopHour())+":"+convertToTwoDidgets(editAppointment.getStopMinute()));
            txtSetAppointmentDescription.setText(editAppointment.getAppointmentDescription());
            drdSetAppointmentDay.setValue(editAppointment.getDayOfTheWeek().name().toLowerCase());
        }

            
    }
    
    
    /**
     * Makes a new Appointment in the Calendar and if in edit mode - deletes the previous one
     * @param event
     * @throws IOException
     */
    public void makeAppointment(ActionEvent event) {

        String appointmentName = txtAppointmentName.getText();
        String appointmentDescription = txtSetAppointmentDescription.getText();
        String weekDay = (String) drdSetAppointmentDay.getValue();

        try {
        if (inEditMode) {     
            currentCalendar.removeAppointment(editAppointment);
        }

        int[] startTime = decodeClock(txtSetStartTime.getText());
        int[] stopTime = decodeClock(txtSetStopTime.getText());
        int startHour = startTime[0];
        int startMinute = startTime[1];
        int stopHour = stopTime[0];
        int stopMinute = stopTime[1];

        // If we are in editing mode (in practice came from "ViewAppointment.fxml"), then remove the current appointment
        // before creating a new one with the desired attributes
       
        Appointment newAppointment = new Appointment(appointmentName, appointmentDescription, DaysOfTheWeek.valueOf(weekDay.toUpperCase()), startHour, stopHour, startMinute, stopMinute);
        calendarLogic.addAppointmentToCalendar(currentCalendar, newAppointment);
        
        // Saves the updated version of the calendar in the respective JSON file
        CalendarSaveHandler.save(currentCalendar);

        // Changes back to the calendar view with the updated view
        String nextScene = "CalendarView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        Parent root = loader.load();
        CalendarViewController calendarViewController = loader.getController();
        calendarViewController.initialize(currentCalendar);
        changeScene(event, root, nextScene);

        } 
        catch (IOException e) {
            txtFeedbackEdit.setText("Something went wrong, please contact support");
            e.printStackTrace();
        } 
        catch (Exception e) {
            txtFeedbackEdit.setText(e.getMessage());
            e.printStackTrace();
        }
        
    }

    /**
     * Switches scene to the calendar view 
     * @param event
     * @throws IOException
     */
    public void exitView(ActionEvent event) {
        String nextScene = "CalendarView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        Parent root;
        try {
            root = loader.load();
            CalendarViewController calendarViewController = loader.getController();
            calendarViewController.initialize(currentCalendar);
            changeScene(event, root, nextScene);
        } catch (IOException e) {
            txtFeedbackEdit.setText("An error occured, could not close window");
        }

    }

    //Helper method that decodes clock of the format 00:00
    //@returns an int array with 2 memory places [0] -> int hour, [1] -> int minute
    private int[] decodeClock(String time) throws IllegalArgumentException {
        try {
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
        catch (Exception e) {
            throw new IllegalArgumentException("Time needs to be on the format xx:xx");
        }
    }

    private String convertToTwoDidgets(int numberToCheck ){
        String result;
        if (Integer.toString(numberToCheck).length() < 2){
            result = "0" + Integer.toString(numberToCheck);
            return result;
        }
        return Integer.toString(numberToCheck);
    }


     //Method for switching between scenes
     private void changeScene(ActionEvent event, Parent root, String sceneName) throws IOException{
        stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    // Getter methods for controller testinng

    public Calendar getCalendar(){
        Calendar calendar = this.currentCalendar;
        return calendar;
    }


    public Boolean getInEditmBoolean(){
        return inEditMode;
    }


}
