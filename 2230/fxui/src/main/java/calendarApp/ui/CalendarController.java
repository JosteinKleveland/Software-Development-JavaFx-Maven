package calendarApp.ui;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.core.DaysOfTheWeek;
import calendarApp.json.CalendarSaveHandler;

public class CalendarController {
    private CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();

    //Declare FXML elements from Calendar.fxml

    //Calender management
    @FXML private Button saveNewCalendar_btn;
    @FXML private Button loadExistingCalendar_btn;
    @FXML private Button deleteCalendar_btn;
    @FXML private TextField calendarName_txt;
    @FXML private Text outputField_txt;
   
   
    //Showing calendar
    @FXML private Text currentCalendarName_txt;
    @FXML private GridPane showWeek_gridpane;

    //Appointment management

    @FXML private TextField appointmentName_txt;
    @FXML private TextField TextFieldstartTime_txt;
    @FXML private TextField TextFieldstopTime_txt;
    @FXML private Button addAppointment_btn;
    @FXML private Button changeAppointment_btn;
    @FXML private Button deleteAppointment_btn;
    

    //To save, load and delete calendars 

    //Method called by clicking on the "save" button in the GUI
    @FXML
    public void saveCalendar() throws IllegalArgumentException {
        //Getting the text input from the text field
        String calendarName = calendarName_txt.getText();

        //Checking whether the text field is empty or not
        if(calendarName.length() == 0){
            outputField_txt.setText("Calendar name cannot be empty");
            throw new IllegalArgumentException("Calendar name cannot be empty");
        }

        //Checks whether the calendar name exists from before.
        if(calendarSaveHandler.checkIfFileExists(calendarName)) {
            outputField_txt.setText("Calendar name already exists, choose another");
            throw new IllegalArgumentException("Calendar name exists from before");
        }

        try {
            //Text field is not empty, and the save handling is forwarded to CalendarSaveHandler
            Calendar calendar = new Calendar(calendarName);
            CalendarSaveHandler.save(calendar);
            outputField_txt.setText("Calendar name is saved");
            calendarName_txt.setText("");
        }
        catch (IOException e) {
            outputField_txt.setText("Could not save calendar name");
            e.printStackTrace();
        }
    }


    // Method called by clicking the load calendar button.
    @FXML
    public void loadExistingCalendar(){
        String calendarName = calendarName_txt.getText();
            
        try {
            Calendar calendar = calendarSaveHandler.load(calendarName);
            fillWeekGrid(calendar);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    
    // Method called by clicking the delete calendar button.
    @FXML
    public void deleteCalendar(){
          // Will be implemented in deliverable 3 
    }

    // To administrate grid 

    //Method to fill gridpane with calendar information
    @FXML
    private void fillWeekGrid(Calendar calendar){
        CalendarLogic calendarLogic = new CalendarLogic();
        calendarLogic.setCurrentCalendar(calendar);

        // Changes preview name in GUI
        currentCalendarName_txt.setText(calendarLogic.getCurrentCalendar().getCalendarName());

        // Adding the appointments in the gridpain

        // Variables to place the appointments in the correct cell of the gridpane
        int mo, tu, we, th, fr, sa, su = 1;

        for (Appointment a : calendarLogic.getCurrentCalendar().getAppointments){

            // Adding the apointment to the correct day

            if (a.getDayOfTheWeek() == DaysOfTheWeek.MONDAY){
                showWeek_gridpane.add(new Label(a.toString()), mo, 1);
                mo ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.TUESDAY){
                showWeek_gridpane.add(new Label(a.toString()), tu, 2);
                tu ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.WEDNESDAY){
                showWeek_gridpane.add(new Label(a.toString()), we, 3);
                we ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.THURSDAY){
                showWeek_gridpane.add(new Label(a.toString()), th, 4);
                th ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.FRIDAY){
                showWeek_gridpane.add(new Label(a.toString()), fr, 5);
                fr ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.SATURDAY){
                showWeek_gridpane.add(new Label(a.toString()), sa, 6);
                sa ++;
            }

            else if (a.getDayOfTheWeek() == DaysOfTheWeek.SUNDAY){
                showWeek_gridpane.add(new Label(a.toString()), su, 7);
                su ++;
            }

        }

    }


    // To administrate appointments

    //Method to add new appointment
    @FXML
    public void addAppointment(){
        // Will be implemented in deliverable 3 
    }


    //Method to delete appointment
    @FXML
    public void deleteAppointment(){
        // Will be implemented in deliverable 3 
    }

   
    //Method to change appointment
    @FXML
    public void changeAppointment(){
        // Will be implemented in deliverable 3 
    }


}
