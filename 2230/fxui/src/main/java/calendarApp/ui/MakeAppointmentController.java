package calendarApp.ui;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MakeAppointmentController {
    
    @FXML TextField txtAppointmentName;
    @FXML TextField txtSetStartTime;
    @FXML TextField txtSetStopTime;
    @FXML TextArea txtSetAppointmentDescription;

    @FXML ChoiceBox drdSetAppointmentDay;

    @FXML Button btnSaveAppointment;
    @FXML Button btnCancelEdit;

    @FXML Label txtFeedbackEdit;

    private CalendarViewController calendarViewController;

    private boolean inEditMode;
    private Appointment editAppointment;


    public void intialize(CalendarViewController calendarViewController, boolean inEditMode, Appointment editAppointment) {
        this.inEditMode = inEditMode;
        if (inEditMode)
            this.editAppointment = editAppointment;
    }
    
    public void makeAppointment() {
        
    }

    public void exitView() {
        
    }

    //Helper method that decodes clock of the format 00:00
    //@returns an int array with 2 memory places [0] -> int hour, [1] -> int minute
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
}
