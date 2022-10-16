package calendarApp.ui;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private CalendarLogic calendarLogic = new CalendarLogic();
    private Calendar calendar = null;

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
    @FXML private TextField startTime_txt;
    @FXML private TextField stopTime_txt;
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
            if (calendar == null) {
                calendar = new Calendar(calendarName);
                CalendarSaveHandler.save(calendar);
            }
            else {
                CalendarSaveHandler.save(calendar);
            }
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
        outputField_txt.setText("");

        try {
            calendar = CalendarSaveHandler.load(calendarName);

            fillWeekGrid(calendar);
        } catch (JsonParseException e) {
            e.printStackTrace();
            outputField_txt.setText("An error occured");
        } catch (JsonMappingException e) {
            e.printStackTrace();
            outputField_txt.setText("An error occured");
        } catch (IOException e) {
            e.printStackTrace();
            outputField_txt.setText("An error occured");
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
        
        calendarLogic.setCurrentCalendar(calendar);

        // Changes preview name in GUI
        currentCalendarName_txt.setText(calendar.getCalendarName());

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
    public void addAppointment() throws JsonProcessingException, IOException{
        String appointmentName = appointmentName_txt.getText();
        int[] startTime = decodeClock(startTime_txt.getText());
        int[] stopTime = decodeClock(stopTime_txt.getText());
        int startHour = startTime[0];
        int startMinute = startTime[1];
        int stopHour = stopTime[0];
        int stopMinute = stopTime[1];
        
        calendar.addAppointment(new Appointment(appointmentName, DaysOfTheWeek.MONDAY, startHour, stopHour, startMinute, stopMinute));

        CalendarSaveHandler.save(calendar);
        fillWeekGrid(calendar);
    }
    
    //Method that decodes clock of the format 00:00
    //returns an int array with 2 memory places [0] -> int hour, [1] -> int minute
    public int[] decodeClock(String time) {
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
