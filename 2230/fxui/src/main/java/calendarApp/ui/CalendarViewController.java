package calendarApp.ui;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ArrayList;
import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.core.DaysOfTheWeek;
import calendarApp.json.CalendarSaveHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

// Manages the main view of the app. Showing the selected calender, selected appointments, navigation back to welcome window, delete appointment, delete calendar and create new appointment
public class CalendarViewController implements Initializable {
    
    //FXML elements
    //Showing chosen appointment
    @FXML private VBox chossenAppointmentCard;
    @FXML private Label lbChosenAppointmentTime;
    @FXML private Label lblChosenAppointmentDescription;
    @FXML private Label lblChosenAppointmentName;

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
    private CalendarLogic calendarLogic;
    private Calendar currentCalendar;
    private Appointment chosenAppointment;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private List<Appointment> appointmentBlocks = new ArrayList<>();
    private CalendarListener calendarListener;

    private List<Appointment> getData(){
        List<Appointment> appointments = this.currentCalendar.getAppointments();
        return appointments;
    } 


    public void initialize(Calendar calendar) {
        this.currentCalendar = calendar;
        this.calendarLogic = new CalendarLogic();
        calendarLogic.setCurrentCalendar(calendar);
        viewCalendar(calendar);
    }


    @FXML
    public void viewCalendar(Calendar calendar) {
        //Not in use. Will be removed
    }

    private void setChosenAppointment(Appointment appointment){

        if (appointment == null){
            lblChosenAppointmentName.setText("");
            lblChosenAppointmentDescription.setText("");
            lbChosenAppointmentTime.setText("");
        }

        else {
        lblChosenAppointmentName.setText(appointment.getAppointmentName());
        lblChosenAppointmentDescription.setText(appointment.getAppointmentDescription());
        lbChosenAppointmentTime.setText(appointment.getStartHour()+":"+appointment.getStartMinute()+" - "+appointment.getStopHour()+":"+ appointment.getStopMinute());
        
        this.chosenAppointment = appointment;
        }

        

        //Will be repalced to allow appointments have dirfferent colors in their preview
        chossenAppointmentCard.setStyle("-fx-background-color: #"+"f16c31;\n"+"-fx-background-radius: 30;");

    }

    // Method to delete current calendar
    public void deleteCalendar(ActionEvent event) throws IOException {
        
        //Allert the user that the delete action is permanent
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Delete calendar");
        alert.setHeaderText("Are you sure you want to delete this calendar?");
        alert.setContentText("The action can not be undone");

        //Delete the calendar if the user agree
        if (alert.showAndWait().get() == ButtonType.OK){
            //CalendarSaveHandler.delete(currentCalendar.getCalendarName);
            changeScene(event,this.root, "welcomeWindow.fxml");
        }
    }

     // Method to delete selected appointment
     public void deleteAppointment(ActionEvent event) throws IOException {
        
        //Allert the user that the delete action is permanent
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Delete Appointment");
        alert.setHeaderText("Are you sure you want to delete this appointment?");
        alert.setContentText("The action can not be undone");

        //Delete the calendar if the user agree
        if (alert.showAndWait().get() == ButtonType.OK){
            currentCalendar.removeAppointment(chosenAppointment);
            //Set preview of selected appointment to first appointment in list
            setChosenAppointment(appointmentBlocks.get(0));
        }

    }


    //------------------------------------------------------
    //Denne må tilpasses nå
    public void openAppointment(ActionEvent event) throws IOException {
        
        // ! ! ! ! Man skal egentlig trykke på en Appointment ! ! ! !
        // Midlertidig ny tom appointment
        Appointment appointment = new Appointment("Fotball", "Husk å ta med ball", DaysOfTheWeek.MONDAY, 18, 19, 30, 30);
        // ! ! ! ! ! ! ! ! ! ! ! ! !  ! ! ! ! !  

        String nextScene = "ViewAppointment.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        this.root = loader.load();

        // Gets the controller that is connected to the ViewAppointment.fxml
        // and intializes the setup to view the respective appointment
        ViewAppointmentController viewAppointmentController = loader.getController();
        viewAppointmentController.viewAppointment(this, this.calendarLogic, appointment);

        changeScene(event,this.root, nextScene); 
    }

    //-------------------------------------------------------

   // Exiting current calendar, and going back to welcome window
   public void exitCalendar(ActionEvent event) throws IOException {

        String nextScene = "WelcomeWindow.fxml";
        changeScene(event,this.root, nextScene);
    }
  
    
    
    public void createNewAppointment(ActionEvent event) throws IOException {
        String nextScene = "MakeAppointment.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene));
        this.root = loader.load();

        // Gets the controller that is connected to the MakeAppointment.fxml and intializes the setup to the create a new appointment
        // Since the same controller is used to edit appointments,
        // the arguments "inEditMode" and "editAppointment" are set to false and null
        MakeAppointmentController makeAppointmentController = loader.getController();
        makeAppointmentController.intialize(this, this.calendarLogic, false, null);

        changeScene(event, this.root, nextScene);
    }


    // navigation
    //Method for switching between scenes
    private void changeScene(ActionEvent event, Parent root, String sceneName) throws IOException{
        stage = (Stage)(((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Calendar prevew
     //Help method to check and return the correct place for the appointment
    private int checkDay(Appointment appointment){
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

    //Help method to fint the first block of the event
    private int findFirstBlock(Appointment appointment){
        int firstBlock = (appointment.getStartHour()*4)+(appointment.getStartMinute()/15);

        return firstBlock;
    }

    
    //Help method to calculate the number of 15 min blocks needed to disply the appointment in the grid
    private int calculateNumberOfBlocks(Appointment appointment){
        int hours = (appointment.getStopHour()-appointment.getStartHour());
        int minutes = (appointment.getStopMinute() - appointment.getStartMinute());
        return (hours*4+(minutes/15));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appointmentBlocks.addAll(getData());
        if(appointmentBlocks.size()>0){
            setChosenAppointment(appointmentBlocks.get(0));
            calendarListener = new CalendarListener() {
                //Set default appointment in view at start
                @Override
                public void onClickListener(Appointment appointment){
                    setChosenAppointment(appointment);
                }
            };
        }
        else {
            setChosenAppointment(null);
            }
       

        int numberOfBlocks;
        int firstBlock;
        int row;

        try {
        for (int i=0; i<appointmentBlocks.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/resources/calendearApp/ui/Appointment.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            
            AppointmentController appointmentController = fxmlLoader.getController();
            appointmentController.setData(appointmentBlocks.get(i), calendarListener);

            //Check appointment length in 15min blocks,
            numberOfBlocks = calculateNumberOfBlocks(appointmentBlocks.get(i));
            
            //Check day for appointment
            row = checkDay(appointmentBlocks.get(i));

            //Find first block
            firstBlock = findFirstBlock(appointmentBlocks.get(i));

            //Adding appointment to calendar view block by block
            for (int j = 0; j> numberOfBlocks; j++){
                gridCalendar.add(anchorPane, firstBlock, row);
                firstBlock++;
                j++;
            
            //Margin for calendarblocks
            GridPane.setMargin(anchorPane,new Insets(0,2,0,2));
            }
            
            //Grid width
            gridCalendar.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridCalendar.setPrefWidth(Region.USE_COMPUTED_SIZE);
            gridCalendar.setMaxWidth(Region.USE_PREF_SIZE);


            //Grid height
            gridCalendar.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridCalendar.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridCalendar.setMaxHeight(Region.USE_PREF_SIZE);
        }
        
        } catch (IOException e) {

                e.printStackTrace();
            }
    }


}
