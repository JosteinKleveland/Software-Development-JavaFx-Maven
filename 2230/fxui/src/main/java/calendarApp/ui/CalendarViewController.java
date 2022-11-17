package calendarApp.ui;

import java.io.IOException;
import java.util.List;

import java.util.ArrayList;
import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;
import calendarApp.json.CalendarSaveHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

// Manages the main view of the app. Showing the selected calender, selecting appointments,
// navigating back to the welcome window, deleting appointments, deleting the calendar, and setting up the creation of new appointments
public class CalendarViewController {
    
    //FXML elements
    @FXML private VBox chosenAppointmentCard;
    @FXML private Label lblChosenAppointmentTime;
    @FXML private Label lblChosenAppointmentDescription;
    @FXML private Label lblChosenAppointmentName;
    @FXML private Label lblFeedbackText;

    //Appointment management
    @FXML private Button btnNewAppointment;

    //Calendar management
    @FXML private Button btnDeleteCalendar;

    //View of current calendar
    @FXML private Label lblCalendarNamePreview;
    @FXML private GridPane gridCalendar;
    @FXML private ScrollPane scroll;

    //Navigation
    @FXML private Button btnExitCalendar;

    //State variables
    private Calendar currentCalendar;
    private Appointment chosenAppointment;

    private Stage stage;
    private Scene scene;
    
    private List<Appointment> appointmentList = new ArrayList<>();

    private final String[] appointmentcolors = {"f16c31;\n","FFC285;\n","EE7FF7;\n","6F7DF7;\n","FC38B2;\n","A2FF88;\n","FF3939;\n","FFEC39;\n","C2D632;\n","74E2B0;\n","74B6E2;\n","2CFF95;\n","FCC0E9;\n","B4E29F;\n","EF7D30;\n"};
    private int lastAppointmentColor = 0;

    private CalendarLogicAccess calendarLogicAccess;

    /**
     * Sets up the state of the controller and activates the calendar view
     * The function is called when moving from another scene to CalendarView.fxml
     * @param calendar the user's Calendar
     */
    protected void initialize(Calendar calendar) {
        try {
            this.currentCalendar = calendar;
            viewCalendar();
        } catch (IOException e) {
            lblFeedbackText.setText("Failed to load calendar, please contact support"); 
            e.printStackTrace();
        } 
    }

    /**
   * Sets the CalendarLogicAccess for this controller,
   * so data can come from different sources.
   *
   * @param calendarLogicAccess the new CalendarLogicAccess to use
   */
    protected void setCalendarLogicAccess(CalendarLogicAccess calendarLogicAccess) {
        this.calendarLogicAccess = calendarLogicAccess;
    }

    public List<Appointment> getData(){
        List<Appointment> appointments = this.currentCalendar.getAppointments();
        this.appointmentList = appointments;
        return appointments;
    } 

    /**
     * Sets the chosen-appointment details card to the respective values of the desired appointment
     * @param appointment the desired appointment
     */
    private void setChosenAppointment(Appointment appointment){

        if (appointment == null){
            lblChosenAppointmentName.setText("");
            lblChosenAppointmentDescription.setText("");
            lblChosenAppointmentTime.setText("");
            chosenAppointmentCard.setVisible(false);
        }

        else {
        lblChosenAppointmentName.setText(appointment.getAppointmentName());
        lblChosenAppointmentDescription.setText(appointment.getAppointmentDescription());
        lblChosenAppointmentTime.setText(convertToTwoDidgets(appointment.getStartHour())+":"+convertToTwoDidgets(appointment.getStartMinute())+" - "+convertToTwoDidgets(appointment.getStopHour())+":"+ convertToTwoDidgets(appointment.getStopMinute()));
        
        this.chosenAppointment = appointment;
        }

    }

    /**
     * Deletes the user's calendar forever and returns to the welcome window
     * @param event
     */
    public void deleteCalendar(ActionEvent event) {
        
        //Allert the user that the delete action is permanent
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Delete calendar");
        alert.setHeaderText("Are you sure you want to delete this calendar?");
        alert.setContentText("The action can not be undone");

        //Delete the calendar if the user agree
        if (alert.showAndWait().get() == ButtonType.OK){
            try {
                this.calendarLogicAccess.deleteCalendar(currentCalendar.getCalendarName());
                String nextScene = "WelcomeWindow.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
                Parent root = loader.load();
                changeScene(event, root, "WelcomeWindow.fxml");
            } catch (IOException e) {
                lblFeedbackText.setText("Something went wrong, please contact support"); 
                e.printStackTrace();
            }
        }
    }

     /**
      * Delete the user's already selected appointment
      * @param event
      */
     public void deleteAppointment(ActionEvent event) {
            //Allert the user that the delete action is permanent
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setTitle("Delete Appointment");
            alert.setHeaderText("Are you sure you want to delete this appointment?");
            alert.setContentText("The action can not be undone");

            //Delete the calendar if the user agree
            if (alert.showAndWait().get() == ButtonType.OK){
                currentCalendar.removeAppointment(chosenAppointment);
                //Set preview of selected appointment to first appointment in list
                chosenAppointmentCard.setVisible(false);
                try {
                    CalendarSaveHandler.save(currentCalendar);
                    viewCalendar();
                } catch (IOException e) {
                    lblFeedbackText.setText("Something went wrong, please contact support"); 
                    e.printStackTrace();
                }
            }
    }

    /**
     * Opens the edit window (MakeAppointmend.fxml) and readies the respective controller
     * with the appointment the user wants to edit
     * @param event
     * @throws IOException
     */
    public void editAppointment(ActionEvent event) {
        try {

            if (!(chosenAppointment == null)){
                String nextScene = "MakeAppointment.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
                Parent root = loader.load();
                
                // Gets the controller that is connected to the MakeAppointment.fxml and intializes the setup to edit an appointment
                // Since the same controller is also used to create new appointments from scratch,
                // the arguments "inEditMode" and "editAppointment" are set to true and to the appointmentInView
                MakeAppointmentController makeAppointmentController = loader.getController();
                makeAppointmentController.intialize(this.currentCalendar, true, this.chosenAppointment);

                changeScene(event, root, nextScene);
            }
        } catch (IOException e) {
            lblFeedbackText.setText("Something went wrong, please contact support"); 
            e.printStackTrace();
        } 
    }


   /**
    * Exiting current calendar, and going back to welcome window
    * @param event
    */
   public void exitCalendar(ActionEvent event) {
        try {
            String nextScene = "WelcomeWindow.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
            Parent root = loader.load();
            changeScene(event, root, nextScene);
        } catch (IOException e) {
            lblFeedbackText.setText("Something went wrong, please contact support");
            e.printStackTrace();
        }
    }
  
    /**
     * Opens the MakeAppointment.fxml scene and tells the respective controller
     * that the user wants to create a new appointment
     * @param event
     */
    public void createNewAppointment(ActionEvent event) {
        try {
            String nextScene = "MakeAppointment.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
            Parent root;
            root = loader.load();
            // Gets the controller that is connected to the MakeAppointment.fxml and intializes the setup to the create a new appointment
            // Since the same controller is used to edit appointments,
            // the arguments "inEditMode" and "editAppointment" are set to false and null
            MakeAppointmentController makeAppointmentController = loader.getController();
            makeAppointmentController.intialize(currentCalendar, false, null);
            changeScene(event, root, nextScene);
        } catch (IOException e) {
            lblFeedbackText.setText("Something went wrong, please contact support");
            e.printStackTrace();
        }
    }

    /**
     * Method for switching between scenes
     * @param event
     * @param root FXML scene root 
     */
    private void changeScene(ActionEvent event, Parent root, String sceneName) throws IOException{
        stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Help method to check and return the correct "column" for the appointment
     * @param appointment
     * @return an int representing the respective weekday column
     */
    private int checkDay(Appointment appointment) {
        if (appointment.getDayOfTheWeek() == DaysOfTheWeek.MONDAY){return 1;}
        else if (appointment.getDayOfTheWeek() == DaysOfTheWeek.TUESDAY){return 2;}
        else if (appointment.getDayOfTheWeek() == DaysOfTheWeek.WEDNESDAY){return 3;}
        else if (appointment.getDayOfTheWeek() == DaysOfTheWeek.THURSDAY){return 4;}
        else if (appointment.getDayOfTheWeek() == DaysOfTheWeek.FRIDAY){return 5;}
        else if (appointment.getDayOfTheWeek() == DaysOfTheWeek.SATURDAY){return 6;}
        else if (appointment.getDayOfTheWeek() == DaysOfTheWeek.SUNDAY){return 7;}
        else {
            throw new IllegalArgumentException("Appointment does not have a valid day");
        }
    }

    /**
     * Helper method to viewCalendar() to find the first time block of the appointment
     * @param appointment  
     * @return an int representing the first time block of the appointment
     */
    private int findFirstBlock(Appointment appointment){
        int firstBlock = (appointment.getStartHour()*4)+(appointment.getStartMinute()/15);
        return firstBlock+1;
    }

    
    /**
     * Helper method to viewCalendar() to calculate the number of 15 min blocks needed to disply the appointment in the grid
     * @param appointment Appointment to be displayed
     * @return an int representing the number of blocks needed
     */
    private int calculateNumberOfBlocks(Appointment appointment){
        int hours = (appointment.getStopHour()-appointment.getStartHour());
        int minutes = (appointment.getStopMinute() - appointment.getStartMinute());
        return (hours*4+(minutes/15));
    }

    /**
     * Sets the chosen-appointment details card to the first appointment in the Calendar
     * Called in viewCalendar() to setup the default view
     */
    private void setPreviewCard(){
        appointmentList.addAll(getData());
        if(appointmentList.size()>0){
            setChosenAppointment(appointmentList.get(0));
            CalendarListener calendarListener = new CalendarListener() {
                //Set default appointment in view at start
                @Override
                public void onClickListener(Appointment appointment){
                    setChosenAppointment(appointment);
                }
            };
            calendarListener.getClass();
        }
        else {
            setChosenAppointment(null);
        }

    }

    /**
     * Helper function of viewCalendar() that adds time labels to the grid
     */

    private void addTimeLabels(){

        int hour = 0;
        int minute = 0;
        Label timeLabel;
        int numberOfBlocks = 0;

        for (int i = 0; i < 24; i++){
            for(int j = 0; j < 4; j++)
            {
                timeLabel = new Label();
                timeLabel.setText(convertToTwoDidgets(hour) + ":" + convertToTwoDidgets(minute));
                gridCalendar.add(timeLabel, 0,numberOfBlocks+1);
                minute += 15;
                numberOfBlocks ++;
            }
            minute = 0;
            hour++;
        }

        Label mo = new Label("Monday");
        Label tu = new Label("Tuesday");
        Label we = new Label("Wednesday");
        Label th = new Label("Thursday");
        Label fr = new Label("Friday");
        Label sa = new Label("Saturday");
        Label su = new Label("Sunday");
        gridCalendar.add(mo,1,0);
        gridCalendar.add(tu,2,0);
        gridCalendar.add(we,3,0);
        gridCalendar.add(th,4,0);
        gridCalendar.add(fr,5,0);
        gridCalendar.add(sa,6,0);
        gridCalendar.add(su,7,0);

    }

    /**
     * Converts a one-digit int representing hour or minute and returns a two-digit version in a String
     * e.g. 3 -> "03"
     * @param numberToCheck
     * @return a String of the two-digit version of numberToCheck
     */
    private String convertToTwoDidgets(int numberToCheck ){
        String result;
        if (Integer.toString(numberToCheck).length() < 2){
            result = "0" + Integer.toString(numberToCheck);
            return result;
        }
        return Integer.toString(numberToCheck);
    }

    /**
     * Activates the chosen-appointment's "details view" if an appointment is clicked on
     * @param event
     * @param a Appointment clicked on
     */
    private void clickAppointment(MouseEvent event, Appointment a) {
        chosenAppointmentCard.setVisible(true);
        setChosenAppointment(a);
    }

    /**
     * Sets up the view of the user's calendar and all its appointments
     */
    private void viewCalendar() throws IOException {
        getData();
        gridCalendar.getChildren().clear();

        lblCalendarNamePreview.setText(currentCalendar.getCalendarName());
        setPreviewCard();
        addTimeLabels();

        String color;

        int numberOfBlocks;
        int firstBlock;
       
        Label appointmentLabel;
        Pane background;

        // For testing - fxid counter for appointment panes' id
        int fxid = 0;

        for (Appointment a : appointmentList){

            // Variables for appointment placement

            numberOfBlocks = calculateNumberOfBlocks(a);
            firstBlock = findFirstBlock(a);

            //Create pane for backgroud color
            background = new Pane();
            color = selectAppointmentColor();
            background.setStyle("-fx-background-color: #"+color+"-fx-background-radius: 5;");
            background.setId("A"+Integer.toString(fxid));

            appointmentLabel = new Label();
            appointmentLabel.setText(a.getAppointmentName());
            background.setOnMouseClicked(event -> clickAppointment(event,a));

            gridCalendar.add(background, checkDay(a),firstBlock, 1,numberOfBlocks);
            gridCalendar.add(appointmentLabel, checkDay(a),firstBlock);

            fxid+=1;
        }
    }  

    /**
     * Method to randomly generate a color for the appointmentblocks
     * @return the color represented through a String
     */
    private String selectAppointmentColor() {
        if (lastAppointmentColor == appointmentcolors.length) {
            this.lastAppointmentColor = 0;
        }
        else {
            lastAppointmentColor +=1;
        }

        return appointmentcolors[lastAppointmentColor];
    }
}